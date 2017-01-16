package com.marlin.tralp.Transcriber.FeatureProcess;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;

import com.marlin.tralp.AppContext;
import com.marlin.tralp.Conexao.DbConnection;
import com.marlin.tralp.MainApplication;
import com.marlin.tralp.Transcriber.tr_Models.tr_FeatureStructure;
import com.marlin.tralp.Transcriber.tr_Models.tr_Sinal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Created by gabriel on 16-02-16.
 */
public class SignClassification {

    SQLiteDatabase db;
    MainApplication mApp;

    ArrayList<tr_FeatureStructure> annotations;
    ArrayList<String> words;

    public SignClassification(MainApplication app)
    {
        mApp = app;
        annotations = mApp.annotation;
        words = new ArrayList<String>();
        connectDb();
    }

    public ArrayList<String> process() {
        int i = 0;
        ArrayList<tr_Sinal> candidates = new ArrayList<tr_Sinal>();
        tr_Sinal found;
        for (; i < annotations.size(); i++) {
            while(candidates.size() == 0){
                findSigns(annotations.get(i), candidates);
                i++;
            }
            i = filterSignal(candidates, i);

            if (candidates.size() >= 1){
                words.add(candidates.get(0).descricao);
                candidates.clear();
            }
        }

        return words;
    }

    private void findSigns(tr_FeatureStructure props, ArrayList<tr_Sinal> candidates ){
        String query =
                "SELECT ID" +
                "FROM tr_SINAL AS S" +
                "  WHERE S.POSX = " + props.handRelativeX +
                "  AND S.POSY = " + props.handRelativeY +
                "  AND S.ID_CONFIG_MAO = " + props.idConfigMao;
        Cursor results = db.rawQuery(query,null);
        for (results.moveToFirst(); !results.isAfterLast(); results.moveToNext()) {
            candidates.add(new tr_Sinal(getI(results,"ID")));
        }
    }

    private int filterSignal(ArrayList<tr_Sinal> toFilter, int index ){

        for (tr_Sinal candidate: toFilter){
            tr_Sinal.estado result = candidate.aceita(annotations.get(index));
            if (result == tr_Sinal.estado.falhou) {
                toFilter.remove(candidate);
                continue;
            }
            if (result == tr_Sinal.estado.concluiu){
                while (result == tr_Sinal.estado.concluiu){
                    index++;
                    result = candidate.aceita(annotations.get(index));
                }
                toFilter.clear();
                toFilter.add(candidate);
                return index;
            }
        }

        return index;
    }

    private int getI(Cursor c, String key ){
        return c.getInt(c.getColumnIndexOrThrow(key));
    }

    private void connectDb(){
        DbConnection connection = new DbConnection(new AppContext().getAppContext());
        try {
            connection.createDataBase();
        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        }
        try {
            db = connection.openDataBase();
        } catch (SQLException sql) {
            throw sql;
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
    }

}

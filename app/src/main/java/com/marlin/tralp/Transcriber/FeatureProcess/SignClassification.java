package com.marlin.tralp.Transcriber.FeatureProcess;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;
import android.util.Log;

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
        db.close();
        return words;
    }

    private void findSigns(tr_FeatureStructure props, ArrayList<tr_Sinal> candidates ){
        String query =
                "SELECT ID\n" +
                "FROM tr_SINAL\n" +
                "  WHERE POSX = " + 0  + "\n" + //props.handRelativeX
                "  AND POSY = " + 8 + "\n" + //props.handRelativeY
                "  AND ID_CONFIG_MAO = " + 3; //props.idConfigMao;
        Log.d("QUERY findsings", query);
        Cursor results = db.rawQuery(query,null);
        for (results.moveToFirst(); !results.isAfterLast(); results.moveToNext()) {
            try{
                tr_Sinal temp = new tr_Sinal(getI(results,"ID"));
                candidates.add(temp);
            } catch(Exception e){
                Log.d("SignClassification", "findSigns: " + e.getMessage().toString());
            }
        }
    }

    private int filterSignal(ArrayList<tr_Sinal> toFilter, int index ){
        int candIndex = 0;
        while (toFilter.size() > 0){
            if(index >= annotations.size()) return index;

            tr_Sinal candidate = toFilter.get(candIndex);
            tr_Sinal.estado result = candidate.aceita(annotations.get(index));

            if (result == tr_Sinal.estado.falhou) {
                toFilter.remove(candIndex);
                candIndex--;
            }
            else if (result == tr_Sinal.estado.concluiu){
                while (result == tr_Sinal.estado.concluiu){
                    index++;
                    result = candidate.aceita(annotations.get(index));
                }
                toFilter.clear();
                toFilter.add(candidate);
                return index;
            }

            candIndex++;
            if(candIndex == toFilter.size() &&  toFilter.size() != 0){
                index++;
                candIndex=0;
            }
        }

        return index;
    }

    private int getI(Cursor c, String key ){
        int temp = c.getInt(c.getColumnIndexOrThrow(key));
        return temp;
    }

    private void connectDb(){
        DbConnection connection = new DbConnection(MainApplication.getContext());
        try {
            connection.createDataBase();
        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        }catch (Exception e) {
            Log.d("SignClass", "connectDb: " + e.getMessage().toString());
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

package com.marlin.tralp.Transcriber.tr_Models;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;

import com.marlin.tralp.AppContext;
import com.marlin.tralp.Conexao.DbConnection;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gabriel on 2017-01-14.
 */

public class tr_Sinal {
    public List<tr_Movimento> movimentosEmOrdem;
    int index_mov_concluido;

    public String descricao;
    public int idSinal;
    public int posX;
    public int posY;
    public int idConfigMao;
    public boolean isStatic;

    ArrayList<tr_Movimento> movimentos;

    public enum estado{
        falhou,
        concluiu,
        andamento
    }

    public tr_Sinal(int idSinal){
        this.idSinal = idSinal;
        loadInfo();
        if(movimentosEmOrdem.size()==0)
            isStatic = true;
        index_mov_concluido = 0;
    }

    public estado aceita(tr_FeatureStructure fs){
        if(isStatic) return staticProcess(fs);

        boolean result = movimentosEmOrdem.get(index_mov_concluido).aceita(fs);
        if(result && index_mov_concluido < movimentosEmOrdem.size() -1)
            return estado.andamento;

        if(result) return estado.concluiu;

        if (!result && index_mov_concluido < movimentosEmOrdem.size() -1 ){
            result = movimentosEmOrdem.get(index_mov_concluido+1).aceita(fs);
            if(!result) return estado.falhou;
            index_mov_concluido++;
            if(index_mov_concluido == movimentosEmOrdem.size()-1)
                return estado.concluiu;
            return estado.andamento;
        }

        return estado.falhou;
    }

    private estado staticProcess(tr_FeatureStructure fs){
        if(fs.idConfigMao != fs.idConfigMao) return estado.falhou;
        if( fs.handRelativeX > posX + 1 || fs.handRelativeX < posX -1)
            return estado.falhou;
        if( fs.handRelativeY > posY + 1 || fs.handRelativeY < posY -1)
            return estado.falhou;
        return estado.concluiu;
    }

    private void loadInfo(){
        SQLiteDatabase db = connectDb();
        String queryBasics =
                "SELECT * " +
                "FROM tr_SINAL AS S" +
                " WHERE  S.ID = "+ idSinal;
        Cursor results = db.rawQuery(queryBasics, null);
        results.moveToFirst();

        descricao = getS(results, "DESCRICAO");
        posX = getI(results, "POSX");
        posY = getI(results, "POSY");
        idConfigMao = getI(results, "ID_CONFIG_MAO");

        String queryMov =
                "SELECT " +
                "  MC.ANGULO as CIRC_ANGULO," +
                "  ML.ANGULO as LIN_ANGULO," +
                "  , *  " +
                "FROM tr_SINALxMOV AS SM" +
                "  LEFT JOIN tr_MOV_LINEAR  as ML" +
                "   on ML.ID = SM.ID_MOV_LINEAR" +
                "  LEFT JOIN tr_MOV_CIRCULAR as MC" +
                "   on MC.ID = SM.ID_MOV_CIRCULAR" +
                " WHERE  SM.ID_SINAL = " + idSinal;

        results = db.rawQuery(queryMov, null);

        for (results.moveToFirst(); !results.isAfterLast(); results.moveToNext()) {
            parseMov(results);
        }
    }

    private void parseMov(Cursor c){
        if(isNull(c,"ID_CONFIG_MAO")){
            movimentosEmOrdem.add(new tr_MovimentoConfigMao(getI(c,"ID_CONFIG_MAO")));
        }
        else if(isNull(c, "ID_MOV_LINEAR")){
            movimentosEmOrdem.add(new tr_MovimentoLinear(getI(c,"LIN_ANGULO"), getI(c, "TAMANHO")));
        }
        else if(isNull(c, "ID_MOV_CIRCULAR")){
            movimentosEmOrdem.add(new tr_MovimentoLinear(getI(c,"CIRC_ANGULO"), getI(c, "RAIO")));
        }

    }
    private boolean isNull(Cursor c, String key){
        return c.isNull(c.getColumnIndexOrThrow(key));
    }
    private int getI(Cursor c, String key ){
        return c.getInt(c.getColumnIndexOrThrow(key));
    }
    private String getS(Cursor c, String key){
        return c.getString(c.getColumnIndexOrThrow(key));
    }

    @Nullable
    private SQLiteDatabase connectDb(){
        SQLiteDatabase db;
        DbConnection connection = new DbConnection(new AppContext().getAppContext());
        try {
            connection.createDataBase();
        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        }
        try {
            db = connection.openDataBase();
            return db;
        } catch (SQLException sql) {
            throw sql;
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}

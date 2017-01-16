package com.marlin.tralp.Conexao.Dao;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.marlin.tralp.AppContext;
import com.marlin.tralp.Conexao.DbConnection;
import com.marlin.tralp.Transcriber.tr_Models.tr_MovimentoLinear;

import java.io.IOException;
import java.util.ArrayList;

/*
  Created by paulo on 10/11/2016.
 */

public class tr_MovimentsDAO {
    private SQLiteDatabase db;

    public tr_MovimentsDAO() {
        int duh = 1;
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

    public ArrayList<tr_MovimentoLinear> get_All_Moviment() {
        String sql = "SELECT * FROM tr_SINALxMOV ";
        Cursor busca = db.rawQuery(sql, null);
        ArrayList<tr_MovimentoLinear> moviments = new ArrayList<>();
        while (busca.moveToNext()){
//            tr_MovimentoLinear moviment = new tr_MovimentoLinear();
//           moviment.setId_SinalxMov(busca.getInt(busca.getColumnIndex("ID_SINALxMOV")));
//            moviment.setId_Sinal(busca.getInt(busca.getColumnIndex("ID_SINAL")));
//            moviment.setId_mov_config_mao(busca.getInt(busca.getColumnIndex("ID_MOV_CONFIGURACAO_MAO")));
//            moviment.setId_Mov_linear(busca.getInt(busca.getColumnIndex("ID_MOV_LINEAR")));
//            moviment.setId_Mov_Rotacional(busca.getInt(busca.getColumnIndex("ID_MOV_CIRCULAR")));
//            moviment.setCardinalidade(busca.getInt(busca.getColumnIndex("CARDINALIDADE")));
//            moviments.add(moviment);
        }
        busca.close();
        db.close();
        return moviments;
    }



}


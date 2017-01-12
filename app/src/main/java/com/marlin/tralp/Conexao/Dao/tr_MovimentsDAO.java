package com.marlin.tralp.Conexao.Dao;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.marlin.tralp.AppContext;
import com.marlin.tralp.Conexao.DbConnection;
import com.marlin.tralp.Transcriber.tr_Models.tr_Moviments;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/*
  Created by paulo on 10/11/2016.
 */

public class tr_MovimentsDAO {
    Context context;
    private SQLiteDatabase db;

    public tr_MovimentsDAO() {
        this.context = new AppContext().getAppContext();
        DbConnection connection = new DbConnection(this.context);
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

    public ArrayList<tr_Moviments> get_All_Moviment() {
        String sql = "SELECT * FROM tr_SINALxMOV ";
        Cursor busca = db.rawQuery(sql, null);
        ArrayList<tr_Moviments> moviments = new ArrayList<>();
        while (busca.moveToNext()){
            tr_Moviments moviment = new tr_Moviments();
            moviment.setId_SinalxMov(busca.getInt(busca.getColumnIndex("ID_SINALxMOV")));
            moviment.setId_Sinal(busca.getInt(busca.getColumnIndex("ID_SINAL")));
            moviment.setId_mov_config_mao(busca.getInt(busca.getColumnIndex("ID_MOV_CONFIGURACAO_MAO")));
            moviment.setId_Mov_linear(busca.getInt(busca.getColumnIndex("ID_MOV_LINEAR")));
            moviment.setId_Mov_Rotacional(busca.getInt(busca.getColumnIndex("ID_MOV_CIRCULAR")));
            moviment.setCardinalidade(busca.getInt(busca.getColumnIndex("CARDINALIDADE")));
            moviments.add(moviment);
        }
        busca.close();
        db.close();
        return moviments;
    }



}


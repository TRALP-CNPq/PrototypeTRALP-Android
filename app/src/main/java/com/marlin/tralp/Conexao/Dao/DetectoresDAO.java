package com.marlin.tralp.Conexao.Dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.marlin.tralp.Conexao.DbConnection;
import com.marlin.tralp.Transcriber.tr_Models.Detector;
import com.marlin.tralp.Transcriber.tr_Models.Detectores;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by paulo on 13/11/2016.
 */
public class DetectoresDAO {

    private SQLiteDatabase db;

    public DetectoresDAO(Context context) {
        DbConnection connection = new DbConnection(context);
        try {
            connection.createDataBase();
        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        }
        try {
            db = connection.openDataBase();
        } catch (android.database.SQLException sql) {
            throw sql;
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Detector> get_All_Moviment() {
        String sql = "SELECT * FROM tr_DETECTORES ";
        Cursor busca = db.rawQuery(sql, null);
        ArrayList<Detector> detectores = new ArrayList<>();
        while (busca.moveToNext()){
            int id = busca.getColumnIndex("ID");
            int id_configuracao_mao = busca.getColumnIndex("ID_CONGIGURACAO_MAO");
            int id_mov_rotacional = busca.getColumnIndex("ID_MOV_ROTACIONAL");
            String file_name = busca.getString(busca.getColumnIndex("FILE_NAME"));

            Detector detec;
            detec = new Detector(id, id_configuracao_mao, id_mov_rotacional, file_name);
            detectores.add(detec);
        }
        busca.close();
        db.close();
        return detectores;
    }
}



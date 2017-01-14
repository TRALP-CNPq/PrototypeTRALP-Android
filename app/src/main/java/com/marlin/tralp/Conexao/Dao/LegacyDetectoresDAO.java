package com.marlin.tralp.Conexao.Dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.marlin.tralp.Conexao.DbConnection;
//import com.marlin.tralp.Transcriber.tr_Models.DetectorFactory;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by paulo on 13/11/2016.
 */
public class LegacyDetectoresDAO {

    private SQLiteDatabase db;

    public LegacyDetectoresDAO(Context context) {
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

//    public ArrayList<DetectorFactory> get_All_Moviment() {
//        String sql = "SELECT * FROM tr_DETECTORES ";
//        Cursor busca = db.rawQuery(sql, null);
//        ArrayList<DetectorFactory> detectores = new ArrayList<>();
//        while (busca.moveToNext()){
//            int id = busca.getColumnIndex("ID");
//            int id_configuracao_mao = busca.getColumnIndex("ID_CONGIGURACAO_MAO");
//            int id_mov_rotacional = busca.getColumnIndex("ID_MOV_ROTACIONAL");
//            String file_name = busca.getString(busca.getColumnIndex("FILE_NAME"));
//
//            DetectorFactory detec;
//            detec = new DetectorFactory(id, id_configuracao_mao, id_mov_rotacional, file_name);
//            detectores.add(detec);
//        }
//        busca.close();
//        db.close();
//        return detectores;
//    }
}


//public class DetectorFactory {
//
//
//    private int ID;
//    private int ID_CONFIGURACAO_MAO;
//    private int ID_MOV_ROTACIONAL;
//    private String file_name;
//
//    public DetectorFactory(int ID, int ID_CONFIGURACAO_MAO, int ID_MOV_ROTACIONAL, String file_name) {
//        this.ID = ID;
//        this.ID_CONFIGURACAO_MAO = ID_CONFIGURACAO_MAO;
//        this.ID_MOV_ROTACIONAL = ID_MOV_ROTACIONAL;
//        this.file_name = file_name;
//
//    }
//
//    public int getiD() {
//        return ID;
//    }
//
//    public void setiD(int ID) {
//        this.ID = ID;
//    }
//
//    public int getiD_CONFIGURACAO_MAO() {
//        return ID_CONFIGURACAO_MAO;
//    }
//
//    public void setiD_CONFIGURACAO_MAO(int ID_CONFIGURACAO_MAO) {
//        this.ID_CONFIGURACAO_MAO = ID_CONFIGURACAO_MAO;
//    }
//
//    public int getiD_MOV_ROTACIONAL() {
//        return ID_MOV_ROTACIONAL;
//    }
//
//    public void setiD_MOV_ROTACIONAL(int ID_MOV_ROTACIONAL) {
//        this.ID_MOV_ROTACIONAL = ID_MOV_ROTACIONAL;
//    }
//
//    public String getFile_name() {
//        return file_name;
//    }
//
//    public void setFile_name(String file_name) {
//        this.file_name = file_name;
//    }
//}


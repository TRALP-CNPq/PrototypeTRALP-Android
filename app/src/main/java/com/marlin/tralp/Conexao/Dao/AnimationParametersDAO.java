package com.marlin.tralp.Conexao.Dao;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.marlin.tralp.Conexao.DbConnection;
import com.marlin.tralp.Transcriber.Models.AnimationParameters;
import com.marlin.tralp.Transcriber.Models.Movement;
import com.marlin.tralp.Transcriber.Models.MovementLinear;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by aneves on 6/1/2016.
 */
public class AnimationParametersDAO {
    private SQLiteDatabase db;

    public AnimationParametersDAO(Context context) {
        //cria uma nova conexao com o banco e coloca ele como editavel pela Aplicacao.
        DbConnection connection = new DbConnection(context);
        try {
            connection.createDataBase();
        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        }
        try {
            db = connection.openDataBase();
        } catch (SQLException sqle) {
            throw sqle;
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
    }

    public AnimationParameters ObterParametrosPorId(int codMov) {
        String[] colunas = new String[]{"*"};
        String[] selectionArgs = new String[]{Integer.toString(codMov)};
        Cursor busca = db.query("parametrosmovimento", colunas, "CODMOV = ? ", selectionArgs, null, null, null, null);
        AnimationParameters mov = new AnimationParameters();
        mov.setCodMov(codMov);
        mov.setRShoulder_V(busca.getFloat(0));
        mov.setRShoulder_H(busca.getFloat(1));
        mov.setRArm_V(busca.getFloat(2));
        mov.setRArm_H(busca.getFloat(3));
        mov.setRArm_R(busca.getFloat(4));
        mov.setRForearm_V(busca.getFloat(5));
        mov.setRForearm_H(busca.getFloat(6));
        mov.setRHand_V(busca.getFloat(7));
        mov.setRHand_H(busca.getFloat(8));
        mov.setRThumb(busca.getFloat(9));
        mov.setRIndex(busca.getFloat(10));
        mov.setRMiddle(busca.getFloat(11));
        mov.setRRing(busca.getFloat(12));
        mov.setRPinky(busca.getFloat(13));
        mov.setLShoulder_V(busca.getFloat(14));
        mov.setLShoulder_H(busca.getFloat(15));
        mov.setLArm_V(busca.getFloat(16));
        mov.setLArm_H(busca.getFloat(17));
        mov.setLArm_R(busca.getFloat(18));
        mov.setLForearm_V(busca.getFloat(19));
        mov.setLForearm_H(busca.getFloat(20));
        mov.setLforearm_R(busca.getFloat(21));
        mov.setLHand_V(busca.getFloat(22));
        mov.setLHand_H(busca.getFloat(23));
        mov.setLThumb(busca.getFloat(24));
        mov.setLIndex(busca.getFloat(25));
        mov.setLMiddle(busca.getFloat(26));
        mov.setLRing(busca.getFloat(27));
        mov.setLPinky(busca.getFloat(28));

        return mov;
    }

}

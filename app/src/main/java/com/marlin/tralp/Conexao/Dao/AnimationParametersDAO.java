package com.marlin.tralp.Conexao.Dao;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.marlin.tralp.Conexao.DbConnection;
import com.marlin.tralp.Transcriber.Models.AnimationParameters;
import com.marlin.tralp.Transcriber.Models.AnimationParametersDTO;
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
        //       Cursor busca = db.query("parametrosmovimento", colunas, "CODMOV = 20 ", null, null, null, null, null);
        Cursor busca = db.query("PARAMETROSMOVIMENTONOVO", colunas, "CODMOV = ? ", selectionArgs, null, null, null, null);
        busca.moveToFirst();
        AnimationParameters mov = new AnimationParameters();
        mov.setCodMov(codMov);
        mov.setRShoulder_V(busca.getFloat(1));
        mov.setRShoulder_H(busca.getFloat(2));
        mov.setRArm_V(busca.getFloat(3));
        mov.setRArm_H(busca.getFloat(4));
        mov.setRArm_R(busca.getFloat(5));
        mov.setRForearm_V(busca.getFloat(6));
        mov.setRForearm_H(busca.getFloat(7));
        mov.setRForearm_R(busca.getFloat(8));
        mov.setRHand_V(busca.getFloat(9));
        mov.setRHand_H(busca.getFloat(10));
        mov.setRThumb1V(busca.getFloat(11));
        mov.setRThumb1H(busca.getFloat(12));
        mov.setRThumb2V(busca.getFloat(13));
        mov.setRIndex1V(busca.getFloat(14));
        mov.setRIndex1H(busca.getFloat(15));
        mov.setRIndex2V(busca.getFloat(16));
        mov.setRMiddle1V(busca.getFloat(17));
        mov.setRMiddle1H(busca.getFloat(18));
        mov.setRMiddle2V(busca.getFloat(19));
        mov.setRRing1V(busca.getFloat(20));
        mov.setRRing1H(busca.getFloat(21));
        mov.setRRing2V(busca.getFloat(22));
        mov.setRPinky1V(busca.getFloat(23));
        mov.setRPinky1H(busca.getFloat(24));
        mov.setRPinky2V(busca.getFloat(25));
        mov.setLShoulder_V(busca.getFloat(26));
        mov.setLShoulder_H(busca.getFloat(27));
        mov.setLArm_V(busca.getFloat(28));
        mov.setLArm_H(busca.getFloat(29));
        mov.setLArm_R(busca.getFloat(30));
        mov.setLForearm_V(busca.getFloat(31));
        mov.setLForearm_H(busca.getFloat(32));
        mov.setLForearm_R(busca.getFloat(33));
        mov.setLHand_V(busca.getFloat(34));
        mov.setLHand_H(busca.getFloat(35));
        mov.setLThumb1V(busca.getFloat(36));
        mov.setLThumb1H(busca.getFloat(37));
        mov.setLThumb2V(busca.getFloat(38));
        mov.setLIndex1V(busca.getFloat(39));
        mov.setLIndex1H(busca.getFloat(40));
        mov.setLIndex2V(busca.getFloat(41));
        mov.setLMiddle1V(busca.getFloat(42));
        mov.setLMiddle1H(busca.getFloat(43));
        mov.setLMiddle2V(busca.getFloat(44));
        mov.setLRing1V(busca.getFloat(45));
        mov.setLRing1H(busca.getFloat(46));
        mov.setLRing2V(busca.getFloat(47));
        mov.setLPinky1V(busca.getFloat(48));
        mov.setLPinky1H(busca.getFloat(49));
        mov.setLPinky2V(busca.getFloat(50));
        busca.close();
        db.close();
        return mov;
    }
    public AnimationParametersDTO ObterParametrosPorIdDTO(int codMov) {
        String[] colunas = new String[]{"*"};
        String[] selectionArgs = new String[]{Integer.toString(codMov)};
//        Cursor busca = db.query("parametrosmovimento", colunas, "CODMOV = 20 ", null, null, null, null, null);
        Cursor busca = db.query("PARAMETROSMOVIMENTONOVO", colunas, "CODMOV = ? ", selectionArgs, null, null, null, null);
        busca.moveToFirst();
        AnimationParametersDTO mov = new AnimationParametersDTO();
        mov.codMov = codMov;
        mov.setRShoulder_V(busca.getFloat(1));
        mov.setRShoulder_H(busca.getFloat(2));
        mov.setRArm_V(busca.getFloat(3));
        mov.setRArm_H(busca.getFloat(4));
        mov.setRArm_R(busca.getFloat(5));
        mov.setRForearm_V(busca.getFloat(6));
        mov.setRForearm_H(busca.getFloat(7));
        mov.setRForearm_R(busca.getFloat(8));
        mov.setRHand_V(busca.getFloat(9));
        mov.setRHand_H(busca.getFloat(10));
        mov.setRThumb1V(busca.getFloat(11));
        mov.setRThumb1H(busca.getFloat(12));
        mov.setRThumb2V(busca.getFloat(13));
        mov.setRIndex1V(busca.getFloat(14));
        mov.setRIndex1H(busca.getFloat(15));
        mov.setRIndex2V(busca.getFloat(16));
        mov.setRMiddle1V(busca.getFloat(17));
        mov.setRMiddle1H(busca.getFloat(18));
        mov.setRMiddle2V(busca.getFloat(19));
        mov.setRRing1V(busca.getFloat(20));
        mov.setRRing1H(busca.getFloat(21));
        mov.setRRing2V(busca.getFloat(22));
        mov.setRPinky1V(busca.getFloat(23));
        mov.setRPinky1H(busca.getFloat(24));
        mov.setRPinky2V(busca.getFloat(25));
        mov.setLShoulder_V(busca.getFloat(26));
        mov.setLShoulder_H(busca.getFloat(27));
        mov.setLArm_V(busca.getFloat(28));
        mov.setLArm_H(busca.getFloat(29));
        mov.setLArm_R(busca.getFloat(30));
        mov.setLForearm_V(busca.getFloat(31));
        mov.setLForearm_H(busca.getFloat(32));
        mov.setLForearm_R(busca.getFloat(33));
        mov.setLHand_V(busca.getFloat(34));
        mov.setLHand_H(busca.getFloat(35));
        mov.setLThumb1V(busca.getFloat(36));
        mov.setLThumb1H(busca.getFloat(37));
        mov.setLThumb2V(busca.getFloat(38));
        mov.setLIndex1V(busca.getFloat(39));
        mov.setLIndex1H(busca.getFloat(40));
        mov.setLIndex2V(busca.getFloat(41));
        mov.setLMiddle1V(busca.getFloat(42));
        mov.setLMiddle1H(busca.getFloat(43));
        mov.setLMiddle2V(busca.getFloat(44));
        mov.setLRing1V(busca.getFloat(45));
        mov.setLRing1H(busca.getFloat(46));
        mov.setLRing2V(busca.getFloat(47));
        mov.setLPinky1V(busca.getFloat(48));
        mov.setLPinky1H(busca.getFloat(49));
        mov.setLPinky2V(busca.getFloat(50));
        busca.close();
        db.close();
        return mov;
    }
}

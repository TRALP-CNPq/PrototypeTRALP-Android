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
        Cursor busca = db.query("parametrosmovimento", colunas, "CODMOV = 20 ", null, null, null, null, null);
//        Cursor busca = db.query("parametrosmovimento", colunas, "CODMOV = ? ", selectionArgs, null, null, null, null);
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
        mov.setRHand_V(busca.getFloat(8));
        mov.setRHand_H(busca.getFloat(9));
        mov.setRThumb(busca.getFloat(10));
        mov.setRIndex(busca.getFloat(11));
        mov.setRMiddle(busca.getFloat(12));
        mov.setRRing(busca.getFloat(13));
        mov.setRPinky(busca.getFloat(14));
        mov.setLShoulder_V(busca.getFloat(15));
        mov.setLShoulder_H(busca.getFloat(16));
        mov.setLArm_V(busca.getFloat(17));
        mov.setLArm_H(busca.getFloat(18));
        mov.setLArm_R(busca.getFloat(19));
        mov.setLForearm_V(busca.getFloat(20));
        mov.setLForearm_H(busca.getFloat(21));
        mov.setLforearm_R(busca.getFloat(22));
        mov.setLHand_V(busca.getFloat(23));
        mov.setLHand_H(busca.getFloat(24));
        mov.setLThumb(busca.getFloat(25));
        mov.setLIndex(busca.getFloat(26));
        mov.setLMiddle(busca.getFloat(27));
        mov.setLRing(busca.getFloat(28));
        mov.setLPinky(busca.getFloat(29));
        busca.close();
        db.close();
        return mov;
    }
    public AnimationParametersDTO ObterParametrosPorIdDTO(int codMov) {
        String[] colunas = new String[]{"*"};
        String[] selectionArgs = new String[]{Integer.toString(codMov)};
//        Cursor busca = db.query("parametrosmovimento", colunas, "CODMOV = 20 ", null, null, null, null, null);
        Cursor busca = db.query("parametrosmovimento", colunas, "CODMOV = ? ", selectionArgs, null, null, null, null);
        busca.moveToFirst();
        AnimationParametersDTO mov = new AnimationParametersDTO();
        mov.codMov = codMov;
        mov.RShoulder_V = busca.getFloat(1);
        mov.RShoulder_H = busca.getFloat(2);
        mov.RArm_V = busca.getFloat(3);
        mov.RArm_H = busca.getFloat(4);
        mov.RArm_R = busca.getFloat(5);
        mov.RForearm_V = busca.getFloat(6);
        mov.RForearm_H = busca.getFloat(7);
        mov.RHand_V = busca.getFloat(8);
        mov.RHand_H = busca.getFloat(9);
        mov.RThumb = busca.getFloat(10);
        mov.RIndex = busca.getFloat(11);
        mov.RMiddle = busca.getFloat(12);
        mov.RRing = busca.getFloat(13);
        mov.RPinky = busca.getFloat(14);
        mov.LShoulder_V = busca.getFloat(15);
        mov.LShoulder_H = busca.getFloat(16);
        mov.LArm_V = busca.getFloat(17);
        mov.LArm_H = busca.getFloat(18);
        mov.LArm_R = busca.getFloat(19);
        mov.LForearm_V = busca.getFloat(20);
        mov.LForearm_H = busca.getFloat(21);
        mov.Lforearm_R = busca.getFloat(22);
        mov.LHand_V = busca.getFloat(23);
        mov.LHand_H = busca.getFloat(24);
        mov.LThumb = busca.getFloat(25);
        mov.LIndex = busca.getFloat(26);
        mov.LMiddle = busca.getFloat(27);
        mov.LRing = busca.getFloat(28);
        mov.LPinky = busca.getFloat(29);
        busca.close();
        db.close();
        return mov;
    }
}

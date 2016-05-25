package com.marlin.tralp.Conexao.Dao;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.marlin.tralp.Conexao.DbConnection;
import com.marlin.tralp.Transcriber.Models.OrientacaoQuadrante;

import java.io.IOException;

/**
 * Created by aneves on 5/20/2016.
 */
public class SignDAO {
    private SQLiteDatabase db;

    public SignDAO(Context context) {
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

    public void ObterConfigMaoPorLocalizaçãoDaMaoAproximada(int limteInfMao_x, int limteSupMao_x, int limteInfMao_y, int limteSupMao_y, int qual_mao) {
        String[] colunas = new String[]{"*"};
        String[] selectionArgs = new String[]{Integer.toString(limteInfMao_x), Integer.toString(limteSupMao_x), Integer.toString(limteInfMao_y), Integer.toString(limteSupMao_y)};
        Cursor busca = db.query("CONFIGURACAO_MAO", colunas, "CODCM1 >= ? AND CODCM1 <= ? AND CODCM2 >= ? AND CODCM2 <= ?", selectionArgs, null, null, null, null);
    }
    public OrientacaoQuadrante ObterOrientacaoQuadrante(int codSin) {
        String[] colunas = new String[]{"*"};
        String[] selectionArgs = new String[]{Integer.toString(codSin)};
        Cursor busca = db.query("ORIENTACAO_QUADRANTE", colunas, "CODSIN = ? ", selectionArgs, null, null, null, null);
        OrientacaoQuadrante orientacaoQuadrante = new OrientacaoQuadrante();
        orientacaoQuadrante.setOrientacaoX(busca.getInt(2));
        orientacaoQuadrante.setOrientacaoY(busca.getInt(3));
        return  orientacaoQuadrante;
    }
}

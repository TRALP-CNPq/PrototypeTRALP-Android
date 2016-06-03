package com.marlin.tralp.Conexao.Dao;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.marlin.tralp.Conexao.DbConnection;
import com.marlin.tralp.Model.Palavra;
import com.marlin.tralp.Transcriber.Models.Movement;
import com.marlin.tralp.Transcriber.Models.MovementLinear;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by aneves on 5/9/2016.
 */
public class MovimentoDAO {
    private SQLiteDatabase db;

    public MovimentoDAO(Context context) {
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

    public void ObterMovimentoPorLocalizaçãoDaMaoAproximada(int mao_x, int mao_y, int qual_mao) {
        String[] colunas = new String[]{"*"};
        String[] selectionArgs = new String[]{Integer.toString(mao_x), Integer.toString(mao_y)};
        Cursor busca = db.query("MOVIMENTO", colunas, "PARAM1 = ? AND PARAM2 = ?", selectionArgs, null, null, null, null);
    }

    public ArrayList<Movement> ObterTodosMovimentosDeUmSinal(int codSin) {
        ArrayList<Movement> movements = new ArrayList<Movement>();
        String[] colunas = new String[]{"*"};
        String[] selectionArgs = new String[]{Integer.toString(codSin)};
        Cursor busca = db.query("MOVIMENTO", colunas, "CODSIN = ? ", selectionArgs, null, null, null, null);
        if (busca.getCount() > 0) {
            busca.moveToFirst();
            do {
                Movement movement = new Movement();
                movement.setCodMov(busca.getInt(0));
                movement.setCodSign(busca.getInt(1));
                movement.setCodMao(busca.getInt(2));
                movement.setCard(busca.getInt(4));
//                movement.setTempo(busca.getColumnName(5));
                movement.setTipo(busca.getInt(6));
                movements.add(movement);
            } while (busca.moveToNext());
        }
        return movements;
    }

    public MovementLinear ObterMovimentoLinearPorId(int codMov) {
        String[] colunas = new String[]{"*"};
        String[] selectionArgs = new String[]{Integer.toString(codMov)};
        Cursor busca = db.query("MOVLINEAR", colunas, "CODMOV = ? ", selectionArgs, null, null, null, null);
        MovementLinear mov = new MovementLinear();
        mov.setCodMov(busca.getInt(0));
        mov.setAngulo1(busca.getInt(1));
        mov.setAngulo2(busca.getInt(2));
        mov.setTamanho(busca.getFloat(3));

        return mov;
    }
}
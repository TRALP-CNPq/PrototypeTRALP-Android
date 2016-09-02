package com.marlin.tralp.Conexao.Dao;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import com.marlin.tralp.Conexao.DbConnection;
import com.marlin.tralp.Model.Palavra;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by psalum on 05/05/2015.
 */
public class PalavraDAO {
    private SQLiteDatabase db;

    public PalavraDAO(Context context) {
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

    public void BuscarDefinicao(List<Palavra> palavras, String coluna) {

        String[] colunas = new String[]{"*"};
        for (Palavra palavraAtual : palavras) {
            Cursor busca = db.query("dicionario", colunas, coluna + " = ?", new String[]{palavraAtual.getToken()}, null, null, null, null);
            if (busca.getCount() > 0) {
                busca.moveToFirst();

                palavraAtual.setTags(busca.getString(1).split(" "));
                palavraAtual.setFeats(busca.getString(2).split(" "));
                palavraAtual.setLemmas(busca.getString(3).split(" "));
                palavraAtual.setToken(busca.getString(4));
                palavraAtual.setLibras(busca.getString(5));
            }
        }
     //   busca.close();
     //   db.close();
    }

    public List<Palavra> buscarVerbos(String verboinfinitivo, String tempo) {
        String[] colunas = new String[]{"*"};
        List<Palavra> palavras = new ArrayList<Palavra>();
        String[] Argumentos = {verboinfinitivo, verboinfinitivo+" %","% "+verboinfinitivo ,"%"+tempo+"%" };

        Cursor busca = db.query("dicionario", colunas, "(lemmas = ? OR lemmas like ? OR lemmas like ?) and (feats like ?)", Argumentos, null, null, null);

        if (busca.getCount() > 0) {
            busca.moveToFirst();
            do {
                Palavra palavraAtual = new Palavra();
                palavraAtual.setTags(busca.getString(0).split(" "));
                palavraAtual.setFeats(busca.getString(1).split(" "));
                palavraAtual.setLemmas(busca.getString(2).split(" "));
                palavraAtual.setToken(busca.getString(3));
                palavraAtual.setLibras(busca.getString(4));
                palavras.add(palavraAtual);
            } while (busca.moveToNext());
        }
        busca.close();
        db.close();
        return palavras;
    }

    public String BuscarPalavraPorId(int id) {

        String[] colunas = new String[]{"TOKEN"};
        Log.d("PalavraDAO: ", "CODPAL = Id " + Integer.toString(id));
    //    Cursor busca = db.query("dicionario", colunas, "CODPAL = ?", new String[]{Integer.toString(id)}, null, null, null, null);
        Cursor busca = db.query("dicionario", colunas, "CODPAL = ? ", new String[]{Integer.toString(id)}, null, null, null, null);
        busca.moveToFirst();
        String palavra = busca.getString(0);
        Log.d("PalavraDAO: ", "palavra = " + palavra);
        busca.close();
        db.close();
        return palavra;
    }
}

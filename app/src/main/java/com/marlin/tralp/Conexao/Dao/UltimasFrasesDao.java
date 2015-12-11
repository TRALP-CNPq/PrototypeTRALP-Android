package com.marlin.tralp.Conexao.Dao;

import android.content.ContentValues;
import android.content.Context;

import com.marlin.tralp.Conexao.DataSource;
import com.marlin.tralp.DataModel.DataModel;
import com.marlin.tralp.Model.Frase;

import java.util.List;


/**
 * Created by psalum on 30/09/2015.
 */
public class UltimasFrasesDao {
    DataSource ds;
    ContentValues values;

    public UltimasFrasesDao(Context context){
        ds = new DataSource(context);
    }
    public boolean adicionar(Frase frase){
        boolean retorno = false;
        values = new ContentValues();
        values.put(DataModel.getFRASE(), frase.getFrase());
        try{
            ds.persist(values, DataModel.getTabelaUltimasfrases());
            return true;
        }catch (Exception e){
            System.out.println(e);
        }
        return retorno;
    }

    public List<Frase> buscarUltimasFrases(){
        return ds.findallFrases();
    }

    public void DeletarFrase(String frase){
        ds.Deletar(frase);



    }
}

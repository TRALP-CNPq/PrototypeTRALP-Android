package com.marlin.tralp.Conexao;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.marlin.tralp.DataModel.DataModel;
import com.marlin.tralp.Model.Frase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by psalum on 30/09/2015.
 */
public class DataSource extends SQLiteOpenHelper{
    SQLiteDatabase db;
    public DataSource(Context context) {
        super(context, DataModel.getDbName(), null, 1);
        db = this.getReadableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DataModel.criarTabelaUltimasFrases());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }
    public void persist(ContentValues values, String tabela){
        if(values.containsKey("id") && values.getAsInteger("id")!= null && values.getAsInteger("id")!=0){
            Integer id = values.getAsInteger("id");
            db.update(tabela,values,"id = "+id, null);
        }else{
            //if(!VerificarSejaExiste(values.getAsString("frase"))){
                db.insert(tabela, null, values);
            //}

        }
    }

    private boolean VerificarSejaExiste(String frase) {
        List<Frase> frases = findallFrases();
        for(Frase f:frases){
            if(f.getFrase().equalsIgnoreCase(frase));
            return true;

        }
        return false;
    }

    public List<Frase> findallFrases(){
        List<Frase> frases = new ArrayList<>();
        String[] colunas = new String[]{"*"};
        Cursor busca = db.query(DataModel.getTabelaUltimasfrases(),colunas,null,null,null,null,"ID DESC","5");
        if(busca.moveToFirst()) {
            while (!busca.isAfterLast()) {
                frases.add(new Frase(busca.getInt(0),busca.getString(1)));
                busca.moveToNext();
            }
        }
        return frases;
    }

    public void Deletar(String s){
        List<Frase> frases = findallFrases();
        String[]frase = s.trim().split("-");
        db.delete(DataModel.getTabelaUltimasfrases(),"id =?",new String[]{frase[0]});

    }

}

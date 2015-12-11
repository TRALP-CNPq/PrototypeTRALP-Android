package com.marlin.tralp.DataModel;

/**
 * Created by psalum on 30/09/2015.
 */
public class DataModel {

    private static final String DB_NAME = "db.tralp.sqlite";
    private static final String TABELA_ULTIMASFRASES = "ultimasFrases";
    private static final String ID = "id";
    private static final String FRASE = "Frase";
    private static final String TIPO_TEXTO = "TEXT";
    private static final String TIPO_INTEIRO = "INTEGER";
    private static final String TIPO_INTEIRO_PK = "INTEGER PRIMARY KEY";



    public static String criarTabelaUltimasFrases(){
        String query = "CREATE TABLE " + TABELA_ULTIMASFRASES;
        query+=" (";
        query+=ID + " " +TIPO_INTEIRO_PK + ", ";
        query+=FRASE + " " +TIPO_TEXTO + " ";
        query+=" )";
        return query;
    }

    public static String getDbName() {
        return DB_NAME;
    }

    public static String getTabelaUltimasfrases() {
        return TABELA_ULTIMASFRASES;
    }

    public static String getID() {
        return ID;
    }

    public static String getFRASE() {
        return FRASE;
    }

    public static String getTipoTexto() {
        return TIPO_TEXTO;
    }

    public static String getTipoInteiro() {
        return TIPO_INTEIRO;
    }

    public static String getTipoInteiroPk() {
        return TIPO_INTEIRO_PK;
    }
}

package com.marlin.tralp.Conexao.Dao;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.marlin.tralp.Conexao.DbConnection;
import com.marlin.tralp.Conexao.Repository.PropertyConfig;
import com.marlin.tralp.Transcriber.Models.AnimationParameters;
import com.marlin.tralp.Transcriber.Models.Movement;
import com.marlin.tralp.Transcriber.Models.OrientacaoQuadrante;
import com.marlin.tralp.Transcriber.Models.SignAnimationDTO;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

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

    public void ObterConfigMaoPorLocalizacaoDaMaoAproximada(int limteInfMao_x, int limteSupMao_x, int limteInfMao_y, int limteSupMao_y, int qual_mao) {
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

    public SignAnimationDTO ObterSinalParaAnimacao(String palavra) throws NoSuchFieldException, ParseException, IllegalAccessException, InstantiationException {
        String[] colunas = new String[]{"*"};
        String[] selectionArgs = new String[]{palavra};
     //   Cursor busca = db.query("dicionario", colunas, "TOKEN = ? ", selectionArgs, null, null, null, null);
     //   int codPal = busca.getInt(0);
//        String sql = "SELECT d.CODPAL as CODPAL, d.TOKEN as TOKEN, ps.CODSIN as CODSIN, sc.CODMAO as CODMAO, " +
//                "sc.CODCM as CODCM, sc.CODPONTI as CODPONTI, sc.CODPART as CODPART, sc.CODOP as CODOP, sc.CODCM1 as CODCM1, " +
//                "sc.CODCM2 as CODCM2, cm.MEIO as MEIO, cm.ANELAR_E_MINDINHO as ANELAR_E_MINDINHO, cm.DEDOS_SEPARADOS as DEDOS_SEPARADOS, " +
//                "cm.PONTAS_DO_DEDO_TOCANDO as PONTAS_DO_DEDO_TOCANDO, cm.POLEGAR as POLEGAR, cm.INDICADOR as INDICADOR " +
//                "FROM DICIONARIO d " +
//                "LEFT JOIN PALAVRAXSINAL ps ON (ps.CODPAL = d.CODPAL) " +
//                "LEFT JOIN SINAL s ON (s.CODSIN = ps.CODSIN) " +
//                "LEFT JOIN SINALXCARACTERISTICA sc ON (sc.CODSIN = ps.CODSIN) " +
//                "LEFT JOIN CONFIGURACAO_MAO cm ON (cm.CODCM = sc.CODCM) " +
//                "LEFT JOIN ORIENTACAO_PALMA op ON (op.CODOP = sc.CODOP) " +
//                "LEFT JOIN PONTO_ARTICULACAO pa ON (pa.CODPART = sc.CODPART) " +
//                "WHERE d.TOKEN = 'jovial'"; //  +
        //        "INNER JOIN PONTO_ARTICULACAO pa ON (pa.CODPART = sc.CODPART)";//'jovial'
        String sql = "SELECT * FROM DICIONARIO WHERE CODPAL = 9";
        Cursor busca = db.rawQuery(sql, selectionArgs);
        int codPal = busca.getInt(0);
     //   SignAnimationDTO sa = new SignAnimationDTO();
        ArrayList<PropertyConfig> signsPropDTO = new ArrayList<PropertyConfig>();
        CreateListPropertiesSignDTO(signsPropDTO);
        ArrayList<SignAnimationDTO> signs = new ArrayList<SignAnimationDTO>();
        signs = signsPropDTO.get(0).createAndMapObjectCollection(signsPropDTO, busca);
        SignAnimationDTO si = signs.get(0);
        ArrayList<Movement> movements = new ArrayList<Movement>();
        movements = new Movement().GetAllMovements(busca.getInt(2));
        si.setMovements(movements);
        ArrayList<AnimationParameters> animationParameters = new ArrayList<AnimationParameters>();
        animationParameters = new AnimationParameters().GetAllAnimationParametersSign(movements);
        si.setAnimationParameters(animationParameters);

        return  si;
    }

    private void CreateListPropertiesSignDTO(ArrayList<PropertyConfig> signsPropDTO) throws NoSuchFieldException {
    //    ArrayList<PropertyConfig> signsPropDTO = new ArrayList<PropertyConfig>();
        PropertyConfig<SignAnimationDTO> signProp = new PropertyConfig<SignAnimationDTO>("codPal","codPal");
        signsPropDTO.add(signProp);
//        signProp = new PropertyConfig<SignAnimationDTO>("TOKEN","TOKEN");
        signProp = new PropertyConfig<SignAnimationDTO>("TOKEN","TOKEN");
        signProp = new PropertyConfig<SignAnimationDTO>("CODSIN","CODSIN");
        signProp = new PropertyConfig<SignAnimationDTO>("CODMAO","TOKEN");
        signProp = new PropertyConfig<SignAnimationDTO>("CODCM","TOKEN");
        signProp = new PropertyConfig<SignAnimationDTO>("CODPONTI","TOKEN");
        signProp = new PropertyConfig<SignAnimationDTO>("CODPART","TOKEN");
        signProp = new PropertyConfig<SignAnimationDTO>("CODOP","TOKEN");
        signProp = new PropertyConfig<SignAnimationDTO>("CODCM1","TOKEN");
        signProp = new PropertyConfig<SignAnimationDTO>("CODCM2","TOKEN");
        signProp = new PropertyConfig<SignAnimationDTO>("ANELAR_E_MINDINHO","TOKEN");
        signProp = new PropertyConfig<SignAnimationDTO>("DEDOS_SEPARADOS","TOKEN");
        signProp = new PropertyConfig<SignAnimationDTO>("PONTAS_DO_DEDO_TOCANDO","TOKEN");
        signProp = new PropertyConfig<SignAnimationDTO>("POLEGAR","TOKEN");
        signProp = new PropertyConfig<SignAnimationDTO>("INDICADOR","TOKEN");

    }
//        String[] colunas = new String[]{"CODPAL"};
//        String[] selectionArgs = new String[]{palavra};
//        Cursor busca = db.query("dicionario", colunas, "TOKEN = ? ", selectionArgs, null, null, null, null);
//        int codPal = busca.getInt(0);
//
//        colunas = new String[]{"CODSIN"};
//        selectionArgs = new String[]{Integer.toString(codPal)};
//        busca = db.query("PALAVRAXSINAL", colunas, "CODPAL = ? ", selectionArgs, null, null, null, null);
//        int codSin = busca.getInt(0);
//
//        colunas = new String[]{"*"};
//        selectionArgs = new String[]{Integer.toString(codSin)};
//        busca = db.query("SINALXCARACTERISTICA", colunas, "CODPAL = ? ", selectionArgs, null, null, null, null);
//        int codSin = busca.getInt(0);
}

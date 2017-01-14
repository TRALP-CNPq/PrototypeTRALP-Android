package com.marlin.tralp.Transcriber.tr_Models;

import com.marlin.tralp.Conexao.Dao.tr_MovimentsDAO;

import java.util.ArrayList;

/**
 * Created by paulo on 10/11/2016.
 */

public class tr_MovimentoLinear implements tr_Movimento {
    public int angulo;
    public String tamanho;

    public tr_MovimentoLinear() {
        //@// TODO: 2017-01-14 Parsear linha de consulta ao banco
    }

    @Override
    public String toString() {
        return "[MOVIMENTO LINEAR] angulo:" + angulo + ", tamanho: " + tamanho +" ]\n";
    }

    @Override
    public boolean aceita(int idConfigMao, String orientacaoPalma, int posX, int posY) {
        return false;
    }
}

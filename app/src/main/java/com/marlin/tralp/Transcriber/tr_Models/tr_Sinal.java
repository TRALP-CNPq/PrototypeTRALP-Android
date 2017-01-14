package com.marlin.tralp.Transcriber.tr_Models;

import java.util.List;

/**
 * Created by gabriel on 2017-01-14.
 */

public class tr_Sinal {
    public List<tr_Movimento> movimentosEmOrdem;
    int index_mov_concluido;

    public enum estado{
        falhou,
        concluiu,
        andamento
    }

    public tr_Sinal(int idSinal){
        //@// TODO: 2017-01-14 Fazer busca no banco
    }

    public estado aceita(int idConfigMao, String orientacaoPalma, int posX, int posY){
        //@// TODO: 2017-01-14  
        return estado.falhou;
    }
}

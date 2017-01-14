package com.marlin.tralp.Transcriber.tr_Models;

/**
 * Created by gabriel on 2017-01-14.
 */

public class tr_MovimentoCircular implements tr_Movimento{
    public int angulo;
    public String raio;

    public tr_MovimentoCircular() {
        //@// TODO: 2017-01-14 Parsear linha de consulta ao banco
    }

    @Override
    public String toString() {
        return "[MOVIMENTO Circular] angulo:" + angulo + ", raio: " + raio +" ]\n";
    }

    @Override
    public boolean aceita(int idConfigMao, String orientacaoPalma, int posX, int posY) {
        return false;
    }
}

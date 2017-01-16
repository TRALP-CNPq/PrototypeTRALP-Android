package com.marlin.tralp.Transcriber.tr_Models;

/**
 * Created by gabriel on 2017-01-14.
 */

public class tr_MovimentoRotacional implements tr_Movimento {
    public int idOrientacaoPalma;
    public String orientacaoPalma;

    public tr_MovimentoRotacional() {
        //@// TODO: 2017-01-14 Parsear linha de consulta ao banco
    }

    @Override
    public String toString() {
        return "[MOVIMENTO Rotacional] idOrientacaoPalma:" + idOrientacaoPalma + ", orientacaoPalma: " + orientacaoPalma +" ]\n";
    }


    @Override
    public boolean aceita(tr_FeatureStructure fs) {
        return false;
    }
}

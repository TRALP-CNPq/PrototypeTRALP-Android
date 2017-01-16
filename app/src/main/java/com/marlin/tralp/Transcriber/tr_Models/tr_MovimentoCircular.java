package com.marlin.tralp.Transcriber.tr_Models;

/**
 * Created by gabriel on 2017-01-14.
 */

public class tr_MovimentoCircular implements tr_Movimento{
    public int angulo;
    public int raio;

    public tr_MovimentoCircular(int angulo, int raio) {
        this.angulo = angulo;
        this.raio = raio;
    }

    @Override
    public String toString() {
        return "[MOVIMENTO Circular] angulo:" + angulo + ", raio: " + raio +" ]\n";
    }

    @Override
    public boolean aceita(tr_FeatureStructure fs) {
        return false;
    }
}

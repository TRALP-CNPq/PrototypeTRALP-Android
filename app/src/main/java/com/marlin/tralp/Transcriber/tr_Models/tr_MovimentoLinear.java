package com.marlin.tralp.Transcriber.tr_Models;

/**
 * Created by paulo on 10/11/2016.
 */

public class tr_MovimentoLinear implements tr_Movimento {
    public int angulo;
    public int tamanho;

    public tr_MovimentoLinear(int angulo, int tamanho) {
        this.angulo = angulo;
        this.tamanho = tamanho;
    }

    @Override
    public String toString() {
        return "[MOVIMENTO LINEAR] angulo:" + angulo + ", tamanho: " + tamanho +" ]\n";
    }

    @Override
    public boolean aceita(tr_FeatureStructure fs) {
        return false;
    }
}

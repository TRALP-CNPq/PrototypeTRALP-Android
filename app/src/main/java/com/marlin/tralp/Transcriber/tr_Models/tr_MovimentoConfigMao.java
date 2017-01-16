package com.marlin.tralp.Transcriber.tr_Models;

/**
 * Created by gabriel on 2017-01-14.
 */

public class tr_MovimentoConfigMao  implements tr_Movimento{
    public int idConfigMao;

    public tr_MovimentoConfigMao(int id) {
        idConfigMao = id;
    }

    @Override
    public String toString() {
        return "[MOVIMENTO ConfigMao] idConfigMao:" + idConfigMao + " ]\n";
    }

    @Override
    public boolean aceita(tr_FeatureStructure fs) {
        return this.idConfigMao == fs.idConfigMao;
    }
}

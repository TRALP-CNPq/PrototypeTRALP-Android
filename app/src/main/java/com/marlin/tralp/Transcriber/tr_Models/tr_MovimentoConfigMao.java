package com.marlin.tralp.Transcriber.tr_Models;

/**
 * Created by gabriel on 2017-01-14.
 */

public class tr_MovimentoConfigMao  implements tr_Movimento{
    public int idConfigMao;
    public String descricao;

    public tr_MovimentoConfigMao() {
        //@// TODO: 2017-01-14 Parsear linha de consulta ao banco
    }

    @Override
    public String toString() {
        return "[MOVIMENTO ConfigMao] idConfigMao:" + idConfigMao + ", descricao: " + descricao +" ]\n";
    }

    @Override
    public boolean aceita(int idConfigMao, String orientacaoPalma, int posX, int posY) {
        return this.idConfigMao == idConfigMao;
    }
}

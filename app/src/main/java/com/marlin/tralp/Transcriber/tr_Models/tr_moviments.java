package com.marlin.tralp.Transcriber.tr_Models;

import com.marlin.tralp.Conexao.Dao.tr_MovimentsDAO;

import java.util.ArrayList;

/**
 * Created by paulo on 10/11/2016.
 */

public class tr_Moviments {
    public tr_Moviments() {
    }

    @Override
    public String toString() {
        return "[id_SinalxMov=" + id_SinalxMov + ", id_Sinal=" + id_Sinal + ", id_mov_config_mao=" + id_mov_config_mao
                + ", id_Mov_linear=" + id_Mov_linear + ", id_Mov_Rotacional=" + id_Mov_Rotacional + ", cardinalidade="
                + cardinalidade + "]\n";
    }

    private int id_SinalxMov;
    private int id_Sinal;
    private int id_mov_config_mao;
    private int id_Mov_linear;
    private int id_Mov_Rotacional;
    private int cardinalidade;

    public tr_Moviments(int id_SinalxMov, int id_Sinal, int id_mov_config_mao, int id_Mov_linear, int id_Mov_Rotacional,
                        int cardinalidade) {
        this.id_SinalxMov = id_SinalxMov;
        this.id_Sinal = id_Sinal;
        this.id_mov_config_mao = id_mov_config_mao;
        this.id_Mov_linear = id_Mov_linear;
        this.setId_Mov_Rotacional(id_Mov_Rotacional);
        this.cardinalidade = cardinalidade;

    }

    public ArrayList<tr_Moviments> getAllMovements(){
        ArrayList<tr_Moviments> moviments;
        tr_MovimentsDAO dao = new tr_MovimentsDAO();
        moviments = dao.get_All_Moviment();
        return moviments;

    }



    public int getId_SinalxMov() {
        return id_SinalxMov;
    }

    public void setId_SinalxMov(int id_SinalxMov) {
        this.id_SinalxMov = id_SinalxMov;
    }

    public int getId_Sinal() {
        return id_Sinal;
    }

    public void setId_Sinal(int id_Sinal) {
        this.id_Sinal = id_Sinal;
    }

    public int getId_mov_config_mao() {
        return id_mov_config_mao;
    }

    public void setId_mov_config_mao(int id_mov_config_mao) {
        this.id_mov_config_mao = id_mov_config_mao;
    }

    public int getId_Mov_linear() {
        return id_Mov_linear;
    }

    public void setId_Mov_linear(int id_Mov_linear) {
        this.id_Mov_linear = id_Mov_linear;
    }

    public int getCardinalidade() {
        return cardinalidade;
    }

    public void setCardinalidade(int cardinalidade) {
        this.cardinalidade = cardinalidade;
    }

    public int getId_Mov_Rotacional() {
        return id_Mov_Rotacional;
    }

    public void setId_Mov_Rotacional(int id_Mov_Rotacional) {
        this.id_Mov_Rotacional = id_Mov_Rotacional;
    }
}

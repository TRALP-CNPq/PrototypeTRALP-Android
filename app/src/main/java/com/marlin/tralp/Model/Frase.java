package com.marlin.tralp.Model;

/**
 * Created by psalum on 30/09/2015.
 */
public class Frase {
    private int id;
    private String frase;
    private int posicaoInicio;
    private int posicaoFinal;


    public Frase(String frase){
        this.frase = frase;
    }
    public Frase(int id, String frase){
        this.id = id;
        this.frase = frase;
    }
    public Frase(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFrase() {
        return frase;
    }

    public void setFrase(String frase) {
        this.frase = frase;
    }

    public int getPosicaoInicio() {
        return posicaoInicio;
    }

    public void setPosicaoInicio(int posicaoInicio) {
        this.posicaoInicio = posicaoInicio;
    }

    public int getPosicaoFinal() {
        return posicaoFinal;
    }

    public void setPosicaoFinal(int posicaoFinal) {
        this.posicaoFinal = posicaoFinal;
    }
}

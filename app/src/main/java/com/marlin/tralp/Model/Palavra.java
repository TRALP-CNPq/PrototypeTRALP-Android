package com.marlin.tralp.Model;

/**
 * Created by psalum on 29/07/2015.
 */
public class Palavra {
    private String token;
    private String libras;
    private String[] tags;
    private String[] feats;
    private String[] lemmas;

    private String tempo;


    private int posicaoInicial;
    private int posicaoFinal;

    private boolean verbo;
    private boolean sujeito;
    private boolean objeto;
    private boolean interrogativo;

    public Palavra() {
        setVerbo(false);
        setSujeito(false);
        setObjeto(false);
    }


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getLibras() {
        return libras;
    }

    public void setLibras(String libras) {
        this.libras = libras;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public String[] getFeats() {
        return feats;
    }

    public void setFeats(String[] feats) {
        this.feats = feats;
    }

    public String[] getLemmas() {
        return lemmas;
    }

    public void setLemmas(String[] lemmas) {
        this.lemmas = lemmas;
    }

    public int getPosicaoInicial() {
        return posicaoInicial;
    }

    public void setPosicaoInicial(int posicaoInicial) {
        this.posicaoInicial = posicaoInicial;
    }

    public int getPosicaoFinal() {
        return posicaoFinal;
    }

    public void setPosicaoFinal(int posicaoFinal) {
        this.posicaoFinal = posicaoFinal;
    }

    public boolean isVerbo() {
        return verbo;
    }

    public void setVerbo(boolean verbo) {
        this.verbo = verbo;
    }

    public boolean isSujeito() {
        return sujeito;
    }

    public void setSujeito(boolean sujeito) {
        this.sujeito = sujeito;
    }

    public boolean isObjeto() {
        return objeto;
    }

    public void setObjeto(boolean objeto) {
        this.objeto = objeto;
    }

    public boolean isInterrogativo() {
        return interrogativo;
    }

    public void setInterrogativo(boolean interrogativo) {
        this.interrogativo = interrogativo;
    }

    public String getTempo() {
        return tempo;
    }

    public void setTempo(String tempo) {
        this.tempo = tempo;
    }
}

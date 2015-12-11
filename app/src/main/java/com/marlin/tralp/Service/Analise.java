package com.marlin.tralp.Service;

import android.content.Context;


import com.marlin.tralp.Conexao.Dao.PalavraDAO;
import com.marlin.tralp.Constantes.Constantes;
import com.marlin.tralp.Model.Palavra;

import java.util.HashMap;
import java.util.List;


/**
 * Created by psalum on 30/07/2015.
 */
public class Analise {
    private PalavraDAO dao;
    private int posicaoDoVerboPrincipal = 0;

    //inicializa o dao
    public Analise(Context context) {
        //Inicializa��o do DAO
        dao = new PalavraDAO(context);
    }

    //realiza a chamada para as buscas de definições das palavras
    public void Morfologica(List<Palavra> palavras, String coluna) {

        dao.BuscarDefinicao(palavras, coluna);
        if ("token".equalsIgnoreCase(coluna)) {
            RemoverArtigosePreposicoes(palavras);
            FazerDatilologia(palavras);
        } else {
            DesfazerDatilologia(palavras);
        }
//        IdentificarTempo(palavras);
    }

    //Realiza a classificação entre sujeito, verbo e objeto. também remove artigos e preposições.
    public void SintaticaLibrasPt(List<Palavra> palavras) {

        IdentificarVerbos(palavras);
        IdentificarInterrogativos(palavras);
        IdentificarSujeito(palavras);
        IdentificarObjetoLibrasPt(palavras);


    }


    //Realiza a classificação entre sujeito, verbo e objeto. também remove artigos e preposições.
    public void SintaticaPtLibras(List<Palavra> palavras) {
        //Identificar o Verbos.

        IdentificarVerbos(palavras);
        IdentificarInterrogativos(palavras);
        IdentificarSujeito(palavras);
        identificarObjetoPtLibras(palavras);
    }



    public void IdentificarVerbos(List<Palavra> palavras) {
        HashMap<String, String> verbos = new HashMap<String, String>();
        verbos.put("finitivo", Constantes.tag_verbo_finitivo);
        verbos.put("gerundio", Constantes.tag_verbo_gerundio);
        verbos.put("infinitivo", Constantes.tag_verbo_infinitivo);
        verbos.put("participio", Constantes.tag_verbo_participio);
        boolean existeVerbo = false;

        for (Palavra palabraAtual : palavras) {
            //Procuro pelo verbo
            if (!existeVerbo) {
                for (String tag : palabraAtual.getTags()) {
                    if (tag != null) {
                        if (verbos.containsValue(tag)) {
                            palabraAtual.setVerbo(true);
                            posicaoDoVerboPrincipal = palabraAtual.getPosicaoInicial();
                            existeVerbo = true;
                            break;
                        }
                    }
                }
            }

        }
    }

    public void IdentificarInterrogativos(List<Palavra> palavras) {
        HashMap<String, String> interrogativos = new HashMap<String, String>();
        interrogativos.put("quem", "quem");
        interrogativos.put("como", "como");
        interrogativos.put("porque", "porque");
        interrogativos.put("por-que", "por-que");
        interrogativos.put("para que", "para que");
        interrogativos.put("para-que", "para-que");

        for (Palavra palavraAtual : palavras) {
            if (interrogativos.containsValue(palavraAtual.getToken())) {
                palavraAtual.setInterrogativo(true);
            }
        }
    }

    public void IdentificarSujeito(List<Palavra> palavras) {
        palavras.get(0).setSujeito(true);
    }

    public void identificarObjetoPtLibras(List<Palavra> palavras) {
        for (Palavra t : palavras) {
            if (t.getPosicaoInicial() > posicaoDoVerboPrincipal && !t.isInterrogativo()) {
                t.setObjeto(true);
            }
        }
    }

    public void IdentificarObjetoLibrasPt(List<Palavra> palavras) {

        for (Palavra palavraAtual : palavras) {
            if (palavraAtual.getPosicaoInicial() < posicaoDoVerboPrincipal && !palavraAtual.isInterrogativo() && !palavraAtual.isSujeito()) {
                palavraAtual.setObjeto(true);
            }
        }
    }

    public void RemoverArtigosePreposicoes(List<Palavra> palavras) {
        HashMap<String, String> artigoePreposicao = new HashMap<String, String>();
        artigoePreposicao.put("artigo3", "ao");
        artigoePreposicao.put("artigo4", "aos");
        artigoePreposicao.put("artigo5", "do");
        artigoePreposicao.put("artigo6", "da");
        artigoePreposicao.put("artigo7", "dos");
        artigoePreposicao.put("artigo8", "das");
        artigoePreposicao.put("artigo9", "dum");
        artigoePreposicao.put("artigo10", "duns");
        artigoePreposicao.put("artigo11", "duma");
        artigoePreposicao.put("artigo12", "dumas");
        artigoePreposicao.put("artigo13", "nele");
        artigoePreposicao.put("artigo14", "neles");
        artigoePreposicao.put("artigo15", "nela");
        artigoePreposicao.put("artigo16", "nelas");
        artigoePreposicao.put("artigo17", "naquele");
        artigoePreposicao.put("artigo18", "naquela");
        artigoePreposicao.put("artigo19", "naqueles");
        artigoePreposicao.put("artigo20", "naquelas");
        artigoePreposicao.put("artigo21", "numa");
        artigoePreposicao.put("artigo22", "numas");
        artigoePreposicao.put("artigo23", "disto");
        artigoePreposicao.put("artigo24", "disso");
        artigoePreposicao.put("artigo25", "daquilo");
        artigoePreposicao.put("artigo26", "num");
        artigoePreposicao.put("artigo27", "nuns");
        artigoePreposicao.put("artigo28", "numa");
        artigoePreposicao.put("artigo29", "numas");
        artigoePreposicao.put("artigo30", "na");
        artigoePreposicao.put("artigo31", "no");
        artigoePreposicao.put("artigo32", "nas");
        artigoePreposicao.put("artigo33", "nos");

        for (int i = palavras.size() - 1; i >= 0; i--) {
            if (artigoePreposicao.containsValue(palavras.get(i).getToken()) || IdentificarPreposicaoOuArtigo(palavras.get(i).getTags())) {
                palavras.remove(i);
            }
        }
    }

    private boolean IdentificarPreposicaoOuArtigo(String[] tags) {
        boolean resultado = false;
        if (tags != null) {
            for (String tag : tags) {

                if (tag.equalsIgnoreCase(Constantes.tag_preposicao) || (tag.equalsIgnoreCase(Constantes.tag_artigo))) {
                    resultado = true;
                }
            }
        }
        return resultado;
    }

    public void FazerDatilologia(List<Palavra> palavras) {
        String[] datilologia = {Constantes.def_datilologia};

        for (Palavra palavraAtual : palavras) {
            String tags[] = palavraAtual.getTags();
            String feats[] = palavraAtual.getTags();
            String lemmas[] = palavraAtual.getTags();


            if ((tags == null) && (feats == null) && (lemmas == null)) {
                palavraAtual.setToken(palavraAtual.getToken().replace("", " ").trim().replace(" ", "-"));
                palavraAtual.setLibras(palavraAtual.getToken());
                palavraAtual.setTags(datilologia);
                palavraAtual.setLemmas(datilologia);
                palavraAtual.setFeats(datilologia);
            }
        }
    }

    public void DesfazerDatilologia(List<Palavra> palavras) {
        String[] datilologia = {Constantes.def_datilologia};
        for (Palavra p : palavras) {
            String tags[] = p.getTags();
            String feats[] = p.getTags();
            String lemmas[] = p.getTags();


            if ((tags == null) && (feats == null) && (lemmas == null)) {
                {
                    p.setToken(p.getToken().replace("-", ""));
                    p.setLibras(p.getToken());
                    p.setTags(datilologia);
                    p.setLemmas(datilologia);
                    p.setFeats(datilologia);
                }
            }
        }
    }
}
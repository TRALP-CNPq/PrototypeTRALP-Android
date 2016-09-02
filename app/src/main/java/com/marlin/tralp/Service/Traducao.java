package com.marlin.tralp.Service;

import android.content.Context;


import com.marlin.tralp.Conexao.Dao.PalavraDAO;
import com.marlin.tralp.Constantes.Constantes;
import com.marlin.tralp.Model.Palavra;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


/**
 * Created by psalum on 30/07/2015.
 */
public class Traducao {
    private PalavraDAO dao;

    public Traducao(Context context) {
        //Inicialização do DAO
        dao = new PalavraDAO(context);
    }

    public String TraduzirPtLibra(List<Palavra> palavras) {
        List<String> sujeito = new ArrayList<String>();
        List<String> objeto = new ArrayList<String>();
        List<String> verbo = new ArrayList<String>();
        StringBuilder frase = new StringBuilder();
        String interrogativo = "";

        ColocarVerbosNoInfinitivo(palavras);

        for (Palavra palavra : palavras) {
            //coloca os verbos no infinitivo e adiciona na lista
            if (palavra.isVerbo()) {
                verbo.add(palavra.getToken());
            }
            //adiciona os sujeitos a lista de sujeitos
            else if (palavra.isSujeito()) {
                    if (palavra.getTags()[0] == Constantes.def_datilologia) {
                        MontaDatilologia(palavra, sujeito);
                    }
                    else
                        sujeito.add(palavra.getLibras());
            }
            //adiciona os objetos a lista de objetos
            else if (palavra.isObjeto()) {
                if (palavra.getTags()[0] == Constantes.def_datilologia) {
                    MontaDatilologia(palavra, objeto);
                }
                else
                    objeto.add(palavra.getLibras());
            } else if (palavra.isInterrogativo()) {
                interrogativo = palavra.getLibras();
            }
        }

        //realiza a montagem da frase.
        for (String t : sujeito) {
            frase.append(t);
            frase.append(" ");
        }
        for (String PalavraAtiva : objeto) {
            frase.append(PalavraAtiva);
            frase.append(" ");
        }

        for (String t : verbo) {
            frase.append(t);
            frase.append(" ");
        }


        if (!VerificarInterrogativo(interrogativo)) {
            frase.append(interrogativo);
        } else {
            frase.insert(0, interrogativo);
        }


        return frase.toString().trim().toUpperCase();
    }

    public String TraduzirLibraPt(List<Palavra> palavras) {
        List<String> sujeito = new ArrayList<String>();
        List<String> objeto = new ArrayList<String>();
        List<Palavra> verbo = new ArrayList<Palavra>();
        StringBuilder frase = new StringBuilder();
        String interrogativo = "";

        for (Palavra palavra : palavras) {

            if (palavra.isVerbo()) {
                verbo.add(palavra);
            }
        }

        for (Palavra p : palavras) {

            if (p.isInterrogativo()) {
                interrogativo = p.getToken();
            } else if (p.isSujeito()) {
                sujeito.add(p.getToken());
            } else if (p.isObjeto()) {
                if (objeto.isEmpty()) {
                    objeto.add(InserirArtigosePreposicoes(p, verbo.isEmpty() ? null : verbo.get(0)));
                } else {
                    objeto.add(p.getToken());
                }

            }
        }
        for (int i = verbo.size() - 1; i == 0; i--) {
            verbo.set(i, FlexionarVerbo(verbo.get(i), objeto, palavras.get(0).getFeats()));
        }

        frase.append(interrogativo).append(" ");

        for (String sujeitoAtual : sujeito) {
            frase.append(sujeitoAtual).append(" ");
        }

        for (Palavra verboAtual : verbo) {
            frase.append(verboAtual.getToken()).append(" ");
        }

        for (String objetoAtual :
                objeto) {
            frase.append(objetoAtual).append(" ");
        }


        return frase.toString().trim().toUpperCase();
    }

    private void MontaDatilologia(Palavra _palavra, List<String> _lista) {

        String pal = _palavra.getLibras().replace("-", "");
        for (char ch: pal.toCharArray()) {
            _lista.add(String.valueOf(ch));
        }
    }

    private boolean VerificarInterrogativo(String palavra) {
        HashMap<String, String> Inicio = new HashMap<String, String>();
        HashMap<String, String> Fim = new HashMap<String, String>();

        Inicio.put("quem", "quem");
        Inicio.put("como", "como");
        Inicio.put("para que", "para que");
        Inicio.put("para-que", "para-que");
        Fim.put("porque", "porque");
        Fim.put("por-que", "por-que");
        Fim.put("por que", "por que");

        return Inicio.containsValue(palavra) || (Fim.containsValue(palavra) ? false : false);

    }

    private void ColocarVerbosNoInfinitivo(List<Palavra> palavras) {
        for (Palavra palavraAtiva : palavras) {
            String[] infinitivo = palavraAtiva.getLemmas();
            palavraAtiva.setToken(infinitivo[0]);
        }
    }

    private Palavra FlexionarVerbo(Palavra verbo, List<String> objetos, String[] featsSujeito) {

        HashMap<String, String> adv_Passado = new HashMap<String, String>();
        adv_Passado.put("ontem", "ontem");
        adv_Passado.put("cedo", "cedo");
        adv_Passado.put("antes", "antes");
        adv_Passado.put("nunca", "nunca");

        HashMap<String, String> adv_Futuro = new HashMap<String, String>();
        adv_Futuro.put("logo", "logo");
        adv_Futuro.put("primeiro", "primeiro");
        adv_Futuro.put("tarde", "tarde");
        adv_Futuro.put("amanhã", "amanhã");
        adv_Futuro.put("depois", "depois");

        HashMap<Integer, String> pessoa = new HashMap<Integer, String>();
        pessoa.put(1, "1S");
        pessoa.put(2, "2S");
        pessoa.put(3, "3S");
        pessoa.put(4, "1P");
        pessoa.put(5, "2P");
        pessoa.put(6, "3P");

        List<Palavra> VerbosRetorno;
        String tempoVerbal = Constantes.feats_presente;
        String Conjugacao = Constantes.feats_terceira_pessoa + Constantes.feats_singular;

        for (String objetoAtual : objetos) {
            if (adv_Passado.containsValue(objetoAtual)) {
                tempoVerbal = Constantes.feats_perfeito_simples;
            } else if (adv_Futuro.containsValue(objetoAtual)) {
                tempoVerbal = Constantes.feats_futuro;
            }
        }
        if (featsSujeito != null) {
            for (String featAtual : featsSujeito) {
                String[] feat = featAtual.split("=");
                for (String f : feat) {
                    if (pessoa.containsValue(f)) {
                        Conjugacao = f;
                    }
                }
            }
        }
        VerbosRetorno = dao.buscarVerbos(verbo.getToken(), tempoVerbal + "=" + Conjugacao);


            List<String> feats = Arrays.asList(VerbosRetorno.get(0).getFeats());
            if (!feats.contains(tempoVerbal + "=" + Conjugacao + "=" + Constantes.feats_subjuntivo)) {
                verbo = VerbosRetorno.get(0);
            }else{
                verbo = VerbosRetorno.get(1);
            }

        return verbo;
    }

    public String InserirArtigosePreposicoes(Palavra palavra, Palavra Verbo) {
        String[] Feats = palavra.getFeats();
        boolean verboExpresso = false;
        String retorno = palavra.getToken();

        if (Verbo != null) {
            List<String> featsVerbo = Arrays.asList(Verbo.getFeats());
            verboExpresso = featsVerbo.contains(Constantes.feats_expresso);
        }


        for (String Feat : Feats) {
            if (Feat.contains(Constantes.feats_masculino + "=" + Constantes.feats_singular) && !verboExpresso ) {
                retorno = ("o " + palavra.getToken());
            } else if (Feat.contains(Constantes.feats_masculino + "=" + Constantes.feats_plural) && !verboExpresso) {
                retorno = ("os " + palavra.getToken());
            } else if (Feat.contains(Constantes.feats_feminino + "=" + Constantes.feats_singular)) {
                retorno = ("a " + palavra.getToken());
            } else if (Feat.contains(Constantes.feats_feminino + "=" + Constantes.feats_plural)) {
                retorno = ("as " + palavra.getToken());
            } else if (Feat.contains(Constantes.feats_masculino + "=" + Constantes.feats_singular) && verboExpresso) {
                retorno = ("ao " + palavra.getToken());
            } else if (Feat.contains(Constantes.feats_masculino + "=" + Constantes.feats_plural) && verboExpresso) {
                retorno = ("aos " + palavra.getToken());
            }
        }
        return retorno;
    }
}




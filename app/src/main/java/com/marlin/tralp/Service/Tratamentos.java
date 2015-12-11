package com.marlin.tralp.Service;

import com.marlin.tralp.Model.Frase;
import com.marlin.tralp.Model.Palavra;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Created by psalum on 29/07/2015.
 */
public class Tratamentos {

    public List<Palavra> TratarDatilologia(List<Palavra> palavras) {
        for (Palavra palavra : palavras) {
            List<String> tags = Arrays.asList(palavra.getTags());
            if(tags.contains("Datilologia")){
                palavra.setToken(palavra.getToken().replace("", " ").trim().replace(" ", "-"));
            }
        }
        return palavras;
    }


    /*Metodo de remocao de caracteres especiais, espacos duplos e quebras de linhas, retorna a frase limpa. Caso possua sinais
        * de + e -, e substituido pela palavra correspondente.*/
    public String LimparFrase(String frase) {
        frase = frase.toLowerCase();
        frase = frase.replace("\n", " ");
        frase = frase.replace(" - ", " menos ");
        frase = frase.replace("-", "");
        frase = frase.replace(" + ", " mais ");
        frase = frase.replaceAll("[^a-zA-Z\\u00C0-\\u00FF]|-|_|\\s", " ");
        frase = frase.replaceAll("[0-9]", "$0 ");
        frase = frase.trim();
        frase = frase.replaceAll("^ +| +$|( )+", "$1");
        return frase;
    }


    //monta a frase definindo posição inicial e posição final
    public Frase ContruirFrase(String frase) {
        Frase f = new Frase();
        String fraselimpa = LimparFrase(frase);
        //String pontuacao = VerificarPontuacao(frase);//verifica��o de pontua��o
        f.setPosicaoInicio(0);
        f.setPosicaoFinal(fraselimpa.length() - 1);
        f.setFrase(fraselimpa);
        return f;
    }


    //Transforma a frase em tokens, definindo posição inicial e final de cada token dentro da frase.
    public List<Palavra> Tokenizacao(Frase f) {

        int ultimaPosicaoFinal =0;

        String[] tokens = f.getFrase().split(" ");
        ArrayList<Palavra> tks = new ArrayList<Palavra>();

        System.out.println("Frase: " + f);
        System.out.println("Tamanho: " + (f.getFrase().length() - 1));
        System.out.println("----------------------------");

       for(String token : tokens){

            Palavra t = new Palavra();
            if(tks.isEmpty()){
                t.setPosicaoInicial(0);
            }else{
                t.setPosicaoInicial(ultimaPosicaoFinal+2);

            }
            t.setPosicaoFinal(t.getPosicaoInicial() + token.length()-1);
            ultimaPosicaoFinal =t.getPosicaoFinal();
            t.setToken(token);


            tks.add(t);
        }

        return tks;
    }

    //Método de limpeza e identifica��o de pontos utilizando Regex, captura apenas o ultimo ponto digitado na frase.
    public String VerificarPontuacao(String frase) {
        frase = frase.replaceAll("[A-Za-zÀ-ú0-9]", "");
        frase = frase.trim();
        String aux[] = frase.split("");
        frase = aux[aux.length - 1];
        if (frase.contains("!") || frase.contains("?") || frase.contains(".")) {
            return frase;
        } else {
            frase = LimparFrase(frase);
            return frase;
        }
    }


}

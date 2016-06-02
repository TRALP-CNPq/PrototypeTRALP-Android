package com.marlin.tralp.Service;

import android.content.Context;

import com.marlin.tralp.Model.Frase;
import com.marlin.tralp.Model.Palavra;

import java.util.List;

/**
 * Created by psalum on 01/10/2015.
 */
public class PortuguesLibras {
    Frase fraseAtual;
    Tratamentos tratamentos;
    Analise analise;
    Traducao traducao;

    public PortuguesLibras(Context context) {
        tratamentos = new Tratamentos();
        analise = new Analise(context);
        traducao = new Traducao(context);
    }


    public String Executar(String frase) {
        Frase fraseAtual = tratamentos.ContruirFrase(frase);
        //quebra da frase em um array de palavras.
        List<Palavra> palavras = tratamentos.Tokenizacao(fraseAtual);

        //envia os tokens para analize e tradução
        analise.Morfologica(palavras, "Token");
        analise.SintaticaPtLibras(palavras);
        //
        palavras = tratamentos.TratarDatilologia(palavras);
        String textoTraduzido = traducao.TraduzirPtLibra(palavras);
    //    UnityBridge.setFraseTraduzida(palavras);
        return textoTraduzido;
    }
}

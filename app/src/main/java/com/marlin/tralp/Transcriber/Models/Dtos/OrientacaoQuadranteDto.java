package com.marlin.tralp.Transcriber.Models.Dtos;

import com.marlin.tralp.Transcriber.Models.OrientacaoQuadrante;

/**
 * Created by cfernandes on 09/06/2016.
 */
public class OrientacaoQuadranteDto {

    public int orientacaoX;
    public int orientacaoY;

    public OrientacaoQuadranteDto(){
    }

    public OrientacaoQuadranteDto(OrientacaoQuadrante origin){
        this.orientacaoX = origin.getOrientacaoX();
        this.orientacaoY = origin.getOrientacaoY();
    }

    public static OrientacaoQuadranteDto Parse(OrientacaoQuadrante origin){
        return new OrientacaoQuadranteDto(origin);
    }
}

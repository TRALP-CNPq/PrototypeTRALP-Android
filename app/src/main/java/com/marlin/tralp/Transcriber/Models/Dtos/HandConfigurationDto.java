package com.marlin.tralp.Transcriber.Models.Dtos;

import com.marlin.tralp.Model.HandConfiguration;

/**
 * Created by cfernandes on 09/06/2016.
 */
public class HandConfigurationDto {
    public int cm;
    public int codCm1;
    public int codCm2;
    public int polegar;
    public int indicador;
    public int meio;
    public int anelarEMindinho;
    public int dedosSeparados;
    public int pontasDedosTocando;

    public HandConfigurationDto(){
    }

    public HandConfigurationDto(HandConfiguration origin){
        this.cm = origin.getCm();
        this.codCm1 = origin.getCodCm1();
        this.codCm2 = origin.getCodCm2();
        this.polegar = origin.getPolegar();
        this.indicador = origin.getIndicador();
        this.meio = origin.getMeio();
        this.anelarEMindinho = origin.getAnelarEMindinho();
        this.dedosSeparados = origin.getDedosSeparados();
        this.pontasDedosTocando = origin.getPontasDedosTocando();
    }

    public static HandConfigurationDto Parse(HandConfiguration origin)
    {
        return new HandConfigurationDto(origin);
    }
}

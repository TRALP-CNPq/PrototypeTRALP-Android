package com.marlin.tralp.Transcriber.Models.Dtos;

import com.marlin.tralp.Transcriber.Models.AnimationParameters;
import com.marlin.tralp.Transcriber.Models.Movement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

/**
 * Created by cfernandes on 09/06/2016.
 */
public class MovementDto {

    public int codMov;
    public int codSign;
    public int codMao;
    public int card;
    public Date tempo;
    public int tipo;
    public AnimationParametersDto animationParameters;

    public MovementDto() {
    }

    public MovementDto(Movement origin) {
        this.codMov = origin.getCodMov();
        this.codSign = origin.getCodSign();
        this.codMao = origin.getCodMao();
        this.card = origin.getCard();
//        this.tempo = origin.getTempo();
        this.tipo = origin.getTipo();
        if(origin.getAnimationParameters() != null)
            this.animationParameters =  new AnimationParametersDto(origin.getAnimationParameters());
    }

    public MovementDto(Movement origin, ArrayList<AnimationParameters> animationParameterses) {
        this(origin);
        this.animationParameters = getAnimationParameters(animationParameterses);
    }

    public static MovementDto Parse(Movement origin)
    {
        return new MovementDto(origin);
    }

    public static MovementDto Parse(Movement origin, ArrayList<AnimationParameters> animationParameterses)
    {
        return new MovementDto(origin, animationParameterses);
    }

    public static MovementDto[] ParseCollection(ArrayList<Movement> origins)
    {
        MovementDto[] collection = new MovementDto[origins.size()];
        for (int i = 0; i < origins.size(); i++){
            collection[i] = new MovementDto(origins.get(i));
        }
        return collection;
    }

    public static MovementDto[] ParseCollection(ArrayList<Movement> origins, ArrayList<AnimationParameters> animationParameterses)
    {
        MovementDto[] collection = new MovementDto[origins.size()];
        for (int i = 0; i < origins.size(); i++){
            collection[i] = new MovementDto(origins.get(i), animationParameterses);
        }
        return collection;
    }

    private AnimationParametersDto getAnimationParameters(ArrayList<AnimationParameters> animationParameterses){
        if(animationParameterses != null) {
            int index = Collections.binarySearch(animationParameterses, new AnimationParameters(this.codMov), getComparator());
            if( index >= 0){
                return new AnimationParametersDto(animationParameterses.get(index));
            }
        }
        return null;
    }

    private Comparator<AnimationParameters> animationParametersComparator;
    public Comparator<AnimationParameters> getComparator(){
        if(animationParametersComparator == null){
            this.animationParametersComparator = new Comparator<AnimationParameters>() {
                public int compare(AnimationParameters u1, AnimationParameters u2) {
                    return u1.getIdentifier().compareTo(u2.getIdentifier());
                }
            };
        }

        return this.animationParametersComparator;
    }
}

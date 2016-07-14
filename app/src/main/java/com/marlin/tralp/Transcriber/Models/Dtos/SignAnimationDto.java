package com.marlin.tralp.Transcriber.Models.Dtos;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.marlin.tralp.Transcriber.Models.AnimationParameters;
import com.marlin.tralp.Transcriber.Models.Sign;

import java.util.ArrayList;

/**
 * Created by cfernandes on 09/06/2016.
 */
public class SignAnimationDto {

    public int codSin;
    public int codPart;
    public int codOp;
    public int codEf;
    public int codCm;
    public int codPal;
    public String palavra;
    public HandConfigurationDto hand;
    public PivotPointDto pivotPoint;
    public FaceExpressionDto faceExpression;
    public PalmOrientationDto palmOrientation;
    public MovementDto[] movements;
    public OrientacaoQuadranteDto orientacaoQuadrante;
    public int ultimaPosX;
    public int ultimaPosY;

    public SignAnimationDto() {
    }

    public SignAnimationDto(Sign origin) {
        this.codSin = origin.getCodSin();
        this.codPart = origin.getCodPart();
        this.codOp = origin.getCodOp();
        this.codEf = origin.getCodEf();
        this.codCm = origin.getCodCm();
        this.codPal = origin.getCodPal();
        this.palavra = origin.getPalavra();
        this.hand = new HandConfigurationDto(origin.getHand());
        this.pivotPoint = new PivotPointDto(origin.getPivotPoint());
        this.faceExpression = new FaceExpressionDto(origin.getFaceExpression());
        this.palmOrientation = new PalmOrientationDto(origin.getPalmOrientation());
        this.orientacaoQuadrante = new OrientacaoQuadranteDto(origin.getOrientacaoQuadrante());
        this.ultimaPosX = origin.getUltimaPosX();
        this.ultimaPosY = origin.getUltimaPosY();
        if(origin.getMovements() != null && origin.getMovements().size() > 0)
            this.movements = MovementDto.ParseCollection(origin.getMovements());
    }

    public SignAnimationDto(Sign origin, ArrayList<AnimationParameters> animationParameterses) {
        this(origin);
        this.movements = MovementDto.ParseCollection(origin.getMovements(), animationParameterses);
    }

    public static SignAnimationDto Parse(Sign origin)
    {
        return new SignAnimationDto(origin);
    }

    public static SignAnimationDto Parse(Sign origin, ArrayList<AnimationParameters> animationParameterses)
    {
        return new SignAnimationDto(origin, animationParameterses);
    }

    public static SignAnimationDto[] ParseCollection(ArrayList<Sign> origins)
    {
        SignAnimationDto[] collection = new SignAnimationDto[origins.size()];
        for (int i = 0; i < origins.size(); i++){
            collection[i] = new SignAnimationDto(origins.get(i));
        }
        return collection;
    }

    public static SignAnimationDto[] ParseCollection(ArrayList<Sign> origins, ArrayList<AnimationParameters> animationParameterses)
    {
        SignAnimationDto[] collection = new SignAnimationDto[origins.size()];
        for (int i = 0; i < origins.size(); i++){
            collection[i] = new SignAnimationDto(origins.get(i), animationParameterses);
        }
        return collection;
    }

    public static String Serialize(SignAnimationDto sign)
    {
        return  GsonHelper.getGson().toJson(sign, SignAnimationDto.class);
    }

    public static String Serialize(SignAnimationDto[] signs){
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        SignCollection collection = new SignCollection(signs);
        return gson.toJson(collection, SignCollection.class);
    }
}

class SignCollection
{
    public SignAnimationDto[] signs = null;

    public SignCollection(SignAnimationDto[] signs)
    {
        this.signs = signs;
    }
}

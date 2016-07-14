package com.marlin.tralp.Transcriber.Models.Dtos;

import com.marlin.tralp.Transcriber.Models.AnimationParameters;

import java.util.ArrayList;

/**
 * Created by cfernandes on 09/06/2016.
 */
public class AnimationParametersDto {
    public int codMov;
    public float RShoulder_V;
    public float RShoulder_H;
    public float RArm_V;
    public float RArm_H;
    public float RArm_R;
    public float RForearm_V;
    public float RForearm_H;
    public float RHand_V;
    public float RHand_H;
    public float RThumb;
    public float RIndex;
    public float RMiddle;
    public float RRing;
    public float RPinky;
    public float LShoulder_V;
    public float LShoulder_H;
    public float LArm_V;
    public float LArm_H;
    public float LArm_R;
    public float LForearm_V;
    public float LForearm_H;
    public float Lforearm_R;
    public float LHand_V;
    public float LHand_H;
    public float LThumb;
    public float LIndex;
    public float LMiddle;
    public float LRing;
    public float LPinky;

    public AnimationParametersDto() {
    }

    public AnimationParametersDto(AnimationParameters origin) {
        this.codMov = origin.getCodMov();
        this.RShoulder_V = origin.getRShoulder_V();
        this.RShoulder_H = origin.getRShoulder_H();
        this.RArm_V = origin.getRArm_V();
        this.RArm_H = origin.getRArm_H();
        this.RArm_R = origin.getRArm_R();
        this.RForearm_V = origin.getRForearm_V();
        this.RForearm_H = origin.getRForearm_H();
        this.RHand_V = origin.getRHand_V();
        this.RHand_H = origin.getRHand_H();
        this.RThumb = origin.getRThumb();
        this.RIndex = origin.getRIndex();
        this.RMiddle = origin.getRMiddle();
        this.RRing = origin.getRRing();
        this.RPinky = origin.getRPinky();
        this.LShoulder_V = origin.getLShoulder_V();
        this.LShoulder_H = origin.getLShoulder_H();
        this.LArm_V = origin.getLArm_V();
        this.LArm_H = origin.getLArm_H();
        this.LArm_R = origin.getLArm_R();
        this.LForearm_V = origin.getLForearm_V();
        this.LForearm_H = origin.getLForearm_H();
        this.Lforearm_R = origin.getLforearm_R();
        this.LHand_V = origin.getLHand_V();
        this.LHand_H = origin.getLHand_H();
        this.LThumb = origin.getLThumb();
        this.LIndex = origin.getLIndex();
        this.LMiddle = origin.getLMiddle();
        this.LRing = origin.getLRing();
        this.LPinky = origin.getLPinky();
    }

    public static AnimationParametersDto Parse(AnimationParameters origin)
    {
        return new AnimationParametersDto(origin);
    }

    public static AnimationParametersDto[] ParseCollection(ArrayList<AnimationParameters> origins)
    {
        AnimationParametersDto[] collection = new AnimationParametersDto[origins.size()];
        for (int i = 0; i < origins.size(); i++){
            collection[i] = new AnimationParametersDto(origins.get(i));
        }
        return collection;
    }
}

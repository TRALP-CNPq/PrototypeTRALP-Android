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
    public float RForearm_R;
    public float RHand_V;
    public float RHand_H;
    public float RThumb1V;
    public float RThumb1H;
    public float RThumb2V;
    public float RIndex1V;
    public float RIndex1H;
    public float RIndex2V;
    public float RMiddle1V;
    public float RMiddle1H;
    public float RMiddle2V;
    public float RRing1V;
    public float RRing1H;
    public float RRing2V;
    public float RPinky1V;
    public float RPinky1H;
    public float RPinky2V;
    public float LShoulder_V;
    public float LShoulder_H;
    public float LArm_V;
    public float LArm_H;
    public float LArm_R;
    public float LForearm_V;
    public float LForearm_H;
    public float LForearm_R;
    public float LHand_V;
    public float LHand_H;
    public float LThumb1V;
    public float LThumb1H;
    public float LThumb2V;
    public float LIndex1V;
    public float LIndex1H;
    public float LIndex2V;
    public float LMiddle1V;
    public float LMiddle1H;
    public float LMiddle2V;
    public float LRing1V;
    public float LRing1H;
    public float LRing2V;
    public float LPinky1V;
    public float LPinky1H;
    public float LPinky2V;


    public AnimationParametersDto() {
    }

    public AnimationParametersDto(AnimationParameters origin) {
        this.codMov = origin.getCodMov();
        this.RShoulder_V = origin.getRShoulder_V();
        this.RShoulder_H = origin.getRShoulder_H();
        this.RArm_V     = origin.getRArm_V();
        this.RArm_H     = origin.getRArm_H();
        this.RArm_R     = origin.getRArm_R();
        this.RForearm_V = origin.getRForearm_V();
        this.RForearm_H = origin.getRForearm_H();
        this.RForearm_R = origin.getRForearm_R();
        this.RHand_V    = origin.getRHand_V();
        this.RHand_H    = origin.getRHand_H();
        this.RThumb1V   = origin.getRThumb1V();
        this.RThumb1H   = origin.getRThumb1H();
        this.RThumb2V   = origin.getRThumb2V();
        this.RIndex1V   = origin.getRIndex1V();
        this.RIndex1H   = origin.getRIndex1H();
        this.RIndex2V   = origin.getRIndex2V();
        this.RMiddle1V  = origin.getRMiddle1V();
        this.RMiddle1H  = origin.getRMiddle1H();
        this.RMiddle2V  = origin.getRMiddle2V();
        this.RRing1V    = origin.getRRing1V();
        this.RRing1H    = origin.getRRing1H();
        this.RRing2V    = origin.getRRing2V();
        this.RPinky1V   = origin.getRPinky1V();
        this.RPinky1H   = origin.getRPinky1H();
        this.RPinky2V   = origin.getRPinky2V();
        this.LShoulder_V = origin.getLShoulder_V();
        this.LShoulder_H = origin.getLShoulder_H();
        this.LArm_V     = origin.getLArm_V();
        this.LArm_H     = origin.getLArm_H();
        this.LArm_R     = origin.getLArm_R();
        this.LForearm_V = origin.getLForearm_V();
        this.LForearm_H = origin.getLForearm_H();
        this.LForearm_R = origin.getLForearm_R();
        this.LHand_V    = origin.getLHand_V();
        this.LHand_H    = origin.getLHand_H();
        this.LThumb1V   = origin.getLThumb1V();
        this.LThumb1H   = origin.getLThumb1H();
        this.LThumb2V   = origin.getLThumb2V();
        this.LIndex1V   = origin.getLIndex1V();
        this.LIndex1H   = origin.getLIndex1H();
        this.LIndex2V   = origin.getLIndex2V();
        this.LMiddle1V  = origin.getLMiddle1V();
        this.LMiddle1H  = origin.getLMiddle1H();
        this.LMiddle2V  = origin.getLMiddle2V();
        this.LRing1V    = origin.getLRing1V();
        this.LRing1H    = origin.getLRing1H();
        this.LRing2V    = origin.getLRing2V();
        this.LPinky1V   = origin.getLPinky1V();
        this.LPinky1H   = origin.getLPinky1H();
        this.LPinky2V   = origin.getLPinky2V();
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

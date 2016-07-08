package com.marlin.tralp.Transcriber.Models.Dtos;

import com.marlin.tralp.Model.PivotPoint;

/**
 * Created by cfernandes on 09/06/2016.
 */
public class PivotPointDto {

    public PivotPointDto(){
    }

    public PivotPointDto(PivotPoint origin){

    }

    public static PivotPointDto Parse(PivotPoint origin)
    {
        return new PivotPointDto(origin);
    }
}

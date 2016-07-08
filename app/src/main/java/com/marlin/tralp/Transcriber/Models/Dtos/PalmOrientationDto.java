package com.marlin.tralp.Transcriber.Models.Dtos;

import com.marlin.tralp.Model.PalmOrientation;

/**
 * Created by cfernandes on 09/06/2016.
 */
public class PalmOrientationDto {

    public PalmOrientationDto() {
    }

    public PalmOrientationDto(PalmOrientation origin) {
    }

    public static PalmOrientationDto Parse(PalmOrientation origin)
    {
        return new PalmOrientationDto(origin);
    }
}

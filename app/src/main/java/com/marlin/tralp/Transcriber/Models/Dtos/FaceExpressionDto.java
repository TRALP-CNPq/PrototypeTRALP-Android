package com.marlin.tralp.Transcriber.Models.Dtos;

import com.marlin.tralp.Model.FaceExpression;

/**
 * Created by cfernandes on 09/06/2016.
 */
public class FaceExpressionDto {

    public FaceExpressionDto(){
    }

    public FaceExpressionDto(FaceExpression origin){
    }

    public static FaceExpressionDto Parse(FaceExpression origin)
    {
        return new FaceExpressionDto(origin);
    }
}

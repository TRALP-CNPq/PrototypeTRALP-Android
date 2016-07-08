package com.marlin.tralp.Transcriber.Models;

/**
 * Created by aneves on 5/17/2016.
 */
public class MovementFactory {
    public Movement GetMovement(int codMov,int tipo) {
            if (tipo == 1) {
                return new MovementLinear(codMov);
            } else if (tipo == 2) {
                return new CircularMotion();
            } else if (tipo == 3) {
                return new MovementRotational();
            } else
                return new HandConfigurationChange();
    }
}

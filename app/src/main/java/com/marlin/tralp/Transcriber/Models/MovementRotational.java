package com.marlin.tralp.Transcriber.Models;

import android.content.Context;

import com.marlin.tralp.AppContext;
import com.marlin.tralp.Conexao.Dao.MovimentoDAO;
import com.marlin.tralp.Transcriber.ImageProcess.Point2D;

/**
 * Created by aneves on 5/17/2016.
 */
public class MovementRotational extends Movement {
    int codMov;



    float latitudeInicial;
    float latitudeFinal;
    float radius;
    Point2D center;
    Point2D posInicial;

    private MovimentoDAO dao;

    public MovementRotational(){

    }
    public MovementRotational(int codMov, Sign candidate){
        this.codMov = codMov;
        Context context = AppContext.getAppContext();
        dao = new MovimentoDAO(context);
        MovementRotational mov = dao.ObterMovimentoRotacionalPorId(codMov);
        latitudeInicial = mov.getLatitudeInicial();
        radius = getRadius();
        if(latitudeInicial < 90 ){
            center = new Point2D( center.getX() + radius*Math.sin(latitudeInicial),
                    center.getY() + radius* Math.cos(latitudeInicial));
        }else if(latitudeInicial < 180){
            center = new Point2D( center.getX() - radius*Math.sin(latitudeInicial),
                    center.getY() + radius* Math.cos(latitudeInicial));
        }else if(latitudeInicial < 270){
            center = new Point2D( center.getX() - radius*Math.sin(latitudeInicial),
                    center.getY() - radius* Math.cos(latitudeInicial));
        }else{
            center = new Point2D( center.getX() + radius*Math.sin(latitudeInicial),
                    center.getY() - radius* Math.cos(latitudeInicial));
        }
        posInicial = new Point2D(candidate.getUltimaPosX(), candidate.getUltimaPosY());


    }

    public boolean MovementsThroughThatAddress(Sign candidate, int handCenterX, int handCenterY, double tolerance) {

        float distance = (float) Math.hypot((handCenterX-center.getX()), (handCenterY - center.getY()));
        if(distance > radius*(1+tolerance) || distance < radius *(1-tolerance)){
            return false;
        }

        distance = (float) Math.hypot((handCenterX-posInicial.getX()), (handCenterY - posInicial.getY()));
        float deltaAngle = (float) Math.acos(1-(Math.pow(distance,2)/(2*Math.pow(radius,2))));

        if (deltaAngle >  Math.abs(latitudeFinal-latitudeInicial)* (1+tolerance)){
            return false;
        }

        return true;
    }
    public float getLatitudeInicial() {
        return latitudeInicial;
    }

    public void setLatitudeInicial(float latitudeInicial) {
        this.latitudeInicial = latitudeInicial;
    }
    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

}

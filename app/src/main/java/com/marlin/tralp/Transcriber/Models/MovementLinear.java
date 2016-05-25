package com.marlin.tralp.Transcriber.Models;

import android.app.Application;
import android.content.Context;

import com.marlin.tralp.AppContext;
import com.marlin.tralp.Conexao.Dao.MovimentoDAO;
import com.marlin.tralp.Model.Mat;
import com.marlin.tralp.Transcriber.ImageProcess.Point3D;
import com.marlin.tralp.Transcriber.ImageProcess.Position;

/**
 * Created by aneves on 5/17/2016.
 */
public class MovementLinear extends Movement {
    int codMov;
    int angulo1;
    int angulo2;
    float tamanho;
    int posicaoIniX;
    int posicaoIniY;
    int posicaoFimX;
    int posicaoFimY;
    double catOposto;
    double catAdjacente;

    private MovimentoDAO dao;

    public MovementLinear() {

    }
    public MovementLinear(int _codMov) {
        codMov = _codMov;
        Context context = AppContext.getAppContext();
        dao = new MovimentoDAO(context);
        MovementLinear mov = dao.ObterMovimentoLinearPorId(_codMov);
        angulo1 = mov.getAngulo1();
        angulo2 = mov.getAngulo2();
        tamanho = mov.getTamanho();
    }

    public boolean MovementsThroughThatAddress(Sign candidate, FeatureStructure frame, double tolerance) {
        Point3D point3d = new Position().handPosition(frame.faceX, frame.faceY, (frame.faceX + frame.faceWidth),
                (frame.faceY + frame.faceHeight), frame.handX, frame.handY, (frame.handX + frame.handWidth), (frame.handY + frame.handHeight));
        posicaoIniX = candidate.getUltimaPosX();
        posicaoIniY = candidate.getUltimaPosY();
        Point3D posicaoFinalMov = CalcularPontoFinal((int) tamanho);
        int novaHipotenusa = (int) Math.sqrt((((int)posicaoFinalMov.getX() - frame.handCenterX) ^ 2)+(((int)posicaoFinalMov.getY() - frame.handCenterY) ^ 2));
        Point3D posicaoFinalFrame = CalcularPontoFinal(novaHipotenusa);

        if ((posicaoFinalFrame.getX() >= (posicaoFinalMov.getX() - (posicaoFinalMov.getX() * tolerance)) ||
                posicaoFinalFrame.getX() <= (posicaoFinalMov.getX() + (posicaoFinalMov.getX() * tolerance))) &&
                (posicaoFinalFrame.getY() >= (posicaoFinalMov.getY() - (posicaoFinalMov.getY() * tolerance)) ||
                        posicaoFinalFrame.getY() <= (posicaoFinalMov.getY() + (posicaoFinalMov.getY() * tolerance))))
            return true;
        else
            return false;
    }

    private Point3D CalcularPontoFinal(int hipotenusa) {
        double a = Math.toRadians(angulo2);
        int quadrante = angulo2 / 90;
        Math.sin(a);
        Math.cos(a);
        catOposto = Math.sin(a) * hipotenusa;
        catAdjacente = Math.cos(a) * hipotenusa;
        Point3D posicaoFinal = new Point3D();
        if (quadrante < 3)
            posicaoFinal.setX(posicaoIniX + (int) catAdjacente);
        else
            posicaoFinal.setX(posicaoIniX - (int) catAdjacente);
        if (quadrante == 1 || quadrante == 4)
            posicaoFinal.setY(posicaoIniY + (int) catOposto);
        else
            posicaoFinal.setY(posicaoIniY - (int) catOposto);

        return  posicaoFinal;
    }

    @Override
    public int getCodMov() {
        return codMov;
    }

    @Override
    public void setCodMov(int codMov) {
        this.codMov = codMov;
    }

    public int getAngulo1() {
        return angulo1;
    }

    public void setAngulo1(int angulo1) {
        this.angulo1 = angulo1;
    }

    public int getAngulo2() {
        return angulo2;
    }

    public void setAngulo2(int angulo2) {
        this.angulo2 = angulo2;
    }

    public float getTamanho() {
        return tamanho;
    }

    public void setTamanho(float tamanho) {
        this.tamanho = tamanho;
    }
}

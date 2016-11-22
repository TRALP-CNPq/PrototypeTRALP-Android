package com.marlin.tralp.Transcriber.tr_Models;

import com.marlin.tralp.Conexao.Dao.DetectoresDAO;

/**
 * Created by paulo on 12/11/2016.
 */


public class Detector {


    private int ID;
    private int ID_CONFIGURACAO_MAO;
    private int ID_MOV_ROTACIONAL;
    private String file_name;

    public Detector(int ID, int ID_CONFIGURACAO_MAO, int ID_MOV_ROTACIONAL, String file_name) {
        this.ID = ID;
        this.ID_CONFIGURACAO_MAO = ID_CONFIGURACAO_MAO;
        this.ID_MOV_ROTACIONAL = ID_MOV_ROTACIONAL;
        this.file_name = file_name;

    }

    public int getiD() {
        return ID;
    }

    public void setiD(int ID) {
        this.ID = ID;
    }

    public int getiD_CONFIGURACAO_MAO() {
        return ID_CONFIGURACAO_MAO;
    }

    public void setiD_CONFIGURACAO_MAO(int ID_CONFIGURACAO_MAO) {
        this.ID_CONFIGURACAO_MAO = ID_CONFIGURACAO_MAO;
    }

    public int getiD_MOV_ROTACIONAL() {
        return ID_MOV_ROTACIONAL;
    }

    public void setiD_MOV_ROTACIONAL(int ID_MOV_ROTACIONAL) {
        this.ID_MOV_ROTACIONAL = ID_MOV_ROTACIONAL;
    }

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }
}



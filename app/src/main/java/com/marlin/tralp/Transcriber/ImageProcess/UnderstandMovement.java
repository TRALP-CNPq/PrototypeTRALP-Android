package com.marlin.tralp.Transcriber.ImageProcess;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.vision.face.FaceDetector;
import com.marlin.tralp.MainApplication;
import com.marlin.tralp.Transcriber.Models.FeatureStructure;
import com.marlin.tralp.Transcriber.Models.FrameQueue;
import com.marlin.tralp.Transcriber.Models.Movement;
import com.marlin.tralp.Transcriber.Models.MovementFactory;
import com.marlin.tralp.Transcriber.Models.Sign;

import org.opencv.objdetect.CascadeClassifier;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by aneves on 5/16/2016.
 */
public class UnderstandMovement {

    private MainApplication mApp;
    private Context context;
    private CascadeClassifier palm, fist;
    private FrameQueue frameQueue;
    private FaceDetector faceDetector;
    private ArrayList<FeatureStructure> annotationResult;
    private String frase;
    String palavra, ultPalavra;
    private int indFrame;

    public UnderstandMovement(MainApplication app) {
        mApp = app;
        this.context = app.getApplicationContext();
    }

    public String StartSignsAndMovementsInterpreter(FeatureAnnotation capture) {
        annotationResult = capture.annotationResult;
        Sign sign = new Sign(mApp);
        Movement movs = new Movement();
        palavra = "";
        ultPalavra = "";
        frase="";

        Log.d("annotationResult: ", "Size " + annotationResult.size());
        for (indFrame = 0; indFrame < (annotationResult.size()); indFrame++) {
            if (annotationResult.get(indFrame) != null) {
                Log.d("annotationResult: ", "secProcessed: " + indFrame +
                        "  X " + annotationResult.get(indFrame).handCenterX +
                        "  Y " + annotationResult.get(indFrame).handCenterY);
                ArrayList<Sign> candidatesSigns = sign.GetSignsStartInPosition(
                        annotationResult.get(indFrame).faceX, annotationResult.get(indFrame).faceY,
                        annotationResult.get(indFrame).faceWidth,
                        annotationResult.get(indFrame).faceHeight,
                        annotationResult.get(indFrame).handX,
                        annotationResult.get(indFrame).handY,
                        annotationResult.get(indFrame).handWidth,
                        annotationResult.get(indFrame).handHeight,.30);
                if (candidatesSigns.size() > 0) {
                    palavra = FilterCandidates(annotationResult, candidatesSigns);
                    if (palavra != ultPalavra && palavra != "null") {
                        frase = frase + palavra + " ";
                        ultPalavra = palavra;
                        palavra = "";
                    }
                }
            }
        }
        return frase;
    }

//    private String FilterCandidates(ArrayList<FeatureStructure> annotationResult, ArrayList<Sign> candidates) {
//       // int iframes = 0;
//        ArrayList<Sign> newCandidates;
//        Sign lastCandidate = new Sign();
//=======
//    private String FilterCandidatesOld(ArrayList<FeatureStructure> annotationResult, ArrayList<Sign> candidates) {
//        int iframes = 0;
//>>>>>>> origin/feauture/vision-linguistic-sys
//
//        //Movement movs = new Movement();
//        for (indFrame = 1; candidates.size() < 1; indFrame++) { // percorre os frames        Iterator iteratorFrame = annotationResult.iterator(); iteratorFrame.hasNext();
////            candidates = null;
//<<<<<<< HEAD
//            FeatureStructure frame = annotationResult.get(indFrame);         //iteratorFrame.next();
//            newCandidates = new ArrayList<Sign>();
//=======
//            FeatureStructure frame = (FeatureStructure) iteratorFrame.next();
//
//            ArrayList<Sign> newCandidates = new ArrayList<Sign>();
//
//>>>>>>> origin/feauture/vision-linguistic-sys
//            for (Iterator iterator = candidates.iterator(); iterator.hasNext(); ) {   // percorre os candidatosint i = 1; i >= candidates.size(); i++
//
//                Sign candidate = (Sign) iterator.next();
//                ArrayList<Movement> movs = candidate.getMovements();
//                for (Iterator iteratorMov = movs.iterator(); iterator.hasNext(); ) {   // percorre os movimentos
//
//                    Movement movCandidate = (Movement) iterator.next();
//                    Movement mov = new MovementFactory().GetMovement(movCandidate.getCodMov(),movCandidate.getTipo());
//<<<<<<< HEAD
//                    if (!movCandidate.isProcessed() && mov.MovementsThroughThatAddress(candidate, frame, .30)) {
//=======
//
//                    if (!movCandidate.isProcessed() && !mov.MovementsThroughThatAddress(candidate, frame, .30)) {
//
//>>>>>>> origin/feauture/vision-linguistic-sys
//                        movCandidate.setProcessed(true);
//                        newCandidates.add(candidate);
//                        lastCandidate = candidate;
//                        //candidates.get(i).getMovements().get(j).setProcessed(true);
//                        break;
//                    } else if (!movCandidate.isProcessed()) {  //candidates.get(i).getMovements().size() < (i + 1)
//                        break;
//                    }
//                }
//            }
//            candidates = newCandidates;
//            newCandidates = null;
//        }
//        if (lastCandidate.getPalavra() != null) {
//            // Reconheci o sinal
//            palavra = lastCandidate.getPalavra();
//            indFrame--;
//        } else {
//            // Não conheço esse sinal
//            DesprezaSinal(annotationResult.get(indFrame));
//            palavra = "Nao entendi sinal";
//            indFrame--;
//        }
//        return palavra;
//    }

    private String FilterCandidates(ArrayList<FeatureStructure> annotationResult, ArrayList<Sign> candidates) {
        int candidatesIndex = 0;
        int codMov = 0;
        int tipo = 0;

        //Movement movs = new Movement();
        Log.d("UnderstandMovement: ", "annotationResult.size = " + annotationResult.size() + "  candidates.size" + candidates.size());
        Iterator iteratorFrame = annotationResult.iterator();
        ArrayList<Sign> lastIterationcandidates = (ArrayList<Sign>) candidates.clone();
        FeatureStructure lastFeature = (FeatureStructure) iteratorFrame.next(),
        featureToTest = (FeatureStructure) iteratorFrame.next(),
        tempFeature;

        while (!candidates.isEmpty()) {   // percorre os candidatosint i = 1; i >= candidates.size(); i++
            Log.d("UnderstandMovement: ", " candidatesIndex.size = " + candidatesIndex + "  candidates.size: " + candidates.size());
            if(candidatesIndex == candidates.size()){ // Reseta para a posição inicial, para manter a filtragem acontecendo
                candidatesIndex = 0;
                lastIterationcandidates = (ArrayList<Sign>) candidates.clone();
                if (iteratorFrame.hasNext()) { // Lida com o possivel fim de features reconhecidas
                    featureToTest = (FeatureStructure) iteratorFrame.next();
                    indFrame++;
                }else {
                 //   return "// fim antes do termino do filtro";
                    //@TODO deve parar o processo e elencar a melhor palavra possível com o restante
                    break;
                }
            }
            Sign candidateOnTest = candidates.get(candidatesIndex);
            Log.d("UnderstandMovement ", "cod Sign = " + candidateOnTest.getCodSin() +
                    " mov.size = " + candidateOnTest.getMovementsSize() +
                    " palavra = " + candidateOnTest.getPalavra());
            if (candidateOnTest.getMovementsSize() > 0) {
                Log.d("UnderstandMovement ", "cod movimento = " + candidateOnTest.getMovement(0)
                        .getCodMov() + "cod tipo = " + candidateOnTest.getMovement(0)
                        .getTipo());
                codMov=candidateOnTest.getMovement(0).getCodMov();
                tipo=candidateOnTest.getMovement(0).getTipo();
            } else {
                Log.d("UnderstandMovement ", "cod Sign = " + candidateOnTest.getCodSin() +
                        " Movimento vazio para palavra  " + candidateOnTest.getPalavra());
                candidatesIndex++;
                candidates.remove(candidateOnTest);
                continue;
            }
            Movement mov = new MovementFactory().GetMovement(codMov, tipo);
            if(candidateOnTest.getMovementsSize() > 0 && mov.
                    MovementsThroughThatAddress(candidateOnTest,featureToTest, 0.1)){
                //Accepts the features as possible
                Log.d("UnderstandMovement ", "achou movimento = " + candidatesIndex);
                candidatesIndex++;
                continue;

            }else{
                int index = annotationResult.indexOf(featureToTest)-1;
                Log.d("UnderstandMovement: ", "index = " + index);
                if(candidateOnTest.getMovementsSize() > 1 && index >=0){ // if there is at least one more
                    //Update the last position to the last succesful feature
                    tempFeature = annotationResult.get(index);
                    candidateOnTest.setUltimaPosX(tempFeature.faceCenterX);
                    candidateOnTest.setUltimaPosY(tempFeature.faceCenterY);
                    Movement mov1 = new MovementFactory().GetMovement(candidateOnTest.getMovement(1)
                            .getCodMov(),candidateOnTest.getMovement(1).getTipo());
                    if(mov1.MovementsThroughThatAddress(candidateOnTest,featureToTest, 0.1)){
                        //Update for the movement being tested
                        candidateOnTest.removeMovement(0);
                        //Accepts the features as possible
                        candidatesIndex++;
                        continue;

                    }
                }
            }
            Log.d("UnderstandMovement: ", "candidatesIndex = " + candidatesIndex + "  Removido " + candidateOnTest.getCodSin());
            candidatesIndex++;
            lastIterationcandidates = (ArrayList<Sign>) candidates.clone();
            candidates.remove(candidateOnTest);
            if(candidatesIndex > candidates.size()) { // Reseta para a posição inicial, para manter a filtragem acontecendo
                candidatesIndex--;
            }
        }
    //    indFrame = annotationResult.lastIndexOf(featureToTest);
        Log.d("UnderstandMovement: ", "candidatesIndex = " + candidatesIndex);
        for (int i = 0; i < lastIterationcandidates.size() ; i++) {
            //@TODO Beter evaluation of successful sign classification
            //Getting the first the has "until the last movement" match
            if(lastIterationcandidates.get(i).getMovementsSize() <= 1){
                return lastIterationcandidates.get(i).getPalavra();
            }
        }
        return ""; // Signal not recognized
    }

    private void DesprezaSinal(FeatureStructure featureStructure) {

    }
    private void ReconheciSinal(ArrayList<Sign> candidates) {
        palavra = candidates.get(0).getPalavra();
    }
}

package com.marlin.tralp.Transcriber.ImageProcess;

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
    private CascadeClassifier palm, fist;
    private FrameQueue frameQueue;
    private FaceDetector faceDetector;
    private ArrayList<FeatureStructure> annotationResult;
    private String frase;
    String palavra;

    public UnderstandMovement(MainApplication app) {
        mApp = app;
    }

    public String StartSignsAndMovementsInterpreter(FeatureAnnotation capture) {
        annotationResult = capture.annotationResult; // mApp.annotation
        Sign sign = new Sign(mApp);
        Movement movs = new Movement();

        Log.d("annotationResult: ", "Size " + annotationResult.size());
        for (int index = 0; index < (annotationResult.size()); index++) {
            if (annotationResult.get(index) != null) {
                Log.d("annotationResult: ", "index: " + index +
                        "  X " + annotationResult.get(index).handRelativeX +
                        "  Y " + annotationResult.get(index).handRelativeY);
                ArrayList<Sign> candidatesSigns = sign.GetSignsStartInPosition(
                        annotationResult.get(index).handRelativeX,
                        annotationResult.get(index).handRelativeY, 1);
                frase = frase + FilterCandidates(annotationResult, candidatesSigns) + " ";

            }
        }
        return frase;
    }
    private String FilterCandidatesOld(ArrayList<FeatureStructure> annotationResult, ArrayList<Sign> candidates) {
        int iframes = 0;

        //Movement movs = new Movement();
        for (Iterator iteratorFrame = annotationResult.iterator(); iteratorFrame.hasNext();) { // percorre os frames      iframes = 1; candidates.size() < 2; iframes++
//            candidates = null;
            FeatureStructure frame = (FeatureStructure) iteratorFrame.next();

            ArrayList<Sign> newCandidates = new ArrayList<Sign>();

            for (Iterator iterator = candidates.iterator(); iterator.hasNext(); ) {   // percorre os candidatosint i = 1; i >= candidates.size(); i++

                Sign candidate = (Sign) iterator.next();
                ArrayList<Movement> movs = candidate.getMovements();
                for (Iterator iteratorMov = movs.iterator(); iterator.hasNext(); ) {   // percorre os movimentos

                    Movement movCandidate = (Movement) iterator.next();
                    Movement mov = new MovementFactory().GetMovement(movCandidate.getCodMov(),movCandidate.getTipo());

                    if (!movCandidate.isProcessed() && !mov.MovementsThroughThatAddress(candidate, frame, .30)) {

                        movCandidate.setProcessed(true);
                        newCandidates.add(candidate);
                        //candidates.get(i).getMovements().get(j).setProcessed(true);
                        break;
                    } else if (!movCandidate.isProcessed()) {  //candidates.get(i).getMovements().size() < (i + 1)
                        break;
                    }
                }
            }
            candidates = newCandidates;
        }
        if (candidates.size() == 1) {
            // Reconheci o sinal
            palavra = candidates.get(0).getPalavra();
        } else {
            // Não conheço esse sinal
            DesprezaSinal(annotationResult.get(iframes));
            palavra = null;
        }
        return palavra;
    }

    private String FilterCandidates(ArrayList<FeatureStructure> annotationResult, ArrayList<Sign> candidates) {
        int candidatesIndex = 0;

        //Movement movs = new Movement();
        Iterator iteratorFrame = annotationResult.iterator();
        ArrayList<Sign> lastIterationcandidates = (ArrayList<Sign>) candidates.clone();
        FeatureStructure lastFeature = (FeatureStructure) iteratorFrame.next(),
        featureToTest = (FeatureStructure) iteratorFrame.next(),
        tempFeature;

        while (!candidates.isEmpty()) {   // percorre os candidatosint i = 1; i >= candidates.size(); i++

            if(candidatesIndex == candidates.size()){ // Reseta para a posição inicial, para manter a filtragem acontecendo
                candidatesIndex = 0;
                lastIterationcandidates = (ArrayList<Sign>) candidates.clone();
                if (iteratorFrame.hasNext()) { // Lida com o possivel fim de features reconhecidas
                    featureToTest = (FeatureStructure) iteratorFrame.next();
                }else {
                    return "// fim antes do termino do filtro";
                    //@TODO deve parar o processo e elencar a melhor palavra possível com o restante
                }

            }
            Sign candidateOnTest = candidates.get(candidatesIndex);
            if(candidateOnTest.getMovementsSize() > 0 && candidateOnTest.getMovement(0).
                    MovementsThroughThatAddress(candidateOnTest,featureToTest, 0.3)){
                //Accepts the features as possible
                candidatesIndex++;
                continue;

            }else{
                int index = annotationResult.indexOf(featureToTest)-1;
                if(candidateOnTest.getMovementsSize() > 1 && index >=0){ // if there is at least one more
                    //Update the last position to the last succesful feature
                    tempFeature = annotationResult.get(index);
                    candidateOnTest.setUltimaPosX(tempFeature.faceCenterX);
                    candidateOnTest.setUltimaPosY(tempFeature.faceCenterY);

                    if(candidateOnTest.getMovement(1).MovementsThroughThatAddress(
                            candidateOnTest,featureToTest, 0.3)){
                        //Update for the movement being tested
                        candidateOnTest.removeMovement(0);
                        //Accepts the features as possible
                        candidatesIndex++;
                        continue;

                    }
                }
            }
            candidatesIndex++;
            candidates.remove(candidateOnTest);
        }

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

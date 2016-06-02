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
    private int indFrame;

    public UnderstandMovement(MainApplication app) {
        mApp = app;
    }

    public String StartSignsAndMovementsInterpreter(FeatureAnnotation capture) {
        annotationResult = capture.annotationResult;
        Sign sign = new Sign(mApp);
        Movement movs = new Movement();

        Log.d("annotationResult: ", "Size " + annotationResult.size());
        for (indFrame = 0; indFrame < (annotationResult.size()); indFrame++) {
            if (annotationResult.get(indFrame) != null) {
                Log.d("annotationResult: ", "secProcessed: " + indFrame +
                        "  X " + annotationResult.get(indFrame).handCenterX +
                        "  Y " + annotationResult.get(indFrame).handCenterY);
                ArrayList<Sign> candidatesSigns = sign.GetSignsStartInPosition(
                        annotationResult.get(indFrame).handCenterX,
                        annotationResult.get(indFrame).handCenterY, .30);
                frase = frase + FilterCandidates(annotationResult, candidatesSigns) + " ";
            }
        }
        return frase;
    }

    private String FilterCandidates(ArrayList<FeatureStructure> annotationResult, ArrayList<Sign> candidates) {
       // int iframes = 0;
        ArrayList<Sign> newCandidates;
        Sign lastCandidate = new Sign();

        //Movement movs = new Movement();
        for (indFrame = 1; candidates.size() < 1; indFrame++) { // percorre os frames        Iterator iteratorFrame = annotationResult.iterator(); iteratorFrame.hasNext();
//            candidates = null;
            FeatureStructure frame = annotationResult.get(indFrame);         //iteratorFrame.next();
            newCandidates = new ArrayList<Sign>();
            for (Iterator iterator = candidates.iterator(); iterator.hasNext(); ) {   // percorre os candidatosint i = 1; i >= candidates.size(); i++
                Sign candidate = (Sign) iterator.next();
                ArrayList<Movement> movs = candidate.getMovements();
                for (Iterator iteratorMov = movs.iterator(); iterator.hasNext(); ) {   // percorre os movimentos
                    Movement movCandidate = (Movement) iterator.next();
                    Movement mov = new MovementFactory().GetMovement(movCandidate.getCodMov(),movCandidate.getTipo());
                    if (!movCandidate.isProcessed() && mov.MovementsThroughThatAddress(candidate, frame, .30)) {
                        movCandidate.setProcessed(true);
                        newCandidates.add(candidate);
                        lastCandidate = candidate;
                        //candidates.get(i).getMovements().get(j).setProcessed(true);
                        break;
                    } else if (!movCandidate.isProcessed()) {  //candidates.get(i).getMovements().size() < (i + 1)
                        break;
                    }
                }
            }
            candidates = newCandidates;
            newCandidates = null;
        }
        if (lastCandidate.getPalavra() != null) {
            // Reconheci o sinal
            palavra = lastCandidate.getPalavra();
            indFrame--;
        } else {
            // Não conheço esse sinal
            DesprezaSinal(annotationResult.get(indFrame));
            palavra = "Nao entendi sinal";
            indFrame--;
        }
        return palavra;
    }

    private void DesprezaSinal(FeatureStructure featureStructure) {

    }
    private void ReconheciSinal(ArrayList<Sign> candidates) {
        palavra = candidates.get(0).getPalavra();
    }
}

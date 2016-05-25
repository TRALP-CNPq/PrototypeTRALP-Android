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
        annotationResult = capture.annotationResult;
        Sign sign = new Sign(mApp);
        Movement movs = new Movement();

        Log.d("annotationResult: ", "Size " + annotationResult.size());
        for (int secProcessed = 0; secProcessed < (annotationResult.size()); secProcessed++) {
            if (annotationResult.get(secProcessed) != null) {
                Log.d("annotationResult: ", "secProcessed: " + secProcessed +
                        "  X " + annotationResult.get(secProcessed).handCenterX +
                        "  Y " + annotationResult.get(secProcessed).handCenterY);
                ArrayList<Sign> candidatesSigns = sign.GetSignsStartInPosition(
                        annotationResult.get(secProcessed).handCenterX,
                        annotationResult.get(secProcessed).handCenterY, .30);
                frase = frase + FilterCandidates(annotationResult, candidatesSigns) + " ";

            }
        }
        return frase;
    }

    private String FilterCandidates(ArrayList<FeatureStructure> annotationResult, ArrayList<Sign> candidates) {
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

    private void DesprezaSinal(FeatureStructure featureStructure) {

    }
    private void ReconheciSinal(ArrayList<Sign> candidates) {
        palavra = candidates.get(0).getPalavra();
    }
}

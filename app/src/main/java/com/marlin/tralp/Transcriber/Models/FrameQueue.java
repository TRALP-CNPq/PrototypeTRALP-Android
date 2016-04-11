package com.marlin.tralp.Transcriber.Models;

import android.app.Fragment;


import com.marlin.tralp.Model.Mat;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by gabriel on 16-04-09.
 */
public class FrameQueue {
    public int howManySeconds = 0;
    private int secondIndex = 0;
    private int frameIndex = 0;
    public int resolutionFramesAmount = 5; //Default amount of frames we will use to represent a second
    private int secondIndexCounter = 0, frameIndexCounter = 0;
    private List<Dictionary<Integer,List<Mat>>> frameData;

    public FrameQueue(){
        frameData = new ArrayList<Dictionary<Integer,List<Mat>>>();
    }

    public int getHowManySeconds(){
        return howManySeconds;
    }

    public void setHowManySeconds(int qtt){
        /**
         *  Sets how many seconds we represent over the structure; does lots of initialization
         *  Highly recommended to be called only ONCE
         */
        if(howManySeconds < qtt) {
            for (int i = howManySeconds; i < qtt; i++) {
                if (!frameData.add(new Hashtable<Integer, List<Mat>>())) {
                    throw new OutOfMemoryError("Run Out Of Memory While Trying to create Dicts for FrameQueue Second");
                }
            }
            initalizeDictionariesLists(howManySeconds, qtt);
        }
        howManySeconds = qtt;
    }

    private void initalizeDictionariesLists(int startSecondIndex, int endSecondIndex ){
        Dictionary<Integer, List<Mat>> temp;
        for (int i = startSecondIndex; i < endSecondIndex; i++) {
            temp = frameData.get(i);
            for (int j = 0;j < resolutionFramesAmount; j++) {
                try {
                    temp.put(j,new ArrayList<Mat>());
                }catch (Exception e){
                    String msg = new String("Failed at FrameQueue while creating lists at dictionaries: ");
                    msg = msg + e.toString();
                    throw new OutOfMemoryError(msg);
                }
            }
        }

    }
    public void setSecondIndex(int num){
        secondIndex = num;
    }
    public int getSecondIndex(int num){
        return secondIndex;
    }
    public void setFrameIndex(int num){
        secondIndex = num;
    }
    public int getFrameIndex(int num){
        return secondIndex;
    }
    public void setMatFrame(int frameIndex, int order, Mat frame ){
        List<Mat> localMatList = frameData.get(secondIndex).get(frameIndex);
        localMatList.add(order,frame);
    }
    public void setMatFrame(int secondIndex, int frameIndex, int order, Mat frame ){
        List<Mat> localMatList = frameData.get(secondIndex).get(frameIndex);
        localMatList.add(order,frame);
    }
    public void setMatFrame(int order, Mat frame){
        List<Mat> localMatList = frameData.get(secondIndex).get(frameIndex);
        localMatList.add(order,frame);
    }
    public void setMatFrameBulk(List<Mat> frames){
        List<Mat> localMatList = frameData.get(secondIndex).get(frameIndex);
        localMatList.addAll(frames);
    }
    public Mat getMatFrame(int frameIndex, int order){
        return frameData.get(secondIndex).get(frameIndex).get(order);
    }
    public Mat getMatFrame(int secondIndex, int frameIndex, int order){
        return frameData.get(secondIndex).get(frameIndex).get(order);
    }
    public Mat getMatFrame(int order){
        return frameData.get(secondIndex).get(frameIndex).get(order);
    }

    public Mat popMatFrame(int secondIndex, int frameIndex){
        if(frameData.get(secondIndex).get(frameIndex).size() > 0){
            return frameData.get(secondIndex).get(frameIndex).remove(0);
        }
        return null;
    }
    public Mat popMatFrame(int frameIndex){
        if(frameData.get(secondIndex).get(frameIndex).size() > 0){
            return frameData.get(secondIndex).get(frameIndex).remove(0);
        }
        return null;
    }
    public Mat popMatFrame(){
        if(frameData.get(secondIndex).get(frameIndex).size() > 0){
            return frameData.get(secondIndex).get(frameIndex).remove(0);
        }
        return null;
    }

}

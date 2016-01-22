package com.marlin.tralp.tools;

/**
 * Created by gabriel on 16-01-22.
 */
public class Position {

    private static class Mypoint{

        public double x,y;

        public Mypoint(double x, double y){
            this.x = x;
            this.y = y;
        }
        public Mypoint(){
        }

        public double distX( Mypoint b){
            return b.x;
        }
    }

    public static int[] handPosition(double faceUL_x, double faceUL_y, double faceDR_x,
                                        double faceDR_y, double handUL_x, double handUL_y,
                                        double handDR_x, double handDR_y){

        int [] result = new int[3];
        Mypoint faceCenter = new Mypoint();
        Mypoint faceSidesize = new Mypoint();
        Mypoint handCenter = new Mypoint();
        Mypoint handSidesize = new Mypoint();

        faceCenter.x = (faceDR_x + faceUL_x)/2;
        faceCenter.y = (faceDR_y + faceUL_y)/2;

        faceSidesize.x = Math.abs(faceUL_x - faceDR_x);
        faceSidesize.y = Math.abs(faceUL_y - faceDR_y);

        handCenter.x = (handDR_x + handUL_x)/2;
        handCenter.y = (handDR_y + handUL_y)/2;

        handSidesize.x = Math.abs(handDR_x - handUL_x);
        handSidesize.y = Math.abs(handDR_y - handUL_y);

        double arbSum = faceSidesize.x/3;

        //Solve X
        if(handCenter.x <= (faceUL_x - faceSidesize.x))
            result[0] = 0;
        else if(handCenter.x >(faceUL_x - faceSidesize.x) &&
                handCenter.x <= faceUL_x)
            result[0] = 1;
        else if(handCenter.x > faceUL_x &&
                handCenter.x <= (faceUL_x + arbSum))
            result[0] = 2;
        else if(handCenter.x > (faceUL_x + arbSum) &&
                handCenter.x <= (faceDR_x - arbSum))
            result[0] = 3;
        else if(handCenter.x > (faceDR_x - arbSum) &&
                handCenter.x <= faceDR_x)
            result[0] = 4;
        else if(handCenter.x > faceDR_x &&
                handCenter.x < faceDR_x + faceSidesize.x)
            result[0] = 5;
        else
            result[0] = 6;

        //Solve y
        arbSum = faceSidesize.y/7 ;
        if(handCenter.y <= (faceUL_y - arbSum))
            result[1] = 0;
        else if(handCenter.y >(faceUL_y -arbSum) &&
                handCenter.y <= faceUL_y)
            result[1] = 1;
        else if(handCenter.y > faceUL_y &&
                handCenter.y <= (faceUL_y + 3*arbSum))
            result[1] = 2;
        else if(handCenter.y > (faceUL_y + 3*arbSum) &&
                handCenter.y <= (faceUL_y + 4*arbSum))
            result[1] = 3;
        else if(handCenter.y > (faceUL_y + 4*arbSum) &&
                handCenter.y <= (faceDR_y - 2*arbSum))
            result[1] = 4;
        else if(handCenter.y > (faceDR_y - 2*arbSum) &&
                handCenter.y <= (faceDR_y - arbSum))
            result[1] = 5;
        else if(handCenter.y > (faceDR_y - arbSum) &&
                handCenter.y <= faceDR_y)
            result[1] = 6;
        else if(handCenter.y > faceDR_y &&
                handCenter.y <= (faceDR_y + arbSum))
            result[1] = 7;
        else if(handCenter.y > (faceDR_y + arbSum) &&
                handCenter.y <= (faceDR_y + 5*arbSum) )
            result[1] = 8;
        else if(handCenter.y > (faceDR_y + 5*arbSum) &&
                handCenter.y <= (faceDR_y + 13*arbSum))
            result[1] = 9;
        else if(handCenter.y > (faceDR_y + 13*arbSum) &&
                handCenter.y <= (faceDR_y + 18*arbSum))
            result[1] = 10;
        else if(handCenter.y > (faceDR_y + 18*arbSum) &&
                handCenter.y <= (faceDR_y + 22*arbSum))
            result[1] = 11;
        else if(handCenter.y > (faceDR_y + 22*arbSum) &&
                handCenter.y <= (faceDR_y + 28*arbSum))
            result[1] = 12;
        else
            result[1] = 13;

        //Solve Z
        if ((handSidesize.x * handSidesize.y) < (faceSidesize.x * faceSidesize.y))
            result[2] = 0;
        else
            result[2] = 1;


        return result;
    }
}

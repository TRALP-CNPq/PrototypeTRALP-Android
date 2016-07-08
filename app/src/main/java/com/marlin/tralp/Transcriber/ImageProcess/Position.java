package com.marlin.tralp.Transcriber.ImageProcess;

/**
 * Created by aneves on 5/19/2016.
 */
public class Position
{
    public Point3D handPosition(double faceUL_x, double faceUL_y, double faceDR_x,
                              double faceDR_y, double handUL_x, double handUL_y,
                              double handDR_x, double handDR_y)
    {
        int [] result = new int[3];
        Point2D faceUpLeftPoint = new Point2D(faceUL_x, faceUL_y);
        Point2D faceDownRightPoint = new Point2D(faceDR_x, faceDR_y);
        Point2D handUpLeftPoint = new Point2D(handUL_x, handUL_y);
        Point2D handDownRightPoint = new Point2D(handDR_x, handDR_y);

        return this.calculateHandPosition(faceUpLeftPoint, faceDownRightPoint, handUpLeftPoint, handDownRightPoint);
    }

    public Point3D calculateHandPosition(Point2D faceUpLeftPoint, Point2D faceDownRightPoint, Point2D handUpLeftPoint, Point2D handDownRightPoint)
    {
        Point2D faceCenter = Point2D.CreateCenterPoint(faceUpLeftPoint, faceDownRightPoint);
        Point2D faceSideSize = Point2D.CreateSideSizePoint(faceUpLeftPoint, faceDownRightPoint);
        Point2D handCenter = Point2D.CreateCenterPoint(handUpLeftPoint, handDownRightPoint);
        Point2D handSidesize = Point2D.CreateSideSizePoint(handUpLeftPoint, handDownRightPoint);

        Point3D result = new Point3D();
        result.setX(this.getX(handCenter, faceSideSize, faceUpLeftPoint, faceDownRightPoint));
        result.setY(this.getY(handCenter, faceSideSize, faceUpLeftPoint, faceDownRightPoint));
        result.setZ(this.getZ(handSidesize,faceSideSize));

        return result;
    }

    private int getX(Point2D handCenter, Point2D faceSideSize, Point2D faceUpLeftPoint, Point2D faceDownRightPoint)
    {
        double arbSum = faceSideSize.getX() / 3;
        double arbSumX = faceSideSize.getX() / 5;
        int result = 6;

        if(handCenter.getX() <= (faceUpLeftPoint.getX() - faceSideSize.getX()))
            result = 0;
        else if(handCenter.getX() > (faceUpLeftPoint.getX() - faceSideSize.getX()) && handCenter.getX() <= faceUpLeftPoint.getX())
            result = 1;
        else if(handCenter.getX() > faceUpLeftPoint.getX() && handCenter.getX() <= (faceUpLeftPoint.getX() + (arbSumX*2)))
            result = 2;
        else if(handCenter.getX() > (faceUpLeftPoint.getX() + (arbSumX*2)) && handCenter.getX() <= (faceDownRightPoint.getX() - (arbSumX*3)))
            result = 3;
        else if(handCenter.getX() > (faceDownRightPoint.getX() - (arbSumX*3)) && handCenter.getX() <= faceDownRightPoint.getX())
            result = 4;
        else if(handCenter.getX() > faceDownRightPoint.getX() && handCenter.getX() < faceDownRightPoint.getX() + faceSideSize.getX())
            result = 5;

        return result;
    }

    private int getY(Point2D handCenter, Point2D faceSideSize, Point2D faceUpLeftPoint, Point2D faceDownRightPoint)
    {
        double arbSumY = faceSideSize.getY() / 7;
        int result = 13;

        if(handCenter.getY() <= (faceUpLeftPoint.getY() - arbSumY))
            result = 0;
        else if(handCenter.getY() > (faceUpLeftPoint.getY() - arbSumY) &&
                handCenter.getY() <= faceUpLeftPoint.getY())
            result = 1;
        else if(handCenter.getY() > faceUpLeftPoint.getY() &&
                handCenter.getY() <= (faceUpLeftPoint.getY() + (3*arbSumY)))
            result = 2;
        else if(handCenter.getY() > (faceUpLeftPoint.getY() + (3*arbSumY)) &&
                handCenter.getY() <= (faceUpLeftPoint.getY() + (4*arbSumY)))
            result = 3;
        else if(handCenter.getY() > (faceUpLeftPoint.getY() + 4*arbSumY) &&
                handCenter.getY() <= (faceDownRightPoint.getY() - 2*arbSumY))
            result = 4;
        else if(handCenter.getY() > (faceDownRightPoint.getY() - 2*arbSumY) &&
                handCenter.getY() <= (faceDownRightPoint.getY() - arbSumY))
            result = 5;
        else if(handCenter.getY() > (faceDownRightPoint.getY() - arbSumY) &&
                handCenter.getY() <= faceDownRightPoint.getY())
            result = 6;
        else if(handCenter.getY() > faceDownRightPoint.getY() &&
                handCenter.getY() <= (faceDownRightPoint.getY() + arbSumY))
            result = 7;
        else if(handCenter.getY() > (faceDownRightPoint.getY() + arbSumY) &&
                handCenter.getY() <= (faceDownRightPoint.getY() + 5*arbSumY) )
            result = 8;
        else if(handCenter.getY() > (faceDownRightPoint.getY() + 5*arbSumY) &&
                handCenter.getY() <= (faceDownRightPoint.getY() + 13*arbSumY))
            result = 9;
        else if(handCenter.getY() > (faceDownRightPoint.getY() + 13*arbSumY) &&
                handCenter.getY() <= (faceDownRightPoint.getY() + 18*arbSumY))
            result = 10;
        else if(handCenter.getY() > (faceDownRightPoint.getY() + 18*arbSumY) &&
                handCenter.getY() <= (faceDownRightPoint.getY() + 22*arbSumY))
            result = 11;
        else if(handCenter.getY() > (faceDownRightPoint.getY() + 22*arbSumY) &&
                handCenter.getY() <= (faceDownRightPoint.getY() + 28*arbSumY))
            result = 12;

        return result;
    }

    private int getZ(Point2D handSideSize, Point2D faceSideSize)
    {
        return ((handSideSize.getX() * handSideSize.getY()) < (faceSideSize.getX() * faceSideSize.getY())) ? 0 : 1;
    }
}





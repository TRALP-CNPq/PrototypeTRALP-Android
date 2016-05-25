package com.marlin.tralp.Transcriber.ImageProcess;

/**
 * Created by aneves on 5/20/2016.
 */
public class Point2D
{
    private double x, y;

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public Point2D()
    {
    }

    public Point2D(double x, double y)
    {
        this.x = x;
        this.y = y;
    }

    public static Point2D CreateCenterPoint(Point2D upLeftPoint, Point2D downRightPoint)
    {
        double _x = (downRightPoint.getX() + upLeftPoint.getX()) / 2;
        double _y = (downRightPoint.getY() + upLeftPoint.getY()) / 2;

        return new Point2D(_x , _y);
    }

    public static Point2D CreateSideSizePoint(Point2D upLeftPoint, Point2D downRightPoint)
    {
        double _x = Math.abs(upLeftPoint.getX() - downRightPoint.getX());
        double _y = Math.abs(upLeftPoint.getY() - downRightPoint.getY());

        return  new Point2D(_x, _y);
    }
}


package com.clefeflo.assistantdenavigation;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

/**
 * Created by Félix on 04/02/2015.
 */
public class Liaison {
    private String point1, point2;
    private int poids;
    private float X1, Y1, X2, Y2, startAngle, sweepAngle, radius;
    private RectF rectF;
    private byte type;

    public Liaison(String p1, String p2, int poids) {
        this.point1 = p1;
        this.point2 = p2;
        this.poids = poids;
    }

    public Liaison(String p1, String p2, int poids, float x1, float y1, float x2, float y2) {
        this.point1 = p1;
        this.point2 = p2;
        this.poids = poids;
        this.X1 = x1;
        this.Y1 = y1;
        this.X2 = x2;
        this.Y2 = y2;
        this.type = 1;
    }

    public Liaison(String p1, String p2, int poids, RectF rectF, float startAngle, float endAngle) {
        this.point1 = p1;
        this.point2 = p2;
        this.poids = poids;
        this.rectF = rectF;
        this.startAngle = 360 - startAngle;
        this.sweepAngle = startAngle - endAngle;
        this.type = 2;
    }

    public Liaison(String p1, String p2, int poids, float x1, float y1, float radius) {
        this.point1 = p1;
        this.point2 = p2;
        this.poids = poids;
        this.X1 = x1;
        this.Y1 = y1;
        this.radius = radius;
        this.type = 3;
    }

    public String getPoint1() {
        return this.point1;
    }

    public String getPoint2() {
        return this.point2;
    }

    public int getPoids() {
        return this.poids;
    }

    public int getType() {
        return this.type;
    }

    public Bitmap addCanvas(Bitmap bitmap, int extra) {
        Paint p = new Paint();
        Canvas canvas = new Canvas(bitmap);
        float point1X, point1Y, point2X, point2Y, centerX, centerY;
        double length = 70;
        switch (this.type) {
            case 1:
                p.setStyle(Paint.Style.STROKE);
                p.setStrokeWidth(10);
                p.setColor(Color.YELLOW);
                p.setTextSize(50);
                try {
                    canvas.drawLine(this.X1, this.Y1, this.X2, this.Y2, p);
                    System.out.println("Line drawed");
                } catch (Exception e) {
                    canvas.drawLine(0, 0, 10, 10, p);
                }
                p.setColor(Color.RED);
                centerX = (this.X1 + this.X2) / 2;
                centerY = (this.Y1 + this.Y2) / 2;
                if (extra == 0) {
                    point1X = (float) (((Math.cos(Math.acos((this.X1 - this.X2) / (Math.sqrt((this.X1 - this.X2) * (this.X1 - this.X2) + (this.Y1 - this.Y2) * (this.Y1 - this.Y2)))) + 0.5)) * length) + centerX);
                    point1Y = (float) (((Math.sin(Math.asin((this.Y1 - this.Y2) / (Math.sqrt((this.X1 - this.X2) * (this.X1 - this.X2) + (this.Y1 - this.Y2) * (this.Y1 - this.Y2)))) + 0.5)) * length) + centerY);
                    point2X = (float) (((Math.cos(Math.acos((this.X1 - this.X2) / (Math.sqrt((this.X1 - this.X2) * (this.X1 - this.X2) + (this.Y1 - this.Y2) * (this.Y1 - this.Y2)))) - 0.5)) * length) + centerX);
                    point2Y = (float) (((Math.sin(Math.asin((this.Y1 - this.Y2) / (Math.sqrt((this.X1 - this.X2) * (this.X1 - this.X2) + (this.Y1 - this.Y2) * (this.Y1 - this.Y2)))) - 0.5)) * length) + centerY);
                } else {
                    point1X = (float) (((Math.cos(Math.acos((this.X2 - this.X1) / (Math.sqrt((this.X1 - this.X2) * (this.X1 - this.X2) + (this.Y1 - this.Y2) * (this.Y1 - this.Y2)))) + 0.5)) * length) + centerX);
                    point1Y = (float) (((Math.sin(Math.asin((this.Y2 - this.Y1) / (Math.sqrt((this.X1 - this.X2) * (this.X1 - this.X2) + (this.Y1 - this.Y2) * (this.Y1 - this.Y2)))) + 0.5)) * length) + centerY);
                    point2X = (float) (((Math.cos(Math.acos((this.X2 - this.X1) / (Math.sqrt((this.X1 - this.X2) * (this.X1 - this.X2) + (this.Y1 - this.Y2) * (this.Y1 - this.Y2)))) - 0.5)) * length) + centerX);
                    point2Y = (float) (((Math.sin(Math.asin((this.Y2 - this.Y1) / (Math.sqrt((this.X1 - this.X2) * (this.X1 - this.X2) + (this.Y1 - this.Y2) * (this.Y1 - this.Y2)))) - 0.5)) * length) + centerY);
                }
                canvas.drawLine(centerX, centerY, point1X, point1Y, p);
                canvas.drawLine(centerX, centerY, point2X, point2Y, p);
                break;
            case 2:
                p.setStyle(Paint.Style.STROKE);
                p.setStrokeWidth(10);
                p.setColor(Color.YELLOW);
                p.setTextSize(70);
                try {
                    canvas.drawArc(this.rectF, this.startAngle, this.sweepAngle, false, p);
                    System.out.println("Arc drawed");
                } catch (Exception e) {
                    canvas.drawArc(new RectF(100, 100, 1605, 1176), 0, 90, false, p);
                }
                p.setColor(Color.RED);
                centerX = (float) (((this.rectF.right + this.rectF.left) / 2) + (Math.cos(Math.toRadians(this.startAngle + this.sweepAngle / 2)) * (this.rectF.right - this.rectF.left) / 2));
                centerY = (float) (((this.rectF.bottom + this.rectF.top) / 2) + (Math.sin(Math.toRadians(this.startAngle + this.sweepAngle / 2)) * (this.rectF.bottom - this.rectF.top) / 2));

                if (extra == 1) {
                    point1X = (float) (centerX + Math.cos(Math.toRadians(this.startAngle + sweepAngle / 2 - 45)) * length);
                    point1Y = (float) (centerY + Math.sin(Math.toRadians(this.startAngle + sweepAngle / 2 - 45)) * length);
                    point2X = (float) (centerX + Math.cos(Math.toRadians(this.startAngle + sweepAngle / 2 - 135)) * length);
                    point2Y = (float) (centerY + Math.sin(Math.toRadians(this.startAngle + sweepAngle / 2 - 135)) * length);
                } else {
                    point1X = (float) (centerX + Math.cos(Math.toRadians(this.startAngle + sweepAngle / 2 + 45)) * length);
                    point1Y = (float) (centerY + Math.sin(Math.toRadians(this.startAngle + sweepAngle / 2 + 45)) * length);
                    point2X = (float) (centerX + Math.cos(Math.toRadians(this.startAngle + sweepAngle / 2 + 135)) * length);
                    point2Y = (float) (centerY + Math.sin(Math.toRadians(this.startAngle + sweepAngle / 2 + 135)) * length);
                }
                canvas.drawLine(centerX, centerY, point1X, point1Y, p);
                canvas.drawLine(centerX, centerY, point2X, point2Y, p);
                break;
            case 3:
                p.setStyle(Paint.Style.FILL_AND_STROKE);
                p.setStrokeWidth(10);
                if (extra == 0) {
                    p.setColor(Color.RED);
                } else if (extra == 1) {
                    p.setColor(Color.GREEN);
                }
                try {
                    canvas.drawCircle(this.X1, this.Y1, this.radius, p);
                    System.out.println("Point drawed");
                } catch (Exception e) {
                    canvas.drawCircle(500, 500, 50, p);
                }
                break;
            default:
                p.setStyle(Paint.Style.FILL_AND_STROKE);
                p.setStrokeWidth(10);
                p.setColor(Color.BLUE);
                canvas.drawCircle(500, 500, 50, p);
                break;
        }
        return bitmap;
    }
}

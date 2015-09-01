package com.clefeflo.assistantdenavigation;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Félix on 04/02/2015.
 */
public class MapView extends View {
    static Bitmap bmp[] = {null}, bmpFloor0_1 = null, bmpFloor2_3 = null;
    static Canvas[] myCanvas = {null};
    //    Bitmap bmp = Bitmap.createBitmap(1705, 1276, Bitmap.Config.ARGB_8888);
    static Thread create;
    float ratio;

    public MapView(Context context, AttributeSet attrs) {
        super(context, attrs);

        create = new Thread() {
            @Override
            public void run() {
                bmpFloor0_1 = ((BitmapDrawable) getResources().getDrawable(R.drawable.floor0_1)).getBitmap();
                bmpFloor2_3 = ((BitmapDrawable) getResources().getDrawable(R.drawable.floor2_3)).getBitmap();
                System.out.println("bmp: " + bmpFloor0_1.getWidth() + " " + bmpFloor0_1.getHeight());

                myCanvas[0] = new Canvas(Bitmap.createScaledBitmap(bmpFloor0_1, 1705, 1276, false));
                ratio = (float) myCanvas[0].getHeight() / (float) myCanvas[0].getWidth();
                System.out.println("Canvas setted(" + myCanvas[0].getWidth() + "-" + myCanvas[0].getHeight() + ") Ratio: " + ratio);
            }
        };
        create.start();

    }

    public void onDraw(Canvas canvas) {
        while (create.isAlive()) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
//            System.out.println(canvas.getWidth() + " " + (int) (canvas.getWidth() * ratio) + " Ratio: " + ratio);
//            canvas.drawBitmap(bmp.createScaledBitmap(bmp, canvas.getWidth(), (int) ((float) canvas.getWidth() * 0.75), false), 0, 0, null);
//            canvas = myCanvas[selectedCanvas];
            float ratioX = ((float) this.getWidth()) / ((float) myCanvas[0].getWidth()), ratioY = ((float) this.getHeight()) / ((float) myCanvas[0].getHeight());

            if (ratioX < ratioY) {
                canvas.drawBitmap(Bitmap.createScaledBitmap(bmp[0], (int) (bmp[0].getHeight() * ratioX / ratio), (int) (bmp[0].getHeight() * ratioX), false), 0, 0, null);
            } else {
                canvas.drawBitmap(Bitmap.createScaledBitmap(bmp[0], (int) (bmp[0].getWidth() * ratioY), (int) (bmp[0].getWidth() * ratioY * ratio), false), 0, 0, null);
            }
        } catch (Exception e) {
            System.out.println("Bitmap not printed: " + e);
        }
    }

    public static void drawSingle(int[] pathNums, char floorSelected) {
        do {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }while (create.isAlive());

        try {
            bmp[0].recycle();
        }
        catch (Exception e){}


        if (floorSelected == '0' || floorSelected == '1') {
            bmp[0] = Bitmap.createScaledBitmap(bmpFloor0_1, 1705, 1276, false);
            System.out.println("bmp: " + bmp[0].getWidth() + " " + bmp[0].getHeight());
            myCanvas[0] = new Canvas(bmp[0]);
        } else if (floorSelected == '2' || floorSelected == '3') {
            bmp[0] = Bitmap.createScaledBitmap(bmpFloor2_3, 1705, 1276, false);
            System.out.println("bmp: " + bmp[0].getWidth() + " " + bmp[0].getHeight());
            myCanvas[0] = new Canvas(bmp[0]);
        }

        while (create.isAlive()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            for (int i = 0; i < pathNums.length; i++) {
                if (AddToMap.liaison[pathNums[i]].getPoint1().charAt(AddToMap.liaison[pathNums[i]].getPoint1().length() - 1) == floorSelected || AddToMap.liaison[pathNums[i]].getPoint2().charAt(AddToMap.liaison[pathNums[i]].getPoint2().length() - 1) == floorSelected) {
                    if (AddToMap.liaison[pathNums[i]].getPoint1().charAt(AddToMap.liaison[pathNums[i]].getPoint1().length() - 1) != AddToMap.liaison[pathNums[i]].getPoint2().charAt(AddToMap.liaison[pathNums[i]].getPoint2().length() - 1)) {
                        if (AddToMap.liaison[pathNums[i - 1]].getPoint1().charAt(AddToMap.liaison[pathNums[i - 1]].getPoint1().length() - 1) == floorSelected) {
                            bmp[0] = AddToMap.liaison[pathNums[i]].addCanvas(bmp[0], 0);
                        } else {
                            bmp[0] = AddToMap.liaison[pathNums[i]].addCanvas(bmp[0], 1);
                        }
                    } else {
                        if (i == 0) {
                            if (AddToMap.liaison[pathNums[i]].getPoint2().equals(AddToMap.liaison[pathNums[i + 1]].getPoint1()) || AddToMap.liaison[pathNums[i]].getPoint2().equals(AddToMap.liaison[pathNums[i + 1]].getPoint2())) {
                                bmp[0] = AddToMap.liaison[pathNums[i]].addCanvas(bmp[0], 0);
                            } else {
                                bmp[0] = AddToMap.liaison[pathNums[i]].addCanvas(bmp[0], 1);
                            }
                        } else {
                            if (AddToMap.liaison[pathNums[i]].getPoint1().equals(AddToMap.liaison[pathNums[i - 1]].getPoint1()) || AddToMap.liaison[pathNums[i]].getPoint1().equals(AddToMap.liaison[pathNums[i - 1]].getPoint2())) {
                                bmp[0] = AddToMap.liaison[pathNums[i]].addCanvas(bmp[0], 0);
                            } else {
                                bmp[0] = AddToMap.liaison[pathNums[i]].addCanvas(bmp[0], 1);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Erreur création bitmap unique: " + e);
        }
    }
}
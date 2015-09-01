package com.clefeflo.assistantdenavigation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class Main extends Activity implements View.OnClickListener {
    EditText startRoom, endRoom;
    int[] pathNums;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_menu);
        startRoom = (EditText) findViewById(R.id.startRoom);
        endRoom = (EditText) findViewById(R.id.endRoom);
        Button startCalcButton = (Button) findViewById(R.id.startCalcButton);
        startCalcButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        MapView view;
        switch (v.getId()) {
            case R.id.startCalcButton:
                int start, end;
                try {
                    start = Integer.parseInt(startRoom.getText().toString());
                } catch (Exception e) {
                    Toast.makeText(Main.this, R.string.enterStartRoom, Toast.LENGTH_LONG).show();
                    break;
                }
                try {
                    end = Integer.parseInt(endRoom.getText().toString());
                } catch (Exception e) {
                    Toast.makeText(Main.this, R.string.enterEndRoom, Toast.LENGTH_LONG).show();
                    break;
                }
                if (start == end) {
                    Toast.makeText(Main.this, R.string.sameRoom, Toast.LENGTH_LONG).show();
                    break;
                }

                try {
                    Integer.parseInt(Sector.getSector(start));
                    Toast.makeText(Main.this, R.string.wrongStartRoom, Toast.LENGTH_LONG).show();
                } catch (Exception a) {
                    try {
                        Integer.parseInt(Sector.getSector(end));
                        Toast.makeText(Main.this, R.string.wrongEndRoom, Toast.LENGTH_LONG).show();
                    } catch (Exception b) {
                        MyPath.calcPath(Sector.getSector(start), Sector.getSector(end));
                        System.out.println("calcPath OK");
                        setContentView(R.layout.map_screen);
                        pathNums = MyPath.getPathNums();
                        System.out.println("pathNums OK:");
                        for (int i = 0; i < pathNums.length; i++) {
                            System.out.println(pathNums[i] + " " + AddToMap.liaison[pathNums[i]].getPoint1() + " " + AddToMap.liaison[pathNums[i]].getPoint2() + " " + AddToMap.liaison[pathNums[i]].getType());
                        }

                        int tmpfloorStart = 0;
                        while (start >= 100) {
                            start = start - 100;
                            tmpfloorStart++;
                        }

                        char floorStart = Integer.toString(tmpfloorStart).charAt(0);
                        MapView.drawSingle(pathNums, floorStart);
                        switch (floorStart) {
                            case '0':
                                findViewById(R.id.floor0).setEnabled(false);
                                break;
                            case '1':
                                findViewById(R.id.floor1).setEnabled(false);
                                break;
                            case '2':
                                findViewById(R.id.floor2).setEnabled(false);
                                break;
                            case '3':
                                findViewById(R.id.floor3).setEnabled(false);
                                break;
                        }
                        System.out.println("MapView draw OK");
                        System.out.println("set view OK");
                        Button floor0 = (Button) findViewById(R.id.floor0);
                        floor0.setOnClickListener(this);
                        Button floor1 = (Button) findViewById(R.id.floor1);
                        floor1.setOnClickListener(this);
                        Button floor2 = (Button) findViewById(R.id.floor2);
                        floor2.setOnClickListener(this);
                        Button floor3 = (Button) findViewById(R.id.floor3);
                        floor3.setOnClickListener(this);
                        Button return_main = (Button) findViewById(R.id.return_main);
                        return_main.setOnClickListener(this);
                    }
                }
                break;

            case R.id.floor0:
                System.out.println("Bouton 0");
                view = (MapView) findViewById(R.id.mapView);
                MapView.drawSingle(pathNums, '0');
                findViewById(R.id.floor1).setEnabled(true);
                findViewById(R.id.floor2).setEnabled(true);
                findViewById(R.id.floor3).setEnabled(true);
                findViewById(R.id.floor0).setEnabled(false);
                view.invalidate();
                break;
            case R.id.floor1:
                System.out.println("Bouton 1");
                view = (MapView) findViewById(R.id.mapView);
                MapView.drawSingle(pathNums, '1');
                findViewById(R.id.floor0).setEnabled(true);
                findViewById(R.id.floor2).setEnabled(true);
                findViewById(R.id.floor3).setEnabled(true);
                findViewById(R.id.floor1).setEnabled(false);
                view.invalidate();
                break;
            case R.id.floor2:
                System.out.println("Bouton 2");
                view = (MapView) findViewById(R.id.mapView);
                MapView.drawSingle(pathNums, '2');
                findViewById(R.id.floor0).setEnabled(true);
                findViewById(R.id.floor1).setEnabled(true);
                findViewById(R.id.floor3).setEnabled(true);
                findViewById(R.id.floor2).setEnabled(false);
                view.invalidate();
                break;
            case R.id.floor3:
                System.out.println("Bouton 3");
                view = (MapView) findViewById(R.id.mapView);
                MapView.drawSingle(pathNums, '3');
                findViewById(R.id.floor0).setEnabled(true);
                findViewById(R.id.floor1).setEnabled(true);
                findViewById(R.id.floor2).setEnabled(true);
                findViewById(R.id.floor3).setEnabled(false);
                view.invalidate();
                break;
            case R.id.return_main:
                MapView.bmp[0].recycle();
                MapView.bmpFloor0_1.recycle();
                MapView.bmpFloor2_3.recycle();
                setContentView(R.layout.activity_select_menu);
                startRoom = (EditText) findViewById(R.id.startRoom);
                endRoom = (EditText) findViewById(R.id.endRoom);
                Button startCalcButton = (Button) findViewById(R.id.startCalcButton);
                startCalcButton.setOnClickListener(this);
                break;
        }
    }
}

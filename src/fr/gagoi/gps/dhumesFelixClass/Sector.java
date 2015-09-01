package com.clefeflo.assistantdenavigation;

/**
 * Created by Félix on 05/02/2015.
 */
public class Sector {
    static String[][] table = {
            {"", "", "B", "", "C", "", "C", "", "D", "", "D", "", "", "", "J", "", "K", "", "L", "", "L", "", "M", "D", "M", "D", "N", "D", "N", "D", "N", "D", "", "D", "", "E", "", "", "", "F", "", "F", "", "", "", "", "", "G", "", "K", "", "L", "", "M", "", "M"},
            {"B", "", "B", "", "C", "B", "C", "B", "C", "C", "D", "D", "D", "D", "E", "F", "F", "I", "G", "J", "I", "K", "I", "K", "K", "", "K", "L", "L", "L", "M", "L", "N", "M", "N", "M"},
            {"B", "", "C", "B", "", "", "D", "C", "E", "D", "F", "D", "G", "E", "I", "", "I", "E", "J", "F", "J", "G", "L", "G", "M", "H", "N", "H", "", "I", "", "I", "", "J", "", "K", "", "", "", "L", "", "L", "", "M", "", "M", "", "N"},
            {"A", "A", "A", "A", "B", "B", "B", "B", "C", "B", "D", "B", "E", "C", "E", "C", "G", "D", "H", "D", "H", "D", "I", "D", "J", "J", "E", "L", "E", "N", "K", "F", "M", "G", "", "G", "", "H", "", "H", "", "I", "", "I", "", "J", "", "K", "", "", "", "L", "", "L", "", "L", "", "M", "", "N", "", "N"}
    };

    public static String getSector(int room) {
        int floor;
        if (room >= 400) {
            floor = 10;
        } else if (room > 300) {
            floor = 3;
            room -= 301;
        } else if (room > 200) {
            floor = 2;
            room -= 201;
        } else if (room > 100) {
            floor = 1;
            room -= 101;
        } else {
            floor = 0;
            room -= 1;
        }


        try {
            return (table[floor][room] + floor);
        } catch (Exception e) {
            return ("0");
        }
    }
}
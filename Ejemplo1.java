package com.example.meta1_1;

public class Ejemplo1 {
    public static void main(String[] args) {
        double x=1;
        double y=1;
        while (x<255) {
            while (y<65535) {
                double w=((4+4)*x*(1+y))*(0.0000000625);
                if(w==1.5){
                    System.out.println(x+" "+y);
                }
                y++;
            }
            y=1;
            x++;
        }
    }
}

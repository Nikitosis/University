package com.lab1;

public class Main {

    private static double EPSILON = 0.0001;
    private static int iter = 0;

    private static double getEqResult(double x) {
        return x*x*x*x - 2*x*x*x + x*x -2*x + 1;
    }

    private static double getDerResult(double x) {
        return 4*x*x*x - 6*x*x + 2*x - 2;
    }

    private static double divisionMethod(double start, double end, double epsilon) {
        iter++;
        if(Math.abs(end - start) <= epsilon) {
            return start + (end - start)/2;
        }

        double mid = start + (end - start)/2;
        double midResult = getEqResult(mid);
        double startResult = getEqResult(start);
        double endResult = getEqResult(end);

        //if middle point is the root
        if(midResult == 0) {
            return mid;
        }

        //signs of start result and mid result<0 then root is between start and mid
        if(startResult * midResult < 0) {
            return divisionMethod(start, mid, epsilon);
        } else {
            //otherwise root is between mid and end
            return divisionMethod(mid, end, epsilon);
        }
    }

    private static double secandMethod(double start, double end, double epsilon) {
        iter++;
        if(Math.abs(end - start) <= epsilon) {
            return start + (end - start)/2;
        }

        double next = end - ((end - start)*getEqResult(end))/(getEqResult(end) - getEqResult(start));

        return secandMethod(end, next, epsilon);
    }



    public static void main(String[] args) {
        System.out.println("Division method:");
        iter = 0;
        System.out.println("First root: x=" + divisionMethod(0, 1, EPSILON));
        System.out.println("Iterations: " + iter);
        iter = 0;
        System.out.println("Second root: x=" + divisionMethod(1, 2, EPSILON));
        System.out.println("Iterations: " + iter);

        System.out.println("Secand method:");
        iter = 0;
        System.out.println("First root: x=" + secandMethod(0, 1, EPSILON));
        System.out.println("Iterations: " + iter);
        iter = 0;
        System.out.println("Second root: x=" + secandMethod(1, 2, EPSILON));
        System.out.println("Iterations: " + iter);
    }
}

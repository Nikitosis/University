package com.lab2;

import org.ejml.simple.SimpleMatrix;

public class Second {
    public static void main(String[] args) {
        SimpleMatrix A = new SimpleMatrix(
                new double[][] {
                        new double[] {1d, 3d, -2d, 0d, -2d},
                        new double[] {3d, 4d, -5d, 1d, -3d},
                        new double[] {-2d, -5d, 3d, -2d, 2},
                        new double[] {0d, 1d, -2d, 5d, 3d},
                        new double[] {-2d, -3d, 2d, 3d, 4d}
                }
        );
        SimpleMatrix b = new SimpleMatrix(
                new double[][] {
                        new double[] {0.5, 5.4, 5.0, 7.5, 3.3},
                }
        ).transpose();
        double epsilon = 1e-3;

        //сходимость
        for(int i=0;i<A.numCols();i++) {
            double sum=0;
            for(int j=0;j<A.numRows();j++) {
                if(i==j) {
                    continue;
                }
                sum+=Math.abs(A.get(i,j));
            }
            if(Math.abs(A.get(i,i)) <= sum) {
                System.out.println("Метод Якобі не сходиться");
                return;
            }
        }

        SimpleMatrix B = new SimpleMatrix(A.numRows(), A.numCols());

        for(int i=0;i<A.numRows();i++) {
            for(int j=0;j<A.numCols();j++) {
                if(i==j) {
                    continue;
                }
                B.set(i, j, -A.get(i,j)/A.get(i,i));
            }
        }

        SimpleMatrix d = new SimpleMatrix(b.numRows(), 1);

        for(int i=0;i<b.numRows();i++) {
            d.set(i, b.get(i)/A.get(i,i));
        }


        SimpleMatrix x = new SimpleMatrix(b.numRows(), 1);

        //x(0)
        for(int i=0;i<d.numRows();i++) {
            x.set(i, d.get(i));
        }

        int iter = 0;
        while(true) {
            x.print();
            iter ++;
            SimpleMatrix prevX = x;
            x = B.mult(prevX).plus(d);

            SimpleMatrix difference = x.minus(prevX);
            double curNorm = 0;
            for(int i=0;i<x.numRows();i++) {
                if(Math.abs(difference.get(i)) > curNorm) {
                    curNorm = Math.abs(difference.get(i));
                }
            }

            if(curNorm<epsilon) {
                break;
            }
        }

        x.print();

        System.out.println("It took iterations: " + iter);
    }
}

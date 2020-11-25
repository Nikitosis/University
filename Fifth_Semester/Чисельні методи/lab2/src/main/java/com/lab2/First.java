package com.lab2;

import org.ejml.simple.SimpleMatrix;

public class First {
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

        SimpleMatrix S = new SimpleMatrix(A.numCols(), A.numRows());
        SimpleMatrix D = new SimpleMatrix(A.numCols(), A.numRows());

        for(int i=0;i<A.numRows();i++) {
            double sumP = A.get(i, i);
            for(int p=0;p<=i-1;p++) {
                sumP -= S.get(p, i)*S.get(p, i)*D.get(p, p);
            }

            D.set(i, i, Math.signum(sumP));
            S.set(i, i, Math.sqrt(Math.abs(sumP)));

            for(int j = i+1;j<A.numCols();j++) {
                double curSumP = A.get(i, j);
                for(int p=0; p<=i-1;p++) {
                    curSumP -= S.get(p, i)*D.get(p, p)*S.get(p, j);
                }
                S.set(i,j, curSumP/(D.get(i, i)*S.get(i, i)));
            }
        }

        SimpleMatrix StD = S.transpose().mult(D);

        SimpleMatrix Y = new SimpleMatrix(A.numCols(), 1);

        //зворотній хід метод гауса зверху вниз
        for(int i=0;i<StD.numRows();i++) {
            double curSum = b.get(i);
            for(int k=0;k<=i-1;k++) {
                curSum -= StD.get(i, k)*Y.get(k);
            }
            Y.set(i, curSum/StD.get(i, i));
        }

        SimpleMatrix X = new SimpleMatrix(A.numCols(), 1);

        //зворотній хід метода гауса знизу вгору
        for(int i=S.numRows()-1;i>=0;i--) {
            double curSum = Y.get(i);
            for(int k=i+1;k<S.numCols();k++) {
                curSum -= S.get(i, k)*X.get(k);
            }
            X.set(i, curSum/S.get(i, i));
        }

        X.print();
    }
}

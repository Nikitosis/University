package com.lab3;

import org.ejml.simple.SimpleMatrix;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        double alpha = 1.5;
        SimpleMatrix A = new SimpleMatrix(
                new double[][] {
                        new double[] {5.18 + alpha, 1.12, 0.95, 1.32, 0.83},
                        new double[] {1.12, 4.28 - alpha, 2.12, 0.57, 0.91},
                        new double[] {0.95, 2.12, 6.13 + alpha, 1.29, 1.57},
                        new double[] {1.32, 0.57, 1.29, 4.57 - alpha, 1.25},
                        new double[] {0.83, 0.91, 1.57, 1.25, 5.21 + alpha}
                }
        );

        //stepen(A, 1e-4);
        stepenObern(A, 1e-4);
    }

    private static void stepen(SimpleMatrix A, double eps) {
        int iteration = 1;
        SimpleMatrix y = new SimpleMatrix(A.numRows(), 1);
        for(int i=0; i<y.getNumElements(); i++) {
            y.set(i, 1);
        }

        SimpleMatrix newY = A.mult(y);
        double alpha = newY.get(0) / y.get(0);
        while(true) {
            SimpleMatrix z = A.mult(newY);
            newY = z.divide(getNorm(z));

            double newAlpha = z.get(0) / y.get(0);

            if(Math.abs(newAlpha - alpha) <= eps) {
                break;
            }

            alpha = newAlpha;
            y = newY;
            iteration++;
        }

        System.out.println("Ітерацій: " + iteration);
        System.out.println("Максимальне власне значення: " + alpha);
        System.out.println("Власний вектор: ");
        newY.print();
    }

    private static void stepenObern(SimpleMatrix A, double eps) {
        A = A.invert();
        int iteration = 1;
        SimpleMatrix y = new SimpleMatrix(A.numRows(), 1);
        for(int i=0; i<y.getNumElements(); i++) {
            y.set(i, 1);
        }

        SimpleMatrix newY = A.mult(y);
        double alpha = newY.get(0) / y.get(0);
        while(true) {
            SimpleMatrix z = A.mult(newY);
            newY = z.divide(getNorm(z));

            double newAlpha = z.get(0) / y.get(0);

            if(Math.abs(newAlpha - alpha) <= eps) {
                break;
            }

            alpha = newAlpha;
            y = newY;
            iteration++;
        }

        System.out.println("Ітерацій: " + iteration);
        System.out.println("Мінімальне власне значення: " + 1/alpha);
        System.out.println("Власний вектор: ");
        newY.print();
    }

    private static double getNorm(SimpleMatrix m) {
        double result = 0;
        for(int i=0;i<m.getNumElements();i++) {
            result += Math.abs(m.get(i));
        }
        return result;
    }

    private static void yakobi(SimpleMatrix A, double eps) {

        int iteration = 1;
        List<SimpleMatrix> listU = new ArrayList<>();
        while(true) {
            System.out.println("Итерация номер " + iteration);
            System.out.println("Матрица A(i)");
            A.print();
            //Выбираем максимальный по модулю внедиагональный элемент матрицы
            double maxElem = 0;
            int maxElemI = 0;
            int maxElemJ = 0;
            for(int j=0; j<A.numCols(); j++) {
                for(int i=0; i<j; i++) {
                    if(Math.abs(A.get(i, j)) > maxElem) {
                        maxElem = Math.abs(A.get(i ,j));
                        maxElemI = i;
                        maxElemJ = j;
                    }
                }
            }

            System.out.println("Максимальный элемент: " + maxElem);
            System.out.println("I: " + maxElemI);
            System.out.println("J: " + maxElemJ);

            //Находим соответствующую этому элементу матрицу вращения:
            SimpleMatrix U = SimpleMatrix.identity(A.numRows());

            //Ноходим фи
            double phi = 0.5 * Math.atan(2*A.get(maxElemI, maxElemJ) / (A.get(maxElemI, maxElemI) - A.get(maxElemJ, maxElemJ)));

            System.out.println("Phi = " + phi);

            //Считаем элементы матрицы вращения
            U.set(maxElemI, maxElemI, Math.cos(phi));
            U.set(maxElemJ, maxElemI, Math.sin(phi));
            U.set(maxElemI, maxElemJ, -Math.sin(phi));
            U.set(maxElemJ, maxElemJ, Math.cos(phi));

            System.out.println("Матрица U");
            U.print();

            listU.add(U);

            //Вычисляем матрицу A(i+1)
            A = U.transpose().mult(A.mult(U));

            System.out.println("Матрица A(i+1)");
            A.print();

            //Проверить окончание итерационного процесса
            double res = 0;
            for(int j=0; j<A.numRows(); j++) {
                for(int i=0; i<j; i++) {
                    res += A.get(i, j) * A.get(i, j);
                }
            }

            res = Math.sqrt(res);

            if(res <= eps) {
                break;
            }

            iteration++;
        }

        System.out.println("Собственные значения:");
        for(int i=0; i<A.numRows(); i++) {
            System.out.println(A.get(i, i));
        }

        SimpleMatrix finalU = listU.stream().reduce((a, b) -> a.mult(b)).get();

        System.out.println("Загальна матриця");
        finalU.print();

        for(int j=0;j<finalU.numCols();j++) {
            System.out.println(j + " собственный вектор");
            for(int i=0;i<finalU.numRows();i++) {
                System.out.println(finalU.get(i, j));
            }
        }
    }
}

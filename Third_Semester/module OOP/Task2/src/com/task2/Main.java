package com.task2;


import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

public class Main {
    private static final Logger log=Logger.getLogger(Main.class.getName());

    public static void main(String[] args){
        System.out.println(f(12));
        System.out.println(f(-12));
        System.out.println(f(12.5));
        System.out.println(f("Hi123"));
        System.out.println(f(new Pair<>(12,13.5)));

        List<Object> objects=new ArrayList<>();
        objects.add(12);
        objects.add(13.5);
        objects.add(19);
        System.out.println(f(objects));

        System.out.println(f(new Object()));
    }

    public static Object f(Object x){
        if(x instanceof Integer){
            log.info("Proceeding Integer");
            Integer n=(Integer)x;
            if(n>=0)
                return Math.cos(Math.toRadians(5*n+156));
            else
                return Math.log(Math.abs(n*n*n*n*n-256))/Math.log(10);
        }
        if(x instanceof Double){
            log.info("Proceeding Double");
            Double n=(Double)x;
            return n/(n-356);
        }
        if(x instanceof String){
            log.info("Proceeding String");
            String s=(String)x;
            int symbols=0;
            int englishSymbols=0;
            for(int i=0;i<s.length();i++){
                symbols++;
                if((s.charAt(i)>'a' && s.charAt(i)<'z') ||
                (s.charAt(i)>'A' && s.charAt(i)<'Z')){
                    englishSymbols++;
                }
            }
            double k=(double)symbols/englishSymbols;
            return k*k*k;

        }
        if(x instanceof Pair){
            log.info("Proceeding Pair");
            Pair<Object,Object> pair=(Pair)x;
            Object a=pair.getKey();
            Object b=pair.getValue();
            return (Double)f(b) * Math.sin(Math.toRadians(556.0*(Double)f(a)));
        }

        if(x instanceof List){
            log.info("Proceeding List");
            List v=(List)x;
            double maxVal=-1;
            for(int i=0;i<v.size();i++){
                String elem=v.get(i).toString();
                Double doubleElem=Double.parseDouble(elem);
                double res=Math.sin(Math.toRadians(doubleElem-656));
                if(res>maxVal)
                    maxVal=res;
            }
            return maxVal;
        }

        log.info("Proceeding default");
        return 17.56;
    }
}

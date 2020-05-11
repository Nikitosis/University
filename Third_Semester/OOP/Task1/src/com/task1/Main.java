package com.task1;


import com.task1.work.*;

import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    public static void main(String[] args){
        Defect defect1=new Defect(0.01,0.05,1.2,1.3);
        Defect defect2=new Defect(0.01,0.5,1.5,1.6);

        Function function1=new Function(true,12);
        Function function2=new Function(1,10);

        ComponentType type1=new ComponentType(Arrays.asList(defect1),Arrays.asList(function1));
        ComponentType type2=new ComponentType(Arrays.asList(defect1,defect2),Arrays.asList(function2));

        Component component1=new Component(type1,100,10,2,new ArrayList<>());
        Component component2=new Component(type2,100,10,1,Arrays.asList(component1));


        ComplexSystem system=new ComplexSystem(Arrays.asList(component2),Arrays.asList(function1,function2));
        system.startUntilTotalSpendingsLess(100);

        System.out.println(Component.getSubComponentsWithType(component2,type1));
    }
}

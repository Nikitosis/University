package com.task1.work;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

public class ComplexSystem {
    private static final Logger log=Logger.getLogger(ComplexSystem.class.getName());

    private List<Component> mainComponents;
    private List<Function> functions;

    private Set<Function> notWorkingFunctions;
    private Set<Function> repairingFunctions;

    private int totalSpendings;
    private int totalReward;

    public ComplexSystem(List<Component> mainComponents, List<Function> functions) {
        this.mainComponents = mainComponents;
        this.functions = functions;
    }

    private void initState(){
        notWorkingFunctions=new HashSet<>();
        repairingFunctions=new HashSet<>();
        totalSpendings=0;
        totalReward=0;
    }

    public void startUntilTimeSpent(int time){
        log.info("startUntilTimeSpent started");

        initState();
        for(int i=0;i<time;i++)
            proceedSystem();

        log.info("startUntilTimeSpent finished");
        log.info("Total reward: "+totalReward);
        log.info("Total spendings: "+totalSpendings);
    }

    public void startUntilTotalSpendingsLess(int maxSpendings){
        log.info("startUntilTotalSpendingsLess started");

        initState();
        while(totalSpendings<maxSpendings){
            proceedSystem();
        }

        log.info("startUntilTotalSpendingsLess finished");
        log.info("Total reward: "+totalReward);
        log.info("Total spendings: "+totalSpendings);
    }

    private void proceedSystem(){
        log.info("Started proceeding work");

        log.info("Executing components work method");
        for(Component component:mainComponents){
            component.work();
        }

        for(Function function:functions){
            log.info("Checking function work status");
            if(!isFunctionWorking(function)) {
                log.info("Function is not working");
                if(!isInterceptedByStoppedComponents(function)) {
                    log.info("Function is not working because it has broken components");
                    log.info("Stopping function components");
                    stopComponentsWithFunction(function);
                    notWorkingFunctions.add(function);
                }
            }
            else{
                log.info("Function has worked fine. Adding reward. ");
                totalReward+=function.getReward();
            }
        }

        for(Function notWorkingFunction:notWorkingFunctions){
            log.info("Trying to identify defect in function components");
            if(tryIdentifyDefectInComponentsWithFunction(notWorkingFunction)){
                log.info("Defect is identified. Starting repair.");
                totalSpendings+=startRepairingComponentsWithFunction(notWorkingFunction);
                repairingFunctions.add(notWorkingFunction);
            }
        }

        for(Function repairingFunction:repairingFunctions){
            log.info("Proceeding function repair");
            proceedRepairingComponentsWithFunction(repairingFunction);
            if(isAllComponentsRepairedWithFunction(repairingFunction)){
                log.info("All repairing components are now repaired. Starting function components.");
                startComponentsWithFunction(repairingFunction);
                repairingFunctions.remove(repairingFunction);
                notWorkingFunctions.remove(repairingFunction);
            }
        }

        log.info("Ended proceeding work\n\n");
    }

    private boolean isFunctionWorking(Function function){
        int components=0;
        int normalWorkingComponents=0;
        for(Component component:mainComponents){
            components+=component.getComponentsWithFunction(function);
            normalWorkingComponents+=component.getNormalWorkingComponentsWithFunction(function);
        }

        if(components!=normalWorkingComponents && function.isAllComponentsRequired())
            return false;

        if(normalWorkingComponents<function.getMinComponentsRequired())
            return false;

        return true;
    }

    private void stopComponentsWithFunction(Function function){
        for(Component component:mainComponents){
            component.stopComponentsWithFunction(function);
        }
    }

    private boolean tryIdentifyDefectInComponentsWithFunction(Function function){
        boolean isDefectIdentified=false;
        for(Component component:mainComponents){
            isDefectIdentified |=component.tryIdentifyDefectInComponentsWithFunction(function);
        }

        return isDefectIdentified;
    }

    private int startRepairingComponentsWithFunction(Function function){
        int repairPrice=0;
        for(Component component:mainComponents){
            repairPrice+=component.startRepairingComponentsWithFunction(function);
        }
        return repairPrice;
    }

    private void proceedRepairingComponentsWithFunction(Function function){
        for(Component component:mainComponents){
            component.proceedRepairingComponentsWithFunction(function);
        }
    }

    private boolean isAllComponentsRepairedWithFunction(Function function){
        boolean isAllRepaired=true;
        for(Component component:mainComponents){
            isAllRepaired &=component.isAllComponentsRepairedWithFunction(function);
        }
        return isAllRepaired;
    }

    private void startComponentsWithFunction(Function function){
        for(Component component:mainComponents){
            component.startComponentsWithFunction(function);
        }
    }

    //if this function is not working because of stopped components
    private boolean isInterceptedByStoppedComponents(Function function){
        int components=0;
        int possibleWorkingComponents=0;
        for(Component component:mainComponents){
            components+=component.getComponentsWithFunction(function);
            possibleWorkingComponents+=component.getPossibleWorkingComponentsWithFunction(function);
        }

        if(components!=possibleWorkingComponents && function.isAllComponentsRequired())
            return false;

        if(possibleWorkingComponents<function.getMinComponentsRequired())
            return false;

        return true;
    }

}

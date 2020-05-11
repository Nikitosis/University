package com.task1.work;

import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

public class Component {
    private static final Logger log=Logger.getLogger(ComplexSystem.class.getName());
    private static double repairProbability=0.5;

    private ComponentType componentType;
    private int price;
    private int baseRepairPrice;
    private int baseRepairTime;
    private List<Component> subComponents;

    private Defect occuredDefect;
    private boolean isStopped;
    private boolean isDefectIdentified;
    private boolean isBeingRepaired;
    private int repairTimeLeft;

    public Component(ComponentType componentType, int price, int baseRepairPrice, int baseRepairTime, List<Component> subComponents) {
        this.componentType = componentType;
        this.price = price;
        this.baseRepairPrice = baseRepairPrice;
        this.baseRepairTime = baseRepairTime;
        this.subComponents = subComponents;
    }

    public ComponentType getComponentType() {
        return componentType;
    }

    public int getPrice() {
        return price;
    }

    public List<Component> getSubComponents() {
        return subComponents;
    }

    public boolean isStopped() {
        return isStopped;
    }

    public int getRepairTimeLeft() {
        return repairTimeLeft;
    }

    public void work(){
        log.info("Component work() method started "+this);

        if(isStopped) {
            log.info("Component stopped. "+this);
            return;
        }

        occuredDefect=getOccuredDefect();

        workSubComponents();

        log.info("Component work() method finished "+this);
    }

    public int getRepairPrice(){
        if(occuredDefect==null)
            return 0;
        return (int)occuredDefect.getRepairPriceKoef()*baseRepairPrice;
    }

    public int getRepairTime(){
        if(occuredDefect==null)
            return 0;
        return (int) occuredDefect.getRepairTimeKoef()*baseRepairTime;
    }

    public void startRepair(){
        log.info("Starting repair "+this);
        isBeingRepaired =true;
        repairTimeLeft=getRepairTime();
    }

    public void proceedRepair(){
        log.info("Proceeding repair "+this);
        repairTimeLeft--;

        if(repairTimeLeft<=0){
            finishRepair();
        }
    }

    public int getComponentsWithFunction(Function function){
        int subEntryAmount=0;
        for(Component component:subComponents){
            subEntryAmount+=component.getComponentsWithFunction(function);
        }
        if(componentType.getFunctions().contains(function))
            subEntryAmount++;
        return subEntryAmount;
    }

    public int getNormalWorkingComponentsWithFunction(Function function){
        if(isStopped)
            return 0;

        int result=0;
        for(Component component:subComponents){
            result+=component.getNormalWorkingComponentsWithFunction(function);
        }
        if(occuredDefect==null && componentType.getFunctions().contains(function))
            result++;

        return result;
    }

    //regardless isStopped
    public int getPossibleWorkingComponentsWithFunction(Function function){
        int result=0;
        for(Component component:subComponents){
            result+=component.getPossibleWorkingComponentsWithFunction(function);
        }
        if(occuredDefect==null && componentType.getFunctions().contains(function))
            result++;

        return result;
    }

    public void stopComponentsWithFunction(Function function){
        for(Component component:subComponents){
            component.stopComponentsWithFunction(function);
        }

        if(componentType.getFunctions().contains(function)) {
            log.info("Component is stopped "+ this);
            isStopped = true;
        }
    }

    public void startComponentsWithFunction(Function function){
        for(Component component:subComponents){
            component.startComponentsWithFunction(function);
        }

        if(componentType.getFunctions().contains(function)) {
            log.info("Component is started "+this);
            isStopped = false;
        }
    }

    public boolean tryIdentifyDefectInComponentsWithFunction(Function function){
        boolean isDefectIdentified=false;
        for(Component component:subComponents)
        {
            isDefectIdentified |=component.tryIdentifyDefectInComponentsWithFunction(function);
        }

        if(componentType.getFunctions().contains(function) && isStopped)
            isDefectIdentified |=tryIdentifyDefect();

        return isDefectIdentified;
    }

    //returns repair price
    public int startRepairingComponentsWithFunction(Function function){
        int repairPrice=0;
        for(int i=0;i<subComponents.size();i++)
        {
            if(shouldBeRepairedToStartFunction(subComponents.get(i),function)) {
                log.info("Component should be repaired");
                repairPrice += subComponents.get(i).startRepairingComponentsWithFunction(function);
            }
            else
            {
                log.info("Component is changed");
                Component changedComponent=subComponents.get(i);
                repairPrice+=changedComponent.price;
                subComponents.set(i,new Component(changedComponent.componentType,
                                                    changedComponent.price,
                                                    changedComponent.baseRepairPrice,
                                                    changedComponent.baseRepairTime,
                                                    changedComponent.subComponents)
                );
            }
        }

        if(componentType.getFunctions().contains(function) && isDefectIdentified && !isBeingRepaired){
            startRepair();
            repairPrice+=getRepairPrice();
        }

        return repairPrice;
    }

    //whether component should be repaired. Otherwise, we should buy new one
    public static boolean shouldBeRepairedToStartFunction(Component component,Function function){
        return component.getRepairTime()*function.getReward()>component.price;
    }

    public static int getSubComponentsWithType(Component component,ComponentType type){
        int sum=0;
        for(Component subComponent:component.subComponents){
            sum+=getSubComponentsWithType(subComponent,type);
        }
        if(component.componentType==type)
            sum++;
        return sum;
    }



    public void proceedRepairingComponentsWithFunction(Function function){
        for(Component component:subComponents)
        {
            component.proceedRepairingComponentsWithFunction(function);
        }

        if(componentType.getFunctions().contains(function) && isBeingRepaired)
            proceedRepair();
    }

    public boolean isAllComponentsRepairedWithFunction(Function function){
        boolean isAllRepaired=true;
        for(Component component:subComponents)
        {
            isAllRepaired&=component.isAllComponentsRepairedWithFunction(function);
        }
        if(componentType.getFunctions().contains(function) && isBeingRepaired)
            return true;
        return isAllRepaired;
    }


    private boolean tryIdentifyDefect(){
        if(occuredDefect==null)
            return false;

        if(new Random().nextDouble()<=occuredDefect.getIdentifyProbability()) {
            isDefectIdentified=true;
            return true;
        }

        return false;
    }

    private void finishRepair(){
        log.info("Finishing repair");

        repairTimeLeft=0;
        occuredDefect=null;
        isDefectIdentified=false;
        isBeingRepaired=false;
    }

    private Defect getOccuredDefect(){
        for(Defect defect:componentType.getDefects()){
            boolean isDefectOccur=new Random().nextDouble()<=defect.getOccurProbability();
            if(isDefectOccur){
                log.info("Defect occured.");
                return defect;
            }
        }
        log.info("Defect not occured");
        return null;
    }

    private void workSubComponents(){
        for(Component component:subComponents)
            component.work();
    }
}

import com.algorithms.AlgorithmFactory;
import com.structures.CircularList;
import com.structures.Iterator;
import com.tracking.Tracker;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Main {
    public static void main(String[] args) {
//        Employee emp1 = new Employee.Builder()
//                .name("Vasya")
//                .surname("Pupkin")
//                .build();
//        Employee emp2 = new Employee.Builder()
//                .name("Zhenya")
//                .surname("Mihaylov")
//                .build();
//
//        Unit taxiUnit = new Unit.Builder()
//                .name("Taxi")
//                .employees(Arrays.asList(emp1, emp2))
//                .build();
//
//        Organization infrostructureOrganization = new Organization.Builder()
//                .name("Infrostructure")
//                .units(Arrays.asList(taxiUnit))
//                .build();
//
//        Ministry economyMinistry = new Ministry.Builder()
//                .name("Ministry of economy")
//                .organizations(Arrays.asList(infrostructureOrganization))
//                .build();
//
//        System.out.println(economyMinistry.toJson());
        CircularList<Integer> list = new CircularList<>();
        list.addFirst(5);
        list.addLast(10);
        list.addLast(3);

        Comparator<Integer> comparator = (a,b)->{
            if(a<b){
                return -1;
            } else if(a.equals(b)) {
                return 0;
            } else {
                return 1;
            }
        };
        Tracker.trackMemory(
                () -> {
                    list.sort(AlgorithmFactory.selectionSort(Integer.class), comparator);
//                    List<Integer> l = new ArrayList<>();
//                    for(int i=0;i<10000;i++) {
//                        l.add(i);
//                    }
                }
        );
        Iterator iterator = list.iterator();
        for(int i=0;i<3;i++) {
            System.out.println(iterator.get());
            iterator.next();
        }

    }
}

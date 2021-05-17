package com.module;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.Scanner;

public class ClientRmiTaskN {
    public static void main(String[] args) throws RemoteException, NotBoundException {
        Scanner scanner = new Scanner(System.in);
        Registry registry = LocateRegistry.getRegistry(9001);
        RemoteInterface stub = (RemoteInterface) registry.lookup("RemoteInterface");

        while(true) {
            System.out.println("Write your command:");
            System.out.println("1 <day> <audience> = Get teachers working in day and audience");
            System.out.println("2 <day> = Get teachers not working in day");
            System.out.println("3 <lessonsAmount> = Get days with specified amount of lessons");
            System.out.println("4 <audiencesAmount> = Get days where specified amount of audiences are busy");
            String[] input =scanner.nextLine().split(" ");
            String commandName = input[0];

            switch (commandName) {
                case "1":{
                    GetTeachersWorkingInDayAndAudience request = new GetTeachersWorkingInDayAndAudience();
                    request.setDayInWeek(DayInWeek.valueOf(input[1]));
                    request.setAudience(Integer.valueOf(input[2]));

                    List<Teacher> response = stub.getTeachersWorkingInDayAndAudience(request.getDayInWeek(), request.getAudience());
                    System.out.println(response);
                    break;
                }
                case "2":{
                    GetTeachersNotWorkingInDay request = new GetTeachersNotWorkingInDay();
                    request.setDayInWeek(DayInWeek.valueOf(input[1]));

                    List<Teacher> response = stub.getTeachersNotWorkingInDay(request.getDayInWeek());
                    System.out.println(response);
                    break;
                }
                case "3":{
                    GetDaysWithAmountLessons request = new GetDaysWithAmountLessons();
                    request.setLessonsAmount(Integer.valueOf(input[1]));

                    List<DayInWeek> response = stub.getDaysWithAmountLessons(request.getLessonsAmount());
                    System.out.println(response);
                    break;
                }
                case "4":{
                    GetDaysWhereAudiencesBusy request = new GetDaysWhereAudiencesBusy();
                    request.setAudiencesAmount(Integer.valueOf(input[1]));

                    List<DayInWeek> response = stub.getDaysWhereAudiencesBusy(request.getAudiencesAmount());
                    System.out.println(response);
                    break;
                }
                default:{
                    System.out.println("No such command");
                }
            }

        }
    }
}

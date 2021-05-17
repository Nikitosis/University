package com.module;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;

public class ClientSocketTaskN {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        SocketClient socketClient = new SocketClient("localhost", 9002);
        try {
            socketClient.connect();
        } catch (IOException e) {
            e.printStackTrace();
        }

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

                    List<Teacher> response = (List<Teacher>) socketClient.sendRequest(request);
                    System.out.println(response);
                    break;
                }
                case "2":{
                    GetTeachersNotWorkingInDay request = new GetTeachersNotWorkingInDay();
                    request.setDayInWeek(DayInWeek.valueOf(input[1]));

                    List<Teacher> response = (List<Teacher>) socketClient.sendRequest(request);
                    System.out.println(response);
                    break;
                }
                case "3":{
                    GetDaysWithAmountLessons request = new GetDaysWithAmountLessons();
                    request.setLessonsAmount(Integer.valueOf(input[1]));

                    List<DayInWeek> response = (List<DayInWeek>) socketClient.sendRequest(request);
                    System.out.println(response);
                    break;
                }
                case "4":{
                    GetDaysWhereAudiencesBusy request = new GetDaysWhereAudiencesBusy();
                    request.setAudiencesAmount(Integer.valueOf(input[1]));

                    List<DayInWeek> response = (List<DayInWeek>) socketClient.sendRequest(request);
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

class SocketClient {
    private String ip;
    private Integer port;
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    public SocketClient(String ip, Integer port) {
        this.ip = ip;
        this.port = port;
    }

    public void connect() throws IOException {
        socket = new Socket(ip, port);
    }

    public Object sendRequest(Object request) {
        try {
            sendMessage(request);
            return receiveMessage();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("Error sending request");
        }
    }

    public Object receiveMessage() throws IOException, ClassNotFoundException {
        if(in == null) {
            in = new ObjectInputStream(socket.getInputStream());
        }
        Object response = in.readObject();

        return response;
    }

    public void sendMessage(Object request) throws IOException {
        if(out == null) {
            out = new ObjectOutputStream(socket.getOutputStream());
        }
        out.writeObject(request);
    }

    public void close() throws IOException {
        socket.close();
    }
}

package com.module;

import lombok.Data;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.stream.Collectors;

@Data
class GetTeachersWorkingInDayAndAudience implements Serializable {
    private DayInWeek dayInWeek;
    private Integer audience;
}

@Data
class GetTeachersNotWorkingInDay implements Serializable{
    private DayInWeek dayInWeek;
}

@Data
class GetDaysWithAmountLessons implements Serializable {
    private Integer lessonsAmount;
}

@Data
class GetDaysWhereAudiencesBusy implements Serializable{
    private Integer audiencesAmount;
}

public class ServerSocketTaskN {
    private final static DAOtaskN dao = DAOtaskN.INSTANCE;

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(9002);
            while(true) {
                System.out.println("Waiting for connection");
                Socket socket = serverSocket.accept();
                System.out.println("Connection established. Listening");
                //after somebody connected, we are waiting for requests
                new Thread(() -> {
                    try {
                        handleRequests(socket);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void handleRequests(Socket socket) throws IOException, ClassNotFoundException {
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());

        while (true) {
            try {
                Object inObject = in.readObject();
                if (inObject instanceof GetTeachersWorkingInDayAndAudience) {
                    GetTeachersWorkingInDayAndAudience request = (GetTeachersWorkingInDayAndAudience) inObject;
                    List<Teacher> teachers = dao.findTeachersByDayAndAudience(request.getDayInWeek(), request.getAudience());
                    out.writeObject(teachers);
                }
                if (inObject instanceof GetTeachersNotWorkingInDay) {
                    GetTeachersNotWorkingInDay request = (GetTeachersNotWorkingInDay) inObject;
                    List<Teacher> teachers = dao.findTeachersNotWorkingInDay(request.getDayInWeek());
                    out.writeObject(teachers);
                }
                if (inObject instanceof GetDaysWithAmountLessons) {
                    GetDaysWithAmountLessons request = (GetDaysWithAmountLessons) inObject;
                    List<DayInWeek> dayInWeeks = dao.findDaysWithAmountLessons(request.getLessonsAmount());
                    out.writeObject(dayInWeeks);
                }
                if (inObject instanceof GetDaysWhereAudiencesBusy) {
                    GetDaysWhereAudiencesBusy request = (GetDaysWhereAudiencesBusy) inObject;
                    List<DayInWeek> dayInWeeks = dao.findDaysWithAudiencesBusy(request.getAudiencesAmount());
                    out.writeObject(dayInWeeks);
                }
            } catch (RuntimeException e) {
                out.writeObject("ERROR");
            }
        }
    }
}

package com.module;

import java.rmi.AlreadyBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

interface RemoteInterface extends Remote {
    List<Teacher> getTeachersWorkingInDayAndAudience(DayInWeek dayInWeek, Integer audience) throws RemoteException;
    List<Teacher> getTeachersNotWorkingInDay(DayInWeek dayInWeek) throws RemoteException;
    List<DayInWeek> getDaysWithAmountLessons(Integer lessonsAmount) throws RemoteException;
    List<DayInWeek> getDaysWhereAudiencesBusy(Integer audiencesAmount) throws RemoteException;
}

class RemoteInterfaceImpl implements RemoteInterface {
    private final static DAOtaskN dao = DAOtaskN.INSTANCE;

    @Override
    public List<Teacher> getTeachersWorkingInDayAndAudience(DayInWeek dayInWeek, Integer audience) {
        return dao.findTeachersByDayAndAudience(dayInWeek, audience);
    }

    @Override
    public List<Teacher> getTeachersNotWorkingInDay(DayInWeek dayInWeek) {
        return dao.findTeachersNotWorkingInDay(dayInWeek);
    }

    @Override
    public List<DayInWeek> getDaysWithAmountLessons(Integer lessonsAmount) {
        return dao.findDaysWithAmountLessons(lessonsAmount);
    }

    @Override
    public List<DayInWeek> getDaysWhereAudiencesBusy(Integer audiencesAmount) {
        return dao.findDaysWithAudiencesBusy(audiencesAmount);
    }
}

public class ServerRmiTaskN {
    public static void main(String[] args) throws RemoteException, AlreadyBoundException {
        RemoteInterfaceImpl remoteInterface = new RemoteInterfaceImpl();
        RemoteInterface stub = (RemoteInterface) UnicastRemoteObject.exportObject(remoteInterface, 9001);
        Registry registry = LocateRegistry.createRegistry(9001);
        registry.rebind("RemoteInterface", stub);
        System.err.println("Server ready");
    }
}

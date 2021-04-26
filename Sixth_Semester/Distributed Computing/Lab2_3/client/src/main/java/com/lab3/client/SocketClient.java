package com.lab3.client;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SocketClient {
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

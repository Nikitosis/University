package com.lab3.server;

import com.lab3.server.controller.Controller;

public class ServerApplication {
    public static void main(String[] args) {
        Controller controller = new Controller();
        controller.start();
    }
}

package controller;

import configuration.SecurityManager;
import entities.dao.User;
import entities.request.UserCreateRequest;
import entities.response.UserResponse;
import mappers.UserMapper;
import service.UserService;
import utils.RequestUtil;
import utils.ResponseUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet({"/user"})
public class UserResource extends HttpServlet {

    private UserService userService = UserService.INSTANCE;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = SecurityManager.getAuthorizedUserId();
        User user = userService.getById(id);

        UserResponse userResponse = UserMapper.INSTANCE.toUserResponse(user);
        ResponseUtil.sendResponse(resp, userResponse);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserCreateRequest request = RequestUtil.getRequestObject(req, UserCreateRequest.class);

        User user = userService.create(request);

        UserResponse userResponse = UserMapper.INSTANCE.toUserResponse(user);
        ResponseUtil.sendResponse(resp, userResponse);
    }
}

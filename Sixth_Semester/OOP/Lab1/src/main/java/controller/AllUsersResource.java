package controller;

import entities.dao.User;
import entities.response.UserResponse;
import mappers.UserMapper;
import service.UserService;
import utils.ResponseUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet({"/users"})
public class AllUsersResource extends HttpServlet {

    private UserService userService = UserService.INSTANCE;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<User> users = userService.getAll();
        List<UserResponse> responses = users.stream()
                .map(UserMapper.INSTANCE::toUserResponse)
                .collect(Collectors.toList());

        ResponseUtil.sendResponse(resp, responses);
    }
}

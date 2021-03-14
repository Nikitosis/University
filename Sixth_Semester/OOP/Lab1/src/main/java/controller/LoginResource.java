package controller;

import entities.request.CreditCardTopUpRequest;
import entities.request.LoginRequest;
import entities.response.TokenResponse;
import service.AuthorizationService;
import service.CreditCardService;
import utils.RequestUtil;
import utils.ResponseUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.HttpMethod;
import java.io.IOException;

@WebServlet({"/login"})
public class LoginResource extends HttpServlet {
    private AuthorizationService authorizationService = AuthorizationService.INSTANCE;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LoginRequest request = RequestUtil.getRequestObject(req, LoginRequest.class);

        String authToken = authorizationService.authorize(request.getUsername(), request.getPassword());

        TokenResponse response = new TokenResponse();
        response.setToken(authToken);

        ResponseUtil.sendResponse(resp, response);
    }
}

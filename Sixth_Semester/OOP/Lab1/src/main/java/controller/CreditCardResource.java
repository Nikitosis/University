package controller;

import configuration.SecurityManager;
import entities.dao.CreditCard;
import entities.request.CreditCardCreateRequest;
import entities.response.CreditCardResponse;
import mappers.CreditCardMapper;
import service.CreditCardService;
import utils.RequestUtil;
import utils.ResponseUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet({"/credit-card"})
public class CreditCardResource extends HttpServlet {
    private CreditCardService creditCardService = CreditCardService.INSTANCE;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CreditCardCreateRequest request = RequestUtil.getRequestObject(req, CreditCardCreateRequest.class);
        Long id = SecurityManager.getAuthorizedUserId();

        CreditCard creditCard = creditCardService.create(id, request);

        CreditCardResponse response = CreditCardMapper.INSTANCE.toResponse(creditCard);

        ResponseUtil.sendResponse(resp, response);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = Long.parseLong(req.getParameter("id"));

        CreditCard creditCard = creditCardService.getById(id);

        CreditCardResponse response = CreditCardMapper.INSTANCE.toResponse(creditCard);

        ResponseUtil.sendResponse(resp, response);
    }
}

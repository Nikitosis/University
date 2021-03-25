package controller;

import configuration.SecurityManager;
import entities.dao.CreditCard;
import entities.dao.User;
import entities.response.CreditCardResponse;
import entities.response.UserResponse;
import mappers.CreditCardMapper;
import mappers.UserMapper;
import service.CreditCardService;
import utils.ResponseUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/user/credit-card")
public class MyCardsResource extends HttpServlet {

    private CreditCardService creditCardService = CreditCardService.INSTANCE;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = SecurityManager.getAuthorizedUserId();
        List<CreditCard> creditCards = creditCardService.getByUserId(id);

        List<CreditCardResponse> creditCardResources = creditCards.stream()
                .map(creditCard -> CreditCardMapper.INSTANCE.toResponse(creditCard))
                .collect(Collectors.toList());

        ResponseUtil.sendResponse(resp, creditCardResources);
    }
}

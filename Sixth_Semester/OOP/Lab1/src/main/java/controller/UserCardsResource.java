package controller;

import configuration.SecurityManager;
import entities.dao.CreditCard;
import entities.request.CreditCardTransferRequest;
import entities.request.GetUserCardsRequest;
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
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/specificUser/credit-cards")
public class UserCardsResource extends HttpServlet {

    private CreditCardService creditCardService = CreditCardService.INSTANCE;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        GetUserCardsRequest request = RequestUtil.getRequestObject(req, GetUserCardsRequest.class);
        Long id = request.getUserId();

        List<CreditCard> creditCards = creditCardService.getByUserId(id);

        List<CreditCardResponse> creditCardResources = creditCards.stream()
                .map(creditCard -> CreditCardMapper.INSTANCE.toResponse(creditCard))
                .collect(Collectors.toList());

        ResponseUtil.sendResponse(resp, creditCardResources);
    }
}
package controller;

import entities.request.CreditCardBlockRequest;
import entities.request.CreditCardTopUpRequest;
import service.CreditCardService;
import utils.RequestUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet({"/credit-card/top-up"})
public class CreditCardTopUpResource extends HttpServlet {

    private CreditCardService creditCardService = CreditCardService.INSTANCE;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CreditCardTopUpRequest request = RequestUtil.getRequestObject(req, CreditCardTopUpRequest.class);

        creditCardService.topUp(request.getCardId(), request.getAmount());
    }
}

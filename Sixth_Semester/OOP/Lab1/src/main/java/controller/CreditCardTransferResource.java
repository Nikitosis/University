package controller;

import entities.request.CreditCardCreateRequest;
import entities.request.CreditCardTransferRequest;
import service.CreditCardService;
import utils.RequestUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet({"/credit-card/transfer"})
public class CreditCardTransferResource extends HttpServlet {
    private CreditCardService creditCardService = CreditCardService.INSTANCE;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CreditCardTransferRequest request = RequestUtil.getRequestObject(req, CreditCardTransferRequest.class);

        creditCardService.transferMoney(request);
    }
}

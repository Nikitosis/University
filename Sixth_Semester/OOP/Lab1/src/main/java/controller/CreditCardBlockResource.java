package controller;

import entities.request.CreditCardBlockRequest;
import entities.request.CreditCardTransferRequest;
import service.CreditCardService;
import utils.RequestUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet({"/credit-card/block"})
public class CreditCardBlockResource extends HttpServlet {
    private CreditCardService creditCardService = CreditCardService.INSTANCE;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CreditCardBlockRequest request = RequestUtil.getRequestObject(req, CreditCardBlockRequest.class);

        creditCardService.block(request.getId());
    }
}

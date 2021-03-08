package controller;

import entities.request.CreditCardTopUpRequest;
import entities.request.CreditCardUnblockRequest;
import service.CreditCardService;
import utils.RequestUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet({"/credit-card/unblock"})
public class CreditCardUnblockResource extends HttpServlet {
    private CreditCardService creditCardService = CreditCardService.INSTANCE;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CreditCardUnblockRequest request = RequestUtil.getRequestObject(req, CreditCardUnblockRequest.class);

        creditCardService.unblock(request.getId());
    }
}

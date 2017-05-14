package org.smart4j.chapter2.controller;

import org.smart4j.chapter2.service.CustomerService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by ZDD on 2017/5/13.
 */
@WebServlet("/customer")
public class CustomerServlet extends HttpServlet{
    private CustomerService customerService;

    @Override
    public void init() throws ServletException {
        customerService = new CustomerService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("customerList", customerService.getCustomerList());
        req.getRequestDispatcher("WEB-INF/view/customer.jsp").forward(req, resp);
    }
}

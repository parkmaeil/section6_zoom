package com.example.controller;

import com.example.entity.BookDTO;
import com.example.entity.BookImageDTO;
import com.example.repository.BookDAOMyBatis;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class BookUpdateGetController implements  Controller {
    @Override
    public String requestHandler(HttpServletRequest req, HttpServletResponse resp)
                                                                                                                 throws ServletException, IOException {
        int num=Integer.parseInt(req.getParameter("num")); // "3"->3
        BookDAOMyBatis dao=new BookDAOMyBatis();
        BookImageDTO book=dao.bookView(num);

        req.setAttribute("book",book);
       // RequestDispatcher rd=req.getRequestDispatcher("/WEB-INF/views/update.jsp");
       // rd.forward(req, resp);
        return "update";
    }
}

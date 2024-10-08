package com.example.controller;

import com.example.entity.BookDTO;
import com.example.repository.BookDAOMyBatis;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// POJO
public class BookImageGetController  implements  Controller{
   // ?book_num=17
    @Override
    public String requestHandler(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        int book_num=Integer.parseInt(req.getParameter("book_num"));

        BookDAOMyBatis dao=new BookDAOMyBatis();
        BookDTO dto=dao.bookView(book_num);
        req.setAttribute("book", dto);

        return "image"; // image.jsp, ${book.num} , ${book.title}
    }
}

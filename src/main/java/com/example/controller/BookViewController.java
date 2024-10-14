package com.example.controller;

import com.example.entity.BookDTO;
import com.example.entity.BookImageDTO;
import com.example.entity.ImageDTO;
import com.example.repository.BookDAOMyBatis;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class BookViewController implements  Controller {
    @Override
    public String requestHandler(HttpServletRequest req, HttpServletResponse resp)
                                                                                                throws ServletException, IOException {

        int num=Integer.parseInt(req.getParameter("num"));

        BookDAOMyBatis dao = new BookDAOMyBatis();
        BookImageDTO book = dao.bookView(num);

        req.setAttribute("book", book);

        // type=2인 이미지 정보를 가져오기
        List<ImageDTO> imglist=dao.getImages(num); // 17
        req.setAttribute("imglist", imglist);

        //RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/views/view.jsp");
        //rd.forward(req, resp);
        return "view";
    }
}

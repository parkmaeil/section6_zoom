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

// POJO
public class BookImageGetController  implements  Controller{
   // ?book_num=17
    @Override
    public String requestHandler(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        int book_num=Integer.parseInt(req.getParameter("book_num"));

        BookDAOMyBatis dao=new BookDAOMyBatis();
        BookImageDTO dto=dao.bookView(book_num);
        req.setAttribute("book", dto);

        // 책에 대한 이미지 정보를 가져와서 뷰에 넘겨줘야 한다.(중요)
        List<ImageDTO>  mlist=dao.imageList(book_num);
        req.setAttribute("mlist", mlist);

        return "image"; // image.jsp, ${book.num} , ${book.title}
    }
}

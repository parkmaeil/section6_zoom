package com.example.controller;

import com.example.entity.BookDTO;
import com.example.entity.BookImageDTO;
import com.example.repository.BookDAOMyBatis;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class BookListController  implements  Controller{ // POJO
       public String requestHandler(HttpServletRequest req, HttpServletResponse resp)
                                                           throws ServletException, IOException {
        //BookDAO dao=new BookDAO();
        //Model과연동하는 부분
        BookDAOMyBatis dao=new BookDAOMyBatis();

        List<BookImageDTO> list=dao.bookList();
       // 객체 바인딩 기술
        req.setAttribute("list", list); // ${list}

        // View와 연동하는 부분
        // forward, dispatcher
        //RequestDispatcher rd=req.getRequestDispatcher("/WEB-INF/views/list.jsp");
        //rd.forward(req, resp); //-------------------------------------------| ${list}
        return "list"; //  /WEB-INF/views/list.jsp
    }
}

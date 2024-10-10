package com.example.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

public class DownloadController implements  Controller{
    @Override
    public String requestHandler(HttpServletRequest req, HttpServletResponse resp)
             throws ServletException, IOException {
         // ?book_num,path
        String book_num=req.getParameter("book_num");
        String filePath=req.getParameter("path");

        String uploadDirectory = req.getServletContext().getRealPath("/uploads");
        String imagePath=uploadDirectory+ File.separator+book_num+File.separator+filePath;
        File file = new File(imagePath);
        if(file.exists()){
            resp.setContentType("image/jpeg");
            resp.setContentLength((int)file.length());
            try(FileInputStream ins=new FileInputStream(file);
                  OutputStream outputStream=resp.getOutputStream()){
                   byte[] buffer=new byte[4096];
                   int bytesRead=-1;
                   while ( (bytesRead=ins.read(buffer))!=-1){
                        outputStream.write(buffer,0, bytesRead);
                   }
                   //outputStream.close();
            }
        }else{
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "File not found");
        }
        return null;
    }
}

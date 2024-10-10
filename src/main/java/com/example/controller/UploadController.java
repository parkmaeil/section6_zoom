package com.example.controller;

import com.example.entity.ImageDTO;
import com.example.repository.BookDAOMyBatis;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.print.Book;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class UploadController implements  Controller{
    @Override
    public String requestHandler(HttpServletRequest req, HttpServletResponse resp)
                                    throws ServletException, IOException {
        // 예외처리
        try{
            DiskFileItemFactory factory=new DiskFileItemFactory();
            ServletFileUpload upload=new ServletFileUpload(factory);
            List<FileItem> items=upload.parseRequest(req); // FormData(files(1),files(2)..., book_num, type)
            int book_num=0;
            int type=0;
            for(FileItem item  : items){
                System.out.println(item.toString());  // ???
                if(item.isFormField()){
                      if(item.getFieldName().equals("book_num")){
                            book_num=Integer.parseInt(item.getString());
                      }else if(item.getFieldName().equals("type")){
                            type=Integer.parseInt(item.getString());
                      }
                }
            }
            // 파일 업로드 하기 : D:\bootcamp2\section6\src\main\webapp
            String uploadPath=req.getServletContext().getRealPath("/uploads");
            String uploadRealPath=uploadPath+ File.separator+book_num;
            File uploadDir=new File(uploadRealPath);
            if(!uploadDir.exists()){
                  uploadDir.mkdir(); // 디렉토리가 생성
            }
            BookDAOMyBatis dao=new BookDAOMyBatis();
            for(FileItem item : items){
                if(!item.isFormField()){ // 파일인 경우
                     String fileName= FilenameUtils.getName(item.getName());
                     String filePath=uploadDir+File.separator+fileName;
                      File uploadFile=new File(filePath);
                      item.write(uploadFile); // 쓰기(업로드)

                    ImageDTO imageDTO=new ImageDTO();
                    imageDTO.setBook_num(book_num);
                    imageDTO.setType(type);
                    imageDTO.setPath(fileName);
                    dao.saveImage(imageDTO);  // 이미지 정보 저장
                }
            }//
            // 업로드와 데이터베이스에 정보 저장 이후....(Servlet)
            resp.setContentType("application/json;charset=utf-8"); // JSON : {  key:value, key:value.....  }
            PrintWriter out=resp.getWriter();
            String jsonResponse = "{\"status\":\"success\", \"message\":\"File uploaded successfully.\"}";
            out.println(jsonResponse);
            out.flush();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null; // fetch()----------->then()
    }
}

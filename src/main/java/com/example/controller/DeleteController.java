package com.example.controller;

import com.example.entity.ImageDTO;
import com.example.repository.BookDAOMyBatis;
import com.example.repository.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

public class DeleteController implements  Controller {
    @Override
    public String requestHandler(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        int id=Integer.parseInt(req.getParameter("id"));
        try{
            boolean isDeleted=deleteImageById(id, req); // true, false
            if (isDeleted) {
                // 성공적으로 삭제된 경우, HTTP 상태 코드 200 반환
                resp.setStatus(HttpServletResponse.SC_OK); // ok=true
                resp.getWriter().write("Image deleted successfully");
            } else {
                // 이미지 삭제 실패 (예: id에 해당하는 이미지가 없는 경우)
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // ok=false
                resp.getWriter().write("Failed to delete image");
            }
        }catch (Exception e){
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // ok=false
            resp.getWriter().write("Failed to delete image");
        }
        return null;
    }
    // 이미지 삭제 메서드 구현
    public boolean deleteImageById(int id, HttpServletRequest req) {
        SqlSession session = MyBatisUtil.openSession();
        try {
            BookDAOMyBatis dao = new BookDAOMyBatis();
            ImageDTO dto = dao.getImagePathById(id); // 이미지 정보 조회
            String uploadDirectory = req.getServletContext().getRealPath("/uploads");
            String imagePath = uploadDirectory + File.separator + dto.getBook_num() + File.separator + dto.getPath();

            // 파일 시스템에서 이미지 파일 삭제 시도
            File imageFile = new File(imagePath);
            if (imageFile.exists() && imageFile.isFile()) {
                if (!imageFile.delete()) { // 파일 삭제 실패 시
                    System.out.println("이미지 파일 삭제 실패");
                    return false;
                }
            }
            // 파일 삭제 성공 후 데이터베이스에서 이미지 정보 삭제
            int count = session.delete("com.example.repository.BookDAO.deleteImageById", id);
            if (count > 0) {
                session.commit(); // 데이터베이스 변경 사항 커밋
                System.out.println("이미지 정보 삭제 성공");
                return true;
            } else {
                System.out.println("해당 ID의 이미지 정보가 데이터베이스에 없습니다.");
                session.rollback(); // 이미지 정보가 없으므로 롤백
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            session.rollback(); // 예외 발생 시 롤백
            return false;
        } finally {
            session.close();
        }
    }
}
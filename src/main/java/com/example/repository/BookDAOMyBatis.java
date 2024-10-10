package com.example.repository;

import com.example.entity.BookDTO;
import com.example.entity.ImageDTO;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class BookDAOMyBatis { // 리팩토링 => 중앙집중식 관리, 코드의 간결성, 자원의관리의 효율성

        public List<BookDTO> bookList(){
            try(SqlSession session=MyBatisUtil.openSession()){ // close()
                 return session.selectList("com.example.repository.BookDAO.bookList");
            }
        // try 블록이 종료되면 session은 자동으로 close 됩니다.
       }

       public int bookDelete(int num){
             try(SqlSession session=MyBatisUtil.openSession()){
                   int cnt=session.delete("bookDelete", num);
                   session.commit(); // 완료
                   return cnt;
             }
       }

       public int bookInsert(BookDTO dto){
           try(SqlSession session=MyBatisUtil.openSession()){
               int cnt=session.insert("bookInsert", dto);
               session.commit(); // 완료
               return cnt;
           }
       }

       public BookDTO bookView(int num){
           try(SqlSession session=MyBatisUtil.openSession()){
               BookDTO dto=session.selectOne("bookView", num);
               return dto;
           }
       }

    public int bookUpdate(BookDTO dto) {
        try(SqlSession session=MyBatisUtil.openSession()){
            int cnt=session.insert("bookUpdate", dto);
            session.commit(); // 완료
            return cnt;
        }
    }
    public void saveImage(ImageDTO imageDTO) {
        try(SqlSession session=MyBatisUtil.openSession()){
            session.insert("saveImage", imageDTO);
            session.commit(); // 완료
        }
    }
}

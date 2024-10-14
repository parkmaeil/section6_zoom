<%@ page contentType="text/html;charset=UTF-8" language="java"
pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="cpath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="en">
<head>
  <title>Bootstrap Example</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/css/bootstrap.min.css">
  <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js"></script>

 <style>
 .carousel-item img {
     max-height: 400px; /* 이미지의 최대 높이를 제한 */
     object-fit: cover; /* 이미지가 컨테이너를 가득 채우면서 비율을 유지하도록 설정 */
     margin: 0 auto; /* 이미지를 수평 중앙에 배치 */
 }

 </style>

  </head>
<body>

<div class="container mt-5">
    <h2>Web MVC Framework Advanced</h2>
    <div class="card">
       <div class="row g-0">
        <!-- 이미지 슬라이더를 위한 컬럼 -->
        <div class="col-md-6">
            <div id="carouselExampleIndicators" class="carousel carousel-dark slide" data-bs-ride="carousel">
                <ol class="carousel-indicators">
                    <li data-bs-target="#carouselExampleIndicators" data-bs-slide-to="0" class="active"></li>
                    <li data-bs-target="#carouselExampleIndicators" data-bs-slide-to="1"></li>
                    <li data-bs-target="#carouselExampleIndicators" data-bs-slide-to="2"></li>
                </ol>
                <div class="carousel-inner">
                   <c:forEach var="img" items="${imglist}" varStatus="status">
                       <div class="carousel-item ${status.index==0 ? 'active' : ''}">
                           <img src="${cpath}/download.do?book_num=${book.num}&path=${img.path}" class="d-block mx-auto" alt="...">
                       </div>
                   </c:forEach>1
                </div>
                <a class="carousel-control-prev" href="#carouselExampleIndicators" role="button" data-bs-slide="prev">
                    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                    <span class="visually-hidden">Previous</span>
                </a>
                <a class="carousel-control-next" href="#carouselExampleIndicators" role="button" data-bs-slide="next">
                    <span class="carousel-control-next-icon" aria-hidden="true"></span>
                    <span class="visually-hidden">Next</span>
                </a>
            </div>
        </div>

        <!-- 상세보기 내용을 위한 컬럼 -->
        <div class="col-md-6">
              <div class="card-body">
                    <h5 class="card-title">Book Details</h5>
                    <table class="table table-bordered">
                        <tr><td>번호</td><td>${book.num}</td></tr>
                        <tr><td>제목</td><td>${book.title}</td></tr>
                        <tr><td>가격</td><td>${book.price}</td></tr>
                        <tr><td>저자</td><td>${book.author}</td></tr>
                        <tr><td>페이지수</td><td>${book.page}</td>
                        </tr>
                    </table>
                    <button class="btn btn-sm btn-primary action">목록</button>
                    <button class="btn btn-sm btn-success action">수정</button>
                    <button class="btn btn-sm btn-warning action">삭제</button>

                    <!-- 여기부터 수량 조정 스텝퍼, 총 금액, 장바구니 담기 버튼 추가 -->
                            <div class="d-flex align-items-center gap-2 mt-3">
                                <button class="btn btn-outline-secondary btn-sm" type="button">-</button>
                                <input type="text" id="quantity" name="quantity" value="1" class="form-control text-center" style="width: 60px;">
                                <button class="btn btn-outline-secondary btn-sm" type="button">+</button>
                            </div>
                            <h5 class="mt-3">총 금액: <span id="total-price">${book.price}</span>원</h5>
                            <button class="btn btn-danger mt-3 d-block w-100"  id="add-to-cart">장바구니 담기</button>

                </div>
            </div>
        </div>
        <div class="row mt-3">
            <div class="col">
                <div class="card">
                    <div class="card-body" style="display: flex; justify-content: center; align-items: center; flex-direction: column;">
                        <h5 class="card-title">세부 이미지 제목</h5>
                        <p class="card-text">이 이미지에 대한 추가 설명이나 정보를 여기에 포함할 수 있습니다.</p>
                         <img align="center" src="${cpath}/download.do?book_num=${book.num}&path=${book.path}" style="height:600px;width: 500px;" alt="Detail Image">
                    </div>
                </div>
            </div>
        </div>
    <div class="card-footer">인프런_마프 1탄_박매일</div>
 </div>
</div>
    <form id="myForm" method="post" action="">
         <input type="hidden" name="num" id="num" value="${book.num}"/>
    </form>
 <script>
       document.querySelector(".card-body").addEventListener("click", function(e){
         if(e.target.classList.contains("action")){
              let form=document.getElementById("myForm");
              let inputNum=document.getElementById("num");
              switch(e.target.classList[2]){
                  case "btn-primary" :
                       console.log("목록버튼클릭");
                       form.action="${cpath}/list.do"
                       if(inputNum){
                           inputNum.remove();
                       }
                       form.method="GET";
                       break;
                  case "btn-success" :
                      console.log("수정버튼클릭");
                      form.action="${cpath}/updateGet.do"
                      form.method="POST";
                      break;
                  case "btn-warning" :
                       console.log("삭제버튼클릭");
                       form.action="${cpath}/delete.do"
                       form.method="POST";
                       break;
              }
              form.submit();
         }
       });
   </script>
</body>
</html>
<%-- 
    Document   : login_register
    Created on : May 24, 2025, 10:50:54 PM
    Author     : DO NGOC ANH HE180661
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                background: #f9f9f9;
                display: flex;
                justify-content: center;
                align-items: flex-start;
                padding: 50px;
            }

            .wrapper {
                display: flex;
                background: #fff;
                padding: 30px;
                box-shadow: 0 0 10px rgba(0,0,0,0.1);
                border-radius: 10px;
                gap: 50px;
            }

            .form-container {
                width: 600px;

            }

            h2 {
                margin-bottom: 17px;
                color: #FF6B6B;
                font-size: 40px;
            }

            .form-group {
                margin-bottom: 15px;
            }

            label {
                font-weight: bold;
            }

            input[type="text"],
            input[type="email"],
            input[type="password"],
            input[type="date"] {
                width: 100%;
                padding: 10px;
                margin-top: 5px;
                border-radius: 6px;
                border: 1px solid #ccc;
            }

            .gender-group {
                margin-top: 5px;
            }

            .gender-group input {
                margin-right: 5px;
            }

            .form-footer {
                font-size: 13px;
                color: #666;
                margin-top: 10px;
            }

            .form-footer a {
                color: #007bff;
                text-decoration: none;
            }

            .checkbox-group {
                display: flex;
                align-items: center;
                gap: 5px;
            }

            .form-options {
                display: flex;
                justify-content: space-between;
                align-items: center;
                font-size: 14px;
            }



            .cl{
                width: 30%;
            }

            button {
                background-color: #FF6B6B;
                color: white;
                border: none;
                padding: 12px 20px;
                width: 100%;
                border-radius: 6px;
                font-size: 16px;
                cursor: pointer;
            }

            button:hover {
                background-color: #FF3366;
            }
            .error-message {
                color: red;
                font-weight: bold;
                margin-bottom: 0.5px;
                display: block;
            }

        </style>
    </head>
    <body>
        <div class="wrapper">
            <!-- Đăng nhập -->
            <div class="form-container">
                <h2>Đăng nhập</h2>
                <form action="login" method="get">
                    <div class="form-group">
                        <label>Email *</label>
                        <input type="text" name="loginEmail"  required />
                    </div>
                    <div class="form-group">
                        <label>Mật khẩu *</label>
                        <input type="password" name="loginPassword" required />
                    </div>
                    <div class="form-group">
                        <select name="role" required>
                            <option value="">--Chọn vai trò--</option>
                            <option value="admin">Quản trị viên</option>

                            <option value="teacher">Giáo viên</option>
                            <option value="Studentt">Học sinh</option>
                            <option value="parent">Phụ huynh</option>
                            <option value="manager">Quản lý</option>
                        </select>
                         <a href="#">Quên mật khẩu?</a>
                    </div>
                    
                    <br />
                    <% if (request.getAttribute("error") != null) { %>
                    <span class="error-message"><%= request.getAttribute("error") %></span>
                    <% } %>
                    <br />
                    <button type="submit" class="log">Đăng nhập</button>
                </form>
            </div>

            <!-- Đăng ký học -->
            <div class="form-container">
                <h2>Đăng ký học</h2>
                <form action="register" method="post"  enctype="multipart/form-data">
                    <div class="form-group">
                        <input type="text" name="fullName" placeholder="Họ và Tên" required />
                    </div>
                    <div class="form-group">

                        <input type="text" name="email" placeholder="Email" required />
                    </div>
                    <div class="form-group">

                        <input type="text" name="phone" placeholder="Số điện thoại" required />
                    </div>
                    <div class="form-group">
                        <label>Giới tính</label><br/>
                        <div class="gender-group">
                            <input type="radio" name="gender" value="Nữ" checked /> Nữ
                            <input type="radio" name="gender" value="Nam" /> Nam
                            <input type="radio" name="gender" value="Khác" /> Khác
                        </div>
                    </div>
                    <div class="form-group">
                        <label>Ngày sinh</label>
                        <input type="date" name="dob" required/>
                    </div>
                    <div class="form-group">

                        <input type="text" name="Address" placeholder="Địa chỉ" required/>
                    </div>

                    <div class="form-group schoolclass sc">

                        <input type="text" name="school" placeholder="Trường" required/>


                    </div>

                    <div class="form-group schoolclass cl">


                        <input type="text" name="class" placeholder="Lớp" required/>

                    </div>


                    <div class="form-group">

                        <input type="text" name="phonepar" placeholder="SĐT người giám hộ" required/>
                    </div>
                    <div class="form-group">

                        <input type="text" name="emailpar" placeholder="Email người giám hộ" required/>
                    </div>

                    <div class="form-group">
                        <label>Tải ảnh thẻ học sinh</label>
                        <br>
                        <input type="file" name="avatar" required>
                    </div>
                    <div class="form-footer">
                        <input type="checkbox" name="verifi" required/>
                        Cam kết rằng những thông tin bạn cung cấp phía trên là chính xác, nếu có gì sai sót bạn phải chịu trách nhiệm trước
                        <a href="#">Pháp Luật</a> theo 
                        <a href="#">Thông tư 29</a> về việc học thêm.
                    </div>
                    <br />
                    
                    <% if (request.getAttribute("error1") != null) { %>
                    <span class="error-message"><%= request.getAttribute("error1") %></span>
                    <% } %>
                    <br />
                    <button type="submit">Gửi yêu cầu đăng kí học</button>

                </form>

            </div>
        </div>

    </body>
</html>

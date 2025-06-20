/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

import dal.RegisterDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 *
 * @author DO NGOC ANH HE180661
 */
@MultipartConfig
@WebServlet(name = "RegisterServlet", urlPatterns = {"/register"})
public class RegisterServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet RegisterServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet RegisterServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String mess = "";
        try {
            String fName = request.getParameter("fullName");
            String email = request.getParameter("email");
            String phone = request.getParameter("phone");
            String gender = request.getParameter("gender");
            String birth = request.getParameter("dob");
            String school = request.getParameter("school");
            String address = request.getParameter("schoolAddress");
            String clAtSc = request.getParameter("classAtSchool");
            String parentPhone = request.getParameter("phonepar");
            String parentEmail = request.getParameter("emailpar");
            String userIntroStr = request.getParameter("userIntro");
            boolean confirm = request.getParameter("verifi") != null;

            int userIntro = 0;
            if (!userIntroStr.matches("^\\d+$")) {
                mess = "Mã người giới thiệu không hợp lệ (chỉ được chứa số)";
            } else {
                userIntro = Integer.parseInt(userIntroStr);
            }
            Date birthDate;
            if (birth.isEmpty()) {
                birthDate = null;
            } else {
                birthDate = Date.valueOf(birth);
            }
            if (!email.matches("^[A-Za-z0-9]+@(.+)$")) {
                mess = "Email không hợp lệ";
            } else if (!phone.matches("^0\\d{9}$")) {
                mess = "Số điện thoại không hợp lệ ";
            } else if (!parentEmail.matches("^[A-Za-z0-9]+@(.+)$")) {
                mess = "Email phụ huynh không hợp lệ ";
            } else if (!parentPhone.matches("^0\\d{9}$")) {
                mess = "Số điện thoại phụ huynh không hợp lệ ";
            }
            try {
                LocalDate birthDate1 = LocalDate.parse(birth);
                LocalDate today = LocalDate.now();
                if (!birthDate1.isBefore(today)) {
                    mess = "Ngày sinh không hợp lệ";
                }
            } catch (DateTimeParseException e) {
                mess = "Ngày sinh không hợp lệ";
            }

            if (!mess.isEmpty()) {
                request.setAttribute("error1", mess);
                request.getRequestDispatcher("login_register.jsp").forward(request, response);
                return;
            }

            RegisterDAO rd = new RegisterDAO();
            boolean success = rd.register(fName, phone, email, gender,
                    birthDate, school, address, clAtSc, parentPhone,
                    parentEmail, userIntro, confirm);

            if (success) {
                request.getRequestDispatcher("SuccessRegister.jsp").forward(request, response);

            } else {
                request.getRequestDispatcher("login_register.jsp").forward(request, response);
                return;
            }
        } catch (Exception e) {
            request.getRequestDispatcher("login_register.jsp").forward(request, response);
            return;
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

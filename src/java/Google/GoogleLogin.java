package Google;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */



import dal.UserDAO;
import entity.User;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author NGOC ANH
 */
@WebServlet("/googlelogin")
public class GoogleLogin extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
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
            out.println("<title>Servlet GoogleLogin</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet GoogleLogin at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
       String code = request.getParameter("code");
    if (code == null || code.isEmpty()) {
        // Chưa có code, chuyển hướng tới Google
        String googleLoginURL = "https://accounts.google.com/o/oauth2/v2/auth"
            + "?scope=email%20profile"
            + "&redirect_uri=http://localhost:9999/WebApplication3/googlelogin"
            + "&response_type=code"
            + "&client_id=462397547099-cog4espvmnlb8qcg8s34ikuum3hufpur.apps.googleusercontent.com"
            + "&prompt=consent";
        response.sendRedirect(googleLoginURL);
        return;
    }

    // 1. Đổi code lấy access token
    String accessToken = GoogleUtils.getToken(code);

    // 2. Dùng access token lấy thông tin user
    GoogleUserDto googleUser = GoogleUtils.getUserInfo(accessToken);
         UserDAO ld = new UserDAO();
        User acc = ld.getUserByEmail(googleUser.getEmail());
        
        

        if (acc != null) {
            HttpSession session = request.getSession();
            session.setAttribute("account", acc);
            session.setAttribute("userName", acc.getName());
            session.setAttribute("userAvatar", acc.getAvatar());

            // Điều hướng theo vai trò
            switch (acc.getRoleID()) {
                case 1:
                    response.sendRedirect("SuccessRegister.jsp");
                    break;
                case 2:
                    response.sendRedirect("teacherdashboard.jsp");
                    break;
                case 3:
                    response.sendRedirect("dashboard.jsp");
                    break;
                case 4:
                    response.sendRedirect("SuccessRegister.jsp");
                    break;
                default:
                    response.sendRedirect("Home.jsp");
            }
        } else {
            request.setAttribute("error", "Email chưa tồn tại trong hệ thống.");
            request.getRequestDispatcher("login_register.jsp").forward(request, response);
        }
    
    } 
    


    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

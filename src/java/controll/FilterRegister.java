/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controll;

import dal.RegisterDAO;
import entity.Register;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 *
 * @author NGOC ANH
 */
@WebServlet(name="FilterRegister", urlPatterns={"/filterregister"})
public class FilterRegister extends HttpServlet {
         private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
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
            out.println("<title>Servlet FilterRegister</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet FilterRegister at " + request.getContextPath () + "</h1>");
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
        String from = request.getParameter("fromDate");
        String to = request.getParameter("toDate");
        String status = request.getParameter("status");
        String keyword = request.getParameter("keyword");

        Date fromDate = null;
        Date toDate = null;

        try {
            if (from != null && !from.isEmpty()) {
                fromDate = sdf.parse(from);
            }
            if (to != null && !to.isEmpty()) {
                toDate = sdf.parse(to);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        RegisterDAO dao = new RegisterDAO();
        List<Register> listRegis = dao.getFilteredRegisters(fromDate, toDate, status, keyword);

        request.setAttribute("listRegis", listRegis);
        request.setAttribute("fromDate", from);
        request.setAttribute("toDate", to);
        request.setAttribute("status", status);
        request.setAttribute("keyword", keyword);
        request.getRequestDispatcher("approveaccount.jsp").forward(request, response);
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

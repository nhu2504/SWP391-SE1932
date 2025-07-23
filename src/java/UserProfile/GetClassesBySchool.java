/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package UserProfile;

import com.google.gson.Gson;
import dal.DBContext;
import dal.SchoolClassDAO;
import entity.SchoolClass;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.util.List;

/**
 *
 * @author NGOC ANH
 */
@WebServlet("/GetClassesBySchoolServlet")
public class GetClassesBySchool extends HttpServlet {
   
     @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String schoolIdStr = request.getParameter("schoolId");
        int schoolId = Integer.parseInt(schoolIdStr);

        try {
            Connection conn = new DBContext().connection;
            SchoolClassDAO dao = new SchoolClassDAO();
            List<SchoolClass> classes = dao.getAllClassesBySchoolId(schoolId);

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            Gson gson = new Gson();
            String json = gson.toJson(classes);
            response.getWriter().write(json);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi máy chủ");
        }
    }
}

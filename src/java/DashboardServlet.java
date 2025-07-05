//
//
//import dal.DocumentDAO;
//import dal.TutoringClassDAO;
//import dal.SubjectDAO;
//import java.io.IOException;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import java.util.ArrayList;
//import entity.Document;
//import entity.TutoringClass;
//import entity.Subject;
//
//@WebServlet(name = "DashboardServlet", urlPatterns = {"/DashboardServlet"})
//public class DashboardServlet extends HttpServlet {
//
//    private TutoringClassDAO tutoringClassDAO = new TutoringClassDAO();
//    private DocumentDAO documentDAO = new DocumentDAO();
//    private SubjectDAO subjectDAO = new SubjectDAO();
//
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        int userID = 1; // Giả lập userID
//
//        try {
//            ArrayList<TutoringClass> classes = tutoringClassDAO.getClassesByUserID(userID);
//            ArrayList<Document> documents = documentDAO.getDocumentsByUserID(userID);
//            ArrayList<Subject> subjects = subjectDAO.getAllSubject();
//
//            request.setAttribute("classes", classes);
//            request.setAttribute("documents", documents);
//            request.setAttribute("subjects", subjects);
//            request.getRequestDispatcher("/dashboard.jsp").forward(request, response);
//        } catch (Exception e) {
//            e.printStackTrace();
//            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi khi tải dữ liệu dashboard.");
//        }
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        doGet(request, response);
//    }
//}
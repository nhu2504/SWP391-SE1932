//import dal.CenterInfoDAO;
//import dal.GradeDAO;
//import dal.SubjectDAO;
//import dal.TutoringClassDAO;
//import java.io.IOException;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import java.util.ArrayList;
//import java.util.Map;
//import entity.Grade;
//import entity.Subject;
//import entity.TutoringClass;
//
//@WebServlet(name = "GradeServlet", urlPatterns = {"/GradeServlet"})
//public class GradeServlet extends HttpServlet {
//
//    private GradeDAO gradeDAO = new GradeDAO();
//    private SubjectDAO subjectDAO = new SubjectDAO();
//    private TutoringClassDAO tutoringClassDAO = new TutoringClassDAO();
//    private CenterInfoDAO centerInfoDAO = new CenterInfoDAO(); // Thêm CenterInfoDAO
//
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        int userID = 1; // Giả lập userID
//
//        try {
//            String subjectIDParam = request.getParameter("subjectID");
//            String searchTestName = request.getParameter("searchTestName");
//
//            ArrayList<Subject> subjects = subjectDAO.getAllSubject();
//            ArrayList<TutoringClass> classes = tutoringClassDAO.getClassesByUserID(userID);
//            ArrayList<Grade> grades = gradeDAO.getGradesByUserID(userID);
//            Map<String, String> centerInfo = centerInfoDAO.getCenterInfo(1); // Lấy thông tin trung tâm (CenterID = 1)
//
//            ArrayList<Grade> filteredGrades = new ArrayList<>();
//
//            Integer selectedSubjectID = null;
//            if (subjectIDParam != null && !subjectIDParam.isEmpty()) {
//                selectedSubjectID = Integer.parseInt(subjectIDParam.trim());
//            }
//
//            for (Grade grade : grades) {
//                boolean matchesSubject = true;
//                boolean matchesSearch = true;
//
//                if (selectedSubjectID != null) {
//                    Integer gradeSubjectID = null;
//                    if (grade.getTutoringClass() != null) {
//                        Subject subject = grade.getTutoringClass().getSubjectID();
//                        if (subject != null) {
//                            gradeSubjectID = subject.getSubjectId();
//                        }
//                    }
//                    matchesSubject = (gradeSubjectID != null && gradeSubjectID.equals(selectedSubjectID));
//                }
//
//                if (searchTestName != null && !searchTestName.trim().isEmpty()) {
//                    if (grade.getNameOfTest() != null) {
//                        matchesSearch = grade.getNameOfTest().toLowerCase().contains(searchTestName.toLowerCase());
//                    } else {
//                        matchesSearch = false;
//                    }
//                }
//
//                if (matchesSubject && matchesSearch) {
//                    filteredGrades.add(grade);
//                }
//            }
//
//            request.setAttribute("subjects", subjects);
//            request.setAttribute("classes", classes);
//            request.setAttribute("grades", filteredGrades);
//            request.setAttribute("selectedSubjectID", selectedSubjectID);
//            request.setAttribute("searchTestName", searchTestName);
//            request.setAttribute("centerInfo", centerInfo); // Đặt centerInfo vào request
//            request.getRequestDispatcher("/grade.jsp").forward(request, response);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            request.setAttribute("error", "Lỗi khi tải dữ liệu điểm: " + e.getMessage());
//            request.getRequestDispatcher("/error.jsp").forward(request, response);
//        }
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        doGet(request, response);
//    }
//}
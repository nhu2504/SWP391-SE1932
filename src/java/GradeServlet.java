

import dal.GradeDAO;
import dal.SubjectDAO;
import dal.TutoringClassDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import entity.Grade;
import entity.Subject;
import entity.TutoringClass;

@WebServlet(name = "GradeServlet", urlPatterns = {"/GradeServlet"})
public class GradeServlet extends HttpServlet {

    private GradeDAO gradeDAO = new GradeDAO();
    private SubjectDAO subjectDAO = new SubjectDAO();
    private TutoringClassDAO tutoringClassDAO = new TutoringClassDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int userID = 1; // Giả lập userID

        try {
            // Lấy tham số lọc và tìm kiếm từ request
            String subjectIDParam = request.getParameter("subjectID");
            String searchTestName = request.getParameter("searchTestName");

            // Lấy danh sách môn học để hiển thị trong dropdown
            ArrayList<Subject> subjects = subjectDAO.getAllSubject();
            request.setAttribute("subjects", subjects);

            // Lấy danh sách lớp học của user
            ArrayList<TutoringClass> classes = tutoringClassDAO.getClassesByUserID(userID);
            request.setAttribute("classes", classes);

            // Lấy danh sách điểm
            ArrayList<Grade> grades = gradeDAO.getGradesByUserID(userID);
            ArrayList<Grade> filteredGrades = new ArrayList<>();

            // Lọc điểm theo môn học (nếu có subjectID được chọn)
            Integer selectedSubjectID = subjectIDParam != null && !subjectIDParam.isEmpty() ? Integer.parseInt(subjectIDParam) : null;
            for (Grade grade : grades) {
                boolean matchesSubject = true;
                boolean matchesSearch = true;

                // Lọc theo môn học
                if (selectedSubjectID != null) {
                    matchesSubject = false;
                    for (TutoringClass cls : classes) {
                        if (cls.getTutoringClassID() == grade.getTutoringClass() && cls.getSubjectID() == selectedSubjectID) {
                            matchesSubject = true;
                            break;
                        }
                    }
                }

                // Tìm kiếm theo tên bài kiểm tra
                if (searchTestName != null && !searchTestName.trim().isEmpty()) {
                    matchesSearch = grade.getNameOfTest().toLowerCase().contains(searchTestName.toLowerCase());
                }

                if (matchesSubject && matchesSearch) {
                    filteredGrades.add(grade);
                }
            }

            // Truyền dữ liệu sang JSP
            request.setAttribute("grades", filteredGrades);
            request.setAttribute("selectedSubjectID", selectedSubjectID);
            request.setAttribute("searchTestName", searchTestName);
            request.getRequestDispatcher("/grade.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Lỗi khi tải dữ liệu điểm: " + e.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}


import dal.TutoringClassDAO;
import dal.SubjectDAO;
import dal.RoomDAO;
import dal.ShiftlearnDAO;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import entity.TutoringClass;
import entity.Subject;
import entity.Room;
import entity.Shift;


@WebServlet(name = "ScheduleServlet", urlPatterns = {"/ScheduleServlet"})
public class ScheduleServlet extends HttpServlet {

    private TutoringClassDAO tutoringClassDAO = new TutoringClassDAO();
    private SubjectDAO subjectDAO = new SubjectDAO();
    private RoomDAO roomDAO = new RoomDAO();
    private ShiftlearnDAO shiftlearnDAO = new ShiftlearnDAO();


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int userID = 1; // Giả lập userID

        try {
            ArrayList<TutoringClass> tutoringClasses = tutoringClassDAO.getClassesByUserID(userID);
            ArrayList<Subject> subjects = subjectDAO.getAllSubject();
            ArrayList<Room> rooms = roomDAO.getAllRooms();
            ArrayList<Shift> shifts = shiftlearnDAO.getAllShifts();


            // Debug: In số lượng dữ liệu
            System.out.println("ScheduleServlet: tutoringClasses size = " + tutoringClasses.size());
            System.out.println("ScheduleServlet: subjects size = " + subjects.size());
            System.out.println("ScheduleServlet: rooms size = " + rooms.size());
            System.out.println("ScheduleServlet: shifts size = " + shifts.size());


            request.setAttribute("tutoringClasses", tutoringClasses);
            request.setAttribute("subjects", subjects);
            request.setAttribute("rooms", rooms);
            request.setAttribute("shifts", shifts);

            request.getRequestDispatcher("/schedule.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Lỗi khi tải dữ liệu lịch học: " + e.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
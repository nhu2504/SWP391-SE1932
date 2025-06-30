

import dal.CenterInfoDAO;
import dal.NotificationDAO;
import entity.Notification;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/notifications")
public class NotificationServlet extends HttpServlet {
    private NotificationDAO notificationDAO;
    private CenterInfoDAO centerInfoDAO;

    @Override
    public void init() throws ServletException {
        notificationDAO = new NotificationDAO();
        centerInfoDAO = new CenterInfoDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<Notification> notifications = notificationDAO.getPublicNotifications();
            request.setAttribute("notifications", notifications);
            request.setAttribute("userName", "Khách");
            request.setAttribute("centerInfo", centerInfoDAO.getCenterInfo(1)); // Sử dụng CenterID = 1

            request.getRequestDispatcher("/notification.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("Database error", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
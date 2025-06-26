

import dal.CenterInfoDAO;
import dal.TutoringClassStuDAO;
import entity.TutoringClassStu;
import entity.account;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

/**
 * Servlet xử lý yêu cầu thanh toán học phí và hiển thị danh sách khóa học.
 * @author DO NGOC ANH HE180661
 */
@WebServlet(name = "PaymentServlet", urlPatterns = {"/PaymentServlet"})
public class PaymentServlet extends HttpServlet {

    private TutoringClassStuDAO tutoringClassDAO = new TutoringClassStuDAO();
    private CenterInfoDAO centerInfoDAO = new CenterInfoDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        account acc = (account) session.getAttribute("account");
        int userID;
        String userName;
        String userAvatar;

        // Nếu không có account trong session, sử dụng userID mặc định
        if (acc == null) {
            userID = 23;
            userName = "Khách";
            userAvatar = "/images/default-avatar.png";
        } else {
            userID = acc.getId();
            userName = acc.getName();
            userAvatar = acc.getAvatar();
        }

        try {
            // Lấy danh sách khóa học của học sinh
            ArrayList<TutoringClassStu> courses = tutoringClassDAO.getClassesByUserID(userID);
            // Lấy thông tin trung tâm
            Map<String, String> centerInfo = centerInfoDAO.getCenterInfo();

            // Lưu dữ liệu vào request và session
            request.setAttribute("courses", courses);
            request.setAttribute("centerInfo", centerInfo);
            session.setAttribute("userName", userName);
            session.setAttribute("userAvatar", userAvatar);

            // Chuyển hướng đến payment.jsp
            request.getRequestDispatcher("/payment.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi khi tải dữ liệu thanh toán.");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Xử lý xác nhận thanh toán
        String courseID = request.getParameter("courseID");
        String confirm = request.getParameter("confirm");

        if (confirm != null && courseID != null) {
            try {
                // Lấy userID (mặc định hoặc từ account)
                HttpSession session = request.getSession();
                account acc = (account) session.getAttribute("account");
                int userID = (acc == null) ? 23 : acc.getId();

                // Cập nhật trạng thái thanh toán trong cơ sở dữ liệu
                tutoringClassDAO.updatePaymentStatus(Integer.parseInt(courseID), userID);
                response.sendRedirect(request.getContextPath() + "/PaymentServlet");
            } catch (Exception e) {
                e.printStackTrace();
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi khi xác nhận thanh toán.");
            }
        } else {
            doGet(request, response);
        }
    }
}
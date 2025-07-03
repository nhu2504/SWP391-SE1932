<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="java.time.DayOfWeek" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.sql.Date" %>
<%
    int[] weekOrder = {2, 3, 4, 5, 6, 7, 1}; // Thứ 2 -> Chủ nhật
    pageContext.setAttribute("weekOrder", weekOrder);
%>

<style>
    
    h2 {
        text-align: center;
        color: #333;
        margin-bottom: 20px;
    }
    th, td {
        border: 1px solid #ccc;
        padding: 10px;
        vertical-align: top;
        text-align: left;
    }
    th {
        background-color: #FF6B6B;
        color: white;
        text-align: center;
    }
    td.today, th.today {
        background-color: #FFF9C4 !important; /* Vàng nhạt cho hôm nay */
        color: black;
    }
    .font-medium {
        font-weight: 500;
    }
    .whitespace-nowrap {
        white-space: nowrap;
    }
    .overflow-auto {
        overflow-x: auto;
    }
    .form-group {
        margin-bottom: 15px;
    }
    .form-group label {
        font-weight: bold;
    }
    .form-group input, .form-group select {
        width: 100%;
        padding: 10px;
        border: 1px solid #ccc;
        border-radius: 4px;
        transition: border-color 0.3s;
    }
    .form-group input:focus, .form-group select:focus {
        border-color: #4CAF50; /* Màu viền khi focus */
        outline: none; /* Bỏ outline mặc định */
    }
    .btn1 {
        background-color: #4CAF50;
        color: white;
        padding: 10px 15px;
        border: none;
        border-radius: 4px;
        cursor: pointer;
        transition: background-color 0.3s;
    }
    .btn1:hover {
        background-color: #45a049; /* Màu khi hover */
    }
    .max-w-4xl {
        max-width: 800px;
        margin: auto;
        background: white;
        padding: 20px;
        border-radius: 8px;
        box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
    }
</style>


    <form method="get" action="admin">
        <input type="hidden" name="tab" value="todaySchedule" />

        <div class="form-group" style="width: 350px;">
            <label>Chọn tuần:</label>
            <select name="weekStart" onchange="this.form.submit()">
                <c:forEach var="ws" items="${weekStartList}">
                    <%
                        java.sql.Date wsDate = (java.sql.Date) pageContext.findAttribute("ws");
                        java.sql.Date weDate = new java.sql.Date(wsDate.getTime() + 6L * 24 * 60 * 60 * 1000);
                        pageContext.setAttribute("weDate", weDate);
                    %>
                    <option value="${ws}" ${ws eq selectedWeekStart ? 'selected' : ''}>
                        <fmt:formatDate value="${ws}" pattern="dd/MM/yyyy"/>
                        -
                        <fmt:formatDate value="${weDate}" pattern="dd/MM/yyyy"/>
                    </option>
                </c:forEach>
            </select>
        </div>

        <div class="form-group d-flex align-items-end" style="gap: 10px;">
            <div>
                <label>Hoặc nhập ngày bất kỳ trong tuần muốn xem:</label>
                <input type="date" name="anyDate" value="${param.anyDate}" class="form-control"/>
            </div>
            <div>
                <button type="submit" class="btn1" style="margin-bottom: 4px;">Xem</button>
            </div>
        </div>

    </form>

    <table class="min-w-full text-sm mt-4">
        <thead>
            <tr>
                <th>Ca học</th>
                <c:forEach var="day" items="${weekOrder}">
                    <th class="${weekdayDates[day].time == todayDate.time ? 'today' : ''}">
                        <c:out value="${weekdays[day]}" default="Thứ ${day}" />
                    </th>
                </c:forEach>
            </tr>
            <tr>
                <th></th>
                <c:forEach var="day" items="${weekOrder}">
                    <th class="${weekdayDates[day].time == todayDate.time ? 'today' : ''}">
                        <fmt:formatDate value="${weekdayDates[day]}" pattern="dd/MM/yyyy"/>
                    </th>
                </c:forEach>
            </tr>
        </thead>

        <tbody>
            <c:forEach var="shift" items="${shifts}">
                <tr>
                    <td class="font-medium whitespace-nowrap">
                        Ca ${shift.id}<br/>
                        <fmt:formatDate value="${shift.startTime}" pattern="HH:mm"/>
                        -
                        <fmt:formatDate value="${shift.endTime}" pattern="HH:mm"/>
                    </td>
                    <c:forEach var="day" items="${weekOrder}">
                        <td class="${weekdayDates[day].time == todayDate.time ? 'today' : ''}">
                            <c:forEach var="entry" items="${weeklySchedules}">
                                <c:if test="${entry[0] == day && entry[1] == shift.id}">
                                    <div class="mb-2">
                                        <strong>${entry[2]}</strong><br/>
                                        GV: ${entry[3]}<br/>
                                        Phòng: ${entry[4]}
                                    </div>
                                </c:if>
                            </c:forEach>
                        </td>
                    </c:forEach>
                </tr>
            </c:forEach>
        </tbody>
    </table>

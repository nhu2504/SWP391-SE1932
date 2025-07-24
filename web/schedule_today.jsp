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
<!-- Văn Thị Như - HE181329 
Ngày update 3/7/2025-->
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

    <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
        <!-- Chọn tuần -->
        <div>
            <label class="block font-semibold mb-1">Chọn tuần:</label>
            <select name="weekStart" onchange="this.form.submit()" class="w-full border rounded px-3 py-2">
                <c:forEach var="ws" items="${weekStartList}">
                    <option value="${ws}" ${ws.toString() == selectedWeekStart.toString() ? 'selected' : ''}>
                        ${weekDisplayMap[ws]}
                    </option>

                </c:forEach>
            </select>

        </div>

        <!-- Nhập ngày -->
        <div>
            <label class="block font-semibold mb-1">Hoặc nhập ngày bất kỳ trong tuần muốn xem:</label>
            <div class="flex gap-2">
                <input type="date" name="anyDate" value="${param.anyDate}" class="border rounded px-3 py-2 w-full"/>
                <button type="submit" class="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700">Xem</button>
            </div>
        </div>
    </div>
</form>


<table class="min-w-full text-sm mt-4">
    <thead>
        <tr>
            <th>Ca học</th>
                <c:forEach var="day" items="${weekOrder}">
                    <fmt:formatDate var="wDate" value="${weekdayDates[day]}" pattern="yyyy-MM-dd"/>
                    <fmt:formatDate var="tDate" value="${todayDate}" pattern="yyyy-MM-dd"/>
                <th class="${wDate == tDate ? 'today' : ''}">
                    <c:out value="${weekdays[day]}" default="Thứ ${day}" />
                </th>
            </c:forEach>
        </tr>
        <tr>
            <th></th>
                <c:forEach var="day" items="${weekOrder}">
                    <fmt:formatDate var="wDate" value="${weekdayDates[day]}" pattern="yyyy-MM-dd"/>
                    <fmt:formatDate var="tDate" value="${todayDate}" pattern="yyyy-MM-dd"/>
                <th class="${wDate == tDate ? 'today' : ''}">
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
                    <fmt:formatDate var="wDate" value="${weekdayDates[day]}" pattern="yyyy-MM-dd"/>
                    <fmt:formatDate var="tDate" value="${todayDate}" pattern="yyyy-MM-dd"/>
                    <td class="${wDate == tDate ? 'today' : ''}">
                        <c:forEach var="entry" items="${weeklySchedules}">
                            <c:if test="${entry[1] == day && entry[2] == shift.id}">
                                <div class="mb-2">
                                    <strong>${entry[3]}</strong><br/>
                                    GV: ${entry[4]}<br/>
                                    Phòng: ${entry[5]}
                                </div>
                            </c:if>
                        </c:forEach>
                    </td>
                </c:forEach>
            </tr>
        </c:forEach>
    </tbody>
</table>
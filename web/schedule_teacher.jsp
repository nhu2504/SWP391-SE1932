<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- Văn Thị Như - HE181329 
Ngày update 3/7/2025-->

<%
    String[] weekdays = { "", "Thứ 2", "Thứ 3", "Thứ 4", "Thứ 5", "Thứ 6", "Thứ 7", "Chủ Nhật" };
    request.setAttribute("weekdays", weekdays);
%>

<style>
    .highlight-today-column {
        background-color: #FFF0F0 !important;
    }
</style>

<form method="get" action="admin" class="bg-white p-4 rounded shadow-md mb-6">
    <input type="hidden" name="tab" value="teacherSchedule" />

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

<div class="flex flex-col md:flex-row gap-4 mb-6">

    <input type="text" id="searchByName" placeholder="🔍 Tìm tên..." class="form-control w-full md:w-1/3">

</div>

<div class="overflow-auto">
    <table class="table-auto w-full text-sm text-center border border-gray-300 shadow-md rounded overflow-hidden">
        <thead>
            <tr style="background-color: #FFF1F1; color: black;">
                <th class="px-4 py-2 border">Giáo viên</th>
                    <c:forEach var="day" items="${weekOrder}">
                    <th class="px-4 py-2 border text-center ${highlightColumnIndex == day ? 'bg-yellow-50' : ''}">
                        ${weekdays[day - 1]}
                    </th>
                </c:forEach>
            </tr>

            <tr style="background-color: #FFF1F1; color: #555;">
                <th class="px-4 py-1 border text-center text-sm font-normal">Ngày</th>
                    <c:forEach var="day" items="${weekOrder}">
                    <th class="px-4 py-1 border text-center text-sm font-normal ${highlightColumnIndex == day ? 'bg-yellow-50' : ''}">
                        <fmt:formatDate value="${weekDates[day]}" pattern="dd/MM/yyyy"/>
                    </th>
                </c:forEach>
            </tr>



        </thead>



        <tbody>
            <c:forEach var="entry" items="${teacherScheduleMap}">
                <tr class="border-t">
                    <td class="px-4 py-2 font-medium border teacher-name">${entry.key}</td>

                    <c:forEach var="day" items="${weekOrder}">
                        <td class="px-2 py-2 align-top border ${highlightColumnIndex == day ? 'bg-yellow-50' : ''}">
                            <c:forEach var="item" items="${entry.value}">
                                <c:if test="${item[2] == (day == 8 ? 1 : day)}"> <!-- 8 = CN → 1 -->
                                    <div class="mb-2 p-2 bg-gray-50 border rounded shadow-sm text-left text-sm">
                                        <div class="font-semibold text-[#FF6B6B]">${item[0]}</div>
                                        <div class="text-gray-600">${item[1]}</div>
                                        <div class="text-gray-500 text-xs">
                                            <fmt:formatDate value="${item[3]}" pattern="HH:mm" /> -
                                            <fmt:formatDate value="${item[4]}" pattern="HH:mm" />
                                        </div>
                                    </div>
                                </c:if>
                            </c:forEach>
                        </td>
                    </c:forEach>
                </tr>
            </c:forEach>

        </tbody>
    </table>

</div>
<script>
    document.addEventListener("DOMContentLoaded", function () {
        const searchInput = document.getElementById("searchByName");

        searchInput.addEventListener("input", function () {
            const nameFilter = searchInput.value.toLowerCase();
            const rows = document.querySelectorAll("table tbody tr");

            rows.forEach(row => {
                const nameCell = row.querySelector(".teacher-name");
                if (!nameCell)
                    return;

                const name = nameCell.textContent.toLowerCase();
                const showRow = name.includes(nameFilter);

                row.style.display = showRow ? "" : "none";
            });
        });
    });
</script>

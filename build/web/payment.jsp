<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<style>
    body {
        font-family: Arial, sans-serif;
        background-color: #f4f4f4;
        margin: 0;
        padding: 20px;
    }
    h2 {
        text-align: center;
        color: #333;
        margin-bottom: 20px;
    }
    table {
        width: 100%;
        border-collapse: collapse;
        margin-bottom: 20px;
        background-color: white;
        border-radius: 8px;
        overflow: hidden;
        box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
    }
    th, td {
        border: 1px solid #ccc;
        padding: 10px;
        text-align: left;
    }
    th {
        background-color: #FF6B6B;
        color: white;
        text-align: center;
    }
    td {
        background-color: #f9f9f9;
    }
    .font-medium {
        font-weight: 500;
    }
    .progress-bar {
        background-color: #f1f1f1;
        border-radius: 8px;
        height: 18px;
        overflow: hidden;
    }
    .progress-fill {
        background-color: #4CAF50;
        height: 100%;
        color: white;
        text-align: center;
        font-size: 12px;
        transition: width 0.3s ease;
    }
    .status {
        font-weight: bold;
    }
    .search-box {
        margin-bottom: 20px;
    }
    .search-box input {
        padding: 10px;
        border-radius: 5px;
        border: 1px solid #ccc;
        width: 100%;
        box-sizing: border-box;
    }
</style>


<!-- Ô tìm kiếm lớp học -->
<div class="search-box">
    <input type="text" id="searchClass" placeholder="Tìm lớp..." onkeyup="filterClasses()" />
</div>

<table>
    <thead>
        <tr>
            <th>Lớp học</th>
            <th>Tổng HS</th>
            <th>Đã đóng</th>
            <th>Còn nợ</th>
            <th>Tỷ lệ</th>
            <th>Tiến độ</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach var="row" items="${paymentDetails}">
            <tr class="class-row">
                <td class="class-name">${row[0]}</td>
                <td>${row[1]}</td>
                <td>${row[2]}</td>
                <td>
                    <c:choose>
                        <c:when test="${row[3] == 0}">
                            <span style="color:green;" class="status">${row[3]}</span>
                        </c:when>
                        <c:otherwise>
                            <span style="color:red;" class="status">${row[3]}</span>
                        </c:otherwise>
                    </c:choose>
                </td>
                <td>${row[4]}%</td>
                <td>
                    <div class="progress-bar">
                        <div class="progress-fill" style="width: ${row[4]}%;">${row[4]}%</div>
                    </div>
                </td>
            </tr>
        </c:forEach>
    </tbody>
</table>

<script>
function removeVietnameseTones(str) {
    return str.normalize("NFD").replace(/[\u0300-\u036f]/g, "")
              .replace(/đ/g, "d").replace(/Đ/g, "D");
}

function filterClasses() {
    const input = document.getElementById("searchClass");
    const filter = removeVietnameseTones(input.value.toLowerCase());
    const rows = document.querySelectorAll(".class-row");

    rows.forEach(row => {
        const className = row.querySelector(".class-name").innerText;
        const normalized = removeVietnameseTones(className.toLowerCase());

        if (normalized.includes(filter)) {
            row.style.display = "";
        } else {
            row.style.display = "none";
        }
    });
}
</script>


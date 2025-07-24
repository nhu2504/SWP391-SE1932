<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Quản Lý Khóa Học</title>
<script src="https://cdn.tailwindcss.com"></script>
<link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap" rel="stylesheet">

<div class="bg-gradient-to-br from-blue-50 to-gray-100 min-h-screen flex flex-col items-center p-6 font-inter">

    <div class="w-full max-w-6xl bg-white rounded-2xl shadow-xl p-8">


        <nav class="flex mb-6" aria-label="Breadcrumb">
            <ol class="inline-flex items-center space-x-1 md:space-x-3">
                <li class="inline-flex items-center">
                    <a href="admin?tab=courseList" class="inline-flex items-center text-sm font-medium text-gray-700 hover:text-blue-600">
                        <i class="fas fa-home mr-2"></i>
                        Khóa học
                    </a>
                </li>
                <li>
                    <div class="flex items-center">
                        <i class="fas fa-chevron-right text-gray-400 mx-2"></i>
                        <a href="admin?tab=classManagement&id=${selectedCourseId}" class="ml-1 text-sm font-medium text-gray-700 hover:text-blue-600">
                            ${selectedCourseName}
                        </a>
                    </div>
                </li>
                <li>
                    <div class="flex items-center">
                        <i class="fas fa-chevron-right text-gray-400 mx-2"></i>
                        <a href="admin?tab=studentListInClass&groupId=${selectedClassGroupId}&id=${selectedCourseId}"
                           class="ml-1 text-sm font-medium text-gray-700 hover:text-blue-600">
                            ${selectedClassGroupName}
                        </a>
                    </div>
                </li>


                <li>
                    <div class="flex items-center">
                        <i class="fas fa-chevron-right text-gray-400 mx-2"></i>
                        <span class="ml-1 text-sm font-medium text-gray-500 pointer-events-none cursor-not-allowed">
                            Thêm học sinh mới
                        </span>
                    </div>
                </li>


            </ol>
        </nav>
        <div class="flex flex-col md:flex-row gap-4 mb-6">
            <input type="text" id="searchById" placeholder="🔍 Tìm ID..." class="form-control w-full md:w-1/3">
            <input type="text" id="searchByName" placeholder="🔍 Tìm tên..." class="form-control w-full md:w-1/3">
            <input type="text" id="searchByPhone" placeholder="🔍 Tìm số điện thoại..." class="form-control w-full md:w-1/3">
        </div>



        <div>
            <div class="flex items-center justify-between mb-6">
                <h2 class="text-xl font-semibold text-gray-700">Danh Sách Học Sinh Ngoài Lớp</h2>
                <div>
                    <span id="selectedCount" class="text-sm text-blue-600 font-medium">
                    Đã chọn: 0 học sinh
                </span>
                <span id="remainingCount" class="block text-sm text-gray-600">
            Có thể thêm: ${maxStudent - currentCount} học sinh
        </span>
                </div>
                
            </div>

            <div class="overflow-x-auto">
                <table class="w-full border-collapse bg-white rounded-lg shadow-sm table-fixed">
                    <thead>

                        <tr style="background-color: #FFF1F1; color: black;">
                            <th class="p-4 text-left cursor-pointer w-[5%]" onclick="toggleCheckAll()">
                                <span class="text-sm font-medium text-black">Chọn tất cả</span>
                                <input type="checkbox" id="checkAll" class="hidden">
                            </th>

                            <th class="p-4 text-left w-[5%]">ID</th>
                            <th class="p-4 text-left w-[8%]">Avatar</th>
                            <th class="p-4 text-left w-[15%]">Tên</th>
                            <th class="p-4 text-left w-[8%]">Giới tính</th>
                            <th class="p-4 text-left w-[10%]">Ngày sinh</th>
                            <th class="p-4 text-left w-[12%]">Số điện thoại</th>
                            <th class="p-4 text-left w-[15%]">Email</th>
                            <th class="p-4 text-left w-[12%]">Trường</th>
                            <th class="p-4 text-left w-[10%]">Lớp</th>

                        </tr>
                    </thead>
                    <tbody class="divide-y divide-gray-200">
                        <c:forEach items="${students}" var="s">
                            <tr class="hover:bg-gray-50 transition">
                                <td class="p-4 w-[5%]">
                                    <input type="checkbox" name="studentIds" value="${s.id}" class="row-checkbox student-checkbox form-checkbox accent-blue-500" data-name="${s.name}">
                                </td>
                                <td class="p-4 w-[5%]">${s.id}</td>
                                <td class="p-4 w-[8%]">
                                    <img src="${pageContext.request.contextPath}/LogoServlet?type=student&userId=${s.id}"
                                         width="48" height="48" class="rounded-full object-cover border border-gray-300"
                                         alt="${s.name}">
                                </td>

                                <td class="p-4 w-[15%]">${s.name}</td>
                                <td class="p-4 w-[8%]">${s.gender}</td>
                                <td class="p-4 w-[10%]"><fmt:formatDate value="${s.birth}" pattern="dd/MM/yyyy"/></td>
                                <td class="p-4 w-[12%]">${s.phone}</td>
                                <td class="p-4 w-[15%] truncate">${s.email}</td>
                                <td class="p-4 w-[12%]">
                                    <c:forEach items="${schools}" var="school">
                                        <c:if test="${school.schoolID == s.schoolID}">
                                            ${school.name}
                                        </c:if>
                                    </c:forEach>
                                </td>
                                <td class="p-4 w-[10%]">
                                    <c:forEach items="${schoolClasses}" var="sc">
                                        <c:if test="${sc.schoolClassID == s.schoolClassId}">
                                            ${sc.className}
                                        </c:if>
                                    </c:forEach>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>

                </table>
            </div>
        </div>
    </div>
</div>

<button id="addSelectedBtn"
        onclick="showConfirmModal()"
        class="fixed bottom-6 right-6 bg-blue-600 text-white px-6 py-3 rounded shadow-lg hover:bg-blue-700 z-50">
    Thêm (0) học sinh đã chọn 
</button>

<!-- Modal xác nhận -->
<div id="confirmModal" class="fixed inset-0 z-50 hidden bg-black bg-opacity-50 flex items-center justify-center p-4">
    <div class="bg-white p-6 rounded-lg w-full max-w-xl max-h-[80vh] overflow-y-auto shadow-lg">
        <h2 class="text-2xl font-semibold text-gray-800 mb-4 text-center">Xác nhận thêm học sinh</h2>
        
        <ul id="selectedStudentList" class="list-disc pl-5 mb-4 text-gray-700 space-y-1">
            <!-- Danh sách học sinh sẽ được thêm ở đây -->
        </ul>
        
        <div class="flex justify-end gap-3 mt-4">
            <button onclick="closeModal()" class="px-4 py-2 bg-gray-300 text-gray-800 rounded hover:bg-gray-400 transition">Hủy</button>
            <button onclick="submitSelectedStudents()" class="px-4 py-2 bg-blue-600 text-white rounded hover:bg-blue-700 transition">Xác nhận thêm</button>
        </div>
    </div>
</div>


<script>
    document.addEventListener("DOMContentLoaded", function () {
    const idInput = document.getElementById("searchById");
    const nameInput = document.getElementById("searchByName");
    const phoneInput = document.getElementById("searchByPhone");
    const rows = document.querySelectorAll("tbody tr");

    function normalize(str) {
        return (str || "")
            .normalize("NFD")
            .replace(/[\u0300-\u036f]/g, "")
            .toLowerCase()
            .trim();
    }

    function applyFilters() {
    const idKeyword = normalize(idInput.value);
    const nameKeyword = normalize(nameInput.value);
    const phoneKeyword = normalize(phoneInput.value);

    rows.forEach(row => {
        const id = normalize(row.querySelector("td:nth-child(2)")?.textContent);
        const name = normalize(row.querySelector("td:nth-child(4)")?.textContent);
        const phone = normalize(row.querySelector("td:nth-child(7)")?.textContent);

        const matchId = id.startsWith(idKeyword); // CHỈ từ đầu đến cuối
        const matchName = name.includes(nameKeyword);
        const matchPhone = phone.startsWith(phoneKeyword);

        const showRow =
            (!idKeyword || matchId) &&
            (!nameKeyword || matchName) &&
            (!phoneKeyword || matchPhone);

        row.style.display = showRow ? "" : "none";
    });
}


    idInput.addEventListener("input", applyFilters);
    nameInput.addEventListener("input", applyFilters);
    phoneInput.addEventListener("input", applyFilters);
});

</script>


<script>
    document.addEventListener("DOMContentLoaded", function () {
        const checkAll = document.getElementById("checkAll");
        const countDisplay = document.getElementById("selectedCount");

        // Kiểm tra sự tồn tại của các phần tử
        if (!checkAll) {
            console.error("Không tìm thấy checkAll");
            return;
        }
        if (!countDisplay) {
            console.error("Không tìm thấy selectedCount");
            return;
        }

        function updateSelectedCount() {
            const checkedBoxes = document.querySelectorAll(".row-checkbox:checked");
            console.log("Checked boxes:", checkedBoxes.length); // Debug
            console.log("countDisplay:", countDisplay); // Debug
            countDisplay.textContent = "Đã chọn: " + checkedBoxes.length + " học sinh"; // Nối chuỗi

            // Cập nhật nút thêm
            const addBtn = document.getElementById("addSelectedBtn");
            if (addBtn) {
                addBtn.textContent = "Thêm (" + checkedBoxes.length + ") học sinh đã chọn";
            }
            console.log("Updated textContent:", countDisplay.textContent); // Debug
            setTimeout(() => {
                console.log("TextContent after 100ms:", countDisplay.textContent); // Debug sau 100ms
            }, 100);
        }

        function toggleCheckAll() {
            const isChecked = checkAll.checked;
            console.log("Toggle all, isChecked:", isChecked); // Debug
            document.querySelectorAll(".row-checkbox").forEach(cb => cb.checked = isChecked);
            updateSelectedCount();
        }

        // Gắn sự kiện cho checkAll
        document.querySelector("th[onclick='toggleCheckAll()']").onclick = function () {
            checkAll.checked = !checkAll.checked;
            toggleCheckAll();
        };
        checkAll.addEventListener("change", toggleCheckAll);

        // Gắn sự kiện cho từng checkbox
        document.querySelectorAll(".row-checkbox").forEach(cb => {
            cb.addEventListener("change", () => {
                console.log("Checkbox changed:", cb.value); // Debug
                updateSelectedCount();
            });
        });

        // Cập nhật lần đầu
        updateSelectedCount();
    });
</script>

<script>
    function showConfirmModal() {
        const checkedBoxes = document.querySelectorAll(".student-checkbox:checked");
        console.log("Checked:", checkedBoxes);

        if (checkedBoxes.length === 0) {
            alert("Vui lòng chọn ít nhất 1 học sinh.");
            return;
        }

        const listContainer = document.getElementById("selectedStudentList");
        if (!listContainer) {
            console.error("selectedStudentList element not found");
            return;
        }
        listContainer.innerHTML = ""; // Xóa nội dung cũ

        checkedBoxes.forEach((cb, index) => {
            const name = cb.getAttribute("data-name") || "Unknown";
            const id = cb.value || "Unknown";

            // Lấy hàng <tr> chứa checkbox
            const row = cb.closest("tr");
            if (!row) {
                console.error(`Row not found for checkbox ${index + 1}`);
                return;
            }

            // Lấy thông tin từ các cột <td>
            const gender = row.querySelector("td:nth-child(5)")?.textContent.trim() || "Unknown";
            const birth = row.querySelector("td:nth-child(6)")?.textContent.trim() || "Unknown";
            const phone = row.querySelector("td:nth-child(7)")?.textContent.trim() || "Unknown";

            console.log("Checkbox " + (index + 1) + " - ID: " + id + ", Name: " + name + ", Gender: " + gender + ", Birth: " + birth + ", Phone: " + phone);

            const li = document.createElement("li");
            li.classList.add("py-2", "border-b", "border-gray-200");
            // Sử dụng nối chuỗi để hiển thị theo thứ tự: ID, Tên, Giới tính, Ngày sinh, Số điện thoại
            li.innerHTML =
                    "<div><strong>ID:</strong> " + id + "</div>" +
                    "<div><strong>Tên:</strong> " + name + "</div>" +
                    "<div><strong>Giới tính:</strong> " + gender + "</div>" +
                    "<div><strong>Ngày sinh:</strong> " + birth + "</div>" +
                    "<div><strong>Số điện thoại:</strong> " + phone + "</div>";
            listContainer.appendChild(li);
        });
        console.log("Modal list content:", listContainer.innerHTML);

        const modal = document.getElementById("confirmModal");
        if (!modal) {
            console.error("Modal element not found");
            return;
        }

        setTimeout(() => {
            modal.classList.remove("hidden");
            console.log("Modal displayed, classes:", modal.className);
        }, 0);
    }
    function closeModal() {
        document.getElementById('confirmModal').classList.add('hidden');
    }

    function submitSelectedStudents() {
        const selected = document.querySelectorAll('.student-checkbox:checked');

        if (selected.length === 0) {
            alert("Vui lòng chọn ít nhất 1 học sinh.");
            return;
        }

        const form = document.createElement('form');
        form.method = 'POST';
        form.action = 'admin?tab=studentInClass&action=ADD_STUDENT';

        // Gửi groupId
        const groupIdInput = document.createElement('input');
        groupIdInput.type = 'hidden';
        groupIdInput.name = 'groupId';
        groupIdInput.value = "${selectedClassGroupId}";
        form.appendChild(groupIdInput);

        // Gửi courseId (id)
        const courseIdInput = document.createElement('input');
        courseIdInput.type = 'hidden';
        courseIdInput.name = 'id';
        courseIdInput.value = "${param.id}";
        form.appendChild(courseIdInput);

        // Gửi các studentIds
        selected.forEach(cb => {
            const input = document.createElement('input');
            input.type = 'hidden';
            input.name = 'studentIds';
            input.value = cb.value;
            form.appendChild(input);
        });

        document.body.appendChild(form);
        form.submit();
        console.log("Submit event fired");
    }

</script>

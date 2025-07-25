<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Quản Lý Khóa Học</title>
<script src="https://cdn.tailwindcss.com"></script>
<link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap" rel="stylesheet">

<div class="bg-gray-50 min-h-screen">


    <div class="container mx-auto px-4 py-8">
        <div class="flex justify-end mb-8">
    <button onclick="openAddCourseModal()" class="bg-blue-600 hover:bg-blue-700 text-white px-4 py-2 rounded-lg flex items-center">
        <i class="fas fa-plus mr-2"></i> Thêm khoá học mới
    </button>
</div>

        <div class="flex flex-col md:flex-row gap-4 mb-6">
            <input type="text" id="searchClass" placeholder="🔍 Tìm khoá..." class="form-control w-full md:w-1/3">

            <select id="filterSubject" class="form-control w-full md:w-1/3">
                <option value="">📘 Tất cả môn học</option>
                <c:forEach var="s" items="${subjects}">
                    <option value="${s.subjectName}">${s.subjectName}</option>
                </c:forEach>
            </select>


            <select id="filterGrade" class="form-control w-full md:w-1/3">
                <option value="">🏷️ Tất cả khối</option>
                <c:forEach var="g" items="${grades}">
                    <option value="${g.gradeName}">${g.gradeName}</option>
                </c:forEach>
            </select>

        </div>


        <div class="mb-8 bg-gray-50 p-6 rounded-xl shadow-sm">
            <h2 class="text-xl font-semibold text-gray-700 mb-6">Chi Tiết Khóa Học</h2>
            <form class="grid grid-cols-1 md:grid-cols-2 gap-4">

                <div>
                    <label class="block text-sm font-medium text-gray-600">Tên Khóa Học</label>
                    <input type="text" value="${c.className}" readonly class="mt-1 block w-full border border-gray-300 rounded-lg p-3 bg-gray-100 text-gray-600">
                </div>

                <div>
                    <label class="block text-sm font-medium text-gray-600">Loại Khóa Học</label>
                    <div class="mt-1 block w-full border border-gray-300 rounded-lg p-3 bg-gray-100 text-gray-600 min-h-[44px]">
                        <c:choose>
                            <c:when test="${not empty c}">
                                ${c.isHot ? "Nổi bật" : "Quanh Năm"}
                            </c:when>
                            <c:otherwise>
                                &nbsp; <!-- giữ độ cao khi chưa có dữ liệu -->
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>



                <div>
                    <label class="block text-sm font-medium text-gray-600">Khối</label>
                    <div class="mt-1 block w-full border border-gray-300 rounded-lg p-3 bg-gray-100 text-gray-600 min-h-[44px]">
                        <c:choose>
                            <c:when test="${not empty c}">
                                <c:forEach items="${grades}" var="g">
                                    <c:if test="${g.gradeID == c.gradeID}">
                                        ${g.gradeName}
                                    </c:if>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                &nbsp;
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>

                <div>
                    <label class="block text-sm font-medium text-gray-600">Môn</label>
                    <div class="mt-1 block w-full border border-gray-300 rounded-lg p-3 bg-gray-100 text-gray-600 min-h-[44px]">
                        <c:choose>
                            <c:when test="${not empty c}">
                                <c:forEach items="${subjects}" var="s">
                                    <c:if test="${s.subjectId == c.subjectID}">
                                        ${s.subjectName}
                                    </c:if>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                &nbsp;
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>



                <div>
                    <label class="block text-sm font-medium text-gray-600">Ngày Bắt Đầu</label>
                    <input type="date" value="${c.startDate}" readonly class="mt-1 block w-full border border-gray-300 rounded-lg p-3 bg-gray-100 text-gray-600">
                </div>

                <div>
                    <label class="block text-sm font-medium text-gray-600">Ngày Kết Thúc</label>
                    <input type="date" value="${c.endDate}" readonly class="mt-1 block w-full border border-gray-300 rounded-lg p-3 bg-gray-100 text-gray-600">
                </div>

                <div>
                    <label class="block text-sm font-medium text-gray-600">Giá</label>
                    <div class="mt-1 block w-full border border-gray-300 rounded-lg p-3 bg-gray-100 text-gray-600 min-h-[44px]">
                        <c:choose>
                            <c:when test="${not empty c}">
                                ${c.price} <c:out value="${c.isHot ? '/ 1 khoá' : '/ 1 buổi'}" />
                            </c:when>
                            <c:otherwise>
                                &nbsp;
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>


                <div class="mb-6">
                    <label class="block text-sm font-medium text-gray-700 mb-1">Ảnh khóa học</label>
                    <div class="mt-1">
                        <img id="courseImagePreview"
                             src="<c:choose>
                                 <c:when test='${not empty c.image}'>
                                     ${pageContext.request.contextPath}/LogoServlet?type=manual&filename=${fn:escapeXml(c.image)}
                                 </c:when>
                                 <c:otherwise>
                                     ${pageContext.request.contextPath}/images/default.jpg

                                 </c:otherwise>
                             </c:choose>"
                             alt="Course preview"
                             class="max-h-28 rounded-md object-contain border border-gray-300">
                    </div>
                </div>

                <div class="md:col-span-2">
                    <label class="block text-sm font-medium text-gray-600">Mô Tả</label>
                    <textarea rows="4" readonly class="mt-1 block w-full border border-gray-300 rounded-lg p-3 bg-gray-100 text-gray-600">${c.descrip}</textarea>
                </div>
            </form>
        </div>



        <!-- Danh sách khóa học -->
        <div>
            <h2 class="text-xl font-semibold text-gray-700 mb-6">Danh Sách Khóa Học</h2>
            <div class="overflow-x-auto">
                <table class="w-full min-w-[1000px] border-collapse bg-white rounded-lg shadow-sm text-sm">
                    <thead >
                        <tr style="background-color: #FFF1F1; color: black;">
                            <th class="p-4 text-left w-24">Ảnh</th>
                            <th class="p-4 text-left w-48">Tên</th>
                            <th class="p-4 text-left w-64">Mô Tả</th>
                            <th class="p-4 text-left w-24">Khối</th>
                            <th class="p-4 text-left w-32">Môn</th>
                            <th class="p-4 text-left w-24">Loại</th>
                            <th class="p-4 text-left w-28">Bắt Đầu</th>
                            <th class="p-4 text-left w-28">Kết Thúc</th>
                            <th class="p-4 text-left w-24">Giá (VND)</th>
                            <th class="p-3 text-left">Trạng thái</th>
                            <th class="p-4 text-left w-24">Hành Động</th>
                        </tr>
                    </thead>
                    <tbody class="divide-y divide-gray-200">
                        <c:forEach items="${data}" var="item">
                            <tr class="hover:bg-gray-50 transition">
                                <td class="p-4">
                                    <img src="${pageContext.request.contextPath}/LogoServlet?type=tutoring&tutoringClassId=${item.tutoringClassID}" 
                                         width="64" height="64" 
                                         alt="${item.className}" class="object-cover rounded-lg">
                                </td>
                                <td class="p-4">
                                    <a href="admin?tab=courseManagement&id=${item.tutoringClassID}" 
                                       class="text-blue-600 hover:underline">${item.className}</a>
                                </td>
                                <td class="p-4">
                                    <c:choose>
                                        <c:when test="${fn:length(item.descrip) > 50}">
                                            ${fn:substring(item.descrip, 0, 50)}...
                                        </c:when>
                                        <c:otherwise>
                                            ${item.descrip}
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td class="p-4">
                                    <c:forEach items="${grades}" var="g">
                                        <c:if test="${g.gradeID == item.gradeID}">
                                            ${g.gradeName}
                                        </c:if>
                                    </c:forEach>
                                </td>
                                <td class="p-4">
                                    <c:forEach items="${subjects}" var="s">
                                        <c:if test="${s.subjectId == item.subjectID}">
                                            ${s.subjectName}
                                        </c:if>
                                    </c:forEach>
                                </td>
                                <td class="p-4">
                                    <c:choose>
                                        <c:when test="${item.isHot}">Nổi bật</c:when>
                                        <c:otherwise>Quanh năm</c:otherwise>
                                    </c:choose>
                                </td>
                                <td class="p-4">
                                    <fmt:formatDate value="${item.startDate}" pattern="dd/MM/yyyy" />
                                </td>
                                <td class="p-4">
                                    <fmt:formatDate value="${item.endDate}" pattern="dd/MM/yyyy" />
                                </td>
                                <td class="p-4">
                                    <fmt:formatNumber value="${item.price}" type="number" groupingUsed="true" />
                                    <c:choose>
                                        <c:when test="${item.isHot}"> / 1 khoá</c:when>
                                        <c:otherwise> / 1 buổi</c:otherwise>
                                    </c:choose>
                                </td>
                                <td class="p-3">
                                    <c:choose>
                                        <c:when test="${item.isActive == 0}">
                                            <span class="text-yellow-600 font-medium">Sắp mở</span>
                                        </c:when>
                                        <c:when test="${item.isActive == 1}">
                                            <span class="text-green-600 font-medium">Đang mở</span>
                                        </c:when>
                                        <c:when test="${item.isActive == 2}">
                                            <span class="text-gray-500 font-medium">Đã đóng</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="text-red-600 font-medium">Không rõ</span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td class="p-4">
                                    <div class="flex space-x-2">
                                        <button type="button" class="text-blue-600 hover:text-blue-800"
                                                data-id="${item.tutoringClassID}"
                                                data-name="${item.className}"
                                                data-descrip="${item.descrip}"
                                                data-subjectid="${item.subjectID}"
                                                data-gradeid="${item.gradeID}"
                                                data-price="${item.price}"
                                                data-start="${item.startDate}"
                                                data-end="${item.endDate}"
                                                data-hot="${item.isHot}"
                                                data-image="${item.image}"
                                                onclick="openEditClassModal(this)">
                                            <i class="fas fa-edit"></i>
                                        </button>


                                        <button onclick="confirmDeleteClass('${item.tutoringClassID}')" 
                                                class="text-red-600 hover:text-red-800">
                                            <i class="fas fa-trash"></i>
                                        </button>
                                    </div>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
<!-- Modal add -->
<div id="addCourseModal" class="fixed inset-0 bg-black bg-opacity-50 hidden flex items-center justify-center z-50">
    <div class="bg-white rounded-xl p-8 max-w-3xl w-full relative max-h-[90vh] overflow-y-auto">
        <!-- Nút đóng -->
        <button onclick="closeAddCourseModal()" class="absolute top-3 right-3 text-gray-600 hover:text-red-600 text-xl">&times;</button>
        <h2 class="text-2xl font-bold mb-6 text-gray-800">Thêm Khóa Học Mới</h2>
        <form action="admin?tab=courseManagement" method="post" enctype="multipart/form-data" class="grid grid-cols-1 md:grid-cols-2 gap-4" id="addCourseFormModal">
            <input type="hidden" name="selectedClassId" value="${c.tutoringClassID}">


            <div>
                <label class="block text-sm font-medium text-gray-600">Tên Khóa Học</label>
                <input type="text" name="name" value="${cModal.className}" class="mt-1 block w-full border border-gray-300 rounded-lg p-3" required>
            </div>

            <div>
                <label class="block text-sm font-medium text-gray-600">Loại Khóa Học</label>
                <select name="isHot" class="mt-1 block w-full border border-gray-300 rounded-lg p-3" required>
                    <option value="">-- Chọn --</option>
                    <option value="0" ${cModal.isHot == false ? 'selected' : ''}>Quanh Năm</option>
                    <option value="1" ${cModal.isHot == true ? 'selected' : ''}>Nổi bật</option>
                </select>

            </div>

            <div>
                <label class="block text-sm font-medium text-gray-600">Khối</label>
                <select name="grade" class="mt-1 block w-full border border-gray-300 rounded-lg p-3" required>
                    <option value="">-- Chọn --</option>
                    <c:forEach items="${grades}" var="g">
                        <option value="${g.gradeID}"
                                <c:if test="${g.gradeID == cModal.gradeID}">selected</c:if>>
                            ${g.gradeName}
                        </option>
                    </c:forEach>
                </select>

            </div>

            <div>
                <label class="block text-sm font-medium text-gray-600">Môn</label>
                <select name="subject" class="mt-1 block w-full border border-gray-300 rounded-lg p-3" required>
                    <option value="">-- Chọn --</option>
                    <c:forEach items="${subjects}" var="s">
                        <option value="${s.subjectId}"
                                <c:if test="${s.subjectId == cModal.subjectID}">selected</c:if>>
                            ${s.subjectName}
                        </option>
                    </c:forEach>
                </select>

            </div>

            <div>
                <label class="block text-sm font-medium text-gray-600">Ngày Bắt Đầu</label>
                <input type="date" name="startDate"
                       value="<fmt:formatDate value='${cModal.startDate}' pattern='yyyy-MM-dd' />"
                       class="mt-1 block w-full border border-gray-300 rounded-lg p-3" required>

            </div>

            <div>
                <label class="block text-sm font-medium text-gray-600">Ngày Kết Thúc</label>
                <input type="date" name="endDate"
                       value="<fmt:formatDate value='${cModal.endDate}' pattern='yyyy-MM-dd' />"
                       class="mt-1 block w-full border border-gray-300 rounded-lg p-3" required>
            </div>

            <div>
                <label class="block text-sm font-medium text-gray-600">Giá (VND)</label>
                <input type="number" name="price" value="${cModal.price}" class="mt-1 block w-full border border-gray-300 rounded-lg p-3" required>
            </div>

            <div class="mb-6">
                <label class="block text-sm font-medium text-gray-700 mb-1">Ảnh khóa học</label>
                <div class="mt-1 flex items-center">
                    <div class="mr-4">
                        <img id="modalCourseImagePreview"
                             src="<c:choose>
                                 <c:when test='${not empty cModal.image}'>
                                     ${pageContext.request.contextPath}/LogoServlet?type=manual&filename=${fn:escapeXml(cModal.image)}
                                 </c:when>
                                 <c:otherwise>
                                     ${pageContext.request.contextPath}/images/default.jpg


                                 </c:otherwise>
                             </c:choose>"
                             alt="Course preview"
                             class="max-h-28 rounded-md object-contain border border-gray-300">


                    </div>
                    <div>
                        <input type="file" id="courseImage" name="courseImageFile" class="hidden" accept="image/*" onchange="previewModalCourseImage(this)">
                        <button type="button" onclick="document.getElementById('courseImage').click()"
                                class="px-4 py-2 bg-blue-600 text-white rounded-md">
                            <i class="fas fa-upload mr-2"></i> Chọn ảnh mới
                        </button>
                        <p class="mt-1 text-xs text-gray-500">PNG, JPG hoặc GIF (tối đa 2MB)</p>
                    </div>
                </div>

                <!-- Tên ảnh cũ -->
                <input type="hidden" name="oldImage" value="${cModal.image}">
            </div>
            <div class="md:col-span-2">
                <label class="block text-sm font-medium text-gray-600">Mô Tả</label>
                <textarea name="description" rows="4" class="mt-1 block w-full border border-gray-300 rounded-lg p-3" required>${cModal.descrip}</textarea>
            </div>

            <!-- THÔNG BÁO LỖI -->
            <c:if test="${not empty errorList}">
                <div class="md:col-span-2 text-red-600 font-medium">
                    <ul class="list-disc pl-4">
                        <c:forEach var="err" items="${errorList}">
                            <li>${err}</li>
                            </c:forEach>
                    </ul>
                </div>
            </c:if>



            <div class="md:col-span-2 flex justify-end">
                <button type="button" onclick="closeAddCourseModal()" class="px-4 py-2 bg-gray-300 hover:bg-gray-400 rounded">Hủy</button>
                <button type="submit" name="action" value="ADD" class="bg-blue-600 text-white px-6 py-3 rounded-lg">Thêm</button>

            </div>
        </form>
    </div>
</div>

<!-- Modal Chỉnh sửa khóa học -->
<div id="editCourseModal" class="fixed inset-0 bg-black bg-opacity-50 hidden flex items-center justify-center z-50">
    <div class="bg-white rounded-xl p-8 max-w-3xl w-full relative max-h-[90vh] overflow-y-auto">
        <!-- Nút đóng -->
        <button onclick="closeEditCourseModal()" class="absolute top-3 right-3 text-gray-600 hover:text-red-600 text-xl">×</button>
        <h2 class="text-2xl font-bold mb-6 text-gray-800">Cập Nhật Khóa Học</h2>

        <form action="admin?tab=courseManagement" method="post" enctype="multipart/form-data" class="grid grid-cols-1 md:grid-cols-2 gap-4" id="editCourseFormModal">
            <input type="hidden" name="action" value="UPDATE">
            <input type="hidden" id="editSelectedClassId" name="selectedClassId">
            <input type="hidden" id="editOldImage" name="oldImage">

            <!-- Tên khóa học -->
            <div>
                <label class="block text-sm font-medium text-gray-600">Tên Khóa Học</label>
                <input type="text" id="editName" name="name" class="mt-1 block w-full border border-gray-300 rounded-lg p-3" required>
            </div>

            <!-- Loại khóa học -->
            <div>
                <label class="block text-sm font-medium text-gray-600">Loại Khóa Học</label>
                <select id="editIsHot" name="isHot" class="mt-1 block w-full border border-gray-300 rounded-lg p-3" required>
                    <option value="">-- Chọn --</option>
                    <option value="true">Nổi bật</option>
                    <option value="false">Quanh năm</option>
                </select>
            </div>

            <!-- Khối -->
            <div>
                <label class="block text-sm font-medium text-gray-600">Khối</label>
                <select id="editGrade" name="grade" class="mt-1 block w-full border border-gray-300 rounded-lg p-3" required>
                    <option value="">-- Chọn --</option>
                    <c:forEach items="${grades}" var="g">
                        <option value="${g.gradeID}">${g.gradeName}</option>
                    </c:forEach>
                </select>
            </div>

            <!-- Môn -->
            <div>
                <label class="block text-sm font-medium text-gray-600">Môn</label>
                <select id="editSubject" name="subject" class="mt-1 block w-full border border-gray-300 rounded-lg p-3" required>
                    <option value="">-- Chọn --</option>
                    <c:forEach items="${subjects}" var="s">
                        <option value="${s.subjectId}">${s.subjectName}</option>
                    </c:forEach>
                </select>
            </div>

            <!-- Ngày bắt đầu -->
            <div>
                <label class="block text-sm font-medium text-gray-600">Ngày Bắt Đầu</label>
                <input type="date" id="editStartDate" name="startDate" class="mt-1 block w-full border border-gray-300 rounded-lg p-3" required>
            </div>

            <!-- Ngày kết thúc -->
            <div>
                <label class="block text-sm font-medium text-gray-600">Ngày Kết Thúc</label>
                <input type="date" id="editEndDate" name="endDate" class="mt-1 block w-full border border-gray-300 rounded-lg p-3" required>
            </div>

            <!-- Giá -->
            <div>
                <label class="block text-sm font-medium text-gray-600">Giá (VND)</label>
                <input type="number" id="editPrice" name="price" class="mt-1 block w-full border border-gray-300 rounded-lg p-3" required>
            </div>

            <!-- Ảnh -->
            <div class="mb-6">
                <label class="block text-sm font-medium text-gray-700 mb-1">Ảnh khóa học</label>
                <div class="mt-1 flex items-center">
                    <div class="mr-4">
                        <img id="editCourseImagePreview"
                             src="<c:choose>
                                 <c:when test='${not empty param.image}'>
                                     ${pageContext.request.contextPath}/LogoServlet?type=manual&filename=${fn:escapeXml(c.image)}

                                 </c:when>
                                 <c:otherwise>
                                     ${pageContext.request.contextPath}/images/default.jpg
                                 </c:otherwise>
                             </c:choose>"
                             alt="Course preview"
                             class="max-h-28 rounded-md object-contain border border-gray-300"
                             onerror="this.src='${pageContext.request.contextPath}/images/default.jpg';">
                    </div>
                    <div>
                        <input type="file" id="editCourseImage" name="courseImageFile" class="hidden" accept="image/*" onchange="previewEditCourseImage(this)">
                        <button type="button" onclick="document.getElementById('editCourseImage').click()" class="px-4 py-2 bg-blue-600 text-white rounded-md">
                            <i class="fas fa-upload mr-2"></i> Chọn ảnh mới
                        </button>
                        <p class="mt-1 text-xs text-gray-500">PNG, JPG hoặc GIF (tối đa 2MB)</p>
                    </div>
                </div>
            </div>

            <!-- Mô tả -->
            <div class="md:col-span-2">
                <label class="block text-sm font-medium text-gray-600">Mô Tả</label>
                <textarea id="editDescription" name="description" rows="4" class="mt-1 block w-full border border-gray-300 rounded-lg p-3" required></textarea>
            </div>

            <div class="md:col-span-2 flex justify-end">
                <button type="button" onclick="closeEditCourseModal()" class="px-4 py-2 bg-gray-300 hover:bg-gray-400 rounded">Hủy</button>
                <button type="submit" class="bg-blue-600 text-white px-6 py-3 rounded-lg">Cập Nhật</button>
            </div>
        </form>
    </div>
</div>



<c:if test="${modalError}">
    <script>
        window.addEventListener('DOMContentLoaded', function () {
            openAddCourseModal(true); // <-- mở lại modal mà không reset
        });
    </script>
</c:if>

<c:if test="${not empty sessionScope.successMessage}">
    <script>
        window.addEventListener('DOMContentLoaded', function () {
            showToast("<c:out value='${sessionScope.successMessage}'/>");
        });
    </script>
    <c:remove var="successMessage" scope="session" />
</c:if>

<!-- Toast thông báo -->
<div id="toast" style="display: none;"
     class="fixed top-5 right-5 bg-green-500 text-white px-6 py-3 rounded-lg shadow-lg z-50 font-medium transition-all duration-300">
</div>

<script>
    function showToast(message) {
        const toast = document.getElementById("toast");
        if (!toast)
            return;

        toast.textContent = message;
        toast.style.display = "block";

        setTimeout(() => {
            toast.style.display = "none";
        }, 3000);
    }

</script>

<script>
    function openAddCourseModal(fromError = false) {
        document.getElementById('addCourseModal').classList.remove('hidden');
        if (!fromError) {
            // Reset form và preview ảnh khi mở mới
            const form = document.getElementById('addCourseFormModal');
            if (form)
                form.reset();
            const img = document.getElementById('modalCourseImagePreview');
            if (img)
                img.src = "https://placehold.co/96x96?text=No+Image";
    }
    // Nếu fromError = true (mở lại do lỗi): KHÔNG reset, giữ dữ liệu cũ server render
    }


    function closeAddCourseModal() {
        // Ẩn modal (nếu muốn, có thể giữ lại)
        document.getElementById('addCourseModal').classList.add('hidden');
        // Tải lại trang để đảm bảo mọi dữ liệu, form, biến đều về trạng thái ban đầu
        window.location.reload();
    }

    function previewModalCourseImage(input) {
        if (input.files && input.files[0]) {
            const reader = new FileReader();
            reader.onload = function (e) {
                document.getElementById('modalCourseImagePreview').src = e.target.result;
            };
            reader.readAsDataURL(input.files[0]);
        }
    }

</script>

<script>
    document.addEventListener("DOMContentLoaded", function () {
        const searchClass = document.getElementById("searchClass");
        const filterSubject = document.getElementById("filterSubject");
        const filterGrade = document.getElementById("filterGrade");

        const rows = document.querySelectorAll("tbody tr");

        function normalize(str) {
            return (str || "")
                    .normalize("NFD")
                    .replace(/[\u0300-\u036f]/g, "")
                    .toLowerCase()
                    .trim();
        }

        function applyFilters() {
            const classKeyword = normalize(searchClass.value);
            const selectedSubject = normalize(filterSubject.value);
            const selectedGrade = normalize(filterGrade.value);

            rows.forEach(row => {
                const className = normalize(row.querySelector("td:nth-child(2)")?.textContent);
                const grade = normalize(row.querySelector("td:nth-child(4)")?.textContent);
                const subject = normalize(row.querySelector("td:nth-child(5)")?.textContent);

                const matchClass = className.includes(classKeyword);
                const matchSubject = !selectedSubject || subject === selectedSubject;
                const matchGrade = !selectedGrade || grade === selectedGrade;

                const showRow = matchClass && matchSubject && matchGrade;
                row.style.display = showRow ? "" : "none";
            });
        }

        searchClass.addEventListener("input", applyFilters);
        filterSubject.addEventListener("change", applyFilters);
        filterGrade.addEventListener("change", applyFilters);
    });
</script>

<script>
    const contextPath = '${pageContext.request.contextPath}';

    function openEditClassModal(button) {
        // Lấy dữ liệu từ các thuộc tính data-*
        const id = button.dataset.id;
        const name = button.dataset.name;
        const description = button.dataset.description;
        const subjectID = button.dataset.subjectid;
        const gradeID = button.dataset.gradeid;
        const price = button.dataset.price;
        const start = button.dataset.start;
        const end = button.dataset.end;
        const isHot = button.dataset.ishot === 'true';
        const image = button.dataset.image;

        // Gán dữ liệu vào form
        document.getElementById('editSelectedClassId').value = id;
        document.getElementById('editName').value = name;
        document.getElementById('editDescription').value = description;
        document.getElementById('editPrice').value = price;
        document.getElementById('editStartDate').value = start;
        document.getElementById('editEndDate').value = end;

        // Chọn option phù hợp trong dropdown
        const subjectSelect = document.getElementById('editSubject');
        const gradeSelect = document.getElementById('editGrade');
        const isHotSelect = document.getElementById('editIsHot');

        // Reset selections
        subjectSelect.value = '';
        gradeSelect.value = '';
        isHotSelect.value = '';

        // Set selected options
        for (const option of subjectSelect.options) {
            if (option.value === subjectID) {
                option.selected = true;
                break;
            }
        }
        for (const option of gradeSelect.options) {
            if (option.value === gradeID) {
                option.selected = true;
                break;
            }
        }
        for (const option of isHotSelect.options) {
            if (option.value === (isHot ? "true" : "false")) {
                option.selected = true;
                break;
            }
        }

        // Hiển thị ảnh
        const preview = document.getElementById('editCourseImagePreview');
        if (image && image.trim() !== '') {
            const imageUrl = contextPath + "/LogoServlet?type=manual&filename=" + encodeURIComponent(image);
            preview.src = imageUrl;
            preview.onload = function () {
                // Ảnh tải thành công, không làm gì thêm
            };
            preview.onerror = function () {
                console.log("Failed to load image, using default.");
                this.src = contextPath + "/images/default.jpg";
            };
        } else {
            preview.src = contextPath + "/images/default.jpg";
        }


        // Lưu ảnh cũ vào hidden input
        document.getElementById('editOldImage').value = image || '';

        // Mở modal
        document.getElementById('editCourseModal').classList.remove('hidden');
    }

    function closeEditCourseModal() {
        document.getElementById('editCourseModal').classList.add('hidden');
    }

    function previewEditCourseImage(input) {
        const file = input.files[0];
        if (file) {
            const reader = new FileReader();
            reader.onload = function (e) {
                document.getElementById('editCourseImagePreview').src = e.target.result;
            };
            reader.readAsDataURL(file);
        }
    }
</script>
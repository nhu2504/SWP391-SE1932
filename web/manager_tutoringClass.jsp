<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Quản Lý Khóa Học</title>
<script src="https://cdn.tailwindcss.com"></script>
<link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap" rel="stylesheet">

<div class="bg-gradient-to-br from-blue-50 to-gray-100 min-h-screen flex flex-col items-center p-6 font-inter">
    <div class="w-full max-w-6xl bg-white rounded-2xl shadow-xl p-8">
        <h1 class="text-3xl font-bold text-center text-gray-800 mb-8">Quản Lý Khóa Học</h1>

        <!-- Form thêm/sửa/tìm kiếm khóa học -->
        <div class="mb-8 bg-gray-50 p-6 rounded-xl shadow-sm">
            <h2 class="text-xl font-semibold text-gray-700 mb-6">Thêm, Sửa hoặc Tìm Kiếm Khóa Học</h2>
            <form action="admin?tab=courseManagement" method="post" enctype="multipart/form-data" class="grid grid-cols-1 md:grid-cols-2 gap-4">                
                <div>
                    <label class="block text-sm font-medium text-gray-600">Tên Khóa Học</label>
                    <input type="text" name="name" value="${c.className}" class="mt-1 block w-full border border-gray-300 rounded-lg p-3" required>
                </div>
                <div>
                    <label class="block text-sm font-medium text-gray-600">Loại Khóa Học</label>
                    <select name="isHot" class="mt-1 block w-full border border-gray-300 rounded-lg p-3">
                        <option value="0" ${!c.isHot ? 'selected="selected"' : ''}>Quanh Năm</option>
                        <option value="1" ${c.isHot ? 'selected="selected"' : ''}>Nổi bật</option>

                    </select>
                </div>

                <div>
                    <label class="block text-sm font-medium text-gray-600">Khối</label>
                    <select name="grade" class="mt-1 block w-full border border-gray-300 rounded-lg p-3" required>
                        <c:forEach items="${grades}" var="g">
                            <option value="${g.gradeID}" ${g.gradeID == c.gradeID ? 'selected="selected"' : ''}>
                                ${g.gradeName}
                            </option>
                        </c:forEach>
                    </select>
                </div>

                <div>
                    <label class="block text-sm font-medium text-gray-600">Môn</label>
                    <select name="subject" class="mt-1 block w-full border border-gray-300 rounded-lg p-3" required>
                        <c:forEach items="${subjects}" var="s">
                            <option value="${s.subjectId}" ${s.subjectId == c.subjectID ? 'selected="selected"' : ''}>
                                ${s.subjectName}
                            </option>
                        </c:forEach>
                    </select>
                </div>

                <div>
                    <label class="block text-sm font-medium text-gray-600">Ngày Bắt Đầu</label>
                    <input type="date" name="startDate" value="${c.startDate}" class="mt-1 block w-full border border-gray-300 rounded-lg p-3" required>
                </div>
                <div>
                    <label class="block text-sm font-medium text-gray-600">Ngày Kết Thúc</label>
                    <input type="date" name="endDate" value="${c.endDate}" class="mt-1 block w-full border border-gray-300 rounded-lg p-3" required>
                </div>
                <div>
                    <label class="block text-sm font-medium text-gray-600">Giá (VND)</label>
                    <input type="number" name="price" value="${c.price}" class="mt-1 block w-full border border-gray-300 rounded-lg p-3" required>
                </div>
                <div class="mb-6">
                    <label class="block text-sm font-medium text-gray-700 mb-1">Ảnh khóa học</label>
                    <div class="mt-1 flex items-center">
                        <div class="mr-4">
                            <img id="courseImagePreview"
                                 src="<c:choose>
                                     <c:when test='${not empty c.image}'>
                                         ${pageContext.request.contextPath}/LogoServlet?type=manual&filename=${fn:escapeXml(c.image)}
                                     </c:when>
                                     <c:otherwise>
                                         https://via.placeholder.com/96
                                     </c:otherwise>
                                 </c:choose>"
                                 alt="Course preview"
                                 class="max-h-28 rounded-md object-contain border border-gray-300">


                        </div>
                        <div>
                            <input type="file" id="courseImage" name="courseImageFile" class="hidden" accept="image/*" onchange="previewCourseImage(this)">
                            <button type="button" onclick="document.getElementById('courseImage').click()"
                                    class="px-4 py-2 bg-blue-600 text-white rounded-md">
                                <i class="fas fa-upload mr-2"></i> Chọn ảnh mới
                            </button>
                            <p class="mt-1 text-xs text-gray-500">PNG, JPG hoặc GIF (tối đa 2MB)</p>
                        </div>
                    </div>

                    <!-- Tên ảnh cũ -->
                    <input type="hidden" name="oldImage" value="${c.image}">
                </div>

                <div class="md:col-span-2">
                    <label class="block text-sm font-medium text-gray-600">Mô Tả</label>
                    <textarea name="description" rows="4" class="mt-1 block w-full border border-gray-300 rounded-lg p-3" required>${c.descrip}</textarea>
                </div> 
                <c:if test="${not empty error}">
                    <div class="text-red-600 font-medium mb-4">${error}</div>
                </c:if>
                <div class="md:col-span-2 flex space-x-4">
                    <button type="submit" name="action" value="ADD" class="bg-blue-600 text-white px-4 py-3 rounded-lg">Thêm</button>
                    <button type="submit" name="action" value="UPDATE" class="bg-green-600 text-white px-4 py-3 rounded-lg">Sửa</button>
                    <button type="submit" name="action" value="DELETE" class="bg-red-600 text-white px-4 py-3 rounded-lg">Xoá</button>
                    <button type="submit" name="action" value="SEARCH" class="bg-gray-600 text-white px-4 py-3 rounded-lg">Tìm kiếm</button>
                </div>

            </form>
        </div>

        <!-- Danh sách khóa học -->
        <div>
            <h2 class="text-xl font-semibold text-gray-700 mb-6">Danh Sách Khóa Học</h2>
            <div class="overflow-x-auto">
                <table class="w-full border-collapse bg-white rounded-lg shadow-sm">
                    <thead>
                        <tr class="bg-blue-600 text-white">
                            <th class="p-4 text-left">Ảnh</th>
                            <th class="p-4 text-left">Tên</th>
                            <th class="p-4 text-left">Mô Tả</th>
                            <th class="p-4 text-left">Khối</th>
                            <th class="p-4 text-left">Môn</th>
                            <th class="p-4 text-left">Loại</th>

                            <th class="p-4 text-left">Bắt Đầu</th>
                            <th class="p-4 text-left">Kết Thúc</th>
                            <th class="p-4 text-left">Giá (VND)</th>
                            <th class="p-4 text-left">Hành Động</th>
                        </tr>
                    </thead>
                    <tbody class="divide-y divide-gray-200">
                        <c:forEach items="${data}" var="item">
                            <tr class="hover:bg-gray-50 transition">
                                <td class="p-4">
                                    <img src="${pageContext.request.contextPath}/LogoServlet?type=tutoring&tutoringClassId=${item.tutoringClassID}"      
                                         width="64" height="64" alt="${item.className}"      
                                         class="object-cover rounded-lg">
                                </td>
                                <td class="p-4">
                                    <a href="admin?tab=courseManagement&id=${item.tutoringClassID}" class="text-blue-600 hover:underline">${item.className}</a>

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

                                <td class="p-4">${item.startDate}</td>
                                <td class="p-4">${item.endDate}</td>
                                <td class="p-4">${item.price}</td>
                                <td class="p-4">
                                    <a href="tutoringclass?id=${item.tutoringClassID}&mode=2" class="bg-red-500 text-white px-3 py-2 rounded-lg hover:bg-red-600 transition">Xóa</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>


<script>
    function previewCourseImage(input) {
        if (input.files && input.files[0]) {
            const reader = new FileReader();
            reader.onload = function (e) {
                document.getElementById('courseImagePreview').src = e.target.result;
            };
            reader.readAsDataURL(input.files[0]);
        }
    }
</script>



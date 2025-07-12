<%-- 
    Document   : createnotification
    Created on : Jul 9, 2025, 9:59:06 PM
    Author     : NGOC ANH
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Tạo Thông Báo</title>
        <script src="https://cdn.tailwindcss.com"></script>
        
    </head>
    <body class="bg-gradient-to-br from-blue-50 to-gray-100 min-h-screen flex flex-col items-center p-6 font-sans">
        <div class="w-full bg-white rounded-2xl shadow-lg p-10 px-[1cm] mx-auto" ">

            <h1 class="text-3xl font-bold text-center text-gray-800 mb-10">Tạo thông báo</h1>

            <form action="createnotification" method="post" class="space-y-6">
                <div>
                    <label class="block font-semibold mb-1">Tiêu đề:</label>
                    <textarea name="title" rows="2" required
                               class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"></textarea>

                    
                          
                </div>

                <div>
                    <label class="block font-semibold mb-1">Nội dung:</label>
                    <textarea name="content" rows="4" required
                              class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"></textarea>
                </div>

                <div class="flex items-center space-x-2">
                    <input type="checkbox" name="isImportant" value="1" class="accent-red-500">
                    <label class="font-medium">Quan trọng</label>
                </div>

                <div>
                    <label class="block font-semibold mb-2">Gửi đến:</label>
                    <div class="space-y-2">
                        <label class="flex items-center space-x-2">
                            <input type="radio" name="targetType" value="all" checked onclick="showTarget('none')" class="accent-blue-500">
                            <span>Tất cả</span>
                        </label>
                        <label class="flex items-center space-x-2">
                            <input type="radio" name="targetType" value="role" onclick="showTarget('role')" class="accent-blue-500">
                            <span>Theo vai trò</span>
                        </label>
                        <label class="flex items-center space-x-2">
                            <input type="radio" name="targetType" value="user" onclick="showTarget('user')" class="accent-blue-500">
                            <span>Người dùng cụ thể</span>
                        </label>
                    </div>
                </div>

                <div id="roleSelect" style="display:none;" class="pl-4 border-l-4 border-blue-200">
                    <label class="block font-semibold mb-1 mt-4">Chọn vai trò:</label>
                    <c:forEach var="role" items="${allRoles}">
                        <label class="block">
                            <input type="checkbox" name="roles" value="${role.roleID}" class="mr-2 accent-blue-500">
                            ${role.roleName}
                        </label>
                    </c:forEach>
                </div>

                <div id="userSelect" style="display:none;" class="pl-4 border-l-4 border-blue-200">
                    <label class="block font-semibold mb-1 mt-4">Chọn người dùng:</label>
                    <c:forEach var="user" items="${allUsers}">
                        <label class="block">
                            <input type="checkbox" name="recipients" value="${user.id}" class="mr-2 accent-blue-500">
                            ${user.fullName} (${user.roleName})
                        </label>
                    </c:forEach>
                </div>

                <div class="text-center">
                    <button type="submit"
                            class="bg-blue-600 text-white font-semibold px-6 py-2 rounded-lg hover:bg-blue-700 transition duration-300">
                        Gửi thông báo
                    </button>
                </div>
            </form>
        </div>

        <script>
            function showTarget(target) {
                document.getElementById('roleSelect').style.display = (target === 'role') ? 'block' : 'none';
                document.getElementById('userSelect').style.display = (target === 'user') ? 'block' : 'none';
            }
        </script>
    </body>
</html>

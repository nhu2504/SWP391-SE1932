<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!-- Văn Thị Như - HE181329 
Ngày update 3/7/2025-->
<div class="max-w-4xl mx-auto">

    <!-- FORM CHỈNH SỬA THÔNG TIN TRUNG TÂM -->
    <div class="bg-white rounded-lg shadow-md overflow-hidden mb-8">
        <div class="px-6 py-4 border-b border-gray-200 bg-gray-50">
            <h2 class="text-lg font-semibold text-gray-800">Chỉnh sửa thông tin trung tâm</h2>
        </div>
        <div class="p-6">
            <form id="centerForm" action="${pageContext.request.contextPath}/UpdateCenterInfoServlet" method="post" enctype="multipart/form-data">
                <div class="mb-6">
                    <label for="centerName" class="block text-sm font-medium text-gray-700 mb-1">Tên trung tâm</label>
                    <input type="text" id="centerName" name="centerName"
                           class="w-full px-4 py-2 rounded-md border border-gray-300"
                           value="${centerName}">
                </div>

                <div class="mb-6">
                    <label for="description" class="block text-sm font-medium text-gray-700 mb-1">Mô tả</label>
                    <textarea id="description" name="descripCenter" rows="5"
                              class="w-full px-4 py-2 rounded-md border border-gray-300">${descripCenter}</textarea>
                </div>

                <div class="grid md:grid-cols-2 gap-6 mb-6">
                    <div>
                        <label for="address" class="block text-sm font-medium text-gray-700 mb-1">Địa chỉ</label>
                        <input type="text" id="address" name="address"
                               class="w-full px-4 py-2 rounded-md border border-gray-300"
                               value="${address}">
                    </div>
                    <div>
                        <label for="phone" class="block text-sm font-medium text-gray-700 mb-1">Số điện thoại</label>
                        <input type="tel" id="phone" name="phone"
                               class="w-full px-4 py-2 rounded-md border border-gray-300"
                               value="${phone}">
                    </div>
                </div>

                <div class="grid md:grid-cols-2 gap-6 mb-6">
                    <div>
                        <label for="email" class="block text-sm font-medium text-gray-700 mb-1">Email</label>
                        <input type="email" id="email" name="email"
                               class="w-full px-4 py-2 rounded-md border border-gray-300"
                               value="${email}">
                    </div>
                    <div>
                        <label for="website" class="block text-sm font-medium text-gray-700 mb-1">Website</label>
                        <input type="url" id="website" name="website"
                               class="w-full px-4 py-2 rounded-md border border-gray-300"
                               value="${website}">
                    </div>
                </div>

                <div class="mb-6">
                    <label class="block text-sm font-medium text-gray-700 mb-1">Logo trung tâm</label>
                    <div class="mt-1 flex items-center">
                        <div class="mr-4">
                            <img id="logoPreview"
                                 src="${pageContext.request.contextPath}/LogoServlet"
                                 alt="Logo preview"
                                 class="h-24 w-24 rounded-md object-cover"
                                 onerror="this.src='${pageContext.request.contextPath}/images/fallback.png';">
                        </div>
                        <div>
                            <input type="file" id="logo" name="logoFile" class="hidden" accept="image/*">
                            <button type="button" onclick="document.getElementById('logo').click()"
                                    class="px-4 py-2 bg-blue-600 text-white rounded-md">
                                <i class="fas fa-upload mr-2"></i> Tải lên logo mới
                            </button>
                            <p class="mt-1 text-xs text-gray-500">PNG, JPG hoặc GIF (tối đa 2MB)</p>
                        </div>
                    </div>
                </div>

                <div class="flex justify-end space-x-3 pt-6 border-t border-gray-200">
                    <button type="reset"
                            class="px-4 py-2 border border-gray-300 rounded-md text-gray-700 hover:bg-gray-50">
                        Hủy bỏ
                    </button>
                    <button type="submit"
                            class="px-6 py-2 bg-blue-600 text-white rounded-md hover:bg-blue-700">
                        <i class="fas fa-save mr-2"></i> Lưu thông tin
                    </button>
                </div>
            </form>
        </div>
    </div>

    <!-- QUẢN LÝ BANNER -->
    <div class="bg-white rounded-lg shadow-md p-6">
        <h3 class="text-lg font-semibold text-gray-800 mb-4">Danh sách Banner trung tâm</h3>

        <div class="mb-6">
            <c:choose>
                <c:when test="${not empty banners}">
                    <div class="grid grid-cols-1 gap-5">
                        <c:forEach var="banner" items="${banners}" varStatus="loop">
                            <div class="flex items-center bg-gray-50 rounded-lg p-4 shadow-sm mb-3">
                                <img src="${pageContext.request.contextPath}/LogoServlet?type=banner&bannerID=${banner.bannerID}"
                                     class="h-24 w-auto rounded-md object-contain bg-white border border-gray-200"
                                     alt="Banner ${loop.index + 1}" style="max-width: 320px;" />
                                <form action="${pageContext.request.contextPath}/BannerServlet"
                                      method="post"
                                      class="ml-6 banner-delete-form">
                                    <input type="hidden" name="action" value="delete" />
                                    <input type="hidden" name="bannerID" value="${banner.bannerID}" />
                                    <button type="submit"
                                            class="px-4 py-2 bg-red-600 text-white rounded-md hover:bg-red-700 ml-2">
                                        Xoá
                                    </button>
                                </form>
                            </div>
                        </c:forEach>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="text-center text-gray-400 py-8">Chưa có banner nào.</div>
                </c:otherwise>
            </c:choose>
        </div>

        <form action="${pageContext.request.contextPath}/BannerServlet"
              method="post"
              enctype="multipart/form-data"
              class="flex flex-col md:flex-row md:items-center gap-4 w-full banner-add-form">
            <input type="hidden" name="action" value="add" />
            <input type="file" name="bannerImage" accept="image/*"
                   class="form-control flex-grow rounded border border-gray-300 px-2 py-2" required>
            <button type="submit"
                    class="px-6 py-2 bg-blue-600 text-white rounded-md hover:bg-blue-700">
                <i class="fas fa-upload mr-2"></i> Thêm banner mới
            </button>
        </form>
        <p class="mt-1 text-xs text-gray-500">PNG, JPG hoặc GIF (tối đa 5MB)</p>
    </div>
</div>

<!-- Toast Notification đặt ngoài #main-content -->
<div id="toast-success" class="fixed top-5 right-5 z-50 hidden items-center max-w-xs p-4 mb-4 text-sm text-white bg-green-600 rounded-lg shadow" role="alert">
    <span class="font-medium">✔ Cập nhật thành công!</span>
</div>

<script>
function showToast(message) {
    const toast = document.getElementById("toast-success");
    toast.querySelector("span").textContent = message;
    toast.classList.remove("hidden");
    clearTimeout(toast.dataset.timer);
    toast.dataset.timer = setTimeout(() => toast.classList.add("hidden"), 6000);
}

function bindAddBannerForm() {
    $(document).off('submit', '.banner-add-form').on('submit', '.banner-add-form', function (e) {
        e.preventDefault();
        const $form = $(this);
        const formData = new FormData(this);
        const url = $form.attr('action');

        $.ajax({
            url,
            type: 'POST',
            data: formData,
            contentType: false,
            processData: false,
            success: function () {
                $('#main-content').load(window.location.pathname + '?tab=setting&ajax=1&_=' + Date.now(), function () {
                    bindSettingEvents();
                    showToast("✔ Thêm banner thành công!");
                });
            },
            error: function (xhr) {
                alert('Thêm banner thất bại! ' + (xhr.responseText || ''));
            }
        });
    });
}

function bindDeleteBannerForm() {
    $(document).off('submit', '.banner-delete-form').on('submit', '.banner-delete-form', function (e) {
        e.preventDefault();
        if (!confirm("Bạn có chắc chắn muốn xoá banner này?")) return;

        const $form = $(this);
        const url = $form.attr('action');
        const formData = $form.serialize();

        $form.find('button[type="submit"]').prop('disabled', true);
        $.ajax({
            url,
            type: 'POST',
            data: formData,
            success: function () {
                $('#main-content').load(window.location.pathname + '?tab=setting&ajax=1&_=' + Date.now(), function () {
                    bindSettingEvents();
                    showToast("✔ Xoá banner thành công!");
                });
            },
            error: function (xhr) {
                alert('Xoá banner thất bại! ' + (xhr.responseText || ''));
                $form.find('button[type="submit"]').prop('disabled', false);
            }
        });
    });
}

function bindCenterForm() {
    $(document).off('submit', '#centerForm').on('submit', '#centerForm', function (e) {
        e.preventDefault();
        const formData = new FormData(this);
        $.ajax({
            url: this.action,
            type: 'POST',
            data: formData,
            contentType: false,
            processData: false,
            success: function () {
                showToast("✔ Cập nhật thông tin trung tâm thành công!");

                // ✅ Sau 1.2 giây, reload lại đúng tab setting
                setTimeout(() => {
                    window.location.href = window.location.pathname + '?tab=setting';
                }, 1200);
            },
            error: function () {
                alert('Cập nhật thất bại!');
            }
        });
    });
}


function bindLogoPreview() {
    $(document).off('change', '#logo').on('change', '#logo', function (e) {
        const file = e.target.files[0];
        if (file) {
            const reader = new FileReader();
            reader.onload = function (event) {
                $('#logoPreview').attr('src', event.target.result);
            };
            reader.readAsDataURL(file);
        }
    });
}

function bindSettingEvents() {
    bindAddBannerForm();
    bindDeleteBannerForm();
    bindCenterForm();
    bindLogoPreview();
}

$(document).ready(function () {
    bindSettingEvents();
});
</script>

// =======================================================
// 📁 sections.js - Quản lý chuyển đổi các phần trong Dashboard
// =======================================================

// 🧭 Hàm chuyển đổi hiển thị các section
function showSection(id) {
    // 1️⃣ Ẩn toàn bộ section khác
    document.querySelectorAll("main section").forEach(section => {
        section.style.display = "none";
    });

    // 2️⃣ Hiện section đang được chọn
    const selectedSection = document.getElementById(id);
    if (selectedSection) {
        selectedSection.style.display = "block";
        selectedSection.scrollIntoView({ behavior: "smooth", block: "start" });
    } else {
        console.warn(`⚠️ Không tìm thấy section: ${id}`);
        return;
    }

    // 3️⃣ Cập nhật trạng thái active trên sidebar
    document.querySelectorAll("#sidebar .nav-link").forEach(link => {
        link.classList.remove("active", "fw-bold", "text-warning");
    });

    const activeLink = Array.from(document.querySelectorAll("#sidebar .nav-link"))
        .find(link => link.getAttribute("onclick")?.includes(id));

    if (activeLink) {
        activeLink.classList.add("active", "fw-bold", "text-warning");
    }

    // 4️⃣ Gọi hàm load dữ liệu tương ứng (nếu có)
    try {
        switch (id) {
            case "dashboard":
                if (typeof loadDashboard === "function") loadDashboard();
                break;

            case "user":
                // ✅ Luôn load lại roles và users mỗi lần bấm “Người dùng”
                setTimeout(() => {
                    if (typeof loadRoles === "function") loadRoles();
                    if (typeof loadUsers === "function") loadUsers();
                }, 150); // Đợi DOM render dropdown xong rồi mới gọi
                break;

            case "drone":
                if (typeof loadDrones === "function") loadDrones();
                break;

            case "order":
                if (typeof loadOrders === "function") loadOrders();
                break;

            case "menu":
                if (typeof loadMenu === "function") loadMenu();
                break;

            case "payment":
                if (typeof loadPayments === "function") loadPayments();
                break;

            case "restaurant":
                if (typeof loadRestaurants === "function") loadRestaurants();
                break;

            default:
                console.log(`⚙️ Không có hàm load tương ứng cho section: ${id}`);
        }
    } catch (err) {
        console.error(`❌ Lỗi khi load dữ liệu cho ${id}:`, err);
    }

    console.log(`📍 Đã chuyển sang tab: ${id}`);
}

// =======================================================
// 🚀 TỰ ĐỘNG MỞ DASHBOARD KHI VỪA TRUY CẬP TRANG
// =======================================================
document.addEventListener("DOMContentLoaded", () => {
    showSection("dashboard"); // Mặc định mở trang chính
});

// =======================================================
// ✨ CẢI TIẾN GIAO DIỆN: Hiệu ứng ẩn/hiện mượt
// =======================================================
const style = document.createElement("style");
style.innerHTML = `
    main section {
        display: none;
        opacity: 0;
        transform: translateY(10px);
        transition: all 0.3s ease;
    }
    main section[style*="display: block"] {
        opacity: 1;
        transform: translateY(0);
    }
    #sidebar .nav-link.active {
        background-color: #343a40 !important;
        border-radius: 8px;
        color: #ffc107 !important;
    }
`;
document.head.appendChild(style);

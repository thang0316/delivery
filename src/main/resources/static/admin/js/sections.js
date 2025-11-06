// =======================================================
// 📁 sections.js - Quản lý chuyển đổi các phần trong Dashboard
// =======================================================

// 🧭 Hàm chuyển đổi hiển thị các section
function showSection(id) {
    // 1️⃣ Ẩn tất cả các section
    document.querySelectorAll("main section").forEach(section => {
        section.classList.remove("active-section");
    });

    // 2️⃣ Hiện section được chọn
    const selected = document.getElementById(id);
    if (selected) {
        selected.classList.add("active-section");
        selected.scrollIntoView({ behavior: "smooth", block: "start" });
    } else {
        console.warn(`⚠️ Không tìm thấy section: ${id}`);
        return;
    }

    // 3️⃣ Cập nhật trạng thái active trong sidebar
    document.querySelectorAll("#sidebar .nav-link").forEach(link => {
        link.classList.remove("active", "fw-bold", "text-warning");
    });

    const activeLink = Array.from(document.querySelectorAll("#sidebar .nav-link"))
        .find(link => link.getAttribute("onclick")?.includes(id));

    if (activeLink) {
        activeLink.classList.add("active", "fw-bold", "text-warning");
    }

    // 4️⃣ Gọi hàm load dữ liệu riêng cho từng section
    try {
        switch (id) {
            case "dashboard":
                if (typeof loadCounts === "function") loadCounts();
                if (typeof loadRevenueChart === "function") loadRevenueChart();
                if (typeof loadRestaurants === "function") loadRestaurants();
                break;

            case "user":
                setTimeout(() => {
                    if (typeof loadRoles === "function") loadRoles();
                    if (typeof loadUsers === "function") loadUsers();
                }, 200);
                break;

            case "restaurant":
                if (typeof loadRestaurants === "function") loadRestaurants();
                break;

            case "drone":
                if (typeof loadDrones === "function") loadDrones();
                break;

            case "order":
                if (typeof loadOrders === "function") loadOrders();
                break;

            case "payment":
                if (typeof loadPayments === "function") loadPayments();
                break;

            default:
                console.log(`⚙️ Không có hàm load tương ứng cho section: ${id}`);
        }
    } catch (err) {
        console.error(`❌ Lỗi khi load dữ liệu cho ${id}:`, err);
    }

    console.log(`📍 Đã chuyển sang section: ${id}`);
}

// =======================================================
// 🚀 Khi trang vừa load xong, hiển thị Dashboard mặc định
// =======================================================
document.addEventListener("DOMContentLoaded", () => {
    showSection("dashboard");
});

// =======================================================
// ✨ Thêm hiệu ứng chuyển section mượt hơn
// =======================================================
const style = document.createElement("style");
style.textContent = `
    main section {
        display: none;
        opacity: 0;
        transform: translateY(10px);
        transition: opacity 0.3s ease, transform 0.3s ease;
    }
    main section.active-section {
        display: block;
        opacity: 1;
        transform: translateY(0);
    }

    #sidebar .nav-link.active {
        background-color: #212529 !important;
        color: #ffc107 !important;
        border-radius: 8px;
    }
`;
document.head.appendChild(style);

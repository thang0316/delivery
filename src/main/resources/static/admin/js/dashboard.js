// =======================================================
// 📊 DASHBOARD - LOAD TỔNG QUAN
// =======================================================
async function loadDashboard() {
    try {
        // Gọi song song các API chính
        const [users, orders, drones, payments] = await Promise.all([
            fetch("/api/users").then(res => res.ok ? res.json() : []),
            fetch("/api/orders").then(res => res.ok ? res.json() : []),
            fetch("/api/drones").then(res => res.ok ? res.json() : []),
            fetch("/api/payments").then(res => res.ok ? res.json() : []),
        ]);

        // Đảm bảo có phần tử HTML (tránh lỗi khi chưa render)
        const userEl = document.getElementById("userCount");
        const orderEl = document.getElementById("orderCount");
        const droneEl = document.getElementById("droneCount");
        const paymentEl = document.getElementById("paymentTotal");

        if (!userEl || !orderEl || !droneEl || !paymentEl) {
            console.warn("⚠️ Không tìm thấy phần tử dashboard cần hiển thị.");
            return;
        }

        // Gán số liệu lên giao diện
        userEl.textContent = users?.length || 0;
        orderEl.textContent = orders?.length || 0;
        droneEl.textContent = drones?.length || 0;

        // Tính tổng doanh thu
        const totalRevenue = Array.isArray(payments)
            ? payments.reduce((sum, p) => sum + (p.amount || 0), 0)
            : 0;

        paymentEl.textContent = totalRevenue.toLocaleString("vi-VN") + " ₫";

        console.log("✅ Dashboard loaded:", {
            users: users.length,
            orders: orders.length,
            drones: drones.length,
            payments: payments.length,
            totalRevenue
        });
    } catch (err) {
        console.error("❌ Lỗi load dashboard:", err);
        // fallback giao diện
        document.getElementById("userCount").textContent = "0";
        document.getElementById("orderCount").textContent = "0";
        document.getElementById("droneCount").textContent = "0";
        document.getElementById("paymentTotal").textContent = "0 ₫";
    }
}

// =======================================================
// 🚀 KHỞI TẠO KHI LOAD TRANG
// =======================================================
document.addEventListener("DOMContentLoaded", loadDashboard);

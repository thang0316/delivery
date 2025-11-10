// =======================================================
// 📊 DASHBOARD - LOAD TỔNG QUAN
// =======================================================
async function loadDashboard() {
    try {
        // 🔄 Gọi song song tất cả các API cần thiết
        const [users, restaurants, orders, drones, payments, deliveries] = await Promise.all([
            fetch("/api/users").then(res => res.ok ? res.json() : []),
            fetch("/api/restaurants").then(res => res.ok ? res.json() : []),
            fetch("/api/orders").then(res => res.ok ? res.json() : []),
            fetch("/api/drones").then(res => res.ok ? res.json() : []),
            fetch("/api/payments").then(res => res.ok ? res.json() : []),
            fetch("/api/deliveries").then(res => res.ok ? res.json() : []),
        ]);

        // 🧩 Lấy các phần tử HTML cần cập nhật
        const userEl = document.getElementById("userCount");
        const restaurantEl = document.getElementById("restaurantCount");
        const orderEl = document.getElementById("orderCount");
        const droneEl = document.getElementById("droneCount");
        const revenueEl = document.getElementById("revenueTotal");
        const deliveryEl = document.getElementById("deliveryCount");

        if (!userEl || !restaurantEl || !orderEl || !droneEl || !revenueEl || !deliveryEl) {
            console.warn("⚠️ Không tìm thấy phần tử dashboard cần hiển thị.");
            return;
        }

        // 📦 Cập nhật số lượng
        userEl.textContent = users?.length || 0;
        restaurantEl.textContent = restaurants?.length || 0;
        orderEl.textContent = orders?.length || 0;
        droneEl.textContent = drones?.length || 0;
        deliveryEl.textContent = deliveries?.length || 0;

        // 💰 Tính tổng doanh thu
        const totalRevenue = Array.isArray(payments)
            ? payments.reduce((sum, p) => sum + (p.amount || 0), 0)
            : 0;

        revenueEl.textContent = totalRevenue.toLocaleString("vi-VN") + " ₫";

        // 🧾 Log ra console để debug nhanh
        console.log("✅ Dashboard loaded:", {
            users: users.length,
            restaurants: restaurants.length,
            orders: orders.length,
            drones: drones.length,
            deliveries: deliveries.length,
            payments: payments.length,
            totalRevenue
        });
    } catch (err) {
        console.error("❌ Lỗi load dashboard:", err);

        // 🩹 Gán giá trị mặc định khi lỗi
        ["userCount", "restaurantCount", "orderCount", "droneCount", "deliveryCount"].forEach(id => {
            const el = document.getElementById(id);
            if (el) el.textContent = "0";
        });
        const revenueEl = document.getElementById("revenueTotal");
        if (revenueEl) revenueEl.textContent = "0 ₫";
    }
}

// =======================================================
// 🚀 KHỞI TẠO KHI LOAD TRANG
// =======================================================
document.addEventListener("DOMContentLoaded", loadDashboard);

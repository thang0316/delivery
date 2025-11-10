// =======================================================
// 📁 sections.js - Quản lý chuyển đổi & tải động nội dung trong Dashboard
// =======================================================

// 🧭 Hàm hiển thị và tải file HTML tương ứng
async function showSection(id) {
    // 1️⃣ Ẩn tất cả section đang hiển thị
    document.querySelectorAll("main section").forEach(section => {
        section.classList.remove("active-section");
    });

    // 2️⃣ Lấy section được chọn
    const selected = document.getElementById(id);
    if (!selected) {
        console.warn(`⚠️ Không tìm thấy section: ${id}`);
        return;
    }

    // 3️⃣ Hiển thị section đang chọn
    selected.classList.add("active-section");
    selected.scrollIntoView({ behavior: "smooth", block: "start" });

    // 4️⃣ Cập nhật trạng thái sidebar
    document.querySelectorAll("#sidebar .nav-link").forEach(link => link.classList.remove("active"));
    const activeLink = Array.from(document.querySelectorAll("#sidebar .nav-link"))
        .find(link => link.getAttribute("onclick")?.includes(id));
    if (activeLink) activeLink.classList.add("active");

    // 5️⃣ Map section → file HTML tương ứng
    const sectionFiles = {
        dashboard: "admin/dashboard.html",
        user: "admin/users.html",
        restaurant: "admin/restaurants.html",
        order: "admin/orders.html",
        payment: "admin/payments.html",
        drone: "admin/drones.html",
        deliveries: "admin/deliveries.html"
    };

    const htmlFile = sectionFiles[id];
    if (!htmlFile) {
        console.warn(`⚙️ Không có file HTML tương ứng cho section: ${id}`);
        return;
    }

    // 6️⃣ Hiển thị spinner trong lúc tải
    selected.innerHTML = `
      <div class="text-center py-5 text-muted">
        <div class="spinner-border text-primary mb-3" role="status"></div>
        <p>Đang tải nội dung <b>${id}</b>...</p>
      </div>
    `;

    // 7️⃣ Fetch nội dung HTML của section
    try {
        const res = await fetch(`/${htmlFile}`);
        if (!res.ok) throw new Error(`Không thể tải file: ${htmlFile}`);

        let html = await res.text();
        html = html.replace(/<\/?(html|head|body)[^>]*>/g, "").trim();
        selected.innerHTML = html;

        // 8️⃣ Chạy các script bên trong file HTML vừa load
        const scripts = selected.querySelectorAll("script");
        scripts.forEach(oldScript => {
            const newScript = document.createElement("script");
            if (oldScript.src) newScript.src = oldScript.src;
            else newScript.textContent = oldScript.textContent;
            document.body.appendChild(newScript);
        });

        console.log(`✅ Đã load ${htmlFile} vào section ${id}`);
    } catch (err) {
        selected.innerHTML = `<div class="alert alert-danger m-4">❌ ${err.message}</div>`;
        console.error(`❌ Lỗi khi load nội dung ${id}:`, err);
    }
}

// =======================================================
// 🚀 Khi trang load, hiển thị Dashboard mặc định
// =======================================================
document.addEventListener("DOMContentLoaded", () => {
    showSection("dashboard");
});

// =======================================================
// ✨ Hiệu ứng CSS mượt khi chuyển section
// =======================================================
const style = document.createElement("style");
style.textContent = `
    main section {
        display: none;
        opacity: 0;
        transform: translateY(10px);
        transition: opacity 0.35s ease, transform 0.35s ease;
    }
    main section.active-section {
        display: block;
        opacity: 1;
        transform: translateY(0);
    }
    #sidebar .nav-link.active {
        background-color: #0d6efd !important;
        color: #fff !important;
        border-radius: 8px;
        font-weight: 600;
    }
`;
document.head.appendChild(style);

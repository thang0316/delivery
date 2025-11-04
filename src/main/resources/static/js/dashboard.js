// =======================================================
// 📊 DASHBOARD - LOAD TỔNG QUAN
// =======================================================
async function loadDashboard() {
    try {
        const [users, orders, menu, drones] = await Promise.all([
            fetch("/api/users").then(res => res.ok ? res.json() : []),
            fetch("/api/orders").then(res => res.ok ? res.json() : []),
            fetch("/api/menu-items").then(res => res.ok ? res.json() : []),
            fetch("/api/drones").then(res => res.ok ? res.json() : []),
        ]);

        document.getElementById("userCount").textContent = users.length;
        document.getElementById("orderCount").textContent = orders.length;
        document.getElementById("menuCount").textContent = menu.length;
        document.getElementById("droneCount").textContent = drones.length;
    } catch (err) {
        console.error("❌ Lỗi load dashboard:", err);
    }
}

// =======================================================
// 👤 LOAD DANH SÁCH ROLE (ROLE DROPDOWN)
// =======================================================
async function loadRoles() {
    const select = document.getElementById("roleSelect");
    if (!select) {
        console.warn("⚠️ Không tìm thấy #roleSelect (chưa mở tab User?)");
        return;
    }

    // Hiển thị trạng thái loading ban đầu
    select.innerHTML = `<option disabled selected>Đang tải vai trò...</option>`;

    try {
        const res = await fetch("/api/roles");
        if (!res.ok) {
            console.warn(`⚠️ API /api/roles lỗi ${res.status}`);
            select.innerHTML = `<option disabled selected>Không thể tải vai trò (Lỗi ${res.status})</option>`;
            return;
        }

        const roles = await res.json();
        if (!Array.isArray(roles) || roles.length === 0) {
            select.innerHTML = `<option disabled selected>Chưa có vai trò nào</option>`;
            return;
        }

        select.innerHTML = roles
            .map(role => `<option value="${role.id}">${role.name}</option>`)
            .join("");

        console.log("✅ Roles loaded:", roles);
    } catch (e) {
        console.error("❌ Lỗi tải roles:", e);
        select.innerHTML = `<option disabled selected>Lỗi kết nối server</option>`;
    }
}

// =======================================================
// 👤 USER CRUD
// =======================================================
async function loadUsers() {
    const body = document.getElementById("userTable");
    try {
        const res = await fetch("/api/users");
        if (!res.ok) throw new Error("API lỗi khi tải users");
        const users = await res.json();

        body.innerHTML = users.map(u => `
          <tr>
            <td>${u.id}</td>
            <td>${u.username}</td>
            <td>${u.email}</td>
            <td>${u.phone}</td>
            <td>${u.role?.name || "?"}</td>
            <td>
                <button class='btn btn-sm btn-warning' onclick='editUser(${u.id})'>✏️</button>
                <button class='btn btn-sm btn-danger' onclick='deleteUser(${u.id})'>🗑️</button>
            </td>
          </tr>
        `).join("");
    } catch (e) {
        console.error(e);
        body.innerHTML = "<tr><td colspan='6' class='text-danger'>❌ Lỗi tải danh sách người dùng</td></tr>";
    }
}

async function createUser() {
    const username = document.getElementById("username").value.trim();
    const email = document.getElementById("email").value.trim();
    const phone = document.getElementById("phone").value.trim();
    const password = document.getElementById("password").value.trim();
    const roleId = document.getElementById("roleSelect")?.value;

    if (!username || !email || !phone || !password || !roleId) {
        alert("⚠️ Vui lòng nhập đầy đủ thông tin!");
        return;
    }

    const body = { username, email, phone, password, role: { id: roleId } };

    try {
        const res = await fetch("/api/users", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(body)
        });

        if (!res.ok) {
            const err = await res.text();
            alert("❌ Lỗi thêm người dùng:\n" + err);
            return;
        }

        alert("✅ Thêm người dùng thành công!");
        document.querySelectorAll("#username,#email,#phone,#password").forEach(i => i.value = "");
        loadUsers();
    } catch (e) {
        alert("❌ Lỗi khi gửi request!");
        console.error(e);
    }
}

async function editUser(id) {
    const newEmail = prompt("Nhập email mới:");
    if (!newEmail) return;

    try {
        const res = await fetch(`/api/users/${id}`, {
            method: "PUT",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ email: newEmail })
        });
        if (res.ok) {
            alert("✅ Cập nhật thành công!");
            loadUsers();
        } else {
            alert("❌ Lỗi cập nhật người dùng!");
        }
    } catch (e) {
        alert("❌ Lỗi khi cập nhật!");
    }
}

async function deleteUser(id) {
    if (!confirm("Bạn có chắc muốn xóa người dùng này?")) return;
    await fetch(`/api/users/${id}`, { method: "DELETE" });
    loadUsers();
}

// =======================================================
// 🚁 DRONE CRUD
// =======================================================
async function loadDrones() {
    const table = document.getElementById("droneTable");
    try {
        const res = await fetch("/api/drones");
        if (!res.ok) throw new Error("API lỗi khi tải drones");
        const drones = await res.json();

        table.innerHTML = drones.map(d => `
            <tr>
                <td>${d.droneId}</td>
                <td>${d.status}</td>
                <td><button class='btn btn-sm btn-danger' onclick='deleteDrone("${d.id}")'>Xóa</button></td>
            </tr>
        `).join("");
    } catch (err) {
        console.error(err);
        table.innerHTML = "<tr><td colspan='3' class='text-danger'>❌ Lỗi tải danh sách Drone</td></tr>";
    }
}

async function createDrone() {
    const droneId = document.getElementById("droneId").value.trim();
    const status = document.getElementById("status").value.trim();

    if (!droneId || !status) {
        alert("⚠️ Vui lòng nhập đủ thông tin Drone!");
        return;
    }

    try {
        const res = await fetch("/api/drones", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ droneId, status })
        });

        if (!res.ok) {
            alert("❌ Lỗi thêm Drone!");
            return;
        }

        alert("✅ Thêm Drone thành công!");
        loadDrones();
    } catch (e) {
        alert("❌ Lỗi khi gửi request Drone!");
        console.error(e);
    }
}

async function deleteDrone(id) {
    if (!confirm("Xóa Drone này?")) return;
    await fetch(`/api/drones/${id}`, { method: "DELETE" });
    loadDrones();
}

// =======================================================
// 🏠 RESTAURANT CRUD
// =======================================================
async function loadRestaurants() {
    const body = document.getElementById("restaurantTable");
    try {
        const res = await fetch("/api/restaurants");
        if (!res.ok) throw new Error("API lỗi khi tải restaurants");
        const restaurants = await res.json();

        body.innerHTML = restaurants.map(r => `
            <tr>
                <td>${r.id}</td>
                <td>${r.name}</td>
                <td>${r.address}</td>
                <td>${r.phone}</td>
                <td>
                    <button class="btn btn-sm btn-warning" onclick="editRestaurant(${r.id})">✏️</button>
                    <button class="btn btn-sm btn-danger" onclick="deleteRestaurant(${r.id})">🗑️</button>
                </td>
            </tr>
        `).join("");
    } catch (e) {
        console.error(e);
        body.innerHTML = "<tr><td colspan='5' class='text-danger'>❌ Lỗi tải danh sách nhà hàng</td></tr>";
    }
}

async function createRestaurant() {
    const name = document.getElementById("resName").value.trim();
    const address = document.getElementById("resAddress").value.trim();
    const phone = document.getElementById("resPhone").value.trim();

    if (!name || !address || !phone) {
        alert("⚠️ Vui lòng nhập đầy đủ thông tin!");
        return;
    }

    try {
        const res = await fetch("/api/restaurants", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ name, address, phone })
        });

        if (!res.ok) {
            alert("❌ Lỗi thêm nhà hàng!");
            return;
        }

        alert("✅ Thêm nhà hàng thành công!");
        loadRestaurants();
    } catch (e) {
        alert("❌ Lỗi khi gửi request!");
        console.error(e);
    }
}

async function editRestaurant(id) {
    const name = prompt("Nhập tên mới:");
    if (!name) return;

    try {
        const res = await fetch(`/api/restaurants/${id}`, {
            method: "PUT",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ name })
        });

        if (res.ok) {
            alert("✅ Cập nhật thành công!");
            loadRestaurants();
        } else {
            alert("❌ Lỗi cập nhật nhà hàng!");
        }
    } catch (e) {
        alert("❌ Lỗi kết nối server khi cập nhật!");
    }
}

async function deleteRestaurant(id) {
    if (!confirm("Xóa nhà hàng này?")) return;
    await fetch(`/api/restaurants/${id}`, { method: "DELETE" });
    loadRestaurants();
}

// =======================================================
// 🚀 KHỞI TẠO DASHBOARD KHI MỞ TRANG
// =======================================================
document.addEventListener("DOMContentLoaded", () => {
    loadDashboard();
    // 🔸 loadRoles() được gọi khi click tab "Người dùng" (trong sections.js)
});

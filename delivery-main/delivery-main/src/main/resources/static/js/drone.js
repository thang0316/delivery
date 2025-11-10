const api = "/api/drones";

async function loadDrones() {
    const table = document.getElementById("droneTable");
    table.innerHTML = "<tr><td colspan='5' class='text-center text-muted'>Đang tải...</td></tr>";

    try {
        const res = await fetch(api);
        const drones = await res.json();

        if (drones.length === 0) {
            table.innerHTML = "<tr><td colspan='5' class='text-center'>Không có drone nào.</td></tr>";
            return;
        }

        table.innerHTML = drones.map(drone => `
      <tr>
        <td>${drone.droneId || drone.id}</td>
        <td>${drone.status}</td>
        <td>${drone.currentLatitude || '—'}</td>
        <td>${drone.currentLongitude || '—'}</td>
        <td>
          <button class="btn btn-warning btn-sm me-2" onclick="editDrone('${drone.id}')">Sửa</button>
          <button class="btn btn-danger btn-sm" onclick="deleteDrone('${drone.id}')">Xóa</button>
        </td>
      </tr>
    `).join("");
    } catch (err) {
        console.error("Lỗi load drone:", err);
        table.innerHTML = "<tr><td colspan='5' class='text-danger text-center'>Lỗi tải dữ liệu.</td></tr>";
    }
}

async function createDrone() {
    const droneId = document.getElementById("droneId").value.trim();
    const status = document.getElementById("status").value.trim();

    if (!droneId || !status) {
        alert("Vui lòng nhập đủ thông tin!");
        return;
    }

    const body = { droneId, status, currentLatitude: 0, currentLongitude: 0 };

    try {
        const res = await fetch(api, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(body)
        });

        if (res.ok) {
            alert("Thêm drone thành công!");
            loadDrones();
        } else {
            alert("Không thể thêm drone!");
        }
    } catch (err) {
        console.error("Lỗi thêm drone:", err);
    }
}

async function deleteDrone(id) {
    if (!confirm("Xóa drone này?")) return;

    try {
        const res = await fetch(`${api}/${id}`, { method: "DELETE" });
        if (res.ok) {
            alert("Đã xóa!");
            loadDrones();
        } else {
            alert("Xóa thất bại!");
        }
    } catch (err) {
        console.error("Lỗi xóa drone:", err);
    }
}

// TODO: Chức năng sửa drone (sẽ thêm sau)
function editDrone(id) {
    alert("Tính năng chỉnh sửa drone sẽ cập nhật sau!");
}

document.addEventListener("DOMContentLoaded", loadDrones);

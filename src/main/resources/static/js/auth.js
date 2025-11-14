async function login() {
    const username = document.getElementById("username").value;
    const password = document.getElementById("password").value;
    const errorMsg = document.getElementById("error-msg");

    try {
        const response = await fetch("/api/auth/login", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ username, password })
        });

        if (!response.ok) {
            errorMsg.textContent = "Sai tài khoản hoặc mật khẩu!";
            return;
        }

        const user = await response.json();
        localStorage.setItem("loggedInUser", JSON.stringify(user));

        const role = user.role.name;

        // Điều hướng theo role
        if (role === "ADMIN") {
            window.location.href = "/admin/dashboard.html";
        }
        else if (role === "RESTAURANT") {
            window.location.href = "/restaurant/dashboard.html";
        }
        else if (role === "CUSTOMER") {
            window.location.href = "/customer/home.html";
        } else {
            errorMsg.textContent = "Không xác định được vai trò người dùng!";
        }

    } catch (err) {
        errorMsg.textContent = "Lỗi kết nối server!";
    }
}

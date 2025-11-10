async function login() {
    const username = document.getElementById("username").value;
    const password = document.getElementById("password").value;
    const errorMsg = document.getElementById("error-msg");

    try {
        const response = await fetch("/api/users");
        const users = await response.json();

        const found = users.find(u => u.username === username && u.password === password);

        if (found) {
            localStorage.setItem("loggedInUser", JSON.stringify(found));
            window.location.href = "/dashboard.html";
        } else {
            errorMsg.textContent = "Sai tài khoản hoặc mật khẩu!";
        }
    } catch (error) {
        console.error("Login error:", error);
        errorMsg.textContent = "Lỗi kết nối server!";
    }
}

async function register() {
    const username = document.getElementById("username").value;
    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;
    const phone = document.getElementById("phone").value;
    const successMsg = document.getElementById("success-msg");

    try {
        const body = {
            username,
            email,
            password,
            phone,
            roleId: 1 // Mặc định là Customer
        };

        const res = await fetch("/api/users", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(body)
        });

        if (res.ok) {
            successMsg.textContent = "Đăng ký thành công! Quay lại đăng nhập.";
        } else {
            successMsg.textContent = "Đăng ký thất bại!";
        }
    } catch (err) {
        console.error("Register error:", err);
        successMsg.textContent = "Lỗi server!";
    }
}

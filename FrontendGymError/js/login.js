// Thêm event listener cho phím Enter
document.addEventListener('keypress', function(e) {
    if (e.key === 'Enter') {
        login();
    }
});

function login() {
    const role = document.getElementById('role').value;
    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;

    fetch('http://localhost:8080/api/login', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ username, password, role })
    })
    .then(res => res.json())
    .then(data => {
        if (data.token) {
            // Lưu token nếu cần: localStorage.setItem('token', data.token);
            if (data.role === "CHUPHONG" || data.role === "ADMIN") {
                window.location.href = "chu-phong-gym-home.html";
            } else if (data.role === "PT") {
                window.location.href = "pt-home.html";
            } else if (data.role === "NHANVIEN") {
                window.location.href = "nhan-vien-le-tan-home.html";
            } else if (data.role === "HOIVIEN") {
                window.location.href = "hoi-vien-home.html";
            } else {
                document.getElementById('message').innerText = "Không xác định vai trò!";
            }
        } else {
            document.getElementById('message').style.color = 'red';
            document.getElementById('message').innerText = data.message || 'Sai tài khoản hoặc mật khẩu!';
        }
    })
    .catch(() => {
        document.getElementById('message').style.color = 'red';
        document.getElementById('message').innerText = 'Lỗi kết nối server!';
    });
}

function showRegister() {
    alert('Chuyển sang trang đăng ký hội viên!');
    // window.location.href = '/register.html';
} 
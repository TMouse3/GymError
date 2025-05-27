import { saveToken } from './config.js';

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
    .then(res => {
        if (!res.ok) {
            // Xử lý lỗi HTTP nếu có (ví dụ: 401 Unauthorized, 403 Forbidden)
            return res.json().then(err => { throw new Error(err.message || 'Đăng nhập thất bại!'); });
        }
        return res.json();
    })
    .then(data => {
        console.log('Login API response data:', data);
        if (data.token) {
            // Lưu token sử dụng hàm saveToken từ config.js
            saveToken(data.token);

            // Lưu thông tin người dùng (bao gồm tên nếu có)
            if (data.role === "NHANVIEN" && data.hoTen) {
                 localStorage.setItem('user', JSON.stringify({ 
                     hoTen: data.hoTen, 
                     role: data.role,
                     maNhanVien: data.maNhanVien 
                 }));
            } else { // Lưu thông tin cơ bản cho các vai trò khác
                 localStorage.setItem('user', JSON.stringify({ role: data.role }));
            }

            // Chuyển hướng dựa trên vai trò
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
             // Trường hợp backend trả về 200 OK nhưng không có token (không mong muốn)
            document.getElementById('message').style.color = 'red';
            document.getElementById('message').innerText = data.message || 'Đăng nhập thất bại: Không nhận được token!';
        }
    })
    .catch(error => {
        document.getElementById('message').style.color = 'red';
        document.getElementById('message').innerText = error.message || 'Lỗi kết nối server hoặc xử lý phản hồi!';
    });
}

function showRegister() {
    // alert('Chuyển sang trang đăng ký hội viên!');
    window.location.href = 'register.html';
}

// Make functions accessible globally
window.login = login;
window.showRegister = showRegister; 
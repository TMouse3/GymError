document.addEventListener('DOMContentLoaded', function() {
    const registerForm = document.getElementById('registerForm');
    const messageDiv = document.getElementById('message');

    registerForm.addEventListener('submit', function(e) {
        e.preventDefault();

        // Lấy giá trị từ form
        const formData = {
            hoTen: document.getElementById('hoTen').value,
            ngaySinh: document.getElementById('ngaySinh').value,
            gioiTinh: document.getElementById('gioiTinh').value === "true", // Boolean
            sdt: document.getElementById('sdt').value,
            email: document.getElementById('email').value,
            cccd: document.getElementById('cccd').value,
            taiKhoan: document.getElementById('taiKhoan').value,
            matKhau: document.getElementById('matKhau').value,
            confirmPassword: document.getElementById('confirmPassword').value
        };

        // Kiểm tra mật khẩu
        if (formData.matKhau !== formData.confirmPassword) {
            showMessage('Mật khẩu xác nhận không khớp!', 'error');
            return;
        }

        // Kiểm tra định dạng email
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        if (!emailRegex.test(formData.email)) {
            showMessage('Email không hợp lệ!', 'error');
            return;
        }

        // Kiểm tra định dạng số điện thoại
        const phoneRegex = /^[0-9]{10}$/;
        if (!phoneRegex.test(formData.sdt)) {
            showMessage('Số điện thoại không hợp lệ!', 'error');
            return;
        }

        // Gửi request đăng ký
        fetch('http://localhost:8080/api/register', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(formData)
        })
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                showMessage('Đăng ký thành công! Đang chuyển hướng...', 'success');
                setTimeout(() => {
                    window.location.href = 'index.html';
                }, 2000);
            } else {
                showMessage(data.message || 'Đăng ký thất bại!', 'error');
            }
        })
        .catch(error => {
            showMessage('Lỗi kết nối server!', 'error');
        });
    });

    function showMessage(message, type) {
        messageDiv.textContent = message;
        messageDiv.className = type;
        messageDiv.style.display = 'block';
    }
});

function showLogin() {
    window.location.href = 'index.html';
}

window.showLogin = showLogin;
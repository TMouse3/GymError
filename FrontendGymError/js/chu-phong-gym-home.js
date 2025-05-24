import { getToken } from './config.js';

const API_BASE = 'http://localhost:8080/api';

function setActiveMenu(index) {
    const items = document.querySelectorAll('.menu li');
    items.forEach((li, i) => {
        if (i === index) li.classList.add('active');
        else li.classList.remove('active');
    });
}
window.setActiveMenu = setActiveMenu;

async function showNhanVien() {
    setActiveMenu(0);
    const main = document.getElementById('main-content');
    main.innerHTML = `
        <h2>Quản lý nhân viên lễ tân</h2>
        <button class="btn-red" id="btnAddNV">Thêm nhân viên</button>
        <div id="nv-table"></div>
        <div id="nv-form-popup" class="popup-form" style="display:none"></div>
    `;
    loadNhanVien();
    document.getElementById('btnAddNV').onclick = function() {
        showNhanVienForm();
    };
}
window.showNhanVien = showNhanVien;

function showNhanVienForm(nv = null) {
    const popup = document.getElementById('nv-form-popup');
    popup.style.display = 'block';
    popup.innerHTML = `
        <div class="form-container">
            <h3>${nv ? 'Sửa' : 'Thêm'} nhân viên lễ tân</h3>
            <form id="nvForm">
                <input name="hoTen" placeholder="Họ tên" value="${nv ? nv.hoTen : ''}" required><br>
                <select name="gioiTinh">
                    <option value="true" ${nv && nv.gioiTinh ? 'selected' : ''}>Nam</option>
                    <option value="false" ${nv && nv.gioiTinh === false ? 'selected' : ''}>Nữ</option>
                </select><br>
                <input name="ngaySinh" type="date" value="${nv && nv.ngaySinh ? nv.ngaySinh.substring(0,10) : ''}" required><br>
                <input name="sdt" placeholder="Số điện thoại" value="${nv ? nv.sdt : ''}" required><br>
                <input name="email" placeholder="Email" value="${nv ? nv.email : ''}"><br>
                <input name="cccd" placeholder="CCCD" value="${nv ? nv.cccd : ''}"><br>
                <input name="taiKhoan" placeholder="Tài khoản" value="${nv ? nv.taiKhoan : ''}" required><br>
                <input name="matKhau" placeholder="Mật khẩu" type="password" value="${nv ? nv.matKhau : ''}" required><br>
                <div class='checkbox-row'>
                    <label for='trangThaiLamViec'>Đang làm việc</label>
                    <input id='trangThaiLamViec' name='trangThaiLamViec' type='checkbox' ${nv && nv.trangThaiLamViec ? 'checked' : ''}>
                </div>
                <button type="submit" class="btn-red">${nv ? 'Lưu' : 'Thêm'}</button>
                <button type="button" onclick="closeNhanVienForm()">Hủy</button>
            </form>
        </div>
    `;
    document.getElementById('nvForm').onsubmit = async function(e) {
        e.preventDefault();
        const form = e.target;
        const data = {
            hoTen: form.hoTen.value,
            gioiTinh: form.gioiTinh.value === 'true',
            ngaySinh: form.ngaySinh.value,
            sdt: form.sdt.value,
            email: form.email.value,
            cccd: form.cccd.value,
            taiKhoan: form.taiKhoan.value,
            matKhau: form.matKhau.value,
            trangThaiLamViec: form.trangThaiLamViec.checked
        };
        // Lấy token bằng hàm getToken từ config.js
        const token = getToken();

        let res;
        if (nv) {
            res = await fetch(`${API_BASE}/nhan-vien-le-tan/${nv.maNhanVien}`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}` // Thêm tiêu đề Authorization
                },
                body: JSON.stringify(data)
            });
        } else {
            res = await fetch(`${API_BASE}/nhan-vien-le-tan`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}` // Thêm tiêu đề Authorization
                },
                body: JSON.stringify(data)
            });
        }
        if (res.ok) {
            closeNhanVienForm();
            loadNhanVien();
        } else {
            alert('Thêm/sửa thất bại! Vui lòng kiểm tra lại dữ liệu.');
        }
    };
}

window.closeNhanVienForm = function() {
    document.getElementById('nv-form-popup').style.display = 'none';
}

async function loadNhanVien() {
    // Lấy token bằng hàm getToken từ config.js
    const token = getToken();

    const res = await fetch(`${API_BASE}/nhan-vien-le-tan`, {
        headers: {
            'Authorization': `Bearer ${token}` // Thêm tiêu đề Authorization
        }
    });
    if (!res.ok) {
        document.getElementById('nv-table').innerHTML = '<div style="color:red">Không thể tải danh sách nhân viên!</div>';
        return;
    }
    const data = await res.json();
    let html = `<table class="table-list"><tr>
        <th>Tên</th><th>Giới tính</th><th>Ngày sinh</th><th>SĐT</th><th>Email</th>
        <th>CCCD</th><th>Tài khoản</th><th>Trạng thái</th><th>Chức năng</th></tr>`;
    data.forEach(nv => {
        html += `<tr>
            <td>${nv.hoTen || ''}</td>
            <td>${nv.gioiTinh ? 'Nam' : 'Nữ'}</td>
            <td>${nv.ngaySinh ? nv.ngaySinh.substring(0,10) : ''}</td>
            <td>${nv.sdt || ''}</td>
            <td>${nv.email || ''}</td>
            <td>${nv.cccd || ''}</td>
            <td>${nv.taiKhoan || ''}</td>
            <td>${nv.trangThaiLamViec ? 'Đang làm' : 'Nghỉ'}</td>
            <td>
                <button onclick="editNhanVien(${nv.maNhanVien})">Sửa</button>
                <button onclick="deleteNhanVien(${nv.maNhanVien})">Xóa</button>
            </td>
        </tr>`;
    });
    html += `</table>`;
    document.getElementById('nv-table').innerHTML = html;
}

window.editNhanVien = async function(id) {
    // Lấy token bằng hàm getToken từ config.js
    const token = getToken();

    const res = await fetch(`${API_BASE}/nhan-vien-le-tan`, {
        headers: {
            'Authorization': `Bearer ${token}` // Thêm tiêu đề Authorization
        }
    });
    const data = await res.json();
    const nv = data.find(n => n.maNhanVien === id);
    showNhanVienForm(nv);
}

window.deleteNhanVien = async function(id) {
    if (!confirm('Bạn có chắc muốn xóa?')) return;
    // Lấy token bằng hàm getToken từ config.js
    const token = getToken();

    await fetch(`${API_BASE}/nhan-vien-le-tan/${id}`, {
        method: 'DELETE',
        headers: {
            'Authorization': `Bearer ${token}` // Thêm tiêu đề Authorization
        }
    });
    loadNhanVien();
}

function showPT() {
    setActiveMenu(1);
    document.getElementById('main-content').innerHTML = `
        <h2>Quản lý PT</h2>
        <button class="btn-red">Thêm PT</button>
        <table class="table-list">
            <tr><th>Tên</th><th>SĐT</th><th>Chức năng</th></tr>
            <tr><td>Trần Thị C</td><td>0912345678</td><td><button>Sửa</button> <button>Xóa</button></td></tr>
        </table>
    `;
}
window.showPT = showPT;

function showGoiTap() {
    setActiveMenu(2);
    document.getElementById('main-content').innerHTML = `
        <h2>Quản lý gói tập</h2>
        <button class="btn-red">Thêm gói tập</button>
        <table class="table-list">
            <tr><th>Tên gói</th><th>Giá</th><th>Chức năng</th></tr>
            <tr><td>Gói 1 tháng</td><td>500.000đ</td><td><button>Sửa</button> <button>Xóa</button></td></tr>
        </table>
    `;
}
window.showGoiTap = showGoiTap;

function showHoaDon() {
    setActiveMenu(3);
    document.getElementById('main-content').innerHTML = `
        <h2>Danh sách hóa đơn đã hoàn thành</h2>
        <table class="table-list">
            <tr><th>Mã hóa đơn</th><th>Khách hàng</th><th>Ngày</th><th>Tổng tiền</th></tr>
            <tr><td>HD001</td><td>Nguyễn Văn D</td><td>01/06/2024</td><td>1.000.000đ</td></tr>
        </table>
    `;
}
window.showHoaDon = showHoaDon;

function showThongKe() {
    setActiveMenu(4);
    document.getElementById('main-content').innerHTML = `
        <h2>Thống kê tổng quan</h2>
        <div class="stat-box">
            <div>Tổng số hội viên: <b>120</b></div>
            <div>Tổng doanh thu: <b>150.000.000đ</b></div>
        </div>
    `;
}
window.showThongKe = showThongKe; 
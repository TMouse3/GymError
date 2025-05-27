import { getToken, removeToken } from './config.js';

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
            <span class="close-btn" onclick="closeNhanVienForm()">×</span>
            <h3>${nv ? 'Sửa' : 'Thêm'} nhân viên lễ tân</h3>
            <form id="nvForm">
                <label for="hoTen">Họ tên:</label>
                <input name="hoTen" id="hoTen" placeholder="Nhập họ tên" value="${nv ? nv.hoTen : ''}" required><br>

                <label for="gioiTinh">Giới tính:</label>
                <select name="gioiTinh" id="gioiTinh">
                    <option value="true" ${nv && nv.gioiTinh ? 'selected' : ''}>Nam</option>
                    <option value="false" ${nv && nv.gioiTinh === false ? 'selected' : ''}>Nữ</option>
                </select><br>

                <label for="ngaySinh">Ngày sinh:</label>
                <input name="ngaySinh" id="ngaySinh" type="date" value="${nv && nv.ngaySinh ? nv.ngaySinh.substring(0,10) : ''}" required><br>

                <label for="sdt">Số điện thoại:</label>
                <input name="sdt" id="sdt" placeholder="Số điện thoại" value="${nv ? nv.sdt : ''}" required><br>

                <label for="email">Email:</label>
                <input name="email" id="email" placeholder="Email" value="${nv ? nv.email : ''}"><br>

                <label for="cccd">CCCD:</label>
                <input name="cccd" id="cccd" placeholder="CCCD" value="${nv ? nv.cccd : ''}"><br>

                <label for="taiKhoan">Tài khoản:</label>
                <input name="taiKhoan" id="taiKhoan" placeholder="Tài khoản" value="${nv ? nv.taiKhoan : ''}" required><br>

                <label for="matKhau">Mật khẩu:</label>
                <input name="matKhau" id="matKhau" placeholder="Mật khẩu" type="password" value="${nv ? nv.matKhau : ''}" required><br>

                <div class='checkbox-row'>
                    <label for='trangThaiLamViec'>Đang làm việc:</label>
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
                <button onclick="editNhanVien(${nv.maNhanVien})" class="btn-edit">Sửa</button>
                <button onclick="deleteNhanVien(${nv.maNhanVien})" class="btn-delete">Xóa</button>
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
    const main = document.getElementById('main-content');
    main.innerHTML = `
        <h2>Quản lý PT</h2>
        <button class="btn-red" id="btnAddPT">Thêm PT</button>
        <div id="pt-table"></div>
        <div id="pt-form-popup" class="popup-form" style="display:none"></div>
    `;
    loadPT();
    document.getElementById('btnAddPT').onclick = function() {
        showPTForm();
    };
}
window.showPT = showPT;

function showPTForm(pt = null) {
    const popup = document.getElementById('pt-form-popup');
    popup.style.display = 'block';
    popup.innerHTML = `
        <div class="form-container">
            <span class="close-btn" onclick="closePTForm()">×</span>
            <h3>${pt ? 'Sửa' : 'Thêm'} PT</h3>
            <form id="ptForm">
                <label for="hoTen">Họ tên:</label>
                <input name="hoTen" id="hoTen" placeholder="Nhập họ tên" value="${pt ? pt.hoTen : ''}" required><br>

                <label for="gioiTinh">Giới tính:</label>
                <select name="gioiTinh" id="gioiTinh">
                    <option value="true" ${pt && pt.gioiTinh ? 'selected' : ''}>Nam</option>
                    <option value="false" ${pt && pt.gioiTinh === false ? 'selected' : ''}>Nữ</option>
                </select><br>

                <label for="ngaySinh">Ngày sinh:</label>
                <input name="ngaySinh" id="ngaySinh" type="date" value="${pt && pt.ngaySinh ? pt.ngaySinh.substring(0,10) : ''}" required><br>

                <label for="sdt">Số điện thoại:</label>
                <input name="sdt" id="sdt" placeholder="Số điện thoại" value="${pt ? pt.sdt : ''}" required><br>

                <label for="email">Email:</label>
                <input name="email" id="email" placeholder="Email" value="${pt ? pt.email : ''}"><br>

                <label for="cccd">CCCD:</label>
                <input name="cccd" id="cccd" placeholder="CCCD" value="${pt ? pt.cccd : ''}"><br>

                <label for="bangCap">Bằng cấp:</label>
                <input name="bangCap" id="bangCap" placeholder="Bằng cấp" value="${pt ? pt.bangCap : ''}"><br>

                <label for="kinhNghiem">Kinh nghiệm (năm):</label>
                <input name="kinhNghiem" id="kinhNghiem" type="number" placeholder="Kinh nghiệm (năm)" value="${pt ? pt.kinhNghiem : ''}"><br>

                <label for="trangThai">Trạng thái:</label>
                <select name="trangThai" id="trangThai">
                    <option value="true" ${pt && pt.trangThai ? 'selected' : ''}>Hoạt động</option>
                    <option value="false" ${pt && pt.trangThai === false ? 'selected' : ''}>Không hoạt động</option>
                </select><br>

                <label for="taiKhoan">Tài khoản:</label>
                <input name="taiKhoan" id="taiKhoan" placeholder="Tài khoản" value="${pt ? pt.taiKhoan : ''}" required><br>

                <label for="matKhau">Mật khẩu:</label>
                <input name="matKhau" id="matKhau" placeholder="Mật khẩu" type="password" value="${pt ? pt.matKhau : ''}" required><br>

                <div class='checkbox-row'>
                    <label for='trangThaiLamViecPT'>Đang làm việc:</label>
                    <input id='trangThaiLamViecPT' name='trangThaiLamViec' type='checkbox' ${pt && pt.trangThaiLamViec ? 'checked' : ''}>
                </div>
                <button type="submit" class="btn-red">${pt ? 'Lưu' : 'Thêm'}</button>
                <button type="button" onclick="closePTForm()">Hủy</button>
            </form>
        </div>
    `;
    document.getElementById('ptForm').onsubmit = async function(e) {
        e.preventDefault();
        const form = e.target;
        const data = {
            hoTen: form.hoTen.value,
            gioiTinh: form.gioiTinh.value === 'true',
            ngaySinh: form.ngaySinh.value,
            sdt: form.sdt.value,
            email: form.email.value,
            cccd: form.cccd.value,
            bangCap: form.bangCap.value,
            kinhNghiem: parseInt(form.kinhNghiem.value) || 0,
            trangThai: form.trangThai.value === 'true',
            taiKhoan: form.taiKhoan.value,
            matKhau: form.matKhau.value,
            trangThaiLamViec: form.trangThaiLamViec.checked
        };
        const token = getToken();

        let res;
        if (pt) {
            res = await fetch(`${API_BASE}/pt/${pt.maPT}`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}`
                },
                body: JSON.stringify(data)
            });
        } else {
            res = await fetch(`${API_BASE}/pt`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}`
                },
                body: JSON.stringify(data)
            });
        }
        if (res.ok) {
            closePTForm();
            loadPT();
        } else {
            alert('Thêm/sửa PT thất bại! Vui lòng kiểm tra lại dữ liệu.');
        }
    };
}

window.closePTForm = function() {
    document.getElementById('pt-form-popup').style.display = 'none';
}

async function loadPT() {
    const token = getToken();
    const res = await fetch(`${API_BASE}/pt`, {
        headers: {
            'Authorization': `Bearer ${token}`
        }
    });
    if (!res.ok) {
        document.getElementById('pt-table').innerHTML = '<div style="color:red">Không thể tải danh sách PT!</div>';
        return;
    }
    const data = await res.json();
    let html = `<table class="table-list"><tr>
        <th>Tên</th><th>Giới tính</th><th>Ngày sinh</th><th>SĐT</th><th>Email</th>
        <th>CCCD</th><th>Bằng cấp</th><th>Kinh nghiệm</th><th>Trạng thái</th><th>Tài khoản</th><th>Trạng thái làm việc</th><th>Chức năng</th></tr>`;
    data.forEach(pt => {
        html += `<tr>
            <td>${pt.hoTen || ''}</td>
            <td>${pt.gioiTinh ? 'Nam' : 'Nữ'}</td>
            <td>${pt.ngaySinh ? pt.ngaySinh.substring(0,10) : ''}</td>
            <td>${pt.sdt || ''}</td>
            <td>${pt.email || ''}</td>
            <td>${pt.cccd || ''}</td>
            <td>${pt.bangCap || ''}</td>
            <td>${pt.kinhNghiem || ''}</td>
            <td>${pt.trangThai ? 'Hoạt động' : 'Không hoạt động'}</td>
            <td>${pt.taiKhoan || ''}</td>
            <td>${pt.trangThaiLamViec ? 'Đang làm' : 'Nghỉ'}</td>
            <td>
                <button onclick="editPT(${pt.maPT})" class="btn-edit">Sửa</button>
                <button onclick="deletePT(${pt.maPT})" class="btn-delete">Xóa</button>
            </td>
        </tr>`;
    });
    html += `</table>`;
    document.getElementById('pt-table').innerHTML = html;
}

window.editPT = async function(id) {
    const token = getToken();
    const res = await fetch(`${API_BASE}/pt`, {
        headers: {
            'Authorization': `Bearer ${token}`
        }
    });
    const data = await res.json();
    const pt = data.find(p => p.maPT === id);
    showPTForm(pt);
}

window.deletePT = async function(id) {
    if (!confirm('Bạn có chắc muốn xóa?')) return;
    const token = getToken();

    await fetch(`${API_BASE}/pt/${id}`, {
        method: 'DELETE',
        headers: {
            'Authorization': `Bearer ${token}`
        }
    });
    loadPT();
}

function showGoiTap() {
    setActiveMenu(2);
    const main = document.getElementById('main-content');
    main.innerHTML = `
        <h2>Quản lý gói tập</h2>
        <button class="btn-red" id="btnAddGoiTap">Thêm gói tập</button>
        <div id="goi-tap-table"></div>
        <div id="goi-tap-form-popup" class="popup-form" style="display:none"></div>
    `;
    loadGoiTap();
     document.getElementById('btnAddGoiTap').onclick = function() {
        showGoiTapForm();
    };
}
window.showGoiTap = showGoiTap;

function showGoiTapForm(goiTap = null) {
    const popup = document.getElementById('goi-tap-form-popup');
    popup.style.display = 'block';
    popup.innerHTML = `
        <div class="form-container">
            <span class="close-btn" onclick="closeGoiTapForm()">×</span>
            <h3>${goiTap ? 'Sửa' : 'Thêm'} gói tập</h3>
            <form id="goiTapForm">
                <label for="tenGoiTap">Tên gói tập:</label>
                <input name="tenGoiTap" id="tenGoiTap" placeholder="Nhập tên gói tập" value="${goiTap ? goiTap.tenGoiTap : ''}" required><br>

                <label for="moTaGoiTap">Mô tả:</label>
                <input name="moTaGoiTap" id="moTaGoiTap" placeholder="Nhập mô tả" value="${goiTap ? goiTap.moTaGoiTap : ''}"><br>

                <label for="soNgay">Số ngày:</label>
                <input name="soNgay" id="soNgay" type="number" placeholder="Nhập số ngày" value="${goiTap ? goiTap.soNgay : ''}" required min="1"><br>

                <label for="giaGoiTap">Giá tiền:</label>
                <input name="giaGoiTap" id="giaGoiTap" type="number" placeholder="Nhập giá tiền" value="${goiTap ? goiTap.giaGoiTap : ''}" required min="0"><br>

                <label for="trangThaiGoiTap">Trạng thái:</label>
                <select name="trangThaiGoiTap" id="trangThaiGoiTap">
                    <option value="true" ${goiTap && goiTap.trangThaiGoiTap ? 'selected' : ''}>Hoạt động</option>
                    <option value="false" ${goiTap && goiTap.trangThaiGoiTap === false ? 'selected' : ''}>Không hoạt động</option>
                </select><br>

                <button type="submit" class="btn-red">${goiTap ? 'Lưu' : 'Thêm'}</button>
                <button type="button" onclick="closeGoiTapForm()">Hủy</button>
            </form>
        </div>
    `;
    document.getElementById('goiTapForm').onsubmit = async function(e) {
        e.preventDefault();
        const form = e.target;
        const data = {
            tenGoiTap: form.tenGoiTap.value,
            moTaGoiTap: form.moTaGoiTap.value,
            soNgay: parseInt(form.soNgay.value) || 0,
            giaGoiTap: parseFloat(form.giaGoiTap.value) || 0,
            trangThaiGoiTap: form.trangThaiGoiTap.value === 'true'
        };
        const token = getToken();

        let res;
        if (goiTap) {
            res = await fetch(`${API_BASE}/goi-tap/${goiTap.maGoiTap}`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}`
                },
                body: JSON.stringify(data)
            });
        } else {
            res = await fetch(`${API_BASE}/goi-tap`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}`
                },
                body: JSON.stringify(data)
            });
        }
        if (res.ok) {
            closeGoiTapForm();
            loadGoiTap();
        } else {
            alert('Thêm/sửa gói tập thất bại! Vui lòng kiểm tra lại dữ liệu.');
        }
    };
}

window.closeGoiTapForm = function() {
    document.getElementById('goi-tap-form-popup').style.display = 'none';
}

async function loadGoiTap() {
    const token = getToken();
    const res = await fetch(`${API_BASE}/goi-tap`, {
        headers: {
            'Authorization': `Bearer ${token}`
        }
    });
     if (!res.ok) {
        document.getElementById('goi-tap-table').innerHTML = '<div style="color:red">Không thể tải danh sách gói tập!</div>';
        return;
    }
    const data = await res.json();
    let html = `<table class="table-list"><tr>
        <th>Tên gói</th><th>Số ngày</th><th>Giá</th><th>Trạng thái</th><th>Mô tả</th><th>Chức năng</th></tr>`;
    data.forEach(goiTap => {
        html += `<tr>
            <td>${goiTap.tenGoiTap || ''}</td>
            <td>${goiTap.soNgay || ''}</td>
            <td>${goiTap.giaGoiTap || ''}đ</td>
            <td>${goiTap.trangThaiGoiTap ? 'Hoạt động' : 'Không hoạt động'}</td>
             <td>${goiTap.moTaGoiTap || ''}</td>
            <td>
                <button onclick="editGoiTap(${goiTap.maGoiTap})" class="btn-edit">Sửa</button>
                <button onclick="deleteGoiTap(${goiTap.maGoiTap})" class="btn-delete">Xóa</button>
            </td>
        </tr>`;
    });
    html += `</table>`;
    document.getElementById('goi-tap-table').innerHTML = html;
}

window.editGoiTap = async function(id) {
     const token = getToken();
    const res = await fetch(`${API_BASE}/goi-tap`, {
        headers: {
            'Authorization': `Bearer ${token}`
        }
    });
    const data = await res.json();
    const goiTap = data.find(g => g.maGoiTap === id);
    showGoiTapForm(goiTap);
}

window.deleteGoiTap = async function(id) {
    if (!confirm('Bạn có chắc muốn xóa?')) return;
    const token = getToken();

    await fetch(`${API_BASE}/goi-tap/${id}`, {
        method: 'DELETE',
        headers: {
            'Authorization': `Bearer ${token}`
        }
    });
    loadGoiTap();
}

function showHoaDon() {
    setActiveMenu(3);
    const main = document.getElementById('main-content');
    main.innerHTML = `
        <h2>Danh sách hóa đơn đã hoàn thành</h2>
        <div id="hoa-don-table"></div>
    `;
    loadHoaDon();
}
window.showHoaDon = showHoaDon;

async function loadHoaDon() {
    const token = getToken();
    const res = await fetch(`${API_BASE}/hoa-don`, {
        headers: {
            'Authorization': `Bearer ${token}`
        }
    });

    if (!res.ok) {
        document.getElementById('hoa-don-table').innerHTML = '<div style="color:red">Không thể tải danh sách hóa đơn!</div>';
        return;
    }

    const data = await res.json();
    let html = `<table class="table-list"><tr>
        <th>Mã hóa đơn</th><th>Tên hóa đơn</th><th>Ngày thanh toán</th><th>Giá tiền</th><th>Ghi chú</th><th>Hội viên</th><th>Gói tập</th><th>Nhân viên tạo</th>
        </tr>`; // Removed Chức năng column as there are no edit/delete operations defined in backend controller
    
    data.forEach(hoaDon => {
        // Format date (optional, basic example)
        const ngayThanhToan = hoaDon.ngayThanhToan ? new Date(hoaDon.ngayThanhToan).toLocaleString() : '';
        
        html += `<tr>
            <td>${hoaDon.maHoaDon || ''}</td>
            <td>${hoaDon.tenHoaDon || ''}</td>
            <td>${ngayThanhToan}</td>
            <td>${hoaDon.giaTien || ''}đ</td>
            <td>${hoaDon.ghiChu || ''}</td>
            <td>${hoaDon.tenHoiVien || ''}</td>
            <td>${hoaDon.tenGoiTap || ''}</td>
            <td>${hoaDon.tenNhanVien || ''}</td>
        </tr>`;
    });
    html += `</table>`;
    document.getElementById('hoa-don-table').innerHTML = html;
}

async function showThongKe() {
    setActiveMenu(4);
    const main = document.getElementById('main-content');
    main.innerHTML = `
        <h2>Thống kê tổng quan</h2>
        <div class="stat-box" id="stats-container">
            <div>Đang tải...</div>
        </div>
    `;

    const statsContainer = document.getElementById('stats-container');
    const token = getToken();

    try {
        // Fetch total members
        const resMembers = await fetch(`${API_BASE}/hoi-vien`, {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        });
        let totalMembers = 'Không khả dụng';
        if (resMembers.ok) {
            const members = await resMembers.json();
            // Check if members is an array before getting length
            if (Array.isArray(members)) {
                totalMembers = members.length;
            } else {
                console.error('Received non-array data for members:', members);
                totalMembers = 'Lỗi dữ liệu'; // Indicate a data format issue
            }
        } else {
            console.error('Failed to fetch members for stats:', resMembers.status, await resMembers.text());
        }

        // Fetch total revenue from invoices
        const resInvoices = await fetch(`${API_BASE}/hoa-don`, {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        });
        let totalRevenue = 'Không khả dụng';
        if (resInvoices.ok) {
            const invoices = await resInvoices.json();
            // Assuming GiaTien is a number (float/double). Summing up.
            totalRevenue = invoices.reduce((sum, invoice) => sum + (invoice.giaTien || 0), 0).toLocaleString('vi-VN'); // Format with locale for currency display
        } else {
             console.error('Failed to fetch invoices for stats:', resInvoices.status, await resInvoices.text());
        }

        statsContainer.innerHTML = `
            <div>Tổng số hội viên: <b>${totalMembers}</b></div>
            <div>Tổng doanh thu: <b>${totalRevenue}đ</b></div>
        `;

    } catch (error) {
        console.error('Error fetching statistics:', error);
        statsContainer.innerHTML = '<div style="color:red">Không thể tải dữ liệu thống kê!</div>';
    }

}
window.showThongKe = showThongKe;

function logout() {
    removeToken(); // Xóa token khỏi localStorage
    window.location.href = 'index.html'; // Chuyển hướng về trang đăng nhập
}

window.logout = logout; // Make logout function globally accessible 
// Import necessary functions from config.js
import { getToken, removeToken } from './config.js';


const API_BASE = 'http://localhost:8080/api';
const mainContentArea = document.getElementById('main-content');
const popupContainer = document.getElementById('form-popup-container');

// Function to set active menu item
function setActiveMenu(index) {
    const items = document.querySelectorAll('.menu li');
    items.forEach((li, i) => {
        if (i === index) li.classList.add('active');
        else li.classList.remove('active');
    });
}

// Function to display member management section
async function showMembers() {
    setActiveMenu(0);
    mainContentArea.innerHTML = `
        <h2>Quản lý hội viên</h2>
        <button class="btn-red" id="add-member-btn">Thêm hội viên mới</button>
        <div id="members-table-container"></div>
        <div id="member-form-popup" class="popup-form" style="display:none"></div>
    `;
    document.getElementById('add-member-btn').onclick = function() {
        showMemberForm(); // Call showMemberForm without arguments for add mode
    };
    loadMembers(); // Placeholder function to load member data
}

// Function to display invoice management section
async function showInvoices() {
    setActiveMenu(1);
    mainContentArea.innerHTML = `
        <h2>Quản lý hóa đơn</h2>
        <button class="btn-red" id="create-invoice-session-btn">Tạo hóa đơn tập theo buổi</button>
        <button class="btn-red" id="create-invoice-package-btn">Tạo hóa đơn bán gói tập</button>
        <div id="invoices-table-container">
            <table class="table-list">
                <thead>
                    <tr>
                        <th>Mã hóa đơn</th>
                        <th>Tên hóa đơn</th>
                        <th>Ngày thanh toán</th>
                        <th>Giá tiền</th>
                        <th>Ghi chú</th>
                        <th>Hội viên</th>
                        <th>Gói tập</th>
                        <th>Nhân viên tạo</th>
                    </tr>
                </thead>
                <tbody id="invoice-table-body">
                    <!-- Invoice rows will be loaded here -->
                </tbody>
            </table>
        </div>
         <div id="invoice-form-popup" class="popup-form" style="display:none"></div>
    `;
    document.getElementById('create-invoice-session-btn').onclick = showSessionInvoiceForm; // Placeholder
    document.getElementById('create-invoice-package-btn').onclick = showPackageInvoiceForm; // Placeholder
    loadInvoices(); // Placeholder function to load invoice data
}

// Function to display check-in section
async function showCheckin() {
    setActiveMenu(2);
    mainContentArea.innerHTML = `
        <h2>Quản lý check-in</h2>
        <button class="btn-red" id="start-checkin-btn">Check-in hội viên</button>
        <div id="checkin-table-container">
            <table class="table-list">
                <thead>
                    <tr>
                        <th>Ngày Check-in</th>
                        <th>Buổi Check-in</th>
                        <th>Hội viên</th>
                        <th>Nhân viên thực hiện</th>
                    </tr>
                </thead>
                <tbody id="checkin-table-body">
                    <!-- Check-in rows will be loaded here -->
                </tbody>
            </table>
        </div>
    `;
    
    // Attach event listener to the check-in button
    document.getElementById('start-checkin-btn').onclick = showCheckinPopup;
    
    // Load check-in history
    loadCheckinHistory();
}

// Function to load check-in history
async function loadCheckinHistory() {
    const token = getToken();
    try {
        const res = await fetch(`${API_BASE}/check-in`, {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        });
        if (!res.ok) throw new Error('Failed to load check-in history');
        const checkins = await res.json();
        displayCheckins(checkins);
    } catch (error) {
        console.error('Error loading check-in history:', error);
        document.getElementById('checkin-table-container').innerHTML = 
            '<div style="color:red">Không thể tải lịch sử check-in! Vui lòng kiểm tra kết nối và thử lại sau.</div>';
    }
}

// Function to display check-ins in the table
function displayCheckins(checkins) {
    const tableBody = document.getElementById('checkin-table-body');
    if (!tableBody) return;

    tableBody.innerHTML = ''; // Clear existing rows

    if (!Array.isArray(checkins) || checkins.length === 0) {
        tableBody.innerHTML = '<tr><td colspan="4" style="text-align:center;">Không có lịch sử check-in nào.</td></tr>';
        return;
    }

    checkins.forEach(checkin => {
        const row = document.createElement('tr');
        // Hiển thị hội viên kèm chữ 'CCCD: ' nếu có
        row.innerHTML = `
            <td>${checkin.checkInTime ? new Date(checkin.checkInTime).toLocaleDateString() : ''}</td>
            <td>${checkin.buoiCheckIn || ''}</td>
            <td>${checkin.hoTenHoiVien || ''}${checkin.cccdHoiVien ? ' (CCCD: ' + checkin.cccdHoiVien + ')' : ''}</td>
            <td>${checkin.hoTenNhanVien || ''}</td>
        `;
        tableBody.appendChild(row);
    });
}

// Function to show the check-in popup
async function showCheckinPopup() {
    // Ensure all other popups are hidden first
    closeAllPopups();

    const popup = document.getElementById('form-popup-container');
    popup.style.display = 'block';
    // Content is directly rendered into form-popup-container for check-in
    popup.innerHTML = `
        <div id="checkin-popup" class="popup-form">
            <div class="form-container">
                <span class="close-btn" onclick="closeFormPopup()">×</span>
                <h3>Check-in hội viên</h3>
                <div id="checkin-form-area">
                    <label for="checkin-member-select">Chọn hội viên:</label>
                    <select id="checkin-member-select" required>
                        <option value="">--- Chọn hội viên ---</option>
                        <!-- Member options will be loaded here -->
                    </select><br>

                    <label for="BuoiCheckIn">Buổi Check-in:</label>
                    <input type="text" id="BuoiCheckIn" name="BuoiCheckIn" placeholder="Ví dụ: Sáng, Chiều, Tối"><br>

                    <button id="process-checkin-btn" class="btn-red">Xác nhận Check-in</button>
                </div>
                <div id="checkin-result" style="margin-top: 15px;"></div>
            </div>
        </div>
    `;

    // Load members into the dropdown
    loadMembersForCheckinDropdown();

    // Attach event listener to the process button
    document.getElementById('process-checkin-btn').onclick = async () => {
        await processSelectedMemberCheckin();
        // Reload check-in history after successful check-in
        loadCheckinHistory();
    };
}

// Function to load members into the dynamically created select dropdown
async function loadMembersForCheckinDropdown() {
    const token = getToken();
    const selectElement = document.getElementById('checkin-member-select'); // Target the dynamic select
    if (!selectElement) return; // Ensure element exists if popup is closed quickly

    selectElement.innerHTML = '<option value="">--- Chọn hội viên ---</option>'; // Clear previous options

    try {
        const res = await fetch(`${API_BASE}/hoi-vien`, {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        });
        if (!res.ok) throw new Error('Failed to load members for check-in');
        const members = await res.json();

        members.forEach(member => {
            const option = document.createElement('option');
            option.value = member.maHoiVien; // Keep maHoiVien as value for backend
            option.textContent = `${member.hoTen} (CCCD: ${member.cccd})`; // Show CCCD instead of ID
            selectElement.appendChild(option);
        });

    } catch (error) {
        console.error('Error loading members for check-in:', error);
        // Optionally display an error message in the dropdown or result area
        const option = document.createElement('option');
        option.value = "";
        option.textContent = "Lỗi tải danh sách hội viên";
        selectElement.appendChild(option); // Add error option
        selectElement.disabled = true; // Disable dropdown on error
        // Also inform the user in the result area
        const checkinResultDiv = document.getElementById('checkin-result');
        if(checkinResultDiv) checkinResultDiv.innerHTML = '<div style="color:red">Không thể tải danh sách hội viên. Vui lòng thử lại sau.</div>';
    }
}

// Process the check-in after a member is selected
async function processSelectedMemberCheckin() {
    const memberSelectElement = document.getElementById('checkin-member-select');
    const BuoiCheckInElement = document.getElementById('BuoiCheckIn');
    const checkinResultDiv = document.getElementById('checkin-result');
    const maHoiVien = memberSelectElement.value;
    const user = JSON.parse(localStorage.getItem('user'));
    const maNhanVienLeTan = user ? user.maNhanVien : null;
    const BuoiCheckIn = BuoiCheckInElement ? BuoiCheckInElement.value : null; // Get the value

    // Clear previous results
    if(checkinResultDiv) checkinResultDiv.innerHTML = '';

    if (!maHoiVien) {
        if(checkinResultDiv) checkinResultDiv.innerHTML = '<div style="color:orange">Vui lòng chọn một hội viên từ danh sách.</div>';
        return;
    }

     if (!maNhanVienLeTan) {
        if(checkinResultDiv) checkinResultDiv.innerHTML = '<div style="color:red">Lỗi: Không tìm thấy thông tin nhân viên đăng nhập.</div>';
        return;
    }

    if(checkinResultDiv) checkinResultDiv.innerHTML = 'Đang xử lý check-in...';
    const token = getToken();

    try {
        const res = await fetch(`${API_BASE}/check-in`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`
            },
            body: JSON.stringify({ 
                maHoiVien: parseInt(maHoiVien), 
                maNhanVienLeTan: parseInt(maNhanVienLeTan),
                buoiCheckIn: BuoiCheckIn // Sửa key về đúng chuẩn backend
            })
        });

        const result = await res.json();

        if (res.ok) {
            let message = `<div style="color:green">Check-in thành công cho hội viên ${result.hoTenHoiVien}.`;
            if (result.tenGoiTap) {
                 message += ` Gói tập: ${result.tenGoiTap}.`;
            }
             if (result.ngayKetThucGoiTap) {
                 message += ` Hết hạn: ${result.ngayKetThucGoiTap}.`;
            }
             if (result.soBuoiConLai != null) { // Check specifically for not null
                 message += ` Còn lại ${result.soBuoiConLai} buổi.`;
            }
             message += ` Thời gian check-in: ${result.checkInTime}.</div>`;
            if(checkinResultDiv) checkinResultDiv.innerHTML = message;
             // Optionally clear the form or close the popup after successful check-in
             // closeFormPopup(); 
        } else {
            // Handle specific backend errors if any
            const errorMessage = result.message || 'Không thể check-in. Vui lòng kiểm tra lại thông tin hội viên hoặc trạng thái gói tập.';
            if(checkinResultDiv) checkinResultDiv.innerHTML = `<div style="color:red">${errorMessage}</div>`;
        }
    } catch (error) {
        console.error('Error during check-in:', error);
        if(checkinResultDiv) checkinResultDiv.innerHTML = '<div style="color:red">Đã xảy ra lỗi trong quá trình check-in.</div>';
    }
}

// --- Placeholder Functions (to be implemented or already implemented) ---

async function loadMembers() {
    console.log('Loading members...');
    // Fetch and display members in #members-table-container
    const token = getToken();
    try {
        const res = await fetch(`${API_BASE}/hoi-vien`, {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        });
        if (!res.ok) throw new Error('Failed to load members');
        const members = await res.json();
        displayMembers(members);
    } catch (error) {
        console.error('Error loading members:', error);
        document.getElementById('members-table-container').innerHTML = '<div style="color:red">Không thể tải danh sách hội viên!</div>';
    }
}

function displayMembers(members) {
    let html = `<table class="table-list"><tr>
        
        <th>Tên</th>
        <th>Giới tính</th>
        <th>Ngày sinh</th>
        <th>Điện thoại</th>
        <th>Email</th>
        <th>CCCD</th>
        <th>Tài khoản</th>
        <th>Gói tập hiện tại</th>
        <th>Chức năng</th>
    </tr>`;
    members.forEach(member => {
        const packageInfo = member.activePackageName === "Không khả dụng" 
            ? "Không khả dụng"
            : `${member.activePackageName || ''} (Hết hạn: ${member.activePackageEndDate || ''})`;

        html += `<tr>
            
            <td>${member.hoTen || ''}</td>
            <td>${member.gioiTinh === true ? 'Nam' : (member.gioiTinh === false ? 'Nữ' : '')}</td>
            <td>${member.ngaySinh ? member.ngaySinh.substring(0, 10) : ''}</td>
            <td>${member.sdt || ''}</td>
            <td>${member.email || ''}</td>
            <td>${member.cccd || ''}</td>
            <td>${member.taiKhoan || ''}</td>
            <td>${packageInfo}</td>
            <td>
                <button onclick="editMember(${member.maHoiVien})" class="btn-edit">Sửa</button>
                <button onclick="deleteMember(${member.maHoiVien})" class="btn-delete">Xóa</button>
            </td>
        </tr>`;
    });
    html += `</table>`;
    document.getElementById('members-table-container').innerHTML = html;
}

async function loadInvoices() {
    console.log('Loading invoices...');
    // Fetch and display invoices in #invoices-table-container
     const token = getToken();
    try {
        const res = await fetch(`${API_BASE}/hoa-don`, {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        });
        if (!res.ok) throw new Error('Failed to load invoices');
        const invoices = await res.json();
        displayInvoices(invoices);
    } catch (error) {
        console.error('Error loading invoices:', error);
        document.getElementById('invoices-table-container').innerHTML = '<div style="color:red">Không thể tải danh sách hóa đơn!</div>';
    }
}

function displayInvoices(invoices) {
    const invoiceTableBody = document.getElementById('invoice-table-body');
    if (!invoiceTableBody) return; // Ensure the table body exists

    invoiceTableBody.innerHTML = ''; // Clear existing rows

    if (!Array.isArray(invoices) || invoices.length === 0) {
        invoiceTableBody.innerHTML = '<tr><td colspan="8" style="text-align:center;">Không có hóa đơn nào.</td></tr>';
        return;
    }

    invoices.forEach(invoice => {
        const row = document.createElement('tr');

        // Format date (optional, basic example)
        const ngayThanhToan = invoice.ngayThanhToan ? new Date(invoice.ngayThanhToan).toLocaleString() : '';

        row.innerHTML = `
            <td>${invoice.maHoaDon || ''}</td>
            <td>${invoice.tenHoaDon || ''}</td>
            <td>${ngayThanhToan}</td>
            <td>${invoice.giaTien || ''}đ</td>
            <td>${invoice.ghiChu || ''}</td>
            <td>${invoice.tenHoiVien || ''}</td>
            <td>${invoice.tenGoiTap || ''}</td>
            <td>${invoice.tenNhanVien || ''}</td>
        `;
        invoiceTableBody.appendChild(row);
    });
}

// Function to show member form popup
function showMemberForm(member = null) {
    // Ensure all other popups are hidden first
    closeAllPopups();

    const popup = document.getElementById('member-form-popup');
    popup.style.display = 'block';
    // Clear previous content to ensure a fresh form for 'Add' mode
    popup.innerHTML = '';
    popup.innerHTML = `
        <div class="form-container">
            <span class="close-btn" onclick="closeFormPopup()">×</span>
            <h3>${member ? 'Sửa' : 'Thêm'} hội viên</h3>
            <form id="memberForm">
                <input type="hidden" name="maHoiVien" value="${member ? member.maHoiVien : ''}">

                <label for="hoTen">Họ tên:</label>
                <input type="text" id="hoTen" name="hoTen" value="${member ? member.hoTen : ''}" placeholder="Nhập họ tên" required><br>

                <label for="gioiTinh">Giới tính:</label>
                <select id="gioiTinh" name="gioiTinh" required>
                    <option value="true" ${member && member.gioiTinh === true ? 'selected' : ''}>Nam</option>
                    <option value="false" ${member && member.gioiTinh === false ? 'selected' : ''}>Nữ</option>
                </select><br>

                <label for="ngaySinh">Ngày sinh:</label>
                <input type="date" id="ngaySinh" name="ngaySinh" value="${member && member.ngaySinh ? member.ngaySinh.substring(0, 10) : ''}" required><br>

                <label for="sdt">Điện thoại:</label>
                <input type="text" id="sdt" name="sdt" value="${member ? member.sdt : ''}" placeholder="Nhập số điện thoại" required><br>

                <label for="email">Email:</label>
                <input type="email" id="email" name="email" value="${member ? member.email : ''}" placeholder="Nhập email"><br>

                <label for="cccd">CCCD:</label>
                <input type="text" id="cccd" name="cccd" value="${member ? member.cccd : ''}" placeholder="Nhập số CCCD"><br>

                <label for="taiKhoan">Tài khoản:</label>
                <input type="text" id="taiKhoan" name="taiKhoan" value="${member ? member.taiKhoan : ''}" ${member ? 'disabled' : 'required'} placeholder="Nhập tài khoản"><br>

                <label for="matKhau">Mật khẩu:</label>
                <input type="password" id="matKhau" name="matKhau" value="${member ? member.matKhau : ''}" ${member ? '' : 'required'} placeholder="Nhập mật khẩu"><br>

                <button type="submit" class="btn-red">${member ? 'Lưu' : 'Thêm'}</button>
                <button type="button" onclick="closeFormPopup()">Hủy</button>
            </form>
        </div>
    `;
    document.getElementById('memberForm').onsubmit = handleMemberFormSubmit;
}
window.showMemberForm = showMemberForm; // Make it accessible from inline HTML

// Handle member form submission (Add/Edit)
async function handleMemberFormSubmit(event) {
    event.preventDefault();
    const form = event.target;
    const formData = new FormData(form);
    const memberData = Object.fromEntries(formData.entries());

    const memberId = memberData.maHoiVien;

    const token = getToken();

    let res;
    try {
        if (memberId) {
            // For PUT request, only send fields that can be updated.
            // Don't send maHoiVien and taiKhoan.
            const updateData = { ...memberData };
            delete updateData.maHoiVien;
            delete updateData.taiKhoan; // Tài khoản không đổi khi sửa
            
            // Only include matKhau if it's not empty
            if (!updateData.matKhau) {
                delete updateData.matKhau;
            }

            res = await fetch(`${API_BASE}/hoi-vien/${memberId}`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}`
                },
                body: JSON.stringify(updateData)
            });
        } else {
            // For POST request (new member)
            res = await fetch(`${API_BASE}/hoi-vien`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}`
                },
                body: JSON.stringify(memberData)
            });
        }

        if (!res.ok) {
            const errorData = await res.json();
            throw new Error(errorData.message || 'Failed to save member');
        }

        closeFormPopup();
        loadMembers(); // Reload the member list
    } catch (error) {
        console.error('Error saving member:', error);
        alert('Lỗi khi lưu thông tin hội viên: ' + error.message);
    }
}

// Function to edit a member
window.editMember = async function(id) {
    console.log('Editing member with ID:', id);
     const token = getToken();
    try {
        const res = await fetch(`${API_BASE}/hoi-vien/${id}`, {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        });
        if (!res.ok) throw new Error('Failed to fetch member for edit');
        const member = await res.json();
        console.log('Fetched member data:', member);
        showMemberForm(member);
    } catch (error) {
        console.error('Error fetching member for edit:', error);
        alert('Không thể tải thông tin hội viên để sửa.');
    }
}
window.editMember = editMember;

// Function to delete a member
async function deleteMember(memberId) {
    if (!confirm('Bạn có chắc muốn xóa hội viên này?')) return;
    const token = getToken();
    try {
        const res = await fetch(`${API_BASE}/hoi-vien/${memberId}`, {
            method: 'DELETE',
            headers: {
                'Authorization': `Bearer ${token}`
            }
        });
        if (!res.ok) throw new Error('Failed to delete member');
        loadMembers(); // Reload members list
    } catch (error) {
        console.error('Error deleting member:', error);
        alert('Đã xảy ra lỗi khi xóa hội viên.');
    }
}
window.deleteMember = deleteMember;


// Function to show session invoice form popup
function showSessionInvoiceForm() {
     // Ensure all other popups are hidden first
    closeAllPopups();

     const popup = document.getElementById('invoice-form-popup');
    popup.style.display = 'block';
    popup.innerHTML = `
        <div class="form-container">
            <span class="close-btn" onclick="closeFormPopup()">×</span>
            <h3>Tạo hóa đơn tập theo buổi</h3>
            <form id="sessionInvoiceForm">
                 <label for="session-invoice-ten-hoa-don">Tên hóa đơn:</label>
                <input type="text" id="session-invoice-ten-hoa-don" name="tenHoaDon" placeholder="Nhập tên hóa đơn"><br>

                 <label for="session-invoice-gia-tien">Tổng tiền:</label>
                <input type="number" id="session-invoice-gia-tien" name="giaTien" required min="0" placeholder="Nhập tổng tiền"><br>

                 <label for="session-invoice-ghi-chu">Ghi chú:</label>
                 <textarea id="session-invoice-ghi-chu" name="ghiChu" placeholder="Nhập ghi chú"></textarea><br>

                 <button type="submit" class="btn-red">Tạo hóa đơn</button>
                <button type="button" onclick="closeFormPopup()">Hủy</button>
            </form>
        </div>
    `;
    document.getElementById('sessionInvoiceForm').onsubmit = handleSessionInvoiceFormSubmit;
}

// Handle session invoice form submission
async function handleSessionInvoiceFormSubmit(event) {
    event.preventDefault();
    const form = event.target;
    const formData = new FormData(form);
    const invoiceData = Object.fromEntries(formData.entries());

    const token = getToken();
    const user = JSON.parse(localStorage.getItem('user'));
    const maNhanVienLeTan = user ? user.maNhanVien : null;

    if (!maNhanVienLeTan) {
        alert('Không tìm thấy thông tin nhân viên đăng nhập.');
        return;
    }

    try {
        const res = await fetch(`${API_BASE}/hoa-don/session`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`
            },
            body: JSON.stringify({ 
                tenHoaDon: invoiceData.tenHoaDon || null, 
                maHoiVien: null, 
                giaTien: parseFloat(invoiceData.giaTien), 
                ghiChu: invoiceData.ghiChu || null, 
                maNhanVienLeTan: parseInt(maNhanVienLeTan) 
            })
        });

        if (res.ok) {
            closeFormPopup();
            loadInvoices(); // Reload invoices list
        } else {
             const error = await res.text();
            alert(`Lỗi khi tạo hóa đơn buổi tập: ${error}`);
        }
    } catch (error) {
        console.error('Error creating session invoice:', error);
        alert('Đã xảy ra lỗi khi tạo hóa đơn buổi tập.');
    }
}

// Function to show package invoice form popup
async function showPackageInvoiceForm() {
     // Ensure all other popups are hidden first
    closeAllPopups();

     const token = getToken();
     let packages = [];
     let members = []; // Variable to store members

     try {
         // Fetch packages (existing logic)
         const resPackages = await fetch(`${API_BASE}/goi-tap`, {
             headers: {
                 'Authorization': `Bearer ${token}`
             }
         });
         if (!resPackages.ok) throw new Error('Failed to fetch packages');
         packages = await resPackages.json();
     } catch (error) {
         console.error('Error fetching packages:', error);
         alert('Không thể tải danh sách gói tập.');
         // Allow form to open even if packages fail to load, but dropdown will be empty
     }

    try {
        // Fetch members
        const resMembers = await fetch(`${API_BASE}/hoi-vien`, {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        });
        if (!resMembers.ok) throw new Error('Failed to fetch members');
        members = await resMembers.json();
    } catch (error) {
        console.error('Error fetching members:', error);
        alert('Không thể tải danh sách hội viên.');
        // Allow form to open even if members fail to load, but dropdown will be empty
    }

     const packageOptions = packages.map(pkg => `<option value="${pkg.maGoiTap}">${pkg.tenGoiTap} - ${pkg.giaGoiTap.toLocaleString('vi-VN')}đ</option>`).join(''); // Use giaGoiTap

    // Create member options for the dropdown
    const memberOptions = members.map(member => 
        `<option value="${member.maHoiVien}">${member.hoTen} (CCCD: ${member.cccd})</option>`
    ).join('');

     const popup = document.getElementById('invoice-form-popup');
    popup.style.display = 'block';
    popup.innerHTML = `
        <div class="form-container">
             <span class="close-btn" onclick="closeFormPopup()">×</span>
            <h3>Tạo hóa đơn bán gói tập</h3>
            <form id="packageInvoiceForm">
                 <label for="package-invoice-member">Hội viên:</label>
                 <select id="package-invoice-member" name="maHoiVien" required>
                     <option value="">--Chọn hội viên--</option>
                     ${memberOptions}
                 </select><br>

                 <label for="package-invoice-package">Gói tập:</label>
                 <select id="package-invoice-package" name="maGoiTap" required>
                     <option value="">--Chọn gói tập--</option>
                     ${packageOptions}
                 </select><br>

                 <label for="package-invoice-ghi-chu">Ghi chú (tùy chọn):</label>
                 <textarea id="package-invoice-ghi-chu" name="ghiChu" placeholder="Nhập ghi chú"></textarea><br>

                 <button type="submit" class="btn-red">Tạo hóa đơn</button>
                <button type="button" onclick="closeFormPopup()">Hủy</button>
            </form>
        </div>
    `;
    document.getElementById('packageInvoiceForm').onsubmit = handlePackageInvoiceFormSubmit;
}

// Handle package invoice form submission
async function handlePackageInvoiceFormSubmit(event) {
    event.preventDefault();
    const form = event.target;
    const formData = new FormData(form);
    const invoiceData = Object.fromEntries(formData.entries());

    const token = getToken();
    const user = JSON.parse(localStorage.getItem('user'));
    const maNhanVienLeTan = user ? user.maNhanVien : null;

    if (!maNhanVienLeTan) {
        alert('Không tìm thấy thông tin nhân viên đăng nhập.');
        return;
    }

    try {
        const res = await fetch(`${API_BASE}/hoa-don/package`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`
            },
            body: JSON.stringify({ 
                maHoiVien: parseInt(invoiceData.maHoiVien), 
                maGoiTap: parseInt(invoiceData.maGoiTap), 
                // tenHoaDon is generated in the backend for package invoices
                giaTien: null, 
                ghiChu: invoiceData.ghiChu || null, 
                maNhanVienLeTan: parseInt(maNhanVienLeTan) 
            })
        });

        if (res.ok) {
            closeFormPopup();
            loadInvoices(); // Reload invoices list
        } else {
             const error = await res.text();
            alert(`Lỗi khi tạo hóa đơn gói tập: ${error}`);
        }
    } catch (error) {
        console.error('Error creating package invoice:', error);
        alert('Đã xảy ra lỗi khi tạo hóa đơn gói tập.');
    }
}

// Function to close any open popup form
function closeFormPopup() {
    // Re-using closeAllPopups now
    closeAllPopups();
}
window.closeFormPopup = closeFormPopup;

// New function to hide all specific popup elements and clear the main container
function closeAllPopups() {
    const memberPopup = document.getElementById('member-form-popup');
    const invoicePopup = document.getElementById('invoice-form-popup');
    const checkinPopupContainer = document.getElementById('form-popup-container'); // Check-in uses the main container directly

    if (memberPopup) {
        memberPopup.style.display = 'none';
        memberPopup.innerHTML = ''; // Clear content when hiding
    }
    if (invoicePopup) {
        invoicePopup.style.display = 'none';
        invoicePopup.innerHTML = ''; // Clear content when hiding
    }
    if (checkinPopupContainer) {
         checkinPopupContainer.style.display = 'none';
        checkinPopupContainer.innerHTML = ''; // Clear content when hiding checkin
    }
}
window.closeAllPopups = closeAllPopups;

// Event listener for menu clicks
document.querySelectorAll('.menu li').forEach(item => {
    item.addEventListener('click', (e) => {
        e.preventDefault();
        const section = e.target.getAttribute('data-section');
        // Close any open popups first
        closeFormPopup();

        if (section === 'members') {
            showMembers();
        } else if (section === 'invoices') {
            showInvoices();
        } else if (section === 'checkin') {
            showCheckin(); // Call showCheckin() to display the list page
        }
    });
});

// Initial setup when script is executed (DOM is ready because script is at the end of body)
// Display user info (if available)
const userInfo = document.getElementById('user-info');
const user = JSON.parse(localStorage.getItem('user'));
if (user && user.hoTen) {
    userInfo.innerHTML = `Nhân viên lễ tân: <br> ${user.hoTen}`;
} else {
    // If no user info, redirect to login (basic check)
    if (!getToken()) {
        console.log('No token found, redirecting to index.html');
        window.location.href = './index.html';
    }
    userInfo.innerHTML = `Nhân viên lễ tân: <br> Không xác định`; // Fallback text
    console.log('User info not found in localStorage, displaying fallback.');
}

// Display a welcome message in the main content area initially
mainContentArea.innerHTML = `
    <h2>Chào mừng bạn đến với hệ thống quản lý phòng gym!</h2>
    <p>Các chức năng nằm ở thanh menu bên trái.</p>
`;

// Handle logout
const logoutLink = document.getElementById('logout-link');
if (logoutLink) {
    console.log('Adding click listener to logout link');
    logoutLink.addEventListener('click', (e) => {
        e.preventDefault();
        console.log('Logout link clicked');
        removeToken(); // Remove token using function from config.js
        localStorage.removeItem('user'); // Also remove user info
        console.log('Token and user info removed from localStorage');
        window.location.href = './index.html'; // Redirect to login page
    });
} else {
    console.log('Logout link element not found!');
}

// Placeholder for viewing invoice detail
function viewInvoiceDetail(invoiceId) {
    console.log('Viewing invoice detail for ID:', invoiceId);
    alert('Chức năng xem chi tiết hóa đơn chưa được triển khai.');
}
window.viewInvoiceDetail = viewInvoiceDetail;

// Placeholder for deleting invoice
async function deleteInvoice(invoiceId) {
    if (!confirm('Bạn có chắc muốn xóa hóa đơn này?')) return;
    const token = getToken();
    try {
        const res = await fetch(`${API_BASE}/hoa-don/${invoiceId}`, {
            method: 'DELETE',
            headers: {
                'Authorization': `Bearer ${token}`
            }
        });
        if (!res.ok) throw new Error('Failed to delete invoice');
        loadInvoices(); // Reload invoices list
    } catch (error) {
        console.error('Error deleting invoice:', error);
        alert('Đã xảy ra lỗi khi xóa hóa đơn.');
    }
}
window.deleteInvoice = deleteInvoice;
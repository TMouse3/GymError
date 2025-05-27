
USE GymErrorDB;
GO

-- 1. Chủ phòng gym
CREATE TABLE ChuPhongGym (
    TaiKhoan NVARCHAR(20) PRIMARY KEY,
    MatKhau NVARCHAR(200) NOT NULL -- bcrypt
);
GO

-- 2. Nhân viên lễ tân
CREATE TABLE NhanVienLeTan (
    MaNhanVien INT IDENTITY PRIMARY KEY,
    HoTen NVARCHAR(50),
    GioiTinh BIT,
    NgaySinh DATE,
    SDT NVARCHAR(10),
    Email NVARCHAR(50),
    CCCD NVARCHAR(12) UNIQUE,
    TaiKhoan NVARCHAR(20) UNIQUE,
    MatKhau NVARCHAR(200),
    TrangThaiLamViec BIT -- 0: còn làm, 1: nghỉ
);
GO

-- 3. PT
CREATE TABLE PT (
    MaPT INT IDENTITY PRIMARY KEY,
    HoTen NVARCHAR(50),
    GioiTinh BIT,
    NgaySinh DATE,
    SDT NVARCHAR(10),
    Email NVARCHAR(50),
    CCCD NVARCHAR(12) UNIQUE,
    BangCap NVARCHAR(200),
    KinhNghiem INT,
    TrangThai BIT, -- 0: sẵn sàng, 1: bận
    TaiKhoan NVARCHAR(20) UNIQUE,
    MatKhau NVARCHAR(200),
    TrangThaiLamViec BIT
);
GO

-- 4. Hội viên
CREATE TABLE HoiVien (
    MaHoiVien INT IDENTITY PRIMARY KEY,
    HoTen NVARCHAR(50),
    GioiTinh BIT,
    NgaySinh DATE,
    SDT NVARCHAR(10),
    Email NVARCHAR(50),
    CCCD NVARCHAR(12) UNIQUE,
    TaiKhoan NVARCHAR(20) UNIQUE,
    MatKhau NVARCHAR(200)
);
GO

-- 5. Gói tập
CREATE TABLE GoiTap (
    MaGoiTap INT IDENTITY PRIMARY KEY,
    TenGoiTap NVARCHAR(50),
    SoNgay INT,
    GiaGoiTap FLOAT,
    TrangThaiGoiTap BIT, -- 0: khả dụng, 1: ẩn
    MoTaGoiTap NVARCHAR(200)
);
GO

-- 6. Gói tập của hội viên
CREATE TABLE GoiTapHoiVien (
    MaHoiVien INT,
    MaGoiTap INT,
    NgayBatDau DATE,
    NgayKetThuc DATE,
    PRIMARY KEY (MaHoiVien, MaGoiTap),
    FOREIGN KEY (MaHoiVien) REFERENCES HoiVien(MaHoiVien),
    FOREIGN KEY (MaGoiTap) REFERENCES GoiTap(MaGoiTap)
);
GO

-- 7. Hóa đơn
CREATE TABLE HoaDon (
    MaHoaDon INT IDENTITY PRIMARY KEY,
    TenHoaDon NVARCHAR(50),
    NgayThanhToan DATETIME,
    GiaTien FLOAT,
    GhiChu NVARCHAR(200),
    MaNhanVien INT NULL,
    MaHoiVien INT NULL,
    MaGoiTap INT,
    FOREIGN KEY (MaNhanVien) REFERENCES NhanVienLeTan(MaNhanVien),
    FOREIGN KEY (MaHoiVien) REFERENCES HoiVien(MaHoiVien),
    FOREIGN KEY (MaGoiTap) REFERENCES GoiTap(MaGoiTap)
);
GO

-- 8. Bài tập
CREATE TABLE BaiTap (
    MaBaiTap INT IDENTITY PRIMARY KEY,
    TenBaiTap NVARCHAR(50),
    MoTaBaiTap NVARCHAR(200)
);
GO

-- 9. Buổi tập
CREATE TABLE BuoiTap (
    MaBuoiTap INT IDENTITY PRIMARY KEY,
    MaPT INT,
    MaHoiVien INT,
    NgayTap DATE,
    Buoi NVARCHAR(10), -- Sáng, Chiều, Tối
    TrangThai INT, -- 0: xác nhận, 1: chờ xác nhận, -1: hủy
    FOREIGN KEY (MaPT) REFERENCES PT(MaPT),
    FOREIGN KEY (MaHoiVien) REFERENCES HoiVien(MaHoiVien)
);
GO

-- 10. Bài tập của buổi tập
CREATE TABLE BaiTapBuoiTap (
    MaBuoiTap INT,
    MaBaiTap INT,
    PRIMARY KEY (MaBuoiTap, MaBaiTap),
    FOREIGN KEY (MaBuoiTap) REFERENCES BuoiTap(MaBuoiTap),
    FOREIGN KEY (MaBaiTap) REFERENCES BaiTap(MaBaiTap)
);
GO

-- 11. Check-in
CREATE TABLE CheckIn (
    MaCheckIn INT IDENTITY PRIMARY KEY,
    NgayCheckIn DATE,
    BuoiCheckIn NVARCHAR(10),
    MaHoiVien INT,
    MaNhanVien INT,
    FOREIGN KEY (MaHoiVien) REFERENCES HoiVien(MaHoiVien),
    FOREIGN KEY (MaNhanVien) REFERENCES NhanVienLeTan(MaNhanVien)
);
GO

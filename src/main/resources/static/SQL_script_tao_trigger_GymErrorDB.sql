USE GymErrorDB;
GO

-- 1. Check-in chỉ cho hội viên có gói tập còn hiệu lực
CREATE TRIGGER trg_CheckValidGoiTapOnCheckIn
ON CheckIn
INSTEAD OF INSERT
AS
BEGIN
    IF EXISTS (
        SELECT 1
        FROM inserted i
        LEFT JOIN GoiTapHoiVien gtv
            ON gtv.MaHoiVien = i.MaHoiVien
           AND i.NgayCheckIn BETWEEN gtv.NgayBatDau AND gtv.NgayKetThuc
        WHERE gtv.MaHoiVien IS NULL
    )
    BEGIN
        RAISERROR(N'Hội viên không có gói tập còn hiệu lực. Không thể check-in.', 16, 1);
        RETURN;
    END

    INSERT INTO CheckIn (NgayCheckIn, BuoiCheckIn, MaHoiVien, MaNhanVien)
    SELECT NgayCheckIn, BuoiCheckIn, MaHoiVien, MaNhanVien
    FROM inserted;
END;
GO

-- 2. Không cho phép tạo buổi tập nếu hội viên không có gói tập
CREATE TRIGGER trg_PreventInsertBuoiTap
ON BuoiTap
INSTEAD OF INSERT
AS
BEGIN
    IF EXISTS (
        SELECT 1
        FROM inserted i
        LEFT JOIN GoiTapHoiVien gtv
            ON gtv.MaHoiVien = i.MaHoiVien
           AND i.NgayTap BETWEEN gtv.NgayBatDau AND gtv.NgayKetThuc
        WHERE gtv.MaHoiVien IS NULL
    )
    BEGIN
        RAISERROR(N'Hội viên không có gói tập hợp lệ. Không thể đăng ký buổi tập.', 16, 1);
        RETURN;
    END

    INSERT INTO BuoiTap (MaPT, MaHoiVien, NgayTap, Buoi, TrangThai)
    SELECT MaPT, MaHoiVien, NgayTap, Buoi, TrangThai
    FROM inserted;
END;
GO

-- 3. Cập nhật ngày kết thúc gói tập dựa vào ngày bắt đầu + số ngày của gói
CREATE TRIGGER trg_UpdateNgayKetThucGoiTap
ON GoiTapHoiVien
AFTER INSERT, UPDATE
AS
BEGIN
    UPDATE gtv
    SET NgayKetThuc = DATEADD(DAY, gt.SoNgay, gtv.NgayBatDau)
    FROM GoiTapHoiVien gtv
    JOIN GoiTap gt ON gt.MaGoiTap = gtv.MaGoiTap
    JOIN inserted i ON i.MaHoiVien = gtv.MaHoiVien AND i.MaGoiTap = gtv.MaGoiTap;
END;
GO

-- 4. Ngăn xóa PT nếu còn buổi tập liên kết
CREATE TRIGGER trg_PreventDeletePT
ON PT
INSTEAD OF DELETE
AS
BEGIN
    IF EXISTS (
        SELECT 1
        FROM deleted d
        JOIN BuoiTap bt ON bt.MaPT = d.MaPT
    )
    BEGIN
        RAISERROR(N'Không thể xóa PT vì đang có buổi tập liên kết.', 16, 1);
        RETURN;
    END

    DELETE FROM PT WHERE MaPT IN (SELECT MaPT FROM deleted);
END;
GO

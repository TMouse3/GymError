USE [master]
GO
/****** Object:  Database [GymErrorDB]    Script Date: 05/27/2025 22:43:26 ******/
CREATE DATABASE [GymErrorDB]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'GymErrorDB', FILENAME = N'D:\Project\SpringBoot\CSDLGymError\GymErrorDB.mdf' , SIZE = 4096KB , MAXSIZE = UNLIMITED, FILEGROWTH = 1024KB )
 LOG ON 
( NAME = N'GymErrorDB_log', FILENAME = N'D:\Project\SpringBoot\CSDLGymError\GymErrorDB_log.ldf' , SIZE = 1024KB , MAXSIZE = 2048GB , FILEGROWTH = 10%)
GO
ALTER DATABASE [GymErrorDB] SET COMPATIBILITY_LEVEL = 120
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [GymErrorDB].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [GymErrorDB] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [GymErrorDB] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [GymErrorDB] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [GymErrorDB] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [GymErrorDB] SET ARITHABORT OFF 
GO
ALTER DATABASE [GymErrorDB] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [GymErrorDB] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [GymErrorDB] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [GymErrorDB] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [GymErrorDB] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [GymErrorDB] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [GymErrorDB] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [GymErrorDB] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [GymErrorDB] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [GymErrorDB] SET  DISABLE_BROKER 
GO
ALTER DATABASE [GymErrorDB] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [GymErrorDB] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [GymErrorDB] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [GymErrorDB] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [GymErrorDB] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [GymErrorDB] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [GymErrorDB] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [GymErrorDB] SET RECOVERY SIMPLE 
GO
ALTER DATABASE [GymErrorDB] SET  MULTI_USER 
GO
ALTER DATABASE [GymErrorDB] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [GymErrorDB] SET DB_CHAINING OFF 
GO
ALTER DATABASE [GymErrorDB] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [GymErrorDB] SET TARGET_RECOVERY_TIME = 0 SECONDS 
GO
ALTER DATABASE [GymErrorDB] SET DELAYED_DURABILITY = DISABLED 
GO
USE [GymErrorDB]
GO
/****** Object:  Table [dbo].[BaiTap]    Script Date: 05/27/2025 22:43:26 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[BaiTap](
	[MaBaiTap] [int] IDENTITY(1,1) NOT NULL,
	[TenBaiTap] [nvarchar](50) NULL,
	[MoTaBaiTap] [nvarchar](200) NULL,
PRIMARY KEY CLUSTERED 
(
	[MaBaiTap] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[BaiTapBuoiTap]    Script Date: 05/27/2025 22:43:26 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[BaiTapBuoiTap](
	[MaBuoiTap] [int] NOT NULL,
	[MaBaiTap] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[MaBuoiTap] ASC,
	[MaBaiTap] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[BuoiTap]    Script Date: 05/27/2025 22:43:26 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[BuoiTap](
	[MaBuoiTap] [int] IDENTITY(1,1) NOT NULL,
	[MaPT] [int] NULL,
	[MaHoiVien] [int] NULL,
	[NgayTap] [date] NULL,
	[Buoi] [nvarchar](10) NULL,
	[TrangThai] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[MaBuoiTap] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[CheckIn]    Script Date: 05/27/2025 22:43:26 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[CheckIn](
	[MaCheckIn] [int] IDENTITY(1,1) NOT NULL,
	[NgayCheckIn] [date] NULL,
	[BuoiCheckIn] [nvarchar](10) NULL,
	[MaHoiVien] [int] NULL,
	[MaNhanVien] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[MaCheckIn] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[ChuPhongGym]    Script Date: 05/27/2025 22:43:26 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ChuPhongGym](
	[TaiKhoan] [nvarchar](20) NOT NULL,
	[MatKhau] [nvarchar](200) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[TaiKhoan] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[GoiTap]    Script Date: 05/27/2025 22:43:26 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[GoiTap](
	[MaGoiTap] [int] IDENTITY(1,1) NOT NULL,
	[TenGoiTap] [nvarchar](50) NULL,
	[SoNgay] [int] NULL,
	[GiaGoiTap] [float] NULL,
	[TrangThaiGoiTap] [bit] NULL,
	[MoTaGoiTap] [nvarchar](200) NULL,
PRIMARY KEY CLUSTERED 
(
	[MaGoiTap] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[GoiTapHoiVien]    Script Date: 05/27/2025 22:43:26 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[GoiTapHoiVien](
	[MaHoiVien] [int] NOT NULL,
	[MaGoiTap] [int] NOT NULL,
	[NgayBatDau] [date] NULL,
	[NgayKetThuc] [date] NULL,
PRIMARY KEY CLUSTERED 
(
	[MaHoiVien] ASC,
	[MaGoiTap] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[HoaDon]    Script Date: 05/27/2025 22:43:26 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[HoaDon](
	[MaHoaDon] [int] IDENTITY(1,1) NOT NULL,
	[TenHoaDon] [nvarchar](50) NULL,
	[NgayThanhToan] [datetime] NULL,
	[GiaTien] [float] NULL,
	[GhiChu] [nvarchar](200) NULL,
	[MaNhanVien] [int] NULL,
	[MaHoiVien] [int] NULL,
	[MaGoiTap] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[MaHoaDon] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[HoiVien]    Script Date: 05/27/2025 22:43:26 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[HoiVien](
	[MaHoiVien] [int] IDENTITY(1,1) NOT NULL,
	[HoTen] [nvarchar](50) NULL,
	[GioiTinh] [bit] NULL,
	[NgaySinh] [date] NULL,
	[SDT] [nvarchar](10) NULL,
	[Email] [nvarchar](50) NULL,
	[CCCD] [nvarchar](12) NULL,
	[TaiKhoan] [nvarchar](20) NULL,
	[MatKhau] [nvarchar](200) NULL,
PRIMARY KEY CLUSTERED 
(
	[MaHoiVien] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[NhanVienLeTan]    Script Date: 05/27/2025 22:43:26 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[NhanVienLeTan](
	[MaNhanVien] [int] IDENTITY(1,1) NOT NULL,
	[HoTen] [nvarchar](50) NULL,
	[GioiTinh] [bit] NULL,
	[NgaySinh] [date] NULL,
	[SDT] [nvarchar](10) NULL,
	[Email] [nvarchar](50) NULL,
	[CCCD] [nvarchar](12) NULL,
	[TaiKhoan] [nvarchar](20) NULL,
	[MatKhau] [nvarchar](200) NULL,
	[TrangThaiLamViec] [bit] NULL,
PRIMARY KEY CLUSTERED 
(
	[MaNhanVien] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[PT]    Script Date: 05/27/2025 22:43:26 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[PT](
	[MaPT] [int] IDENTITY(1,1) NOT NULL,
	[HoTen] [nvarchar](50) NULL,
	[GioiTinh] [bit] NULL,
	[NgaySinh] [date] NULL,
	[SDT] [nvarchar](10) NULL,
	[Email] [nvarchar](50) NULL,
	[CCCD] [nvarchar](12) NULL,
	[BangCap] [nvarchar](200) NULL,
	[KinhNghiem] [int] NULL,
	[TrangThai] [bit] NULL,
	[TaiKhoan] [nvarchar](20) NULL,
	[MatKhau] [nvarchar](200) NULL,
	[TrangThaiLamViec] [bit] NULL,
PRIMARY KEY CLUSTERED 
(
	[MaPT] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET IDENTITY_INSERT [dbo].[CheckIn] ON 

INSERT [dbo].[CheckIn] ([MaCheckIn], [NgayCheckIn], [BuoiCheckIn], [MaHoiVien], [MaNhanVien]) VALUES (35, CAST(N'2025-05-27' AS Date), N'Sáng', 1, 11)
INSERT [dbo].[CheckIn] ([MaCheckIn], [NgayCheckIn], [BuoiCheckIn], [MaHoiVien], [MaNhanVien]) VALUES (36, CAST(N'2025-05-27' AS Date), N'Chiều', 2, 14)
SET IDENTITY_INSERT [dbo].[CheckIn] OFF
INSERT [dbo].[ChuPhongGym] ([TaiKhoan], [MatKhau]) VALUES (N'admin', N'$2a$10$IRoAgfVI2XOFIEi7cY7h9.vYUvJTiXAA2DpXpWFhpGq0FowOvBkLW')
INSERT [dbo].[ChuPhongGym] ([TaiKhoan], [MatKhau]) VALUES (N'trantuan', N'$2a$10$IRoAgfVI2XOFIEi7cY7h9.vYUvJTiXAA2DpXpWFhpGq0FowOvBkLW')
SET IDENTITY_INSERT [dbo].[GoiTap] ON 

INSERT [dbo].[GoiTap] ([MaGoiTap], [TenGoiTap], [SoNgay], [GiaGoiTap], [TrangThaiGoiTap], [MoTaGoiTap]) VALUES (2, N'Gói 1 tháng', 30, 500000, 1, N'Gói tập 1 tháng tại GymError')
INSERT [dbo].[GoiTap] ([MaGoiTap], [TenGoiTap], [SoNgay], [GiaGoiTap], [TrangThaiGoiTap], [MoTaGoiTap]) VALUES (3, N'Gói 15 ngày', 15, 300000, 1, N'Gói tập 15 ngày tại GymError')
INSERT [dbo].[GoiTap] ([MaGoiTap], [TenGoiTap], [SoNgay], [GiaGoiTap], [TrangThaiGoiTap], [MoTaGoiTap]) VALUES (4, N'Gói 1 tuần', 7, 150000, 1, N'Gói tập 7 ngày tại GymError')
INSERT [dbo].[GoiTap] ([MaGoiTap], [TenGoiTap], [SoNgay], [GiaGoiTap], [TrangThaiGoiTap], [MoTaGoiTap]) VALUES (5, N'Gói 3 tháng', 90, 1000000, 1, N'Gói tập 3 tháng tại GymError')
SET IDENTITY_INSERT [dbo].[GoiTap] OFF
INSERT [dbo].[GoiTapHoiVien] ([MaHoiVien], [MaGoiTap], [NgayBatDau], [NgayKetThuc]) VALUES (1, 2, CAST(N'2025-05-27' AS Date), CAST(N'2025-06-26' AS Date))
INSERT [dbo].[GoiTapHoiVien] ([MaHoiVien], [MaGoiTap], [NgayBatDau], [NgayKetThuc]) VALUES (2, 4, CAST(N'2025-05-27' AS Date), CAST(N'2025-06-03' AS Date))
SET IDENTITY_INSERT [dbo].[HoaDon] ON 

INSERT [dbo].[HoaDon] ([MaHoaDon], [TenHoaDon], [NgayThanhToan], [GiaTien], [GhiChu], [MaNhanVien], [MaHoiVien], [MaGoiTap]) VALUES (1, N'khách vãng lai', CAST(N'2025-05-27 11:46:26.443' AS DateTime), 20000, N'Anh Trần Văn Chú', 11, NULL, NULL)
INSERT [dbo].[HoaDon] ([MaHoaDon], [TenHoaDon], [NgayThanhToan], [GiaTien], [GhiChu], [MaNhanVien], [MaHoiVien], [MaGoiTap]) VALUES (2, N'khách vãng lai', CAST(N'2025-05-27 11:51:04.590' AS DateTime), 20000, N'chị Trần Thị C', 11, NULL, NULL)
INSERT [dbo].[HoaDon] ([MaHoaDon], [TenHoaDon], [NgayThanhToan], [GiaTien], [GhiChu], [MaNhanVien], [MaHoiVien], [MaGoiTap]) VALUES (3, N'khách vãng lai', CAST(N'2025-05-27 11:53:39.297' AS DateTime), 20000, N'cHỊ abc', 11, NULL, NULL)
INSERT [dbo].[HoaDon] ([MaHoaDon], [TenHoaDon], [NgayThanhToan], [GiaTien], [GhiChu], [MaNhanVien], [MaHoiVien], [MaGoiTap]) VALUES (4, N'khách vãng lai', CAST(N'2025-05-27 12:50:11.797' AS DateTime), 20000, NULL, 11, NULL, NULL)
INSERT [dbo].[HoaDon] ([MaHoaDon], [TenHoaDon], [NgayThanhToan], [GiaTien], [GhiChu], [MaNhanVien], [MaHoiVien], [MaGoiTap]) VALUES (5, N'Hóa đơn gói tập: Gói 1 tháng', CAST(N'2025-05-27 12:52:34.537' AS DateTime), 500000, NULL, 11, 1, 2)
INSERT [dbo].[HoaDon] ([MaHoaDon], [TenHoaDon], [NgayThanhToan], [GiaTien], [GhiChu], [MaNhanVien], [MaHoiVien], [MaGoiTap]) VALUES (6, N'Hóa đơn gói tập: Gói 1 tuần', CAST(N'2025-05-27 13:54:46.757' AS DateTime), 150000, NULL, 11, 2, 4)
INSERT [dbo].[HoaDon] ([MaHoaDon], [TenHoaDon], [NgayThanhToan], [GiaTien], [GhiChu], [MaNhanVien], [MaHoiVien], [MaGoiTap]) VALUES (7, N'khách vãng lai', CAST(N'2025-05-27 13:55:03.750' AS DateTime), 50000, N'anh ABCDE', 14, NULL, NULL)
INSERT [dbo].[HoaDon] ([MaHoaDon], [TenHoaDon], [NgayThanhToan], [GiaTien], [GhiChu], [MaNhanVien], [MaHoiVien], [MaGoiTap]) VALUES (8, N'khách vãng lai', CAST(N'2025-05-27 14:32:17.943' AS DateTime), 50000, N'Chị Uyên tập 1 buổi', 14, NULL, NULL)
INSERT [dbo].[HoaDon] ([MaHoaDon], [TenHoaDon], [NgayThanhToan], [GiaTien], [GhiChu], [MaNhanVien], [MaHoiVien], [MaGoiTap]) VALUES (9, N'khách vãng lai', CAST(N'2025-05-27 17:51:17.907' AS DateTime), 20000, N'em Khang tập 1 buổi', 11, NULL, NULL)
INSERT [dbo].[HoaDon] ([MaHoaDon], [TenHoaDon], [NgayThanhToan], [GiaTien], [GhiChu], [MaNhanVien], [MaHoiVien], [MaGoiTap]) VALUES (10, N'khách vãng lai', CAST(N'2025-05-27 17:51:50.390' AS DateTime), 20000, N'em Tú ', 14, NULL, NULL)
SET IDENTITY_INSERT [dbo].[HoaDon] OFF
SET IDENTITY_INSERT [dbo].[HoiVien] ON 

INSERT [dbo].[HoiVien] ([MaHoiVien], [HoTen], [GioiTinh], [NgaySinh], [SDT], [Email], [CCCD], [TaiKhoan], [MatKhau]) VALUES (1, N'Nguyễn Trung', 1, CAST(N'2024-01-01' AS Date), N'0123654512', N'nguyentrung@gmail.com', N'046545888542', N'nguyentrung', N'$2a$10$pGSZe/1nmS.TvQfFJUr1GO75iXWp12Pc8dFk93r718jqTXvIOV4xy')
INSERT [dbo].[HoiVien] ([MaHoiVien], [HoTen], [GioiTinh], [NgaySinh], [SDT], [Email], [CCCD], [TaiKhoan], [MatKhau]) VALUES (2, N'Nguyễn Gia Khang', 1, CAST(N'2025-05-24' AS Date), N'0124512451', N'giakhang@gmail.com', N'046524555124', N'giakhang', N'$2a$10$czwpIwg70ICGDkGNtyfHJenWhujCKeK.owBSyhhdB2Fch5NGhiR8W')
INSERT [dbo].[HoiVien] ([MaHoiVien], [HoTen], [GioiTinh], [NgaySinh], [SDT], [Email], [CCCD], [TaiKhoan], [MatKhau]) VALUES (4, N'Bùi Bội Bội', 1, CAST(N'2004-01-01' AS Date), N'012546532', N'boiboi@gmail.com', N'046525444325', N'boiboi', N'$2a$10$haudSTV7BvGbz0eNn21sve6qJRMWRMsHNxVbFolIy9cXwLVaChwxa')
SET IDENTITY_INSERT [dbo].[HoiVien] OFF
SET IDENTITY_INSERT [dbo].[NhanVienLeTan] ON 

INSERT [dbo].[NhanVienLeTan] ([MaNhanVien], [HoTen], [GioiTinh], [NgaySinh], [SDT], [Email], [CCCD], [TaiKhoan], [MatKhau], [TrangThaiLamViec]) VALUES (11, N'Trần Cẩm Uyên', 0, CAST(N'2024-03-03' AS Date), N'0125463251', N'camuyen@gmail.com', N'046325111456', N'camuyen', N'$2a$10$v6Z3eWQEu8fdTrCvbQIe0uQ0iIJb4L.IlQgtAanTCFM8tHIrUHAb6', 1)
INSERT [dbo].[NhanVienLeTan] ([MaNhanVien], [HoTen], [GioiTinh], [NgaySinh], [SDT], [Email], [CCCD], [TaiKhoan], [MatKhau], [TrangThaiLamViec]) VALUES (14, N'Lê Hải Kiều Linh', 0, CAST(N'2004-01-01' AS Date), N'0541265484', N'kieulinh@gmail.com', N'046587999854', N'kieulinh', N'$2a$10$If9tSeuoPVPBGE3y1sAM6eIzBiwfAw.JgpACAyP5vMQh5Co2RsJH6', 1)
INSERT [dbo].[NhanVienLeTan] ([MaNhanVien], [HoTen], [GioiTinh], [NgaySinh], [SDT], [Email], [CCCD], [TaiKhoan], [MatKhau], [TrangThaiLamViec]) VALUES (15, N'Nguyễn Kim Chi', 1, CAST(N'2004-01-01' AS Date), N'0451653321', N'kimchi@gmail.com', N'046254111542', N'kimchi', N'$2a$10$oDX8exFbJIA30GjkjLPLde8LURFjW86H6PFi1AvEss1M6WAdsKd7K', 1)
SET IDENTITY_INSERT [dbo].[NhanVienLeTan] OFF
SET IDENTITY_INSERT [dbo].[PT] ON 

INSERT [dbo].[PT] ([MaPT], [HoTen], [GioiTinh], [NgaySinh], [SDT], [Email], [CCCD], [BangCap], [KinhNghiem], [TrangThai], [TaiKhoan], [MatKhau], [TrangThaiLamViec]) VALUES (1, N'Hồng Trung Việt', 1, CAST(N'2024-02-02' AS Date), N'0123452618', N'trungviet@gmail.com', N'046523666154', N'PT pro', 5, 1, N'trungviet', N'$2a$10$Yq5siVAOgKpaPNWatsyKgu7oDqQELooh09lMgiilLVEQOP2Be.n12', 1)
INSERT [dbo].[PT] ([MaPT], [HoTen], [GioiTinh], [NgaySinh], [SDT], [Email], [CCCD], [BangCap], [KinhNghiem], [TrangThai], [TaiKhoan], [MatKhau], [TrangThaiLamViec]) VALUES (2, N'Trần Đại Thành', 1, CAST(N'2025-05-27' AS Date), N'0451654845', N'daithanh@gmail.com', N'046524555154', N'Không', 2, 1, N'daithanh', N'$2a$10$im7nyBm/yGwKrGRZdLKc2.oyRe8//epCSTo2DkZa03XV/ILaWw3dm', 1)
INSERT [dbo].[PT] ([MaPT], [HoTen], [GioiTinh], [NgaySinh], [SDT], [Email], [CCCD], [BangCap], [KinhNghiem], [TrangThai], [TaiKhoan], [MatKhau], [TrangThaiLamViec]) VALUES (3, N'Lê Quang', 1, CAST(N'2003-01-01' AS Date), N'0154653215', N'lequang@gmail.com', N'046587999532', N'PT Pro, PT Master', 10, 1, N'lequang', N'$2a$10$QFOIJ/oloUW7IvisNnNjlOTxE88u6d7NPk7bK6j5vhqtXo.lZt2Qq', 1)
SET IDENTITY_INSERT [dbo].[PT] OFF
SET ANSI_PADDING ON

GO
/****** Object:  Index [UQ__HoiVien__A955A0AA2F507E5B]    Script Date: 05/27/2025 22:43:26 ******/
ALTER TABLE [dbo].[HoiVien] ADD UNIQUE NONCLUSTERED 
(
	[CCCD] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
GO
SET ANSI_PADDING ON

GO
/****** Object:  Index [UQ__HoiVien__D5B8C7F0D5E0899F]    Script Date: 05/27/2025 22:43:26 ******/
ALTER TABLE [dbo].[HoiVien] ADD UNIQUE NONCLUSTERED 
(
	[TaiKhoan] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
GO
SET ANSI_PADDING ON

GO
/****** Object:  Index [UQ__NhanVien__A955A0AA09E7154E]    Script Date: 05/27/2025 22:43:26 ******/
ALTER TABLE [dbo].[NhanVienLeTan] ADD UNIQUE NONCLUSTERED 
(
	[CCCD] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
GO
SET ANSI_PADDING ON

GO
/****** Object:  Index [UQ__NhanVien__D5B8C7F08F5D04C8]    Script Date: 05/27/2025 22:43:26 ******/
ALTER TABLE [dbo].[NhanVienLeTan] ADD UNIQUE NONCLUSTERED 
(
	[TaiKhoan] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
GO
SET ANSI_PADDING ON

GO
/****** Object:  Index [UQ__PT__A955A0AAD65FDD6D]    Script Date: 05/27/2025 22:43:26 ******/
ALTER TABLE [dbo].[PT] ADD UNIQUE NONCLUSTERED 
(
	[CCCD] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
GO
SET ANSI_PADDING ON

GO
/****** Object:  Index [UQ__PT__D5B8C7F049DF8634]    Script Date: 05/27/2025 22:43:26 ******/
ALTER TABLE [dbo].[PT] ADD UNIQUE NONCLUSTERED 
(
	[TaiKhoan] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
GO
ALTER TABLE [dbo].[BaiTapBuoiTap]  WITH CHECK ADD FOREIGN KEY([MaBaiTap])
REFERENCES [dbo].[BaiTap] ([MaBaiTap])
GO
ALTER TABLE [dbo].[BaiTapBuoiTap]  WITH CHECK ADD FOREIGN KEY([MaBuoiTap])
REFERENCES [dbo].[BuoiTap] ([MaBuoiTap])
GO
ALTER TABLE [dbo].[BuoiTap]  WITH CHECK ADD FOREIGN KEY([MaHoiVien])
REFERENCES [dbo].[HoiVien] ([MaHoiVien])
GO
ALTER TABLE [dbo].[BuoiTap]  WITH CHECK ADD FOREIGN KEY([MaPT])
REFERENCES [dbo].[PT] ([MaPT])
GO
ALTER TABLE [dbo].[CheckIn]  WITH CHECK ADD FOREIGN KEY([MaHoiVien])
REFERENCES [dbo].[HoiVien] ([MaHoiVien])
GO
ALTER TABLE [dbo].[CheckIn]  WITH CHECK ADD FOREIGN KEY([MaNhanVien])
REFERENCES [dbo].[NhanVienLeTan] ([MaNhanVien])
GO
ALTER TABLE [dbo].[GoiTapHoiVien]  WITH CHECK ADD FOREIGN KEY([MaGoiTap])
REFERENCES [dbo].[GoiTap] ([MaGoiTap])
GO
ALTER TABLE [dbo].[GoiTapHoiVien]  WITH CHECK ADD FOREIGN KEY([MaHoiVien])
REFERENCES [dbo].[HoiVien] ([MaHoiVien])
GO
ALTER TABLE [dbo].[HoaDon]  WITH CHECK ADD FOREIGN KEY([MaGoiTap])
REFERENCES [dbo].[GoiTap] ([MaGoiTap])
GO
ALTER TABLE [dbo].[HoaDon]  WITH CHECK ADD FOREIGN KEY([MaHoiVien])
REFERENCES [dbo].[HoiVien] ([MaHoiVien])
GO
ALTER TABLE [dbo].[HoaDon]  WITH CHECK ADD FOREIGN KEY([MaNhanVien])
REFERENCES [dbo].[NhanVienLeTan] ([MaNhanVien])
GO
USE [master]
GO
ALTER DATABASE [GymErrorDB] SET  READ_WRITE 
GO

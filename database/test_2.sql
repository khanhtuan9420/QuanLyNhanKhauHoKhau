-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th2 13, 2023 lúc 10:34 AM
-- Phiên bản máy phục vụ: 10.4.27-MariaDB
-- Phiên bản PHP: 8.2.0

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `test`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `chu_ho`
--

CREATE TABLE `chu_ho` (
  `MaHo` int(11) NOT NULL,
  `IDChuHo` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;

--
-- Đang đổ dữ liệu cho bảng `chu_ho`
--

INSERT INTO `chu_ho` (`MaHo`, `IDChuHo`) VALUES
(1, 1),
(2, 2),
(3, 3),
(4, 5),
(5, 8),
(6, 9);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `ho_khau`
--

CREATE TABLE `ho_khau` (
  `MaHo` int(11) NOT NULL,
  `SoThanhVien` int(11) NOT NULL,
  `DiaChi` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;

--
-- Đang đổ dữ liệu cho bảng `ho_khau`
--

INSERT INTO `ho_khau` (`MaHo`, `SoThanhVien`, `DiaChi`) VALUES
(1, 4, 'Hưng Yên'),
(2, 1, 'Phúc thành'),
(3, 1, 'Kim'),
(4, 1, 'Hà Tĩnh'),
(5, 1, 'Seoul, Korea'),
(6, 1, 'Yên Mỹ, Hưng Yên');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `khoan_thu`
--

CREATE TABLE `khoan_thu` (
  `MaKhoanThu` int(11) NOT NULL,
  `TenKhoanThu` varchar(100) NOT NULL,
  `SoTien` double NOT NULL,
  `LoaiKhoanThu` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;

--
-- Đang đổ dữ liệu cho bảng `khoan_thu`
--

INSERT INTO `khoan_thu` (`MaKhoanThu`, `TenKhoanThu`, `SoTien`, `LoaiKhoanThu`) VALUES
(1, 'tiền thu rác', 10000, 0),
(2, 'nước', 1000.12, 1),
(3, 'Tiền Điện', 200000, 1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `nhan_khau`
--

CREATE TABLE `nhan_khau` (
  `ID` int(11) NOT NULL,
  `CMND` varchar(20) DEFAULT NULL,
  `Ten` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `Ngay_sinh` date NOT NULL,
  `gioi_tinh` varchar(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;

--
-- Đang đổ dữ liệu cho bảng `nhan_khau`
--

INSERT INTO `nhan_khau` (`ID`, `CMND`, `Ten`, `Ngay_sinh`, `gioi_tinh`) VALUES
(1, '123456789', 'Dương Khánh Tuân', '2002-10-07', 'Nam'),
(2, '122330541', 'Trần Ngọc Phiên', '2001-07-22', 'Nam'),
(3, '122440651', 'Nguyễn Văn Thanh', '1976-12-22', 'Nam'),
(4, '123456789', 'Mai Xuân Ngọc', '2002-01-01', 'Nam'),
(5, '123456789', 'Đoàn Sỹ Nguyên', '1997-02-12', 'Nam'),
(6, '3123123123', 'Vũ Hoàng Dũng', '2007-12-12', 'Nam'),
(7, '123123', 'Nguyễn Hà Phong', '2021-12-31', 'Nữ'),
(8, '124234234234', 'Kim Richa', '2019-02-07', 'Nam'),
(9, '4134213', 'Giang Trung Nghĩa', '2016-02-10', 'Nữ');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `nop_tien`
--

CREATE TABLE `nop_tien` (
  `IDNopTien` int(11) NOT NULL,
  `MaKhoanThu` int(11) NOT NULL,
  `NgayThu` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;

--
-- Đang đổ dữ liệu cho bảng `nop_tien`
--

INSERT INTO `nop_tien` (`IDNopTien`, `MaKhoanThu`, `NgayThu`) VALUES
(2, 2, '2020-12-16'),
(3, 1, '2020-12-16'),
(3, 2, '2020-12-16');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `quan_he`
--

CREATE TABLE `quan_he` (
  `MaHo` int(11) NOT NULL,
  `IDThanhVien` int(11) NOT NULL,
  `QuanHe` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;

--
-- Đang đổ dữ liệu cho bảng `quan_he`
--

INSERT INTO `quan_he` (`MaHo`, `IDThanhVien`, `QuanHe`) VALUES
(1, 1, 'Là chủ hộ'),
(1, 4, 'null'),
(1, 6, 'ở tạm trú'),
(1, 7, 'Bạn'),
(2, 2, ''),
(3, 3, 'Là chủ hộ'),
(4, 5, 'Là chủ hộ'),
(5, 8, 'Là chủ hộ'),
(6, 9, 'Là chủ hộ');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `tam_tru`
--

CREATE TABLE `tam_tru` (
  `id` int(11) NOT NULL,
  `chu_ho_id` int(11) NOT NULL,
  `tu_ngay` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Đang đổ dữ liệu cho bảng `tam_tru`
--

INSERT INTO `tam_tru` (`id`, `chu_ho_id`, `tu_ngay`) VALUES
(6, 1, '2023-02-01');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `tam_vang`
--

CREATE TABLE `tam_vang` (
  `id` int(11) NOT NULL,
  `nhan_khau_id` int(11) NOT NULL,
  `diadiem` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_vietnamese_ci NOT NULL,
  `tu_ngay` date NOT NULL,
  `den_ngay` date NOT NULL,
  `ly_do` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_vietnamese_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Đang đổ dữ liệu cho bảng `tam_vang`
--

INSERT INTO `tam_vang` (`id`, `nhan_khau_id`, `diadiem`, `tu_ngay`, `den_ngay`, `ly_do`) VALUES
(1, 3, 'Hưng Yên', '2023-02-05', '2023-02-17', 'fsdafas'),
(2, 7, 'Son La', '2023-02-16', '2023-02-25', 'fasdf');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `users`
--

CREATE TABLE `users` (
  `ID` int(11) NOT NULL,
  `name` varchar(30) NOT NULL,
  `username` varchar(30) NOT NULL,
  `passwd` varchar(30) NOT NULL,
  `role` int(11) NOT NULL DEFAULT 4
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;

--
-- Đang đổ dữ liệu cho bảng `users`
--

INSERT INTO `users` (`ID`, `name`, `username`, `passwd`, `role`) VALUES
(1, 'Dương Khánh Tuân', 'admin', '123456789', 1),
(2, 'Trần Thị Nhã Phương', 'deputy_admin', 'ronaldinho', 2),
(3, 'Shakira', 'accountant', '123456', 3);

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `chu_ho`
--
ALTER TABLE `chu_ho`
  ADD PRIMARY KEY (`MaHo`,`IDChuHo`),
  ADD KEY `chu_ho_2` (`IDChuHo`);

--
-- Chỉ mục cho bảng `ho_khau`
--
ALTER TABLE `ho_khau`
  ADD PRIMARY KEY (`MaHo`);

--
-- Chỉ mục cho bảng `khoan_thu`
--
ALTER TABLE `khoan_thu`
  ADD PRIMARY KEY (`MaKhoanThu`);

--
-- Chỉ mục cho bảng `nhan_khau`
--
ALTER TABLE `nhan_khau`
  ADD PRIMARY KEY (`ID`);

--
-- Chỉ mục cho bảng `nop_tien`
--
ALTER TABLE `nop_tien`
  ADD PRIMARY KEY (`IDNopTien`,`MaKhoanThu`),
  ADD KEY `nop_tien_2` (`MaKhoanThu`);

--
-- Chỉ mục cho bảng `quan_he`
--
ALTER TABLE `quan_he`
  ADD PRIMARY KEY (`MaHo`,`IDThanhVien`),
  ADD KEY `quan_he_2` (`IDThanhVien`);

--
-- Chỉ mục cho bảng `tam_tru`
--
ALTER TABLE `tam_tru`
  ADD PRIMARY KEY (`id`),
  ADD KEY `chu_ho_id` (`chu_ho_id`);

--
-- Chỉ mục cho bảng `tam_vang`
--
ALTER TABLE `tam_vang`
  ADD PRIMARY KEY (`id`),
  ADD KEY `test` (`nhan_khau_id`) USING BTREE;

--
-- Chỉ mục cho bảng `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`ID`);

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `ho_khau`
--
ALTER TABLE `ho_khau`
  MODIFY `MaHo` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT cho bảng `khoan_thu`
--
ALTER TABLE `khoan_thu`
  MODIFY `MaKhoanThu` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT cho bảng `nhan_khau`
--
ALTER TABLE `nhan_khau`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;

--
-- AUTO_INCREMENT cho bảng `users`
--
ALTER TABLE `users`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- Các ràng buộc cho các bảng đã đổ
--

--
-- Các ràng buộc cho bảng `chu_ho`
--
ALTER TABLE `chu_ho`
  ADD CONSTRAINT `chu_ho_1` FOREIGN KEY (`MaHo`) REFERENCES `ho_khau` (`MaHo`),
  ADD CONSTRAINT `chu_ho_2` FOREIGN KEY (`IDChuHo`) REFERENCES `nhan_khau` (`ID`);

--
-- Các ràng buộc cho bảng `nop_tien`
--
ALTER TABLE `nop_tien`
  ADD CONSTRAINT `nop_tien_1` FOREIGN KEY (`IDNopTien`) REFERENCES `nhan_khau` (`ID`),
  ADD CONSTRAINT `nop_tien_2` FOREIGN KEY (`MaKhoanThu`) REFERENCES `khoan_thu` (`MaKhoanThu`);

--
-- Các ràng buộc cho bảng `quan_he`
--
ALTER TABLE `quan_he`
  ADD CONSTRAINT `quan_he_1` FOREIGN KEY (`MaHo`) REFERENCES `ho_khau` (`MaHo`),
  ADD CONSTRAINT `quan_he_2` FOREIGN KEY (`IDThanhVien`) REFERENCES `nhan_khau` (`ID`);

--
-- Các ràng buộc cho bảng `tam_tru`
--
ALTER TABLE `tam_tru`
  ADD CONSTRAINT `tam_tru_ibfk_1` FOREIGN KEY (`id`) REFERENCES `nhan_khau` (`ID`);

--
-- Các ràng buộc cho bảng `tam_vang`
--
ALTER TABLE `tam_vang`
  ADD CONSTRAINT `tam_vang_ibfk_1` FOREIGN KEY (`nhan_khau_id`) REFERENCES `nhan_khau` (`ID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

-- phpMyAdmin SQL Dump
-- version 4.8.5
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 16, 2019 at 12:25 PM
-- Server version: 10.1.38-MariaDB
-- PHP Version: 7.3.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `thietbi`
--

-- --------------------------------------------------------

--
-- Table structure for table `account`
--

CREATE TABLE `account` (
  `user_id` int(11) NOT NULL,
  `user_name` varchar(50) NOT NULL,
  `sdt` varchar(11) NOT NULL,
  `password` varchar(50) NOT NULL,
  `email` varchar(100) DEFAULT NULL,
  `diachi` varchar(500) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `account`
--

INSERT INTO `account` (`user_id`, `user_name`, `sdt`, `password`, `email`, `diachi`) VALUES
(22, 'Lê Hồng Quân', '0326779770', 'c47569c7e5aeb9e13e7fde8ea9f9d9bb', 'quanjoker25998@gmail.com', '48, Man Thiện, Tăng Nhơn Phú A, Quận 9'),
(33, 'Phạm Minh Tuấn', '0944567589', 'd6b8cc42803ea100735c719f1d7f5e11', 'tuan@gmail.com', '101, Lê Văn Việt, Quận 9'),
(37, 'Thuỷ Phạm', '0373865759', '7673529eca14e08ac9e2cfb551ecfdde', 'phamthithuy4444@gmail.com', '97, Man Thiên, Hiep Phu, Quan 9'),
(38, 'Tuyền Huỳnh', '0356789678', '0005cbfae198631b2234df6c0c18ca21', 'tuyen@gmail.com', '3, Phan Đăng Lưu, Q.Phú Nhuận');

-- --------------------------------------------------------

--
-- Table structure for table `chitietdonhang`
--

CREATE TABLE `chitietdonhang` (
  `id` int(11) NOT NULL,
  `madonhang` int(11) NOT NULL,
  `masanpham` int(11) NOT NULL,
  `tensanpham` varchar(10000) NOT NULL,
  `giasanpham` int(11) NOT NULL,
  `soluongsanpham` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `chitietgiohang`
--

CREATE TABLE `chitietgiohang` (
  `id` int(11) NOT NULL,
  `idgiohang` int(11) NOT NULL,
  `idsanpham` int(11) NOT NULL,
  `soluong` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `donhang`
--

CREATE TABLE `donhang` (
  `id` int(11) NOT NULL,
  `idkhachhang` int(11) NOT NULL,
  `sodienthoai` varchar(11) NOT NULL,
  `ngaydat` date NOT NULL,
  `diachigiao` varchar(200) NOT NULL,
  `trangthaidh` tinyint(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `giohang`
--

CREATE TABLE `giohang` (
  `id` int(11) NOT NULL,
  `idkhachhang` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `giohang`
--

INSERT INTO `giohang` (`id`, `idkhachhang`) VALUES
(1, 22),
(2, 33),
(3, 37);

-- --------------------------------------------------------

--
-- Table structure for table `loaisanpham`
--

CREATE TABLE `loaisanpham` (
  `id` int(11) NOT NULL,
  `tenloaisanpham` varchar(200) NOT NULL,
  `hinhanhloaisanpham` varchar(200) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `loaisanpham`
--

INSERT INTO `loaisanpham` (`id`, `tenloaisanpham`, `hinhanhloaisanpham`) VALUES
(1, 'Điện Thoại', 'https://www.materialui.co/materialIcons/hardware/phone_iphone_grey_192x192.png'),
(2, 'Laptop', 'http://www.stickpng.com/assets/thumbs/588a6b2ad06f6719692a2d2a.png');

-- --------------------------------------------------------

--
-- Table structure for table `sanpham`
--

CREATE TABLE `sanpham` (
  `id` int(11) NOT NULL,
  `tensanpham` varchar(200) NOT NULL,
  `giasanpham` int(15) NOT NULL,
  `hinhanhsanpham` varchar(200) NOT NULL,
  `motasanpham` varchar(10000) NOT NULL,
  `idsanpham` int(11) NOT NULL,
  `soluongsanpham` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `sanpham`
--

INSERT INTO `sanpham` (`id`, `tensanpham`, `giasanpham`, `hinhanhsanpham`, `motasanpham`, `idsanpham`, `soluongsanpham`) VALUES
(1, 'Điện Thoại Iphone 6s Plus 32gb', 10990000, 'https://cdn.tgdd.vn/Products/Images/42/87846/iphone-6s-plus-32gb-400x450-400x450.png', '    Màn hình\r\n    Công nghệ màn hìnhLED-backlit IPS LCD\r\n    Độ phân giảiFull HD (1080 x 1920 Pixels)\r\n    Màn hình rộng5.5\"\r\n    Mặt kính cảm ứng\r\n    Kính oleophobic (ion cường lực)\r\n    Camera sau\r\n    Độ phân giải12 MP\r\n    Quay phimQuay phim 4K 2160p@30fps\r\n    Đèn FlashCó\r\n    Chụp ảnh nâng caoTự động lấy nét, Chạm lấy nét, Nhận diện khuôn mặt, HDR, Panorama, Chống rung quang học (OIS)\r\n    Camera trước\r\n    Độ phân giải5 MP\r\n    VideocallCó\r\n    Thông tin khácSelfie ngược sáng HDR, Tự động lấy nét, Tự động cân bằng sáng, Retina Flash, Panorama\r\n    Hệ điều hành - CPU\r\n    Hệ điều hànhiOS 12\r\n    Chipset (hãng SX CPU)Apple A9 2 nhân 64-bit\r\n    Tốc độ CPU1.8 GHz\r\n    Chip đồ họa (GPU)PowerVR GT7600\r\n    Bộ nhớ & Lưu trữ\r\n    RAM2 GB\r\n    Bộ nhớ trong32 GB\r\n    Bộ nhớ còn lại (khả dụng)Khoảng 28 GB\r\n    Thẻ nhớ ngoàiKhông\r\n    Kết nối\r\n    Mạng di động3G, 4G LTE Cat 6\r\n    SIM1 Nano SIM\r\n    WifiWi-Fi 802.11 a/b/g/n/ac, Dual-band, DLNA, Wi-Fi Direct, Wi-Fi hotspot\r\n    GPSA-GPS, GLONASS\r\n    BluetoothA2DP, V4.1\r\n    Cổng kết nối/sạcLightning\r\n    Jack tai nghe3.5 mm\r\n    Kết nối khácAir Play\r\n    Thiết kế & Trọng lượng\r\n    Thiết kếNguyên khối, mặt kính cong 2.5D\r\n    Chất liệuHợp kim nhôm\r\n    Kích thướcDài 158.2 mm - Ngang 77.9 mm - Dày 7.3 mm\r\n    Trọng lượng192 g\r\n    Thông tin pin & Sạc\r\n    Dung lượng pin2750 mAh\r\n    Loại pinPin chuẩn Li-Po\r\n    Công nghệ pin\r\n    Tiết kiệm pin\r\n    Tiện ích\r\n    Bảo mật nâng caoMở khóa bằng vân tay\r\n    Tính năng đặc biệt3D Touch\r\n    Mặt kính 2.5D\r\n    Ghi âmCó\r\n    RadioKhông\r\n    Xem phim3GP, MP4, AVI, WMV, H.263, H.264(MPEG4-AVC), DivX\r\n    Nghe nhạcMP3, WAV, WMA\r\n    Thông tin khác\r\n    Thời điểm ra mắt11/2015', 1, 100),
(2, 'Điện Thoại Iphone 7 32gb', 12990000, 'https://cdn.tgdd.vn/Products/Images/42/74110/iphone-7-32gb-den-400x460.png', '    Màn hình\r\n    Công nghệ màn hìnhLED-backlit IPS LCD\r\n    Độ phân giảiHD (750 x 1334 Pixels)\r\n    Màn hình rộng4.7\"\r\n    Mặt kính cảm ứng\r\n    Kính oleophobic (ion cường lực)\r\n    Camera sau\r\n    Độ phân giải12 MP\r\n    Quay phimQuay phim 4K 2160p@30fps\r\n    Đèn Flash4 đèn LED (2 tông màu)\r\n    Chụp ảnh nâng caoTự động lấy nét, Chạm lấy nét, Nhận diện khuôn mặt, HDR, Panorama, Chống rung quang học (OIS)\r\n    Camera trước\r\n    Độ phân giải7 MP\r\n    VideocallHỗ trợ VideoCall thông qua ứng dụng\r\n    Thông tin khácPanorama, Retina Flash, Nhận diện khuôn mặt, Quay video Full HD, Tự động lấy nét, Selfie ngược sáng HDR\r\n    Hệ điều hành - CPU\r\n    Hệ điều hànhiOS 12\r\n    Chipset (hãng SX CPU)Apple A10 Fusion 4 nhân 64-bit\r\n    Tốc độ CPU2.3 GHz\r\n    Chip đồ họa (GPU)Chip đồ họa 6 nhân\r\n    Bộ nhớ & Lưu trữ\r\n    RAM2 GB\r\n    Bộ nhớ trong32 GB\r\n    Bộ nhớ còn lại (khả dụng)Khoảng 28 GB\r\n    Thẻ nhớ ngoàiKhông\r\n    Kết nối\r\n    Mạng di động3G, 4G LTE Cat 9\r\n    SIM1 Nano SIM\r\n    WifiWi-Fi 802.11 a/b/g/n/ac, Dual-band, Wi-Fi hotspot\r\n    GPSA-GPS, GLONASS\r\n    BluetoothA2DP, LE, v4.2\r\n    Cổng kết nối/sạcLightning\r\n    Jack tai ngheKhông\r\n    Kết nối khácNFC, Air Play, OTG, HDMI\r\n    Thiết kế & Trọng lượng\r\n    Thiết kếNguyên khối, mặt kính cong 2.5D\r\n    Chất liệuHợp kim Nhôm + Magie\r\n    Kích thướcDài 138.3 mm - Ngang 67.1 mm - Dày 7.1 mm\r\n    Trọng lượng138 g\r\n    Thông tin pin & Sạc\r\n    Dung lượng pin1960 mAh\r\n    Loại pinPin chuẩn Li-Ion\r\n    Công nghệ pin\r\n    Tiết kiệm pin\r\n    Tiện ích\r\n    Bảo mật nâng caoMở khóa bằng vân tay\r\n    Tính năng đặc biệt3D Touch\r\n    Ghi âmCó, microphone chuyên dụng chống ồn\r\n    RadioKhông\r\n    Xem phimH.265, MP4, AVI, H.264(MPEG4-AVC), DivX, Xvid\r\n    Nghe nhạcLossless, MP3, WAV, AAC, FLAC\r\n    Thông tin khác\r\n    Thời điểm ra mắt11/2016', 1, 100),
(3, 'Điện Thoại Iphone 7 Plus 32gb', 13990000, 'https://cdn.tgdd.vn/Products/Images/42/78124/iphone-7-plus-gold-400x460.png', '    Màn hình\r\n    Công nghệ màn hìnhLED-backlit IPS LCD\r\n    Độ phân giảiFull HD (1080 x 1920 Pixels)\r\n    Màn hình rộng5.5\"\r\n    Mặt kính cảm ứng\r\n    Kính oleophobic (ion cường lực)\r\n    Camera sau\r\n    Độ phân giải2 camera 12 MP\r\n    Quay phimQuay phim 4K 2160p@30fps\r\n    Đèn Flash4 đèn LED (2 tông màu)\r\n    Chụp ảnh nâng caoTự động lấy nét, Chạm lấy nét, Nhận diện khuôn mặt, HDR, Panorama, Chống rung quang học (OIS)\r\n    Camera trước\r\n    Độ phân giải7 MP\r\n    VideocallHỗ trợ VideoCall thông qua ứng dụng\r\n    Thông tin khácSelfie ngược sáng HDR, Tự động lấy nét, Quay video Full HD, Retina Flash, Nhận diện khuôn mặt\r\n    Hệ điều hành - CPU\r\n    Hệ điều hànhiOS 12\r\n    Chipset (hãng SX CPU)Apple A10 Fusion 4 nhân 64-bit\r\n    Tốc độ CPU2.3 GHz\r\n    Chip đồ họa (GPU)Chip đồ họa 6 nhân\r\n    Bộ nhớ & Lưu trữ\r\n    RAM3 GB\r\n    Bộ nhớ trong32 GB\r\n    Bộ nhớ còn lại (khả dụng)Khoảng 28 GB\r\n    Thẻ nhớ ngoàiKhông\r\n    Kết nối\r\n    Mạng di động3G, 4G LTE Cat 9\r\n    SIM1 Nano SIM\r\n    WifiWi-Fi 802.11 a/b/g/n/ac, Dual-band, Wi-Fi hotspot\r\n    GPSA-GPS, GLONASS\r\n    BluetoothA2DP, LE, v4.2\r\n    Cổng kết nối/sạcLightning\r\n    Jack tai ngheKhông\r\n    Kết nối khácNFC, Air Play, OTG, HDMI\r\n    Thiết kế & Trọng lượng\r\n    Thiết kếNguyên khối, mặt kính cong 2.5D\r\n    Chất liệuHợp kim Nhôm + Magie\r\n    Kích thướcDài 158.2 mm - Ngang 77.9 mm - Dày 7.3 mm\r\n    Trọng lượng188 g\r\n    Thông tin pin & Sạc\r\n    Dung lượng pin2900 mAh\r\n    Loại pinPin chuẩn Li-Ion\r\n    Công nghệ pin\r\n    Tiết kiệm pin\r\n    Tiện ích\r\n    Bảo mật nâng caoMở khóa bằng vân tay\r\n    Tính năng đặc biệt3D Touch\r\n    Ghi âmCó, microphone chuyên dụng chống ồn\r\n    RadioKhông\r\n    Xem phimH.265, 3GP, MP4, AVI, WMV, H.264(MPEG4-AVC), DivX, WMV9, Xvid\r\n    Nghe nhạcMidi, Lossless, MP3, WAV, WMA, AAC, eAAC+\r\n    Thông tin khác\r\n    Thời điểm ra mắt11/2016', 1, 96),
(4, 'Điện Thoại Iphone 8 Plus 64gb', 20990000, 'https://cdn.tgdd.vn/Products/Images/42/114110/iphone-8-plus-64gb-h1-400x460.png', '    Màn hình\r\n    Công nghệ màn hìnhLED-backlit IPS LCD\r\n    Độ phân giảiFull HD (1080 x 1920 Pixels)\r\n    Màn hình rộng5.5\"\r\n    Mặt kính cảm ứng\r\n    Kính oleophobic (ion cường lực)\r\n    Camera sau\r\n    Độ phân giải2 camera 12 MP\r\n    Quay phimQuay phim 4K 2160p@60fps\r\n    Đèn Flash4 đèn LED (2 tông màu)\r\n    Chụp ảnh nâng caoChụp ảnh xóa phông, Lấy nét dự đoán, Tự động lấy nét, Chạm lấy nét, Nhận diện khuôn mặt, HDR, Panorama, Chống rung quang học (OIS)\r\n    Camera trước\r\n    Độ phân giải7 MP\r\n    VideocallCó\r\n    Thông tin khácNhận diện khuôn mặt, Quay video Full HD, Selfie ngược sáng HDR, Camera góc rộng\r\n    Hệ điều hành - CPU\r\n    Hệ điều hànhiOS 12\r\n    Chipset (hãng SX CPU)Apple A11 Bionic 6 nhân\r\n    Tốc độ CPU2 nhân 2.1 GHz Monsoon và 4 nhân 2.1 GHz Mistral\r\n    Chip đồ họa (GPU)Apple GPU 3 nhân\r\n    Bộ nhớ & Lưu trữ\r\n    RAM3 GB\r\n    Bộ nhớ trong64 GB\r\n    Bộ nhớ còn lại (khả dụng)Khoảng 55 GB\r\n    Thẻ nhớ ngoàiKhông\r\n    Kết nối\r\n    Mạng di động3G, 4G LTE Cat 16\r\n    SIM1 Nano SIM\r\n    WifiWi-Fi 802.11 a/b/g/n/ac, Dual-band, Wi-Fi hotspot\r\n    GPSA-GPS, GLONASS\r\n    BluetoothEDR, A2DP, LE, v5.0\r\n    Cổng kết nối/sạcLightning\r\n    Jack tai ngheKhông\r\n    Kết nối khácNFC, OTG\r\n    Thiết kế & Trọng lượng\r\n    Thiết kếNguyên khối\r\n    Chất liệuKhung kim loại + mặt kính cường lực\r\n    Kích thướcDài 158.4 mm - Ngang 78.1 mm - Dày 7.5 mm\r\n    Trọng lượng202 g\r\n    Thông tin pin & Sạc\r\n    Dung lượng pin2691 mAh\r\n    Loại pinPin chuẩn Li-Ion\r\n    Công nghệ pin\r\n    Tiết kiệm pin, Sạc pin nhanh, Sạc pin không dây\r\n    Tiện ích\r\n    Bảo mật nâng caoMở khóa bằng vân tay\r\n    Tính năng đặc biệt3D Touch\r\n    Ghi âmCó, microphone chuyên dụng chống ồn\r\n    RadioKhông\r\n    Xem phimH.265, 3GP, MP4, AVI, WMV, H.263, H.264(MPEG4-AVC)\r\n    Nghe nhạcLossless, Midi, MP3, WAV, WMA, WMA9, AAC, AAC+, AAC++, eAAC+\r\n    Thông tin khác\r\n    Thời điểm ra mắt09/2017', 1, 95),
(6, 'Điện Thoại Iphone X 64gb', 22990000, 'https://cdn.tgdd.vn/Products/Images/42/114115/iphone-x-64gb-1-400x460.png', '    Màn hình\r\n    Công nghệ màn hìnhOLED\r\n    Độ phân giải1125 x 2436 Pixels\r\n    Màn hình rộng5.8\"\r\n    Mặt kính cảm ứng\r\n    Kính oleophobic (ion cường lực)\r\n    Camera sau\r\n    Độ phân giải2 camera 12 MP\r\n    Quay phimQuay phim 4K 2160p@60fps\r\n    Đèn Flash4 đèn LED (2 tông màu)\r\n    Chụp ảnh nâng caoLấy nét dự đoán, Chụp ảnh xóa phông, Tự động lấy nét, Chạm lấy nét, Nhận diện khuôn mặt, HDR, Panorama, Chống rung quang học (OIS)\r\n    Camera trước\r\n    Độ phân giải7 MP\r\n    VideocallCó\r\n    Thông tin khácNhận diện khuôn mặt, Quay video Full HD, Camera góc rộng, Selfie ngược sáng HDR\r\n    Hệ điều hành - CPU\r\n    Hệ điều hànhiOS 12\r\n    Chipset (hãng SX CPU)Apple A11 Bionic 6 nhân\r\n    Tốc độ CPU2.39 GHz\r\n    Chip đồ họa (GPU)Apple GPU 3 nhân\r\n    Bộ nhớ & Lưu trữ\r\n    RAM3 GB\r\n    Bộ nhớ trong64 GB\r\n    Bộ nhớ còn lại (khả dụng)Khoảng 55 GB\r\n    Thẻ nhớ ngoàiKhông\r\n    Kết nối\r\n    Mạng di động3G, 4G LTE Cat 16\r\n    SIM1 Nano SIM\r\n    WifiWi-Fi 802.11 a/b/g/n/ac, Dual-band, Wi-Fi hotspot\r\n    GPSA-GPS, GLONASS\r\n    BluetoothEDR, LE, A2DP, v5.0\r\n    Cổng kết nối/sạcLightning\r\n    Jack tai ngheKhông\r\n    Kết nối khácNFC, OTG\r\n    Thiết kế & Trọng lượng\r\n    Thiết kếNguyên khối\r\n    Chất liệuKhung kim loại + mặt kính cường lực\r\n    Kích thướcDài 143.6 mm - Ngang 70.9 mm - Dày 7.7 mm\r\n    Trọng lượng174 g\r\n    Thông tin pin & Sạc\r\n    Dung lượng pin2716 mAh\r\n    Loại pinPin chuẩn Li-Ion\r\n    Công nghệ pin\r\n    Tiết kiệm pin, Sạc pin nhanh, Sạc pin không dây\r\n    Tiện ích\r\n    Bảo mật nâng caoNhận diện khuôn mặt Face ID\r\n    Tính năng đặc biệt3D Touch\r\n    Ghi âmCó, microphone chuyên dụng chống ồn\r\n    RadioKhông\r\n    Xem phimH.265, 3GP, MP4, AVI, WMV, H.263, H.264(MPEG4-AVC)\r\n    Nghe nhạcMidi, Lossless, MP3, WAV, WMA, WMA9, AAC, AAC+, AAC++, eAAC+\r\n    Thông tin khác\r\n    Thời điểm ra mắt09/2017', 1, 46),
(7, 'Điện Thoại Iphone Xr 128gb', 22990000, 'https://cdn.tgdd.vn/Products/Images/42/191483/iphone-xr-128gb-red-400x460.png', '    Màn hình\r\n    Công nghệ màn hìnhIPS LCD\r\n    Độ phân giải828 x 1792 Pixels\r\n    Màn hình rộng6.1\"\r\n    Mặt kính cảm ứng\r\n    Kính oleophobic (ion cường lực)\r\n    Camera sau\r\n    Độ phân giải12 MP\r\n    Quay phimQuay phim FullHD 1080p@30fps, Quay phim FullHD 1080p@60fps, Quay phim FullHD 1080p@120fps, Quay phim FullHD 1080p@240fps, Quay phim 4K 2160p@24fps, Quay phim 4K 2160p@30fps, Quay phim 4K 2160p@60fps\r\n    Đèn Flash4 đèn LED (2 tông màu)\r\n    Chụp ảnh nâng caoLấy nét dự đoán, Chụp ảnh xóa phông, Chế độ Slow Motion, Chế độ chụp ban đêm (ánh sáng yếu), A.I Camera, Điều chỉnh khẩu độ, Tự động lấy nét, Chạm lấy nét, Nhận diện khuôn mặt, HDR, Panorama, Chống rung quang học (OIS)\r\n    Camera trước\r\n    Độ phân giải7 MP\r\n    VideocallCó\r\n    Thông tin khácSelfie ngược sáng HDR, Camera góc rộng, Nhận diện khuôn mặt, Quay video Full HD\r\n    Hệ điều hành - CPU\r\n    Hệ điều hànhiOS 12\r\n    Chipset (hãng SX CPU)Apple A12 Bionic 6 nhân\r\n    Tốc độ CPU2 nhân 2.5 GHz Vortex & 4 nhân 1.6 GHz Tempest\r\n    Chip đồ họa (GPU)Apple GPU 4 nhân\r\n    Bộ nhớ & Lưu trữ\r\n    RAM3 GB\r\n    Bộ nhớ trong128 GB\r\n    Bộ nhớ còn lại (khả dụng)Khoảng 121 GB\r\n    Thẻ nhớ ngoàiKhông\r\n    Kết nối\r\n    Mạng di độngHỗ trợ 4G\r\n    SIMNano SIM & eSIM\r\n    WifiWi-Fi 802.11 a/b/g/n/ac, Dual-band, Wi-Fi hotspot\r\n    GPSA-GPS, GLONASS\r\n    BluetoothLE, A2DP, v5.0\r\n    Cổng kết nối/sạcLightning\r\n    Jack tai ngheKhông\r\n    Kết nối khácNFC, OTG\r\n    Thiết kế & Trọng lượng\r\n    Thiết kếNguyên khối\r\n    Chất liệuKhung nhôm + mặt kính cường lực\r\n    Kích thướcDài 150.9 mm - Ngang 75.7 mm - Dày 8.3 mm\r\n    Trọng lượng194 g\r\n    Thông tin pin & Sạc\r\n    Dung lượng pin2942 mAh\r\n    Loại pinPin chuẩn Li-Ion\r\n    Công nghệ pin\r\n    Tiết kiệm pin, Sạc pin nhanh, Sạc pin không dây\r\n    Tiện ích\r\n    Bảo mật nâng caoNhận diện khuôn mặt Face ID\r\n    Tính năng đặc biệtChuẩn Kháng nước, Chuẩn kháng bụi\r\n    Sạc pin nhanh\r\n    Apple Pay\r\n    Ghi âmCó, microphone chuyên dụng chống ồn\r\n    RadioKhông\r\n    Xem phimH.265, 3GP, MP4, AVI, WMV, H.263, H.264(MPEG4-AVC)\r\n    Nghe nhạcLossless, Midi, MP3, WAV, WMA9, WMA, AAC, AAC+, AAC++, eAAC+\r\n    Thông tin khác\r\n    Thời điểm ra mắt11/2018', 1, 93),
(8, 'Điện Thoại Iphone 7 Plus 256gb', 23990000, 'https://cdn.tgdd.vn/Products/Images/42/87840/iphone-7-plus-256gb-jet-black-3-400x460-400x460.png', '    Màn hình\r\n    Công nghệ màn hìnhLED-backlit IPS LCD\r\n    Độ phân giảiFull HD (1080 x 1920 Pixels)\r\n    Màn hình rộng5.5\"\r\n    Mặt kính cảm ứng\r\n    Kính oleophobic (ion cường lực)\r\n    Camera sau\r\n    Độ phân giải2 camera 12 MP\r\n    Quay phimQuay phim 4K 2160p@30fps\r\n    Đèn Flash4 đèn LED (2 tông màu)\r\n    Chụp ảnh nâng caoTự động lấy nét, Chạm lấy nét, Nhận diện khuôn mặt, HDR, Panorama, Chống rung quang học (OIS)\r\n    Camera trước\r\n    Độ phân giải7 MP\r\n    VideocallHỗ trợ VideoCall thông qua ứng dụng\r\n    Thông tin khácNhận diện khuôn mặt, Quay video Full HD, Tự động lấy nét, Selfie ngược sáng HDR\r\n    Hệ điều hành - CPU\r\n    Hệ điều hànhiOS 12\r\n    Chipset (hãng SX CPU)Apple A10 Fusion 4 nhân 64-bit\r\n    Tốc độ CPU2.3 GHz\r\n    Chip đồ họa (GPU)Chip đồ họa 6 nhân\r\n    Bộ nhớ & Lưu trữ\r\n    RAM3 GB\r\n    Bộ nhớ trong256 GB\r\n    Bộ nhớ còn lại (khả dụng)Khoảng 247.52 GB\r\n    Thẻ nhớ ngoàiKhông\r\n    Kết nối\r\n    Mạng di động3G, 4G LTE Cat 9\r\n    SIM1 Nano SIM\r\n    WifiWi-Fi 802.11 a/b/g/n/ac, Dual-band, Wi-Fi hotspot\r\n    GPSA-GPS, GLONASS\r\n    BluetoothA2DP, LE, v4.2\r\n    Cổng kết nối/sạcLightning\r\n    Jack tai ngheKhông\r\n    Kết nối khácNFC, Air Play, OTG, HDMI\r\n    Thiết kế & Trọng lượng\r\n    Thiết kếNguyên khối, mặt kính cong 2.5D\r\n    Chất liệuHợp kim Nhôm + Magie\r\n    Kích thướcDài 158.2 mm - Ngang 77.9 mm - Dày 7.3 mm\r\n    Trọng lượng188 g\r\n    Thông tin pin & Sạc\r\n    Dung lượng pin2900 mAh\r\n    Loại pinPin chuẩn Li-Ion\r\n    Công nghệ pin\r\n    Tiết kiệm pin\r\n    Tiện ích\r\n    Bảo mật nâng caoMở khóa bằng vân tay\r\n    Tính năng đặc biệtChuẩn Kháng nước, Chuẩn kháng bụi\r\n    3D Touch\r\n    Ghi âmCó, microphone chuyên dụng chống ồn\r\n    RadioKhông\r\n    Xem phimH.265, 3GP, MP4, AVI, WMV, H.264(MPEG4-AVC), DivX, WMV9, Xvid\r\n    Nghe nhạcMidi, Lossless, MP3, WAV, WMA, eAAC+, OGG, AC3, FLAC\r\n    Thông tin khác\r\n    Thời điểm ra mắt11/2016', 1, 98),
(9, 'Điện Thoại Iphone Xr 256gb', 24990000, 'https://cdn.tgdd.vn/Products/Images/42/190326/iphone-xr-256gb-white-400x460.png', '    Màn hình\r\n    Công nghệ màn hìnhIPS LCD\r\n    Độ phân giải828 x 1792 Pixels\r\n    Màn hình rộng6.1\"\r\n    Mặt kính cảm ứng\r\n    Kính oleophobic (ion cường lực)\r\n    Camera sau\r\n    Độ phân giải12 MP\r\n    Quay phimQuay phim FullHD 1080p@30fps, Quay phim FullHD 1080p@60fps, Quay phim FullHD 1080p@120fps, Quay phim FullHD 1080p@240fps, Quay phim 4K 2160p@24fps, Quay phim 4K 2160p@30fps, Quay phim 4K 2160p@60fps\r\n    Đèn Flash4 đèn LED (2 tông màu)\r\n    Chụp ảnh nâng caoChụp ảnh xóa phông, Chế độ Slow Motion, Chế độ chụp ban đêm (ánh sáng yếu), A.I Camera, Điều chỉnh khẩu độ, Tự động lấy nét, Chạm lấy nét, Nhận diện khuôn mặt, HDR, Panorama, Chống rung quang học (OIS)\r\n    Camera trước\r\n    Độ phân giải7 MP\r\n    VideocallCó\r\n    Thông tin khácSelfie ngược sáng HDR, Camera góc rộng, Nhận diện khuôn mặt, Quay video Full HD\r\n    Hệ điều hành - CPU\r\n    Hệ điều hànhiOS 12\r\n    Chipset (hãng SX CPU)Apple A12 Bionic 6 nhân\r\n    Tốc độ CPU2 nhân 2.5 GHz Vortex & 4 nhân 1.6 GHz Tempest\r\n    Chip đồ họa (GPU)Apple GPU 4 nhân\r\n    Bộ nhớ & Lưu trữ\r\n    RAM3 GB\r\n    Bộ nhớ trong256 GB\r\n    Bộ nhớ còn lại (khả dụng)Khoảng 249 GB\r\n    Thẻ nhớ ngoàiKhông\r\n    Kết nối\r\n    Mạng di độngHỗ trợ 4G\r\n    SIMNano SIM & eSIM\r\n    WifiWi-Fi 802.11 a/b/g/n/ac, Dual-band, Wi-Fi hotspot\r\n    GPSA-GPS, GLONASS\r\n    BluetoothA2DP, LE, v5.0\r\n    Cổng kết nối/sạcLightning\r\n    Jack tai ngheKhông\r\n    Kết nối khácNFC, OTG\r\n    Thiết kế & Trọng lượng\r\n    Thiết kếNguyên khối\r\n    Chất liệuKhung nhôm + mặt kính cường lực\r\n    Kích thướcDài 150.9 mm - Ngang 75.7 mm - Dày 8.3 mm\r\n    Trọng lượng194 g\r\n    Thông tin pin & Sạc\r\n    Dung lượng pin2942 mAh\r\n    Loại pinPin chuẩn Li-Ion\r\n    Công nghệ pin\r\n    Tiết kiệm pin, Sạc pin nhanh, Sạc pin không dây\r\n    Tiện ích\r\n    Bảo mật nâng caoNhận diện khuôn mặt Face ID\r\n    Tính năng đặc biệtChuẩn Kháng nước, Chuẩn kháng bụi\r\n    Sạc pin nhanh\r\n    Apple Pay\r\n    Ghi âmCó, microphone chuyên dụng chống ồn\r\n    RadioKhông\r\n    Xem phimH.265, 3GP, MP4, AVI, WMV, H.263, H.264(MPEG4-AVC)\r\n    Nghe nhạcLossless, Midi, MP3, WAV, WMA, WMA9, AAC, AAC+, AAC++, eAAC+\r\n    Thông tin khác\r\n    Thời điểm ra mắt11/2018', 1, 95),
(10, 'Laptop Dell Inspiron 3576', 14690000, 'https://cdn.tgdd.vn/Products/Images/44/166521/dell-inspiron-3576-p63f002n76f-450-600x600.png', '    Bộ xử lý\r\n    Công nghệ CPUIntel Core i5 Kabylake Refresh\r\n    Loại CPU\r\n    8250U\r\n    Tốc độ CPU1.60 GHz\r\n    Tốc độ tối đaTurbo Boost 3.4 GHz\r\n    Tốc độ Bus2400 MHz\r\n    Bộ nhớ, RAM, Ổ cứng\r\n    RAM4 GB\r\n    Loại RAMDDR4 (2 khe)\r\n    Tốc độ Bus RAM2400 MHz\r\n    Hỗ trợ RAM tối đa\r\n    16 GB\r\n    Ổ cứng\r\n    HDD: 1 TB\r\n    Màn hình\r\n    Kích thước màn hình15.6 inch\r\n    Độ phân giải\r\n    Full HD (1920 x 1080)\r\n    Công nghệ màn hìnhTrueLife LED-Backlit Display\r\n    Màn hình cảm ứng\r\n    Không\r\n    Đồ họa và Âm thanh\r\n    Thiết kế cardCard đồ họa tích hợp\r\n    Card đồ họa\r\n    Intel® UHD Graphics 620\r\n    Công nghệ âm thanh\r\n    Waves MaxxAudio, Combo Microphone & Headphone, Loa kép (2 kênh)\r\n    Cổng kết nối & tính năng mở rộng\r\n    Cổng giao tiếpHDMI 1.4, 2 x USB 3.0, LAN (RJ45), USB 2.0\r\n    Kết nối không dâyBluetooth 4.1, Wi-Fi 802.11 a/b/g/n/ac\r\n    Khe đọc thẻ nhớ\r\n    SD\r\n    Ổ đĩa quang\r\n    Có (đọc, ghi dữ liệu)\r\n    Webcam\r\n    HD webcam\r\n    Đèn bàn phím\r\n    Không\r\n    Tính năng khác\r\n    Multi TouchPad\r\n    PIN\r\n    Loại PINPIN rời\r\n    Thông tin Pin\r\n    Li-Ion 4 cell\r\n    Hệ điều hành\r\n    Hệ điều hànhWindows 10 Home SL\r\n    Kích thước & trọng lượng\r\n    Kích thướcDài 380 mm - Rộng 260.3 mm - Dày 23.65 mm\r\n    Trọng lượng\r\n    2.3 Kg\r\n    Chất liệuVỏ nhựa', 2, 99),
(11, 'Laptop Apple Macbook Air(2018)', 31990000, 'https://cdn.tgdd.vn/Products/Images/44/197305/apple-macbook-air-mree2sa-a-i5-8gb-128gb-133-gold-600x600.jpg', '    Bộ xử lý\r\n    Công nghệ CPUIntel Core i5 Coffee Lake\r\n    Loại CPU\r\n    Hãng không công bố\r\n    Tốc độ CPU1.60 GHz\r\n    Tốc độ tối đaTurbo Boost 3.6 GHz\r\n    Tốc độ Bus2133 MHz\r\n    Bộ nhớ, RAM, Ổ cứng\r\n    RAM8 GB\r\n    Loại RAMDDR3\r\n    Tốc độ Bus RAM2133 MHz\r\n    Hỗ trợ RAM tối đa\r\n    Không hỗ trợ nâng cấp\r\n    Ổ cứng\r\n    SSD: 128 GB\r\n    Màn hình\r\n    Kích thước màn hình13.3 inch\r\n    Độ phân giải\r\n    Retina (2560 x 1600)\r\n    Công nghệ màn hìnhCông nghệ IPS, LED Backlit\r\n    Màn hình cảm ứng\r\n    Không\r\n    Đồ họa và Âm thanh\r\n    Thiết kế cardCard đồ họa tích hợp\r\n    Card đồ họa\r\n    Intel UHD Graphics 617\r\n    Công nghệ âm thanh\r\n    3 microphones, Headphones, Loa kép (2 kênh)\r\n    Cổng kết nối & tính năng mở rộng\r\n    Cổng giao tiếp2 x Thunderbolt 3 (USB-C)\r\n    Kết nối không dâyBluetooth 4.2, Wi-Fi 802.11 a/b/g/n/ac\r\n    Khe đọc thẻ nhớ\r\n    Không\r\n    Ổ đĩa quang\r\n    Không\r\n    Webcam\r\n    FaceTime Camera\r\n    Đèn bàn phím\r\n    Có\r\n    Tính năng khác\r\n    Hỗ trợ eGPU rời, Bảo mật vân tay\r\n    PIN\r\n    Loại PINPIN liền\r\n    Thông tin Pin\r\n    Khoảng 10 tiếng\r\n    Hệ điều hành\r\n    Hệ điều hànhMac OS\r\n    Kích thước & trọng lượng\r\n    Kích thướcDài 304.1 mm - Rộng 212.1 mm - Dày 4.1 đến 15.6 mm\r\n    Trọng lượng\r\n    1.25 kg\r\n    Chất liệuVỏ kim loại nguyên khối', 2, 100),
(12, 'Laptop Asus Vivobook X507uf', 14590000, 'https://cdn.tgdd.vn/Products/Images/44/193516/asus-x507uf-i5-8250u-4gb-1tb-2gb-mx130-ej121t-thumb-1-600x600.jpg', '    Bộ xử lý\r\n    Công nghệ CPUIntel Core i5 Kabylake Refresh\r\n    Loại CPU\r\n    8250U\r\n    Tốc độ CPU1.60 GHz\r\n    Tốc độ tối đaTurbo Boost 3.4 GHz\r\n    Tốc độ Bus\r\n    Bộ nhớ, RAM, Ổ cứng\r\n    RAM4 GB\r\n    Loại RAMDDR4 (2 khe)\r\n    Tốc độ Bus RAM2400 MHz\r\n    Hỗ trợ RAM tối đa\r\n    16 GB\r\n    Ổ cứng\r\n    HDD: 1 TB SATA3, Hỗ trợ khe cắm SSD M.2 SATA3\r\n    Màn hình\r\n    Kích thước màn hình15.6 inch\r\n    Độ phân giải\r\n    Full HD (1920 x 1080)\r\n    Công nghệ màn hìnhLED Backlight - AntiGlare\r\n    Màn hình cảm ứng\r\n    Không\r\n    Đồ họa và Âm thanh\r\n    Thiết kế cardCard đồ họa rời\r\n    Card đồ họa\r\n    NVIDIA Geforce MX130, 2GB\r\n    Công nghệ âm thanh\r\n    Realtek High Definition Audio\r\n    Cổng kết nối & tính năng mở rộng\r\n    Cổng giao tiếp2 x USB 2.0, HDMI, USB 3.0\r\n    Kết nối không dâyBluetooth v4.0\r\n    Khe đọc thẻ nhớ\r\n    Micro SD\r\n    Ổ đĩa quang\r\n    Không\r\n    Webcam\r\n    VGA Webcam\r\n    Đèn bàn phím\r\n    Không\r\n    Tính năng khác\r\n    Bảo mật vân tay\r\n    PIN\r\n    Loại PINPIN liền\r\n    Thông tin Pin\r\n    Li-Ion 3 cell\r\n    Hệ điều hành\r\n    Hệ điều hànhWindows 10 Home SL\r\n    Kích thước & trọng lượng\r\n    Kích thướcDài 365mm - Rộng 266mm - Dày 21.9mm\r\n    Trọng lượng\r\n    1.8 kg\r\n    Chất liệuVỏ nhựa', 2, 0),
(13, 'Laptop Acer Aspire E5 576 34ND', 10790000, 'https://cdn.tgdd.vn/Products/Images/44/184747/acer-aspire-e5-576-34nd-nxgrysv004-600x600.jpg', '    Bộ xử lý\r\n    Công nghệ CPUIntel Core i3 Kabylake Refresh\r\n    Loại CPU\r\n    8130U\r\n    Tốc độ CPU2.20 GHz\r\n    Tốc độ tối đaTurbo Boost 3.4 GHz\r\n    Tốc độ Bus1600 MHz\r\n    Bộ nhớ, RAM, Ổ cứng\r\n    RAM4 GB\r\n    Loại RAMDDR3L (2 khe RAM)\r\n    Tốc độ Bus RAM1600 MHz\r\n    Hỗ trợ RAM tối đa\r\n    16 GB\r\n    Ổ cứng\r\n    HDD: 1 TB, Hỗ trợ khe cắm SSD M.2 PCIe\r\n    Màn hình\r\n    Kích thước màn hình15.6 inch\r\n    Độ phân giải\r\n    Full HD (1920 x 1080)\r\n    Công nghệ màn hìnhACER CineCrystal LED Backlit\r\n    Màn hình cảm ứng\r\n    Không\r\n    Đồ họa và Âm thanh\r\n    Thiết kế cardCard đồ họa tích hợp\r\n    Card đồ họa\r\n    Intel® UHD Graphics 620\r\n    Công nghệ âm thanh\r\n    Loa kép (2 kênh), Combo Microphone & Headphone, Acer TrueHarmony\r\n    Cổng kết nối & tính năng mở rộng\r\n    Cổng giao tiếp2 x USB 3.0, HDMI, LAN (RJ45), USB 2.0, USB Type-C, VGA (D-Sub)\r\n    Kết nối không dâyBluetooth v4.0, Wi-Fi 802.11 a/b/g/n/ac\r\n    Khe đọc thẻ nhớ\r\n    SD, SDHC, SDXC\r\n    Ổ đĩa quang\r\n    Không\r\n    Webcam\r\n    1 MP, HD webcam\r\n    Đèn bàn phím\r\n    Không\r\n    Tính năng khác\r\n    Multi TouchPad\r\n    PIN\r\n    Loại PINPIN liền\r\n    Thông tin Pin\r\n    Li-Ion 4 cell\r\n    Hệ điều hành\r\n    Hệ điều hànhWindows 10 Home SL\r\n    Kích thước & trọng lượng\r\n    Kích thướcDài 381.6 mm - Rộng 259 mm - Dày 30.2 mm\r\n    Trọng lượng\r\n    2.23 kg\r\n    Chất liệuVỏ nhựa', 2, 98),
(14, 'Laptop HP Envy 13 ah1011TU', 22490000, 'https://cdn.tgdd.vn/Products/Images/44/196906/hp-envy-13-ah1011tu-i5-8265u-8gb-256gb-win10-5hz2-33397-thumb-600x600.jpg', '    Bộ xử lý\r\n    Công nghệ CPUIntel Core i5 Coffee Lake\r\n    Loại CPU\r\n    8265U\r\n    Tốc độ CPU1.60 GHz\r\n    Tốc độ tối đaTurbo Boost 3.9 GHz\r\n    Tốc độ Bus2133 MHz\r\n    Bộ nhớ, RAM, Ổ cứng\r\n    RAM8 GB\r\n    Loại RAMDDR3L\r\n    Tốc độ Bus RAM2133 MHz\r\n    Hỗ trợ RAM tối đa\r\n    Không hỗ trợ nâng cấp\r\n    Ổ cứng\r\n    SSD 256GB NVMe PCIe\r\n    Màn hình\r\n    Kích thước màn hình13.3 inch\r\n    Độ phân giải\r\n    Full HD (1920 x 1080)\r\n    Công nghệ màn hìnhTấm nền IPS, Viền màn hình mỏng, LED Backlit\r\n    Màn hình cảm ứng\r\n    Không\r\n    Đồ họa và Âm thanh\r\n    Thiết kế cardCard đồ họa tích hợp\r\n    Card đồ họa\r\n    Intel® HD Graphics 620\r\n    Công nghệ âm thanh\r\n    Bang & Olufsen audio\r\n    Cổng kết nối & tính năng mở rộng\r\n    Cổng giao tiếp2 x USB 3.1, USB Type-C\r\n    Kết nối không dâyBluetooth 4.2, Wi-Fi 802.11 a/b/g/n\r\n    Khe đọc thẻ nhớ\r\n    Micro SD\r\n    Ổ đĩa quang\r\n    Không\r\n    Webcam\r\n    1 MP\r\n    Đèn bàn phím\r\n    Có\r\n    Tính năng khác\r\n    Không\r\n    PIN\r\n    Loại PINPIN liền\r\n    Thông tin Pin\r\n    Li-Ion 4 cell\r\n    Hệ điều hành\r\n    Hệ điều hànhWindows 10 Home SL\r\n    Kích thước & trọng lượng\r\n    Kích thướcDài 308 mm - Rộng 213 mm - Dày 15 mm\r\n    Trọng lượng\r\n    1.2 kg\r\n    Chất liệuVỏ kim loại nguyên khối', 2, 100),
(15, 'Laptop Dell Vostro 3578', 20990000, 'https://cdn.tgdd.vn/Products/Images/44/166602/dell-vostro-3578-ngmpf11-450x300-600x600-600x600.jpg', '    Bộ xử lý\r\n    Công nghệ CPUIntel Core i7 Kabylake Refresh\r\n    Loại CPU\r\n    8550U\r\n    Tốc độ CPU1.80 GHz\r\n    Tốc độ tối đaTurbo Boost 4.0 GHz\r\n    Tốc độ Bus2400 MHz\r\n    Bộ nhớ, RAM, Ổ cứng\r\n    RAM8 GB\r\n    Loại RAMDDR4 (2 khe)\r\n    Tốc độ Bus RAM2400 MHz\r\n    Hỗ trợ RAM tối đa\r\n    16 GB\r\n    Ổ cứng\r\n    HDD: 1 TB\r\n    Màn hình\r\n    Kích thước màn hình15.6 inch\r\n    Độ phân giải\r\n    Full HD (1920 x 1080)\r\n    Công nghệ màn hìnhMàn hình chống chói, TrueLife LED-Backlit Display\r\n    Màn hình cảm ứng\r\n    Không\r\n    Đồ họa và Âm thanh\r\n    Thiết kế cardCard đồ họa rời\r\n    Card đồ họa\r\n    AMD Radeon 520, 2GB\r\n    Công nghệ âm thanh\r\n    Combo Microphone & Headphone, Waves MaxxAudio, Loa kép (2 kênh)\r\n    Cổng kết nối & tính năng mở rộng\r\n    Cổng giao tiếpHDMI 1.4, 2 x USB 3.0, LAN (RJ45), USB 2.0, VGA (D-Sub)\r\n    Kết nối không dâyBluetooth 4.1, Wi-Fi 802.11 a/b/g/n/ac\r\n    Khe đọc thẻ nhớ\r\n    SD, SDHC, SDXC\r\n    Ổ đĩa quang\r\n    Có (đọc, ghi dữ liệu)\r\n    Webcam\r\n    0.3 MP, VGA Webcam\r\n    Đèn bàn phím\r\n    Không\r\n    Tính năng khác\r\n    Bảo mật vân tay, Multi TouchPad\r\n    PIN\r\n    Loại PINPIN rời\r\n    Thông tin Pin\r\n    Li-Ion 4 cell\r\n    Hệ điều hành\r\n    Hệ điều hànhWindows 10 Home SL\r\n    Kích thước & trọng lượng\r\n    Kích thướcDài 380 mm - Rộng 240 mm - Dày 25.44 mm\r\n    Trọng lượng\r\n    2.2 kg\r\n    Chất liệuVỏ nhựa', 2, 9),
(16, 'Laptop Lenovo IdeaPad 330 14IKBR', 10290000, 'https://cdn.tgdd.vn/Products/Images/44/185314/lenovo-ideapad-330-14ikb-81g2001avn-33397-thumb-600x600.jpg', '    Bộ xử lý\r\n    Công nghệ CPUIntel Core i3 Kabylake\r\n    Loại CPU\r\n    7020U\r\n    Tốc độ CPU2.30 GHz\r\n    Tốc độ tối đaKhông\r\n    Tốc độ Bus2133 MHz\r\n    Bộ nhớ, RAM, Ổ cứng\r\n    RAM4 GB\r\n    Loại RAMDDR4 (On board +1 khe)\r\n    Tốc độ Bus RAM2133 MHz\r\n    Hỗ trợ RAM tối đa\r\n    8 GB\r\n    Ổ cứng\r\n    SSD: 128 GB\r\n    Màn hình\r\n    Kích thước màn hình14 inch\r\n    Độ phân giải\r\n    Full HD (1920 x 1080)\r\n    Công nghệ màn hìnhLED Backlit\r\n    Màn hình cảm ứng\r\n    Không\r\n    Đồ họa và Âm thanh\r\n    Thiết kế cardCard đồ họa tích hợp\r\n    Card đồ họa\r\n    Intel® HD Graphics 620\r\n    Công nghệ âm thanh\r\n    Combo Microphone & Headphone, Dolby Home Theater, Loa kép (2 kênh)\r\n    Cổng kết nối & tính năng mở rộng\r\n    Cổng giao tiếp2 x USB 2.0, HDMI, LAN (RJ45), USB Type-C\r\n    Kết nối không dâyBluetooth 4.1, Wi-Fi 802.11 a/b/g/n/ac\r\n    Khe đọc thẻ nhớ\r\n    SD, SDHC, SDXC\r\n    Ổ đĩa quang\r\n    Không\r\n    Webcam\r\n    VGA Webcam\r\n    Đèn bàn phím\r\n    Không\r\n    Tính năng khác\r\n    Multi TouchPad\r\n    PIN\r\n    Loại PINPIN liền\r\n    Thông tin Pin\r\n    Li-Ion 2 cell\r\n    Hệ điều hành\r\n    Hệ điều hànhWindows 10 Home SL\r\n    Kích thước & trọng lượng\r\n    Kích thướcDài 338.3 mm - Rộng 249.9 mm - Dày 22.7 mm\r\n    Trọng lượng\r\n    2.2 Kg\r\n    Chất liệuVỏ nhựa', 2, 98),
(17, 'Laptop Hp 15 Da0054tu', 10990000, 'https://cdn.tgdd.vn/Products/Images/44/177770/hp-15-da0054tu-4me68pa-thumbnail-600x600.jpg', '    Bộ xử lý\r\n    Công nghệ CPUIntel Core i3 Kabylake\r\n    Loại CPU\r\n    7020U\r\n    Tốc độ CPU2.30 GHz\r\n    Tốc độ tối đaKhông\r\n    Tốc độ Bus2133 MHz\r\n    Bộ nhớ, RAM, Ổ cứng\r\n    RAM4 GB\r\n    Loại RAMDDR4 (On board +1 khe)\r\n    Tốc độ Bus RAM2133 MHz\r\n    Hỗ trợ RAM tối đa\r\n    8 GB\r\n    Ổ cứng\r\n    HDD: 500 GB SATA3, Hỗ trợ khe cắm SSD M.2 SATA3\r\n    Màn hình\r\n    Kích thước màn hình15.6 inch\r\n    Độ phân giải\r\n    Full HD (1920 x 1080)\r\n    Công nghệ màn hìnhHD BrightView LED-backlit\r\n    Màn hình cảm ứng\r\n    Không\r\n    Đồ họa và Âm thanh\r\n    Thiết kế cardCard đồ họa tích hợp\r\n    Card đồ họa\r\n    Intel® HD Graphics 620\r\n    Công nghệ âm thanh\r\n    Combo Microphone & Headphone, Loa kép (2 kênh)\r\n    Cổng kết nối & tính năng mở rộng\r\n    Cổng giao tiếp2 x USB 3.1, HDMI, LAN (RJ45), USB 2.0\r\n    Kết nối không dâyWi-Fi 802.11 a/b/g/n, Bluetooth v4.0\r\n    Khe đọc thẻ nhớ\r\n    Micro SD\r\n    Ổ đĩa quang\r\n    Có (đọc, ghi dữ liệu)\r\n    Webcam\r\n    1 MP, HP TrueVision Webcam\r\n    Đèn bàn phím\r\n    Không\r\n    Tính năng khác\r\n    Multi TouchPad\r\n    PIN\r\n    Loại PINPIN liền\r\n    Thông tin Pin\r\n    Li-Ion 3 cell\r\n    Hệ điều hành\r\n    Hệ điều hànhWindows 10 Home SL\r\n    Kích thước & trọng lượng\r\n    Kích thướcDài 376 mm - Rộng 246 mm - Dày 22.5 mm\r\n    Trọng lượng\r\n    1.77 kg\r\n    Chất liệuVỏ nhựa', 2, 88),
(18, 'Điện Thoại Oppo F11 Pro 64gb', 8490000, 'https://cdn.tgdd.vn/Products/Images/42/198791/oppo-f11-pro-2nambh-400x460.png', 'Màn hình\nCông nghệ màn hình	LTPS LCD\nĐộ phân giải	Full HD+ (1080 x 2340 Pixels)\nMàn hình rộng	6.5\"\nMặt kính cảm ứng	Mặt kính cong 2.5D\nCamera sau\nĐộ phân giải	Chính 48 MP & Phụ 5 MP\nQuay phim	Quay phim FullHD 1080p@30fps\nĐèn Flash	Có\nChụp ảnh nâng cao	Chụp ảnh xóa phông, Lấy nét theo pha, Tự động lấy nét, Chạm lấy nét, Nhận diện khuôn mặt, HDR, Panorama, Làm đẹp (Beautify)\nCamera trước\nĐộ phân giải	16 MP\nVideocall	Hỗ trợ VideoCall thông qua ứng dụng\nThông tin khác	Selfie ngược sáng HDR, Tự động lấy nét, Quay video Full HD, Chế độ làm đẹp, Nhận diện khuôn mặt, Sticker AR (biểu tượng thực tế ảo), Quay video HD\nHệ điều hành - CPU\nHệ điều hành	Android 9.0 (Pie)\nChipset (hãng SX CPU)	MediaTek Helio P70 8 nhân\nTốc độ CPU	4 nhân 2.1 GHz Cortex-A73 & 4 nhân 2.0 GHz Cortex-A53\nChip đồ họa (GPU)	Mali-G72 MP3\nBộ nhớ & Lưu trữ\nRAM	6 GB\nBộ nhớ trong	64 GB\nBộ nhớ còn lại (khả dụng)	Khoảng 46 GB\nThẻ nhớ ngoài	MicroSD, hỗ trợ tối đa 256 GB\nKết nối\nMạng di động	Hỗ trợ 4G\nSIM	2 SIM Nano (SIM 2 chung khe thẻ nhớ)\nWifi	Wi-Fi 802.11 a/b/g/n/ac, Dual-band, Wi-Fi Direct, Wi-Fi hotspot\nGPS	BDS, A-GPS, GLONASS\nBluetooth	A2DP, LE, v5.0\nCổng kết nối/sạc	Micro USB\nJack tai nghe	3.5 mm\nKết nối khác	Không\nThiết kế & Trọng lượng\nThiết kế	Nguyên khối\nChất liệu	Nhựa\nKích thước	Dài 161.3 mm - Ngang 76.1 mm - Dày 8.8 mm\nTrọng lượng	190 g\nThông tin pin & Sạc\nDung lượng pin	4000 mAh\nLoại pin	Pin chuẩn Li-Ion\nCông nghệ pin	Tiết kiệm pin, Sạc nhanh VOOC\nTiện ích\nBảo mật nâng cao	Mở khóa bằng vân tay, Mở khóa bằng khuôn mặt\nTính năng đặc biệt	Đa cửa sổ (chia đôi màn hình)\nĐèn pin\nSạc pin nhanh\nChặn cuộc gọi\nChặn tin nhắn\nMặt kính 2.5D\nNhân bản ứng dụng\nGhi âm	Có, microphone chuyên dụng chống ồn\nRadio	Có\nXem phim	H.265, 3GP, MP4, H.263, H.264(MPEG4-AVC)\nNghe nhạc	AMR, MP3, WAV, AAC, FLAC\nThông tin khác\nThời điểm ra mắt	03/2019', 1, 93),
(19, 'Điện Thoại Samsung Galaxy Note 8', 11990000, 'https://cdn.tgdd.vn/Products/Images/42/106211/samsung-galaxy-note8-black-400x460.png', 'Màn hình\nCông nghệ màn hình	Super AMOLED\nĐộ phân giải	2K+ (1440 x 2960 Pixels)\nMàn hình rộng	6.3\"\nMặt kính cảm ứng	Corning Gorilla Glass 5\nCamera sau\nĐộ phân giải	Chính 12 MP & Phụ 12 MP\nQuay phim	Quay phim 4K 2160p@30fps\nĐèn Flash	Có\nChụp ảnh nâng cao	Chụp ảnh xóa phông, Zoom quang học, Tự động lấy nét, Chạm lấy nét, Nhận diện khuôn mặt, HDR, Panorama, Chống rung quang học (OIS), Chế độ chụp chuyên nghiệp (Pro)\nCamera trước\nĐộ phân giải	8 MP\nVideocall	Có\nThông tin khác	Selfie ngược sáng HDR, Camera góc rộng, Chế độ làm đẹp, Nhận diện khuôn mặt, Chụp bằng giọng nói, Selfie bằng cử chỉ\nHệ điều hành - CPU\nHệ điều hành	Android 7.1 (Nougat)\nChipset (hãng SX CPU)	Exynos 8895 8 nhân 64-bit\nTốc độ CPU	4 nhân 2.3 GHz và 4 nhân 1.7 GHz\nChip đồ họa (GPU)	Mali-G71 MP20\nBộ nhớ & Lưu trữ\nRAM	6 GB\nBộ nhớ trong	64 GB\nBộ nhớ còn lại (khả dụng)	Khoảng 50 GB\nThẻ nhớ ngoài	MicroSD, hỗ trợ tối đa 256 GB\nKết nối\nMạng di động	3G, 4G LTE Cat 16\nSIM	2 Nano SIM\nWifi	Wi-Fi 802.11 a/b/g/n/ac, Dual-band, DLNA, Wi-Fi Direct, Wi-Fi hotspot\nGPS	A-GPS, GLONASS\nBluetooth	EDR, LE, A2DP, apt-X, v5.0\nCổng kết nối/sạc	USB Type-C\nJack tai nghe	3.5 mm\nKết nối khác	NFC, Kết nối nhanh™, OTG, Miracast\nThiết kế & Trọng lượng\nThiết kế	Nguyên khối\nChất liệu	Khung kim loại + mặt kính cường lực\nKích thước	Dài 162,5 mm - Ngang 74,8 mm - Dày 8,6 mm\nTrọng lượng	195 g\nThông tin pin & Sạc\nDung lượng pin	3300 mAh\nLoại pin	Pin chuẩn Li-Ion\nCông nghệ pin	Siêu tiết kiệm pin, Sạc pin nhanh, Sạc pin không dây\nTiện ích\nBảo mật nâng cao	Mở khóa bằng vân tay, Mở khóa bằng khuôn mặt, Quét mống mắt\nTính năng đặc biệt	Chuẩn Kháng nước, Chuẩn kháng bụi\nGhi âm	Có, microphone chuyên dụng chống ồn\nRadio	Không\nXem phim	H.265, 3GP, MP4, AVI, WMV, H.264(MPEG4-AVC), DivX, WMV9, Xvid\nNghe nhạc	Lossless, Midi, MP3, WAV, WMA, AAC++, eAAC+, OGG, AC3, FLAC\nThông tin khác\nThời điểm ra mắt	08/2017', 1, 95),
(20, 'Laptop Hp Pavilion 15 Cs1009tu', 15390000, 'https://cdn.tgdd.vn/Products/Images/44/195383/hp-pavilion-15-cs1009tu-i5-8265u-4gb-1tb-win10-5j-thumb-33397-600x600.jpg', 'Bộ xử lý\nCông nghệ CPU	Intel Core i5 Coffee Lake\nLoại CPU	8265U\nTốc độ CPU	1.60 GHz\nTốc độ tối đa	Turbo Boost 3.9 GHz\nTốc độ Bus	2666 MHz\nBộ nhớ, RAM, Ổ cứng\nRAM	4 GB\nLoại RAM	DDR4 (2 khe)\nTốc độ Bus RAM	2666 MHz\nHỗ trợ RAM tối đa	Đang cập nhật\nỔ cứng	HDD: 1 TB SATA3, Hỗ trợ khe cắm SSD M.2 PCIe\nMàn hình\nKích thước màn hình	15.6 inch\nĐộ phân giải	Full HD (1920 x 1080)\nCông nghệ màn hình	LED Backlight\nMàn hình cảm ứng	Không\nĐồ họa và Âm thanh\nThiết kế card	Card đồ họa tích hợp\nCard đồ họa	Intel® HD Graphics 620\nCông nghệ âm thanh	Realtek High Definition Audio\nCổng kết nối & tính năng mở rộng\nCổng giao tiếp	2 x USB 3.1, HDMI, LAN (RJ45), USB Type-C\nKết nối không dây	Bluetooth v5.0, Wi-Fi 802.11 a/b/g/n/ac\nKhe đọc thẻ nhớ	SD\nỔ đĩa quang	Không\nWebcam	HD webcam\nĐèn bàn phím	Không\nTính năng khác	Hỗ trợ khe PCI M.2 (2280) SSD\nPIN\nLoại PIN	PIN liền\nThông tin Pin	Li-Ion 3 cell (4240mAh)\nHệ điều hành\nHệ điều hành	Windows 10 Home SL\nKích thước & trọng lượng\nKích thước	Dài 361 mm - Rộng 241 mm - Dày 17.9 mm\nTrọng lượng	1.8 kg\nChất liệu	Vỏ nhựa - nắp lưng bằng kim loại', 2, 90);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `account`
--
ALTER TABLE `account`
  ADD PRIMARY KEY (`user_id`),
  ADD UNIQUE KEY `sdt` (`sdt`) USING BTREE,
  ADD UNIQUE KEY `email` (`email`);

--
-- Indexes for table `chitietdonhang`
--
ALTER TABLE `chitietdonhang`
  ADD PRIMARY KEY (`id`),
  ADD KEY `madonhang` (`madonhang`),
  ADD KEY `masanpham` (`masanpham`);

--
-- Indexes for table `chitietgiohang`
--
ALTER TABLE `chitietgiohang`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `donhang`
--
ALTER TABLE `donhang`
  ADD PRIMARY KEY (`id`),
  ADD KEY `idkhachhang` (`idkhachhang`);

--
-- Indexes for table `giohang`
--
ALTER TABLE `giohang`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `idkhachhang` (`idkhachhang`);

--
-- Indexes for table `loaisanpham`
--
ALTER TABLE `loaisanpham`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `sanpham`
--
ALTER TABLE `sanpham`
  ADD PRIMARY KEY (`id`),
  ADD KEY `idsanpham` (`idsanpham`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `account`
--
ALTER TABLE `account`
  MODIFY `user_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=39;

--
-- AUTO_INCREMENT for table `chitietdonhang`
--
ALTER TABLE `chitietdonhang`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=41;

--
-- AUTO_INCREMENT for table `chitietgiohang`
--
ALTER TABLE `chitietgiohang`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `donhang`
--
ALTER TABLE `donhang`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=34;

--
-- AUTO_INCREMENT for table `giohang`
--
ALTER TABLE `giohang`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `sanpham`
--
ALTER TABLE `sanpham`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=26;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `chitietdonhang`
--
ALTER TABLE `chitietdonhang`
  ADD CONSTRAINT `chitietdonhang_ibfk_1` FOREIGN KEY (`madonhang`) REFERENCES `donhang` (`id`),
  ADD CONSTRAINT `chitietdonhang_ibfk_2` FOREIGN KEY (`masanpham`) REFERENCES `sanpham` (`id`);

--
-- Constraints for table `donhang`
--
ALTER TABLE `donhang`
  ADD CONSTRAINT `donhang_ibfk_1` FOREIGN KEY (`idkhachhang`) REFERENCES `account` (`user_id`);

--
-- Constraints for table `giohang`
--
ALTER TABLE `giohang`
  ADD CONSTRAINT `giohang_ibfk_1` FOREIGN KEY (`idkhachhang`) REFERENCES `account` (`user_id`);

--
-- Constraints for table `sanpham`
--
ALTER TABLE `sanpham`
  ADD CONSTRAINT `sanpham_ibfk_1` FOREIGN KEY (`idsanpham`) REFERENCES `loaisanpham` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

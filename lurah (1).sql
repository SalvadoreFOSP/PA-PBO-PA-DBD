-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Waktu pembuatan: 04 Nov 2023 pada 18.45
-- Versi server: 10.4.27-MariaDB
-- Versi PHP: 8.2.0

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `lurah`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `akte_kelahiran`
--

CREATE TABLE `akte_kelahiran` (
  `nik` int(9) NOT NULL,
  `anakke` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `akte_kelahiran`
--

INSERT INTO `akte_kelahiran` (`nik`, `anakke`) VALUES
(739966723, '2');

-- --------------------------------------------------------

--
-- Struktur dari tabel `ktp`
--

CREATE TABLE `ktp` (
  `nik` int(9) NOT NULL,
  `goldar` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `ktp`
--

INSERT INTO `ktp` (`nik`, `goldar`) VALUES
(220911689, 'B');

-- --------------------------------------------------------

--
-- Struktur dari tabel `laporan_surat`
--

CREATE TABLE `laporan_surat` (
  `id` int(3) NOT NULL,
  `tanggal` date NOT NULL,
  `status` char(20) NOT NULL,
  `kategori` varchar(30) NOT NULL,
  `pegawai_lurah_nip` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `laporan_surat`
--

INSERT INTO `laporan_surat` (`id`, `tanggal`, `status`, `kategori`, `pegawai_lurah_nip`) VALUES
(427, '2023-11-04', 'Disetujui', 'KTP', NULL),
(545, '2023-11-04', 'Belum Disetujui', 'Akta', NULL);

-- --------------------------------------------------------

--
-- Struktur dari tabel `surat`
--

CREATE TABLE `surat` (
  `nik` int(9) NOT NULL,
  `nama` varchar(50) NOT NULL,
  `tanggal_lahir` date NOT NULL,
  `tempat_lahir` varchar(30) NOT NULL,
  `jenis_kelamin` varchar(10) NOT NULL,
  `pegawai_lurah_nip` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `surat`
--

INSERT INTO `surat` (`nik`, `nama`, `tanggal_lahir`, `tempat_lahir`, `jenis_kelamin`, `pegawai_lurah_nip`) VALUES
(220911689, 'Salvadore', '2004-10-06', 'Samarinda', 'Pria', NULL),
(739966723, 'Kinur', '2003-06-10', 'Bontang', 'Pria', NULL);

-- --------------------------------------------------------

--
-- Struktur dari tabel `warga`
--

CREATE TABLE `warga` (
  `nama` varchar(50) NOT NULL,
  `alamat` char(30) NOT NULL,
  `nik` int(9) NOT NULL,
  `no_telp` char(20) NOT NULL,
  `username` char(30) NOT NULL,
  `password` char(30) NOT NULL,
  `surat_nik` int(11) DEFAULT NULL,
  `laporan_surat_id` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `warga`
--

INSERT INTO `warga` (`nama`, `alamat`, `nik`, `no_telp`, `username`, `password`, `surat_nik`, `laporan_surat_id`) VALUES
('Salvadore', 'Jalan Kebon Agung', 220911, '08123035', 'salva', 'salva', NULL, NULL),
('Kinur', 'Jalan Gelatik', 220911606, '0812313', 'tesnur', 'tesnur', NULL, NULL);

--
-- Indexes for dumped tables
--

--
-- Indeks untuk tabel `akte_kelahiran`
--
ALTER TABLE `akte_kelahiran`
  ADD PRIMARY KEY (`nik`);

--
-- Indeks untuk tabel `ktp`
--
ALTER TABLE `ktp`
  ADD PRIMARY KEY (`nik`);

--
-- Indeks untuk tabel `laporan_surat`
--
ALTER TABLE `laporan_surat`
  ADD PRIMARY KEY (`id`),
  ADD KEY `laporan_surat_pegawai_lurah_fk` (`pegawai_lurah_nip`);

--
-- Indeks untuk tabel `surat`
--
ALTER TABLE `surat`
  ADD PRIMARY KEY (`nik`),
  ADD KEY `surat_pegawai_lurah_fk` (`pegawai_lurah_nip`);

--
-- Indeks untuk tabel `warga`
--
ALTER TABLE `warga`
  ADD PRIMARY KEY (`nik`),
  ADD UNIQUE KEY `warga__idx` (`laporan_surat_id`),
  ADD KEY `warga_surat_fk` (`surat_nik`);

--
-- Ketidakleluasaan untuk tabel pelimpahan (Dumped Tables)
--

--
-- Ketidakleluasaan untuk tabel `akte_kelahiran`
--
ALTER TABLE `akte_kelahiran`
  ADD CONSTRAINT `akte_kelahiran_surat_fk` FOREIGN KEY (`nik`) REFERENCES `surat` (`nik`);

--
-- Ketidakleluasaan untuk tabel `ktp`
--
ALTER TABLE `ktp`
  ADD CONSTRAINT `ktp_surat_fk` FOREIGN KEY (`nik`) REFERENCES `surat` (`nik`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

package com.example.appbanhang.model;

import java.io.Serializable;

public class DonHang implements Serializable {
    private int id;
    private int idKhachHang;
    private String sdt;
    private String ngayDat;
    private String diaChiGiao;
    private int trangThaiDonHang;

    public DonHang() {
    }

    public DonHang(int id, int idKhachHang, String sdt, String ngayDat, String diaChiGiao, int trangThaiDonHang) {
        this.id = id;
        this.idKhachHang = idKhachHang;
        this.sdt = sdt;
        this.ngayDat = ngayDat;
        this.diaChiGiao = diaChiGiao;
        this.trangThaiDonHang = trangThaiDonHang;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdKhachHang() {
        return idKhachHang;
    }

    public void setIdKhachHang(int idKhachHang) {
        this.idKhachHang = idKhachHang;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }


    public String getNgayDat() {
        return ngayDat;
    }

    public void setNgayDat(String ngayDat) {
        this.ngayDat = ngayDat;
    }

    public String getDiaChiGiao() {
        return diaChiGiao;
    }

    public void setDiaChiGiao(String diaChiGiao) {
        this.diaChiGiao = diaChiGiao;
    }

    public int getTrangThaiDonHang() {
        return trangThaiDonHang;
    }

    public void setTrangThaiDonHang(int trangThaiDonHang) {
        this.trangThaiDonHang = trangThaiDonHang;
    }
}

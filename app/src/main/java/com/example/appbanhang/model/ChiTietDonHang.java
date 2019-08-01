package com.example.appbanhang.model;

import java.io.Serializable;

public class ChiTietDonHang implements Serializable {
    public int id;
    public int idDonHang;
    public int idSanPham;
    public String tenSanPham;
    public Integer giaSP;
    public Integer soLuongSP;
    public String hinhAnhCT;

    public String getHinhAnhCT() {
        return hinhAnhCT;
    }

    public void setHinhAnhCT(String hinhAnhCT) {
        this.hinhAnhCT = hinhAnhCT;
    }

    public ChiTietDonHang() {
    }

    public ChiTietDonHang(int id, int idDonHang, int idSanPham, String tenSanPham, Integer giaSP, Integer soLuongSP,String hinhAnhCT) {
        this.id = id;
        this.idDonHang = idDonHang;
        this.idSanPham = idSanPham;
        this.tenSanPham = tenSanPham;
        this.giaSP = giaSP;
        this.soLuongSP = soLuongSP;
        this.hinhAnhCT=hinhAnhCT;
    }
    public ChiTietDonHang(int id, int idDonHang, String tenSanPham, Integer giaSP, Integer soLuongSP) {
        this.id = id;
        this.idDonHang = idDonHang;
        this.tenSanPham = tenSanPham;
        this.giaSP = giaSP;
        this.soLuongSP = soLuongSP;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdDonHang() {
        return idDonHang;
    }

    public void setIdDonHang(int idDonHang) {
        this.idDonHang = idDonHang;
    }

    public int getIdSanPham() {
        return idSanPham;
    }

    public void setIdSanPham(int idSanPham) {
        this.idSanPham = idSanPham;
    }

    public String getTenSanPham() {
        return tenSanPham;
    }

    public void setTenSanPham(String tenSanPham) {
        this.tenSanPham = tenSanPham;
    }

    public Integer getGiaSP() {
        return giaSP;
    }

    public void setGiaSP(Integer giaSP) {
        this.giaSP = giaSP;
    }

    public Integer getSoLuongSP() {
        return soLuongSP;
    }

    public void setSoLuongSP(Integer soLuongSP) {
        this.soLuongSP = soLuongSP;
    }
}

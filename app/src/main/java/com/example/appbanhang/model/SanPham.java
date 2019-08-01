package com.example.appbanhang.model;

import java.io.Serializable;

public class SanPham implements Serializable {
    public int id;
    public String tenSP;
    public Integer giaSP;
    public String hinhAnhSP;
    public String moTaSP;
    public int idSP;
    public int idNhaCungCap;
    public  int soLuongSP;

    public int getIdNhaCungCap() {
        return idNhaCungCap;
    }

    public void setIdNhaCungCap(int idNhaCungCap) {
        this.idNhaCungCap = idNhaCungCap;
    }

    public SanPham(int id, String tenSP, Integer giaSP, String hinhAnhSP, String moTaSP, int idSP, int idNhaCungCap, int soLuongSP) {
        this.id = id;
        this.tenSP = tenSP;
        this.giaSP = giaSP;
        this.hinhAnhSP = hinhAnhSP;
        this.moTaSP = moTaSP;
        this.idSP = idSP;
        this.idNhaCungCap = idNhaCungCap;
        this.soLuongSP = soLuongSP;
    }



    public int getSoLuongSP() {
        return soLuongSP;
    }

    public void setSoLuongSP(int soLuongSP) {
        this.soLuongSP = soLuongSP;
    }

    public SanPham(int id, String tenSP, Integer giaSP, String hinhAnhSP, String moTaSP, int idSP, int soLuongSP) {
        this.id = id;
        this.tenSP = tenSP;
        this.giaSP = giaSP;
        this.hinhAnhSP = hinhAnhSP;
        this.moTaSP = moTaSP;
        this.idSP = idSP;
        this.soLuongSP = soLuongSP;
    }

    public SanPham(int id, String tenSP, Integer giaSP, String hinhAnhSP, String moTaSP, int idSP) {
        this.id = id;
        this.tenSP = tenSP;
        this.giaSP = giaSP;
        this.hinhAnhSP = hinhAnhSP;
        this.moTaSP = moTaSP;
        this.idSP = idSP;
    }

    public SanPham() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public Integer getGiaSP() {
        return giaSP;
    }

    public void setGiaSP(Integer giaSP) {
        this.giaSP = giaSP;
    }

    public String getHinhAnhSP() {
        return hinhAnhSP;
    }

    public void setHinhAnhSP(String hinhAnhSP) {
        this.hinhAnhSP = hinhAnhSP;
    }

    public String getMoTaSP() {
        return moTaSP;
    }

    public void setMoTaSP(String moTaSP) {
        this.moTaSP = moTaSP;
    }

    public int getIdSP() {
        return idSP;
    }

    public void setIdSP(int idSP) {
        this.idSP = idSP;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

/**
 *
 * @author Tiến Mạnh
 */
public class MatHang {
   private String maMH;
   private String tenMH;
   private String donVi;
   private int soLuong;
   private String maDM;
   private float giaNhap;
   private float giaBan;
   private String anh;
   private String ghiChu;
   private String maVach;

    public MatHang(String maMH, String tenMH, String donVi, int soLuong, String maDM, float giaNhap, float giaBan, String anh, String ghiChu, String maVach) {
        this.maMH = maMH;
        this.tenMH = tenMH;
        this.donVi = donVi;
        this.soLuong = soLuong;
        this.maDM = maDM;
        this.giaNhap = giaNhap;
        this.giaBan = giaBan;
        this.anh = anh;
        this.ghiChu = ghiChu;
        this.maVach = maVach;
    }

    public MatHang() {
    }

    public String getMaMH() {
        return maMH;
    }

    public void setMaMH(String maMH) {
        this.maMH = maMH;
    }

    public String getTenMH() {
        return tenMH;
    }

    public void setTenMH(String tenMH) {
        this.tenMH = tenMH;
    }

    public String getDonVi() {
        return donVi;
    }

    public void setDonVi(String donVi) {
        this.donVi = donVi;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public String getMaDM() {
        return maDM;
    }

    public void setMaDM(String maDM) {
        this.maDM = maDM;
    }

    public float getGiaNhap() {
        return giaNhap;
    }

    public void setGiaNhap(float giaNhap) {
        this.giaNhap = giaNhap;
    }

    public float getGiaBan() {
        return giaBan;
    }

    public void setGiaBan(float giaBan) {
        this.giaBan = giaBan;
    }

    public String getAnh() {
        return anh;
    }

    public void setAnh(String anh) {
        this.anh = anh;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public String getMaVach() {
        return maVach;
    }

    public void setMaVach(String maVach) {
        this.maVach = maVach;
    }
   
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.util.Date;

/**
 *
 * @author Tiến Mạnh
 */
public class HoaDon_M {
   private String maHD; 
   private String maNV;
   private String maKH;
   private String hinhThucThanhToan;
   private Date ngayBan;
   private Float tongTien;
  

    public HoaDon_M(String maHD, String maNV, String maKH, String hinhThucThanhToan, Date ngayBan, Float tongTien) {
        this.maHD = maHD;
        this.maNV = maNV;
        this.maKH = maKH;
        this.hinhThucThanhToan = hinhThucThanhToan;
        this.ngayBan = ngayBan;
        this.tongTien = tongTien;
    }

    public HoaDon_M() {
    }

    public String getMaHD() {
        return maHD;
    }

    public void setMaHD(String maHD) {
        this.maHD = maHD;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public String getMaKH() {
        return maKH;
    }

    public void setMaKH(String maKH) {
        this.maKH = maKH;
    }

    public String getHinhThucThanhToan() {
        return hinhThucThanhToan;
    }

    public void setHinhThucThanhToan(String hinhThucThanhToan) {
        this.hinhThucThanhToan = hinhThucThanhToan;
    }

    public Date getNgayBan() {
        return ngayBan;
    }

    public void setNgayBan(Date ngayBan) {
        this.ngayBan = ngayBan;
    }

    public Float getTongTien() {
        return tongTien;
    }

    public void setTongTien(Float tongTien) {
        this.tongTien = tongTien;
    }
   
   
}

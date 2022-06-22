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
public class KhachHang {

    private String maKH;
    private String tenKH;
    private String SDT;
    private String anh;
    private String diaChi;
    private String email;
    private String ghiChu;

    public KhachHang(String maKH, String tenKH, String SDT, String anh, String diaChi, String email, String ghiChu) {
        this.maKH = maKH;
        this.tenKH = tenKH;
        this.SDT = SDT;
        this.anh = anh;
        this.diaChi = diaChi;
        this.email = email;
        this.ghiChu = ghiChu;
    }

    

    public KhachHang() {
    }

    public String getMaKH() {
        return maKH;
    }

    public void setMaKH(String maKH) {
        this.maKH = maKH;
    }

    public String getTenKH() {
        return tenKH;
    }

    public void setTenKH(String tenKH) {
        this.tenKH = tenKH;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

    public String getAnh() {
        return anh;
    }

    public void setAnh(String anh) {
        this.anh = anh;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

}

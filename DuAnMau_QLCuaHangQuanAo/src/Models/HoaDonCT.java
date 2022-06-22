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
public class HoaDonCT {

    private String MAHD;
    private String MAMH;
    private int soLuong;
    private float DONGIA;   
   private float GIAMGIA;  
    private float THANHTIEN;  

    public HoaDonCT(String MAHD, String MAMH, int soLuong, float DONGIA, float GIAMGIA, float THANHTIEN) {
        this.MAHD = MAHD;
        this.MAMH = MAMH;
        this.soLuong = soLuong;
        this.DONGIA = DONGIA;
        this.GIAMGIA = GIAMGIA;
        this.THANHTIEN = THANHTIEN;
    }

    public HoaDonCT() {
    }

    public String getMAHD() {
        return MAHD;
    }

    public void setMAHD(String MAHD) {
        this.MAHD = MAHD;
    }

    public String getMAMH() {
        return MAMH;
    }

    public void setMAMH(String MAMH) {
        this.MAMH = MAMH;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public float getDONGIA() {
        return DONGIA;
    }

    public void setDONGIA(float DONGIA) {
        this.DONGIA = DONGIA;
    }

    public float getGIAMGIA() {
        return GIAMGIA;
    }

    public void setGIAMGIA(float GIAMGIA) {
        this.GIAMGIA = GIAMGIA;
    }

    public float getTHANHTIEN() {
        return THANHTIEN;
    }

    public void setTHANHTIEN(float THANHTIEN) {
        this.THANHTIEN = THANHTIEN;
    }
            
}

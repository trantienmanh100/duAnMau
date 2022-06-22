/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Form.HoaDon;
import Models.HoaDonCT;
import Models.HoaDon_M;
import Util.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Tiến Mạnh
 */
public class QL_HoaDon {
    DBConnection con =new DBConnection();
    Connection conn ;
    public List<HoaDon_M> getAllHD(){
        List<HoaDon_M> listHD =new ArrayList<>();
        try {
            conn=con.getConnection();
            String query ="SELECT * FROM HD  ORDER BY THOIGIAN DESC";
            PreparedStatement ps =conn.prepareStatement(query);
            ResultSet rs =ps.executeQuery();
            while(rs.next()){
                String maHD =rs.getString("MAHD");
                String maKH =rs.getString("MAKH");
                String maNV =rs.getString("MANV");
                Date thoiGian =rs.getDate("THOIGIAN");
                String hinhttt =rs.getString("HINHTHUCTHANHTOAN");
               
                Float tongTien =rs.getFloat("TONGTIEN");
                listHD.add(new HoaDon_M(maHD, maNV, maKH, hinhttt, thoiGian, tongTien));
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listHD;
    }
    public String checkHDCT(String maHD,String maMH){
        try {
            conn=con.getConnection();
            String query ="SELECT * FROM CHITIETHD WHERE MAHD=? and MAMH=?";
            PreparedStatement ps =conn.prepareStatement(query);
            ps.setString(1, maHD);
            ps.setString(2, maMH);
            ResultSet rs =ps.executeQuery();
            while(rs.next()){
                String kq ="co";
                return kq;
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return null;
    }
     public List<HoaDon_M> getAllHD_KH(String maKH){
        List<HoaDon_M> listHD =new ArrayList<>();
        try {
            conn=con.getConnection();
            String query ="SELECT * FROM HD where MAKH =?";
            PreparedStatement ps =conn.prepareStatement(query);
            ps.setString(1, maKH);
            ResultSet rs =ps.executeQuery();
            while(rs.next()){
                String maHD =rs.getString("MAHD");
                String maKHach =rs.getString("MAKH");
                String maNV =rs.getString("MANV");
                Date thoiGian =rs.getDate("THOIGIAN");
                String hinhttt =rs.getString("HINHTHUCTHANHTOAN");
               
                Float tongTien =rs.getFloat("TONGTIEN");
                listHD.add(new HoaDon_M(maHD, maNV, maKHach, hinhttt, thoiGian, tongTien));
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listHD;
    }
    
    public List<HoaDonCT> getAllHDCT(String maHD){
        List<HoaDonCT> listHDCT =new ArrayList<>();
        try {
            conn=con.getConnection();
            String query ="SELECT * FROM CHITIETHD WHERE MAHD=?";
            PreparedStatement ps =conn.prepareStatement(query);
            ps.setString(1, maHD);
            ResultSet rs =ps.executeQuery();
            while(rs.next()){
                String maHoaD =rs.getString("MAHD");
                String maMH =rs.getString("MAMH");
                int sl =rs.getInt("SL");
                 Float donGia =rs.getFloat("DONGIA");
                Float giamGia =rs.getFloat("GIAMGIA");
                Float thanhTien =rs.getFloat("THANHTIEN");
                listHDCT.add(new HoaDonCT(maHD, maMH, sl, donGia, giamGia, thanhTien));
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listHDCT;
    }
    public void insertHDCT(HoaDonCT hdct){
        try{
        conn=con.getConnection();
           String sql ="INSERT INTO CHITIETHD(MAHD,MAMH,SL,DONGIA,GIAMGIA,THANHTIEN) VALUES(?,?,?,?,?,?)";
           PreparedStatement ps =conn.prepareStatement(sql);
           
            ps.setString(1, hdct.getMAHD());
            ps.setString(2, hdct.getMAMH());
            
            ps.setInt(3,hdct.getSoLuong() );
            ps.setFloat(4, hdct.getDONGIA());
            ps.setFloat(5, hdct.getGIAMGIA());
            ps.setFloat(6, hdct.getTHANHTIEN());
            ps.executeUpdate();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void insertHD(HoaDon_M hd){
        try{
        conn=con.getConnection();
           String sql ="INSERT INTO HD(MAHD,MANV,MAKH,THOIGIAN,HINHTHUCTHANHTOAN,TONGTIEN) VALUES(?,?,?,?,?,?)";
           PreparedStatement ps =conn.prepareStatement(sql);
           
            ps.setString(1, hd.getMaHD());
            ps.setString(2, hd.getMaNV());
            ps.setString(3, hd.getMaKH());
            java.sql.Date ngayBan = new java.sql.Date( hd.getNgayBan().getTime() );
            ps.setDate(4, ngayBan );
            ps.setString(5, hd.getHinhThucThanhToan());
            ps.setFloat(6, hd.getTongTien());
            ps.executeUpdate();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void updateHDCT(HoaDonCT hdct){
        try{
        conn=con.getConnection();
           String sql ="UPDATE CHITIETHD SET SL=?,DONGIA=?,GIAMGIA=?,THANHTIEN=? WHERE MAHD =? AND MAMH=?";
           PreparedStatement ps =conn.prepareStatement(sql);
           
            ps.setString(5, hdct.getMAHD());
            ps.setString(6, hdct.getMAMH());
            
            ps.setInt(1,hdct.getSoLuong() );
            ps.setFloat(2, hdct.getDONGIA());
            ps.setFloat(3, hdct.getGIAMGIA());
            ps.setFloat(4, hdct.getTHANHTIEN());
            ps.executeUpdate();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
        public void updateHD(HoaDon_M hd){
        try{
        conn=con.getConnection();
           String sql ="UPDATE HD SET MANV=?,MAKH=?,THOIGIAN=?,HINHTHUCTHANHTOAN=?,TONGTIEN=? WHERE MAHD=?";
           PreparedStatement ps =conn.prepareStatement(sql);
           
            ps.setString(6, hd.getMaHD());
            ps.setString(1, hd.getMaNV());
            ps.setString(2, hd.getMaKH());
            java.sql.Date ngayBan = new java.sql.Date( hd.getNgayBan().getTime() );
            ps.setDate(3, ngayBan );
            ps.setString(4, hd.getHinhThucThanhToan());
            ps.setFloat(5, hd.getTongTien());
            ps.executeUpdate();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
        public void deleteHDCT(String maHD,String maMH){
        try {
            conn =con.getConnection();
            String sql ="DELETE CHITIETHD WHERE MAHD=? and MAMH=?";
            PreparedStatement ps =conn.prepareStatement(sql);
            ps.setString(1, maHD);
            ps.setString(2, maMH);
            ps.executeUpdate();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        }
        public void delete(String maHD){
        try {
            conn =con.getConnection();
            String sql ="DELETE HD WHERE MAHD=? ";
            PreparedStatement ps =conn.prepareStatement(sql);
            ps.setString(1, maHD);
            ps.executeUpdate();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        }
        public void updatetongTien(Float tongTien,String maHD){
            try {
                conn=con.getConnection();
                String query ="UPDATE HD SET TONGTIEN=? WHERE MAHD =? ";
                PreparedStatement ps =conn.prepareStatement(query);
                ps.setFloat(1,tongTien);
                ps.setString(2,maHD);
                ps.executeUpdate();
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
} 

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Models.KhachHang;
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
public class QL_KhachHang {
    DBConnection con =new DBConnection();
    Connection conn;
    public List<KhachHang> getAll_KH(){
        List<KhachHang> listKH =new ArrayList<>();
        try {
            conn =con.getConnection();
            String sql ="SELECT *FROM KHACHHANG";
            PreparedStatement ps =conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                String ma =rs.getString("MAKH");
                String ten =rs.getString("TENKH");
                String anh =rs.getString("ANH");
                String sdt =rs.getString("SDT");
                String diaChi =rs.getString("DIACHI");
                String email =rs.getString("EMAIL");
                String ghiChu =rs.getString("GHICHU");
                listKH.add(new KhachHang(ma, ten, sdt, anh, diaChi, email, ghiChu));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listKH;
    }
    public KhachHang insert(KhachHang kh){
        try {
            conn =con.getConnection();
            String sql ="insert into KHACHHANG(MAKH,TENKH,ANH,SDT,DIACHI,GHICHU,EMAIL )"
                    + "values(?,?,?,?,?,?,?)" ;
            
            PreparedStatement ps =conn.prepareStatement(sql);
            ps.setString(1, kh.getMaKH());
            ps.setString(2, kh.getTenKH());
            ps.setString(3, kh.getAnh());
            ps.setString(4, kh.getSDT());
            ps.setString(5, kh.getDiaChi());
            ps.setString(6, kh.getGhiChu());
            ps.setString(7, kh.getEmail());
            ps.executeUpdate();
            conn.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return kh;
    } 
    public void  update(KhachHang kh){
        try {
           conn=con.getConnection();
           String sql ="UPDATE KHACHHANG SET TENKH=?,ANH=?,SDT=?,DIACHI=?,GHICHU=?,EMAIL=? WHERE MAKH=?";
           PreparedStatement ps =conn.prepareStatement(sql);
            ps.setString(7, kh.getMaKH());
            ps.setString(1, kh.getTenKH());
            ps.setString(2, kh.getAnh());
            ps.setString(3, kh.getSDT());
            ps.setString(4, kh.getDiaChi());
            ps.setString(5, kh.getGhiChu());
            ps.setString(6, kh.getEmail());
            ps.executeUpdate();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
       
    }
    public void delete(String maKH){
        try {
           conn =con.getConnection();
            String sql ="DELETE KHACHHANG WHERE MAKH=?";
            PreparedStatement ps =conn.prepareStatement(sql);
            ps.setString(1, maKH);
            ps.executeUpdate();
            conn.close(); 
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Models.NhanVien;
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
public class QlNV {
    DBConnection cn =new DBConnection();
    Connection con ;
    //NhanVien nv =new NhanVien();
    public List<NhanVien> getAll_NV(){
      List<NhanVien> listNV =new ArrayList<>();
        try {
            con =cn.getConnection();
            String sql ="SELECT * FROM NHANVIEN WHERE TRANGTHAI =0";
            PreparedStatement ps =con.prepareStatement(sql);
            ResultSet rs =ps.executeQuery();
            while (rs.next()){
                String ma =rs.getString("MANV");
                 String TENNV =rs.getString("TENNV");
                  String ANH =rs.getString("ANH");
                   String SDT =rs.getString("SDT");
                     Date NGAYSINH =rs.getDate("NGAYSINH");
                       String GHICHU =rs.getString("GHICHU");
                   listNV.add(new NhanVien(ma, TENNV, NGAYSINH, SDT, GHICHU, ANH));
                   //con.close();
                   
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listNV;
    }
    public NhanVien insert(NhanVien nv){
        try {
            con =cn.getConnection();
            String sql ="insert into NHANVIEN(MANV,TENNV,ANH,SDT,NGAYSINH,GHICHU,TRANGTHAI )"
                    + "values(?,?,?,?,?,?,?)" ;
            PreparedStatement ps =con.prepareStatement(sql);
            ps.setString(1, nv.getMaNV());
            ps.setString(2, nv.getTenNV());
            ps.setString(3, nv.getAnh());
            ps.setString(4, nv.getSDT());
             java.sql.Date ngaySinh = new java.sql.Date( nv.getNgaySinh().getTime() );
            ps.setDate(5, ngaySinh);
            ps.setString(6, nv.getGhiChu());
            ps.setString(7, "0");
            ps.executeUpdate();
            con.close();
                    

        } catch (Exception e) {
            e.printStackTrace();
        }
        
     return null;
    }
    public void update(NhanVien nv){
        try {
            con =cn.getConnection();
            String sql ="UPDATE NHANVIEN SET TENNV =?,ANH=?,SDT=?,NGAYSINH=?,GHICHU=? WHERE MANV=?";
            PreparedStatement ps =con.prepareStatement(sql);
            ps.setString(6, nv.getMaNV());
            ps.setString(1, nv.getTenNV());
            ps.setString(2, nv.getAnh());
            ps.setString(3, nv.getSDT());
             java.sql.Date ngaySinh = new java.sql.Date( nv.getNgaySinh().getTime() );
            ps.setDate(4, ngaySinh);
            ps.setString(5, nv.getGhiChu());
            ps.executeUpdate();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void khoaNV(String maNV){
        try {
            con =cn.getConnection();
            String sql ="UPDATE NHANVIEN SET TRANGTHAI =1 where MANV=?";
            PreparedStatement ps =con.prepareStatement(sql);
            ps.setString(1, maNV);
            ps.executeUpdate();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Models.DanhMucM;
import Models.MatHang;
import Util.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.naming.spi.DirStateFactory;

/**
 *
 * @author Tiến Mạnh
 */
   
public class QLMatHang {
    DBConnection con =new DBConnection();
    Connection conn ;
    int SL =0;
    public List<MatHang> getAllMH(String maDM ,String SX){
        List<MatHang> listMH =new ArrayList<>();
        try {
           conn=con.getConnection();
           String sql ="SELECT * FROM MATHANG WHERE MADANHMUC =? "+SX;
            PreparedStatement ps =conn.prepareStatement(sql);
            ps.setString(1, maDM);
            ResultSet rs =ps.executeQuery();
            while (rs.next()) {                
     String maMH =rs.getString("MAMH");
    String tenMH =rs.getString("TENMH");
    String donVi = rs.getString("DONVITINH");
    int soLuong =rs.getInt("SL");
    String maDanhMuc = rs.getString("MADANHMUC");
    float giaNhap =rs.getFloat("GIANHAP");
    float giaBan =rs.getFloat("GIABAN");
    String anh = rs.getString("ANH");
    String ghiChu= rs.getString("GHICHU");
    String maVach= rs.getString("MAVACH"); 
    listMH.add(new MatHang(maMH, tenMH, donVi, soLuong, maDanhMuc, giaNhap, giaBan, anh, ghiChu, maVach));
            }
            conn.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listMH;
    }
    public int getSL(String maMH){
        try {
           conn=con.getConnection();
           String sql ="SELECT SL FROM MATHANG where MAMH =? ";
            PreparedStatement ps =conn.prepareStatement(sql);
            ps.setString(1, maMH);
            ResultSet rs =ps.executeQuery();
            while (rs.next()) {        
                SL =Integer.parseInt(rs.getString(1));
            }
            }
        catch (Exception e) {
            e.printStackTrace();
        }
        return SL;
    }
    public List<MatHang> getAllMH(){
        List<MatHang> listMH =new ArrayList<>();
        try {
           conn=con.getConnection();
           String sql ="SELECT * FROM MATHANG ";
            PreparedStatement ps =conn.prepareStatement(sql);
            
            ResultSet rs =ps.executeQuery();
            while (rs.next()) {                
     String maMH =rs.getString("MAMH");
    String tenMH =rs.getString("TENMH");
    String donVi = rs.getString("DONVITINH");
    int soLuong =rs.getInt("SL");
    String maDanhMuc = rs.getString("MADANHMUC");
    float giaNhap =rs.getFloat("GIANHAP");
    float giaBan =rs.getFloat("GIABAN");
    String anh = rs.getString("ANH");
    String ghiChu= rs.getString("GHICHU");
    String maVach= rs.getString("MAVACH"); 
    listMH.add(new MatHang(maMH, tenMH, donVi, soLuong, maDanhMuc, giaNhap, giaBan, anh, ghiChu, maVach));
            }
            conn.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listMH;
    }
    
    public MatHang insert(MatHang mh){
        try {
            conn =con.getConnection();
            String sql ="INSERT INTO MATHANG(MAMH,TENMH,MAVACH,SL,GIANHAP,GIABAN,DONVITINH,MADANHMUC,ANH,GHICHU)"
                    + " VALUES (?,?,?,?,?,?,?,?,?,?)";
            
            PreparedStatement ps =conn.prepareStatement(sql);
            ps.setString(1, mh.getMaMH());
            ps.setString(2, mh.getTenMH());
            ps.setString(3, mh.getMaVach());
            ps.setInt(4, mh.getSoLuong());
            ps.setFloat(5, mh.getGiaNhap());
            ps.setFloat(6, mh.getGiaBan());
            ps.setString(7, mh.getDonVi());
            ps.setString(8, mh.getMaDM());
            ps.setString(9, mh.getAnh());
            ps.setString(10, mh.getGhiChu());
            ps.executeUpdate();
            conn.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mh;
    } 
    public void update(MatHang mh){
        try {
            conn =con.getConnection();
            String sql ="UPDATE MATHANG SET TENMH=?,MAVACH=?,SL=?,GIANHAP=?,GIABAN=?,DONVITINH=?,MADANHMUC=?,ANH=?,GHICHU=? WHERE MAMH =?";
                    
            
            PreparedStatement ps =conn.prepareStatement(sql);
            ps.setString(10, mh.getMaMH());
            ps.setString(1, mh.getTenMH());
            ps.setString(2, mh.getMaVach());
            ps.setInt(3, mh.getSoLuong());
            ps.setFloat(4, mh.getGiaNhap());
            ps.setFloat(5, mh.getGiaBan());
            ps.setString(6, mh.getDonVi());
            ps.setString(7, mh.getMaDM());
            ps.setString(8, mh.getAnh());
            ps.setString(9, mh.getGhiChu());
            ps.executeUpdate();
            conn.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
       
    } 
    public void updateSL(String maMH,int SL){
        try {
            conn =con.getConnection();
            String sql =" UPDATE MATHANG SET SL =SL - ? WHERE MAMH =?";
                    
            
            PreparedStatement ps =conn.prepareStatement(sql);
            ps.setInt(1, SL);
            ps.setString(2, maMH);
            
            ps.executeUpdate();
            conn.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
       
    } 
    public void delete(String maMH){
        try {
           conn =con.getConnection();
            String sql ="DELETE MATHANG WHERE MAMH=?";
            PreparedStatement ps =conn.prepareStatement(sql);
            ps.setString(1, maMH);
            ps.executeUpdate();
            conn.close(); 
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;


import Models.DanhMucM;
import Util.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Tiến Mạnh
 */
public class QL_DM {
    DBConnection cn =new DBConnection();
    Connection con ;
    public List<DanhMucM> getAll_DM(){
        List<DanhMucM> listDM =new ArrayList<>();
        try {
            con =cn.getConnection();
            String sql ="SELECT * FROM DANHMUC";
            PreparedStatement ps =con.prepareStatement(sql);
            ResultSet rs =ps.executeQuery();
            while (rs.next()){
            String ma =rs.getString("MADANHMUC");
                 String ten =rs.getString("TENDANHMUC");
                 listDM.add(new DanhMucM(ma, ten));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listDM;
    }
    public void insert (DanhMucM dm){
        try {
            con =cn.getConnection();
            String sql ="insert into DANHMUC(MADANHMUC,TENDANHMUC) VALUES(?,?)";
            PreparedStatement ps =con.prepareStatement(sql);
            ps.setString(1, dm.getMaDM());
            ps.setString(2, dm.getTenDM());
            ps.executeUpdate();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void update (DanhMucM dm){
        try {
            con =cn.getConnection();
            String sql ="UPDATE DANHMUC SET TENDANHMUC =? WHERE MADANHMUC =?";
            PreparedStatement ps =con.prepareStatement(sql);
            ps.setString(2, dm.getMaDM());
            ps.setString(1, dm.getTenDM());
            ps.executeUpdate();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void delete(String maDM){
        try {
            con =cn.getConnection();
            String sql ="DELETE DANHMUC WHERE MADANHMUC =?";
            PreparedStatement ps =con.prepareStatement(sql);
            ps.setString(1, maDM);
            ps.executeUpdate();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

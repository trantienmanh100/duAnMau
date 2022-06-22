/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Util.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author Tiến Mạnh
 */
public class QL_BCTT {
    DBConnection con =new DBConnection();
    Connection conn ;
    int kq;
    double duLieu =0;
    public int HTTT( String httt){
        try {
            
           conn=con.getConnection();
        String query = "select count(MAHD) from HD\n" +
"WHERE HD.HINHTHUCTHANHTOAN = "+ httt ;
            PreparedStatement ps =conn.prepareStatement(query);
            ResultSet rs =ps.executeQuery();
            while(rs.next()){
              kq =Integer.parseInt(rs.getString(1));
            }
        
        } catch (Exception e) {
            e.printStackTrace();
        }
        return kq;
    }
    public double thongSo( String query){
        try {
            conn=con.getConnection();
        
        PreparedStatement ps =conn.prepareStatement(query);
        
        ResultSet rs =ps.executeQuery();
        while(rs.next()){
            if(rs.getString(1)==null){
                duLieu =0;
            }
            else{
            duLieu =Double.parseDouble(rs.getString(1));
            }
        }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return duLieu;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Models.Account;
import Util.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author Tiến Mạnh
 */
public class QL_Account {
    public Account checkLogin(String username,String password){
        try {
            DBConnection cn =new DBConnection();
            Connection con= cn.getConnection();
            String sql="select * from ACCOUNT WHERE USERNAME =? AND PASSWORD =?";
            PreparedStatement ps =con.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs =ps.executeQuery();
            if(rs.next()){
                Account a =new Account();
                a.setUserName(username);
                a.setPassword(password);
                return a;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author Tiến Mạnh
 */
public class DBConnection {
    public Connection getConnection() throws Exception{
       Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
       String url ="jdbc:sqlserver://localhost:1433;databaseName=QLKQA;user=manh;password=123454321";
       return DriverManager.getConnection(url);
    }
}

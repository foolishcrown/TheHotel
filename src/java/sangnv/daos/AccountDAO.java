/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sangnv.daos;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import sangnv.conn.MyConnection;
import sangnv.dtos.AccountDTO;

/**
 *
 * @author Shang
 */
public class AccountDAO implements Serializable {
    
    private Connection conn;
    private PreparedStatement preStm;
    private ResultSet rs;
    
    public AccountDAO() {
    }
    
    public void closeConnection() throws SQLException {
        if (rs != null) {
            rs.close();
        }
        if (preStm != null) {
            preStm.close();
        }
        if (conn != null) {
            conn.close();
        }
    }
    
    public String checkRole(String email, String password) throws NamingException, SQLException {
        String role = "fail";
        String sql = "Select role_name From Role where role_id in (Select role_id From Account Where acc_email = ? and acc_password = ?)";
        try {
            conn = MyConnection.getMyConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setString(1, email);
            preStm.setString(2, password);
            rs = preStm.executeQuery();
            if (rs.next()) {
                role = rs.getString("role_name");
            }
        } finally {
            closeConnection();
        }
        return role;
    }
    
    public boolean registerAccount(AccountDTO dto) throws NamingException, SQLException {
        boolean check = false;
        String sql = "Insert Into Account(acc_email, acc_password, acc_phone, acc_address, role_id) Values(?,?,?,?,?)";
        try {
            conn = MyConnection.getMyConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setString(1, dto.getEmail());
            preStm.setString(2, dto.getPassword());
            preStm.setString(3, dto.getPhone());
            preStm.setString(4, dto.getAddress());
            preStm.setInt(5, dto.getRole());
            check = preStm.executeUpdate() > 0;
        } finally {
            closeConnection();
        }
        return check;
    }
    
    
    
}

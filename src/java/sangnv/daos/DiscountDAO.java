/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sangnv.daos;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.naming.NamingException;
import sangnv.conn.MyConnection;
import sangnv.dtos.DiscountDTO;

/**
 *
 * @author Shang
 */
public class DiscountDAO implements Serializable {

    private Connection conn;
    private PreparedStatement preStm;
    private ResultSet rs;

    private void closeConnection() throws SQLException {
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

    public DiscountDTO getCodeByID(String discountCode) throws NamingException, SQLException {
        DiscountDTO dto = null;
        String sql = "Select discount_value, status, expiry_date From Discount Where discount_id = ?";
        try {
            conn = MyConnection.getMyConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setString(1, discountCode);
            rs = preStm.executeQuery();
            if (rs.next()) {
                float value = rs.getFloat("discount_value");
                boolean status = rs.getBoolean("status");
                Date expiryDate = rs.getDate("expiry_date");
                dto = new DiscountDTO(discountCode, value, status, expiryDate);
            }
        } finally {
            closeConnection();
        }
        return dto;
    }

    public boolean checkValidCode(DiscountDTO dto) {
        boolean check = true;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar currCal = Calendar.getInstance();
        String currDate = sdf.format(currCal.getTime());
        Date sqlCurDate = Date.valueOf(currDate);
        if (dto.getExpiryDate().getTime() <= sqlCurDate.getTime()) {
            check = false;
        }
        if (!dto.isStatus()) {
            check = false;
        }
        return check;
    }

    public boolean applyCode(String discountCode) throws NamingException, SQLException {
        boolean check = false;
        String sql = "Update Discount set status = 0 Where discount_id = ?";
        try {
            conn = MyConnection.getMyConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setString(1, discountCode);
            check = preStm.executeUpdate() > 0;
        } finally {
            closeConnection();
        }
        return check;
    }

}

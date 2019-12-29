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
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import sangnv.conn.MyConnection;
import sangnv.dtos.FeedbackDTO;

/**
 *
 * @author Shang
 */
public class FeedbackDAO implements Serializable {

    public FeedbackDAO() {
    }

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

    public boolean sendFeedback(FeedbackDTO dto) throws NamingException, SQLException {
        boolean check = false;
        String sql = "Insert Into Feedback(hotel_id, acc_email, feedback_content) Values(?,?,?)";
        try {
            conn = MyConnection.getMyConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setString(1, dto.getHotelID());
            preStm.setString(2, dto.getEmail());
            preStm.setString(3, dto.getContent());
            check = preStm.executeUpdate() > 0;
        } finally {
            closeConnection();
        }
        return check;
    }

    public List<FeedbackDTO> getListFeedBackByHotelID(String hotelID) throws NamingException, SQLException {
        List<FeedbackDTO> result = null;
        String sql = "Select acc_email, feedback_content, feedback_date From Feedback Where hotel_id = ?";
        try {
            conn = MyConnection.getMyConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setString(1, hotelID);
            result = new ArrayList<>();
            rs = preStm.executeQuery();
            while (rs.next()) {
                String email = rs.getString("acc_email");
                String content = rs.getString("feedback_content");
                Timestamp date = rs.getTimestamp("feedback_date");
                FeedbackDTO dto = new FeedbackDTO(hotelID, email, content, date);
                result.add(dto);
            }
        } finally {
            closeConnection();
        }
        return result;
    }

}

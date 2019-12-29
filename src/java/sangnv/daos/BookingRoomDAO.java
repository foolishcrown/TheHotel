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
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import sangnv.conn.MyConnection;
import sangnv.dtos.BookingRoomDTO;

/**
 *
 * @author Shang
 */
public class BookingRoomDAO implements Serializable {

    private Connection conn;
    private PreparedStatement preStm;
    private ResultSet rs;

    public BookingRoomDAO() {
    }

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

    public boolean confirmBookingDetail(BookingRoomDTO dto) throws NamingException, SQLException {
        boolean check = false;
        String sql = "Insert Into BookingRoom(booking_id, type_id, amount, price, date_checkin, date_checkout) Values(?,?,?,?,?,?)";
        try {
            conn = MyConnection.getMyConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setInt(1, dto.getBookingID());
            preStm.setString(2, dto.getTypeID());
            preStm.setInt(3, dto.getAmount());
            preStm.setFloat(4, dto.getPrice());
            preStm.setDate(5, dto.getCheckin());
            preStm.setDate(6, dto.getCheckout());
            check = preStm.executeUpdate() > 0;
        } finally {
            closeConnection();
        }
        return check;
    }

    public List<BookingRoomDTO> getDetailListByID(int bookingID) throws NamingException, SQLException {
        List<BookingRoomDTO> result = null;
        String sql = "Select type_id, amount, price, date_checkin, date_checkout From BookingRoom Where booking_id = ?";
        try {
            conn = MyConnection.getMyConnection();
            preStm = conn.prepareStatement(sql);
            result = new ArrayList<>();
            preStm.setInt(1, bookingID);
            rs = preStm.executeQuery();
            while (rs.next()) {
                String typeID = rs.getString("type_id");
                int amount = rs.getInt("amount");
                float price = rs.getFloat("price");
                Date checkin = rs.getDate("date_checkin");
                Date checkout = rs.getDate("date_checkout");
                BookingRoomDTO dto = new BookingRoomDTO(bookingID, typeID, amount, price);
                dto.setCheckin(checkin);
                dto.setCheckout(checkout);
                result.add(dto);
            }
        } finally {
            closeConnection();
        }

        return result;
    }

}

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
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import sangnv.conn.MyConnection;
import sangnv.dtos.BookingDTO;

/**
 *
 * @author Shang
 */
public class BookingDAO implements Serializable {

    private Connection conn;
    private PreparedStatement preStm;
    private ResultSet rs;

    public BookingDAO() {
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

    public boolean confirmBookingOrder(BookingDTO dto) throws NamingException, SQLException {
        boolean check = false;
        String sql = "Insert Into Booking(acc_email, total_price, discount_id, booking_discount) Values(?, ?, ?, ?)";
        try {
            conn = MyConnection.getMyConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setString(1, dto.getCustomer());
            preStm.setFloat(2, dto.getTotal());
            preStm.setString(3, dto.getDiscountID());
            preStm.setFloat(4, dto.getDiscount());
            check = preStm.executeUpdate() > 0;
        } finally {
            closeConnection();
        }
        return check;
    }

    public int getNewestBookingIDByCustomer(String email) throws NamingException, SQLException {
        int result = -1;
        String sql = "Select booking_id From Booking Where booking_date = (Select MAX(booking_date) From Booking Where acc_email = ?)";
        try {
            conn = MyConnection.getMyConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setString(1, email);
            rs = preStm.executeQuery();
            if (rs.next()) {
                result = rs.getInt("booking_id");
            }
        } finally {
            closeConnection();
        }
        return result;
    }

    public List<BookingDTO> getListHistoryByCustomer(String email) throws NamingException, SQLException {
        List<BookingDTO> result = null;
        String sql = "Select booking_id, booking_date, total_price, booking_discount, discount_id, booking_status From Booking Where acc_email = ? ORDER BY booking_date DESC";
        try {
            conn = MyConnection.getMyConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setString(1, email);
            rs = preStm.executeQuery();
            result = new ArrayList<>();
            while (rs.next()) {
                int id = rs.getInt("booking_id");
                Timestamp bookingDate = rs.getTimestamp("booking_date");
                float total = rs.getFloat("total_price");
                String discountID = rs.getString("discount_id");
                float discount = 0;
                if (discountID != null) {
                    discount = rs.getFloat("booking_discount");
                }
                boolean status = rs.getBoolean("booking_status");
                BookingDTO dto = new BookingDTO(id, email, discountID, bookingDate, total, discount);
                dto.setStatus(status);
                result.add(dto);
            }
        } finally {
            closeConnection();
        }
        return result;
    }
    public List<BookingDTO> getListHistoryByCustomerAndDate(String email, Date bookingDay) throws NamingException, SQLException {
        List<BookingDTO> result = null;
        String sql = "Select booking_id, booking_date, total_price, booking_discount, discount_id, booking_status From Booking Where acc_email = ? and CONVERT(date, booking_date) = ? ORDER BY booking_date DESC";
        try {
            conn = MyConnection.getMyConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setString(1, email);
            preStm.setDate(2, bookingDay);
            rs = preStm.executeQuery();
            result = new ArrayList<>();
            while (rs.next()) {
                int id = rs.getInt("booking_id");
                Timestamp bookingDate = rs.getTimestamp("booking_date");
                float total = rs.getFloat("total_price");
                String discountID = rs.getString("discount_id");
                float discount = 0;
                if (discountID != null) {
                    discount = rs.getFloat("booking_discount");
                }
                boolean status = rs.getBoolean("booking_status");
                BookingDTO dto = new BookingDTO(id, email, discountID, bookingDate, total, discount);
                dto.setStatus(status);
                result.add(dto);
            }
        } finally {
            closeConnection();
        }
        return result;
    }
    
    public boolean cancelBooking(int bookingID) throws NamingException, SQLException{
        boolean check = false;
        String sql = "Update Booking Set booking_status = 0 where booking_id = ?";
        try {
            conn = MyConnection.getMyConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setInt(1, bookingID);
            check = preStm.executeUpdate() > 0;
        }finally {
         closeConnection();
        }
        return check;
    }

    public List<BookingDTO> getAllHistory() throws NamingException, SQLException {
        List<BookingDTO> result = null;
        String sql = "Select booking_id, booking_date, total_price, booking_discount, discount_id, acc_email From Booking ORDER BY booking_date DESC";
        try {
            conn = MyConnection.getMyConnection();
            preStm = conn.prepareStatement(sql);
            rs = preStm.executeQuery();
            result = new ArrayList<>();
            while (rs.next()) {
                int id = rs.getInt("booking_id");
                Timestamp bookingDate = rs.getTimestamp("booking_date");
                float total = rs.getFloat("total_price");
                String discountID = rs.getString("discount_id");
                float discount = 0;
                if (discountID != null) {
                    discount = rs.getFloat("booking_discount");
                }
                String email = rs.getString("acc_email");
                BookingDTO dto = new BookingDTO(id, email, discountID, bookingDate, total, discount);
                result.add(dto);

            }
        } finally {
            closeConnection();
        }

        return result;
    }
    
    
    public BookingDTO getBookingByID(int bookingID) throws NamingException, SQLException{
        BookingDTO dto = null;
        String sql = "Select acc_email, booking_date, total_price, booking_discount, booking_status, discount_id From Booking Where booking_id = ?";
        try {
            conn = MyConnection.getMyConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setInt(1, bookingID);
            rs = preStm.executeQuery();
            if (rs.next()) {
                String email = rs.getString("acc_email");
                Timestamp bookingDate = rs.getTimestamp("booking_date");
                float total = rs.getFloat("total_price");
                String discountCode = rs.getString("discount_id");
                float discount = 0;
                if (discountCode != null) {
                    discount = rs.getFloat("booking_discount");
                }
                boolean status = rs.getBoolean("booking_status");
                dto = new BookingDTO(bookingID, email, discountCode, bookingDate, total, discount);
                dto.setStatus(status);
            }
        }finally {
         closeConnection();
        }
        return dto;
    }

}

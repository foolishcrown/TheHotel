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
import sangnv.dtos.HotelDTO;

/**
 *
 * @author Shang
 */
public class HotelDAO implements Serializable {

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

    public List<HotelDTO> getAllHotel() throws NamingException, SQLException {
        List<HotelDTO> result = null;
        String sql = "Select hotel_id, hotel_name From Hotel";
        try {
            conn = MyConnection.getMyConnection();
            preStm = conn.prepareStatement(sql);
            rs = preStm.executeQuery();
            result = new ArrayList<>();
            while (rs.next()) {
                String id = rs.getString("hotel_id");
                String name = rs.getString("hotel_name");
                HotelDTO dto = new HotelDTO(id, name);
                result.add(dto);
            }
        } finally {
            closeConnection();
        }
        return result;
    }

    public List<HotelDTO> searchHotelByName(String txtSearch) throws NamingException, SQLException {
        List<HotelDTO> result = null;
        String sql = "Select hotel_id, hotel_name, hotel_address, hotel_phone, hotel_photo, area_id, description From Hotel Where hotel_name LIKE ? and hotel_status = 1";
        try {
            conn = MyConnection.getMyConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setString(1, "%" + txtSearch + "%");
            rs = preStm.executeQuery();
            result = new ArrayList<>();
            while (rs.next()) {
                String id = rs.getString("hotel_id");
                String name = rs.getString("hotel_name");
                String address = rs.getString("hotel_address");
                String phone = rs.getString("hotel_phone");
                String photo = rs.getString("hotel_photo");
                String description = rs.getString("description");
                int areaID = rs.getInt("area_id");
                HotelDTO dto = new HotelDTO(id, name, address, phone, photo, description, areaID);
                result.add(dto);
            }
        } finally {
            closeConnection();
        }
        return result;
    }

    public List<HotelDTO> searchByGroup(int area, Date checkin, Date checkout, int numOfRoom) throws NamingException, SQLException {
        List<HotelDTO> result = null;
        String stm = "?";
        if (area == -1) {
            stm = "ANY(select area_id From Area)";
        }
        String sql = "Select hotel_id, hotel_name, hotel_address, hotel_phone, hotel_photo, area_id, description \n"
                + "From Hotel\n"
                + "Where area_id = " + stm + " and hotel_id in(\n"
                + "select hotel_id from RoomType where type_id in\n"
                + "(Select type_id from RoomType\n"
                + "EXCEPT\n"
                + "Select type_id from RoomType Where type_id in (Select RoomType.type_id from BookingRoom \n"
                + "INNER JOIN RoomType ON BookingRoom.type_id = RoomType.type_id where booking_id in (Select booking_id from Booking \n"
                + "Where booking_status = 1) and ((date_checkin between ? and ?) or (date_checkout between ? and ?)) GROUP BY RoomType.type_id, RoomType.type_name, RoomType.type_price, RoomType.type_amount Having (type_amount - SUM(amount)) < ? )))";
        try {
            conn = MyConnection.getMyConnection();
            preStm = conn.prepareStatement(sql);
            if (area == -1) {
                preStm.setDate(1, checkin);
                preStm.setDate(2, checkout);
                preStm.setDate(3, checkin);
                preStm.setDate(4, checkout);
                preStm.setInt(5, numOfRoom);
            } else {
                preStm.setInt(1, area);
                preStm.setDate(2, checkin);
                preStm.setDate(3, checkout);
                preStm.setDate(4, checkin);
                preStm.setDate(5, checkout);
                preStm.setInt(6, numOfRoom);
            }

            rs = preStm.executeQuery();
            result = new ArrayList<>();
            while (rs.next()) {
                String id = rs.getString("hotel_id");
                String name = rs.getString("hotel_name");
                String address = rs.getString("hotel_address");
                String phone = rs.getString("hotel_phone");
                String photo = rs.getString("hotel_photo");
                String description = rs.getString("description");
                int areaID = rs.getInt("area_id");
                HotelDTO dto = new HotelDTO(id, name, address, phone, photo, description, areaID);
                result.add(dto);
            }
        } finally {
            closeConnection();
        }

        return result;
    }

    public List<HotelDTO> searchByGroupDefautl(Date checkin, Date checkout, int numOfRoom) throws NamingException, SQLException {
        List<HotelDTO> result = null;
        String sql = "Select hotel_id, hotel_name, hotel_address, hotel_phone, hotel_photo, area_id, description \n"
                + "From Hotel\n"
                + "Where hotel_id in(\n"
                + "select hotel_id from RoomType where type_id in\n"
                + "(Select type_id from RoomType\n"
                + "EXCEPT\n"
                + "Select type_id from RoomType Where type_id in (Select RoomType.type_id from BookingRoom \n"
                + "INNER JOIN RoomType ON BookingRoom.type_id = RoomType.type_id where booking_id in (Select booking_id from Booking \n"
                + "Where booking_status = 1 ) and ((date_checkin between ? and ?) or (date_checkout between ? and ?)) GROUP BY RoomType.type_id, RoomType.type_name, RoomType.type_price, RoomType.type_amount Having (type_amount - SUM(amount)) < ? )))";
        try {
            conn = MyConnection.getMyConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setDate(1, checkin);
            preStm.setDate(2, checkout);
            preStm.setDate(3, checkin);
            preStm.setDate(4, checkout);
            preStm.setInt(5, numOfRoom);
            rs = preStm.executeQuery();
            result = new ArrayList<>();
            while (rs.next()) {
                String id = rs.getString("hotel_id");
                String name = rs.getString("hotel_name");
                String address = rs.getString("hotel_address");
                String phone = rs.getString("hotel_phone");
                String photo = rs.getString("hotel_photo");
                String description = rs.getString("description");
                int areaID = rs.getInt("area_id");
                HotelDTO dto = new HotelDTO(id, name, address, phone, photo, description, areaID);
                result.add(dto);
            }
        } finally {
            closeConnection();
        }

        return result;
    }

    public HotelDTO getHotelByID(String hotelID) throws SQLException, NamingException {
        HotelDTO dto = null;
        String sql = "Select hotel_name, hotel_address, hotel_phone, hotel_photo, area_id, description From Hotel Where hotel_id = ? and hotel_status = 1";
        try {
            conn = MyConnection.getMyConnection();
            preStm = conn.prepareCall(sql);
            preStm.setString(1, hotelID);
            rs = preStm.executeQuery();
            if (rs.next()) {
                String name = rs.getString("hotel_name");
                String address = rs.getString("hotel_address");
                String photo = rs.getString("hotel_photo");
                String phone = rs.getString("hotel_phone");
                int areaId = rs.getInt("area_id");
                String description = rs.getString("description");
                dto = new HotelDTO(hotelID, name, address, phone, photo, description, areaId);
            }

        } finally {
            closeConnection();
        }
        return dto;
    }

}

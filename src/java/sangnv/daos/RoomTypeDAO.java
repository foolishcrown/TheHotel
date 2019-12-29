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
import sangnv.dtos.RoomTypeDTO;

/**
 *
 * @author Shang
 */
public class RoomTypeDAO implements Serializable {

    private Connection conn;
    private PreparedStatement preStm;
    private ResultSet rs;

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

    public RoomTypeDAO() {
    }

    public List<RoomTypeDTO> getListRoomByHotelIDCheckinCheckout(String hotelID, Date checkin, Date checkout) throws SQLException, NamingException {
        List<RoomTypeDTO> result = null;
        String sql = "Select RoomType.type_id, RoomType.type_name, RoomType.type_price, (type_amount - SUM( amount)) as Available  from BookingRoom \n"
                + "INNER JOIN RoomType ON BookingRoom.type_id = RoomType.type_id where hotel_id = ? and booking_id in (Select booking_id from Booking \n"
                + "Where booking_status = 1) and ((BookingRoom.date_checkin between ? and ?) or (BookingRoom.date_checkout between ? and ?)) GROUP BY RoomType.type_id, RoomType.type_name, RoomType.type_price, RoomType.type_amount\n"
                + "UNION\n"
                + "Select type_id, type_name,type_price, type_amount as Available from RoomType Where hotel_id = ? and type_id in\n"
                + "(Select type_id from RoomType\n"
                + "EXCEPT\n"
                + "Select RoomType.type_id from BookingRoom \n"
                + "INNER JOIN RoomType ON BookingRoom.type_id = RoomType.type_id where booking_id in (Select booking_id from Booking \n"
                + "Where booking_status = 1) and ((BookingRoom.date_checkin between ? and ?) or (BookingRoom.date_checkout between ? and ?)) ) ";
        try {
            conn = MyConnection.getMyConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setString(1, hotelID);
            preStm.setDate(2, checkin);
            preStm.setDate(3, checkout);
            preStm.setDate(4, checkin);
            preStm.setDate(5, checkout);
            preStm.setString(6, hotelID);
            preStm.setDate(7, checkin);
            preStm.setDate(8, checkout);
            preStm.setDate(9, checkin);
            preStm.setDate(10, checkout);
            result = new ArrayList<>();
            rs = preStm.executeQuery();
            while (rs.next()) {
                String id = rs.getString("type_id");
                String name = rs.getString("type_name");
                float price = rs.getFloat("type_price");
                int availableRoom = rs.getInt("Available");
                RoomTypeDTO dto = new RoomTypeDTO(id, name, hotelID, price);
                dto.setAvailableAmount(availableRoom);
                result.add(dto);
            }
        } finally {
            closeConnection();
        }
        return result;
    }

    public RoomTypeDTO getTypeByID(String id, Date checkin, Date checkout) throws SQLException, NamingException {
        RoomTypeDTO dto = null;
        String sql = "Select RoomType.hotel_id, RoomType.type_name, RoomType.type_price, (type_amount - SUM( amount)) as Available  from BookingRoom \n"
                + "INNER JOIN RoomType ON BookingRoom.type_id = RoomType.type_id where RoomType.type_id =? and booking_id in (Select booking_id from Booking \n"
                + "Where booking_status = 1) and ((BookingRoom.date_checkin between ? and ?) or (BookingRoom.date_checkout between ? and ?)) GROUP BY RoomType.type_id, RoomType.type_name, RoomType.type_price, RoomType.type_amount,RoomType.hotel_id\n"
                + "UNION\n"
                + "Select hotel_id, type_name,type_price, type_amount as Available from RoomType Where type_id = ? and type_id in\n"
                + "(Select type_id from RoomType\n"
                + "EXCEPT\n"
                + "Select RoomType.type_id from BookingRoom \n"
                + "INNER JOIN RoomType ON BookingRoom.type_id = RoomType.type_id where booking_id in (Select booking_id from Booking \n"
                + "Where booking_status = 1) and ((BookingRoom.date_checkin between ? and ?) or (BookingRoom.date_checkout between ? and ?)) ) ";
        try {
            conn = MyConnection.getMyConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setString(1, id);
            preStm.setDate(2, checkin);
            preStm.setDate(3, checkout);
            preStm.setDate(4, checkin);
            preStm.setDate(5, checkout);
            preStm.setString(6, id);
            preStm.setDate(7, checkin);
            preStm.setDate(8, checkout);
            preStm.setDate(9, checkin);
            preStm.setDate(10, checkout);
            rs = preStm.executeQuery();
            if (rs.next()) {
                String hotelID = rs.getString("hotel_id");
                String name = rs.getString("type_name");
                float price = rs.getFloat("type_price");
                int availableRoom = rs.getInt("Available");
                dto = new RoomTypeDTO(id, name, hotelID, price);
                dto.setAvailableAmount(availableRoom);
            }
        } finally {
            closeConnection();
        }
        return dto;
    }
    public  List<RoomTypeDTO> getListType() throws NamingException, SQLException{
        List<RoomTypeDTO> result = null;
        String sql = "Select type_id, type_name From RoomType";
        try {
            conn = MyConnection.getMyConnection();
            preStm = conn.prepareStatement(sql);
            result  = new ArrayList<>();
            rs = preStm.executeQuery();
            while (rs.next()) {
                String typeID = rs.getString("type_id");
                String typeName = rs.getString("type_name");
                RoomTypeDTO dto =new RoomTypeDTO(typeID, typeName, null, 0);
                result.add(dto);
            }
        }finally {
         closeConnection();
        }
        return result;
    }

}

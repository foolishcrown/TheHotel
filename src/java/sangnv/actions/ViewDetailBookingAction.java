/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sangnv.actions;

import com.opensymphony.xwork2.ActionSupport;
import java.sql.SQLException;
import java.util.List;
import javax.naming.NamingException;
import org.apache.log4j.Logger;
import sangnv.daos.BookingRoomDAO;
import sangnv.daos.RoomTypeDAO;
import sangnv.dtos.BookingRoomDTO;
import sangnv.dtos.RoomTypeDTO;

/**
 *
 * @author Shang
 */
public class ViewDetailBookingAction extends ActionSupport {

    private static final Logger LOGGER = Logger.getLogger(ViewDetailBookingAction.class);
    private int bookingID;
    private List<BookingRoomDTO> listDetails;
    private List<RoomTypeDTO> listRoomType;

    public List<RoomTypeDTO> getListRoomType() {
        return listRoomType;
    }

    public void setListRoomType(List<RoomTypeDTO> listRoomType) {
        this.listRoomType = listRoomType;
    }

    public int getBookingID() {
        return bookingID;
    }

    public void setBookingID(int bookingID) {
        this.bookingID = bookingID;
    }

    public List<BookingRoomDTO> getListDetails() {
        return listDetails;
    }

    public void setListDetails(List<BookingRoomDTO> listDetails) {
        this.listDetails = listDetails;
    }

    public ViewDetailBookingAction() {
    }

    public String viewDetail() throws Exception {
        try {
            BookingRoomDAO bookingRoomDAO = new BookingRoomDAO();
            RoomTypeDAO typeDAO = new RoomTypeDAO();
            listDetails = bookingRoomDAO.getDetailListByID(bookingID);
            listRoomType = typeDAO.getListType();
        } catch (SQLException | NamingException e) {
            LOGGER.error("Error at ViewDetialBookingAction: " + e.getMessage());
        }
        return "success";
    }

}

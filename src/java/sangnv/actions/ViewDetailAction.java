/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sangnv.actions;

import com.opensymphony.xwork2.ActionSupport;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import javax.naming.NamingException;
import org.apache.log4j.Logger;
import sangnv.daos.FeedbackDAO;
import sangnv.daos.HotelDAO;
import sangnv.daos.RoomTypeDAO;
import sangnv.dtos.FeedbackDTO;
import sangnv.dtos.HotelDTO;
import sangnv.dtos.RoomTypeDTO;

/**
 *
 * @author Shang
 */
public class ViewDetailAction extends ActionSupport {

    private static final Logger LOGGER = Logger.getLogger(ViewDetailAction.class);
    private String hotelID;
    private String checkin;
    private String checkout;
    private int areaID;
    private int numOfRoom;
    private HotelDTO dto;
    private List<RoomTypeDTO> listType;
    private List<FeedbackDTO> listFeedback;

    public List<FeedbackDTO> getListFeedback() {
        return listFeedback;
    }

    public void setListFeedback(List<FeedbackDTO> listFeedback) {
        this.listFeedback = listFeedback;
    }

    public List<RoomTypeDTO> getListType() {
        return listType;
    }

    public void setListType(List<RoomTypeDTO> listType) {
        this.listType = listType;
    }

    public HotelDTO getDto() {
        return dto;
    }

    public void setDto(HotelDTO dto) {
        this.dto = dto;
    }

    public String getHotelID() {
        return hotelID;
    }

    public void setHotelID(String hotelID) {
        this.hotelID = hotelID;
    }

    public String getCheckin() {
        return checkin;
    }

    public void setCheckin(String checkin) {
        this.checkin = checkin;
    }

    public String getCheckout() {
        return checkout;
    }

    public void setCheckout(String checkout) {
        this.checkout = checkout;
    }

    public int getAreaID() {
        return areaID;
    }

    public void setAreaID(int areaID) {
        this.areaID = areaID;
    }

    public int getNumOfRoom() {
        return numOfRoom;
    }

    public void setNumOfRoom(int numOfRoom) {
        this.numOfRoom = numOfRoom;
    }

    public ViewDetailAction() {
    }

    public String execute() throws Exception {
        try {
            HotelDAO hotelDAO = new HotelDAO();
            RoomTypeDAO roomtypeDAO = new RoomTypeDAO();
            dto = hotelDAO.getHotelByID(hotelID);
            if (checkin != null) {
                Date chkIn = Date.valueOf(checkin);
                Date chkOut = Date.valueOf(checkout);
                listType = roomtypeDAO.getListRoomByHotelIDCheckinCheckout(hotelID, chkIn, chkOut);
            }
            FeedbackDAO feedbackDAO = new FeedbackDAO();
            listFeedback = feedbackDAO.getListFeedBackByHotelID(hotelID);
        } catch (SQLException | NamingException e) {
            LOGGER.error("Error at ViewDetailAction" + e.getMessage());
        }
        return "success";
    }

}

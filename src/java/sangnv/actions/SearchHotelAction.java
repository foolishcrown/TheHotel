/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sangnv.actions;

import com.opensymphony.xwork2.ActionSupport;
import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import javax.naming.NamingException;
import org.apache.log4j.Logger;
import sangnv.daos.HotelDAO;
import sangnv.dtos.HotelDTO;

/**
 *
 * @author Shang
 */
public class SearchHotelAction extends ActionSupport {

    private static final Logger LOGGER = Logger.getLogger(SearchHotelAction.class);
    private String txtSearch;
    private String checkin;
    private String checkout;
    private int areaID;
    private int numOfRoom;

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
    private List<HotelDTO> listHotel;

    public String getTxtSearch() {
        return txtSearch;
    }

    public void setTxtSearch(String txtSearch) {
        this.txtSearch = txtSearch;
    }

    public List<HotelDTO> getListHotel() {
        return listHotel;
    }

    public void setListHotel(List<HotelDTO> listHotel) {
        this.listHotel = listHotel;
    }

    public SearchHotelAction() {
    }

    public String execute() {
        try {
            HotelDAO dao = new HotelDAO();
            if (txtSearch != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Calendar currCal = Calendar.getInstance();
                Calendar nextCal = Calendar.getInstance();
                nextCal.add(Calendar.DATE, 1);
                String currDate = sdf.format(currCal.getTime());
                String nextDate = sdf.format(nextCal.getTime());
                checkin = currDate;
                checkout = nextDate;
                listHotel = dao.searchHotelByName(txtSearch);
            } else if (checkin != null) {
                Date dayFrom = Date.valueOf(checkin);
                Date dayTo = Date.valueOf(checkout);
                listHotel = dao.searchByGroup(areaID, dayFrom, dayTo, numOfRoom);
            } else {
                listHotel = dao.getAllHotel();
            }
        } catch (SQLException | NamingException e) {
            LOGGER.error("Error at SearchHotelAction: " + e.getMessage());
        }
        return "success";
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sangnv.actions;

import com.opensymphony.xwork2.ActionSupport;
import java.sql.SQLException;
import javax.naming.NamingException;
import org.apache.log4j.Logger;
import sangnv.daos.BookingDAO;

/**
 *
 * @author Shang
 */
public class CancelBookingAction extends ActionSupport {

    private static final Logger LOGGER = Logger.getLogger(CancelBookingAction.class);
    private int bookingID;
    private String success;
    private String fail;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getFail() {
        return fail;
    }

    public void setFail(String fail) {
        this.fail = fail;
    }
    
    public int getBookingID() {
        return bookingID;
    }

    public void setBookingID(int bookingID) {
        this.bookingID = bookingID;
    }
    
    public CancelBookingAction() {
    }

    public String cancelBooking() throws Exception {
        try {
            BookingDAO dao = new BookingDAO();
            boolean check = dao.cancelBooking(bookingID);
            if (check) {
                success = "Cancel Success";
            }else{
                fail = "Booking Order Not Found";
            }
        } catch (SQLException | NamingException e) {
            LOGGER.error("Error at CancelBookingAction:" + e.getMessage());
        }
        return "success";
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sangnv.actions;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import javax.naming.NamingException;
import org.apache.log4j.Logger;
import sangnv.daos.BookingDAO;
import sangnv.dtos.AccountDTO;
import sangnv.dtos.BookingDTO;

/**
 *
 * @author Shang
 */
public class ViewHistoryAction extends ActionSupport {

    private static final Logger LOGGER = Logger.getLogger(ViewHistoryAction.class);

    private List<BookingDTO> listHistory;
    private String bookingDate;

    public String getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }

    public List<BookingDTO> getListHistory() {
        return listHistory;
    }

    public void setListHistory(List<BookingDTO> listHistory) {
        this.listHistory = listHistory;
    }

    public ViewHistoryAction() {
    }

    public String viewHistory() {
        try {
            BookingDAO bookingDAO = new BookingDAO();
            Map session = ActionContext.getContext().getSession();
            AccountDTO user = (AccountDTO) session.get("ACCOUNT");
            listHistory = bookingDAO.getListHistoryByCustomer(user.getEmail());
        } catch (SQLException | NamingException e) {
            LOGGER.error("Error at View History Aciton: " + e.getMessage());
        }
        return "success";
    }

    public String searchHistory() {
        try {
            if (bookingDate != null) {
                BookingDAO bookingDAO = new BookingDAO();
                Map session = ActionContext.getContext().getSession();
                AccountDTO user = (AccountDTO) session.get("ACCOUNT");
                Date bookingDay = Date.valueOf(bookingDate);
                listHistory = bookingDAO.getListHistoryByCustomerAndDate(user.getEmail(), bookingDay);
            }
        } catch (SQLException | NamingException e) {
            LOGGER.error("Error at SearchHistoryAction : " + e.getMessage());
        }

        return "success";
    }

}

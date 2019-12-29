/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sangnv.actions;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import java.sql.SQLException;
import java.util.Map;
import javax.naming.NamingException;
import org.apache.log4j.Logger;
import sangnv.daos.FeedbackDAO;
import sangnv.dtos.AccountDTO;
import sangnv.dtos.FeedbackDTO;

/**
 *
 * @author Shang
 */
public class FeedbackAction extends ActionSupport {

    private static final Logger LOGGER = Logger.getLogger(FeedbackAction.class);

    private String hotelID;
    private String txtFeedback;
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

    public String getHotelID() {
        return hotelID;
    }

    public void setHotelID(String hotelID) {
        this.hotelID = hotelID;
    }

    public String getTxtFeedback() {
        return txtFeedback;
    }

    public void setTxtFeedback(String txtFeedback) {
        this.txtFeedback = txtFeedback;
    }

    public FeedbackAction() {
    }

    public String execute() throws Exception {
        try {
            Map session = ActionContext.getContext().getSession();
            AccountDTO user = (AccountDTO) session.get("ACCOUNT");
            if (user != null) {
                String email = user.getEmail();
                FeedbackDTO dto = new FeedbackDTO(hotelID, email, txtFeedback, null);
                FeedbackDAO dao = new FeedbackDAO();
                boolean check = dao.sendFeedback(dto);
                if (check) {
                    success = "Sent successed";
                } else {
                    fail = "Sent failed, try again";
                }
            } else {
                fail = "Can't find your account, log in again!";
            }
        } catch (SQLException | NamingException e) {
            LOGGER.error("Error at FeedbackAction : " + e.getMessage());
        }

        return "success";
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sangnv.actions;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import java.sql.Date;
import java.util.Map;
import org.apache.log4j.Logger;
import sangnv.daos.RoomTypeDAO;
import sangnv.dtos.BookingCartDTO;
import sangnv.dtos.RoomTypeDTO;

/**
 *
 * @author Shang
 */
public class UpdateCartAction extends ActionSupport {

    private static final Logger LOGGER = Logger.getLogger(UpdateCartAction.class);
    private String success;
    private String fail;

    private String[] typeID;
    private String[] checkin;
    private String[] checkout;
    private String hotelID;
    private int[] amount;

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

    public String[] getTypeID() {
        return typeID;
    }

    public void setTypeID(String[] typeID) {
        this.typeID = typeID;
    }

    public String[] getCheckin() {
        return checkin;
    }

    public void setCheckin(String[] checkin) {
        this.checkin = checkin;
    }

    public String[] getCheckout() {
        return checkout;
    }

    public void setCheckout(String[] checkout) {
        this.checkout = checkout;
    }

   
    public String getHotelID() {
        return hotelID;
    }

    public void setHotelID(String hotelID) {
        this.hotelID = hotelID;
    }

    public int[] getAmount() {
        return amount;
    }

    public void setAmount(int[] amount) {
        this.amount = amount;
    }

    public UpdateCartAction() {
    }

    public String updateCart() {
        try {
            Map session = ActionContext.getContext().getSession();
            BookingCartDTO cart = (BookingCartDTO) session.get("CART");
            if (cart != null) {

                if (checkin != null && typeID != null) {
                   
                    RoomTypeDAO roomtypeDAO = new RoomTypeDAO();
                    boolean check = true;
                    for (int i = 0; i < typeID.length; i++) {
                        String dayFrom = checkin[i];
                        String dayTo = checkout[i];
                        Date chkIn = Date.valueOf(dayFrom);
                        Date chkOut = Date.valueOf(dayTo);
                        RoomTypeDTO dto = roomtypeDAO.getTypeByID(typeID[i], chkIn, chkOut);
                        if (dto != null) {
                            if (dto.getAvailableAmount() >= amount[i]) {
                                cart.updateCart(typeID[i], amount[i], dayFrom, dayTo);
                            } else {
                                check = false;
                            }
                        }else{
                            check = false;
                        }
                    }
                    if (check) {
                        session.put("CART", cart);
                        
                    } else {
                        fail = "Out of Stock";
                    }

                } else {
                    fail = "Null Values";
                }
            } else {
                fail = "Cart Not Found";
            }
        } catch (Exception e) {

            LOGGER.error("Error at UpdateCartAction: " + e.getMessage());
        }

        return "success";
    }
}

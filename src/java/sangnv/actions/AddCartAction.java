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
import sangnv.dtos.AccountDTO;
import sangnv.dtos.BookingCartDTO;
import sangnv.dtos.RoomTypeDTO;

/**
 *
 * @author Shang
 */
public class AddCartAction extends ActionSupport {

    private static final Logger LOGGER = Logger.getLogger(AddCartAction.class);
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

    private String typeID;
    private String checkin;
    private String checkout;
    private String hotelID;
    private int amount;

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getHotelID() {
        return hotelID;
    }

    public void setHotelID(String hotelID) {
        this.hotelID = hotelID;
    }

    public String getTypeID() {
        return typeID;
    }

    public void setTypeID(String typeID) {
        this.typeID = typeID;
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

    public AddCartAction() {
    }

    public String addCart() {

        try {
            Map session = ActionContext.getContext().getSession();
            AccountDTO user = (AccountDTO) session.get("ACCOUNT");
            BookingCartDTO cart = (BookingCartDTO) session.get("CART");
            if (user != null) {
                if (cart == null) {
                    cart = new BookingCartDTO(user.getEmail());
                }
                RoomTypeDAO roomtypeDAO = new RoomTypeDAO();
                if (checkin != null) {
                    Date chkIn = Date.valueOf(checkin);
                    Date chkOut = Date.valueOf(checkout);
                    RoomTypeDTO dto = roomtypeDAO.getTypeByID(typeID, chkIn, chkOut);
                    if (dto != null) {
                        int availableRoom = dto.getAvailableAmount();
                        if (availableRoom != 0) {
                            cart.addToCart(typeID, checkin, checkout);
                            session.put("CART", cart);
                            success = "Add Success";
                        } else {
                            fail = "Out of stock! try another date";
                        }
                    } else {
                        fail = "Your room is not exist!";
                    }
                } else {
                    fail = "Wrong Date taken, try again!";
                }
            } else {
                fail = "Account not found ! please login to add cart room.";
            }
        } catch (Exception e) {
            LOGGER.error("Error at AddCartAction: " + e.getMessage());
        }

        return "success";
    }

    public String removeCart() {
        try {
            Map session = ActionContext.getContext().getSession();
            BookingCartDTO cart = (BookingCartDTO) session.get("CART");
            if (cart != null) {
                cart.removeCart(typeID);
                session.put("CART", cart);
                success = "Remove Success";
            } else {
                fail = " Cart Not Found";
            }
        } catch (Exception e) {
            LOGGER.error("Error at RemoveCartAction : " + e.getMessage());
        }
        return "success";
    }

}

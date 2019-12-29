/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sangnv.actions;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import sangnv.daos.BookingDAO;
import sangnv.daos.BookingRoomDAO;
import sangnv.daos.DiscountDAO;
import sangnv.dtos.BookingCartDTO;
import sangnv.dtos.BookingDTO;
import sangnv.dtos.BookingRoomDTO;
import sangnv.dtos.RoomTypeDTO;

/**
 *
 * @author Shang
 */
public class ConfirmCartAction extends ActionSupport {

    private static final String SUCCESSFUL = "success";
    private static final String FAILURE = "fail";

    private static final Logger LOGGER = Logger.getLogger(ConfirmCartAction.class);
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

    public ConfirmCartAction() {
    }

    public String confirmCart() {
        String url = FAILURE;
        try {
            Map session = ActionContext.getContext().getSession();
            BookingCartDTO cart = (BookingCartDTO) session.get("CART");
            if (cart != null) {
                List<RoomTypeDTO> listCart = cart.getCartDetail();
                boolean checkValidCart = cart.checkValidConfirm(listCart);
                if (checkValidCart) {
                    BookingDAO bookingDAO = new BookingDAO();
                    String email = cart.getCustomerEmail();
                    String discountID = cart.getDiscountCode();
                    float total = cart.getTotal();
                    float discount = cart.getDiscount();
                    BookingDTO bookingDTO = new BookingDTO(email, discountID, total, discount);
                    boolean checkBooking = bookingDAO.confirmBookingOrder(bookingDTO);
                    if (checkBooking) {
                        DiscountDAO discountDAO = new DiscountDAO();
                        discountDAO.applyCode(discountID);
                        int bookingID = bookingDAO.getNewestBookingIDByCustomer(email);
                        if (bookingID != -1) {
                            List<Boolean> checkDetails = new ArrayList<>();
                            BookingRoomDAO bookingroomDAO = new BookingRoomDAO();
                            for (RoomTypeDTO roomTypeDTO : listCart) {
                                String typeID = roomTypeDTO.getId();
                                int amount = roomTypeDTO.getAmountCart();
                                float price = roomTypeDTO.getPrice();
                                String chkIn = roomTypeDTO.getCheckinCart();
                                String chkOut = roomTypeDTO.getCheckoutCart();
                                Date checkin = Date.valueOf(chkIn);
                                Date checkout = Date.valueOf(chkOut);
                                BookingRoomDTO bookingRoomDTO = new BookingRoomDTO(bookingID, typeID, amount, price);
                                bookingRoomDTO.setCheckin(checkin);
                                bookingRoomDTO.setCheckout(checkout);
                                
                                boolean detail = bookingroomDAO.confirmBookingDetail(bookingRoomDTO);
                                checkDetails.add(detail);
                            }
                            if (!checkDetails.contains(false)) {
                                success = "Booking Successfully, your booking ID is #" + bookingID;
                            } else {
                                fail = "Your booking was lost some resource but still success, your booking ID is #" + bookingID;
                            }
                            session.remove("CART");
                            url = SUCCESSFUL;
                        } else {
                            fail = "Can't found your booking request";
                        }

                    } else {
                        fail = "Something wrong when sending booking reuqest, try again!";
                    }
                } else {
                    fail = "Some room out of stock, try later";
                }
            } else {
                fail = "Cart Not Found";
            }

        } catch (Exception e) {
            LOGGER.error("Error at Confirm Cart Controller:" + e.getMessage());
        }
        return url;
    }

}

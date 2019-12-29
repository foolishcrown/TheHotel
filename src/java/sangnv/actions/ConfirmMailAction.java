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
import java.util.Random;
import org.apache.log4j.Logger;
import sangnv.daos.BookingDAO;
import sangnv.daos.BookingRoomDAO;
import sangnv.daos.DiscountDAO;
import sangnv.dtos.BookingCartDTO;
import sangnv.dtos.BookingDTO;
import sangnv.dtos.BookingRoomDTO;
import sangnv.dtos.MailSender;
import sangnv.dtos.RoomTypeDTO;

/**
 *
 * @author Shang
 */
public class ConfirmMailAction extends ActionSupport {

    private static final String SUCCESSFUL = "success";
    private static final String FAILURE = "fail";
    private static final Logger LOGGER = Logger.getLogger(ConfirmMailAction.class);
    private String success;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }
    private String confirmed;
    private String fail;
    private String confirmCode;

    public String getConfirmCode() {
        return confirmCode;
    }

    public void setConfirmCode(String confirmCode) {
        this.confirmCode = confirmCode;
    }

    public String getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(String confirmed) {
        this.confirmed = confirmed;
    }

    public String getFail() {
        return fail;
    }

    public void setFail(String fail) {
        this.fail = fail;
    }

    public ConfirmMailAction() {
    }

    public String sendMail() throws Exception {

        try {
            Map session = ActionContext.getContext().getSession();
            BookingCartDTO cart = (BookingCartDTO) session.get("CART");
            if (cart != null) {
                List<RoomTypeDTO> listCart = cart.getCartDetail();
                boolean checkValidCart = cart.checkValidConfirm(listCart);
                if (checkValidCart) {

                    Random rand = new Random();
                    String confirmCodes = "";
                    for (int i = 0; i < 6; i++) {
                        int randNum = rand.nextInt(10);
                        confirmCodes += randNum;
                    }
                    String toMail = cart.getCustomerEmail();
                    session.put("CODE", confirmCodes);
                    confirmed = "Please check your mail and input verify code here to confirm Booking Order";
                    MailSender.sendEmail(toMail, confirmCodes);
                } else {
                    fail = "Some room out of stock, try later";
                }
            } else {
                fail = "Cart Not Found";
            }

        } catch (Exception e) {
            LOGGER.error("Error at Confirm Mail Controller: " + e.getMessage());
            fail = "Can't connection to mail internet";
        }
        return "success";
    }

    public String acceptConfirm() {
        String url = FAILURE;
        try {
            Map session = ActionContext.getContext().getSession();
            String code = (String) session.get("CODE");
            BookingCartDTO cart = (BookingCartDTO) session.get("CART");
            if (code != null) {
                if (code.equals(confirmCode)) {
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
                    }
                }else{
                    confirmed  = "Wrong Code, try again";
                }
            }else{
                fail = "Code Not Found, Try again";
            }

        } catch (Exception e) {
            LOGGER.error("Error at Accept Confirm Code : " + e.getMessage());
        }

        return url;
    }

}

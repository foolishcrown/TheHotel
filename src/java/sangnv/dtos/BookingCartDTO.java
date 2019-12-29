/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sangnv.dtos;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import sangnv.daos.RoomTypeDAO;

/**
 *
 * @author Shang
 */
public class BookingCartDTO implements Serializable {

    private String customerEmail;
    private HashMap<String, Integer> cart;
    private float total;
    private String discountCode;
    private float discount;
    private HashMap<String, String> checkinDetails;
    private HashMap<String, String> checkoutDetails;

    public HashMap<String, String> getCheckinDetails() {
        return checkinDetails;
    }

    public void setCheckinDetails(HashMap<String, String> checkinDetails) {
        this.checkinDetails = checkinDetails;
    }

    public HashMap<String, String> getCheckoutDetails() {
        return checkoutDetails;
    }

    public void setCheckoutDetails(HashMap<String, String> checkoutDetails) {
        this.checkoutDetails = checkoutDetails;
    }

    public int getNumDay(String checkin, String checkout) throws Exception {
        Date chkIn = Date.valueOf(checkin);
        Date chkOut = Date.valueOf(checkout);
        return (int) (chkOut.getTime() - chkIn.getTime()) / (24 * 3600 * 1000);
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public HashMap<String, Integer> getCart() {
        return cart;
    }

    public void setCart(HashMap<String, Integer> cart) {
        this.cart = cart;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public String getDiscountCode() {
        return discountCode;
    }

    public void setDiscountCode(String discountCode) {
        this.discountCode = discountCode;
    }

    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }

    public BookingCartDTO() {
        this.customerEmail = "GUEST";
        this.cart = new HashMap<>();
    }

    public BookingCartDTO(String customerEmail) {
        this.customerEmail = customerEmail;
        this.cart = new HashMap<>();
        this.checkinDetails = new HashMap<>();
        this.checkoutDetails = new HashMap<>();
        this.discount = 0;
    }

    public void addToCart(String id, String checkin, String checkout) throws Exception {
        int amountCart = 1;
        if (this.cart.containsKey(id)) {
            amountCart = this.cart.get(id) + 1;
        }
        this.cart.put(id, amountCart);
        this.checkinDetails.put(id, checkin);
        this.checkoutDetails.put(id, checkout);
    }

    public void removeCart(String id) throws Exception {
        if (this.cart.containsKey(id)) {
            this.cart.remove(id);
        }
        if (this.checkinDetails.containsKey(id)) {
            this.checkinDetails.remove(id);
        }
        if (this.checkoutDetails.containsKey(id)) {
            this.checkoutDetails.remove(id);
        }
    }

    public void updateCart(String id, int amountCart, String checkin, String checkout) throws Exception {
        if (this.cart.containsKey(id)) {
            this.cart.put(id, amountCart);
        }
        if (this.checkinDetails.containsKey(id)) {
            this.checkinDetails.put(id, checkin);
        }
        if (this.checkoutDetails.containsKey(id)) {
            this.checkoutDetails.put(id, checkout);
        }
    }

    public List<RoomTypeDTO> getCartDetail() throws Exception {
        this.total = 0;
        List<RoomTypeDTO> result = new ArrayList<>();
        RoomTypeDAO roomtypeDAO = new RoomTypeDAO();
        for (String key : this.cart.keySet()) {
            String checkin = this.checkinDetails.get(key);
            String checkout = this.checkoutDetails.get(key);
            Date chkIn = Date.valueOf(checkin);
            Date chkOut = Date.valueOf(checkout);
            RoomTypeDTO dto = roomtypeDAO.getTypeByID(key, chkIn, chkOut);
            if (dto != null) {
                int amount = this.cart.get(key);
                if (dto.getAvailableAmount() >= amount) {
                    dto.setAmountCart(amount);
                } else {
                    dto.setAmountCart(dto.getAvailableAmount());
                }
                dto.setCheckinCart(checkin);
                dto.setCheckoutCart(checkout);
                dto.setNumDays(getNumDay(checkin, checkout));
                result.add(dto);
                this.total += dto.getAmountCart() * dto.getPrice() * dto.getNumDays();
            }
        }
        return result;
    }

    public boolean checkValidConfirm(List<RoomTypeDTO> listCart) throws Exception {
        boolean check = true;
        for (String string : this.cart.keySet()) {
            RoomTypeDTO dto = new RoomTypeDTO(string, null, null, 0);
            if (!listCart.contains(dto)) {
                removeCart(string);
                check = false;
            }
        }
        return check;
    }

}

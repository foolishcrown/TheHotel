/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sangnv.dtos;

import java.io.Serializable;
import java.sql.Date;

/**
 *
 * @author Shang
 */
public class BookingRoomDTO implements Serializable {
    
    private int bookingID;
    private String typeID;
    private String hotelID;
    private int amount;
    private float price; 
    private Date checkin;
    private Date checkout;

    public String getHotelID() {
        return typeID.substring(0, typeID.lastIndexOf("_"));
    }

    public void setHotelID(String hotelID) {
        this.hotelID = hotelID;
    }

    
    public Date getCheckin() {
        return checkin;
    }

    public void setCheckin(Date checkin) {
        this.checkin = checkin;
    }

    public Date getCheckout() {
        return checkout;
    }

    public void setCheckout(Date checkout) {
        this.checkout = checkout;
    }
    

    public int getBookingID() {
        return bookingID;
    }

    public void setBookingID(int bookingID) {
        this.bookingID = bookingID;
    }

    public String getTypeID() {
        return typeID;
    }

    public void setTypeID(String typeID) {
        this.typeID = typeID;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public BookingRoomDTO(int bookingID, String typeID, int amount, float price) {
        this.bookingID = bookingID;
        this.typeID = typeID;
        this.amount = amount;
        this.price = price;
    }

    public BookingRoomDTO() {
    }
    
}

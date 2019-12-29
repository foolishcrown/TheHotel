/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sangnv.dtos;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 *
 * @author Shang
 */
public class BookingDTO implements Serializable {

    private int bookingID;
    private String customer;
    private String discountID;
    private Timestamp bookingDate;
    private float total;
    private float discount;
    
    private boolean status;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public BookingDTO(int bookingID, String customer, String discountID, Timestamp bookingDate, float total, float discount) {
        this.bookingID = bookingID;
        this.customer = customer;
        this.discountID = discountID;
        this.bookingDate = bookingDate;
        this.total = total;
        this.discount = discount;
       
    }

    public BookingDTO(String customer, String discountID, float total, float discount) {
        this.customer = customer;
        this.discountID = discountID;
        this.total = total;
        this.discount = discount;
        
    }
    
    public BookingDTO() {
    }

    public String getDiscountID() {
        return discountID;
    }

    public void setDiscountID(String discountID) {
        this.discountID = discountID;
    }

    public BookingDTO(int bookingID, String customer, Timestamp bookingDate, float total, float discount) {
        this.bookingID = bookingID;
        this.customer = customer;
        this.bookingDate = bookingDate;
        this.total = total;
        this.discount = discount;
    }

    public BookingDTO(int bookingID, String customer, Timestamp bookingDate, float total) {
        this.bookingID = bookingID;
        this.customer = customer;
        this.bookingDate = bookingDate;
        this.total = total;
    }
     
    

    public int getBookingID() {
        return bookingID;
    }

    public void setBookingID(int bookingID) {
        this.bookingID = bookingID;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public Timestamp getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(Timestamp bookingDate) {
        this.bookingDate = bookingDate;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }

}

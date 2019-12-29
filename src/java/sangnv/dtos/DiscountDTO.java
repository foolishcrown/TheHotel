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
public class DiscountDTO implements Serializable {
    private String discountID;
    private float value;
    private boolean status; 
    private Date expiryDate;

    public String getDiscountID() {
        return discountID;
    }

    public void setDiscountID(String discountID) {
        this.discountID = discountID;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public DiscountDTO(String discountID, float value, boolean status, Date expiryDate) {
        this.discountID = discountID;
        this.value = value;
        this.status = status;
        this.expiryDate = expiryDate;
    }

    public DiscountDTO() {
    }
    
    
    
}

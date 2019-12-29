/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sangnv.dtos;

import java.util.Objects;

/**
 *
 * @author Shang
 */
public class RoomTypeDTO {
    private String id, name, hotelID;
    private float price;
    private int amount;
    private int availableAmount;
    private int amountCart;
    private String checkinCart;
    private String checkoutCart;
    private int numDays;

    public int getNumDays() {
        return numDays;
    }

    public void setNumDays(int numDays) {
        this.numDays = numDays;
    }

    public String getCheckinCart() {
        return checkinCart;
    }

    public void setCheckinCart(String checkinCart) {
        this.checkinCart = checkinCart;
    }

    public String getCheckoutCart() {
        return checkoutCart;
    }

    public void setCheckoutCart(String checkoutCart) {
        this.checkoutCart = checkoutCart;
    }

    
    
    public int getAmountCart() {
        return amountCart;
    }

    public void setAmountCart(int amountCart) {
        this.amountCart = amountCart;
    }
    

    public int getAvailableAmount() {
        return availableAmount;
    }

    public void setAvailableAmount(int availableAmount) {
        this.availableAmount = availableAmount;
    }
    
    public RoomTypeDTO() {
    }

    public RoomTypeDTO(String id, String name, String hotelID, float price) {
        this.id = id;
        this.name = name;
        this.hotelID = hotelID;
        this.price = price;
    }
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHotelID() {
        return hotelID;
    }

    public void setHotelID(String hotelID) {
        this.hotelID = hotelID;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final RoomTypeDTO other = (RoomTypeDTO) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    
    
   
}

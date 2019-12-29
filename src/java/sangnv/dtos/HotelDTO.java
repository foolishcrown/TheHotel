/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sangnv.dtos;

import java.io.Serializable;

/**
 *
 * @author Shang
 */
public class HotelDTO implements Serializable {
    
    private String id, name, address, phone, photo, description;
    private int areaId;
    private boolean status;

    public HotelDTO() {
    }

    public HotelDTO(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public HotelDTO(String id, String name, String address, String phone, String photo, String description, int areaId) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.photo = photo;
        this.description = description;
        this.areaId = areaId;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAreaId() {
        return areaId;
    }

    public void setAreaId(int areaId) {
        this.areaId = areaId;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
    
    
    
}

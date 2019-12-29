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
public class FeedbackDTO implements Serializable {

    private String hotelID;
    private String email;
    private String content;
    private Timestamp date;

    public FeedbackDTO() {
    }

    public FeedbackDTO(String hotelID, String email, String content, Timestamp date) {
        this.hotelID = hotelID;
        this.email = email;
        this.content = content;
        this.date = date;
    }

    public String getHotelID() {
        return hotelID;
    }

    public void setHotelID(String hotelID) {
        this.hotelID = hotelID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

}

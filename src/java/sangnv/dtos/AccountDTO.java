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
public class AccountDTO implements Serializable {
    
    private String email, password, phone, address;
    private int role;
    private Timestamp createDate;
    
    public AccountDTO() {
    }

    public AccountDTO(String email, int role) {
        this.email = email;
        this.role = role;
    }

    public AccountDTO(String email, String password, String phone, String address, int role) {
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.address = address;
        this.role = role;
    }

    
    

    public AccountDTO(String email, String password, String phone, String address, int role, Timestamp createDate) {
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.address = address;
        this.role = role;
        this.createDate = createDate;
    }

    public AccountDTO(String email, String password, int role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }
    
    
    
    
}

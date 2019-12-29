/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sangnv.actions;

import com.opensymphony.xwork2.ActionSupport;
import java.sql.SQLException;
import javax.naming.NamingException;
import org.apache.log4j.Logger;
import sangnv.daos.AccountDAO;
import sangnv.dtos.AccountDTO;

/**
 *
 * @author Shang
 */
public class RegisterAction extends ActionSupport {

    public static final Logger LOGGER = Logger.getLogger(RegisterAction.class);
    private String success;
    private String invalid;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getInvalid() {
        return invalid;
    }

    public void setInvalid(String invalid) {
        this.invalid = invalid;
    }

    private String email;
    private String password;
    private String address;
    private String phone;

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

    public RegisterAction() {
    }

    public String execute() {
        try {
            AccountDAO dao = new AccountDAO();
            if (email != null) {
                AccountDTO dto = new AccountDTO(email, password, phone, address, 1);
                boolean check = dao.registerAccount(dto);
                if (check) {
                    success = "Register Success, try to login!";
                } else {
                    invalid = "Register Failed";
                }
            }
        } catch (SQLException | NamingException e) {
            LOGGER.error("Error at Register Action: " + e.getMessage());
            if (e.getMessage().contains("duplicate")) {
                invalid = "Email existed.";
            }
        }
        
        return "success";
    }

}

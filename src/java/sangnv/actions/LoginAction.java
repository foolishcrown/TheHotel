/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sangnv.actions;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import javax.naming.NamingException;
import org.apache.log4j.Logger;
import sangnv.daos.AccountDAO;
import sangnv.daos.RoleDAO;
import sangnv.dtos.AccountDTO;
import sangnv.dtos.RoleDTO;

/**
 *
 * @author Shang
 */
public class LoginAction extends ActionSupport {

    private static final Logger LOGGER = Logger.getLogger(LoginAction.class);
    private static final String ADMIN = "admin";
    private static final String CUSTOMER = "customer";
    private static final String FAIL = "fail";
    private String email;
    private String password;
    private String invalid;

    public String getInvalid() {
        return invalid;
    }

    public void setInvalid(String invalid) {
        this.invalid = invalid;
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

    public LoginAction() {
    }

    public String login() {
        String url = FAIL;
        try {
            AccountDAO accDAO = new AccountDAO();
            String role = accDAO.checkRole(email, password);
            RoleDAO roleDAO = new RoleDAO();
            List<RoleDTO> listRole = roleDAO.getRoleList();
            if (role.equals("fail")) {
                invalid = "Wrong username or password!";
            } else {
                if (listRole.size() > 0) {
                    for (RoleDTO roleDTO : listRole) {
                        if (roleDTO.getRoleName().equals(role)) {
                            AccountDTO user = new AccountDTO(email, roleDTO.getRoleID());
                            if (role.equals("ADMIN")) {
                                url = ADMIN;
                            } else {
                                url = CUSTOMER;
                            }
                            Map session = ActionContext.getContext().getSession();
                            session.put("ACCOUNT", user);
                        }
                    }
                }
            }
        } catch (SQLException | NamingException e) {
            LOGGER.error("Error at LoginAction: " + e.getMessage());
        }
        return url;

    }

    public String logout() throws Exception {
        try {
            Map session = ActionContext.getContext().getSession();
            session.clear();

        } catch (Exception e) {
            LOGGER.error("Error at LogoutAction:" + e.getMessage());
        }
        return "success";

    }
    
    public String register(){
        
        
        return "success";
    }

}

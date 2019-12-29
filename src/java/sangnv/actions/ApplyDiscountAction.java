/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sangnv.actions;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import java.sql.SQLException;
import java.util.Map;
import javax.naming.NamingException;
import org.apache.log4j.Logger;
import sangnv.daos.DiscountDAO;
import sangnv.dtos.BookingCartDTO;
import sangnv.dtos.DiscountDTO;

/**
 *
 * @author Shang
 */
public class ApplyDiscountAction extends ActionSupport {
    private static final Logger LOGGER = Logger.getLogger(ApplyDiscountAction.class);
    private String success;
    private String fail;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getFail() {
        return fail;
    }

    public void setFail(String fail) {
        this.fail = fail;
    }
    
    private String discountCode;

    public String getDiscountCode() {
        return discountCode;
    }

    public void setDiscountCode(String discountCode) {
        this.discountCode = discountCode;
    }
    
    
    public ApplyDiscountAction() {
    }
    
    public String execute() throws Exception {
        try {
            DiscountDAO discountDAO = new DiscountDAO();
            DiscountDTO discountDTO = discountDAO.getCodeByID(discountCode);
            if (discountDTO != null) {
                boolean checkValid = discountDAO.checkValidCode(discountDTO);
                if (checkValid) {
                    Map session = ActionContext.getContext().getSession();
                    BookingCartDTO cart = (BookingCartDTO) session.get("CART");
                    if (cart != null) {
                        cart.setDiscountCode(discountCode);
                        cart.setDiscount(discountDTO.getValue());
                        session.put("CART", cart);
                        success = "Apply Code successed";
                    }else{
                        fail = "Cart Not Found";
                    }
                    
                }else{
                    fail = "Code was used or over expiry date";
                }
                
            }else{
                fail = "Your code is not exist";
            }
            
            
        } catch (SQLException | NamingException e) {
            LOGGER.error("Error at ApplyDiscountAction :" + e.getMessage());
        }
        
        return "success";
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sangnv.actions;

import com.opensymphony.xwork2.ActionSupport;
import java.sql.SQLException;
import java.util.List;
import javax.naming.NamingException;
import org.apache.log4j.Logger;
import sangnv.daos.HotelDAO;
import sangnv.dtos.HotelDTO;

/**
 *
 * @author Shang
 */
public class AdminLoadAction extends ActionSupport {

    private static final Logger LOGGER = Logger.getLogger(AdminLoadAction.class);

    private List<HotelDTO> listHotel;

    public List<HotelDTO> getListHotel() {
        return listHotel;
    }

    public void setListHotel(List<HotelDTO> listHotel) {
        this.listHotel = listHotel;
    }

    
    
    public AdminLoadAction() {
    }

    public String execute() throws Exception {
        try {
            HotelDAO dao = new HotelDAO();
            listHotel = dao.searchHotelByName("");
            
        } catch (SQLException | NamingException e) {
            LOGGER.error("Error at AdminLoadAction : " + e.getMessage());
        }
        
        return "success";
    }

}

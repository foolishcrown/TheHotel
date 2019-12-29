/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sangnv.actions;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import javax.naming.NamingException;
import org.apache.log4j.Logger;
import sangnv.daos.AreaDAO;
import sangnv.daos.HotelDAO;
import sangnv.dtos.AreaDTO;
import sangnv.dtos.HotelDTO;

/**
 *
 * @author Shang
 */
public class LoadAction extends ActionSupport {
    
    private static final Logger LOGGER = Logger.getLogger(LoadAction.class);
    private List<HotelDTO> listHotel;
    private Date checkin;
    private Date checkout;
    
    public Date getCheckin() {
        return checkin;
    }
    
    public void setCheckin(Date checkin) {
        this.checkin = checkin;
    }
    
    public Date getCheckout() {
        return checkout;
    }
    
    public void setCheckout(Date checkout) {
        this.checkout = checkout;
    }
    
    public List<HotelDTO> getListHotel() {
        return listHotel;
    }
    
    public void setListHotel(List<HotelDTO> listHotel) {
        this.listHotel = listHotel;
    }
    
    public LoadAction() {
    }
    
    public String execute()  {
        try {
            HotelDAO dao = new HotelDAO();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Calendar currCal = Calendar.getInstance();
            Calendar nextCal = Calendar.getInstance();
            nextCal.add(Calendar.DATE, 1);
            String currDate = sdf.format(currCal.getTime());
            String nextDate = sdf.format(nextCal.getTime());
            checkin = Date.valueOf(currDate);
            checkout = Date.valueOf(nextDate);
            listHotel = dao.searchByGroupDefautl(checkin, checkout, 1);
            AreaDAO areaDAO = new AreaDAO();
            List<AreaDTO> listArea = areaDAO.getListArea();
            List<HotelDTO> sessionHotel = dao.getAllHotel();
            Map session = ActionContext.getContext().getSession();
            session.put("AREAS", listArea);
            session.put("HOTELS", sessionHotel);
        } catch (SQLException | NamingException e) {
            LOGGER.error("Error at Load Action:" + e.getMessage());
        }
        
        return "success";
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sangnv.daos;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import sangnv.conn.MyConnection;
import sangnv.dtos.AreaDTO;

/**
 *
 * @author Shang
 */
public class AreaDAO implements Serializable {

    public AreaDAO() {
    }
    
    public List<AreaDTO> getListArea() throws NamingException, SQLException{
        List<AreaDTO> result;
        String sql = "Select area_id, area_name From Area";
        try(Connection conn = MyConnection.getMyConnection();
                PreparedStatement preStm = conn.prepareStatement(sql)){
            result = new ArrayList<>();
            try(ResultSet rs = preStm.executeQuery()){
                while (rs.next()) {
                    int areaID = rs.getInt("area_id");
                    String areaName = rs.getString("area_name");
                    AreaDTO dto = new AreaDTO(areaID, areaName);
                    result.add(dto);
                }
            }
        } 
        return result;
    }
    
}

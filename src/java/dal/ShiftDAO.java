/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;
import entity.Shift;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author DO NGOC ANH HE180661
 */
public class ShiftDAO {
    public List<Shift> getAllShift(){
        List<Shift> list = new ArrayList<>();
        
        String query = "select * from Shiftlearn";
        try {
            Connection conn = new DBContext().connection;
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                list.add(new Shift(rs.getInt("ShiftID"),
                        rs.getTime("Start_time"),
                         rs.getTime("End_time")));
                
               
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    public static void main(String[] args) {
        ShiftDAO sd = new ShiftDAO();
        List<Shift> listshift = new ArrayList<Shift>();
        listshift = sd.getAllShift();
        for (Shift shift : listshift) {
            System.out.println(shift);
        }
    }
}

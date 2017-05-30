package daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.AvailableRequests;

public class AvailableRequestsDao extends ConnectionDao // extend the connection dao

{


 private AvailableRequests populateEvent(ResultSet rs) throws SQLException 
    {
        AvailableRequests event = new AvailableRequests();
        
         event.setRequestID(rs.getInt("REQUEST_ID"));
         event.setRequestFrom(rs.getString("REQUEST_FROM"));
         event.setRequestTo(rs.getString("REQUEST_TO"));
         event.setName(rs.getString("PASSENGER_NAME"));
         event.setPhone(rs.getString("PASSENGER_PHONE"));
         event.setLeavingTime(rs.getString("LEAVING_TIME"));
         return event;
    }    
    
   public void insertrequest(AvailableRequests request) throws Exception
   {                
        System.out.println("Connected to the dao :) ");
        
        try 
        {
            Connection conn = getConnection();
            
            String sql =
                      "INSERT INTO AVALIABLE_REQUESTS "
                    + "( REQUEST_ID,"
                    + " REQUEST_FROM,"
                    + " REQUEST_TO,"
                    + " PASSENGER_NAME,"
                    + " PASSENGER_PHONE,"
                    + " LEAVING_TIME,"
                    + "TIME_STAMP,"
                    + " VALUES ((select max(REQUEST_ID) from AVAILABLE_REQUESTS)+1,?,?,?,?,?)";
            
            
            PreparedStatement ps = conn.prepareStatement(sql); 
            
            ps.setString(1, request.getRequestFrom());
            ps.setString(2, request.getRequestTo());
            ps.setString(3, request.getName());
            ps.setString(4, request.getPhone());
            ps.setString(5, request.getLeavingTime());
            
            ps.executeUpdate();
            
            ps.close();
        } 
        catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }
   
      public void deleteEvent(int PASSENGER_ID) throws Exception
      
      {
          
        Connection conn = getConnection();
        
        try 
        {
                String sql = "DELETE FROM AVAILABLE_REQUESTS WHERE REQUEST_ID=?";                               
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setInt(1, PASSENGER_ID);

                ps.executeUpdate();

                ps.close();
          } 
       
                catch (SQLException e) 
             {
                throw new SQLException(e.getMessage());
             }
        }   
   
   
     public void updateevent(AvailableRequests event) throws Exception 
     {
        try 
        {
            Connection conn = getConnection();

            String sql = "UPDATE AVAILABLE_REQUESTS SET "
                    + "(REQUEST_FROM=?,"
                    + " REQUEST_TO=?,"
                    + " PASSENGER_NAME=?,"
                    + " PASSENGER_PHONE=?,"
                    + " LEAVING_TIME=?"
                    + " WHERE REQUEST_ID=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            
           ps.setString(1, event.getRequestFrom());
            ps.setString(2, event.getRequestTo());
            ps.setString(3, event.getName());
            ps.setString(4, event.getPhone());
            ps.setString(5, event.getLeavingTime());            
            ps.setInt(6, event.getRequestID());

            ps.executeUpdate();
            
            ps.close();
        } 
            catch (SQLException e) 
            {
               throw new SQLException(e.getMessage());
        
            }
    }
   
   
    public ArrayList<AvailableRequests> buildevents() throws Exception 
    
    {
                
        ArrayList<AvailableRequests> list = new ArrayList<>();
        try 
        {   
            Connection conn = getConnection();
            
            String sql = "SELECT * FROM AVAILABLE_REQUESTS";          // select all avaliable requests               
            PreparedStatement ps = conn.prepareStatement(sql);            

            ResultSet rs = ps.executeQuery();           

            while (rs.next()) {
                list.add(populateEvent(rs));
            }
            
            rs.close();
            ps.close();
            
            return list;            
        } 
        catch (SQLException e)
        {
            throw new SQLException(e.getMessage());
        }
    }

   
    
    public static void main(String [] args)
    {        
        try 
        {
            AvailableRequests dao = new AvailableRequests();                
        } 
        
        catch (Exception ex) 
        {
            Logger.getLogger(AvailableRequests.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
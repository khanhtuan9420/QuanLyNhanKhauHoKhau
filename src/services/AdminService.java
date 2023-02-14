package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import models.UsersModel;


public class AdminService {
	
	// ckecked
	public  boolean add(UsersModel usersModel) throws ClassNotFoundException, SQLException {
		Connection connection = MysqlConnection.getMysqlConnection();
        String query = "INSERT INTO users(id, name, username, passwd, role)" 
                    + " values (?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setInt(1,usersModel.getId());
		preparedStatement.setString(2,usersModel.getName());
        preparedStatement.setString(3, usersModel.getUsername());
        preparedStatement.setString(4, usersModel.getPasswd());
        preparedStatement.setInt(5, usersModel.getRole());
        preparedStatement.executeUpdate();
        preparedStatement.close();
        connection.close();
		return true;
	}
	
	// checked
	public boolean del(int id) throws ClassNotFoundException, SQLException {
		Connection connection = MysqlConnection.getMysqlConnection();
		String query = "SELECT * FROM users WHERE id='"+id+"';";
	    PreparedStatement preparedStatement = (PreparedStatement)connection.prepareStatement(query);
	    ResultSet rs = preparedStatement.executeQuery();
	    while (rs.next()){
	    	query="DELETE FROM users WHERE id='"+id+"'";
	    	preparedStatement = connection.prepareStatement(query);
	    	preparedStatement.executeUpdate();
	    }
		preparedStatement.close();
	    connection.close();
		return true;  
	}
	
	public boolean update(int id,String password) throws ClassNotFoundException, SQLException {
		Connection connection = MysqlConnection.getMysqlConnection();
		PreparedStatement preparedStatement;

		String query = "UPDATE users " + "set passwd= '"+password+"'"+" where id =" + id;
		preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
		preparedStatement.executeUpdate();
		preparedStatement.close();
		connection.close();
		
		
		return true;
	}
	
	// checked
	public List<UsersModel> getListUsers() throws ClassNotFoundException, SQLException{
		List<UsersModel> list = new ArrayList<>();
		
		Connection connection = MysqlConnection.getMysqlConnection();
	    String query = "SELECT * FROM users";
	    PreparedStatement preparedStatement = (PreparedStatement)connection.prepareStatement(query);
	    ResultSet rs = preparedStatement.executeQuery();
	    while (rs.next()){
	        UsersModel usersModel = new UsersModel(rs.getInt("ID"),rs.getString("name"), rs.getString("username"),
	        		rs.getString("passwd"),rs.getInt("role"));
	        list.add(usersModel);
	   }
	    preparedStatement.close();
	    connection.close();
		return list;
	}
	
}

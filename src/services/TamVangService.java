package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import models.TamVangModel;

public class TamVangService {

	// checked
	public boolean add(TamVangModel tamVangModel) throws ClassNotFoundException, SQLException {

		Connection connection = MysqlConnection.getMysqlConnection();
		String query = "INSERT INTO tam_vang(id, nhan_khau_id, diadiem, tu_ngay, den_ngay, ly_do)" + " values (?, ?, ?, ?, ?,?)";
		PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
		preparedStatement.setInt(1, tamVangModel.getId());
		preparedStatement.setInt(2, tamVangModel.getNhan_khau_id());
		preparedStatement.setString(3, tamVangModel.getDiadiem());
		preparedStatement.setString(4, tamVangModel.getFrom());
		preparedStatement.setString(5, tamVangModel.getTo());
		preparedStatement.setString(6, tamVangModel.getLydo());
		preparedStatement.executeUpdate();
		preparedStatement.close();
		connection.close();
		return true;
	}

	public boolean del(int ID) throws ClassNotFoundException, SQLException {
		Connection connection = MysqlConnection.getMysqlConnection();
		String query = "SELECT * FROM tam_vang WHERE nhan_khau_id='" + ID + "';";
		PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(query);
		ResultSet rs = preparedStatement.executeQuery();
		while (rs.next()) {
			query = "DELETE FROM tam_vang WHERE nhan_khau_id='" + ID + "'";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.executeUpdate();
		}

		query = "SELECT * FROM quan_he WHERE quan_he.IDThanhVien='" + ID + "';";
		preparedStatement = (PreparedStatement) connection.prepareStatement(query);
		rs = preparedStatement.executeQuery();
		while (rs.next()) {
			query = "DELETE FROM quan_he WHERE IDThanhVien='" + ID + "'";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.executeUpdate();
		}
		query = "DELETE FROM nhan_khau WHERE ID = '" + ID + "'";
		preparedStatement = connection.prepareStatement(query);
		preparedStatement.executeUpdate();
		preparedStatement.close();
		connection.close();
		return true;
	}


    // checked
	public List<TamVangModel> getListTamVang() throws ClassNotFoundException, SQLException {
		List<TamVangModel> list = new ArrayList<>();

		Connection connection = MysqlConnection.getMysqlConnection();
		String query = "SELECT * FROM tam_vang";
		PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(query);
		ResultSet rs = preparedStatement.executeQuery();
		while (rs.next()) {
			TamVangModel tamVangModel = new TamVangModel(rs.getInt("id"), rs.getInt("nhan_khau_id"), rs.getString("diadiem"), rs.getString("tu_ngay"),
					rs.getString("den_ngay"), rs.getString("ly_do"));
			list.add(tamVangModel);
		}
		preparedStatement.close();
		connection.close();
		return list;
	}
}

package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.NhanKhauModel;
import models.TamTruModel;

public class TamTruService {

	// checked
	public boolean add(TamTruModel tamtruModel) throws ClassNotFoundException, SQLException {

		Connection connection = MysqlConnection.getMysqlConnection();
		String query = "INSERT INTO tam_tru(id, chu_ho_id, tu_ngay)" + " values (?, ?, ?)";
		PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
		preparedStatement.setInt(1, tamtruModel.getId());
		preparedStatement.setInt(2, tamtruModel.getChu_ho_id());
		preparedStatement.setString(3, tamtruModel.getFrom());
		preparedStatement.executeUpdate();
		preparedStatement.close();
		connection.close();
		return true;
	}

	public boolean del(int ID) throws ClassNotFoundException, SQLException {
		Connection connection = MysqlConnection.getMysqlConnection();
		String query = "SELECT * FROM tam_tru WHERE id='" + ID + "';";
		PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(query);
		ResultSet rs = preparedStatement.executeQuery();
		while (rs.next()) {
			query = "DELETE FROM tam_tru WHERE id='" + ID + "'";
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
	public List<NhanKhauModel> getListTamTru() throws ClassNotFoundException, SQLException {
		List<NhanKhauModel> list = new ArrayList<>();

		Connection connection = MysqlConnection.getMysqlConnection();
		String query = "SELECT a.id, a.CMND, a.Ten, a.Ngay_sinh, a.gioi_tinh FROM nhan_khau a, ho_khau b, chu_ho c where c.IDChuHo = a.ID and c.MaHo=b.MaHo;";
		PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(query);
		ResultSet rs = preparedStatement.executeQuery();
		while (rs.next()) {
			NhanKhauModel nhanKhauModel = new NhanKhauModel(rs.getInt("id"), rs.getString("CMND"),
					 rs.getString("Ten"), rs.getString("Ngay_sinh"), rs.getString("gioi_tinh"));
			list.add(nhanKhauModel);
		}
		preparedStatement.close();
		connection.close();
		return list;
	}
}

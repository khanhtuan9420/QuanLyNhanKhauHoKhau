package services;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import models.NhanKhauModel;

public class NhanKhauService {
	private List<Integer> listNgay_sinh = new ArrayList<>();

	// checked
	public boolean add(NhanKhauModel nhanKhauModel) throws ClassNotFoundException, SQLException {
		Date date = Date.valueOf(nhanKhauModel.getNgaysinh());
		Connection connection = MysqlConnection.getMysqlConnection();
		String query = "INSERT INTO nhan_khau(ID, CMND, Ten, Ngay_sinh, gioi_tinh)" + " values (?, ?, ?, ?, ?)";
		PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
		preparedStatement.setInt(1, nhanKhauModel.getId());
		preparedStatement.setString(2, nhanKhauModel.getCmnd());
		preparedStatement.setString(3, nhanKhauModel.getTen());
		preparedStatement.setDate(4, date);
		preparedStatement.setString(5, nhanKhauModel.getSex());
		preparedStatement.executeUpdate();
		preparedStatement.close();
		connection.close();
		return true;
	}

	// checked
	public boolean del(int ID) throws ClassNotFoundException, SQLException {
		Connection connection = MysqlConnection.getMysqlConnection();
		String query = "SELECT * FROM nop_tien WHERE IDNopTien='" + ID + "';";
		PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(query);
		ResultSet rs = preparedStatement.executeQuery();
		while (rs.next()) {
			query = "DELETE FROM nop_tien WHERE IDNopTien='" + ID + "'";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.executeUpdate();
		}

		query = "SELECT * FROM tam_vang WHERE nhan_khau_id='" + ID + "';";
		preparedStatement = (PreparedStatement) connection.prepareStatement(query);
		rs = preparedStatement.executeQuery();
		while (rs.next()) {
			query = "DELETE FROM tam_vang WHERE nhan_khau_id='" + ID + "'";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.executeUpdate();
		}

		query = "SELECT * FROM tam_tru WHERE id='" + ID + "';";
		preparedStatement = (PreparedStatement) connection.prepareStatement(query);
		rs = preparedStatement.executeQuery();
		while (rs.next()) {
			query = "DELETE FROM tam_tru WHERE id='" + ID + "'";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.executeUpdate();
		}

		query = "SELECT * FROM chu_ho WHERE chu_ho.IDChuHo='" + ID + "';";
		preparedStatement = (PreparedStatement) connection.prepareStatement(query);
		rs = preparedStatement.executeQuery();
		while (rs.next()) {
			query = "DELETE FROM chu_ho WHERE IDChuHo='" + ID + "'";
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

	public boolean update(int id, String cmnd, String ten, String ngaysinh, String sex)
			throws ClassNotFoundException, SQLException {
		Connection connection = MysqlConnection.getMysqlConnection();
		PreparedStatement preparedStatement;
		System.out.println(ngaysinh);
		String query = "UPDATE nhan_khau " + "set CMND =" + "'" + cmnd + "'," + "Ten =" + "'" + ten + "'," + "Ngay_sinh = '"
				+ ngaysinh + "'," + "gioi_tinh =" + "'" + sex + "' where ID =" + id;
		preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
		preparedStatement.executeUpdate();
		preparedStatement.close();
		connection.close();
		return true;
	}

	public List<Integer> getTuoi() throws ClassNotFoundException, SQLException {
		Connection connection = MysqlConnection.getMysqlConnection();
		String query = "select count(a.ID) tong from nhan_khau a where year(now())-year(a.Ngay_sinh)<6";
		PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(query);
		ResultSet rs = preparedStatement.executeQuery();
		rs.next();
		listNgay_sinh.add(rs.getInt("tong"));
		query = "select count(a.ID) tong from nhan_khau a where year(now())-year(a.Ngay_sinh) < 18";
		preparedStatement = (PreparedStatement) connection.prepareStatement(query);
		rs = preparedStatement.executeQuery();
		rs.next();
		listNgay_sinh.add(rs.getInt("tong"));
		query = "select count(a.ID) tong from nhan_khau a where year(now())-year(a.Ngay_sinh) >=15 and "+
				"( (a.gioi_tinh =\"Nam\" and year(now())-year(a.Ngay_sinh) <= 60) or (a.gioi_tinh =\"Nữ\" and year(now())-year(a.Ngay_sinh)<=55) )";

		preparedStatement = (PreparedStatement) connection.prepareStatement(query);
		rs = preparedStatement.executeQuery();
		rs.next();
		listNgay_sinh.add(rs.getInt("tong"));
		query = "select count(a.ID) tong from nhan_khau a where " +
		"(a.gioi_tinh =\"Nam\" and year(now())-year(a.Ngay_sinh) > 60) or (a.gioi_tinh =\"Nữ\" and year(now())-year(a.Ngay_sinh)>55)";
		preparedStatement = (PreparedStatement) connection.prepareStatement(query);
		rs = preparedStatement.executeQuery();
		rs.next();
		listNgay_sinh.add(rs.getInt("tong"));
		preparedStatement.close();
		connection.close();
		return listNgay_sinh;
	}

	public int getSoNam() throws ClassNotFoundException, SQLException {
		Connection connection = MysqlConnection.getMysqlConnection();
		String query = "select count(a.gioi_tinh) tong from nhan_khau a where a.gioi_tinh=\"Nam\"";
		PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(query);
		ResultSet rs = preparedStatement.executeQuery();
		rs.next();
		int res = rs.getInt("tong");
		preparedStatement.close();
		connection.close();
		return res;
	}


	public int getSoNhanKhau() throws ClassNotFoundException, SQLException {
		Connection connection = MysqlConnection.getMysqlConnection();
		String query = "select COUNT(a.ID) tong from nhan_khau a;";
		PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(query);
		ResultSet rs = preparedStatement.executeQuery();
		rs.next();
		int res = rs.getInt("tong");
		preparedStatement.close();
		connection.close();
		return res;
	}

	public int getSoTamVang() throws ClassNotFoundException, SQLException {
		Connection connection = MysqlConnection.getMysqlConnection();
		String query = "select COUNT(a.ID) tong from tam_vang a;";
		PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(query);
		ResultSet rs = preparedStatement.executeQuery();
		rs.next();
		int res = rs.getInt("tong");
		preparedStatement.close();
		connection.close();
		return res;
	}

	public int getSoTamTru() throws ClassNotFoundException, SQLException {
		Connection connection = MysqlConnection.getMysqlConnection();
		String query = "select COUNT(a.ID) tong from tam_tru a;";
		PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(query);
		ResultSet rs = preparedStatement.executeQuery();
		rs.next();
		int res = rs.getInt("tong");
		preparedStatement.close();
		connection.close();
		return res;
	}


	public List<NhanKhauModel> getListNhanKhauKhongVang() throws ClassNotFoundException, SQLException {
		List<NhanKhauModel> list = new ArrayList<>();

		Connection connection = MysqlConnection.getMysqlConnection();
		String query = "SELECT distinct a.* FROM nhan_khau a, tam_vang b where b.nhan_khau_id != a.ID;";
		PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(query);
		ResultSet rs = preparedStatement.executeQuery();
		while (rs.next()) {
			NhanKhauModel nhanKhauModel = new NhanKhauModel(rs.getInt("ID"), rs.getString("CMND"), rs.getString("Ten"),
					rs.getDate("Ngay_sinh").toString(), rs.getString("gioi_tinh"));
			list.add(nhanKhauModel);
		}
		query ="SELECT a.* FROM nhan_khau a, tam_vang b where b.nhan_khau_id = a.ID and b.den_ngay < CURRENT_DATE();";
		preparedStatement = (PreparedStatement) connection.prepareStatement(query);
		rs = preparedStatement.executeQuery();
		while (rs.next()) {
			NhanKhauModel nhanKhauModel = new NhanKhauModel(rs.getInt("ID"), rs.getString("CMND"), rs.getString("Ten"),
					rs.getDate("Ngay_sinh").toString(), rs.getString("gioi_tinh"));
			list.add(nhanKhauModel);
		}
		preparedStatement.close();
		connection.close();
		return list;
	}


	public List<NhanKhauModel> getListNhanKhauVang() throws ClassNotFoundException, SQLException {
		List<NhanKhauModel> list = new ArrayList<>();

		Connection connection = MysqlConnection.getMysqlConnection();
		String query = "SELECT a.id, a.Ten, a.CMND, a.Ngay_sinh, a.gioi_tinh, b.tu_ngay, b.den_ngay FROM nhan_khau a, tam_vang b where b.nhan_khau_id = a.ID;";
		PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(query);
		ResultSet rs = preparedStatement.executeQuery();
		while (rs.next()) {
			String fromValue=rs.getDate("tu_ngay").toString();
			String toValue=rs.getDate("den_ngay").toString();
			NhanKhauModel nhanKhauModel = new NhanKhauModel(rs.getInt("ID"), rs.getString("CMND"), rs.getString("Ten"),
					rs.getDate("Ngay_sinh").toString(), rs.getString("gioi_tinh"), fromValue,toValue);

			list.add(nhanKhauModel);
		}
		preparedStatement.close();
		connection.close();
		return list;
	}

	public List<NhanKhauModel> getListNhanKhauTamTru() throws ClassNotFoundException, SQLException {
		List<NhanKhauModel> list = new ArrayList<>();

		Connection connection = MysqlConnection.getMysqlConnection();
		String query = "SELECT a.id, a.Ten, a.CMND, a.Ngay_sinh, a.gioi_tinh, b.tu_ngay FROM nhan_khau a, tam_tru b where b.id=a.ID;";
		PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(query);
		ResultSet rs = preparedStatement.executeQuery();
		while (rs.next()) {
			String fromValue=rs.getDate("tu_ngay").toString();
			String toValue="";
			NhanKhauModel nhanKhauModel = new NhanKhauModel(rs.getInt("ID"), rs.getString("CMND"), rs.getString("Ten"),
					rs.getDate("Ngay_sinh").toString(), rs.getString("gioi_tinh"), fromValue,toValue);

			list.add(nhanKhauModel);
		}
		preparedStatement.close();
		connection.close();
		return list;
	}


	// checked
	public List<NhanKhauModel> getListNhanKhau() throws ClassNotFoundException, SQLException {
		List<NhanKhauModel> list = new ArrayList<>();

		Connection connection = MysqlConnection.getMysqlConnection();
		String query = "SELECT * FROM nhan_khau";
		PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(query);
		ResultSet rs = preparedStatement.executeQuery();
		while (rs.next()) {
			NhanKhauModel nhanKhauModel = new NhanKhauModel(rs.getInt("ID"), rs.getString("CMND"), rs.getString("Ten"),
					rs.getDate("Ngay_sinh").toString(), rs.getString("gioi_tinh"));
			list.add(nhanKhauModel);
		}
		preparedStatement.close();
		connection.close();
		return list;
	}

}

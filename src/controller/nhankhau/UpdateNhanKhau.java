package controller.nhankhau;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import models.NhanKhauModel;
import services.NhanKhauService;

public class UpdateNhanKhau implements Initializable {
	private int maNhanKhau;
	
	@FXML
	private TextField tfMaNhanKhau;
	@FXML
	private DatePicker tfNgaySinh;
	@FXML
	private TextField tfTenNhanKhau;
	@FXML
	private ComboBox sex;
	@FXML
	private TextField tfSoCMND;

	private NhanKhauModel nhanKhauModel;

	public NhanKhauModel getNhanKhauModel() {
		return nhanKhauModel;
	}

	public void setNhanKhauModel(NhanKhauModel nhanKhauModel) throws ClassNotFoundException, SQLException {
		this.nhanKhauModel = nhanKhauModel;

		maNhanKhau = nhanKhauModel.getId();
		tfMaNhanKhau.setText(Integer.toString(maNhanKhau));
		tfNgaySinh.setValue(LocalDate.parse(nhanKhauModel.getNgaysinh()));
		tfTenNhanKhau.setText(nhanKhauModel.getTen());
		tfSoCMND.setText(nhanKhauModel.getCmnd());
		int selected;
		if(nhanKhauModel.getSex().equals("Nam")) selected=0;
		else if(nhanKhauModel.getSex().equals("Nữ")) selected=1;
		else selected=2;
		sex.getSelectionModel().select(selected);
	}

	public void updateNhanKhau(ActionEvent event) throws ClassNotFoundException, SQLException {
		// khai bao mot mau de so sanh
		Pattern pattern;

		// kiem tra ten nhap vao
		// ten nhap vao la chuoi tu 1 toi 50 ki tu
		if (tfTenNhanKhau.getText().length() >= 50 || tfTenNhanKhau.getText().length() <= 1) {
			Alert alert = new Alert(AlertType.WARNING, "Hãy nhập vào 1 tên hợp lệ!", ButtonType.OK);
			alert.setHeaderText(null);
			alert.showAndWait();
			return;
		}

		// kiem tra tuoi nhap vao
		// tuoi nhap vao nhieu nhat la 1 so co 3 chu so

		// kiem tra cmnd nhap vao
		// cmnd nhap vao phai la mot day so tu 1 toi 20 so
		pattern = Pattern.compile("\\d{1,20}");
		if (!pattern.matcher(tfSoCMND.getText()).matches()) {
			Alert alert = new Alert(AlertType.WARNING, "Hãy nhập vào CMND hợp lệ!", ButtonType.OK);
			alert.setHeaderText(null);
			alert.showAndWait();
			return;
		}

		// kiem tra sdt nhap vao
		// SDT nhap vao phai khong chua chu cai va nho hon 15 chu so
		
		// ghi nhan gia tri ghi tat ca deu da hop le
		String tenString = tfTenNhanKhau.getText();
		String ngayString = tfNgaySinh.getValue()!=null?tfNgaySinh.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")):null;
		String cmndString = tfSoCMND.getText();
		String sexString = sex.getValue().toString();
		
		// xoa di nhan khau hien tai va them vao nhan khau vua cap nhat
		new NhanKhauService().update(maNhanKhau, cmndString, tenString, ngayString, sexString);
		
		Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.close();
		
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		ObservableList<String> list = FXCollections.observableArrayList("Nam", "Nữ", "Khác");
		sex.setItems(list);
	}
}

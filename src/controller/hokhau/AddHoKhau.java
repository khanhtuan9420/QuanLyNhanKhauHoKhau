package controller.hokhau;

import java.net.URL;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.List;
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
import models.ChuHoModel;
import models.HoKhauModel;
import models.NhanKhauModel;
import models.QuanHeModel;
import services.ChuHoService;
import services.HoKhauService;
import services.NhanKhauService;
import services.QuanHeService;

public class AddHoKhau implements Initializable {
	@FXML
	private TextField tfMaHoKhau;
	@FXML
	private TextField tfDiaChi;
	@FXML
	private TextField tfMaChuHo;
	@FXML
	private TextField tfTenChuHo;
	@FXML
	private DatePicker ngaysinh;
	@FXML
	private TextField tfCMND;
	@FXML
	private ComboBox sex;

	@FXML
	public void addHoKhau(ActionEvent event) throws ClassNotFoundException, SQLException {
		// khai bao mot mau de so sanh
		Pattern pattern;

		// kiem tra maHo nhap vao
		// maHo la day so tu 1 toi 11 chu so
		pattern = Pattern.compile("\\d{1,11}");
		if (!pattern.matcher(tfMaHoKhau.getText()).matches()) {
			Alert alert = new Alert(AlertType.WARNING, "Hãy nhập vào mã hộ khẩu hợp lệ!", ButtonType.OK);
			alert.setHeaderText(null);
			alert.showAndWait();
			return;
		}
		
		// kiem tra maHo co bi trung voi maHo nao da ton tai truoc do hay khong
		List<HoKhauModel> listHoKhauModels = new HoKhauService().getListHoKhau();
		for(HoKhauModel hokhau : listHoKhauModels) {
			if (hokhau.getMaHo() == Integer.parseInt(tfMaHoKhau.getText())) {
				Alert alert = new Alert(AlertType.WARNING, "Mã hộ bị trùng với một hộ khác!", ButtonType.OK);
				alert.setHeaderText(null);
				alert.showAndWait();
				return;
			}
		}

		// kiem tra dia chi nhap vao
		// dia chi nhap vao la 1 chuoi t 1 toi 30 ki tu
		if (tfDiaChi.getText().length() >= 50 || tfDiaChi.getText().length() <= 1) {
			Alert alert = new Alert(AlertType.WARNING, "Hãy nhập vào 1 địa chỉ hợp lệ!", ButtonType.OK);
			alert.setHeaderText(null);
			alert.showAndWait();
			return;
		}

		// kiem tra id nhan khau nhap vao
		// id la day so tu 1 toi 11 chu so
		pattern = Pattern.compile("\\d{1,11}");
		if (!pattern.matcher(tfMaChuHo.getText()).matches()) {
			Alert alert = new Alert(AlertType.WARNING, "Hãy nhập vào mã nhân khẩu hợp lệ!", ButtonType.OK);
			alert.setHeaderText(null);
			alert.showAndWait();
			return;
		}
		// kiem tra ID them moi co bi trung voi nhung ID da ton tai hay khong
		List<NhanKhauModel> listNhanKhauModels = new NhanKhauService().getListNhanKhau();
		for(NhanKhauModel nhankhau : listNhanKhauModels) {
			if (nhankhau.getId() == Integer.parseInt(tfMaChuHo.getText())) {
				Alert alert = new Alert(AlertType.WARNING, "Mã chủ hộ trùng với một người khác!", ButtonType.OK);
				alert.setHeaderText(null);
				alert.showAndWait();
				return;
			}
		}

		// kiem tra ten nhap vao
		// ten nhap vao la chuoi tu 1 toi 50 ki tu
		if (tfTenChuHo.getText().length() >= 50 || tfTenChuHo.getText().length() <= 1) {
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
		if (!pattern.matcher(tfCMND.getText()).matches()) {
			Alert alert = new Alert(AlertType.WARNING, "Hãy nhập vào CMND hợp lệ!", ButtonType.OK);
			alert.setHeaderText(null);
			alert.showAndWait();
			return;
		}

		// kiem tra sdt nhap vao
		// SDT nhap vao phai khong chua chu cai va nho hon 15 chu s
		
		// ghi nhan cac gia tri khi tat ca deu hop le
		int maHo = Integer.parseInt(tfMaHoKhau.getText());
		String diaChi = tfDiaChi.getText();
		int maChuHo = Integer.parseInt(tfMaChuHo.getText());
		String tenChuHo = tfTenChuHo.getText();
		String ngayString = ngaysinh.getValue()!=null?ngaysinh.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")):null;
		String cmndChuHo = tfCMND.getText();
		String sexString = sex.getValue().toString();
		
		HoKhauModel hoKhauModel = new HoKhauModel(maHo, 0, diaChi);
		NhanKhauModel nhanKhauModel = new NhanKhauModel(maChuHo, cmndChuHo, tenChuHo, ngayString, sexString);
		
		new HoKhauService().add(hoKhauModel);
		new NhanKhauService().add(nhanKhauModel);
		new QuanHeService().add(new QuanHeModel(maHo,maChuHo,"Là chủ hộ"));
		new ChuHoService().add(new ChuHoModel(maHo, maChuHo));
		
		Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.close();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		ObservableList<String> list = FXCollections.observableArrayList("Nam", "Nữ", "Khác");
		sex.setItems(list);
		sex.getSelectionModel().selectFirst();
	}

}

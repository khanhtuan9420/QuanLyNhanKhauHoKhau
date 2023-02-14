package controller.nhankhau;

import java.io.IOException;
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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import models.ChuHoModel;
import models.HoKhauModel;
import models.NhanKhauModel;
import models.QuanHeModel;
import services.ChuHoService;
import services.HoKhauService;
import services.NhanKhauService;
import services.QuanHeService;

public class AddNhanKhau implements Initializable {
	@FXML
	private TextField tfId;
	@FXML
	private TextField tfTen;
	@FXML
	private DatePicker tfNgaySinh;
	@FXML
	private TextField tfCmnd;
	@FXML
	private ComboBox sex;
	@FXML
	private TextField tfMaChuHo;
	@FXML
	private TextField tfQuanHe;

	private NhanKhauModel nhanKhauModel;

	public void chooseChuHo(ActionEvent event) throws SQLException, IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/views/nhankhau/ChooseChuHo.fxml"));
		Parent home = loader.load(); 
        Stage stage = new Stage();
        stage.setTitle("Chọn chủ hộ");
        stage.setScene(new Scene(home));
        stage.setResizable(false);
        stage.showAndWait();
        
        ChooseChuHo chooseChuHo = loader.getController();
        nhanKhauModel = chooseChuHo.getNhanKhauChoose();
        if(nhanKhauModel == null) return;
        
        tfMaChuHo.setText(nhanKhauModel.getId()+"");
	}

	public void addNhanKhau(ActionEvent event) throws ClassNotFoundException, SQLException {
		// khai bao mot mau de so sanh
		Pattern pattern;

		// kiem tra id nhap vao
		// id la day so tu 1 toi 11 chu so
		pattern = Pattern.compile("\\d{1,11}");
		if (!pattern.matcher(tfId.getText()).matches()) {
			Alert alert = new Alert(AlertType.WARNING, "Hãy nhập vào mã nhân khẩu hợp lệ!", ButtonType.OK);
			alert.setHeaderText(null);
			alert.showAndWait();
			return;
		}
		// kiem tra ID them moi co bi trung voi nhung ID da ton tai hay khong
		List<NhanKhauModel> listNhanKhauModels = new NhanKhauService().getListNhanKhau();
		for (NhanKhauModel nhankhau : listNhanKhauModels) {
			if (nhankhau.getId() == Integer.parseInt(tfId.getText())) {
				Alert alert = new Alert(AlertType.WARNING, "ID bị trùng với một người khác!", ButtonType.OK);
				alert.setHeaderText(null);
				alert.showAndWait();
				return;
			}
		}

		// kiem tra ten nhap vao
		// ten nhap vao la chuoi tu 1 toi 50 ki tu
		if (tfTen.getText().length() >= 50 || tfTen.getText().length() <= 1) {
			Alert alert = new Alert(AlertType.WARNING, "Hãy nhập vào 1 tên hợp lệ!", ButtonType.OK);
			alert.setHeaderText(null);
			alert.showAndWait();
			return;
		}


		// kiem tra cmnd nhap vao
		// cmnd nhap vao phai la mot day so tu 1 toi 20 so
		pattern = Pattern.compile("\\d{1,20}");
		if (!pattern.matcher(tfCmnd.getText()).matches()) {
			Alert alert = new Alert(AlertType.WARNING, "Hãy nhập vào CMND hợp lệ!", ButtonType.OK);
			alert.setHeaderText(null);
			alert.showAndWait();
			return;
		}

		// kiem tra maHo nhap vao
		// ma ho nhap vao phai khong chua chu cai va nho hon 11 chu so
		pattern = Pattern.compile("\\d{1,11}");
		if (!pattern.matcher(tfMaChuHo.getText()).matches()) {
			Alert alert = new Alert(AlertType.WARNING, "Hãy nhập vào mã hộ hợp lệ!", ButtonType.OK);
			alert.setHeaderText(null);
			alert.showAndWait();
			return;
		}

		// kiem tra ma ho nhap vao da ton tai hay chua
		List<ChuHoModel> listChuHoModels = new ChuHoService().getListChuHo();
		long check = listChuHoModels.stream()
				.filter(chuho -> chuho.getIdChuHo() == Integer.parseInt(tfMaChuHo.getText())).count();
		if (check <= 0) {
			Alert alert = new Alert(AlertType.WARNING, "Không có chủ hộ này!", ButtonType.OK);
			alert.setHeaderText(null);
			alert.showAndWait();
			return;
		}

		// Kiem tra Quan he nhap vao
		if (tfQuanHe.getText().length() >= 30 || tfQuanHe.getText().length() <= 1) {
			Alert alert = new Alert(AlertType.WARNING, "Hãy nhập vào 1 quan hệ hợp lệ!", ButtonType.OK);
			alert.setHeaderText(null);
			alert.showAndWait();
			return;
		}
		int maHoInt=0;
		for(ChuHoModel chuho : listChuHoModels) {
			if(chuho.getIdChuHo() == Integer.parseInt(tfMaChuHo.getText())) {
				maHoInt=chuho.getMaHo();
			}
		}

		// ghi nhan gia tri ghi tat ca deu da hop le
		int idInt = Integer.parseInt(tfId.getText());
		String tenString = tfTen.getText();
		String ngayString = tfNgaySinh.getValue()!=null?tfNgaySinh.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")):null;
		String cmndString = tfCmnd.getText();
		String sexString = sex.getValue().toString();
		int maCHuHoInt = Integer.parseInt(tfMaChuHo.getText());
		String quanheString = tfQuanHe.getText();

		NhanKhauService nhanKhauService = new NhanKhauService();
		QuanHeService quanHeService = new QuanHeService();

		NhanKhauModel nhanKhauModel = new NhanKhauModel(idInt, cmndString, tenString, ngayString, sexString);
		QuanHeModel quanHeModel = new QuanHeModel(maHoInt, idInt, quanheString);

		nhanKhauService.add(nhanKhauModel);
		quanHeService.add(quanHeModel);

		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.close();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		ObservableList<String> list = FXCollections.observableArrayList("Nam", "Nữ", "Khác");
		sex.setItems(list);
		sex.getSelectionModel().selectFirst();
	}
}
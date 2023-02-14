package controller.admin;

import java.net.URL;
import java.sql.SQLException;
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
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import models.KhoanThuModel;
import models.UsersModel;
import services.AdminService;
import services.KhoanThuService;

public class AddTaiKhoan implements Initializable {
	@FXML
	private TextField tfMaTaiKhoan;
	@FXML
	private TextField tfTenTaiKhoan;
	@FXML
	private ComboBox<String> cbRole;
	@FXML
	private TextField tfPassword;
    @FXML
    private TextField tfName;

	public void addTaiKhoan(ActionEvent event) throws ClassNotFoundException, SQLException {
		Pattern pattern;

		// kiem tra maKhoanThu nhap vao
		// maKhoanThu la day so tu 1 toi 11 chu so
		pattern = Pattern.compile("\\d{1,11}");
		if (!pattern.matcher(tfMaTaiKhoan.getText()).matches()) {
			Alert alert = new Alert(AlertType.WARNING, "Hãy nhập vào mã tài khoản hợp lệ!", ButtonType.OK);
			alert.setHeaderText(null);
			alert.showAndWait();
			return;
		}
		
		// kiem tra ma khoan thu them moi co bi trung voi nhung ma khoan thu da ton tai hay khong
		List<KhoanThuModel> listKhoanThuModels = new KhoanThuService().getListKhoanThu(); 
		for(KhoanThuModel khoanThuModel : listKhoanThuModels) {
			if(khoanThuModel.getMaKhoanThu() == Integer.parseInt(tfMaTaiKhoan.getText())) {
				Alert alert = new Alert(AlertType.WARNING, "Mã tài khoản đã bị trùng!", ButtonType.OK);
				alert.setHeaderText(null);
				alert.showAndWait();
				return;
			}
		}
		
		// kiem tra ten nhap vao
		// ten nhap vao la chuoi tu 1 toi 50 ki tu
		if (tfTenTaiKhoan.getText().length() >= 50 || tfTenTaiKhoan.getText().length() <= 1) {
			Alert alert = new Alert(AlertType.WARNING, "Hãy nhập vào 1 tên tài khoản hợp lệ!", ButtonType.OK);
			alert.setHeaderText(null);
			alert.showAndWait();
			return;
		}

		List<UsersModel> lUsersModels = new AdminService().getListUsers(); 
		for(UsersModel user : lUsersModels) {
			if(user.getUsername().equals(tfTenTaiKhoan.getText())) {
				Alert alert = new Alert(AlertType.WARNING, "Tên tài khoản đã tồn tại!", ButtonType.OK);
				alert.setHeaderText(null);
				alert.showAndWait();
				return;
			}
		}

		// kiem tra soTien nhap vao
		// so tien nhap vao phai la so va nho hon 11 chu so
		if (tfPassword.getText().length() >= 50 || tfPassword.getText().length() <= 8) {
			Alert alert = new Alert(AlertType.WARNING, "Hãy nhập vào mật khẩu lớn hơn 8 ký tự!", ButtonType.OK);
			alert.setHeaderText(null);
			alert.showAndWait();
			return;
		}

		// ghi nhan gia tri ghi tat ca deu da hop le
		SingleSelectionModel<String> roleSelection = cbRole.getSelectionModel();
		String role_tmp = roleSelection.getSelectedItem();
		
		int maTaiKhoan = Integer.parseInt(tfMaTaiKhoan.getText());
		String tenTaiKhoanString = tfTenTaiKhoan.getText();
        String nameString = tfName.getText();
		String passwordString = tfPassword.getText();
		int role;
		if(role_tmp.equals("Tổ trưởng")) {
			role = 1;
		} else if(role_tmp.equals("Tổ phó")) {
            role=2;
        } else if(role_tmp.equals("Kế toán")) {
            role=3;
        }
        else {
			role = 4;
		}

		new AdminService().add(new UsersModel(maTaiKhoan, nameString, tenTaiKhoanString, passwordString, role));
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.close();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// thiet lap gia tri cho loai khoan thu
		ObservableList<String> listComboBox = FXCollections.observableArrayList("Tổ phó","Kế toán", "Tổ trưởng");
		cbRole.setValue("Tổ trưởng");
		cbRole.setItems(listComboBox);
	}
}

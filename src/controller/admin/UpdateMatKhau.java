package controller.admin;

import java.io.IOException;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import models.UsersModel;
import services.AdminService;

public class UpdateMatKhau {
	@FXML
	private PasswordField curPass;
    @FXML
    private PasswordField password;
    @FXML
    private PasswordField rePassword;

	private UsersModel userModel;
	private boolean isChanged=false;
	private static boolean noChange=true;
	private static int isChoose=0;

    public static void setIsChoose() {
        if(isChoose==0) isChoose=1;
        else if(isChoose==2) isChoose=0;
        else if(isChoose==1) isChoose=0;
    }

    public static int getIsChoose() {
        return isChoose;
    }

	public void setMatKhauModel(UsersModel userModel) {
		this.userModel = userModel;
	}

	public void setMatKhauModel(UsersModel userModel, boolean a) {
		this.userModel = userModel;
		this.isChanged=a;
	}

	public static boolean getNoChanged() {
		return noChange;
	}

	public void updateMatKhau(ActionEvent event) throws ClassNotFoundException, SQLException, IOException {
		// kiem tra ten nhap vao
		// ten nhap vao la chuoi tu 1 toi 50 ki tu
		if(isChanged) {
			if (!curPass.getText().equals(userModel.getPasswd())) {
				Alert alert = new Alert(AlertType.WARNING, "Mật khẩu hiện tại không chính xác!", ButtonType.OK);
				alert.setHeaderText(null);
				alert.showAndWait();
				return;
			}
		}

		if (password.getText().length() >= 21 || password.getText().length() <= 7) {
			Alert alert = new Alert(AlertType.WARNING, "Hãy nhập vào 1 mật khẩu hợp lệ với độ dài từ 8 đến 20 kí tự!", ButtonType.OK);
			alert.setHeaderText(null);
			alert.showAndWait();
			return;
		}

		if (password.getText().length() < 21 && password.getText().length() > 7 && !password.getText().equals(rePassword.getText())) {
			Alert alert = new Alert(AlertType.WARNING, "Mật khẩu nhập lại chưa chính xác!", ButtonType.OK);
			alert.setHeaderText(null);
			alert.showAndWait();
			return;
        }
		
        // ghi nhan cac gia tri sau khi da kiem tra hop le
        int id = userModel.getId();
        String matkhau = password.getText();
        noChange=false;
        
        new AdminService().update(id, matkhau);
		Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
		if(isChanged) {
			FXMLLoader loader1 = new FXMLLoader();
			loader1.setLocation(getClass().getResource("/views/resetPassNoti.fxml"));
			Parent home=loader1.load();
			stage.setScene(new Scene(home));
		} else {
			stage.close();
		}
		
	}
}   
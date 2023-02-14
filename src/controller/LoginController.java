package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import models.UsersModel;
import services.MysqlConnection;

public class LoginController {
	@FXML
	private TextField tfUsername;
	@FXML
	private PasswordField tfPassword;
	@FXML
	private CheckBox adModeBtn;
	
	private static UsersModel usersModel;
	private boolean isLogIn=false;

	
	public static int ROLE;

	public static UsersModel getUsersModel() {
		return usersModel;
	}
	
	public void Login(ActionEvent event) throws IOException, ClassNotFoundException, SQLException {
		String name = tfUsername.getText();
		String pass = tfPassword.getText();
		Connection connection = MysqlConnection.getMysqlConnection();
		String query = "select * from users";
		Statement st =connection.createStatement();
		ResultSet set = st.executeQuery(query);
		while(set.next()) {
			if(name.equals((String)set.getObject("username")) && pass.equals((String)set.getObject("passwd"))) {
				isLogIn=true;
				usersModel= new UsersModel((int)set.getObject("id"), (String)set.getObject("name"), (String)set.getObject("username")
					, (String)set.getObject("passwd"), (int)set.getObject("role"));
				ROLE = (int) set.getObject("role");
				break;
			}
		}
		
		// check username and password
		if(!isLogIn || (adModeBtn.selectedProperty().get() && ROLE!=1 )) {
			Alert alert = new Alert(AlertType.WARNING, "Tài khoản hoặc mật khẩu không chính xác!", ButtonType.OK);
			alert.setHeaderText(null);
			alert.showAndWait();
			return;
		}
		Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
		Parent home;
		if(adModeBtn.selectedProperty().get()) {
			home = FXMLLoader.load(getClass().getResource("/views/Admin.fxml"));
		} else {
			home = FXMLLoader.load(getClass().getResource("/views/Home3.fxml"));
		}
		stage.setScene(new Scene(home));
		stage.centerOnScreen();
		stage.setResizable(false);
		stage.show();
	}

}

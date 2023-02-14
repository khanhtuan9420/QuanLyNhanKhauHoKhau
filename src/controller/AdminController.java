package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.TreeMap;

import controller.admin.UpdateMatKhau;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.UsersModel;
import services.AdminService;

public class AdminController implements Initializable {
	@FXML
	private TableView<UsersModel> tvUsers;
	@FXML
	private TableColumn<UsersModel, String> colId;
	@FXML
	private TableColumn<UsersModel, String> colUsername;
	@FXML
	private TableColumn<UsersModel, String> colPasswd;
	@FXML
	private TableColumn<UsersModel, String> colRole;
	@FXML
	private TableColumn<UsersModel, String> colName;
	@FXML
	private TextField tfSearch;
	@FXML
	private ImageView signOutBtn;
	@FXML
	private ComboBox<String> cbChooseSearch;
	private List<UsersModel> listUsers;
	private ObservableList<UsersModel> listValueTableView;
	private Map<Integer, String> mapRole = new TreeMap();

	private void logOut() throws IOException {
		Parent home = FXMLLoader.load(getClass().getResource("/views/LogOut.fxml"));
		Stage stage = new Stage();
		stage.setScene(new Scene(home));
		stage.setResizable(false);
		stage.setOnCloseRequest(e -> {
			LogOutController.setIsChoose();
			((Stage) ((Stage) ( e.getSource())).getScene().getWindow()).close();
		});
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.showAndWait();
		while(LogOutController.getIsChoose()==1 || LogOutController.getIsChoose()==2) {
			if(LogOutController.getIsChoose()==2) {
				Parent home1 = FXMLLoader.load(getClass().getResource("/views/Login.fxml"));
				Stage stage1 = (Stage) signOutBtn.getScene().getWindow();
				Scene sc = new Scene(home1);
				stage1.setScene(sc);
				stage1.show();
				LogOutController.setIsChoose();
				break;
			}
		}
	}
	EventHandler<MouseEvent> signOut = new EventHandler<MouseEvent>() { 
		@Override 
		public void handle(MouseEvent e) { 
			try {
				if(LogOutController.getIsChoose()==0) {
					LogOutController.setIsChoose();
					logOut();
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}		    
		} 
	 };
	 
	public void showTaiKhoan() throws ClassNotFoundException, SQLException {
		listUsers = new AdminService().getListUsers();
		listValueTableView = FXCollections.observableArrayList(listUsers);

		// thiet lap cac cot cho table views
		colId.setCellValueFactory(new PropertyValueFactory<UsersModel, String>("id"));
		colName.setCellValueFactory(new PropertyValueFactory<UsersModel, String>("name"));
		colUsername.setCellValueFactory(new PropertyValueFactory<UsersModel, String>("username"));
		colPasswd.setCellValueFactory(new PropertyValueFactory<UsersModel, String>("passwd"));

		
		mapRole.put(1, "Tổ trưởng");
		mapRole.put(2, "Tổ phó");
		mapRole.put(3, "Kế toán");
		mapRole.put(4, "Thành viên");

		try {
			colRole
					.setCellValueFactory((CellDataFeatures<UsersModel, String> p) -> new ReadOnlyStringWrapper(
							mapRole.get(p.getValue().getRole())));
		} catch (Exception e) {
			// TODO: handle exception
		}
		tvUsers.setItems(listValueTableView);

		// thiet lap gia tri cho combobox
		ObservableList<String> listComboBox = FXCollections.observableArrayList("Tên", "Chức vụ");
		cbChooseSearch.setValue("Tên");
		cbChooseSearch.setItems(listComboBox);
	}

	// Tim kiem khoan thu
	public void searchTaiKhoan() {
		ObservableList<UsersModel> listValueTableView_tmp = null;
		String keySearch = tfSearch.getText().toLowerCase();

		// lay lua chon tim kiem cua khach hang
		SingleSelectionModel<String> typeSearch = cbChooseSearch.getSelectionModel();
		String typeSearchString = typeSearch.getSelectedItem();

		// tim kiem thong tin theo lua chon da lay ra
		switch (typeSearchString) {
		case "Tên": {
			// neu khong nhap gi -> thong bao loi
			if (keySearch.length() == 0) {
				tvUsers.setItems(listValueTableView);
				Alert alert = new Alert(AlertType.WARNING, "Hãy nhập vào thông tin cần tìm kiếm!", ButtonType.OK);
				alert.setHeaderText(null);
				alert.showAndWait();
				break;
			}

			int index = 0;
			List<UsersModel> listNameModelsSearch = new ArrayList<>();
			for (UsersModel nameModel : listUsers) {
				if (nameModel.getName().toLowerCase().contains(keySearch)) {
					listNameModelsSearch.add(nameModel);
					index++;
				}
			}
			listValueTableView_tmp = FXCollections.observableArrayList(listNameModelsSearch);
			tvUsers.setItems(listValueTableView_tmp);

			// neu khong tim thay thong tin can tim kiem -> thong bao toi nguoi dung khong
			// tim thay
			if (index == 0) {
				tvUsers.setItems(listValueTableView); // hien thi toan bo thong tin
				Alert alert = new Alert(AlertType.INFORMATION, "Không tìm thấy thông tin!", ButtonType.OK);
				alert.setHeaderText(null);
				alert.showAndWait();
			}
			break;
		}
		default: { // truong hop con lai : tim theo ma khoan thu
			// neu khong nhap gi -> thong bao loi
			if (keySearch.length() == 0) {
				tvUsers.setItems(listValueTableView);
				Alert alert = new Alert(AlertType.INFORMATION, "Bạn cần nhập vào thông tin tìm kiếm!", ButtonType.OK);
				alert.setHeaderText(null);
				alert.showAndWait();
				break;
			}

			int role=-1;
			for(int k : mapRole.keySet()) {
				if(mapRole.get(k).toLowerCase().equals(keySearch)) {
					role=k;
					break;
				}
			}
			
			for (UsersModel usersModel : listUsers) {
				if (usersModel.getRole() == role) {
					listValueTableView_tmp = FXCollections.observableArrayList(usersModel);
					tvUsers.setItems(listValueTableView_tmp);
					return;
				}
			}

			// khong tim thay thong tin -> thong bao toi nguoi dung
			tvUsers.setItems(listValueTableView);
			Alert alert = new Alert(AlertType.WARNING, "Không tìm thấy thông tin!", ButtonType.OK);
			alert.setHeaderText(null);
			alert.showAndWait();
		}
		}
	}

	public void addTaiKhoan() throws IOException, ClassNotFoundException, SQLException {
		Parent home = FXMLLoader.load(getClass().getResource("/views/admin/AddTaiKhoan.fxml"));
		Stage stage = new Stage();
		stage.setScene(new Scene(home));
		stage.setResizable(false);
		stage.showAndWait();
		showTaiKhoan();
	}

	public void delTaiKhoan() throws ClassNotFoundException, SQLException {
		UsersModel userModel = tvUsers.getSelectionModel().getSelectedItem();

		if (userModel == null) {
			Alert alert = new Alert(AlertType.WARNING, "Chọn tài khoản bạn muốn xóa!", ButtonType.OK);
			alert.setHeaderText(null);
			alert.showAndWait();
		} else {
			Alert alert = new Alert(AlertType.WARNING, "Bạn có chắc chắn muốn xóa tài khoản này!", ButtonType.YES,
					ButtonType.NO);
			alert.setHeaderText(null);
			Optional<ButtonType> result = alert.showAndWait();

			if (result.get() == ButtonType.NO) {
				return;
			} else {
				new AdminService().del(userModel.getId());
			}
		}

		showTaiKhoan();
	}

	public void doiMatKhau() throws ClassNotFoundException, SQLException, IOException {
		// lay ra nhan khau can update
		UsersModel userModel = tvUsers.getSelectionModel().getSelectedItem();

		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/views/admin/UpdateMatKhau.fxml"));
		Parent home = loader.load();
		Stage stage = new Stage();
		stage.setScene(new Scene(home));
		UpdateMatKhau updateMatKhau = loader.getController();

		// bat loi truong hop khong hop le
		if (updateMatKhau == null) return;
		if (userModel == null) {
			Alert alert = new Alert(AlertType.WARNING, "Chọn tài khoản update !", ButtonType.OK);
			alert.setHeaderText(null);
			alert.showAndWait();
			return;
		}
		updateMatKhau.setMatKhauModel(userModel);

		stage.setResizable(false);
		stage.showAndWait();
		showTaiKhoan();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		signOutBtn.addEventFilter(MouseEvent.MOUSE_CLICKED, signOut);
		try {
			showTaiKhoan();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

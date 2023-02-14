package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import controller.admin.UpdateMatKhau;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import models.UsersModel;

public class HomeController implements Initializable {
	@FXML
	private StackPane hp;	
	@FXML
	private HBox hBox;
	@FXML
	private VBox vBox;
	@FXML
	private ImageView showBtn;
	@FXML
	private ImageView signOutBtn;
	@FXML
	private ImageView resetPass;

	private int check=0;
	private Node lastBtn;

	private void popup() throws IOException {
		Parent home = FXMLLoader.load(getClass().getResource("/views/RoleNoti.fxml"));
		Stage stage = new Stage();
		stage.setScene(new Scene(home, 440, 250));
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setResizable(false);
        stage.showAndWait();
	}

	private void logOut() throws IOException {
		Parent home = FXMLLoader.load(getClass().getResource("/views/LogOut.fxml"));
		Stage stage = new Stage();
		stage.setScene(new Scene(home, 400, 250));
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

	private void fadeAnime(Node b) {
		if(hp.getChildren().size()>=2) hp.getChildren().remove(0);
		Node a = hp.getChildren().get(0);
		hp.getChildren().add(b);
		b.translateXProperty().set(-646);
		anim(a,1,646);
		anim(b, 1, 0);
	}

	public void resize() {
		if(check==1) {
			hp.setPrefWidth(646);
		} else {
			(hp).setPrefWidth(856);
		}
	}

	public void chooseEffect(ActionEvent e) {
		if(lastBtn!=null) lastBtn.getStyleClass().remove("choose");
		lastBtn=(Node) e.getSource();
		((Node) e.getSource()).getStyleClass().add("choose");
	}

	
	public void setNhanKhau(ActionEvent event) throws IOException {
		if(LoginController.ROLE<=2) {
			FXMLLoader loader = new FXMLLoader(LoginController.class.getResource("/views/NhanKhau.fxml"));
			Pane nhankhauPane = (Pane) loader.load();
			fadeAnime(nhankhauPane);
			resize();
			chooseEffect(event);
		} else {
			popup();
		}
	}

	public void setHoKhau(ActionEvent event) throws IOException {
		if(LoginController.ROLE<=2) {
			FXMLLoader loader = new FXMLLoader(LoginController.class.getResource("/views/HoKhau.fxml"));
			Pane hokhauPane = (Pane) loader.load();
			fadeAnime(hokhauPane);
			resize();
			chooseEffect(event);
		} else {
			popup();
		}
	}

	public void setKhoanPhi(ActionEvent event) throws IOException {
		if(LoginController.ROLE>2) {
			FXMLLoader loader = new FXMLLoader(LoginController.class.getResource("/views/KhoanThu.fxml"));
			Pane khoanphiPane = (Pane) loader.load();
			fadeAnime(khoanphiPane);
			resize();
			chooseEffect(event);
		} else {
			popup();
		}
	}
	
	public void setDongPhi(ActionEvent event) throws IOException {
		if(LoginController.ROLE>2) {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/NopTien.fxml"));
			Pane dongphiPane = (Pane) loader.load();
			fadeAnime(dongphiPane);
			resize();
			chooseEffect(event);
		} else {
			popup();
		}
	}
	
	public void setThongKe(ActionEvent event) throws IOException, InterruptedException {
		if(LoginController.ROLE>2) {
			FXMLLoader loader = new FXMLLoader(LoginController.class.getResource("/views/ThongKe.fxml"));
			Pane thongkePane = (Pane) loader.load();
			fadeAnime(thongkePane);
			resize();
			chooseEffect(event);
		} else {
			FXMLLoader loader = new FXMLLoader(LoginController.class.getResource("/views/ThongKe2.fxml"));
			Pane thongkePane = (Pane) loader.load();
			fadeAnime(thongkePane);
			resize();
			chooseEffect(event);
		}
	}
	
	public void setTrangChu(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(LoginController.class.getResource("/views/Main.fxml"));
		Pane trangchuPane = (Pane) loader.load();
		fadeAnime(trangchuPane);
		resize();
		if(lastBtn!=null) lastBtn.getStyleClass().remove("choose");
	}

	public void anim(Node a, double time, int x) {
		KeyValue keyValue = new KeyValue(a.translateXProperty(), x,Interpolator.EASE_IN);
		KeyFrame keyFrame = new KeyFrame(Duration.seconds(time), keyValue);
        Timeline timeline = new Timeline(keyFrame);
		timeline.play();
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

	EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() { 
		@Override 
		public void handle(MouseEvent e) { 
			if(hp.getChildren().size()==2) hp.getChildren().remove(0);
		   if(check==0) {
				check=1;
				((Region) hp).setPrefWidth(646);
				anim(vBox, 0.5, 262);
				anim(hBox,0.35, 213);
			} else {
				check=0;
				((Region) hp).setPrefWidth(857);
				anim(hBox,0.35, 0);
				anim(vBox, 0.5, -256);
		   }		    
		} 
	 };

	
	EventHandler<MouseEvent> hoverEffect = new EventHandler<MouseEvent>() {

		@Override
		public void handle(MouseEvent e) {
			anim((Node) e.getSource(), 0.2, 15);
		}
		
	};

	EventHandler<MouseEvent> destroyHoverEffect = new EventHandler<MouseEvent>() {

		@Override
		public void handle(MouseEvent e) {
			anim((Node) e.getSource(), 0.2, 0);
		}
		
	};

	EventHandler<MouseEvent> resetPassword = new EventHandler<MouseEvent>() {
		@Override
		public void handle(MouseEvent e) {
			if(UpdateMatKhau.getIsChoose()==0) {
				UsersModel userModel= LoginController.getUsersModel();
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("/views/admin/UpdateMatKhau2.fxml"));
				Parent home=null;
				try {
					home = loader.load();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				Stage stage = new Stage();
				stage.setScene(new Scene(home));
				stage.initModality(Modality.APPLICATION_MODAL);
				stage.show();
				UpdateMatKhau updateMatKhau = loader.getController();
	
				// bat loi truong hop khong hop le
				if (updateMatKhau == null) return;
				if (userModel == null) {
					Alert alert = new Alert(AlertType.WARNING, "Chọn tài khoản update !", ButtonType.OK);
					alert.setHeaderText(null);
					alert.showAndWait();
					return;
				}
				updateMatKhau.setMatKhauModel(userModel,true);
				Stage stage1 = (Stage) signOutBtn.getScene().getWindow();
				stage.setOnCloseRequest(e1 -> {
					UpdateMatKhau.setIsChoose();
					if(!UpdateMatKhau.getNoChanged()) {
						Parent home1=null;
						try {
							home1 = FXMLLoader.load(getClass().getResource("/views/Login.fxml"));
						} catch (IOException e2) {
							e2.printStackTrace();
						}
						Scene sc = new Scene(home1);
						stage1.setScene(sc);
						stage1.centerOnScreen();
					}
				});
				UpdateMatKhau.setIsChoose();
			}
		}
		
	};
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			Pane login = FXMLLoader.load(getClass().getResource("/views/Main.fxml"));
			hp.getChildren().add(login);
			// hp.setCenter(login);
		} catch (IOException e) {
			e.printStackTrace();
		}
		resetPass.addEventFilter(MouseEvent.MOUSE_CLICKED, resetPassword);
		showBtn.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);
		signOutBtn.addEventFilter(MouseEvent.MOUSE_CLICKED, signOut);
		for(int i=1;i<vBox.getChildren().size();i++) {
			vBox.getChildren().get(i).addEventFilter(MouseEvent.MOUSE_ENTERED, hoverEffect);
			vBox.getChildren().get(i).addEventFilter(MouseEvent.MOUSE_EXITED, destroyHoverEffect);
		}
	}

}

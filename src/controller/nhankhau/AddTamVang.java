package controller.nhankhau;

import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Pattern;
import javafx.scene.Node;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import models.TamVangModel;
import services.TamVangService;

public class AddTamVang {
    @FXML
	TextField id;
	@FXML
    TextField diadiem;
	@FXML
	TextArea lydo;
	@FXML
    DatePicker from;
	@FXML
	DatePicker to;

    private static int nhanKhauId;

    public static void setNhan_khau_id(int a) {
        nhanKhauId=a;
    }

    public void addTamVang(ActionEvent event) throws ClassNotFoundException, SQLException {
        Pattern pattern;

		// kiem tra id nhap vao
		// id la day so tu 1 toi 11 chu so
		pattern = Pattern.compile("\\d{1,11}");
		if (!pattern.matcher(id.getText()).matches()) {
			Alert alert = new Alert(AlertType.WARNING, "Hãy nhập vào mã giấy tạm vắng hợp lệ!", ButtonType.OK);
			alert.setHeaderText(null);
			alert.showAndWait();
			return;
		}

        // kiem tra ID them moi co bi trung voi nhung ID da ton tai hay khong
		List<TamVangModel> listTamVangModels = new TamVangService().getListTamVang();
		for (TamVangModel nhankhau : listTamVangModels) {
			if (nhankhau.getId() == Integer.parseInt(id.getText())) {
				Alert alert = new Alert(AlertType.WARNING, "ID bị trùng với một giấy tạm vắng khác!", ButtonType.OK);
				alert.setHeaderText(null);
				alert.showAndWait();
				return;
			}
		}

		// kiem tra ten nhap vao
		// ten nhap vao la chuoi tu 1 toi 50 ki tu
		if (diadiem.getText().length() >= 50 || diadiem.getText().length() <= 1) {
			Alert alert = new Alert(AlertType.WARNING, "Hãy nhập vào 1 tên hợp lệ!", ButtonType.OK);
			alert.setHeaderText(null);
			alert.showAndWait();
			return;
		}

        if (lydo.getText().length() >= 50 || lydo.getText().length() <= 1) {
			Alert alert = new Alert(AlertType.WARNING, "Hãy nhập vào lý do hợp lệ!", ButtonType.OK);
			alert.setHeaderText(null);
			alert.showAndWait();
			return;
		}

		// ghi nhan gia tri ghi tat ca deu da hop le
		int idInt = Integer.parseInt(id.getText());
		String diadiemString = diadiem.getText();
        String fromValue=from.getValue()!=null?from.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")):null;
		String toValue=to.getValue()!=null?to.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")):null;
		String lydoString = lydo.getText();

		TamVangService tamVangService = new TamVangService();

		TamVangModel tamVangModel = new TamVangModel(idInt, nhanKhauId, diadiemString, fromValue, toValue, lydoString);

		tamVangService.add(tamVangModel);

		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.close();
    }
}

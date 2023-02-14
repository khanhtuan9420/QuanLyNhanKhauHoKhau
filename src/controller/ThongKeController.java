package controller;

import java.net.URL;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TreeMap;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import models.KhoanThuModel;
import models.NhanKhauModel;
import models.NopTienModel;
import models.QuanHeModel;
import models.TamVangModel;
import services.KhoanThuService;
import services.NhanKhauService;
import services.NopTienService;
import services.QuanHeService;

public class ThongKeController implements Initializable {
	// Quan ly
	@FXML
	private Label vangLabel;
	@FXML
	private Label vangCnt;
	@FXML
	private TableColumn<NhanKhauModel, String> colMaNhanKhau;
	@FXML
	private TableColumn<NhanKhauModel, String> colTen;
	@FXML
	private TableColumn<NhanKhauModel, String> colCMND;
	@FXML
	private TableColumn<NhanKhauModel, String> colFrom;
	@FXML
	private TableColumn<NhanKhauModel, String> colTo;
	@FXML
	private TableColumn<NhanKhauModel, String> colMaHo;
	@FXML
	TableView<NhanKhauModel> tvThongKe1;
	// Ke Toan
	@FXML
	TableColumn<KhoanThuModel, String> colTenPhi;
	@FXML
	TableColumn<KhoanThuModel, String> colTongSoTien;
	@FXML
	TableView<KhoanThuModel> tvThongKe;
	@FXML
	ComboBox<String> cbChooseSearch;
	@FXML
	DatePicker from;
	@FXML
	DatePicker to;

	private ObservableList<KhoanThuModel> listValueTableView;
	private ObservableList<NhanKhauModel> listValueTableView1;
	private List<KhoanThuModel> listKhoanThu;

	public void showThongKe1(List<NhanKhauModel> lists) throws ClassNotFoundException, SQLException {
		List<NhanKhauModel> listNhanKhau = lists;
		listValueTableView1 = FXCollections.observableArrayList(listNhanKhau);

		// thiet lap cac cot cho tableviews
		colMaNhanKhau.setCellValueFactory(new PropertyValueFactory<NhanKhauModel, String>("id"));
		colTen.setCellValueFactory(new PropertyValueFactory<NhanKhauModel, String>("ten"));
		colCMND.setCellValueFactory(new PropertyValueFactory<NhanKhauModel, String>("cmnd"));
		colFrom.setCellValueFactory(new PropertyValueFactory<NhanKhauModel, String>("from"));
		colTo.setCellValueFactory(new PropertyValueFactory<NhanKhauModel, String>("to"));

		Map<Integer, Integer> mapIdToMaho = new HashMap<>();
		List<QuanHeModel> listQuanHe = new QuanHeService().getListQuanHe();
		listQuanHe.forEach(quanhe -> {
			mapIdToMaho.put(quanhe.getIdThanhVien(), quanhe.getMaHo());
		});
		try {
			colMaHo.setCellValueFactory(
					(CellDataFeatures<NhanKhauModel, String> p) -> new ReadOnlyStringWrapper(mapIdToMaho.get(p.getValue().getId()).toString())
			);
		} catch (Exception e) {
			System.out.println("haha");
		}

		tvThongKe1.setItems(listValueTableView1);
		// thiet lap gia tri cho combobox
	}

	public void showThongKe() throws ClassNotFoundException, SQLException {
		listKhoanThu = new KhoanThuService().getListKhoanThu();
		listValueTableView = FXCollections.observableArrayList(listKhoanThu);
		String fromValue=from.getValue()!=null?from.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")):null;
		String toValue=to.getValue()!=null?to.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")):null;

		List<NopTienModel> listNopTien = new NopTienService().getListNopTien(fromValue,toValue);
		Map<Integer, Long> mapMaKhoanThuToSoLuong = new TreeMap<>();
		for (KhoanThuModel khoanThuModel : listKhoanThu) {
			long cntNopTien = listNopTien.stream()
					.filter(noptien -> noptien.getMaKhoanThu() == khoanThuModel.getMaKhoanThu()).count();
			mapMaKhoanThuToSoLuong.put(khoanThuModel.getMaKhoanThu(), cntNopTien);
		}

		// thiet lap cac cot cho tableviews
		colTenPhi.setCellValueFactory(new PropertyValueFactory<KhoanThuModel, String>("tenKhoanThu"));
		try {
			colTongSoTien.setCellValueFactory(
					(CellDataFeatures<KhoanThuModel, String> p) -> new ReadOnlyStringWrapper(Double.toString(
							mapMaKhoanThuToSoLuong.get(p.getValue().getMaKhoanThu()) * p.getValue().getSoTien())));
		} catch (Exception e) {
			// TODO: handle exception
		}

		tvThongKe.setItems(listValueTableView);
		// thiet lap gia tri cho combobox
	}

	public void loc() throws ClassNotFoundException, SQLException {
		ObservableList<KhoanThuModel> listValueTableView_tmp = null;
		List<KhoanThuModel> listKhoanThuBatBuoc = new ArrayList<>();
		List<KhoanThuModel> listKhoanThuTuNguyen = new ArrayList<>();
		if(LoginController.ROLE==3) {
			for (KhoanThuModel khoanThuModel : listKhoanThu) {
				if (khoanThuModel.getLoaiKhoanThu() == 0) {
					listKhoanThuTuNguyen.add(khoanThuModel);
				} else {
					listKhoanThuBatBuoc.add(khoanThuModel);
				}
			}
		}

		// lay lua chon tim kiem cua khach hang
		SingleSelectionModel<String> typeSearch = cbChooseSearch.getSelectionModel();
		String typeSearchString = typeSearch.getSelectedItem();
		if(LoginController.ROLE==3) showThongKe();
		// else showThongKe1(new NhanKhauService().getListNhanKhau());
		switch (typeSearchString) {
		case "Tất cả":
			tvThongKe.setItems(listValueTableView);
			break;
		case "Bắt buộc":
			listValueTableView_tmp = FXCollections.observableArrayList(listKhoanThuBatBuoc);
			tvThongKe.setItems(listValueTableView_tmp);
			break;
		case "Tự nguyện":
			listValueTableView_tmp = FXCollections.observableArrayList(listKhoanThuTuNguyen);
			tvThongKe.setItems(listValueTableView_tmp);
			break;
		case "Tạm vắng":
			showThongKe1(new NhanKhauService().getListNhanKhauVang());
			vangLabel.setText("Số người tạm vắng: ");
			vangCnt.setText(new NhanKhauService().getSoTamVang()+"");
			break;
		case "Tạm trú":
			showThongKe1(new NhanKhauService().getListNhanKhauTamTru());
			vangLabel.setText("Số người tạm trú: ");
			vangCnt.setText(new NhanKhauService().getSoTamTru()+"");
			break;
		default:
			break;
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		if(LoginController.ROLE<=2) {
			ObservableList<String> listComboBox = FXCollections.observableArrayList("Tạm vắng", "Tạm trú");
			cbChooseSearch.setValue("Tạm vắng");
			cbChooseSearch.setItems(listComboBox);
		} else {
			ObservableList<String> listComboBox = FXCollections.observableArrayList("Tất cả", "Bắt buộc", "Tự nguyện");
			cbChooseSearch.setValue("Tất cả");
			cbChooseSearch.setItems(listComboBox);
		}
		try {
			if(LoginController.ROLE<=2) {
				showThongKe1(new NhanKhauService().getListNhanKhauVang());
				vangLabel.setText("Số người tạm vắng: ");
				vangCnt.setText(new NhanKhauService().getSoTamVang()+"");
			} else
					showThongKe();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}

	}
}

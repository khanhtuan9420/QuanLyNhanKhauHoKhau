package controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import models.HoKhauModel;
import models.KhoanThuModel;
import services.HoKhauService;
import services.KhoanThuService;
import services.NhanKhauService;

public class MainController implements Initializable{
	@FXML
	private Label lbSoHoKhau;

	@FXML
	private Label lbSoKhoanThu;

	@FXML
	private Label lbNhanKhau;

	@FXML
	private Label lbTamTru;
	@FXML
	private Label lbTamVang;
	@FXML
	private Label lbNam;
	@FXML
	private Label lbMauGiao;
	@FXML
	private Label lbDiHoc;
	@FXML
	private Label lbLaoDong;
	@FXML
	private Label lbNghiHuu;
	@FXML
	private Label lbNu;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			List<HoKhauModel> listHoKhau = new HoKhauService().getListHoKhau();
			long soHoKhau = listHoKhau.stream().count();
			lbSoHoKhau.setText(Long.toString(soHoKhau));
			
			List<KhoanThuModel> listKhoanThu = new KhoanThuService().getListKhoanThu();
			long soKhoanThu = listKhoanThu.stream().count();
			lbSoKhoanThu.setText(Long.toString(soKhoanThu));
			NhanKhauService nhanKhauService = new NhanKhauService();
			lbNhanKhau.setText(nhanKhauService.getSoNhanKhau()+"");
			lbTamTru.setText(nhanKhauService.getSoTamTru()+"");
			lbTamVang.setText(nhanKhauService.getSoTamVang()+"");
			lbNam.setText(nhanKhauService.getSoNam()+"");
			lbNu.setText((nhanKhauService.getSoNhanKhau()-nhanKhauService.getSoNam())+"");
			lbMauGiao.setText(nhanKhauService.getTuoi().get(0)+"");
			lbDiHoc.setText(nhanKhauService.getTuoi().get(1)+"");
			lbLaoDong.setText(nhanKhauService.getTuoi().get(2)+"");
			lbNghiHuu.setText(nhanKhauService.getTuoi().get(3)+"");

		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}

package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class LogOutController {
    @FXML
    Button noBtn;
    @FXML
    Button yesBtn;
    private static int isChoose=0;

    public static void setIsChoose() {
        if(isChoose==0) isChoose=1;
        else if(isChoose==2) isChoose=0;
        else if(isChoose==1) isChoose=0;
    }

    public static int getIsChoose() {
        return isChoose;
    }

    public void close(ActionEvent e) {
        isChoose=0;
        Stage stage = (Stage) ((Node) e.getTarget()).getScene().getWindow();
        stage.close();
    }

    public void logout(ActionEvent e) {
        isChoose=2;
        Stage stage = (Stage) ((Node) e.getTarget()).getScene().getWindow();
        stage.close();
    }
}

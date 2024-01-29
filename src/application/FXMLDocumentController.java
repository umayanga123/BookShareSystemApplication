package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class FXMLDocumentController implements Initializable {

	@FXML
	private Button close;

	@FXML
	private Button login_Btn;

	@FXML
	private Button minimize;

	@FXML
	private PasswordField password;

	@FXML
	private TextField studentNumber;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

	}

	@FXML
	public void minimize() {
		Stage stage = (Stage)minimize.getScene().getWindow();
		stage.setIconified(true);
	}

	@FXML
	public void exit() {
		System.exit(0);
	}

}

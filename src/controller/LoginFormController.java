package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import db.DBDataConstanst;
import db.Database;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LoginFormController implements Initializable {

	@FXML
	private Button close;

	@FXML
	private Button login_Btn;

	@FXML
	private Button reg_Btn;

	@FXML
	private Button minimize;

	@FXML
	private PasswordField password;

	@FXML
	private TextField studentNumber;

	private Connection connect;
	private PreparedStatement prepare;
	private ResultSet result;
	private double x = 0;
	private double y = 0;

	public void login() {

		String sql = "SELECT * FROM student WHERE studentNumber = ? and password = ?";

		connect = Database.connectDB();

		try {

			prepare = connect.prepareStatement(sql);
			prepare.setString(1, studentNumber.getText());
			prepare.setString(2, password.getText());
			result = prepare.executeQuery();

			Alert alert;

			if (studentNumber.getText().isEmpty() || password.getText().isEmpty()) {

				alert = new Alert(AlertType.ERROR);
				alert.setTitle("Admin Message");
				alert.setHeaderText(null);
				alert.setContentText("Please fill all blank fields.");
				alert.showAndWait();
			} else {
				if (result.next()) {

					DBDataConstanst.studentNumber = studentNumber.getText();

					// DON'T FORGET THIS!!!!
					DBDataConstanst.path = result.getString("image");

					alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Admin Message");
					alert.setHeaderText(null);
					alert.setContentText("Successfully Login!");
					alert.showAndWait();

					// TO HIDE THE LOGIN FORM
					login_Btn.getScene().getWindow().hide();

					// FOR DASHBOARD
					Parent root = FXMLLoader.load(getClass().getResource("/view/StudentDashboard.fxml"));

					Stage stage = new Stage();

					Scene scene = new Scene(root);

					root.setOnMousePressed((MouseEvent event) -> {
						x = event.getSceneX();
						y = event.getSceneY();
					});

					root.setOnMouseDragged((MouseEvent event) -> {

						stage.setX(event.getScreenX() - x);
						stage.setY(event.getScreenY() - y);

					});

					stage.initStyle(StageStyle.TRANSPARENT);

					stage.setScene(scene);
					stage.show();

				} else {
					alert = new Alert(AlertType.ERROR);
					alert.setTitle("Admin Message");
					alert.setHeaderText(null);
					alert.setContentText("Wrong Username or Password.");
					alert.showAndWait();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@FXML
	void regsiter(ActionEvent event) {
		// TO HIDE THE LOGIN FORM
		reg_Btn.getScene().getWindow().hide();

		// FOR DASHBOARD
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("/view/RegisterForm.fxml"));
			Stage stage = new Stage();
			Scene scene = new Scene(root);

			root.setOnMousePressed((MouseEvent e) -> {
				x = e.getSceneX();
				y = e.getSceneY();
			});

			root.setOnMouseDragged((MouseEvent e) -> {
				stage.setX(e.getScreenX() - x);
				stage.setY(e.getScreenY() - y);

			});

			stage.initStyle(StageStyle.TRANSPARENT);
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// ONLY NUMBERS ALLOWED
	public void numbersOnly(KeyEvent event) {

		if (event.getCharacter().matches("[^\\e\t\r\\d+$]")) {
			event.consume();

			studentNumber.setStyle("-fx-border-color:#e04040");
		} else {
			studentNumber.setStyle("-fx-border-color:#fff");
		}

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

	}

	@FXML
	public void minimize() {
		Stage stage = (Stage) minimize.getScene().getWindow();
		stage.setIconified(true);
	}

	@FXML
	public void exit() {
		System.exit(0);
	}

}

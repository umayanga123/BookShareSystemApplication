package controller;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import db.Database;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class RegisterFormController implements Initializable {

	@FXML
	private Button clear_Btn;

	@FXML
	private PasswordField confirmPassword_TextFeild;

	@FXML
	private Button exit;

	@FXML
	private Button logout_btn;

	@FXML
	private Button minimize;

	@FXML
	private PasswordField password_TextFeild;

	@FXML
	private ImageView register_imageView;

	@FXML
	private Button browse_btn;

	@FXML
	private Button reg_button;

	@FXML
	private TextField userName_textFeild;
	private double x = 0;
	private double y = 0;

	private Connection connect;
	private PreparedStatement prepare;
	private ResultSet result;

	/**
	 * Exit from System
	 */
	@FXML
	void exit() {
		System.exit(0);
	}

	/**
	 * Minimized the window
	 */
	@FXML
	void minimize() {
		Stage stage = (Stage) minimize.getScene().getWindow();
		stage.setIconified(true);
	}

	@FXML
	void logout(ActionEvent event) {
		try {
			if (event.getSource() == logout_btn) {
				// TO SWAP FROM DASHBOARD TO LOGIN FORM
				Parent root = FXMLLoader.load(getClass().getResource("/view/LoginForm.fxml"));

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

				logout_btn.getScene().getWindow().hide();

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/***
	 * Clear input button field
	 */
	@FXML
	void clear() {
		userName_textFeild.setText(null);
		password_TextFeild.setText(null);
		confirmPassword_TextFeild.setText(null);

		// Load the image
		Image image = new Image("file:/C:/Users/abans/eclipse-workspace/LibraryManagementSystem/src/image/logo1.jpg"); 
		// Set the image to the ImageView
		register_imageView.setImage(image);
	}

	/***
	 * Select Image
	 */
	public void insertImage() {
		// Create a FileChooser
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Select Image");

		// Set the initial directory
		fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));

		// Add image file filters
		fileChooser.getExtensionFilters()
				.addAll(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));

		// Show the file chooser dialog
		File selectedFile = fileChooser.showOpenDialog(null);

		// If a file is selected, set it as the image in the ImageView
		if (selectedFile != null) {
			Image image = new Image(selectedFile.toURI().toString());
			register_imageView.setImage(image);
		}
	}

	// ONLY NUMBERS ALLOWED
	public void numbersOnly(KeyEvent event) {
		if (event.getCharacter().matches("[^\\e\t\r\\d+$]")) {
			event.consume();
			userName_textFeild.setStyle("-fx-border-color:#e04040");
		} else {
			userName_textFeild.setStyle("-fx-border-color:#fff");
		}

	}

	/***
	 * Doing user registration
	 * 
	 * @param event
	 */
	@FXML
	void registerUser() {
		Alert alert;
		String password = password_TextFeild.getText();
		String confirmPassword = confirmPassword_TextFeild.getText();
		if (userName_textFeild.getText().isBlank() || password.isBlank() || confirmPassword.isBlank()
				|| register_imageView.getImage().getUrl().isBlank()) {
			alert = new Alert(AlertType.ERROR);
			alert.setTitle("Admin Message");
			alert.setHeaderText(null);
			alert.setContentText("Please fill all blank fields.");
			alert.showAndWait();
		} else if (!password.equals(confirmPassword)) {
			alert = new Alert(AlertType.ERROR);
			alert.setTitle("Admin Message");
			alert.setHeaderText(null);
			alert.setContentText("Password and Confirm password mismatch.");
			alert.showAndWait();
		} else if (this.isUserExsistInDB()) {
			alert = new Alert(AlertType.ERROR);
			alert.setTitle("Admin Message");
			alert.setHeaderText(null);
			alert.setContentText("User Name already content in DB");
			alert.showAndWait();
		} else {
			if (registerNewUser() > 0) {
				alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Admin Message");
				alert.setHeaderText(null);
				alert.setContentText("Suscssfully register new user to the system");
				alert.showAndWait();
				clear();
			}
		}

	}

	/**
	 * Check user already in db
	 * 
	 * @return boolean
	 * @throws SQLException
	 */
	private boolean isUserExsistInDB() {
		boolean userExists = false;
		try {
			String sql = "SELECT COUNT(*) FROM student WHERE studentNumber = ?";
			connect = Database.connectDB();
			prepare = connect.prepareStatement(sql);
			prepare.setString(1, userName_textFeild.getText());
			result = prepare.executeQuery();

			// Check if any rows were returned
			if (result.next() && result.getInt(1) > 0) {
				userExists = true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return userExists;
	}

	/**
	 * register new user already in DB
	 * 
	 * @return boolean
	 * @throws SQLException
	 */
	private int registerNewUser() {
		try {
			String sql = "INSERT INTO student VALUES (?,?,?)";
			String url = register_imageView.getImage().getUrl();
			// Convert the URL to a File object
			File file = new File(url);
			// Get the absolute path from the File object
			String absolutePath = file.getPath().replace("file:\\", "");
			connect = Database.connectDB();
			prepare = connect.prepareStatement(sql);
			prepare.setString(1, userName_textFeild.getText());
			prepare.setString(2, password_TextFeild.getText());
			prepare.setString(3, absolutePath);
			return prepare.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

	}

}

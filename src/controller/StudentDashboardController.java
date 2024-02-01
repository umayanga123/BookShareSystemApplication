package controller;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import db.DBDataConstanst;
import db.Database;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import model.AvailableBooks;
import model.ReturnBook;
import model.SaveBook;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class StudentDashboardController implements Initializable {

	@FXML
	private Button close;

	@FXML
	private Button minimize;

	@FXML
	private Button bars_btn;

	@FXML
	private Button arrow_btn;

	@FXML
	private AnchorPane nav_form;

	@FXML
	private Circle circle_image;

	@FXML
	private Label studentNumber_label;

	@FXML
	private Button availableBooks_btn;

	@FXML
	private Button issueBooks_btn;

	@FXML
	private Button bookPage_btn;

	@FXML
	private Button halfNav_bookPageBtn;

	@FXML
	private Button returnBooks_btn;

	@FXML
	private Button savedBooks_btn;

	@FXML
	private Button edit_btn;

	@FXML
	private Button logout_btn;

	@FXML
	private AnchorPane availableBooks_form;

	@FXML
	private AnchorPane bookPage_form;

	@FXML
	private ImageView bookPage_imageViewer;

	@FXML
	private ComboBox<String> bookType_ComboBox;

	@FXML
	private Button bpAddBookBtn;

	@FXML
	private Button bookPageBrowse_Btn;

	@FXML
	private TextField bpAuthor_Text;

	@FXML
	private Button bpClearBtn;

	@FXML
	private TextField bpTitle_Text;

	@FXML
	private DatePicker bookPageDateChooser;

	@FXML
	private TableView<AvailableBooks> availableBooks_tableView;

	@FXML
	private TableColumn<?, ?> col_ab_bookTitle;

	@FXML
	private TableColumn<AvailableBooks, String> col_ab_author;

	@FXML
	private TableColumn<AvailableBooks, String> col_ab_bookType;

	@FXML
	private TableColumn<AvailableBooks, String> col_ab_publishedDate;

	@FXML
	private ImageView availableBooks_imageView;

	@FXML
	private Button save_btn;

	@FXML
	private Label availableBooks_title;

	@FXML
	private Button take_btn;

	@FXML
	private AnchorPane halfNav_form;

	@FXML
	private Circle smallCircle_image;

	@FXML
	private Button halfNav_availableBtn;

	@FXML
	private Button halfNav_takeBtn;

	@FXML
	private Button halfNav_returnBtn;

	@FXML
	private Button halfNav_saveBtn;

	@FXML
	private AnchorPane mainCenter_form;

	@FXML
	private AnchorPane issue_form;

	@FXML
	private AnchorPane returnBook_form;

	@FXML
	private AnchorPane savedBook_form;

	@FXML
	private Label currentForm_label;

	@FXML
	private Label take_StudentNumber;

	@FXML
	private TextField take_FirstName;

	@FXML
	private TextField take_LastName;

	@FXML
	private ComboBox<String> take_Gender;

	@FXML
	private TextField take_BookTitle;

	@FXML
	private Label take_IssuedDate;

	@FXML
	private ImageView take_imageView;

	@FXML
	private Label take_titleLabel;

	@FXML
	private Label take_authorLabel;

	@FXML
	private Label take_genreLabel;

	@FXML
	private Label take_dateLabel;

	@FXML
	private Button take_clearBtn;

	@FXML
	private Button take_takeBtn;

	@FXML
	private TableView<ReturnBook> return_tableView;

	@FXML
	private TableColumn<ReturnBook, String> col_return_BookTitle;

	@FXML
	private TableColumn<ReturnBook, String> col_return_Author;

	@FXML
	private TableColumn<ReturnBook, String> col_return_bookType;

	@FXML
	private TableColumn<ReturnBook, String> col_return_dateIssue;

	@FXML
	private ImageView return_imageView;

	@FXML
	private Button return_button;

	@FXML
	private Label returnBookTitle;

	@FXML
	private TableView<SaveBook> save_tableView;

	@FXML
	private TableColumn<SaveBook, String> col_saveTitle;

	@FXML
	private TableColumn<SaveBook, String> col_saveAuthor;

	@FXML
	private TableColumn<SaveBook, String> col_saveGenre;

	@FXML
	private TableColumn<SaveBook, String> col_saveDate;

	@FXML
	private ImageView save_imageView;

	@FXML
	private Button unsaveBtn;

	@FXML
	private FontAwesomeIcon edit_icon;

	private Image image;

	private Connection connect;
	private PreparedStatement prepare;
	private Statement statement;
	private ResultSet result;

	private String comboBox[] = { "Male", "Female", "Others" };
	private String bookTypeComboBox[] = { "Non-fiction", "fiction", "Novels", "Others" };

	/**
	 * Adding gender to combo box
	 */
	public void addGenders() {
		List<String> combo = new ArrayList<>();
		for (String data : comboBox) {
			combo.add(data);
		}

		ObservableList<String> list = FXCollections.observableList(combo);
		take_Gender.setItems(list);
	}

	/***
	 * Adding book type to combo box
	 */
	public void addBookTypes() {
		List<String> combo = new ArrayList<>();
		for (String data : bookTypeComboBox) {
			combo.add(data);
		}

		ObservableList<String> list = FXCollections.observableList(combo);
		bookType_ComboBox.setItems(list);

	}

	public void takeBook() {

		Date date = new Date();
		java.sql.Date sqlDate = new java.sql.Date(date.getTime());

		String sql = "INSERT INTO take VALUES (?,?,?,?,?,?,?,?,?,?)";

		connect = Database.connectDB();

		try {

			Alert alert;

			if (take_FirstName.getText().isEmpty() || take_LastName.getText().isEmpty()
					|| take_Gender.getSelectionModel().isEmpty()) {

				alert = new Alert(AlertType.ERROR);
				alert.setTitle("Admin Message");
				alert.setHeaderText(null);
				alert.setContentText("Please type complete Student Details");
				alert.showAndWait();
			} else if (take_titleLabel.getText().isEmpty()) {
				alert = new Alert(AlertType.ERROR);
				alert.setTitle("Admin Message");
				alert.setHeaderText(null);
				alert.setContentText("Please indicate the book you want to take.");
				alert.showAndWait();
			} else {

				prepare = connect.prepareStatement(sql);
				prepare.setString(1, take_StudentNumber.getText());
				prepare.setString(2, take_FirstName.getText());
				prepare.setString(3, take_LastName.getText());
				prepare.setString(4, (String) take_Gender.getSelectionModel().getSelectedItem());
				prepare.setString(5, take_titleLabel.getText());
				prepare.setString(6, take_authorLabel.getText());
				prepare.setString(7, take_genreLabel.getText());
				prepare.setString(8, DBDataConstanst.path);
				prepare.setDate(9, sqlDate);

				String check = "Not Return";

				prepare.setString(10, check);
				prepare.executeUpdate();

				alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Admin Message");
				alert.setHeaderText(null);
				alert.setContentText("Successful!y take the book!");
				alert.showAndWait();

				clearTakeData();

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void findBook(ActionEvent event) {

		clearFindData();

		String sql = "SELECT * FROM book WHERE bookTitle = '" + take_BookTitle.getText() + "'";

		connect = Database.connectDB();

		try {

			prepare = connect.prepareStatement(sql);
			result = prepare.executeQuery();
			boolean check = false;

			Alert alert;

			if (take_BookTitle.getText().isEmpty()) {

				alert = new Alert(AlertType.ERROR);
				alert.setTitle("Admin Message");
				alert.setHeaderText(null);
				alert.setContentText("Please select the book.");
				alert.showAndWait();

			} else {

				while (result.next()) {

					take_titleLabel.setText(result.getString("bookTitle"));
					take_authorLabel.setText(result.getString("author"));
					take_genreLabel.setText(result.getString("bookType"));
					take_dateLabel.setText(result.getString("date"));

					DBDataConstanst.path = result.getString("image");

					String uri = "file:" + DBDataConstanst.path;

					image = new Image(uri, 127, 162, false, true);
					take_imageView.setImage(image);

					check = true;
				}

				if (!check) {
					take_titleLabel.setText("Book is not available!");
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void studentNumberLabel() {
		take_StudentNumber.setText(DBDataConstanst.studentNumber);
	}

	public void clearTakeData() {
		take_BookTitle.setText("");
		take_titleLabel.setText("");
		take_authorLabel.setText("");
		take_genreLabel.setText("");
		take_dateLabel.setText("");
		take_imageView.setImage(null);

	}

	public void clearFindData() {
		take_titleLabel.setText("");
		take_authorLabel.setText("");
		take_genreLabel.setText("");
		take_dateLabel.setText("");
		take_imageView.setImage(null);
	}

	public void displayDate() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String date = format.format(new java.util.Date());
		take_IssuedDate.setText(date);
	}

	// RETURN FORM
	public ObservableList<ReturnBook> returnBook() {

		ObservableList<ReturnBook> bookReturnData = FXCollections.observableArrayList();

		String check = "Not Return";

		String sql = "SELECT * FROM take WHERE checkReturn = '" + check + "' and studentNumber = '"
				+ DBDataConstanst.studentNumber + "'";

		connect = Database.connectDB();

		try {

			ReturnBook rBook;

			prepare = connect.prepareStatement(sql);
			result = prepare.executeQuery();

			while (result.next()) {

				rBook = new ReturnBook(result.getString("bookTitle"), result.getString("author"),
						result.getString("bookType"), result.getString("image"), result.getDate("date"));
				bookReturnData.add(rBook);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return bookReturnData;

	}

	public void returnBooks() {

		String sql = "UPDATE take SET checkReturn = 'Returned' WHERE bookTitle = '" + DBDataConstanst.takeBookTitle
				+ "'";

		connect = Database.connectDB();

		Alert alert;

		try {

			if (return_imageView.getImage() == null) {

				alert = new Alert(AlertType.ERROR);
				alert.setTitle("Admin Message");
				alert.setHeaderText(null);
				alert.setContentText("Please select the book you want to return");
				alert.showAndWait();

			} else {

				statement = connect.createStatement();
				statement.executeUpdate(sql);

				alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Admin Message");
				alert.setHeaderText(null);
				alert.setContentText("Successfully returned!");
				alert.showAndWait();

				showBookReturn();

				return_imageView.setImage(null);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	ObservableList<ReturnBook> retBook;

	public void showBookReturn() {

		retBook = returnBook();
		col_return_BookTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
		col_return_Author.setCellValueFactory(new PropertyValueFactory<>("author"));
		col_return_bookType.setCellValueFactory(new PropertyValueFactory<>("genre"));
		col_return_dateIssue.setCellValueFactory(new PropertyValueFactory<>("date"));

		return_tableView.setItems(retBook);

	}

	public void selectReturnBook() {

		ReturnBook rBook = return_tableView.getSelectionModel().getSelectedItem();
		int num = return_tableView.getSelectionModel().getFocusedIndex();

		if ((num - 1) < -1) {
			return;
		}

		String uri = "file:" + rBook.getImage();

		image = new Image(uri, 143, 181, false, true);
		return_imageView.setImage(image);

		DBDataConstanst.takeBookTitle = rBook.getTitle();
	}

	public ObservableList<AvailableBooks> dataList() {

		ObservableList<AvailableBooks> listBooks = FXCollections.observableArrayList();
		String sql = "SELECT * FROM book";
		connect = Database.connectDB();

		try {

			AvailableBooks aBooks;
			prepare = connect.prepareStatement(sql);
			result = prepare.executeQuery();

			while (result.next()) {

				aBooks = new AvailableBooks(result.getString("bookTitle"), result.getString("author"),
						result.getString("bookType"), result.getString("image"), result.getDate("date"));

				listBooks.add(aBooks);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return listBooks;
	}

	// SAVED BOOKS
	public ObservableList<SaveBook> savedBooksData() {

		ObservableList<SaveBook> listSaveData = FXCollections.observableArrayList();

		String sql = "SELECT * FROM save WHERE studentNumber = '" + DBDataConstanst.studentNumber + "'";

		connect = Database.connectDB();

		try {

			SaveBook sBook;

			prepare = connect.prepareStatement(sql);
			result = prepare.executeQuery();

			while (result.next()) {

				sBook = new SaveBook(result.getString("bookTitle"), result.getString("author"),
						result.getString("bookType"), result.getString("image"), result.getDate("date"));

				listSaveData.add(sBook);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return listSaveData;
	}

	private ObservableList<SaveBook> sBookList;

	public void showSavedBooks() {

		sBookList = savedBooksData();

		col_saveTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
		col_saveAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));
		col_saveGenre.setCellValueFactory(new PropertyValueFactory<>("genre"));
		col_saveDate.setCellValueFactory(new PropertyValueFactory<>("date"));

		save_tableView.setItems(sBookList);

	}

	/***
	 * Mouse click funtion to show saved book image
	 */
	public void selectSavedBooks() {

		SaveBook sBook = save_tableView.getSelectionModel().getSelectedItem();
		int num = save_tableView.getSelectionModel().getFocusedIndex();

		if ((num - 1) < -1 || sBook == null) {
			return;
		}

		String uri = "file:" + sBook.getImage();

		image = new Image(uri, 117, 148, false, true);
		save_imageView.setImage(image);

		DBDataConstanst.savedImage = sBook.getImage();
		DBDataConstanst.savedTitle = sBook.getTitle();

	}

	public void saveBooks() {

		String sql = "INSERT INTO save VALUES (?,?,?,?,?,?)";

		connect = Database.connectDB();

		try {

			Alert alert;

			if (availableBooks_title.getText().isEmpty()) {
				alert = new Alert(AlertType.ERROR);
				alert.setTitle("Admin Message");
				alert.setHeaderText(null);
				alert.setContentText("Please select the book");
				alert.showAndWait();
			} else {
				prepare = connect.prepareStatement(sql);
				prepare.setString(1, DBDataConstanst.studentNumber);
				prepare.setString(2, DBDataConstanst.savedTitle);
				prepare.setString(3, DBDataConstanst.savedAuthor);
				prepare.setString(4, DBDataConstanst.savedGenre);
				prepare.setString(5, DBDataConstanst.savedImage);
				prepare.setDate(6, DBDataConstanst.savedDate);
				prepare.executeUpdate();

				alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Admin Message");
				alert.setHeaderText(null);
				alert.setContentText("Successfully Saved.");
				alert.showAndWait();

				showSavedBooks();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void unsaveBooks() {

		String sql = "DELETE FROM save WHERE bookTitle = '" + DBDataConstanst.savedTitle + "'"
				+ " and studentNumber = '" + DBDataConstanst.studentNumber + "'";

		connect = Database.connectDB();

		try {

			Alert alert;

			if (save_imageView.getImage() == null) {

				alert = new Alert(AlertType.ERROR);
				alert.setTitle("Admin Message");
				alert.setHeaderText(null);
				alert.setContentText("Please Select the book you want to unsave");
				alert.showAndWait();

			} else {

				statement = connect.createStatement();
				statement.executeUpdate(sql);

				alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Admin Message");
				alert.setHeaderText(null);
				alert.setContentText("Successfully unsaved.");
				alert.showAndWait();

				showSavedBooks();

				save_imageView.setImage(null);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// SHOWING BOOKS DATA
	private ObservableList<AvailableBooks> listBook;

	public void showAvailableBooks() {

		listBook = dataList();

		col_ab_bookTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
		col_ab_author.setCellValueFactory(new PropertyValueFactory<>("author"));
		col_ab_bookType.setCellValueFactory(new PropertyValueFactory<>("genre"));
		col_ab_publishedDate.setCellValueFactory(new PropertyValueFactory<>("date"));

		availableBooks_tableView.setItems(listBook);

	}

	public void selectAvailableBooks() {

		AvailableBooks bookData = availableBooks_tableView.getSelectionModel().getSelectedItem();

		int num = availableBooks_tableView.getSelectionModel().getFocusedIndex();

		if ((num - 1) < -1) {
			return;
		}

		availableBooks_title.setText(bookData.getTitle());

		// THIS IS REQUIRED TO DISPLAY THE IMAGE
		// NOTE! DON'T FORGET THE "file:"
		String uri = "file:" + bookData.getImage();

		image = new Image(uri, 134, 171, false, true);

		availableBooks_imageView.setImage(image);

		DBDataConstanst.takeBookTitle = bookData.getTitle();

		DBDataConstanst.savedTitle = bookData.getTitle();
		DBDataConstanst.savedAuthor = bookData.getAuthor();
		DBDataConstanst.savedGenre = bookData.getGenre();
		DBDataConstanst.savedImage = bookData.getImage();
		DBDataConstanst.savedDate = bookData.getDate();

	}

	public void abTakeButton(ActionEvent event) {

		if (event.getSource() == take_btn) {
			issue_form.setVisible(true);
			availableBooks_form.setVisible(false);
			savedBook_form.setVisible(false);
			returnBook_form.setVisible(false);
			bookPage_form.setVisible(false);

			issueBooks_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #46589a, #4278a7);");
			availableBooks_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");
			returnBooks_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");
			savedBooks_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");
			bookPage_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");

			halfNav_takeBtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #46589a, #4278a7);");
			halfNav_availableBtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");
			halfNav_returnBtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");
			halfNav_saveBtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");
			halfNav_bookPageBtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");

			currentForm_label.setText("Issue Books");

		}
	}

	public void studentNumber() {
		// WE CAN DISPLAY THE STUDENT NUMBER THAT STUDENT USED
		studentNumber_label.setText(DBDataConstanst.studentNumber);
	}

	public void insertImage() {
		FileChooser open = new FileChooser();
		open.setTitle("Image File");
		open.getExtensionFilters().add(new ExtensionFilter("Image file", "*png", "*jpg"));
		Stage stage = (Stage) nav_form.getScene().getWindow();

		File file = open.showOpenDialog(stage);
		if (file != null) {
			image = new Image(file.toURI().toString(), 112, 84, false, true);
			circle_image.setFill(new ImagePattern(image));
			smallCircle_image.setFill(new ImagePattern(image));
			DBDataConstanst.path = file.getAbsolutePath();
			changeProfile();
		}
	}

	public void changeProfile() {
		String uri = DBDataConstanst.path;
		uri = uri.replace("\\", "\\\\");
		String sql = "UPDATE student SET image = '" + uri + "' WHERE studentNumber = '" + DBDataConstanst.studentNumber
				+ "'";
		connect = Database.connectDB();
		try {
			statement = connect.createStatement();
			statement.executeUpdate(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void showProfile() {
		String uri = "file:" + DBDataConstanst.path;
		image = new Image(uri, 112, 84, false, true);
		circle_image.setFill(new ImagePattern(image));
		smallCircle_image.setFill(new ImagePattern(image));
	}

	public void designInserImage() {

		edit_btn.setVisible(false);

		circle_image.setOnMouseEntered((MouseEvent event) -> {
			edit_btn.setVisible(true);
		});

		circle_image.setOnMouseExited((MouseEvent event) -> {
			edit_btn.setVisible(false);
		});

		edit_btn.setOnMouseEntered((MouseEvent event) -> {
			edit_btn.setVisible(true);
			edit_icon.setFill(Color.valueOf("#fff"));
		});

		edit_btn.setOnMousePressed((MouseEvent event) -> {
			edit_btn.setVisible(true);
			edit_icon.setFill(Color.RED);
		});

		edit_btn.setOnMouseExited((MouseEvent event) -> {
			edit_btn.setVisible(false);
		});

	}

	public void sideNavButtonDesign(ActionEvent event) {

		if (event.getSource() == halfNav_availableBtn) {

			issue_form.setVisible(false);
			availableBooks_form.setVisible(true);
			savedBook_form.setVisible(false);
			returnBook_form.setVisible(false);
			bookPage_form.setVisible(false);

			availableBooks_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #46589a, #4278a7);");
			issueBooks_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");
			returnBooks_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");
			savedBooks_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");
			bookPage_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");

			halfNav_availableBtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #46589a, #4278a7);");
			halfNav_takeBtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");
			halfNav_returnBtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");
			halfNav_saveBtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");
			halfNav_bookPageBtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");

			currentForm_label.setText("Available Books");

		} else if (event.getSource() == halfNav_takeBtn) {

			issue_form.setVisible(true);
			availableBooks_form.setVisible(false);
			savedBook_form.setVisible(false);
			returnBook_form.setVisible(false);
			bookPage_form.setVisible(false);

			issueBooks_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #46589a, #4278a7);");
			availableBooks_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");
			returnBooks_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");
			savedBooks_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");
			bookPage_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");

			halfNav_takeBtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #46589a, #4278a7);");
			halfNav_availableBtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");
			halfNav_returnBtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");
			halfNav_saveBtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");
			halfNav_bookPageBtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");

			currentForm_label.setText("Issue Books");

		} else if (event.getSource() == halfNav_returnBtn) {

			issue_form.setVisible(false);
			availableBooks_form.setVisible(false);
			savedBook_form.setVisible(false);
			returnBook_form.setVisible(true);
			bookPage_form.setVisible(false);

			returnBooks_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #46589a, #4278a7);");
			availableBooks_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");
			issueBooks_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");
			savedBooks_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");
			bookPage_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");

			halfNav_returnBtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #46589a, #4278a7);");
			halfNav_takeBtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");
			halfNav_availableBtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");
			halfNav_saveBtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");
			halfNav_bookPageBtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");

			currentForm_label.setText("Return Books");

			showBookReturn();

		} else if (event.getSource() == halfNav_saveBtn) {

			issue_form.setVisible(false);
			availableBooks_form.setVisible(false);
			savedBook_form.setVisible(true);
			returnBook_form.setVisible(false);
			bookPage_form.setVisible(false);

			savedBooks_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #46589a, #4278a7);");
			availableBooks_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");
			issueBooks_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");
			returnBooks_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");
			bookPage_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");

			halfNav_saveBtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #46589a, #4278a7);");
			halfNav_takeBtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");
			halfNav_returnBtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");
			halfNav_availableBtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");
			halfNav_bookPageBtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");

			currentForm_label.setText("Saved Books");

		} else if (event.getSource() == halfNav_bookPageBtn) {

			issue_form.setVisible(false);
			availableBooks_form.setVisible(false);
			savedBook_form.setVisible(false);
			returnBook_form.setVisible(false);
			bookPage_form.setVisible(true);

			savedBooks_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");
			availableBooks_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");
			issueBooks_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");
			returnBooks_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");
			bookPage_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #46589a, #4278a7);");

			halfNav_saveBtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");
			halfNav_takeBtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");
			halfNav_returnBtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");
			halfNav_availableBtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");
			halfNav_bookPageBtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #46589a, #4278a7);");

			currentForm_label.setText("Saved Books");

		}

	}

	public void navButtonDesign(ActionEvent event) {

		if (event.getSource() == availableBooks_btn) {

			issue_form.setVisible(false);
			availableBooks_form.setVisible(true);
			savedBook_form.setVisible(false);
			returnBook_form.setVisible(false);
			bookPage_form.setVisible(false);

			availableBooks_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #46589a, #4278a7);");
			issueBooks_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");
			returnBooks_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");
			savedBooks_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");
			bookPage_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");

			halfNav_availableBtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #46589a, #4278a7);");
			halfNav_takeBtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");
			halfNav_returnBtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");
			halfNav_saveBtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");
			halfNav_bookPageBtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");

			currentForm_label.setText("Available Books");

		} else if (event.getSource() == issueBooks_btn) {

			issue_form.setVisible(true);
			availableBooks_form.setVisible(false);
			savedBook_form.setVisible(false);
			returnBook_form.setVisible(false);
			bookPage_form.setVisible(false);

			issueBooks_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #46589a, #4278a7);");
			availableBooks_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");
			returnBooks_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");
			savedBooks_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");
			bookPage_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");

			halfNav_takeBtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #46589a, #4278a7);");
			halfNav_availableBtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");
			halfNav_returnBtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");
			halfNav_saveBtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");
			halfNav_bookPageBtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");

			currentForm_label.setText("Issue Books");

		} else if (event.getSource() == returnBooks_btn) {

			issue_form.setVisible(false);
			availableBooks_form.setVisible(false);
			savedBook_form.setVisible(false);
			returnBook_form.setVisible(true);
			bookPage_form.setVisible(false);

			returnBooks_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #46589a, #4278a7);");
			availableBooks_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");
			issueBooks_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");
			savedBooks_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");
			bookPage_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");

			halfNav_returnBtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #46589a, #4278a7);");
			halfNav_takeBtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");
			halfNav_availableBtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");
			halfNav_saveBtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");
			halfNav_bookPageBtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");

			currentForm_label.setText("Return Books");

			showBookReturn();

		} else if (event.getSource() == savedBooks_btn) {

			issue_form.setVisible(false);
			availableBooks_form.setVisible(false);
			savedBook_form.setVisible(true);
			returnBook_form.setVisible(false);
			bookPage_form.setVisible(false);

			savedBooks_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #46589a, #4278a7);");
			availableBooks_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");
			issueBooks_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");
			returnBooks_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");
			bookPage_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");

			halfNav_saveBtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #46589a, #4278a7);");
			halfNav_takeBtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");
			halfNav_returnBtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");
			halfNav_availableBtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");
			halfNav_bookPageBtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");

			currentForm_label.setText("Saved Books");

			showSavedBooks();

		} else if (event.getSource() == bookPage_btn) {

			issue_form.setVisible(false);
			availableBooks_form.setVisible(false);
			savedBook_form.setVisible(false);
			returnBook_form.setVisible(false);
			bookPage_form.setVisible(true);

			savedBooks_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");
			availableBooks_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");
			issueBooks_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");
			returnBooks_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");
			bookPage_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #46589a, #4278a7);");

			halfNav_saveBtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");
			halfNav_takeBtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");
			halfNav_returnBtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");
			halfNav_availableBtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #344275, #3a6389);");
			halfNav_bookPageBtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #46589a, #4278a7);");

			currentForm_label.setText("Book Page");

		}
	}

	/***
	 * Slider arrow button funtinality implment
	 */
	private double x = 0;
	private double y = 0;

	public void sliderArrow() {

		TranslateTransition slide = new TranslateTransition();

		slide.setDuration(Duration.seconds(.5));
		slide.setNode(nav_form);
		slide.setToX(-224);

		TranslateTransition slide1 = new TranslateTransition();

		slide1.setDuration(Duration.seconds(.5));
		slide1.setNode(mainCenter_form);
		slide1.setToX(-224 + 90);

		TranslateTransition slide2 = new TranslateTransition();
		slide2.setDuration(Duration.seconds(.5));
		slide2.setNode(halfNav_form);
		slide2.setToX(0);

		slide.setOnFinished((ActionEvent event) -> {

			arrow_btn.setVisible(false);
			bars_btn.setVisible(true);

		});

		slide2.play();
		slide1.play();
		slide.play();

	}

	public void sliderBars() {

		TranslateTransition slide = new TranslateTransition();

		slide.setDuration(Duration.seconds(.5));
		slide.setNode(nav_form);
		slide.setToX(0);

		TranslateTransition slide1 = new TranslateTransition();

		slide1.setDuration(Duration.seconds(.5));
		slide1.setNode(mainCenter_form);
		slide1.setToX(0);

		TranslateTransition slide2 = new TranslateTransition();
		slide2.setDuration(Duration.seconds(.5));
		slide2.setNode(halfNav_form);
		slide2.setToX(-77);

		slide.setOnFinished((ActionEvent event) -> {

			arrow_btn.setVisible(true);
			bars_btn.setVisible(false);

		});

		slide2.play();
		slide1.play();
		slide.play();
	}

	/***
	 * Log out from the Student portal
	 * 
	 * @param event
	 */
	@FXML
	public void logout(ActionEvent event) {
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
	 * Exit or close open view
	 */
	public void exit() {
		System.exit(0);

	}

	/***
	 * Minimized the open view
	 */
	public void minimize() {
		Stage stage = (Stage) minimize.getScene().getWindow();
		stage.setIconified(true);
	}

	/**
	 * clear book page field
	 */
	public void bookPageClear() {
		bpAuthor_Text.setText(null);
		bpTitle_Text.setText(null);
		
		// Load the image
		Image image = new Image("file:/C:/Users/abans/eclipse-workspace/LibraryManagementSystem/src/image/logo1.jpg"); 
		// Set the image to the ImageView
		bookPage_imageViewer.setImage(image);
	}

	/***
	 * Browse image and set to image view
	 */
	public void bookPageBrowseImage() {
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
			bookPage_imageViewer.setImage(image);
		}
	}

	/***
	 * initialized view data with loading.
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		designInserImage();

		showProfile();

		// TO SHOW THE AVAILABLE BOOKS
		showAvailableBooks();

		studentNumber();

		studentNumberLabel();

		displayDate();

		addGenders();

		addBookTypes();

		showSavedBooks();

		// RETURN FORM
		showBookReturn();

	}

}

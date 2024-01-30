module LibraryManagementSystem {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;
	requires java.sql;
	requires javafx.base;
	requires fontawesomefx;
	
	opens application to javafx.graphics, javafx.fxml,javafx.base;
}

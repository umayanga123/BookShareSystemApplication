module LibraryManagementSystem {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;
	requires java.sql;
	requires javafx.base;
	requires fontawesomefx;

	
	opens application to javafx.graphics, javafx.fxml,javafx.base;
	opens view to javafx.graphics, javafx.fxml,javafx.base;
	opens controller to javafx.graphics, javafx.fxml,javafx.base;
	opens model to javafx.graphics, javafx.fxml,javafx.base;
}

package application;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.fxml.FXMLLoader;

public class LibraryManagement extends Application {

	private double x = 0;
	private double y = 0;

	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/view/FXMLDocument.fxml"));
		Scene scene = new Scene(root);
		root.setOnMousePressed((MouseEvent event) -> {
			x = event.getSceneX();
			y = event.getSceneY();

		});

		root.setOnMouseDragged((MouseEvent event) -> {
			primaryStage.setX(event.getScreenX() - x);
			primaryStage.setY(event.getScreenY() - y);
		});

		primaryStage.initStyle(StageStyle.TRANSPARENT);
		primaryStage.setScene(scene);
		primaryStage.show();

	}

	public static void main(String[] args) {
		launch(args);
	}
}

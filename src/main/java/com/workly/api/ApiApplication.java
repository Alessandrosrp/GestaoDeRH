package com.workly.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

@SpringBootApplication
public class ApiApplication extends Application {

	public static ConfigurableApplicationContext context;

	public static void main(String[] args) {
		launch();
		SpringApplication.run(ApiApplication.class, args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		context = SpringApplication.run(ApiApplication.class);
		FXMLLoader fxml = new FXMLLoader(getClass().getResource("/com/workly/api/login/login.fxml"));
		fxml.setControllerFactory(context::getBean);

		Scene scene = new Scene(fxml.load());
		stage.setTitle("Workly");
		stage.setScene(scene);
		stage.show();
	}

}

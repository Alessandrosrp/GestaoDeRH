package com.workly.api;

import java.net.URI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

@SpringBootApplication
public class ApiApplication extends Application {

    private static ConfigurableApplicationContext context;

    public static void main(String[] args) {
		// Inicializa Spring Boot primeiro
        context = SpringApplication.run(ApiApplication.class, args);
		
		// Lança JavaFX
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxml = new FXMLLoader(getClass().getResource("/com/workly/api/login/login.fxml"));
        fxml.setControllerFactory(context::getBean);

        Scene scene = new Scene(fxml.load());
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/com/workly/api/imagens/logo.png")));
        stage.setTitle("Workly");
        stage.setScene(scene);
        stage.show();

		// Abre navegador usando JavaFX
    	getHostServices().showDocument("http://localhost:8080/html/teladelogin.html");
    }

    @Override
    public void stop() throws Exception {
        context.close();
    }
}

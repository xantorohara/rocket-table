package io.github.xantorohara.rocket_table;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.util.List;
import java.util.Map;


public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage stage) throws Exception {
        stage.setTitle(Const.VERSION);

        stage.getIcons().addAll(new Image(getClass().getResourceAsStream("icon.png")));


        FXMLLoader loader = new FXMLLoader(getClass().getResource("RocketTable.fxml"));
        Parent root = loader.load();
        RocketTable controller = loader.getController();

        stage.setScene(new Scene(root, 960, 540));
        controller.init(stage);
        stage.show();

        List<String> args = getParameters().getUnnamed();
        Map<String, String> namedParameters = getParameters().getNamed();

        if (namedParameters.containsKey("encoding")) {
            controller.setEncoding(namedParameters.get("encoding"));
        }

        if (!args.isEmpty()) {
            controller.openFile(new File(args.get(0)));
        }

        SplashScreen splashScreen = SplashScreen.getSplashScreen();
        if (splashScreen != null) {
            splashScreen.close();
        }
    }
}

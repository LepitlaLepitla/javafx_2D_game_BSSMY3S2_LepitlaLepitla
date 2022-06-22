package com.example.lepitlalepitla_2d_game_javafx;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Objects;

public class HelloApplication extends Application {
    Label score_panel = new Label("Your score is : 0");
    Rectangle air_plane = new Rectangle();
    Pane environment = new Pane();
    HBox end_box = new HBox();
    Scene end_scene = new Scene(end_box, 500, 300);

    int score = 0;
    Stage end_game = new Stage();


    Rectangle2D screenBounds = Screen. getPrimary(). getBounds();
    VBox root = new VBox();
    Scene scene = new Scene(root, screenBounds.getWidth(), screenBounds.getHeight()-80);
    @Override
    public void start(Stage stage) throws IOException {



        //setting the stage to exit when the end stage is close
        end_game.setOnCloseRequest(e -> Platform.exit());

        //root of the application

        root.getChildren().addAll(score_panel, game_environment());



        //setting the click listeners for the scene
        scene.setOnKeyPressed(keyEvent -> {
            switch (keyEvent.getCode()){
                case UP -> {
                    if(!(air_plane.getY() <= 0)){
                        air_plane.setY(air_plane.getY() - 20);
                    }
                }
                case DOWN -> {
                    if (!(air_plane.getY() >= screenBounds.getHeight())){
                        air_plane.setY(air_plane.getY() + 20);
                    }
                }
                case LEFT -> {
                    if(!(air_plane.getX() <= 0)){
                        air_plane.setX(air_plane.getX() - 20);
                    }
                }
                case RIGHT -> {
                    if(!(air_plane.getX() >= screenBounds.getWidth())){
                        air_plane.setX(air_plane.getX() + 20);
                    }
                }
            }
        });

        end_scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/style.css")).toExternalForm());
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/style.css")).toExternalForm());
        stage.setTitle("2D game");
        stage.setScene(scene);
        stage.show();
    }

    private Pane game_environment() {
        environment_variables_func();
        environment.getChildren().addAll(plane()); //this adds the airplane to the scene
        return environment;
    }

    private void environment_variables_func() {
        Environment_variables environment_variables1 = new Environment_variables();
        Environment_variables environment_variables2 = new Environment_variables();
        Environment_variables environment_variables3 = new Environment_variables();
        Environment_variables environment_variables4 = new Environment_variables();
        Environment_variables environment_variables5 = new Environment_variables();

    }


    private Rectangle plane() {
        air_plane.setFill(new ImagePattern(new Image(Objects.requireNonNull(getClass().getResource("/plane.png")).toExternalForm())));
        air_plane.setWidth(200);
        air_plane.setHeight(80);
        air_plane.setX(0);
        air_plane.setY(0);

        return air_plane;
    }

    public static void main(String[] args) {
        launch();
    }



    //cloud creater class
    class Environment_variables{

        TranslateTransition translateTransition = new TranslateTransition();
        Environment_variables(){
            //creating a single rectangle for the clouds
            Rectangle cloud = new Rectangle();
            cloud.setY(Math.random() * 300);
            cloud.setWidth(200);
            cloud.setHeight(100);
            cloud.setX(screenBounds.getWidth());
            environment.getChildren().add(cloud);
            cloud.setFill(new ImagePattern(new Image(Objects.requireNonNull(getClass().getResource("/cloud.png")).toExternalForm())));

            //creating the actual translation for every cloud

            translateTransition.setNode(cloud);
            translateTransition.setDuration(Duration.minutes(Math.random() * 1));
            translateTransition.setAutoReverse(false);
            translateTransition.setByX(-screenBounds.getWidth() - 200);
            translateTransition.setCycleCount(100000000);
            translateTransition.play();

            //creating a listener for collision






            Circle coin = new Circle();
            coin.setRadius(20);
            coin.setCenterX(screenBounds.getWidth() + 10);
            coin.setCenterY(Math.random() * 300);
            environment.getChildren().add(coin);
            coin.setFill(new ImagePattern(new Image(Objects.requireNonNull(getClass().getResource("/coin.png")).toExternalForm())));

            //creating the actual translation for every coin
            TranslateTransition translateTransition1 = new TranslateTransition();
            translateTransition1.setNode(coin);
            translateTransition1.setDuration(Duration.minutes(Math.random() * 1));
            translateTransition1.setAutoReverse(false);
            translateTransition1.setByX(-screenBounds.getWidth());
            translateTransition1.setCycleCount(100000000);
            translateTransition1.play();

            //creating a listener for collision
            coin.translateXProperty().addListener(e -> {
                //increase score when plane hits the coin
                if (coin.getBoundsInParent().intersects(air_plane.getBoundsInParent())){
                    score += 1;
                    score_panel.setText("Your score is: " + score);
                }
            });


            cloud.translateXProperty().addListener(e -> {
                //the end scene when cloud hits the airplane
                if (cloud.getBoundsInParent().intersects(air_plane.getBoundsInParent())){

                    end_box.getChildren().add(score_panel);
                    end_box.setAlignment(Pos.CENTER);

                    end_game.setScene(end_scene);
                    end_game.show();
                    translateTransition.pause();
                    translateTransition1.pause();
                    Timeline timeline = new Timeline();
                    KeyFrame key = new KeyFrame(Duration.millis(1100), new KeyValue(scene.getRoot().opacityProperty(), 0));
                    timeline.getKeyFrames().add(key);
                    timeline.setOnFinished((ae) -> {
                        Platform.exit();
                    });
                    timeline.play();

                }
            });
        }
    }



}


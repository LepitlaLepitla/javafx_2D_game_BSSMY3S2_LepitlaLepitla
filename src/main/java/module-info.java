module com.example.lepitlalepitla_2d_game_javafx {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.lepitlalepitla_2d_game_javafx to javafx.fxml;
    exports com.example.lepitlalepitla_2d_game_javafx;
}
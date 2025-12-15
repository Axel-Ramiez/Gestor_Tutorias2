/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */
package gestor_tutorias;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Gestor_Tutorias extends Application {

    @Override
    public void start(Stage primaryStage){
        try{
            Parent parent = FXMLLoader.load(getClass().getResource("/gestor_tutorias/vista/FXMLInicioSesion.fxml"));
            Scene scene = new Scene(parent);
            primaryStage.setScene(scene);
            primaryStage.show();
        }catch(IOException ioe){
            System.out.println("No ha sido posible ejecutar el programa");
            ioe.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}

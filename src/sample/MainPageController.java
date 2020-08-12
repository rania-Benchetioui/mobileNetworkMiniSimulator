package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainPageController implements Initializable {
    @FXML
    Button button1;
    @FXML
    TextField textNumberOfNodes, textZoneSize;
    @FXML
    ComboBox comboNumberOfStations;



    private ObservableList<String> baseStationNumber = FXCollections.observableArrayList("4", "9", "16");


    // variables network

    //public static Vector<Line>listeLine = new Vector<>() ;
    static double tailleZone, nbStation ,nodeNumbers;


    @FXML
    private void handleButtonAction(ActionEvent event) throws IOException
    {
        tailleZone = Integer.parseInt(textZoneSize.getText());
        nodeNumbers = Integer.parseInt(textNumberOfNodes.getText());
        nbStation = Integer.parseInt(comboNumberOfStations.getValue().toString());

        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/generation.fxml"));
        Parent parent =  loader.load();
        GenerationController generationController = loader.getController();
        generationController.changeZoneSize(tailleZone,nbStation,nodeNumbers);
        Scene mainScene = new Scene(parent);

        Stage window = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        window.setScene(mainScene);
        window.show();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        comboNumberOfStations.setValue("4");
        comboNumberOfStations.setItems(baseStationNumber);


    }
}
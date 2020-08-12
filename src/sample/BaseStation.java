package sample;

import javafx.scene.image.Image;

import java.util.Vector;

public class BaseStation
{
    int id;
    double x,y;
    Vector<Nodes> myNodesListe = new Vector<>();
    Image image;


    public BaseStation(int id,double x,double y) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.image = new Image("sample/images_icones/radio_station.png", 64, 64, false, false);
    }
}

package sample;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import java.awt.*;


public class Nodes
{
    int id,sb;
    int x,y;
    ImageView image;
    Button btn ;

    public Button getBtn() {
        return btn;
    }

    public int getId() {
        return id;
    }

    public int getSb() {
        return sb;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }


    public void setY(int y) {
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public Nodes (int id, int x, int y, Button btn)
    {
        this.id = id;
        this.x = x;
        this.y = y;
        this.setBtn(btn);
        this.setImageView();

    }
    public ImageView getImageView() {
        return this.image;
    }

    private void setImageView()
    {
        this.image = new ImageView( new Image("sample/images_icones/call.png", 32, 32, false, false));
    }

    private void setBtn(Button btn)
    {
        this.btn=btn;
        btn.setStyle("-fx-background-color: transparent");
    }


}

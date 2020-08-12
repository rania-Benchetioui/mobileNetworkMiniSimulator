/**
 * @author : Benchetioui Rania
 * last edit >> 12/08/2020
 */



package sample;

import javafx.animation.PathTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Vector;


public class GenerationController
{
    @FXML
    Pane paneCenter;
    static int taille_Zone;
    static int nb_Station;
    static int node_Numbers;
    static Vector<BaseStation> listeSB = new Vector<>();
    static Vector<Nodes> nodesList = new Vector<>();
    static Vector<Button> btn_nodes = new Vector<>();
    static Vector<Line> lignes = new Vector<>();

    //public static File file = new File("file.txt") ;



    @FXML
    private void handleButtonAction(ActionEvent event) throws IOException
    {
        Parent mainParent = FXMLLoader.load(getClass().getResource("fxml/mainPage.fxml"));
        Scene mainScene = new Scene(mainParent);

        Stage window = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        window.setScene(mainScene);
        window.show();

        clearAll();

    }

    private void clearAll()
    {
        paneCenter.getChildren().clear();
        listeSB.clear();
        nodesList.clear();
        btn_nodes.clear();
        lignes.clear();
    }

    public void changeZoneSize(double tailleZone, double nbStation, double nodeNumbers)

    {
        taille_Zone = (int) tailleZone;
        nb_Station =(int) nbStation;
        node_Numbers =(int) nodeNumbers;

        //je dessine les lignes de la zone
        paneCenter.setPrefSize(tailleZone,tailleZone);
        if (nbStation==4)
        {
            //draw 2 lines
            int size_4 = (int)(tailleZone/2);
            Line line1 , line2 ;
            line1 = new Line(size_4,0.0,size_4,tailleZone);
            line2 = new Line(0.0,size_4,tailleZone,size_4);
            paneCenter.getChildren().add(line1);
            paneCenter.getChildren().add(line2);

        }
        if (nbStation==9)
        {
            //draw 3 lines
            int size_9 = (int)(tailleZone/3);
            Line line1 , line2 , line3, line4 ;

           line1 = new Line(size_9,0.0,size_9,tailleZone);
           line2 = new Line(size_9*2,0.0,size_9*2,tailleZone);
           line3 = new Line(0.0,size_9,tailleZone,size_9);
           line4 = new Line(0.0,size_9*2,tailleZone,size_9*2);

            paneCenter.getChildren().add(line1);
            paneCenter.getChildren().add(line2);
            paneCenter.getChildren().add(line3);
            paneCenter.getChildren().add(line4);
        }
        if (nbStation==16)
        {
            //draw 4 lines
            int size_16 = (int) (tailleZone/4);
            Line line1 , line2 , line3, line4 , line5 , line6;

             line1 = new Line(size_16,0.0,size_16,tailleZone);
             line2 = new Line(size_16*2,0.0,size_16*2,tailleZone);
             line3 = new Line(size_16*3,0.0,size_16*3,tailleZone);
             line4 = new Line(0.0,size_16,tailleZone,size_16);
             line5 = new Line(0.0,size_16*2,tailleZone,size_16*2);
             line6 = new Line(0.0,size_16*3,tailleZone,size_16*3);

            paneCenter.getChildren().add(line1);
            paneCenter.getChildren().add(line2);
            paneCenter.getChildren().add(line3);
            paneCenter.getChildren().add(line4);
            paneCenter.getChildren().add(line5);
            paneCenter.getChildren().add(line6);
        }

        createStations(tailleZone,nbStation);
        createNodes(nodeNumbers,tailleZone);
        drawNodes(nodeNumbers,nbStation,tailleZone);

    }

    // juste les données leurs coordonées aléatoires .
    private void createNodes(double nodeNumbers,double tailleZone)
    {
        taille_Zone = (int) tailleZone;
        for(int i=0;i<nodeNumbers;i++)
        {
            Random random = new Random();
            int x = random.nextInt(taille_Zone);
            int y = random.nextInt(taille_Zone);
            Button b = new Button();
            Nodes node = new Nodes(i,x,y,b);
            btn_nodes.add(i,b);
            nodesList.add(node); //node 0  node 1 .... etc
        }
    }

    private void drawNodes(double nodeNumbers, double nbStations,double tailleZone)
    {
        int tailleStation = (int) (tailleZone / Math.sqrt(nbStations));

        if (nbStations == 4)
        {
            for (int i = 0; i < nodeNumbers; i++) {
                Nodes node = nodesList.elementAt(i);
                if ((node.x <= tailleStation) && (node.y < tailleStation)) {
                    nodesList.elementAt(i).sb = 0;
                    listeSB.elementAt(0).myNodesListe.add(node);
                }
                if ((node.x > tailleStation) && (node.y <= tailleStation)) {
                    nodesList.elementAt(i).sb = 1;
                    listeSB.elementAt(1).myNodesListe.add(node);
                }
                if ((node.x <= tailleStation) && (node.y >= tailleStation)) {
                    nodesList.elementAt(i).sb = 2;
                    listeSB.elementAt(2).myNodesListe.add(node);
                }
                if ((node.x > tailleStation) && (node.y > tailleStation)) {
                    nodesList.elementAt(i).sb = 3;
                    listeSB.elementAt(3).myNodesListe.add(node);
                }


                node.btn.setLayoutX(node.x-16);
                node.btn.setLayoutY(node.y-16);
                node.btn.setGraphic(node.image);
                paneCenter.getChildren().add(node.btn);

                node.btn.setOnMouseClicked(event -> {
                    // je récupère le id de noeud et je le passe à une fonction qui va dessiner la ligne d'appel
                    call_Line(node);

                });
            }
        }
        if (nbStations == 9) {
            for (int i = 0; i < nodeNumbers; i++) {
                Nodes node = nodesList.elementAt(i);
                if ((node.x <= tailleStation) && (node.y < tailleStation)) {
                    nodesList.elementAt(i).sb = 0;
                    listeSB.elementAt(0).myNodesListe.add(node);
                }
                if ((node.x > tailleStation) && (node.x < tailleStation * 2) && (node.y <= tailleStation)) {
                    nodesList.elementAt(i).sb = 1;
                    listeSB.elementAt(1).myNodesListe.add(node);
                }
                if ((node.x > tailleStation * 2) && (node.y < tailleStation * 3) && (node.y <= tailleStation)) {
                    nodesList.elementAt(i).sb = 2;
                    listeSB.elementAt(2).myNodesListe.add(node);
                }
                if ((node.x <= tailleStation) && (node.y > tailleStation) && (node.y < tailleStation * 2)) {
                    nodesList.elementAt(i).sb = 3;
                    listeSB.elementAt(3).myNodesListe.add(node);
                }
                if ((node.x > tailleStation) && (node.x < tailleStation * 2) && (node.y > tailleStation) && (node.y < tailleStation * 2)) {
                    nodesList.elementAt(i).sb = 4;
                    listeSB.elementAt(4).myNodesListe.add(node);
                }
                if ((node.x > tailleStation * 2) && (node.x < tailleStation * 3) && (node.y > tailleStation * 2) && (node.y < tailleStation * 3)) {
                    nodesList.elementAt(i).sb = 5;
                    listeSB.elementAt(5).myNodesListe.add(node);
                }
                if ((node.x < tailleStation) && (node.y > tailleStation * 2) && (node.y > tailleStation * 3)) {
                    nodesList.elementAt(i).sb = 6;
                    listeSB.elementAt(6).myNodesListe.add(node);
                }
                if ((node.x > tailleStation * 2) && (node.x < tailleStation * 3) && (node.y > tailleStation) && (node.y < tailleStation * 2)) {
                    nodesList.elementAt(i).sb = 7;
                    listeSB.elementAt(7).myNodesListe.add(node);
                }
                if ((node.x > tailleStation * 2) && (node.x > tailleStation * 3) && (node.y > tailleStation * 2) && (node.y > tailleStation * 3)) {
                    nodesList.elementAt(i).sb = 8;
                    listeSB.elementAt(8).myNodesListe.add(node);
                }

                node.btn.setLayoutX(node.x-16);
                node.btn.setLayoutY(node.y-16);
                node.btn.setGraphic(node.image);
                paneCenter.getChildren().add(node.btn);
                node.btn.setOnMouseClicked(event -> {
                    // je récupère le id de noeud et je le passe à une fonction qui va dessiner la ligne d'appel
                    call_Line(node);

                });

            }
        }
            if (nbStations==16) {
                for (int i = 0; i < nodeNumbers; i++) {
                    Nodes node = nodesList.elementAt(i);
                    if ((node.x <= tailleStation) && (node.y < tailleStation)) {
                        nodesList.elementAt(i).sb = 0;
                        listeSB.elementAt(0).myNodesListe.add(node);
                    }
                    if ((node.x > tailleStation) && (node.x < tailleStation * 2) && (node.y < tailleStation)) {
                        nodesList.elementAt(i).sb = 1;
                        listeSB.elementAt(1).myNodesListe.add(node);
                    }
                    if ((node.x > tailleStation * 2) && (node.x < tailleStation * 3) && (node.y < tailleStation)) {
                        nodesList.elementAt(i).sb = 2;
                        listeSB.elementAt(2).myNodesListe.add(node);
                    }
                    if ((node.x > tailleStation * 3) && (node.x < tailleStation * 4) && (node.y < tailleStation)) {
                        nodesList.elementAt(i).sb = 3;
                        listeSB.elementAt(3).myNodesListe.add(node);
                    }
                    if ((node.x < tailleStation) && (node.y > tailleStation) && (node.y < tailleStation * 2)) {
                        nodesList.elementAt(i).sb = 4;
                        listeSB.elementAt(4).myNodesListe.add(node);
                    }
                    if ((node.x > tailleStation) && (node.x < tailleStation * 2) && (node.y > tailleStation) && (node.y < tailleStation * 2)) {
                        nodesList.elementAt(i).sb = 5;
                        listeSB.elementAt(5).myNodesListe.add(node);
                    }
                    if ((node.x > tailleStation * 2) && (node.x < tailleStation * 3) && (node.y > tailleStation) && (node.y < tailleStation * 2)) {
                        nodesList.elementAt(i).sb = 6;
                        listeSB.elementAt(6).myNodesListe.add(node);
                    }
                    if ((node.x > tailleStation * 3) && (node.x < tailleStation * 4) && (node.y > tailleStation) && (node.y < tailleStation * 2)) {
                        nodesList.elementAt(i).sb = 7;
                        listeSB.elementAt(7).myNodesListe.add(node);
                    }
                    if ((node.x < tailleStation) && (node.y > tailleStation * 2) && (node.y < tailleStation * 3)) {
                        nodesList.elementAt(i).sb = 8;
                        listeSB.elementAt(8).myNodesListe.add(node);
                    }
                    if ((node.x > tailleStation) && (node.x < tailleStation * 2) && (node.y > tailleStation * 2) && (node.y < tailleStation * 3)) {
                        nodesList.elementAt(i).sb = 9;
                        listeSB.elementAt(9).myNodesListe.add(node);
                    }
                    if ((node.x > tailleStation * 2) && (node.x < tailleStation * 3) && (node.y > tailleStation * 2) && (node.y < tailleStation * 3)) {
                        nodesList.elementAt(i).sb = 10;
                        listeSB.elementAt(10).myNodesListe.add(node);
                    }
                    if ((node.x > tailleStation * 3) && (node.x < tailleStation * 4) && (node.y > tailleStation * 2) && (node.y < tailleStation * 3)) {
                        nodesList.elementAt(i).sb = 11;
                        listeSB.elementAt(11).myNodesListe.add(node);
                    }
                    if ((node.x < tailleStation) && (node.y > tailleStation * 3) && (node.y < tailleStation * 4)) {
                        nodesList.elementAt(i).sb = 12;
                        listeSB.elementAt(12).myNodesListe.add(node);
                    }
                    if ((node.x > tailleStation) && (node.x < tailleStation * 2) && (node.y > tailleStation * 3) && (node.y < tailleStation * 4)) {
                        nodesList.elementAt(i).sb = 13;
                        listeSB.elementAt(13).myNodesListe.add(node);
                    }
                    if ((node.x > tailleStation * 2) && (node.x < tailleStation * 3) && (node.y > tailleStation * 3) && (node.y < tailleStation * 4)) {
                        nodesList.elementAt(i).sb = 14;
                        listeSB.elementAt(14).myNodesListe.add(node);
                    }
                    if ((node.x > tailleStation * 3) && (node.x < tailleStation * 4) && (node.y > tailleStation * 3) && (node.y < tailleStation * 4)) {
                        nodesList.elementAt(i).sb = 15;
                        listeSB.elementAt(15).myNodesListe.add(node);
                    }

                    node.btn.setLayoutX(node.x-16);
                    node.btn.setLayoutY(node.y-16);
                    node.btn.setGraphic(node.image);
                    paneCenter.getChildren().add(node.btn);
                    node.btn.setOnMouseClicked(event -> {
                        // je récupère le id de noeud et je le passe à une fonction qui va dessiner la ligne d'appel
                        call_Line(node);

                    });

                }
            }
        }

    private void call_Line(Nodes nodeCalling)
    {
        Path path = new Path();
        Random random = new Random();
        int randomNum = random.nextInt(nodesList.size()+1);

        // je vais choisir alétoirement un autre noeud
        // de 1 à node number - moi meme
       while((randomNum == nodeCalling.id))
       {
           randomNum = random.nextInt(nodesList.size());
       }
       Nodes destinationNode = nodesList.elementAt(randomNum);
       System.out.println("i'm node "+nodeCalling.id +" calling node  " +  destinationNode.id);

         BaseStation baseStation1 = listeSB.elementAt(nodeCalling.sb);
         BaseStation baseStation2 = listeSB.elementAt(destinationNode.sb);

         if (baseStation1.id == baseStation2.id)
         {
             System.out.println("we are in same base station "+baseStation1.id);
             path.getElements().addAll(
                     new MoveTo(nodeCalling.x, nodeCalling.y),
                     new LineTo(baseStation1.x,baseStation1.y),
                     new LineTo(destinationNode.x,destinationNode.y));


             PathTransition animation = createPathAnimation(path ,Duration.seconds(2));
             animation.play();



         }
         else {
             System.out.println("we are in different base stations " + baseStation1.id + " and " + baseStation2.id);
             path.getElements().addAll(
                     new MoveTo(nodeCalling.x, nodeCalling.y),
                     new LineTo(baseStation1.x, baseStation1.y),
                     new LineTo(baseStation2.x,baseStation2.y),
                     new LineTo(destinationNode.x,destinationNode.y)) ;

             path.setFill(Color.color(Math.random(),Math.random(),Math.random()));


             PathTransition animation = createPathAnimation(path ,Duration.seconds(2));
             animation.play();

         }

    }

    public PathTransition createPathAnimation(Path path, Duration duration)
    {
        //listeLine.clear();
        // move a node along a path. we want its position
        Circle pen = new Circle(0, 0, 4);

        // create path transition
        PathTransition pathTransition = new PathTransition(duration, path, pen);
        pathTransition.currentTimeProperty().addListener(new ChangeListener<Duration>() {

            Location oldLocation = null;

            /**
             * Draw a line from the old location to the new location
             */
            @Override
            public void changed(ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue) {

                // skip starting at 0/0
                if (oldValue == Duration.ZERO)
                    return;

                // get current location
                double x = pen.getTranslateX();
                double y = pen.getTranslateY();

                // initialize the location
                if (oldLocation == null) {
                    oldLocation = new Location();
                    oldLocation.x = x;
                    oldLocation.y = y;
                    return;
                }
                Line l = new Line(oldLocation.x, oldLocation.y, x, y) ;
                lignes.addElement(l);
                l.setStroke(Color.valueOf("#4CB8C4"));
                l.setStrokeWidth(3.5);
                paneCenter.getChildren().addAll(l);

                // update old location with current one
                oldLocation.x = x;
                oldLocation.y = y;
            }
        });

        return pathTransition;
    }

    public void handleClearBtn(ActionEvent event)
    {
        paneCenter.getChildren().removeAll(lignes);
    }


    public static class Location
    {
        double x;
        double y;
    }

    private void createStations(double tailleZone, double nbStation)
    {
        int size , xStation , yStation ;
        int lines = (int) (Math.sqrt(nbStation));
        int count = 0;

        size = (int) (tailleZone /lines);
        xStation = size/2;
        yStation = size/2;

        for (int i=0 ; i<lines ;i++)
        {
            for (int j=0 ; j<lines ;j++)
            {
                BaseStation baseStation = new BaseStation(count,xStation,yStation); // sb id =0 x= y=
                listeSB.add(baseStation); // tbda mn 0
                xStation = xStation+size;
                count++;
            }
            xStation=size/2;
            yStation=yStation+size;
        }
        drawStations(nbStation);
    }

    private void drawStations(double nbStation)
    {
        for (int i = 0 ;i<nbStation ;i++)
        {
            BaseStation sb = listeSB.elementAt(i) ;
            int x = (int) sb.x ;
            int y = (int) sb.y ;

            //System.out.println("i'm SB "+sb.id+" i'm at "+sb.x+" and "+sb.y);
            ImageView imageView = new ImageView(sb.image);
            imageView.setX(x-32);
            imageView.setY(y-32);
            paneCenter.getChildren().add(imageView);
        }
    }

    @FXML
    private void handleSaveBtnAction(ActionEvent event) throws IOException
    {
        File file = new File("file.txt");
        file.createNewFile();
        FileWriter fww = new FileWriter(file);
        BufferedWriter bfw = new BufferedWriter(fww) ;

        bfw.write("This system contains "+listeSB.size()+" base stations :\n\n");
        bfw.flush();

        for (int i =0 ; i<listeSB.size();i++)
        {
            bfw.write("Base Station "+listeSB.elementAt(i).id
                    +" | x = "+listeSB.elementAt(i).x
                    +" | y = "+listeSB.elementAt(i).y
                    +" | nodes = ");
            bfw.flush();
            for (int j=0 ; j<listeSB.elementAt(i).myNodesListe.size();j++)
            {
                bfw.write( listeSB.elementAt(i).myNodesListe.elementAt(j).id+"|");
                bfw.flush();
            }
            bfw.newLine();
        }
        bfw.write("\nHere is its list of nodes   :\n");
        bfw.flush();

        for (int j = 0 ; j<nodesList.size();j++)
        {
            bfw.write("Node "
                    + (nodesList.elementAt(j).id)
                    +" | x = "+nodesList.elementAt(j).x
                    +" | y ="+nodesList.elementAt(j).y+ " SB ="
                    +nodesList.elementAt(j).sb+"\n");
            bfw.flush();
        }
    }

    public void handleMobilityBtn(ActionEvent event) {
        clearAll();
        changeZoneSize(taille_Zone,nb_Station,node_Numbers);
    }
}

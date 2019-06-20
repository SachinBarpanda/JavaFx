package sample;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import jdk.nashorn.internal.runtime.regexp.joni.exception.SyntaxException;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

public class Controller {
    @FXML
    private Label label;
    @FXML
    private GridPane gridPane;
    @FXML
    private WebView webView;


    @FXML
    public void handleMouseEnter(){
        label.setScaleX(2.0);
        label.setScaleY(2.0);
    }
    @FXML

    public void handleMouseExit(){
        label.setScaleX(1.0);
        label.setScaleY(1.0);
    }

    public void handleClick(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Application file");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text",".txt"),
                new FileChooser.ExtensionFilter("PDF",".pdf"),
                new FileChooser.ExtensionFilter("All Files","*.*")

        );

        List<File> file = fileChooser.showOpenMultipleDialog(gridPane.getScene().getWindow());
        if(file!=null) {
            for (int i = 0;i<file.size(); i++) {
                System.out.println(file.get(i));
            }
        }
        else{
            System.out.println("Chooser was cancelled");
        }
    }
    public void handleLink(){
//        try{
//            Desktop.getDesktop().browse(new URI("www.google.com"));
//        }catch (IOException e){
//            e.printStackTrace();
//        }catch (URISyntaxException e){
//            e.printStackTrace();
//        }
        WebEngine webEngine = webView.getEngine();
        webEngine.load("www.oracle.com");
    }
}

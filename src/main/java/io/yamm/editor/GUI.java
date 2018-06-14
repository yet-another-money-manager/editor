package io.yamm.editor;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;

import java.awt.*;
import java.io.*;

public class GUI extends Application implements UserInterface {
    private Editor editor = new Editor(this);

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // set up GUI
        primaryStage.setTitle("Untitled - YAMM Editor");
        BorderPane root = new BorderPane();

        // create text area, add to GUI
        TextArea textArea = new TextArea();
        root.setCenter(textArea);

        // add listener to text area
        textArea.textProperty().addListener((observable, oldValue, newValue) -> editor.setContent(newValue));

        // set up menu bar and menus
        MenuBar menuBar = new MenuBar();
        Menu[] menus = new Menu[4];
        MenuItem[][] menuItems = new MenuItem[4][];

        // set up file menu
        menus[0] = new Menu("File");
        menuItems[0] = new MenuItem[5];
        menuItems[0][0] = new MenuItem("New");
        menuItems[0][0].setDisable(true);
        menuItems[0][1] = new MenuItem("Open");
        menuItems[0][1].setDisable(true);
        menuItems[0][2] = new MenuItem("Save");
        menuItems[0][2].setOnAction(t -> editor.save());
        menuItems[0][3] = new MenuItem("Save As");
        menuItems[0][3].setOnAction(t -> editor.saveAs());
        menuItems[0][4] = new MenuItem("Exit");
        menuItems[0][4].setOnAction(t -> editor.exit());

        // set up edit menu
        menus[1] = new Menu("Edit");
        menuItems[1] = new MenuItem[11];
        menuItems[1][0] = new MenuItem("Undo");
        menuItems[1][0].setDisable(true);
        menuItems[1][1] = new MenuItem("Redo");
        menuItems[1][1].setDisable(true);
        menuItems[1][2] = new MenuItem("Cut");
        menuItems[1][2].setDisable(true);
        menuItems[1][3] = new MenuItem("Copy");
        menuItems[1][3].setDisable(true);
        menuItems[1][4] = new MenuItem("Paste");
        menuItems[1][4].setDisable(true);
        menuItems[1][5] = new MenuItem("Delete");
        menuItems[1][5].setDisable(true);
        menuItems[1][6] = new MenuItem("Find");
        menuItems[1][6].setDisable(true);
        menuItems[1][7] = new MenuItem("Find Next");
        menuItems[1][7].setDisable(true);
        menuItems[1][8] = new MenuItem("Replace");
        menuItems[1][8].setDisable(true);
        menuItems[1][9] = new MenuItem("Go To");
        menuItems[1][9].setDisable(true);
        menuItems[1][10] = new MenuItem("Select All");
        menuItems[1][10].setDisable(true);

        // set up format menu
        menus[2] = new Menu("Format");
        menuItems[2] = new MenuItem[2];
        menuItems[2][0] = new CheckMenuItem("Word Wrap");
        ((CheckMenuItem) menuItems[2][0]).selectedProperty().addListener((ov, oldValue, newValue) -> textArea.setWrapText(newValue));
        menuItems[2][1] = new MenuItem("Font");
        menuItems[2][1].setDisable(true);

        // set up help menu
        menus[3] = new Menu("Help");
        menuItems[3] = new MenuItem[2];
        menuItems[3][0] = new MenuItem("FAQ");
        menuItems[3][0].setOnAction(t -> {
            try {
                // TODO: change this to a FAQ specific to YAMM Editor
                Desktop.getDesktop().browse(java.net.URI.create("https://yamm.io/faq"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        menuItems[3][1] = new MenuItem("About");
        menuItems[3][1].setOnAction(t -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("About - YAMM Editor");
            alert.setHeaderText(null);
            alert.setContentText("YAMM Editor version " + getVersion() + ". Licensed under the GPLv3.");
            alert.showAndWait();
        });

        // add menu items to menus
        for(int i = 0; i < menus.length; i++) {
            menus[i].getItems().addAll(menuItems[i]);
        }

        // add menus to menu bar
        menuBar.getMenus().addAll(menus);

        // add menu bar to GUI
        root.setTop(menuBar);

        // show GUI
        primaryStage.setScene(new Scene(root, 300, 250));
        primaryStage.show();
    }

    /**
     * Get the version from pom.xml. Only works when running from a Jar file.
     * @return The version of the application, or "unknown".
     */
    private String getVersion() {
        MavenXpp3Reader reader = new MavenXpp3Reader();
        try {
            InputStream in = getClass().getResourceAsStream("/META-INF/maven/io.yamm/yamm-editor/pom.xml");
            Model model = reader.read(new BufferedReader(new InputStreamReader(in)));
            return model.getVersion();
        } catch (IOException | NullPointerException | XmlPullParserException e) {
            return "unknown";
        }
    }

    private File showFileChooser(FileChooserAction action, String title) {
        FileChooser fileChooser = new FileChooser();

        // set the FileChooser title, if applicable
        if (title != null) {
            fileChooser.setTitle(title);
        }

        // show the approrpiate FileChooser dialog, return the selected file
        File selectedFile = null;
        if (action == FileChooserAction.OPEN) {
            selectedFile = fileChooser.showOpenDialog(null);
        } else if (action == FileChooserAction.SAVE) {
            selectedFile = fileChooser.showSaveDialog(null);
        }
        return selectedFile;
    }

    public File showOpenFileChooser(String title) {
        return showFileChooser(FileChooserAction.OPEN, title);
    }

    public File showSaveFileChooser(String title) {
        return showFileChooser(FileChooserAction.SAVE, title);
    }
}

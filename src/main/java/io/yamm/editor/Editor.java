package io.yamm.editor;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

class Editor {
    private String content = "";
    private boolean contentChangedSinceSave = false;
    private File file = null;
    private UserInterface userInterface;

    Editor(UserInterface userInterface) {
        this.userInterface = userInterface;
    }

    void exit() {
        System.exit(0);
    }

    void save() {
        // check that a save file has been selected
        if (file == null) {
            saveAs();
            return;
        }

        // write content to file
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(content);
            writer.close();
        } catch (IOException e) {
            // TODO: display exception through the GUI (see http://code.makery.ch/blog/javafx-dialogs-official/#exception-dialog)
            e.printStackTrace();
        }
    }

    void saveAs() {
        file = userInterface.showSaveFileChooser("Save As");
        save();
    }

    void setContent(String content) {
        contentChangedSinceSave = true;
        this.content = content;
    }
}

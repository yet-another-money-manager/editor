package io.yamm.editor;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

class Editor {
    private String content = "";
    private boolean contentChangedSinceSave = false;
    private File file = null;
    private UserInterface ui;

    Editor(UserInterface ui) {
        this.ui = ui;
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
        if (contentChangedSinceSave) {
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                writer.write(content);
                contentChangedSinceSave = false;
                writer.close();
            } catch (IOException e) {
                // TODO: display exception through the GUI (see http://code.makery.ch/blog/javafx-dialogs-official/#exception-dialog)
                e.printStackTrace();
            }
        }
    }

    void saveAs() {
        // ask the user for a file to save as
        File newFile = ui.showSaveFileChooser("Save As");

        // if the user clicked cancel, stop here
        if (file == null) {
            return;
        }

        // flag unsaved changes to force a save, and actually save
        file = newFile;
        contentChangedSinceSave = true;
        save();
    }

    void setContent(String content) {
        contentChangedSinceSave = true;
        this.content = content;
    }
}

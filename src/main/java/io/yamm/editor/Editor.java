package io.yamm.editor;

import java.io.*;
import java.security.GeneralSecurityException;

class Editor {
    private String content = "";
    private boolean contentChangedSinceSave = false;
    private boolean encryptedMode = false;
    private File file = null;
    private UserInterface ui;

    Editor(UserInterface ui) {
        this.ui = ui;
    }

    void exit() {
        if (contentChangedSinceSave) {
            // ask the user if they want to save changes
            String result = ui.showDialogWarning(
                    "Unsaved Changes",
                    "You have unsaved changes!",
                    "You have unsaved changes - would you like to save them now?",
                    new String[]{"Yes", "No", "Cancel"});

            // if the user asked not to exit, abort here
            if (result == null || result.equals("Cancel")) {
                return;
            }

            // if the user asked to save, do it here
            else if (result.equals("Yes")) {
                save();
            }

            // if the user asked not to save, we don't need to do anything!
        }

        // if we get this far, actually exit
        System.exit(0);
    }

    String getFileName() {
        if (file != null) {
            return file.getName();
        } else {
            return "Untitled";
        }
    }

    void save() {
        // check that a save file has been selected
        if (file == null) {
            saveAs();
            return;
        }

        // write content to file
        if (contentChangedSinceSave) {
            try (FileOutputStream stream = new FileOutputStream(file)) {
                byte[] bytes;
                if (encryptedMode) {
                    bytes = Crypto.encrypt(content.getBytes());
                } else {
                    bytes = content.getBytes();
                }
                stream.write(bytes);
            } catch (IOException e) {
                // TODO: display exception through the UI (see http://code.makery.ch/blog/javafx-dialogs-official/#exception-dialog)
                e.printStackTrace();
            } catch (GeneralSecurityException e) {
                ui.showDialogError(
                        "Security Exception",
                        "Security Exception",
                        "An unknown security exception occurred. This file has not been saved.",
                        new String[]{"OK"});
            }
        }

        contentChangedSinceSave = false;
    }

    void saveAs() {
        // ask the user for a file to save as
        File newFile = ui.showFileChooserSave("Save As");

        // if the user clicked cancel, stop here
        if (newFile == null) {
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

    void toggleEncryptedMode() {
        contentChangedSinceSave = true;
        encryptedMode = !encryptedMode;
    }
}

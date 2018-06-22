package io.yamm.editor;

import java.io.File;

interface UserInterface {
    void setContent(String content);
    String showDialogError(String title, String header, String content, String[] options);
    String showDialogWarning(String title, String header, String content, String[] options);
    File showFileChooserOpen(String title);
    File showFileChooserSave(String title);
}

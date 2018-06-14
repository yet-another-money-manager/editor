package io.yamm.editor;

import java.io.File;

interface UserInterface {
    String showDialogWarning(String title, String header, String content, String[] options);
    File showFileChooserOpen(String title);
    File showFileChooserSave(String title);
}

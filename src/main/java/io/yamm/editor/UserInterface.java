package io.yamm.editor;

import java.io.File;

interface UserInterface {
    File showFileChooserOpen(String title);
    File showFileChooserSave(String title);
}

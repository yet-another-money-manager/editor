package io.yamm.editor;

import java.io.File;

interface UserInterface {
    File showOpenFileChooser(String title);
    File showSaveFileChooser(String title);
}

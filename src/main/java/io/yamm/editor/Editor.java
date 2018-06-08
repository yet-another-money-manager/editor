package io.yamm.editor;

class Editor {
    private String content = "";
    private boolean contentChangedSinceSave = false;
    private UserInterface userInterface;

    Editor(UserInterface userInterface) {
        this.userInterface = userInterface;
    }

    void exit() {
        System.exit(0);
    }

    void setContent(String content) {
        contentChangedSinceSave = true;
        this.content = content;
    }
}

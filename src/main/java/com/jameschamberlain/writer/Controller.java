package com.jameschamberlain.writer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.HashMap;

class Controller {

    /**
     * Stores the model for the application
     */
    private Model model;
    /**
     * Stores the view for the application
     */
    private View view;
    /**
     * Stores the currently opened file
     */
    private File file;

    private HashMap<String, Color> colorMap = new HashMap<>();

    Controller(Model _model, View _view) {
        this.model = _model;
        this.view = _view;

        this.view.startGUI();
        this.view.loadOpenListener(new openListener());
        this.view.loadSaveListener(new saveListener());
        this.view.loadFontSizeListener(new fontSizeListener());
        this.view.loadFontColourListener(new fontColourListener());

        initialiseColours();
    }

    /**
     * Listens for whether the open menu item has been clicked
     */
    class openListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equals("Open")) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
                int result = fileChooser.showOpenDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    file = fileChooser.getSelectedFile();
                    view.setTitle(file);
                    view.showFileContents(file);
                }
            }
        }
    }

    /**
     * Listens for whether the save menu item has been clicked
     */
    class saveListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equals("Save")) {
                boolean hasWrittenFile = model.writeFile(view.getText(), file);
                if (!hasWrittenFile) JOptionPane.showMessageDialog(view.frame, "Couldn't save file");
            }
        }
    }

    /**
     * Listens for whether the font size menu item has been clicked
     */
    class fontSizeListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equals("Font size")) {
                String input = JOptionPane.showInputDialog(view.frame, "Enter a font size");
                if (!(input == null)) {
                    while (!input.matches("[0-9]+")) {
                        JOptionPane.showMessageDialog(view.frame, "Invalid input");
                        input = JOptionPane.showInputDialog("Enter a font size");
                    }
                    view.changeFontSize(Integer.parseInt(input));
                }
            }
        }
    }

    /**
     * Listens for whether the font colour menu item has been clicked
     */
    class fontColourListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equals("Font colour")) {
                Object[] options = {"Black", "Blue", "Cyan", "Green", "Grey", "Magenta", "Orange", "Pink", "Red", "Yellow"};
                String input = (String) JOptionPane.showInputDialog(
                        view.frame,
                        "Pick a font colour",
                        "Change font colour",
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        options,
                        "Black");
                if ((input != null) && (input.length() > 0)) {
                    view.changeFontColour(colorMap.get(input));
                }
            }
        }
    }


    private void initialiseColours() {
        colorMap.put("Black", Color.BLACK);
        colorMap.put("Blue", Color.BLUE);
        colorMap.put("Cyan", Color.CYAN);
        colorMap.put("Green", Color.GREEN);
        colorMap.put("Grey", Color.GRAY);
        colorMap.put("Magenta", Color.MAGENTA);
        colorMap.put("Orange", Color.ORANGE);
        colorMap.put("Pink", Color.PINK);
        colorMap.put("Red", Color.RED);
        colorMap.put("Yellow", Color.YELLOW);
    }


}

package com.jameschamberlain.writer;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.*;

class View {

    /**
     * Stores the main frame in the gui
     */
    JFrame frame;
    /**
     * Stores the main text area
     */
    private JTextPane editArea;
    /**
     * Stores the open menu item
     */
    private JMenuItem open;
    /**
     * Stores the save menu item
     */
    private JMenuItem save;
    /**
     * Stores the font size menu item
     */
    private JMenuItem fontSize;
    /**
     * Stores the font colour menu item
     */
    private JMenuItem fontColour;

    View() {}

    /**
     * Builds the main gui e.g. window, menus etc.
     */
    void startGUI() {

        Runnable r = () -> {
            // Setup frame
            frame = new JFrame("Writer");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationByPlatform(true);
            frame.setPreferredSize(new Dimension(900, 700));
            frame.setMinimumSize(new Dimension(400, 400));

            // Setup menu items
            JMenuBar menuBar = new JMenuBar();
            JMenu fileMenu = new JMenu("File");
            JMenu editMenu = new JMenu("Edit");
            open = new JMenuItem("Open");
            save = new JMenuItem("Save");
            fontSize = new JMenuItem("Font size");
            fontColour = new JMenuItem("Font colour");
            fileMenu.add(open);
            fileMenu.add(save);
            editMenu.add(fontSize);
            editMenu.add(fontColour);
            menuBar.add(fileMenu);
            menuBar.add(editMenu);
            frame.setJMenuBar(menuBar);

            // Setup keyboard shortcuts
            KeyStroke openKeystroke = KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK);
            open.setAccelerator(openKeystroke);
            KeyStroke saveKeystroke = KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK);
            save.setAccelerator(saveKeystroke);

            // Setup main panel
            JPanel mainPanel = new JPanel(new BorderLayout());
            mainPanel.setBorder(new EmptyBorder(2, 3, 2, 3));

            // Setup editable area
            editArea = new JTextPane();
            Font defaultFont = new Font(Font.MONOSPACED, Font.PLAIN, editArea.getFont().getSize());
            editArea.setFont(defaultFont);
            mainPanel.add(new JScrollPane(editArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS));
            frame.add(mainPanel);


            // Frame maintenance
            try {
                ImageIcon img = new ImageIcon(ImageIO.read(ClassLoader.getSystemResource("icon.png")));
                frame.setIconImage(img.getImage());
                frame.pack();
                frame.setVisible(true);
            }
            catch (IOException e) {
                System.out.println("Couldn't find image");
            }

        };
        SwingUtilities.invokeLater(r);
    }


    /**
     *
     * Adds an action listener for the open file menu item
     *
     * @param _listener The associated action listener
     */
    void loadOpenListener(ActionListener _listener) {
        Runnable r = () -> open.addActionListener(_listener);
        SwingUtilities.invokeLater(r);
    }

    /**
     *
     * Adds an action listener for the save file menu item
     *
     * @param _listener The associated action listener
     */
    void loadSaveListener(ActionListener _listener) {
        Runnable r = () -> save.addActionListener(_listener);
        SwingUtilities.invokeLater(r);
    }

    /**
     *
     * Adds an action listener for the font size file menu item
     *
     * @param _listener The associated action listener
     */
    void loadFontSizeListener(ActionListener _listener) {
        Runnable r = () -> fontSize.addActionListener(_listener);
        SwingUtilities.invokeLater(r);
    }

    /**
     *
     * Adds an action listener for the font colour file menu item
     *
     * @param _listener The associated action listener
     */
    void loadFontColourListener(ActionListener _listener) {
        Runnable r = () -> fontColour.addActionListener(_listener);
        SwingUtilities.invokeLater(r);
    }

    /**
     *
     * Sets the title of the window to the directory
     * of the currently opened file
     *
     * @param file The file currently open
     */
    void setTitle(File file) {
        Runnable r = () -> frame.setTitle(file.getAbsolutePath());
        SwingUtilities.invokeLater(r);
    }

    /**
     *
     * Gets the current text on show in the main
     * text area
     *
     * @return The text in the main text area
     */
    String[] getText() {
        return editArea.getText().split("\\n");

    }

    /**
     *
     * Shows the contents of the selected file
     * in the main text area
     *
     * @param file The file currently open
     */
    void showFileContents(File file) {
        Runnable r = () -> {
            editArea.setText(null);
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new FileReader(file));
            }
            catch (FileNotFoundException e) {
                JOptionPane.showMessageDialog(frame, "Couldn't open file");
            }
            try {
                assert reader != null;
                String line = reader.readLine();
                StringBuilder text = new StringBuilder();
                while (line != null) {
                    text.append(line);
                    text.append('\n');
                    line = reader.readLine();
                }
                editArea.setText(text.toString());
            }
            catch (IOException | NullPointerException e) {
                JOptionPane.showMessageDialog(frame, "Couldn't read file");
            }
        };
        SwingUtilities.invokeLater(r);
    }

    /**
     *
     * Changes the font size of the text in
     * the main text area
     *
     * @param fontSize The new font size
     */
    void changeFontSize(int fontSize) {
        Runnable r = () -> {
            Font defaultFont = new Font(Font.MONOSPACED, Font.PLAIN, fontSize);
            editArea.setFont(defaultFont);
        };
        SwingUtilities.invokeLater(r);
    }

    /**
     *
     * Changes the font colour of the text in
     * the main text area
     *
     * @param colour The new font colour
     */
    void changeFontColour(Color colour) {
        Runnable r = () -> {
            SimpleAttributeSet keyword = new SimpleAttributeSet();
            StyleConstants.setForeground(keyword, colour);
            String currentText = editArea.getText();
            editArea.setText(null);
            StyledDocument doc = editArea.getStyledDocument();
            try {
                doc.insertString(0, currentText, keyword);
            }
            catch (Exception e) {
                System.out.println("Didn't work");
            }
        };
        SwingUtilities.invokeLater(r);
    }

}

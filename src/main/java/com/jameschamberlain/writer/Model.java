package com.jameschamberlain.writer;

import java.io.*;

class Model {

    Model() {
    }

    /**
     *
     * Writes a string to a file
     *
     * @param text A string array holding each line to be written to the file
     * @param file The file that should be written to
     * @return Whether the operation was a success
     */
    boolean writeFile(String[] text, File file) {
        String path = file.getAbsolutePath();
        try {
            // Create a new BufferedWriter to write to the file
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File(path), false));
            for (String line : text) {
                writer.write(line);
                writer.newLine();
            }
            writer.close();
            return true;
        }
        catch (IOException e) {
            return false;
        }
    }

}

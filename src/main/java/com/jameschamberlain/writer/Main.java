package com.jameschamberlain.writer;

class Main {

    public static void main(String[] args) {

        Model model = new Model();
        View view = new View();

        new Controller(model, view);


    }

}

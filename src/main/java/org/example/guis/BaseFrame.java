package org.example.guis;

import javax.swing.*;
/*
    Creating an abstract class helps us setup the blueprint that our GUIs will follow, for example
    in each of GUIs they will be the same size and will need to invoke their own addGuiComponents()
    which will be unique to each subclass
*/
public abstract class BaseFrame extends JFrame {
    public BaseFrame(String title) {
        initialize(title);
    }

    private void initialize(String title){
        // instantiate jframe properties and add a title to the bar
        setTitle(title);

        // set size (in pixels)
        setSize(420, 600);

        // terminate program when the gui is closed
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // set layout to null to have absolute layout which allows us to manually specifiy the size and position of each gui component
        setLayout(null);

        // prevent gui from being resized
        setResizable(false);

        // launch the gui in the center of the screen
        setLocationRelativeTo(null);

        // call on the subclass addGuiComponent()
        addGuiComponentes();
    }

    // this method will need to be defined by subclasses when this class in being inherited from
    protected abstract void addGuiComponentes();
}

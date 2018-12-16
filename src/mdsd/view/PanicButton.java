package mdsd.view;

import mdsd.controller.RewardController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class PanicButton {
    private JDesktopPane desktop;
    private List<ActionListener> listners;

    public PanicButton(JDesktopPane desktop, ActionListener listner){
        listners = new ArrayList<>();
        this.desktop = desktop;
        listners.add(listner);
    }

    public void addListner(ActionListener listner){
        listners.add(listner);
    }

    public void createButton(){
        createButton(100,500);
    }

    public void createButton(int x, int z){
        JInternalFrame frame = new JInternalFrame("PanicButton",true,false,false,false);
        frame.setLayout(new FlowLayout());
        frame.setSize(120,80);

        frame.setLocation(x,z);

        JButton button = new JButton();
        button.setPreferredSize(new Dimension(100,50));
        button.setText("Stop all!");
        button.setBackground(Color.RED);
        for(ActionListener al : listners){
            button.addActionListener(al);
        }

        frame.add(button);
        frame.setVisible(true);
        desktop.add(frame);
    }
}

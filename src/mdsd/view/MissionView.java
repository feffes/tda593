package mdsd.view;

import mdsd.controller.IRobotController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class MissionView implements ActionListener ,IMissionView {
    private JDesktopPane desktop;
    private IRobotController cont;
    private List<String> commands;
    private JTextField guiText;
    private JLabel guiLabel;
    private List<JLabel> robotInfo;

    public MissionView(IRobotController cont, JDesktopPane desktop){
        this.desktop = desktop;
        this.cont = cont;
        robotInfo = new ArrayList<>();
    }

    public void createGUI(int x , int z){
        JInternalFrame frame = new JInternalFrame("MissionView",true,false,false,false);
        frame.setSize(700,cont.getAmountRobots() * 50 + 100);
        frame.setLocation(x,z);
        frame.setLayout(new GridLayout(cont.getAmountRobots() + 2,1));
        frame.setVisible(true);

        for(int i = 0 ; i < cont.getAmountRobots() ; i++){
            robotInfo.add(new JLabel("Id: " + i + " " + cont.getRobotInfo(i)));
        }

        for(JLabel l : robotInfo){
            frame.add(l);
        }

        JTextField text = new JTextField();
        this.guiText = text;
        text.addActionListener(this);
        JLabel label = new JLabel();
        this.guiLabel = label;
        frame.add(label);
        frame.add(text);
        desktop.add(frame);
    }



    //return false if incorrect command, otherwise performs the command
    private boolean isValidCommand(String cmd){
        cmd = cmd.toLowerCase();
        String[] splited = cmd.split(" ");
        for(int i = 0; i < splited.length ; i++){
            splited[i] = splited[i].replaceAll("\\s","");
        }
        if(splited[0].equals("enter") || splited[0].equals("middle") || splited[0].equals("exit")){
            return splited.length == 2 ? true : false;
        }else if(splited[0] == "point"){
            return splited.length == 3 ? true : false;
        }
        return false;
    }


    //should get robot indx from robotcontroller
    public static  int isOkayInteger(String str, int robotAmount)
    {
        try
        {
            Integer i = Integer.parseInt(str);
            if( !(i < 0 || i > robotAmount)){
                return i;
            }
        }
        catch(NumberFormatException nfe)
        {
            return -1;
        }
        return -1;
    }


    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        List<String> commands = new ArrayList<>();
        guiLabel.setText(guiText.getText());
        String[] strings = guiText.getText().split(",");
        for(int i = 1; i < strings.length -1 ; i++){ //dont want the last, which is stratedgy
            strings[i].replace("\\s",""); //remove first whitespace
            if(isValidCommand(strings[i])){
                commands.add(strings[i]);
            }
        }
        int rbtIdx = isOkayInteger(strings[0], cont.getAmountRobots());
        if(rbtIdx != -1){
            cont.setMission(rbtIdx, commands , strings[strings.length -1]);
        }else{
            guiLabel.setText("Invalid Command");
        }
    }

    @Override
    public void updateMission(int robotIndex, List<String> mission) {
        robotInfo.get(robotIndex).setText("Id: " + robotIndex + " " + cont.getRobotInfo(robotIndex));
    }
}

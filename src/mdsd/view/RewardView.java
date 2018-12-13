package mdsd.view;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RewardView implements IRewardView {

    private List<String> robotNames;
    private Map<String,Integer> mapRewardPoints;
    private JDesktopPane desktop;

    private Map<String, JLabel> mapRobotLabel;

    public RewardView(JDesktopPane desktop,List<String> robotNames){
        this.desktop = desktop;
        this.robotNames =  robotNames;
        mapRobotLabel = new HashMap<>();

        mapRewardPoints = new HashMap<>();
        for(String name : robotNames){
            mapRewardPoints.put(name,0);
        }

        createPane();
    }

    private void createPane(){
        JInternalFrame frame = new JInternalFrame("RewardPoints",true,false,false,false);
        frame.setSize(200,100);
        frame.setLocation(10,10);
        frame.setLayout(new FlowLayout());
        JLabel procedure = new JLabel("Current procedure: ");
        mapRobotLabel.put("procedure",procedure);
        frame.add(procedure);
        for(String name : robotNames){
            JLabel label = new JLabel(name + " has " + 0 + " points");
            frame.add(label);
            mapRobotLabel.put(name,label);
        }

        frame.setVisible(true);
        desktop.add(frame);
    }

    @Override
    public void updateRewardPoints(String robotName, int rewardPoints, String currentProcendureName) {

        mapRobotLabel.get("procedure").setText("Current procedure: " + currentProcendureName);

        rewardPoints += mapRewardPoints.get(robotName);
        mapRewardPoints.replace(robotName,rewardPoints);

        mapRobotLabel.get(robotName).setText(robotName + " has "+ rewardPoints +" points" );

    }
}

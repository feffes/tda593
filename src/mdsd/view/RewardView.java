package mdsd.view;

import mdsd.controller.IRewardControlller;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RewardView implements IRewardView {
    //private IRewardControlller cont;
    private String procendureName = null;
    private List<String> robotNames;
    private Map<String,Integer> mapRewardPoints;


    public RewardView(){
        //this.cont = cont;
        robotNames =  new ArrayList<>();
        mapRewardPoints = new HashMap<>();
    }



    @Override
    public void updateRewardPoints(String robotName, int rewardPoints, String currentProcendureName) {
        if(!robotNames.contains(robotName)){
            robotNames.add(robotName);
            mapRewardPoints.put(robotName,rewardPoints);
        }else{
            rewardPoints += mapRewardPoints.get(robotName);
            mapRewardPoints.replace(robotName,rewardPoints);
        }

        System.out.println("Current procedure: " + currentProcendureName);
        for(String name : robotNames){
            System.out.println(name + " has " + mapRewardPoints.get(robotName)+ " points!");
        }
    }
}

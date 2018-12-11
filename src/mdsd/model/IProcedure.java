package mdsd.model;

public interface IProcedure {
    int getPoints(IRobot robot);
    void addArea(Area area, int rewardPoints);
    void removeArea(Area area);
    boolean isValid(IRobot robot);
    void setName(String name);
    String getName();

}

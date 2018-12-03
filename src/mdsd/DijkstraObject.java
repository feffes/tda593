package mdsd;

public class DijkstraObject {

    private double distance;
    private DijkstraObject previous;
    private GridElement elem;

    DijkstraObject(double distance,GridElement elem){
        this.elem = elem;
        this.distance = distance;
    }

    public GridElement getElem() {
        return elem;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public DijkstraObject getPrevious() {
        return previous;
    }

    public void setPrevious(DijkstraObject previous) {
        this.previous = previous;
    }




}

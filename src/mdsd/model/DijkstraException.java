package mdsd.model;

public class DijkstraException extends NullPointerException {
    public DijkstraException(){
        super("Dijkstra could not find a valid path");
    }

}

package mdsd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DijkstraSolver {
    private GridElement[][] grid;
    private List<GridElement> elems;
    private GridManager man;
    private List<DijkstraObject> unvisited;
    private Map<GridElement, DijkstraObject> map;
    private List<DijkstraObject> nodes ;
    private GridElement current;

    private double opposingDist = 1;
    private double diagonalDist = 1.4142; //good enough???
    private double INF = Double.MAX_VALUE;

    DijkstraSolver(GridManager man){
        this.man = man;
        map =  new HashMap<>();
        grid = man.getGrid();
        elems = man.getAllElements();
        nodes = new ArrayList<>();
        unvisited = new ArrayList<DijkstraObject>();
        //in beging we have not visited any nodes
        for (GridElement elem : elems){
            DijkstraObject dijObj = new DijkstraObject(INF,elem);
            map.put(elem, dijObj);
            nodes.add(dijObj);
            unvisited.add(dijObj);
        }
    }

    //need to see if start and end is inside grid

    public List<GridElement> solve(GridElement startGE, GridElement endGE){
        DijkstraObject start = map.get(startGE);
        DijkstraObject end = map.get(endGE);

        List<GridElement> path = new ArrayList<GridElement>();

        start.setDistance(0);
        DijkstraObject current;
        current = start;

        double tempDist;
        DijkstraObject smallest;

        while(current != end){ //maybe  a do while is better
            smallest = null;
            for (DijkstraObject prospect : getUnvisitedNeighbors(current)) {

                if(current.getElem().isDiagonalNeighbor(prospect.getElem())){
                    tempDist = current.getDistance() + diagonalDist;
                }else{ //opposing
                    tempDist = current.getDistance() + opposingDist;

                }
                if(prospect.getDistance() > tempDist){
                    prospect.setPrevious(current);
                    prospect.setDistance(tempDist);
                }
                if(smallest == null || smallest.getDistance() > prospect.getDistance()){
                    smallest = prospect;
                }
            }
            unvisited.remove(current);
            current = smallest;
        }
        System.out.println("Made it out off the unvisited loop");
        DijkstraObject next = end;
        while(next != start){
            System.out.println("hehej");
            path.add(next.getElem());
            next = next.getPrevious();
        }

        return path;
    }


    private List<DijkstraObject> getUnvisitedNeighbors(DijkstraObject e) {
        List<GridElement> neighors = man.getNeighbors(e.getElem());
        List<DijkstraObject> unvisitedNeighors = new ArrayList<DijkstraObject>();

        for (GridElement elem : neighors) {
            if (unvisited.contains(map.get(elem))) {
                unvisitedNeighors.add(map.get(elem));
            }
        }
        return unvisitedNeighors;
    }


}

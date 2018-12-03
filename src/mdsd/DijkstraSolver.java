package mdsd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DijkstraSolver {
    private GridElement[][] grid;
    private List<GridElement> elems;
    private GridManager man;
    private List<GridElement> unvisited;
    private Map<GridElement, DijkstraObject> nodes;
    private GridElement current;

    private double opposingDist = 1;
    private double diagonalDist = 1.4142; //good enough???
    private double INF = Double.MAX_VALUE;

    DijkstraSolver(GridManager man){
        this.man = man;
        grid = man.getGrid();
        elems = man.getAllElements();
        unvisited = new ArrayList<GridElement>();
        nodes = new HashMap<>();
        //in beging we have not visited any nodes
        for (GridElement elem : elems){
            nodes.put(elem,new DijkstraObject(INF,elem)); //switch to only list of dijkstraObjects
            unvisited.add(elem);
        }
    }



    public List<GridElement> solve(GridElement start, GridElement end){
        List<GridElement> path = new ArrayList<GridElement>();

        nodes.get(start).setDistance(0);
        current = start;

        double tempDist;
        GridElement smallest;

        while(unvisited.contains(end)){
            System.out.println("inside here");
            smallest = null;
            for (GridElement uN : getUnvisitedNeighbors(current)) {
                if(current.isDiagonalNeighbor(uN)){
                    tempDist = nodes.get(current).getDistance() + diagonalDist;
                }else{ //opposing
                    tempDist = nodes.get(current).getDistance() + opposingDist;

                }
                if(nodes.get(uN).getDistance() > tempDist){
                    nodes.get(uN).setDistance(tempDist);
                }
                if(smallest == null || nodes.get(smallest).getDistance() > nodes.get(uN).getDistance()){
                    smallest = uN;
                }
            }
            unvisited.remove(current);
            current = smallest;
        }
        System.out.println("Made it out off the unvisited loop");
        DijkstraObject next = nodes.get(end);
        while(!path.contains(start)){
            path.add(next.getElem());
            next = next.getPrevious();
        }

        return path;
    }


    private List<GridElement> getUnvisitedNeighbors(GridElement e) {
        List<GridElement> neighors = man.getNeighbors(e);
        List<GridElement> unvisitedNeighors = new ArrayList<GridElement>();

        for (GridElement elem : neighors) {
            if (unvisited.contains(elem)) {
                unvisitedNeighors.add(elem);
            }
        }
        return unvisitedNeighors;
    }

    /*private GridElement findDiagonalNeighbor(GridElement e) {
        List<GridElement> diagNeighors = man.getDiagonalNeighbors(e);
        for (GridElement elem : diagNeighors) {
            if (unvisited.contains(elem)) {
                unvisited.remove(elem);
                return elem;
            }
        }
        return null;
    }*/









}

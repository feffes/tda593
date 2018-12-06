package mdsd.model;

import mdsd.model.DijkstraObject;
import mdsd.model.GridElement;
import mdsd.model.GridManager;
import project.Point;

import java.util.*;

public class DijkstraSolver {
    private GridElement[][] grid;
    private List<GridElement> elems;
    private GridManager gm;
    private List<DijkstraObject> unvisited;
    private Map<GridElement, DijkstraObject> map;
    private List<DijkstraObject> nodes ;
    private GridElement current;

    private double opposingDist = 1;
    private double diagonalDist = 1.4142; //good enough???
    private double INF = Double.MAX_VALUE;

    public DijkstraSolver(GridManager gm){
        this.gm = gm;
        map =  new HashMap<>();
        grid = gm.getGrid();
        elems = gm.getAllElements();
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
    public List<GridElement> solve (Point start,Point end ){
        return solve(gm.translateToGrid(start), gm.translateToGrid(end));
    }


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

            }
            unvisited.remove(current);
            current = findShortestUnvisited();
        }
        DijkstraObject next = end;
        while(next != start){
            path.add(next.getElem());
            next = next.getPrevious();
        }
        Collections.reverse(path);

        return path;
    }


    DijkstraObject findShortestUnvisited(){
        DijkstraObject tmp = null;
        for (DijkstraObject obj : unvisited){
            if(tmp == null || obj.getDistance() < tmp.getDistance()){
                tmp = obj;
            }
        }
        return tmp;
    }

    private List<DijkstraObject> getUnvisitedNeighbors(DijkstraObject e) {
       // System.out.println(e.toString());
        List<GridElement> neighors = gm.getNeighbors(e.getElem());
        List<DijkstraObject> unvisitedNeighors = new ArrayList<DijkstraObject>();

        for (GridElement elem : neighors) {
            if (unvisited.contains(map.get(elem))) {
                unvisitedNeighors.add(map.get(elem));
            }
        }
        return unvisitedNeighors;
    }


}

package mdsd.model;

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
        elems = gm.getAllElements();
        grid = gm.getGrid();


    }

    private void init(){
        map =  new HashMap<>();
        nodes = new ArrayList<>();
        unvisited = new ArrayList<>();
        //in beging we have not visited any nodes
        for (GridElement elem : elems){
            DijkstraObject dijObj = new DijkstraObject(INF,elem);
            map.put(elem, dijObj);
            nodes.add(dijObj);
            unvisited.add(dijObj);
        }
    }

    //need to see if start and end is inside grid
    public List<GridElement> solve (Point start,Point end, int wallBuffer ){
        return solve(gm.translateToGrid(start), gm.translateToGrid(end), wallBuffer);
    }


    public List<GridElement> solve(GridElement startGE, GridElement endGE, int wallBuffer){
        init();
        DijkstraObject start = map.get(startGE);
        DijkstraObject end = map.get(endGE);

        List<GridElement> path = new ArrayList<GridElement>();

        start.setDistance(0);
        DijkstraObject current;
        current = start;

        double tempDist;

        while(current != end){ //maybe  a do while is better
            for (DijkstraObject prospect : getUnvisitedNeighbors(current, wallBuffer)) {

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
        while (next != start) {
                try {
                    path.add(next.getElem());
                    next = next.getPrevious();
                } catch (NullPointerException e) {
                    throw new DijkstraException();
                }
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

    private List<DijkstraObject> getUnvisitedNeighbors(DijkstraObject e, int wallBuffer) {
       // System.out.println(e.toString());
        List<GridElement> neighors = gm.getNeighbors(e.getElem());
        List<DijkstraObject> unvisitedNeighors = new ArrayList<DijkstraObject>();


        for (GridElement elem : neighors) {

            if (unvisited.contains(map.get(elem)) && !hasNeighborWall(elem, wallBuffer - 1)) {
                unvisitedNeighors.add(map.get(elem));
            }
        }
        return unvisitedNeighors;
    }

    private Boolean hasNeighborWall(GridElement e, int wallBuffer){
        if(wallBuffer < 0){
            return  false;
        }
        List<GridElement> neighors = gm.getNeighbors(e);

        if(neighors.size() == 8 && wallBuffer == 0){
            return  false;
        }else if(neighors.size() != 8){
            return  true;
        }

        for (GridElement elem: neighors) {
            if(hasNeighborWall(elem , wallBuffer-1)){
                return true;
            }
        }

        return false;

    }


}

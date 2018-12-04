package mdsd;

import project.Point;
import simbad.sim.AbstractWall;
import simbad.sim.HorizontalWall;
import simbad.sim.VerticalWall;

import java.util.ArrayList;
import java.util.List;

public class GridManager {

    private List<GridElement> allElements = new ArrayList<GridElement>(); //dont need??
    private GridElement[][] grid;
    private double elementSize;
    private List<AbstractWall> walls = new ArrayList<AbstractWall>();

    private int worldX1;
    private int worldZ1;
    private int worldX2;
    private int worldZ2;




    GridManager(List<GridElement> allElements){
        this.allElements = allElements;
    }

    public GridManager(){

    }

    public void addHorizantalWall(HorizontalWall wall){
        walls.add(wall);

        double x1 = wall.getP1x();
        double z1 = wall.getP1z();
        double z2 = wall.getP2z();
        double x2 = x1 + 0.3;


        while(x1 < x2){
            z1 = wall.getP1z();
            while (z1 < z2){
                translateToGrid(x1,z1).occupy();
                z1 += elementSize;
            }
            x1 += elementSize;
        }

    }

    public void addVerticalWall(VerticalWall wall){
        walls.add(wall);

        double x1 = wall.getP1x();
        double x2 = wall.getP2x();
        double z1 = wall.getP1z();
        double z2 = z1 + 0.3;

        while(x1 < x2){
            z1 = wall.getP1z();
            while (z1 < z2){
                translateToGrid(x1,z1).occupy();
                z1 += elementSize;
            }
            x1 += elementSize;
        }

    }

    public Point translateToPoint (int x, int z){
        double newX = x * elementSize;
        double newZ = z *elementSize;
        return new Point(newX,newZ);
    }

    public GridElement translateToGrid(double x, double z){
        int newX = (int) Math.round((x - worldX1) / elementSize);
        int newZ = (int) Math.round((z - worldZ1) / elementSize);
        return grid[newX][newZ];

    }


    //Possible imporovement: decide the distance between the points. 1 for now
    public void generateGrid(int x1, int z1, int x2, int z2, double elementSize){
            worldX1 = x1;
            worldX2 = x2;
            worldZ1 = z1;
            worldZ2 = z2;

        //assuming user knows wtf he's doing
        this.elementSize = elementSize;

        Double tempx = Math.abs(x2 - x1) / elementSize;
        Double tempz = Math.abs(z2 - z1) / elementSize;
        int sizeX = (int) Math.round(tempx);
        int sizeZ = (int) Math.round(tempz);
        grid = new GridElement[sizeX][sizeZ];
        int x;
        int z;

        x=0;
         while(x <= sizeX-1){
             z = 0;
             while(z <= sizeZ-1){
                 GridElement e = new GridElement(x,z,true);
                 grid[x][z] = e;
                 allElements.add(e);
                z++;
             }
             x++;
         }
    }

    public List<GridElement> getNeighbors(GridElement e){
        List<GridElement> neighbors = new ArrayList<GridElement>();

        for(int i = -1 ;i<2;i++){
            for(int j = -1; j<2 ;j++){
                if(! (i==0 && j==0))  {
                    GridElement n = getFreeNeighbor(e,i,j);
                    if(n != null){
                        neighbors.add(n);
                    }
                }
            }
        }
        return neighbors;

    }

    public List<GridElement> getDiagonalNeighbors(GridElement e){
        List<GridElement> neighbors = new ArrayList<GridElement>();
        int[] offsets = {1,-1};

        for (int i : offsets) {
            for (int j : offsets) {
                    GridElement n = getFreeNeighbor(e,i,j);
                    if(n != null){
                        neighbors.add(n);
                    }
            }
        }

        return neighbors;

    }

    public List<GridElement> getOpposingNeighbors(GridElement e){
        List<GridElement> neighbors = new ArrayList<GridElement>();
        int[] offsets = {1,-1,0};

        for (int i : offsets) {
            for (int j : offsets) {
                if(! ( (i==0 && j==0) || (i==1 && j==1) || (i==1 && j==-1)
                        || (i==-1 && j==1) || (i==-1 && j==-1))) {
                    GridElement n = getFreeNeighbor(e,i,j);
                    if(n != null){
                        neighbors.add(n);
                    }
                }

            }
        }

        return neighbors;

    }

    public GridElement getFreeNeighbor(GridElement e , int offsetX, int offsetZ){
        try{
            GridElement neighbor = grid[e.getX()+offsetX][e.getZ()+offsetZ];
            if(neighbor.isFree()){
                return neighbor;
            }else{
                return null;
            }
        }catch(Exception exp){
            return null; //maybe imporve this
        }

    }

    public GridElement[][] getGrid(){
        return grid;
    }

    public void add(GridElement ge){
        allElements.add(ge);
    }

    public List<GridElement> getAllElements(){
        return allElements;
    }

    public String gridToString() {
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < grid.length ; i++){
            for( int j = 0; j < grid[i].length ; j++){
                builder.append(grid[j][i].toString());
            }
            builder.append("\n");
        }
        return builder.toString();
    }

    public String gridToString(List<GridElement> gridList) {
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < grid.length ; i++){
            for( int j = 0; j < grid[i].length ; j++){
                if(gridList.contains(grid[j][i])){
                    builder.append("o");
                }else{
                    builder.append(grid[j][i].toString());
                }

            }
            builder.append("\n");
        }
        return builder.toString();
    }
}

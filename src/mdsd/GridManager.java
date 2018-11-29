package mdsd;

import java.util.ArrayList;
import java.util.List;

public class GridManager {

    private List<GridElement> allElements = new ArrayList<GridElement>(); //dont need??
    private GridElement[][] grid;

    GridManager(List<GridElement> allElements){
        this.allElements = allElements;
    }

    GridManager(){ }

    //Possible imporovement: decide the distance between the points. 1 for now
    public void generateGrid(int x1, int z1, int x2, int z2){

        int sizeX = Math.abs(x2 - x1);
        int sizeZ = Math.abs(z2 - z1);
        grid = new GridElement[sizeX][sizeZ];
        int x;
        int z;

        x=0;
         while(x <= sizeX-1){
             z = 0;
             while(z <= sizeZ-1){
                grid[x][z] = new GridElement(x,z,true);
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
                builder.append(grid[i][j].toString());
            }
            builder.append("\n");
        }
        return builder.toString();
    }
}

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
                grid[x][z] = new GridElement(x,z);
                z++;
             }
             x++;
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

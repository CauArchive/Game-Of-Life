package com.holub.visitor;

import com.holub.life.Neighborhood;
import com.holub.life.Resident;

import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

public class GridMatrixConvertVisitor implements CellVisitor {
    int gridSize;
    ArrayList<Boolean> residentInfo;

    public GridMatrixConvertVisitor(){
        gridSize = 1;
        residentInfo = new ArrayList<Boolean>();
    }

    public void visit(Resident resident){
        if (resident.isAlive()){
            residentInfo.add(true);
        }
        else {
            residentInfo.add(false);
        }
    }
    public void visit(Neighborhood neighborhood){
        gridSize = neighborhood.getGrid().length;
    }

    public boolean[][] getGridMatrix(){
        boolean[][] gridMatrix = new boolean[gridSize*gridSize][gridSize*gridSize];
        int row = 0;
        int column = 0;
        for(int i = 0; i < gridSize; i++) {
            int offset0 = i * gridSize * gridSize * gridSize;
            for (int j = 0; j < gridSize; j++) {
                int offset1 = j * gridSize;
                for (int k = 0; k < gridSize; k++) {
                    int offset2 = gridSize * gridSize * k;
                    for (int l = 0; l < gridSize; l++) {
                        int offset3 = l;
                        int index = offset0 + offset1 + offset2 + offset3;
                        gridMatrix[row][column] =  residentInfo.get(index);
                        column++;
                    }
                }
                row++;
                column = 0;
            }
        }
        return gridMatrix;
    }
}

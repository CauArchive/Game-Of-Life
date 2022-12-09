package com.holub.inputstream;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.stream.IntStream;

public class GridMatrixStreamAdapter extends InputStream {
    private Character liveChar = '\u25A0'; // \u25A0 = ■
    private Character deadChar = '\u25A1'; // \u25A1 = □
    boolean[][] gridMatrix;
    private int row;
    private int col;
    boolean isRowChanged;

    public GridMatrixStreamAdapter(boolean[][] gridMatrix) {
        this.gridMatrix = gridMatrix;
        this.row = 0;
        this.col = 0;
        isRowChanged = false;
    }

    @Override
    public int read() throws IOException {
        if (isRowChanged) {
            isRowChanged = false;
            return '\n';
        }
        if(isFinished())
            return -1;
        int ret = gridMatrix[row][col] ? liveChar : deadChar;
        setNextIndex();
        return ret;
    }

    private void setNextIndex(){
        col++;
        if (col >= gridMatrix[0].length){
            col = 0;
            row++;
            isRowChanged = true;
        }
    }

    private boolean isFinished(){
        return row >= gridMatrix.length;
    }

}

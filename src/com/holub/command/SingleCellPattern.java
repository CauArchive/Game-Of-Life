package com.holub.command;

import com.holub.life.Cell;

public class SingleCellPattern extends CellPattern{
    public SingleCellPattern(Cell outermostCell) {
        super(outermostCell);
    }

    @Override
    void setDeltaIndex(){
        deltaIndex = new int[][]{
            {0, 0},
        };
    }
}

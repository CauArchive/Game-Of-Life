package com.holub.command;

import com.holub.life.Cell;

public class GliderCellPattern extends CellPattern {

    public GliderCellPattern(Cell outermostCell) {
        super(outermostCell);
    }

    @Override
    void setDeltaIndex(){
        deltaIndex = new int[][]{
                {0, 0},
                {0, 1},
                {0, 2},
                {1, 0},
                {2, 1},
        };
    }

}

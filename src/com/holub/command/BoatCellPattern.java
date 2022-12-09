package com.holub.command;

import com.holub.life.Cell;
import com.holub.ui.MenuSite;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BoatCellPattern extends CellPattern {

    public BoatCellPattern(Cell outermostCell) {
        super(outermostCell);
    }

    @Override
    void setDeltaIndex(){
        deltaIndex = new int[][]{
            {0, 0},
            {1, 0},
            {0, 1},
            {1, 2},
            {2, 1},
        };
    }

}

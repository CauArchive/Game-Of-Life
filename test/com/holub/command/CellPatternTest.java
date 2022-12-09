package com.holub.command;

import com.holub.life.Cell;
import com.holub.life.Universe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class CellPatternTest {
    private Universe universe = new Universe(0);
    Cell outermostCell;
    Rectangle surface = new Rectangle(0, 0, 512, 512);
    @BeforeEach
    void setUp() {
        outermostCell = universe.getOutermostCell();
        surface.x = 0;
        surface.y = 0;
    }
    @Test
    void executePatternTest(){

        CellPattern cellPattern = new BoatCellPattern(outermostCell);
        cellPattern.executePattern(new Point(1,1), surface);

        Cell[][] grid = outermostCell.getGrid();

        assertTrue(grid[0][0].isActive(0,1));
        assertTrue(grid[0][0].isActive(0,0));
        assertTrue(grid[0][0].isActive(1,0));

    }
}
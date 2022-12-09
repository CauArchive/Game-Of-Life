package com.holub.life;

import com.holub.visitor.CellVisitor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class NeighborhoodTest {
    Universe universe =  new Universe(0);
    Cell outermostCell;
    Cell[][] grid;

    @BeforeEach
    void setup(){
        outermostCell = universe.getOutermostCell();
        grid = outermostCell.getGrid();

    }
    // 특정 셀 살았는지 확인하는 테스트
    @Test
    void checkSellIsAlive() {
        grid[0][0].makeActive(0, 0);
        assertTrue(grid[0][0].isActive(0,0));

    }
    // 특정 셀 죽었는지 확인하는 테스트
    @Test
    void checkCellIsDead(){
        grid[0][0].clear();
        assertFalse(grid[0][0].isActive(2, 2));
    }

}
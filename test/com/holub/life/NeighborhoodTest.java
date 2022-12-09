package com.holub.life;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class NeighborhoodTest {
    Cell cell;
    @BeforeEach
    void setup(){
        cell = new Resident();
    }
    // 모든 셀 죽었는지 확인하는 테스트
    @Test
    void checkAllCellDead() {
        final Cell[][] grid = new Cell[8][8];
        for( int row = 0; row < 8; ++row )
            for( int column = 0; column < 8; ++column )
                grid[row][column] = cell.create();

        for( int row = 0; row < 8; ++row )
            for( int column = 0; column < 8; ++column )
                assertFalse(grid[row][column].isAlive());
    }

}
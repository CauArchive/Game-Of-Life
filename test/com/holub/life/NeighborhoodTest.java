package com.holub.life;

import com.holub.visitor.CellVisitor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class NeighborhoodTest {
    private Cell cell;
    private Cell aliveCell;
    private final Cell[][] grid = new Cell[8][8];

    @BeforeEach
    void setup(){
        cell = new Resident();
        for( int row = 0; row < 8; ++row )
            for( int column = 0; column < 8; ++column )
                grid[row][column] = cell;

        aliveCell = new Cell() {
            @Override
            public boolean figureNextState(Cell north, Cell south, Cell east, Cell west, Cell northeast, Cell northwest, Cell southeast, Cell southwest) {
                return false;
            }

            @Override
            public Cell edge(int row, int column) {
                return null;
            }

            @Override
            public boolean transition() {
                return false;
            }

            @Override
            public void redraw(Graphics g, Rectangle here, boolean drawAll) {

            }

            @Override
            public void userClicked(Point here, Rectangle surface) {

            }

            @Override
            public int getPixelsPerResident(Rectangle surface) {
                return 0;
            }

            @Override
            public void accept(CellVisitor visitor) {

            }

            @Override
            public boolean isAlive() {
                return true;
            }

            @Override
            public int widthInCells() {
                return 0;
            }

            @Override
            public Cell create() {
                return null;
            }

            @Override
            public Direction isDisruptiveTo() {
                return null;
            }

            @Override
            public void clear() {

            }

            @Override
            public boolean transfer(Storable memento, Point upperLeftCorner, boolean doLoad) {
                return false;
            }

            @Override
            public Storable createMemento() {
                return null;
            }
        };
    }
    // 모든 셀 죽었는지 확인하는 테스트
    @Test
    void checkAllCellDead() {


        for( int row = 0; row < 8; ++row )
            for( int column = 0; column < 8; ++column )
                assertFalse(grid[row][column].isAlive());
    }
    // 특정 셀 살았는지 확인하는 테스트
    @Test
    void checkSellIsAlive() {
        grid[2][2] = aliveCell;
        assertTrue(grid[2][2].isAlive());

    }
    // 특정 셀 죽었는지 확인하는 테스트
    @Test
    void checkCellIsDead(){
        grid[2][2].clear();
        assertFalse(grid[2][2].isAlive());
    }

}
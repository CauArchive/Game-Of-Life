package com.holub.command;

import com.holub.life.Cell;
import com.holub.life.Neighborhood;
import com.holub.life.Resident;

import java.awt.*;

public abstract class CellPattern {
    Point startPoint;
    int  [][]deltaIndex;
    Cell outermostCell;
    Rectangle surface;

    public CellPattern(Cell outermostCell){
        this.outermostCell = outermostCell;
    }

    public void executePattern(Point startPoint, Rectangle surface){
        setStartPoint(startPoint);
        setDeltaIndex();
        for (int [] delta :deltaIndex) {
            int pixelsPerCell = ((Neighborhood)outermostCell).getPixelsPerResident(surface);
            Point nextPoint = new Point(startPoint.x + delta[0] * pixelsPerCell, startPoint.y + delta[1] * pixelsPerCell);
            if (surface.height > nextPoint.y && surface.width > nextPoint.x)
                outermostCell.userClicked(nextPoint,surface);
        }
    }

    public void setStartPoint(Point point) {
        this.startPoint = point;
    }

    abstract void setDeltaIndex();
}

package com.holub.visitor;

import com.holub.life.Neighborhood;
import com.holub.life.Resident;

public interface CellVisitor {
    public void visit(Resident resident);
    public void visit(Neighborhood neighborhood);
}

package com.holub.visitor;

import com.holub.life.Neighborhood;
import com.holub.life.Resident;

public class CellReverseVisitor implements CellVisitor{
    public void visit(Resident resident){
        resident.reverse();
    }
    public void visit(Neighborhood neighborhood){

    }
}

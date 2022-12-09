package com.holub.visitor;

import com.holub.life.Neighborhood;
import com.holub.life.Resident;

import java.io.*;
import java.util.ArrayList;

public class CellPrintVisitor implements CellVisitor {
    int row;
    int column;
    ArrayList<Character> residentInfo;
    PrintWriter printWriter;

    public CellPrintVisitor(){
        row = 0;
        column = 0;
        residentInfo = new ArrayList<Character>();

        try{
            printWriter = new PrintWriter(new OutputStreamWriter(System.out, "UTF-8"));
        }catch (Exception e){
            e.printStackTrace();
            printWriter = new PrintWriter(System.out);
        }
    }

    public void visit(Resident resident){
        if (resident.isAlive()){
            //residentInfo.add('■');
            residentInfo.add('\u25A0'); // \u25A0 = ■
        }
        else {
            //residentInfo.add('□');
            residentInfo.add('\u25A1'); // \u25A1 = □
        }
    }
    public void visit(Neighborhood neighborhood){

    }

    public void print(int gridSize){
        for(int i = 0; i < gridSize; i++) {
            int offset0 = i * gridSize * gridSize * gridSize;
            for (int j = 0; j < gridSize; j++) {
                int offset1 = j * gridSize;
                for (int k = 0; k < gridSize; k++) {
                    int offset2 = gridSize * gridSize * k;
                    for (int l = 0; l < gridSize; l++) {
                        int offset3 = l;
                        int index = offset0 + offset1 + offset2 + offset3;
                        printWriter.print(residentInfo.get(index));
                    }
                }
                printWriter.println();
                printWriter.flush();
            }
        }
    }

    public void setPrintWriter(PrintWriter printWriter){
        try {
            this.printWriter = printWriter;
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}

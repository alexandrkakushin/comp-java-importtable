/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.ak.importtable.model;

import java.util.ArrayList;
import javax.xml.bind.annotation.*;

/**
 *
 * @author ak
 */

@XmlAccessorType(XmlAccessType.FIELD)
public class ModelRow {

    @XmlElement(name = "cell")    
    private ArrayList<ModelCell> cells = new ArrayList<>();

    public ModelRow() {
        //cells = new ArrayList<>();
    }
        
    public ModelRow(String[] fields) {
        //cells = new ArrayList<>();
    }    
    
    public ModelRow(ArrayList<ModelCell> cells) {
        this.cells = new ArrayList<>(cells);        
    }
    
    public ArrayList<ModelCell> getCells() {
        ArrayList<ModelCell> cellsCopy = new ArrayList<>(this.cells);
        return cellsCopy;
    }
}

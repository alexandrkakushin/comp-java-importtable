/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.ak.importtable.model;

/**
 *
 * @author akakushin
 */

public class ModelField implements Comparable<ModelField>{

    private String name;
    private int index;
    private String type;

    public ModelField() {
        this(null, -1, null);
    }

    public ModelField(String name, int index, String type) {
        this.name = name;
        this.index = index;
        this.type = type;
    }
           
    public void setIndex(int index) {
        this.index = index;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }
    
    public int getIndex() {
        return this.index;
    }

    public String getName() {
        return this.name;
    }
    
    public String getType() {
        return this.type;
    }

    @Override
    public int compareTo(ModelField other) {
        return this.name.compareTo(other.getName());
    }
    
}

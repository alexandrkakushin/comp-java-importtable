/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.ak.importtable.model;

import javax.xml.bind.annotation.*;

/**
 *
 * @author ak
 * @param <T>
 */

@XmlAccessorType(XmlAccessType.FIELD)
public class ModelCell<T> {
    
    private T value;
    
    @XmlAttribute
    private String type;
        
    public ModelCell() {
        this(null, null);
    }
    
    public ModelCell(T arg) {
        this.value = arg;
    }
    
    public ModelCell(T arg, String type) {
        this.value = arg;
        this.type = type;
    }
                    
    public void setValue(T value) {
        this.value = value;
    }
    
    public T getValue() {
        return this.value;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    public String getType() {
        return this.type;
    }
    
    public String getClassName() {
        return (this.value != null) ? this.value.getClass().getName() : null;
    }
    
    @Override
    public String toString() {        
        return (this.value != null) ? this.value.toString() : null;
    }
    
}

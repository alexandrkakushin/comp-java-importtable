/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.ak.importtable.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

/**
 *
 * @author akakushin
 */

@XmlAccessorType(XmlAccessType.FIELD)
public class ModelPing {
    
    private boolean error;
    private String description;

    public ModelPing() {
        this.error = false;
        this.description = "";
    }

    public ModelPing(boolean error, String description) {
        this.error = error;
        this.description = description;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}

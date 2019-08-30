package ru.ak.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

/**
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

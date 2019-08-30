package ru.ak.model;

import java.util.ArrayList;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

/**
 *
 * @author akakushin
 */

@XmlAccessorType(XmlAccessType.FIELD)
public class ModelDb {

    private String error;
    
    @XmlElementWrapper(name = "tables")
    @XmlElement(name = "table")
    private final ArrayList<ModelTable> tables; 

    public ModelDb() {
        this.tables = new ArrayList<>();
    }

    public void addTable(ModelTable table) {
        this.tables.add(table);
    }
    
    public void setError(String error) {
        this.error = error;
    }
    
    public String getError() {
        return this.error;
    }
    
}

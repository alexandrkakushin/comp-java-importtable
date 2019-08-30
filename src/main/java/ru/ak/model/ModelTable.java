package ru.ak.model;

import java.util.ArrayList;
import java.util.Collections;

import javax.xml.bind.annotation.*;

/**
 *
 * @author akakushin
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class ModelTable {

    private String name;    
    
    @XmlElementWrapper(name = "fields")
    @XmlElement(name = "field")
    private final ArrayList<ModelField> fields; 
    
    @XmlElementWrapper(name = "rows")
    @XmlElement(name = "row")    
    private final ArrayList<ModelRow> rows;

    private String error;
    
    public ModelTable() {
        rows = new ArrayList<>();
        fields = new ArrayList<>();
        error = null;
        this.name = null;
    }

    public void addField(ModelField field) {
        this.fields.add(field);
    }
    
    public void sortFieldsByName() {
        Collections.sort(fields);
    }
    
    public String[] getFieldNames() {
        String[] result = new String[fields.size()];        
        for (int i = 0; i < result.length; i++) {
            result[i] = fields.get(i).getName();
        }        
        return result;
    }
    
    public void addRow(ModelRow row) {
        this.rows.add(row);
    }
    
    public int getCountFields() {
        return fields.size();
    }

    public void setError(String description) {
        this.error = description;
    }
    
    public String getName() {
        return this.name;        
    }
            
    public void setName(String name) {
        this.name = name;
    }
    
}

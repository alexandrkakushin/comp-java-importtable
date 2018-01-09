/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.ak.importtable.parser;

import ru.ak.importtable.model.ModelTable;
import com.opencsv.CSVReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;

import ru.ak.importtable.model.ModelCell;
import ru.ak.importtable.model.ModelField;

/**
 *
 * @author ak
 */
public class CSVParser {

    public static ModelTable parse(String name, String charset, char separator) {
        ModelTable table = new ModelTable();
        
        try (CSVReader reader = new CSVReader(
                new InputStreamReader(
                        new FileInputStream(name), Charset.forName(charset)), 
                separator)) {

            boolean addingFields = true;
            
            String[] record = null;
            while ((record = reader.readNext()) != null) {
                ArrayList<ModelCell> modelCells = new ArrayList<>();
                
                for (int i = 0; i < record.length; i++) {
                    ModelCell modelCell = new ModelCell();  
                    modelCell.setValue(record[i]);
                    modelCells.add(modelCell);
                    if (addingFields) {
                        table.addField(new ModelField("field" + Integer.toString(i), i, String.class.toString()));
                    }                    
                }
                addingFields = false;                
                table.addRow(new ru.ak.importtable.model.ModelRow(modelCells));
            }    
                        
        } catch (IOException ex) {
            table.setError(ex.getLocalizedMessage());
        }
                
        return table;
    }
    
}

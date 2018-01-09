/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.ak.importtable.parser;

import com.linuxense.javadbf.*;
import java.io.FileInputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import ru.ak.importtable.model.ModelCell;
import ru.ak.importtable.model.ModelField;
import ru.ak.importtable.model.ModelTable;

/**
 *
 * @author ak
 */
public class DbfParser {

    public static ModelTable parse(String name, String charset) {
        ModelTable table = new ModelTable();        
        try (DBFReader reader = new DBFReader(new FileInputStream(name), Charset.forName(charset))) {           
            int numberOfFields = reader.getFieldCount();                        
            String[] fields = new String[numberOfFields];
            for (int i = 0; i < numberOfFields; i++) {
                DBFField dbfField = reader.getField(i);
                table.addField(new ModelField(dbfField.getName(), i, dbfField.getType().toString()));
                fields[i] = reader.getField(i).getName();
            }
            
            Object[] rowObjects;
            ArrayList<ModelCell> modelCells = new ArrayList<>();
            while ((rowObjects = reader.nextRecord()) != null) { 
                modelCells.clear();
                for (int i = 0; i < rowObjects.length; i++) {                    
                    ModelCell modelCell = new ModelCell();  
                    modelCell.setValue(rowObjects[i]);
                    modelCells.add(modelCell);
                }
                table.addRow(new ru.ak.importtable.model.ModelRow(modelCells));
            }            
        } catch (Exception ex) {
            Logger.getLogger(JdbcParser.class.getName()).log(Level.SEVERE, null, ex);            
            table.setError(ex.getLocalizedMessage());
        }
        
        return table;
    }
    
}

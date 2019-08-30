/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.ak.importtable.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import static java.net.HttpURLConnection.HTTP_OK;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import ru.ak.model.ModelCell;
import ru.ak.model.ModelField;
import ru.ak.model.ModelTable;


/**
 *
 * @author akakushin
 */
public class BudgetGovRuParser {

    static final String URL_REGISTRY = "http://budget.gov.ru/epbs/registry/";
    
    public static ModelTable parse(String innerCode) {
    
        ModelTable table = new ModelTable();        
                
        int pageCount = 1;
        int pageNum = 1;
        JSONObject page = null;
                        
        while (pageNum <= pageCount) {
            page = getPage(innerCode, pageNum);
            if (page != null) {
                pageCount = (int) page.get("pageCount");
                addPage(table, page);
            }
            pageNum++;
        }
                
        return table;
    }
    
    private static JSONObject getPage(String innerCode, int pageNum) {

        JSONObject data = null;
        
        try {
            URL url = new URL(URL_REGISTRY + innerCode + "/data?pageSize=1000&pageNum=" + Integer.toString(pageNum));
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");            
            if (connection.getResponseCode() == HTTP_OK) {
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
                StringBuilder sb = new StringBuilder();
                String out;
                
                while ((out = br.readLine()) != null) {
                    sb.append(out);
                }
                connection.disconnect();                                
                data = new JSONObject(sb.toString());                
            }
            
        } catch (MalformedURLException ex) {
            Logger.getLogger(BudgetGovRuParser.class.getName()).log(Level.SEVERE, null, ex);          
            
        } catch (IOException ex) {
            Logger.getLogger(BudgetGovRuParser.class.getName()).log(Level.SEVERE, null, ex);
        }
         
        return data;
    }
    
    private static void addPage(ModelTable table, JSONObject page) {
    
        JSONArray data = page.getJSONArray("data");

        JSONObject row;        
        String[] fields = table.getFieldNames();
        
        for (int i = 0; i < data.length(); i++) {
            row = data.getJSONObject(i);             
            if (table.getCountFields() == 0) {
                JSONArray names = row.names();                
                for (int iField = 0; iField < names.length(); iField++) {
                    table.addField(new ModelField(names.getString(iField), iField, String.class.toString()));
                }                
                table.sortFieldsByName();
                fields = table.getFieldNames();
            }
                        
            ArrayList<ModelCell> modelCells = new ArrayList<>();
            for (String fieldName : fields) {
                ModelCell modelCell = new ModelCell();
                Object value = row.get(fieldName);
                if (value.getClass() == JSONArray.class) {
                    value = "";
                }
                modelCell.setValue(value);
                modelCells.add(modelCell);                
            }                        
            table.addRow(new ru.ak.model.ModelRow(modelCells));
        }        
    }
    
}

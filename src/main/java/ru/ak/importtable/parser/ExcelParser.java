/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.ak.importtable.parser;

import java.io.FileInputStream;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import ru.ak.importtable.model.ModelCell;
import ru.ak.importtable.model.ModelField;
import ru.ak.importtable.model.ModelTable;

/**
 *
 * @author ak
 */
public class ExcelParser {

    private static boolean isXSSF(String name) {
        return Paths.get(name).toString().endsWith(".xlsx");
    }
    
    
    public static ModelTable parseWithEmpty(String name, int pageIndex) {
        
        ModelTable table = new ModelTable();        
        
        try {
            boolean isXSSF = isXSSF(name);                
            XSSFSheet sheet2007 = null; 
            HSSFSheet sheet = null; 
            FileInputStream fis = new FileInputStream(name);

            if (isXSSF) {
                XSSFWorkbook workbook2007 = new XSSFWorkbook(fis);
                sheet2007 = workbook2007.getSheetAt(pageIndex); 
            } else {
                HSSFWorkbook workbook97 = new HSSFWorkbook(fis);
                sheet = workbook97.getSheetAt(pageIndex); 
            }                
            int countRows = (isXSSF) ? sheet2007.getLastRowNum() : sheet.getLastRowNum();                

            ArrayList<ModelCell> modelCells = new ArrayList<>();
            for (int iRow = 0; iRow < countRows; iRow++) {
                modelCells.clear();
                Row row = (isXSSF) ? sheet2007.getRow(iRow) : sheet.getRow(iRow);
                if (row != null) {
                    for (int i = 0; i < row.getLastCellNum(); i++) {                    
                        if (table.getCountFields() < i + 1) {
                            table.addField(new ModelField("field" + Integer.toString(i), i, null));
                        }

                        Cell excelCell = row.getCell(i);
                        ModelCell modelCell = new ModelCell();
                        if (excelCell != null) {            
                            modelCell.setType(excelCell.getCellTypeEnum().toString());
                            switch (excelCell.getCellTypeEnum()) {
                                case BLANK:
                                    break;
                                case BOOLEAN:
                                    modelCell.setValue(excelCell.getBooleanCellValue());
                                    break;
                                case ERROR:
                                    break;
                                case FORMULA:
                                    break;
                                case NUMERIC:
                                    modelCell.setValue(excelCell.getNumericCellValue());
                                    break;
                                case STRING:
                                    modelCell.setValue(excelCell.getStringCellValue());
                                    break;
                                case _NONE:
                                    break;
                                default:
                                    break;
                            }
                        }                        
                        modelCells.add(modelCell);                    
                    }                    
                }
                table.addRow(new ru.ak.importtable.model.ModelRow(modelCells));
            }
        } catch (Exception ex) {
            Logger.getLogger(JdbcParser.class.getName()).log(Level.SEVERE, null, ex);
            table.setError(ex.getLocalizedMessage());
        }
                    
        return table;
        
    }
      
    public static ModelTable parse(String name, int pageIndex) {
        return null;       
    }
    
}

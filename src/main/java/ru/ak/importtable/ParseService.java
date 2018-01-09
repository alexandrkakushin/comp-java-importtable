/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.ak.importtable;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import ru.ak.importtable.parser.DbfParser;
import ru.ak.importtable.parser.JdbcParser;
import ru.ak.importtable.parser.BudgetGovRuParser;
import ru.ak.importtable.parser.CSVParser;
import ru.ak.importtable.parser.ExcelParser;
import ru.ak.importtable.model.ModelDb;
import ru.ak.importtable.model.ModelPing;
import ru.ak.importtable.model.ModelTable;


/**
 *
 * @author akakushin
 */
@WebService(name = "Parse", serviceName = "ParseService", portName = "ParsePort")
public class ParseService {
        
    @WebMethod (operationName = "parseExcel")
    public ModelTable parseExcel(@WebParam (name = "inFile") String inFile, @WebParam (name = "pageIndex") int pageIndex) {
        return ExcelParser.parseWithEmpty(inFile, pageIndex);
    }    
    
    @WebMethod (operationName = "parseDbf")
    public ModelTable parseDbf(@WebParam (name = "inFile") String inFile, @WebParam (name = "charset") String charset) {
        return DbfParser.parse(inFile, charset);
    } 
    
    @WebMethod
    public ModelTable parseCsv(
            @WebParam (name = "inFile") String inFile, 
            @WebParam (name = "charset") String charset,
            @WebParam (name = "separator") String separator) {
        
        char s = com.opencsv.CSVParser.DEFAULT_SEPARATOR;
        if (!separator.isEmpty()) {
            s = separator.charAt(0);
        }
        
        return CSVParser.parse(inFile, charset, s);
    }
    
    @WebMethod
    public ModelTable parseBudgetGovRu(
            @WebParam (name = "innerCode") String innerCode) {        
        return BudgetGovRuParser.parse(innerCode);        
    }
    
    
    @WebMethod (operationName = "parseJdbc")
    public ModelTable parseJdbc(@WebParam (name = "dbms") String dbms, @WebParam (name = "connectionString") String connectionString, 
            @WebParam (name = "query") String query) {
        
        return JdbcParser.parse(dbms, connectionString, query);
    }
    
    @WebMethod (operationName = "pingJdbc")
    public ModelPing pingJdbc(@WebParam (name = "dbms") String dbms, @WebParam (name = "connectionString") String connectionString) {
        return JdbcParser.ping(dbms, connectionString);
    }
    
    @WebMethod (operationName = "structureDb") 
    public ModelDb structureDb(@WebParam (name = "dbms") String dbms, @WebParam (name = "connectionString") String connectionString) {
        return JdbcParser.getStructure(dbms, connectionString);
    }
    
    
    @WebMethod (operationName = "version")
    public String version() {
        return "1.0-6";
    }
        
}

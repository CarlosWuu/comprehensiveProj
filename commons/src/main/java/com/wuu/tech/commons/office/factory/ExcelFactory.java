package com.wuu.tech.commons.office.factory;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

/**
 * Created by az6295 on 2018/5/16.
 */
public class ExcelFactory {
    public final static String XLS_SUFFIX = ".xls";
    public final static String XLSX_SUFFIX = ".xlsx";

    private ExcelFactory(){

    }

    public static Workbook getInstance(String filename) throws IllegalArgumentException{

        if(filename == null){
            throw new IllegalArgumentException("filename cannot be null!");
        }

        if(filename.endsWith(XLS_SUFFIX)){
            return new HSSFWorkbook();
        }else if(filename.endsWith(XLSX_SUFFIX)){
            return new SXSSFWorkbook();
        }else{
            throw new IllegalArgumentException("this filename is not supported");
        }

    }

}

package com.wuu.tech.commons.office;

import com.wuu.tech.commons.office.factory.ExcelFactory;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

/**
 * Created by az6295 on 2018/5/16.
 */
public class ExcelReader {



    private ExcelReader(){

    }

    public static void createExcel(List<ExcelRow> excelRows,String path,String filename) throws Exception {
        Workbook workbook = processData(excelRows, ExcelFactory.getInstance(filename));
        workbook.write(new FileOutputStream(new File(path+filename)));
    }



    /**
     * 生成xls（office 2007版本以前）及xlsx（office 2007及以上版本）
     * @param rows
     * @return
     */
    private static Workbook processData(List<ExcelRow> rows,Workbook wb) throws IllegalAccessException {
        if(null == wb){
            throw new IllegalAccessException("Workbook cannot be null!");
        }

        Sheet sheet = wb.createSheet();
        for(ExcelRow rowObj:rows){
            Row row = sheet.getRow(rowObj.getRow());
            if(null == row){
                row = sheet.createRow(rowObj.getRow());
            }
            Cell cell = row.createCell(rowObj.getCell());
            if(null != rowObj.getCellStyleHandler()){
                rowObj.getCellStyleHandler().setCellStyle(wb.createCellStyle(),cell);
            }

            rowObj.setCell(cell);

            ExcelRow.MergeProperty mergeProperty = rowObj.getMergeProperty();
            if(mergeProperty != null){
                sheet.addMergedRegion(new CellRangeAddress(mergeProperty.getFirstRow(),mergeProperty.getLastRow(),mergeProperty.getFirstCol(),mergeProperty.getLastCol()));
            }

        }
        return wb;
    }
}

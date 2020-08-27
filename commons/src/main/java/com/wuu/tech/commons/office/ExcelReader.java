package com.wuu.tech.commons.office;

import com.wuu.tech.commons.office.factory.ExcelFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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


    public static void main(String[] args) {
        try {
            FileInputStream in = new FileInputStream(new File("/Users/carloswuu/Desktop/user.xls"));

            FileReader reader = new FileReader(new File("/Users/carloswuu/Desktop/data.txt"));
            BufferedReader bufferedReader = new BufferedReader(reader);
            Map<Integer,String> map = new HashMap<>();
            String str;
            while((str = bufferedReader.readLine()) != null){
                String[] s = str.split(",");
                map.put(Integer.parseInt(s[0]),s[1]);
            }

            Workbook instance = new HSSFWorkbook(in);
            List<Row> allRows = getAllRows(instance);

            FileOutputStream out = new FileOutputStream(new File("/Users/carloswuu/Desktop/userNew.xls"));
            Workbook newBook = ExcelFactory.getInstance("userNew.xls");
            Sheet sheet = newBook.createSheet();
            Sheet sheet2 = newBook.createSheet();

            Sheet curor = sheet;
            int index = 1;
            for (Row row:allRows){
                if(index > 65536){
                    curor = sheet2;
                    index = 1;
                }
                int physicalNumberOfCells = row.getPhysicalNumberOfCells();

                Row newRow = curor.createRow(index - 1);
                Cell originCell = row.getCell(1);
                Cell cell = newRow.createCell(0);
                if(originCell.getCellType() == 0){
                    Double numericCellValue = originCell.getNumericCellValue();
                    cell.setCellValue(map.get(numericCellValue.intValue()));
                }else{
                    String stringCellValue = originCell.getStringCellValue();
                    cell.setCellValue(stringCellValue.equals("null")? "null":map.get(Integer.parseInt(stringCellValue)));
                }
                /*for(int columnIndex = 0;columnIndex<physicalNumberOfCells;columnIndex++){
                    Cell cell = newRow.createCell(columnIndex);
                    Cell originCell = row.getCell(columnIndex);
                    if(originCell.getCellType() == 0){
                        Double numericCellValue = originCell.getNumericCellValue();


                        cell.setCellValue(map.get(numericCellValue.intValue()));
                    }else{
                        cell.setCellValue(originCell.getStringCellValue());
                    }

                }*/

                index++;
            }
            newBook.write(out);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static List<Row> getAllRows(Workbook workbook){
        int numberOfSheets = workbook.getNumberOfSheets();
        List<Row> rows = new ArrayList<>();

        for(int index =0;index<numberOfSheets;index++){
            Sheet sheet = workbook.getSheetAt(index);
            int physicalNumberOfRows = sheet.getPhysicalNumberOfRows();
            for(int rowIndex = 0;rowIndex<physicalNumberOfRows;rowIndex++){
                Row row = sheet.getRow(rowIndex);
                rows.add(row);
            }
        }

        return rows;
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

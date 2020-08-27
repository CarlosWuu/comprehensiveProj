package com.wuu.tech.commons.office;

import com.wuu.tech.commons.office.factory.ExcelFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

/**
 * @author: wjb
 * @Date: 2020/4/20 上午10:40
 */
public class ReadUtil {

    public static void main(String[] args) {
     /*    = ExcelFactory.getInstance("");
        instance.*/

        Workbook instance = null;
        try {
            instance = new HSSFWorkbook(new FileInputStream(new File("/Users/carloswuu/Desktop/4.8上线之前数据.xls")));

            Sheet sheetAt = instance.getSheetAt(0);

            Iterator<Row> iterator = sheetAt.iterator();

            while(iterator.hasNext()){
                Row next = iterator.next();
                Cell cell = next.getCell(0);

                System.out.println(cell.getStringCellValue());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }



    }
}

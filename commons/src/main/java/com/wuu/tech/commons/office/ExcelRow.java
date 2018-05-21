package com.wuu.tech.commons.office;

import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;

/**
 * Created by az6295 on 2018/5/16.
 */
public class ExcelRow {

    //行号
    private int row;
    //列号
    private int cell;
    private Object cellValue;

    /**
     * firstRow，lastRow，firstCol，lastCol
     * 这几个属性用来设定合并单元格的范围
     */
    private MergeProperty mergeProperty;


    //通用设置Excel样式属性
    private CellStyleHandler cellStyleHandler;

    public ExcelRow(){}

    public ExcelRow(int row, int cell, Object cellValue) {
        this.row = row;
        this.cell = cell;
        this.cellValue = cellValue;
    }

    public ExcelRow(int row, int cell, Object cellValue, MergeProperty mergeProperty) {
        this.row = row;
        this.cell = cell;
        this.cellValue = cellValue;
        this.mergeProperty = mergeProperty;
    }


    /**
     * 兼容xls、xlsx设置单元格值
     * @param cell
     * @throws IllegalAccessException
     */
    public void setCell(Cell cell)throws IllegalAccessException{
        if(null == cellValue){
            throw new IllegalAccessException("cell值不能为空");
        }
        if(cellValue instanceof String){
            cell.setCellValue(new HSSFRichTextString((String)cellValue));
        }else if(cellValue instanceof Integer){
            cell.setCellValue((int)cellValue);
        }else{
            cell.setCellValue((double)cellValue);
        }
    }


    public interface CellStyleHandler{
        void setCellStyle(CellStyle style, Cell cell);
    }

    public static class MergeProperty{
        private int firstRow;
        private int lastRow;
        private int firstCol;
        private int lastCol;

        public MergeProperty(int firstRow, int lastRow, int firstCol, int lastCol) {
            this.firstRow = firstRow;
            this.lastRow = lastRow;
            this.firstCol = firstCol;
            this.lastCol = lastCol;
        }

        public int getFirstRow() {
            return firstRow;
        }

        public void setFirstRow(int firstRow) {
            this.firstRow = firstRow;
        }

        public int getLastRow() {
            return lastRow;
        }

        public void setLastRow(int lastRow) {
            this.lastRow = lastRow;
        }

        public int getFirstCol() {
            return firstCol;
        }

        public void setFirstCol(int firstCol) {
            this.firstCol = firstCol;
        }

        public int getLastCol() {
            return lastCol;
        }

        public void setLastCol(int lastCol) {
            this.lastCol = lastCol;
        }
    }

    public CellStyleHandler getCellStyleHandler() {
        return cellStyleHandler;
    }

    public void setCellStyleHandler(CellStyleHandler cellStyleHandler) {
        this.cellStyleHandler = cellStyleHandler;
    }

    public int getCell() {
        return cell;
    }

    public void setCell(int cell) {
        this.cell = cell;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public Object getCellValue() {
        return cellValue;
    }

    public void setCellValue(Object cellValue) {
        this.cellValue = cellValue;
    }

    public MergeProperty getMergeProperty() {
        return mergeProperty;
    }

    public void setMergeProperty(MergeProperty mergeProperty) {
        this.mergeProperty = mergeProperty;
    }

}

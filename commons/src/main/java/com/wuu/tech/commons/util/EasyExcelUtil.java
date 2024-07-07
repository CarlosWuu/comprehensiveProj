package com.wuu.tech.commons.util;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class EasyExcelUtil {
    private EasyExcelUtil(){

    }

    public static void main(String[] args) {
        List<RepairItem> repairItems = readExcel(new File("/Users/wujiabin/Desktop/售改项目导入0328.xlsx"), RepairItem.class);

        List<String> sqls = new ArrayList<>();
        for(RepairItem repairItem:repairItems){
            String sql = "INSERT INTO TM_REPAIR_ITEM (REPAIR_ITEM_ID,PLANT_NO,GROUP_CODE,LOCAL_LABOUR_CODE,SGM_LABOUR_CODE,LABOUR_NAME_LOCAL,LABOUR_NAME_SGM,STD_LABOUR_HOUR,ASSIGN_LABOUR_HOUR,PRICE,OPERATION_CODE,WORKER_TYPE,SPELL_CODE,MODEL_LABOUR_CODE,CREATE_BY,CREATE_DATE,UPDATE_BY,UPDATE_DATE,ASC_CODE,ENABLE,ADD_LABOUR_HOUR) VALUES (SEQ_REPAIR_ITEM.nextval,'10000004  ','%s','%s','%s','%s','%s',0,0,NULL,'%s',NULL,NULL,'%s',-1,sysdate,-1,sysdate,'10000004  ',1,0);";
            String hangCode = repairItem.getHangCode();
            String hangName = repairItem.getHangName();
            String model = repairItem.getModel();

            String[] split = model.split("、");
            for(String splitModel:split){
                String format = String.format(sql, hangCode, hangCode, hangCode, hangName, hangName, hangCode, splitModel);
                sqls.add(format);
                System.out.println(format);
            }
        }

        /*List<Order> repairItems = readExcel(new File("/Users/wujiabin/Desktop/dingdan.xlsx"), Order.class);
        try {
            BufferedReader reader = new BufferedReader(new FileReader(new File("/Users/wujiabin/Desktop/替换链.txt")));
            List<String> line = new ArrayList<>();

            String s ;
            while((s=reader.readLine())!= null){
                line.add(s);
            }

            for(Order order:repairItems){
                for(String tihuanlian:line){
                    String[] split = tihuanlian.split(",");
                    for(int i=0;i<split.length;i++){
                        if(order.getPartNo().equals(split[i])){
                            if(i+1< split.length){
                                if(order.getOptionNo().equals(split[i+1])){
                                    System.out.println(order.getOrderNo());
                                }
                            }

                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }

    public static class Order{
        @ExcelProperty("ORDER_NO")
        private String orderNo;

        @ExcelProperty("PART_NO")
        private String partNo;

        @ExcelProperty("OPTION_PART_NO")
        private String optionNo;

        public String getOrderNo() {
            return orderNo;
        }

        public void setOrderNo(String orderNo) {
            this.orderNo = orderNo;
        }

        public String getPartNo() {
            return partNo;
        }

        public void setPartNo(String partNo) {
            this.partNo = partNo;
        }

        public String getOptionNo() {
            return optionNo;
        }

        public void setOptionNo(String optionNo) {
            this.optionNo = optionNo;
        }
    }

    public static class RepairItem{
        @ExcelProperty("行管代码")
        private String hangCode;

        @ExcelProperty("SMCV代码")
        private String smcvCode;

        @ExcelProperty("行管名称")
        private String hangName;

        @ExcelProperty("SMCV名称")
        private String smcvName;

        @ExcelProperty("车型")
        private String model;

        public String getHangCode() {
            return hangCode;
        }

        public void setHangCode(String hangCode) {
            this.hangCode = hangCode;
        }

        public String getSmcvCode() {
            return smcvCode;
        }

        public void setSmcvCode(String smcvCode) {
            this.smcvCode = smcvCode;
        }

        public String getHangName() {
            return hangName;
        }

        public void setHangName(String hangName) {
            this.hangName = hangName;
        }

        public String getSmcvName() {
            return smcvName;
        }

        public void setSmcvName(String smcvName) {
            this.smcvName = smcvName;
        }

        public String getModel() {
            return model;
        }

        public void setModel(String model) {
            this.model = model;
        }
    }



    public static <T> List<T> readExcel(File file,Class<T> clazz){

        List<T> results = new ArrayList<>();

        try {
            FileInputStream inputStream = new FileInputStream(file);
            EasyExcel.read(inputStream, clazz, new ReadListener<T>() {
                @Override
                public void invoke(T t, AnalysisContext analysisContext) {
                    results.add(t);
                }

                @Override
                public void doAfterAllAnalysed(AnalysisContext analysisContext) {

                }
            }).sheet()
              .doRead();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }



        return results;
    }
}

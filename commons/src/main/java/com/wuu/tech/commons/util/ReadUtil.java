package com.wuu.tech.commons.util;

import com.alibaba.fastjson.JSONObject;
import com.sun.tools.corba.se.idl.constExpr.Or;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ReadUtil {
    private ReadUtil(){

    }

    static class Order{
        private double money;
        private String orderNo;
        private int operateType;

        public double getMoney() {
            return money;
        }

        public void setMoney(double money) {
            this.money = money;
        }

        public String getOrderNo() {
            return orderNo;
        }

        public void setOrderNo(String orderNo) {
            this.orderNo = orderNo;
        }

        public int getOperateType() {
            return operateType;
        }

        public void setOperateType(int operateType) {
            this.operateType = operateType;
        }
    }

    /*public static void main(String[] args) {

        try {
            BufferedReader reader = new BufferedReader(new FileReader(new File("/Users/wujiabin/Desktop/a.txt")));
            String s = null;
            List<Order> orders = new ArrayList<>();
            while((s = reader.readLine()) != null){
                Order order = new Order();
                String[] split = s.split("\t");
                order.setOrderNo(split[1]);
                order.setMoney(Double.parseDouble(split[0]));
                order.setOperateType(Integer.valueOf(split[2]));

                orders.add(order);
            }

            Map<String, List<Order>> collect = orders.stream().collect(Collectors.groupingBy(Order::getOrderNo));
            for(Map.Entry<String,List<Order>> entry: collect.entrySet()){
                String orderNo = entry.getKey();
                List<Order> value = entry.getValue();
                BigDecimal a = BigDecimal.ZERO;
                for(Order order:value){
                    int p = order.getOperateType() == 0 ? -1 : 1;
                    a = a.add(new BigDecimal(p).multiply(new BigDecimal(order.getMoney())));
                }

                if(a.compareTo(BigDecimal.ZERO) != 0){
                    System.out.println(orderNo+"::" +a.setScale(2, RoundingMode.HALF_UP).doubleValue());

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        /*try {
//            List<String> strings = readToStr("/Users/wujiabin/Desktop/update.sql");

            List<String> priceTxt = readToStr("/Users/wujiabin/Desktop/a.txt");
            List<ReplaceCondition> conditions = new ArrayList<>();
            for(String priceline:priceTxt){
                String[] split = priceline.split("\t");
                if(split.length <2){
                    continue;
                }
                ReplaceCondition replaceCondition = new ReplaceCondition();
                replaceCondition.setCondition(split[0]);
                replaceCondition.setNeedReplaceStr("a.sale_price =");
                replaceCondition.setStr("a.sale_price ="+split[1]);

                conditions.add(replaceCondition);
            }

            List<String> strings = replaceByCondition(readToStr("/Users/wujiabin/Desktop/update.sql"), conditions);
            strings.forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
*/
//    }

/*
     服务包上传图片代码示例
    public static void main(String[] args) throws Exception{

        File imgFile = new File("/Users/wujiabin/Downloads/图片");
        File[] files = imgFile.listFiles();
        for(File file:files){
            if(file.isDirectory()){
                System.out.println("文件夹："+file.getName());
                File[] subFiles = file.listFiles();
                for(File subFile:subFiles){


                    String url = "http://xyx.saicmaxus.com/wxFileService/base/oss/file/uploadFile?serviceCode=im&isCdn=true&isNeedThumb=false&needShowUploadName=false";
                    RestTemplate restTemplate = new RestTemplate();
                    HttpHeaders httpHeaders = new HttpHeaders();
                    MediaType mediaType = MediaType.MULTIPART_FORM_DATA;
                    httpHeaders.setContentType(mediaType);

                    HttpHeaders pictureHeaders = new HttpHeaders();
                    pictureHeaders.setContentType(MediaType.IMAGE_JPEG);
                    pictureHeaders.setContentDispositionFormData("file",subFile.getName());
                    FileInputStream inputStream = new FileInputStream(new File(subFile.getAbsolutePath()));
                    ByteArrayResource byteArrayResource = new ByteArrayResource(IOUtils.toByteArray(inputStream));

                    HttpEntity<ByteArrayResource> picturePart = new HttpEntity<>(byteArrayResource,pictureHeaders);

                    MultiValueMap<String,Object> multipartRequest = new LinkedMultiValueMap<>();
                    multipartRequest.add("file",picturePart);



                    HttpEntity entity = new HttpEntity(multipartRequest,httpHeaders);


                    ResponseEntity<String> exchange = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

                    String body = exchange.getBody();

                    JSONObject jsonObject = JSONObject.parseObject(body);
                    String fileUrl = jsonObject.getJSONObject("data").getString("fileUrl");

                    System.out.println(file.getName()+"::"+subFile.getName()+"::"+fileUrl);
                }
            }
        }


    }*/

    /**
     * 根据条件替换文本内容
     * @param source
     * @param conditions
     * @return
     */
    public static List<String> replaceByCondition(List<String> source,List<ReplaceCondition> conditions){
        List<String> result = new ArrayList<>();
        for(String text:source){
            for(ReplaceCondition condition:conditions){
                if (text.contains(condition.condition)){
                    text = text.replaceAll(condition.getNeedReplaceStr(),condition.getStr());
                    break;
                }
            }
            result.add(text);
        }

        return result;
    }



    /**
     * 根据路径读取文件
     * @param path
     * @return 每行文本
     * @throws IOException
     */
    public static List<String> readToStr(String path) throws IOException {
        List<String> result = new ArrayList<>();
        if(null == path || "".equals(path.trim())){
            return result;
        }

        BufferedReader reader = null;

        try{
            reader = new BufferedReader(new FileReader(path));

            String text;
            while((text = reader.readLine()) != null){
                result.add(text);
            }


        }finally {
            if(null != reader){
                reader.close();
            }
        }

        return result;
    }

    static class ReplaceCondition{
        /**
         * 满足的条件
         */
        private String condition;

        /**
         * 需要替换的字符串
         */
        private String needReplaceStr;

        /**
         * 替换的值
         */
        private String str;

        public String getCondition() {
            return condition;
        }

        public void setCondition(String condition) {
            this.condition = condition;
        }

        public String getNeedReplaceStr() {
            return needReplaceStr;
        }

        public void setNeedReplaceStr(String needReplaceStr) {
            this.needReplaceStr = needReplaceStr;
        }

        public String getStr() {
            return str;
        }

        public void setStr(String str) {
            this.str = str;
        }
    }

}

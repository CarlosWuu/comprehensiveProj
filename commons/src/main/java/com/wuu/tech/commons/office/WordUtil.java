package com.wuu.tech.commons.office;

import freemarker.cache.FileTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by carloswuu on 2018/10/31.
 */
public class WordUtil {
    public static void createWord(Map dataMap, String templateName, String filePath, String fileName){
        try{
            //创建配置实例
            Configuration configuration = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
            //设置编码
            configuration.setDefaultEncoding("UTF-8");
            //ftl模板文件统一放至 /com/sinosoft/yjcz/web/template/包下面
//            configuration.setClassForTemplateLoading(WordUtil.class,"/");
            TemplateLoader loader = new FileTemplateLoader(new File(Thread.currentThread().getContextClassLoader().getResource("").getPath()));
            configuration.setTemplateLoader(loader);

            //获取模板
            Template template = configuration.getTemplate(templateName);

            //输出文件
            File outFile = new File(filePath+File.separator+fileName);
            //如果输出目标文件夹不存在，则创建
            if (!outFile.getParentFile().exists()){
                outFile.getParentFile().mkdirs();
            }

            //将模板和数据模型合并生成文件
            Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile),"UTF-8"));


            //生成文件
            template.process(dataMap, out);

            //关闭流
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String path = "/Users/carloswuu/work/workspace";
        String fileName = "test.fonts";
        String templateName = "template.ftl";

        Map<String,String> paramMap = new HashMap();
        paramMap.put("userName", "吴佳宾");
        paramMap.put("birthYear","1990");
        paramMap.put("birthMonth","08");
        paramMap.put("birthDay", "05");
        paramMap.put("gender","男");
        paramMap.put("idNo", "310225199008053417");
        paramMap.put("emploiedYear", "2018");
        paramMap.put("emploiedMonth","08");
        paramMap.put("dept","运营及开发组");
        paramMap.put("year", "2018");
        paramMap.put("month", "10");
        paramMap.put("day", "31");


        createWord(paramMap, templateName, path, fileName);

    }
}

package com.wuu.tech.commons.office;

import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by carloswuu on 2018/11/2.
 */
public class PdfUtil {

    public static void main(String[] args) {
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

        String path = Thread.currentThread().getContextClassLoader().getResource("").getPath();

        try {
            generateToFile(path, templateName, "file:"+path, paramMap, "/Users/carloswuu/work/workspace/test.pdf");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    /**
     * 生成PDF到文件
     * @param ftlPath 模板文件路径（不含文件名）
     * @param ftlName 模板文件吗（不含路径）
     * @param imageDiskPath 图片的磁盘路径
     * @param data 数据
     * @param outputFile 目标文件（全路径名称）
     * @throws Exception
     */
    public static void generateToFile(String ftlPath,String ftlName,String imageDiskPath,Object data,String outputFile) throws Exception {
        String html=PdfHelper.getPdfContent(ftlPath, ftlName, data);
        OutputStream out = null;
        ITextRenderer render = null;
        out = new FileOutputStream(outputFile);
        render = PdfHelper.getRender();
        render.setDocumentFromString(html);
            //html中如果有图片，图片的路径则使用这里设置的路径的相对路径，这个是作为根路径
        render.getSharedContext().setBaseURL(imageDiskPath);
        render.layout();
        render.createPDF(out);
        render.finishPDF();
        out.close();
    }


}

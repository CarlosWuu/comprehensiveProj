package com.wuu.tech.commons.office;

import com.lowagie.text.pdf.BaseFont;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Locale;

/**
 * Created by carloswuu on 2018/11/2.
 */
public class PdfHelper {
    public static ITextRenderer getRender() throws Exception {

        ITextRenderer render = new ITextRenderer();

        String path = getPath();
        //添加字体，以支持中文
        render.getFontResolver().addFont(path + "fonts/arialuni.ttf", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
        render.getFontResolver().addFont(path + "fonts/simsun.ttc", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);

        return render;
    }

    //获取要写入PDF的内容
    public static String getPdfContent(String ftlPath, String ftlName, Object o) throws Exception {
        return useTemplate(ftlPath, ftlName, o);
    }

    //使用freemarker得到html内容
    public static String useTemplate(String ftlPath, String ftlName, Object o) throws Exception {

        String html = null;

        Template tpl = getFreemarkerConfig(ftlPath).getTemplate(ftlName);
        tpl.setEncoding("UTF-8");

        StringWriter writer = new StringWriter();
        tpl.process(o, writer);
        writer.flush();
        html = writer.toString();
        return html;
    }

    /**
     * 获取Freemarker配置
     * @param templatePath
     * @return
     * @throws IOException
     */
    private static Configuration getFreemarkerConfig(String templatePath) throws IOException {
        freemarker.template.Version version = new freemarker.template.Version("2.3.22");
        Configuration config = new Configuration(version);
        config.setDirectoryForTemplateLoading(new File(templatePath));
        config.setEncoding(Locale.CHINA, "utf-8");
        return config;
    }

    /**
     * 获取类路径
     * @return
     */
    public static String getPath(){
        return PdfHelper.class.getResource("/").getPath();
    }

}

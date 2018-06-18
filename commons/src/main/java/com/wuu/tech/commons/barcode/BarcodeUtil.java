package com.wuu.tech.commons.barcode;

import org.apache.commons.lang.StringUtils;
import org.krysalis.barcode4j.ClassicBarcodeLogicHandler;
import org.krysalis.barcode4j.impl.AbstractBarcodeBean;
import org.krysalis.barcode4j.impl.DefaultCanvasLogicHandler;
import org.krysalis.barcode4j.impl.code39.Code39Bean;
import org.krysalis.barcode4j.impl.code39.Code39LogicImpl;
import org.krysalis.barcode4j.output.Canvas;
import org.krysalis.barcode4j.output.CanvasProvider;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;
import org.krysalis.barcode4j.tools.UnitConv;

import java.awt.image.BufferedImage;
import java.io.*;

/**
 * Created by carlos wuu on 2018/5/17.
 * 条形码生成器
 */
public class BarcodeUtil {
    /**
     * 生成文件
     *
     * @param msg
     * @param path
     * @return
     */
    public static File generateFile(String msg, String path) {
        File file = new File(path);
        try {
            generate(msg, new FileOutputStream(file));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return file;
    }

    /**
     * 生成字节
     *
     * @param msg
     * @return
     */
    public static byte[] generate(String msg) {
        ByteArrayOutputStream ous = new ByteArrayOutputStream();
        generate(msg, ous);
        return ous.toByteArray();
    }

    /**
     * 生成到流
     *
     * @param msg
     * @param ous
     */
    public static void generate(String msg, OutputStream ous) {
        if (StringUtils.isEmpty(msg) || ous == null) {
            return;
        }

        MyCode39Bean bean = new MyCode39Bean();

        // 精细度
        final int dpi = 100;
        // module宽度
        final double moduleWidth = UnitConv.in2mm(1.0f / dpi);

        // 配置对象
        bean.setModuleWidth(moduleWidth);
        bean.setWideFactor(3);
        bean.doQuietZone(false);


        String format = "image/png";
        try {

            // 输出到流
            BitmapCanvasProvider canvas = new BitmapCanvasProvider(ous, format, dpi,
                    BufferedImage.TYPE_BYTE_BINARY, false, 0);

            // 生成条形码
            bean.generateBarcode(canvas, msg);

            // 结束绘制
            canvas.finish();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static class MyCode39Bean extends Code39Bean{

        /**
         * 生成自定义显示文本
         * @param canvas
         * @param msg
         * @param title
         */
        public void generateBarcode(CanvasProvider canvas, String msg, String title) {
            if(msg != null && msg.length() != 0) {
                ClassicBarcodeLogicHandler handler = new MyDefaultCanvasLogicHandler(this, new Canvas(canvas),title);
                Code39LogicImpl impl = new Code39LogicImpl(this.getChecksumMode(), this.isDisplayStartStop(), this.isDisplayChecksum(), this.isExtendedCharSetEnabled());
                impl.generateBarcodeLogic(handler, msg);
            } else {
                throw new NullPointerException("Parameter msg must not be empty");
            }
        }
    }

    static class MyDefaultCanvasLogicHandler extends DefaultCanvasLogicHandler{
        private String displayMessage;

        public MyDefaultCanvasLogicHandler(AbstractBarcodeBean bcBean, Canvas canvas, String title) {
            super(bcBean, canvas);
            this.displayMessage = title;
        }

        public String getDisplayMessage() {
            return displayMessage;
        }

        public void setDisplayMessage(String displayMessage) {
            this.displayMessage = displayMessage;
        }

        public void startBarcode(String msg, String formattedMsg) {
            super.startBarcode(msg,getDisplayMessage());
        }
    }


    public static void main(String[] args) {
        String msg = "3773306";
        String path = "/Users/carloswuu/work/barcode.png";
        generateFile(msg, path);
    }
}

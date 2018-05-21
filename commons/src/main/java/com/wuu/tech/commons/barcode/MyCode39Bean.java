package com.wuu.tech.commons.barcode;

import org.krysalis.barcode4j.ClassicBarcodeLogicHandler;
import org.krysalis.barcode4j.impl.code39.Code39Bean;
import org.krysalis.barcode4j.impl.code39.Code39LogicImpl;
import org.krysalis.barcode4j.output.Canvas;
import org.krysalis.barcode4j.output.CanvasProvider;

/**
 * Created by carlos wuu on 2018/5/18.
 */
public class MyCode39Bean extends Code39Bean{

    /**
     * 生成自定义显示文本
     * @param canvas
     * @param msg
     * @param title
     */
    public void generateBarcode(CanvasProvider canvas, String msg,String title) {
        if(msg != null && msg.length() != 0) {
            ClassicBarcodeLogicHandler handler = new MyDefaultCanvasLogicHandler(this, new Canvas(canvas),title);
            Code39LogicImpl impl = new Code39LogicImpl(this.getChecksumMode(), this.isDisplayStartStop(), this.isDisplayChecksum(), this.isExtendedCharSetEnabled());
            impl.generateBarcodeLogic(handler, msg);
        } else {
            throw new NullPointerException("Parameter msg must not be empty");
        }
    }
}

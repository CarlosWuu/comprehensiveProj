package com.wuu.tech.commons.barcode;

import org.krysalis.barcode4j.impl.AbstractBarcodeBean;
import org.krysalis.barcode4j.impl.DefaultCanvasLogicHandler;
import org.krysalis.barcode4j.output.Canvas;

/**
 * Created by carlos wuu on 2018/5/18.
 */
public class MyDefaultCanvasLogicHandler extends DefaultCanvasLogicHandler {
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

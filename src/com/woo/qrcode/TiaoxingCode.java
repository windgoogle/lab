package com.woo.qrcode;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;


/**
 * 生成条形码，解析条形码
 */

public class TiaoxingCode {
    private static final String tiaoxing_CODE_IMAGE_PATH = "c:/Users/hzy20/Desktop/tiaoxingCode.png";
    public static void main(String[] args) throws Exception {

        genrateTiaoxing();
        parseTiaoxing();
    }

    public static void genrateTiaoxing() throws Exception {
        String content = "222123456789";
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        hints.put(EncodeHintType.MARGIN, 0);
        int onColor = 0xFF000000;     //前景色
        int offColor = 0xBFB0A8;    //背景色
        MatrixToImageConfig config = new MatrixToImageConfig(onColor, offColor);
        BitMatrix bitMatrix = new MultiFormatWriter()
                .encode(content, BarcodeFormat.CODE_128, 300, 100, hints);
        //ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Path path = FileSystems.getDefault().getPath(tiaoxing_CODE_IMAGE_PATH);

        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path,config);
        // MatrixToImageWriter.writeToStream(bitMatrix, "png", baos, config);
        //System.out.println(Base64.getEncoder().encodeToString(baos.toByteArray()));
    }

    public static void parseTiaoxing() throws Exception {
        InputStream is = new FileInputStream(tiaoxing_CODE_IMAGE_PATH);
        BufferedImage image = ImageIO.read(is);
        LuminanceSource source = new BufferedImageLuminanceSource(image);
        BinaryBitmap bb = new BinaryBitmap(new HybridBinarizer(source));
        HashMap<DecodeHintType, String> hints = new HashMap<>();
        hints.put(DecodeHintType.CHARACTER_SET, "utf-8");
        Result result = new MultiFormatReader().decode(bb, hints);
        System.out.println("二维码格式类型：" + result.getBarcodeFormat());
        System.out.println("二维码文本内容：" + result.getText());
    }
}

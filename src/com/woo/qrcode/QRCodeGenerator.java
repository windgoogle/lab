package com.woo.qrcode;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import javax.imageio.ImageIO;

/**
 * 生成二维码程序
 */
public class QRCodeGenerator {

    private static final String QR_CODE_IMAGE_PATH = "c:/Users/hzy20/Desktop/MyQRCode.png";

    private static void generateQRCodeImage(String text, int width, int height, String filePath) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();

        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

        Path path = FileSystems.getDefault().getPath(filePath);

        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);

    }

    public static void genrateLogo() throws Exception {
        String content = "中国";
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        hints.put(EncodeHintType.MARGIN, 0);
        int onColor = 0xFF000000;     //前景色
        int offColor = 0xBFB0A8;    //背景色
        MatrixToImageConfig config = new MatrixToImageConfig(onColor, offColor);
        BitMatrix bitMatrix = new MultiFormatWriter()
                .encode(content, BarcodeFormat.QR_CODE, 300, 300, hints);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        FileOutputStream fout=new FileOutputStream(QR_CODE_IMAGE_PATH);
        BufferedImage image = MatrixToImageWriter.toBufferedImage(bitMatrix, config);
        addLogo(image);
        ImageIO.write(image, "png", fout);
        //MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
      //  System.out.println(Base64.getEncoder().encodeToString(baos.toByteArray()));
    }

    private static void addLogo(BufferedImage image) throws IOException {
        Graphics2D g = image.createGraphics();
        // 读取logo图片
        InputStream logoInput = new FileInputStream("C:\\Users\\hzy20\\Pictures\\tradeOverview.png");
        if (Objects.isNull(logoInput)) {
            return;
        }
        BufferedImage logo = ImageIO.read(logoInput);
        // 设置logo的大小,本人设置为二维码图片的20%,由于过大会盖掉二维码
        int widthLogo = logo.getWidth(null) > image.getWidth() * 2 / 10 ? (image.getWidth() * 2 / 10)
                : logo.getWidth(null);
        int heightLogo = logo
                .getHeight(null) > image.getHeight() * 2 / 10 ?
                (image.getHeight() * 2 / 10) : logo.getWidth(null);

        // 计算图片放置位置
        // logo放在中心
        int x = (image.getWidth() - widthLogo) / 2;
        int y = (image.getHeight() - heightLogo) / 2;
        // 开始绘制图片
        g.drawImage(logo, x, y, widthLogo, heightLogo, null);
        g.drawRoundRect(x, y, widthLogo, heightLogo, 15, 15);
        g.drawRect(x, y, widthLogo, heightLogo);

        g.dispose();
        logo.flush();
    }



    public static void main(String[] args) throws Exception{
        try {
          //  generateQRCodeImage("This is my first QR Code,安康", 350, 350, QR_CODE_IMAGE_PATH);  //中文乱码
            genrateLogo();
        } catch (WriterException e) {
            System.out.println("Could not generate QR Code, WriterException :: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Could not generate QR Code, IOException :: " + e.getMessage());
        }

    }


}
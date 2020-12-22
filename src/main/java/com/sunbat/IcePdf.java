package com.sunbat;

import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.icepdf.core.pobjects.Document;
import org.icepdf.core.util.GraphicsRenderingHints;

/**
 * PDF 转 图片
 */
public class IcePdf {
    public static void pdf2Pic(String pdfPath, String path) throws Exception {
        Document document = new Document();
        document.setFile(pdfPath);
        //缩放比例
        float scale = 2.5f;
        //旋转角度
        float rotation = 0f;

        for (int i = 0; i < document.getNumberOfPages(); i++) {
            BufferedImage image = (BufferedImage)
                    document.getPageImage(i, GraphicsRenderingHints.SCREEN, org.icepdf.core.pobjects.Page.BOUNDARY_CROPBOX, rotation, scale);
            RenderedImage rendImage = image;
            try {
                String imgName = i + ".jpg";
                System.out.println(imgName);
                File file = new File(path + imgName);
                ImageIO.write(rendImage, "png", file);
            } catch (IOException e) {
                e.printStackTrace();
            }
            image.flush();
        }
        document.dispose();
    }
    public static void main(String[] args) throws Exception {
        String filePath = "C:\\Users\\cpr066\\Desktop\\保单调研附件\\人保\\交强\\蔡芬芬交强.pdf";
        pdf2Pic(filePath, "D:\\temp\\");
    }
}
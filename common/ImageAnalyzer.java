package common;

import java.awt.image.BufferedImage;
import java.awt.image.Raster;

public class ImageAnalyzer {
    private int [] [] redPart, greenPart, bluePart;
    private Raster raster;
    private int width;
    private int height;
    private static final ImageAnalyzer INSTANCE = new ImageAnalyzer();

    private ImageAnalyzer() {}



    public void analyze (BufferedImage image){
        raster = image.getRaster();
        width = raster.getWidth();
        height = raster.getHeight();

        redPart = new int[height] [width];
        greenPart = new int[height] [width];
        bluePart = new int[height] [width];

        int [] pixel = new int[4];

        for (int i= 0; i < height; i++) {
            for (int j= 0; j < width; j++) {
                raster.getPixel(j, i, pixel);
                redPart[i][j] = pixel[0];
                greenPart[i][j] = pixel[1];
                bluePart[i][j] = pixel[2];
            }
        }
    }

    public static ImageAnalyzer getInstance(){
        return INSTANCE;
    }

    public void setRedPart(int[][] redPart) {
        this.redPart = redPart;
    }

    public void setGreenPart(int[][] greenPart) {
        this.greenPart = greenPart;
    }

    public void setBluePart(int[][] bluePart) {
        this.bluePart = bluePart;
    }

    public void setRaster(Raster raster) {
        this.raster = raster;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int[][] getRedPart() {
        return redPart;
    }

    public int[][] getGreenPart() {
        return greenPart;
    }

    public int[][] getBluePart() {
        return bluePart;
    }
    public Raster getRaster() {
        return raster;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

}

package common;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

public class ImagesUtil {
    int amountOfThreads = 13;

    public final static String RESOURCES_ROOT = "C:\\Users\\MARATILIA\\IdeaProjects\\parallel\\resources\\";

    private static void shift(int X, int Y, int [][] colorComponent ) {
        int width = colorComponent[0].length;
        int height = colorComponent.length;

        for( int i = height -1 - Y; i > -1; i--){
            for( int j = width -1 - X ; j > -1; j--){
                colorComponent[i + Y][j + X] = colorComponent[i][j];
            }
        }
    }
    public static void shiftImage (int xShift, int yShift, int [] [] R, int [] [] G, int [] [] B,
                                   int [] backgroundColor, boolean colorBackground)  {
        int width = R[0].length;
        int height = R.length;

        if (xShift > R[0].length || yShift > R.length) {
            colorArea(0, width, 0, height, R,G,B, backgroundColor);
            return;
        }


        ImagesUtil.shift(xShift,yShift, R);
        ImagesUtil.shift(xShift,yShift, G);
        ImagesUtil.shift(xShift,yShift, B);
        if(colorBackground) {
            colorArea(0,width - 1,0, yShift , R,G,B,  backgroundColor);
            colorArea(0, xShift+1,yShift, height, R,G,B,  backgroundColor);
        }
    }

    public static int[][] changeChannelByConvolutionBlur(int [][] channel, int [][] convolutionMatrix) throws IndexOutOfBoundsException {
        int convolutionMatrixSize = convolutionMatrix.length;
        int width = channel[0].length;
        int height = channel.length;
        int resultMatrixWidth = width - convolutionMatrixSize + 1;
        int resultMatrixHeight = height - convolutionMatrixSize + 1;
        int [][] resultMatrix = new int [resultMatrixHeight] [resultMatrixWidth];
        int minSum = 1000, maxSum = -1000;

        //int n=0;
        for( int i =0; i < resultMatrixHeight; i ++) {
            for( int j = 0; j < resultMatrixWidth; j++) {
                    int sum = 0;

                    for(int k = 0; k < convolutionMatrixSize; k++){
                        for(int m = 0; m < convolutionMatrixSize; m++){
                            sum += channel[i+k][j+m] * convolutionMatrix[k][m];
                        }
                    }
                resultMatrix[i][j] = sum / 9; //Math.abs(sum); //Math.abs(sum);
            }
        }
        return resultMatrix;
    }

    public static int[][] changeChannelByConvolutionContrast(int [][] channel, int [][] convolutionMatrix) throws IndexOutOfBoundsException {
        int convolutionMatrixSize = convolutionMatrix.length;
        int width = channel[0].length;
        int height = channel.length;
        int resultMatrixWidth = width - convolutionMatrixSize + 1;
        int resultMatrixHeight = height - convolutionMatrixSize + 1;
        int [][] resultMatrix = new int [resultMatrixHeight] [resultMatrixWidth];
        int minSum = 1000, maxSum = -1000;

        //int n=0;
        for( int i =0; i < resultMatrixHeight; i ++) {
            for( int j = 0; j < resultMatrixWidth; j++) {
                int sum = 0;

                for(int k = 0; k < convolutionMatrixSize; k++){
                    for(int m = 0; m < convolutionMatrixSize; m++){
                        sum += channel[i+k][j+m] * convolutionMatrix[k][m];
                    }
                }
                resultMatrix[i][j] = Math.abs(sum); //Math.abs(sum); //Math.abs(sum);
            }
        }
        return resultMatrix;
    }

    private static void colorArea(int xStartPoint, int xEndPoint, int yStartPoint, int yEndPoint,
                                  int [] [] R, int [] [] G, int [] [] B, int [] backgroundColor) {
        for( int i = yStartPoint; i < yEndPoint ; i++){
            for( int j = xStartPoint ; j < xEndPoint; j++){
                R[i][j] = backgroundColor[0];
                G[i][j] = backgroundColor[1];
                B[i][j] = backgroundColor[2];
            }
        }
    }

    private static void invertChannel(int [][] channel) {
        for( int i =0; i < channel.length; i ++){
            for( int j = 0; j < channel[0].length; j++){
                channel[i][j] = 255 - channel[i][j] ;
            }
        }

    }

    public static void invertImage(int [] [] R, int [] [] G, int [] [] B) {
        invertChannel(R);
        invertChannel(G);
        invertChannel(B);
    }

    public static BufferedImage createBufferedImage(int [][] R, int[][] G, int [][] B)  {
        int width = R[0].length;
        int height = R.length;
        int [] pixel = new int[4];

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        WritableRaster raster = image.getRaster();

        for (int i= 0; i <height; i++) {
            for (int j= 0; j <width; j++) {
                pixel[0] = R[i][j];
                pixel[1] = G[i][j];
                pixel[2] = B[i][j];
                raster.setPixel(j,i,pixel);
            }
        }
        image.setData(raster);
        return image;

    }

    }



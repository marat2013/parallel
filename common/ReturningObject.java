package common;

public class ReturningObject {

    public int [] [] [] redChannelPieces;
    public int [] [] [] greenChannelPieces;
    public int [] [] [] blueChannelPieces;
    public int width;
    public int height;
    public boolean needToAdjust = true;
    int pixelsColored;

    public boolean isNeedToAdjust() {
        return needToAdjust;
    }

    public void setNeedToAdjust(boolean needToAdjust) {
        this.needToAdjust = needToAdjust;
    }

    public int[][][] getRedChannelPieces() {
        return redChannelPieces;
    }

    public int[][][] getGreenChannelPieces() {
        return greenChannelPieces;
    }

    public int[][][] getBlueChannelPieces() {
        return blueChannelPieces;
    }

    public void setRedChannelPieces(int[][][] redChannelPieces) {
        this.redChannelPieces = redChannelPieces;
    }

    public void setGreenChannelPieces(int[][][] greenChannelPieces) {
        this.greenChannelPieces = greenChannelPieces;
    }

    public void setBlueChannelPieces(int[][][] blueChannelPieces) {
        this.blueChannelPieces = blueChannelPieces;
    }

    public void adjustChannelsPieces(int amountOfThreads, int height, int width) {
        if(needToAdjust) {
            redChannelPieces = new int[amountOfThreads][height][width];
            greenChannelPieces = new int[amountOfThreads][height][width];
            blueChannelPieces = new int[amountOfThreads][height][width];
            needToAdjust = false;
        }

    }
}

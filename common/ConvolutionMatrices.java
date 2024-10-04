package common;

public final class ConvolutionMatrices {

    public static final int [][] BLUR = {
        {0, 0, 0, 0, 0},
        {0, 1, 1, 1, 0},
        {0, 1, 1, 1, 0},
        {0, 1, 1, 1, 0},
        {0, 0, 0, 0, 0}};

//    public static final int [][] CONTRAST = {
//        {0, 0, 0, 0, 0},
//        {0, 0,-1, 0, 0},
//        {0,-1, 5,-1, 0},
//        {0, 0,-1, 0, 0},
//        {0, 0, 0, 0, 0}};

        public static final int [][] CONTRAST = {
        {0, 0, 0, 0, 0},
        {0, 0,-1, 0, 0},
        {0,-1, 5,-1, 0},
        {0, 0,-1, 0, 0},
        {0, 0, 0, 0, 0},
};
}

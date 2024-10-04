//package TaskB;
//
//import common.ConvolutionMatrices;
//import common.ImagesUtil;
//import common.ReturningObject;
//
//import java.util.Arrays;
//import java.util.concurrent.BrokenBarrierException;
//import java.util.concurrent.CyclicBarrier;
//
//public class res package TaskB;
//
//import common.ConvolutionMatrices;
//import common.ImagesUtil;
//import common.ReturningObject;
//
//import java.util.Arrays;
//import java.util.concurrent.BrokenBarrierException;
//import java.util.concurrent.CyclicBarrier;
//
//public class MyThreadB extends Thread{
//    private static final int [] backgroundColor = { 187, 38, 73 };
//
//    private static final int [][] convolutionMatrixBlur = ConvolutionMatrices.BLUR;
//    private static final int [][] convolutionMatrixContrast = ConvolutionMatrices.CONTRAST;
//
//    private int [][] R;
//    private int [][] G;
//    private int [][] B;
//    private CyclicBarrier cyclicBarrier;
//    private ReturningObject returningObject;
//    int number;
//
//    public MyThreadB(MyThreadBuilder myThreadBuilder) {
//        R = myThreadBuilder.R;
//        G = myThreadBuilder.G;
//        B = myThreadBuilder.B;
//        cyclicBarrier = myThreadBuilder.cyclicBarrier;
//        returningObject = myThreadBuilder.returningObject;
//        number = myThreadBuilder.number;
//
//    }
//
//    public static class MyThreadBuilder {
//        private int [][] R;
//        private int [][] G;
//        private int [][] B;
//        private CyclicBarrier cyclicBarrier;
//        private ReturningObject returningObject;
//        private int number;
//
//
//        public MyThreadBuilder setNumber(int number) {
//            this.number = number;
//            return this;
//        }
//
//        public MyThreadBuilder setReturningObject(ReturningObject returningObject) {
//            this.returningObject = returningObject;
//            return this;
//        }
//        public MyThreadBuilder setR(int[][] r) {
//            R = r;
//            return this;
//        }
//
//        public MyThreadBuilder setG(int[][] g) {
//            G = g;
//            return this;
//        }
//
//        public MyThreadBuilder setB(int[][] b) {
//            B = b;
//            return this;
//        }
//
//        public MyThreadBuilder setCyclicBarrier(CyclicBarrier cyclicBarrier) {
//            this.cyclicBarrier = cyclicBarrier;
//            return this;
//        }
//        public MyThreadB build() {
//            return new MyThreadB(this);
//        }
//    }
//
//
//
//    @Override
//    public void run() {
//        int xShift = 300;
//        int yShift = 1000;
//        int amountOfThreads = 13;
//
//        System.out.println(this.getName() + " starts its work");
//        ImagesUtil.shiftImage(xShift, 0, R, G , B, backgroundColor, true);
//
//        //dangerous code
//        if(Integer.parseInt(this.getName()) == number) {
//            ImagesUtil.shiftImage(0,yShift - (amountOfThreads - number) * R.length , R, G , B, backgroundColor, true);
//        } //yShift - number * R.length это сколько осталось закрасить
//        //dangerous code
//
//        if(Integer.parseInt(this.getName()) > number) { //5 is random at now
//            ImagesUtil.shiftImage(R[0].length + 100, R.length + 100, R,G,B, backgroundColor, true);
//        }
//
//        ImagesUtil.invertImage(R, G, B);
////            R = ImagesUtil.changeChannelByConvolution(R, convolutionMatrixContrast);
////        G = ImagesUtil.changeChannelByConvolution(G, convolutionMatrixContrast);
////        B = ImagesUtil.changeChannelByConvolution(B, convolutionMatrixContrast);
//
//        //ImagesUtil.changeAllChannelsByConvolution(R,G,B , convolutionMatrixContrast);
////        ImagesUtil.changeChannelByConvolution(R, convolutionMatrixBlur);
////        ImagesUtil.changeChannelByConvolution(G, convolutionMatrixBlur);
////         ImagesUtil.changeChannelByConvolution(B, convolutionMatrixBlur);
//        returningObject.height = R.length;
//        returningObject.width = R[0].length;
//
//        returningObject.adjustChannelsPieces(13, R.length, R[0].length);
////            System.out.println("My name is " + this.getName() + " i put " +
////                            "returningObject.redChannelPieces[%d] = Arrays.copyOfRange(R,0,R.length);".formatted(Integer.parseInt(this.getName())) );
//
//        returningObject.redChannelPieces[Integer.parseInt(this.getName())] = Arrays.copyOfRange(R,0,R.length);
//        returningObject.greenChannelPieces[Integer.parseInt(this.getName())] = Arrays.copyOfRange(G,0,G.length);
//        returningObject.blueChannelPieces[Integer.parseInt(this.getName())] = Arrays.copyOfRange(B,0,B.length);
//
//
//        System.out.println(this.getName() + " finished");
//        try {
//            cyclicBarrier.await();
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        } catch (BrokenBarrierException e) {
//            throw new RuntimeException(e);
//        }
//
//    }
//}
//{
//}

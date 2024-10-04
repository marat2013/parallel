package TaskB;

import common.ConvolutionMatrices;
import common.ImagesUtil;
import common.ReturningObject;

import java.util.Arrays;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;


public class MyThreadB extends Thread{


    private static final int [][] convolutionMatrixBlur = ConvolutionMatrices.BLUR;
    private static final int [][] convolutionMatrixContrast = ConvolutionMatrices.CONTRAST;

    private int [][] R;
    private int [][] G;
    private int [][] B;
    private CyclicBarrier cyclicBarrier;
    private ReturningObject returningObject;
    int number;
    int amountOfThreads;

    public MyThreadB(MyThreadBuilder myThreadBuilder) {
        R = myThreadBuilder.R;
        G = myThreadBuilder.G;
        B = myThreadBuilder.B;
        cyclicBarrier = myThreadBuilder.cyclicBarrier;
        returningObject = myThreadBuilder.returningObject;
        number = myThreadBuilder.number;
        amountOfThreads = myThreadBuilder.amountOfThreads;

    }

    public static class MyThreadBuilder {
        private int [][] R;
        private int [][] G;
        private int [][] B;
        private CyclicBarrier cyclicBarrier;
        private ReturningObject returningObject;
        private int number;
        private int amountOfThreads;


        public MyThreadBuilder setNumber(int number) {
            this.number = number;
            return this;
        }
        public MyThreadBuilder setAmountOfThreads(int number) {
            this.amountOfThreads = amountOfThreads;
            return this;
        }

        public MyThreadBuilder setReturningObject(ReturningObject returningObject) {
            this.returningObject = returningObject;
            return this;
        }
        public MyThreadBuilder setR(int[][] r) {
            R = r;
            return this;
        }

        public MyThreadBuilder setG(int[][] g) {
            G = g;
            return this;
        }

        public MyThreadBuilder setB(int[][] b) {
            B = b;
            return this;
        }

        public MyThreadBuilder setCyclicBarrier(CyclicBarrier cyclicBarrier) {
            this.cyclicBarrier = cyclicBarrier;
            return this;
        }
        public MyThreadB build() {
            return new MyThreadB(this);
        }
    }



    @Override
    public void run() {

            System.out.println(this.getName() + " starts its work");
            ImagesUtil.invertImage(R,G,B);
            R = ImagesUtil.changeChannelByConvolutionContrast(R, convolutionMatrixBlur);
            G = ImagesUtil.changeChannelByConvolutionContrast(G, convolutionMatrixContrast);
            B = ImagesUtil.changeChannelByConvolutionContrast(B, convolutionMatrixContrast);


            returningObject.height = R.length;
            returningObject.width = R[0].length;

            returningObject.adjustChannelsPieces(amountOfThreads, R.length, R[0].length);


            returningObject.redChannelPieces[Integer.parseInt(this.getName())] = Arrays.copyOfRange(R,0,R.length);
            returningObject.greenChannelPieces[Integer.parseInt(this.getName())] = Arrays.copyOfRange(G,0,G.length);
            returningObject.blueChannelPieces[Integer.parseInt(this.getName())] = Arrays.copyOfRange(B,0,B.length);


            System.out.println(this.getName() + " finished");
        try {
            cyclicBarrier.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (BrokenBarrierException e) {
            throw new RuntimeException(e);
        }

    }
}

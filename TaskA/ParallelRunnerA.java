package TaskA;

import common.ConvolutionMatrices;
import common.ImageAnalyzer;
import common.ImagesUtil;
import common.ReturningObject;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


public class ParallelRunnerA {

        public static void main(String[] args) throws Exception{

            int [][] convolutionMatrixBlur = ConvolutionMatrices.BLUR;
            int [][] convolutionMatrixContrast = ConvolutionMatrices.CONTRAST;
            int [] listOfAmountOfTheads = {2, 4, 6, 8, 10, 12, 14, 16};
            int numberOfTimeChecks = 5;
            File imagesFolder = new File(ImagesUtil.RESOURCES_ROOT + "images\\" +  "original images");
            File resultsFolder = new File(ImagesUtil.RESOURCES_ROOT + "results\\");

            File[] imagesList = imagesFolder.listFiles();
            //File [] resultsToDelete = resultsFolder.listFiles();

            int[][] R,G,B;
            int [] backgroundColor = { 187, 38, 73 };
            System.out.println(Thread.currentThread().getName() + "main has started");

            //deleting all previous results
            for (File folder: resultsFolder.listFiles()) {
                for( File file : folder.listFiles()){
                    file.delete();
                }

            }
            for(int amountOfThreads : listOfAmountOfTheads) {
                //int amountOfThreads = 16;
                ArrayList<Long> processingTimeList = new ArrayList<>();
                long averageTimeOfProcessing;


                System.out.println("-----------------------------------");
                System.out.println(amountOfThreads + " threads");
                ExecutorService threadPool = Executors.newFixedThreadPool(amountOfThreads);
                try {

                    for (int imageNumber = 0; imageNumber < imagesList.length; imageNumber++ ) {
                        File file = imagesList[imageNumber];
//                        File file = imagesList[0];

                        long startTime = System.currentTimeMillis();
                        for(int i = 0; i < numberOfTimeChecks; i ++) {
                            CyclicBarrier cyclicBarrier = new CyclicBarrier(amountOfThreads + 1);

                            BufferedImage image = ImageIO.read(file);

                            ImageAnalyzer imageAnalyzer = ImageAnalyzer.getInstance();
                            imageAnalyzer.analyze(image);

                            R = imageAnalyzer.getRedPart();
                            G = imageAnalyzer.getGreenPart();
                            B = imageAnalyzer.getBluePart();

                            int yShift = 300;
                            int xShift = 300;
                            ImagesUtil.shiftImage(xShift, yShift, R, G, B, backgroundColor, true);

                            int amountOfRows = R.length;
                            int amountOfColumns = R[0].length;

                            ReturningObject returningObject = new ReturningObject();
                            returningObject.adjustChannelsPieces(amountOfThreads, amountOfRows, amountOfColumns);



                            float rowsPerThread = (float) amountOfRows / (float) amountOfThreads;
                            Thread [] threads = new Thread [amountOfThreads];
                            for (int j = 0; j < amountOfThreads; j++) {

                                MyThreadA thread = new MyThreadA.MyThreadBuilder().
                                        setR(Arrays.copyOfRange(R, (int) (j * rowsPerThread), (int) ((j + 1) * rowsPerThread))).
                                        setG(Arrays.copyOfRange(G, (int) (j * rowsPerThread), (int) ((j + 1) * rowsPerThread))).
                                        setB(Arrays.copyOfRange(B, (int) (j * rowsPerThread), (int) ((j + 1) * rowsPerThread))).
                                        setCyclicBarrier(cyclicBarrier).
                                        setReturningObject(returningObject).
                                        build();
                                thread.setName(String.valueOf(j));


                                threadPool.submit(thread);
                            }


                            cyclicBarrier.await();
                            R = new int[(returningObject.height - 1) * amountOfThreads][returningObject.width];
                            G = new int[(returningObject.height - 1) * amountOfThreads][returningObject.width];
                            B = new int[(returningObject.height - 1) * amountOfThreads][returningObject.width];

                            for (int k = 0; k < amountOfThreads; k++) {

                                for (int m = 0; m < returningObject.height - 1; m++) {
                                    for (int n = 0; n < returningObject.width; n++) {

                                        R[k * (returningObject.height - 1) + m][n] = returningObject.getRedChannelPieces()[k][m][n]; // k - номер потока обработавшего кусочек изображения m n -обычные индексы для матрицы интенсивности
                                        G[k * (returningObject.height - 1) + m][n] = returningObject.getGreenChannelPieces()[k][m][n];
                                        B[k * (returningObject.height - 1) + m][n] = returningObject.getBlueChannelPieces()[k][m][n];
                                    }
                                }
                            }


                            BufferedImage newImage = ImagesUtil.createBufferedImage(R, G, B);
                            ImageIO.write(newImage, "png", new File(ImagesUtil.RESOURCES_ROOT +"images\\" + "merged\\" + file.getName()));

                            long executionTime = System.currentTimeMillis() - startTime;
                            processingTimeList.add(executionTime);
                        }

                        Long sum = 0l;
                        for(Long el : processingTimeList) {
                            sum+=el;
                        }
                        averageTimeOfProcessing = sum / numberOfTimeChecks;

                        String resultFilePath  = ImagesUtil.RESOURCES_ROOT + "results//" + imageNumber + "//" + amountOfThreads + ".txt";

                        File resultFile = new File (resultFilePath) ;


                        resultFile.createNewFile();
                        Files.write(Paths.get(resultFilePath), Long.toString(averageTimeOfProcessing).getBytes() );

                    }

                }
                catch (Exception exception) {
                System.out.println(exception.getMessage());
                }
                finally {
                    threadPool.shutdown();
                    threadPool.awaitTermination(10, TimeUnit.MINUTES);
                }


            }


            System.out.println(Thread.currentThread().getName() + "main has closed");
        }
}
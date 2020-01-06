/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package face_rec;

import java.util.Arrays;
import java.util.Comparator;
import Jama.EigenvalueDecomposition;
import Jama.Matrix;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComponent;
import javax.swing.ProgressMonitor;
import javax.swing.Timer;
import org.bytedeco.javacpp.opencv_core;
import org.opencv.face.Face;

public class Training {
    
    Matrix averageFace;		//stores the average face useful when probing the database
    Matrix eigVectors;			//stores all the sorted eigen vectors from the training set
    Matrix eigValues;			//Stores all the sorted eigen Values from the training set
    boolean trained = false;	//has a training set been provided yet?
    int numEigenVecs = 0;	//number of eigen vectors availiable
    public Picture picture;
    
    public void processTrainingSet(Face[] faces, ProgressTracker progress) {
 
        
        /**
         * STEP 1 Read in the images, flatten them out into one row of values,
         * and stack in a big matrix
         */
        progress.advanceProgress("Constructing matrix...");
        
        double[][] dpix = new double[faces.length][];

       
        //make matrix of stacked flattened images
        Matrix matrix = new Matrix(dpix);

       progress.advanceProgress("Calculating averages...");
        //compute the average image
        averageFace = new Matrix(1, matrix.getColumnDimension());
        for (int i = 0; i < matrix.getRowDimension(); i++) {
            averageFace.plusEquals(matrix.getMatrix(i, i, 0, matrix.getColumnDimension() - 1));
        }
        averageFace.timesEquals(1.0 / (double) matrix.getRowDimension());	//divide by the number of pixels to get the average
        Matrix bigAvg = new Matrix(matrix.getRowDimension(), matrix.getColumnDimension());
        for (int i = 0; i < bigAvg.getRowDimension(); i++) {
            bigAvg.setMatrix(i, i, 0, bigAvg.getColumnDimension() - 1, averageFace);
        }
        // Compute the diference from the average face for each image
        Matrix A = matrix.minus(bigAvg).transpose();



        progress.advanceProgress("Computing covariance matrix...");

        Matrix At = A.transpose();
        Matrix L = At.times(A);

        progress.advanceProgress("Calculating eigenvectors...");
        EigenvalueDecomposition eigen = L.eig();
        eigValues = eigen.getD();
        eigVectors = eigen.getV();

        progress.advanceProgress("Sorting eigenvectors...");
        Matrix[] eigDVSorted = sortem(eigValues, eigVectors);
        eigValues = eigDVSorted[0];
        eigVectors = eigDVSorted[1];
        eigVectors = A.times(eigVectors);



        progress.advanceProgress("Extracting eigenvalues...");
        double[] values = diag(eigValues);
        for (int i = 0; i < values.length; i++) {
            values[i] /= A.getColumnDimension() - 1;
        }



        progress.advanceProgress("Normalising eigenvectors...");
        numEigenVecs = 0;
        for (int i = 0; i < eigVectors.getColumnDimension(); i++) {
            Matrix tmp;
            if (values[i] < 0.0001) {
                tmp = new Matrix(eigVectors.getRowDimension(), 1);
            } else {
                tmp = eigVectors.getMatrix(0, eigVectors.getRowDimension() - 1, i, i).times(
                        1 / eigVectors.getMatrix(0, eigVectors.getRowDimension() - 1, i, i).normF());
                numEigenVecs++;
            }
            eigVectors.setMatrix(0, eigVectors.getRowDimension() - 1, i, i, tmp);
            //eigVectors.timesEquals(1 / eigVectors.getMatrix(0, eigVectors.getRowDimension() - 1, i, i).normInf());
        }
        eigVectors = eigVectors.getMatrix(0, eigVectors.getRowDimension() - 1, 0, numEigenVecs - 1);

        trained = true;

        progress.finished();

    }

    public double[] getEigenFaces(Picture pic, int number) {
        if (number > numEigenVecs) //adjust the number to the maxium number of eigen vectors availiable
        {
            number = numEigenVecs;
        }
        double[] ret = new double[number];

        double[] pixels = pic.getImagePixels();
        Matrix face = new Matrix(pixels, pixels.length);
        Matrix Vecs = eigVectors.getMatrix(0, eigVectors.getRowDimension() - 1, 0, number - 1).transpose();

        Matrix rslt = Vecs.times(face);
        for (int i = 0; i < number; i++) {
            ret[i] = rslt.get(i, 0);
        }
        return ret;    }

    private double[] diag(Matrix M) {
        double[] dvec = new double[M.getColumnDimension()];
        for (int i = 0; i < M.getColumnDimension(); i++) {
            dvec[i] = M.get(i, i);
        }
        return dvec;
    }

    private Matrix[] sortem(Matrix D, Matrix V) {
        //dvec = diag(D); // get diagonal components
        double[] dvec = diag(D);

        class di_pair {

            double value;
            int index;
        };
        di_pair[] dvec_indexed = new di_pair[dvec.length];
        for (int i = 0; i < dvec_indexed.length; i++) {
            dvec_indexed[i] = new di_pair();
            dvec_indexed[i].index = i;
            dvec_indexed[i].value = dvec[i];
        }

        Comparator di_pair_sort = new Comparator() {
            public int compare(Object arg0, Object arg1) {
                di_pair lt = (di_pair) arg0;
                di_pair rt = (di_pair) arg1;
                double dif = (lt.value - rt.value);
                if (dif > 0) {
                    return -1;
                }
                if (dif < 0) {
                    return 1;
                } else {
                    return 0;
                }
            }
        };
        Arrays.sort(dvec_indexed, di_pair_sort);

        Matrix D2 = new Matrix(D.getRowDimension(), D.getColumnDimension());
        Matrix V2 = new Matrix(V.getRowDimension(), V.getColumnDimension());

        for (int i = 0; i < dvec_indexed.length; i++) {
            D2.set(i, i, D.get(dvec_indexed[i].index, dvec_indexed[i].index));
            int height = V.getRowDimension() - 1;
            Matrix tmp = V.getMatrix(dvec_indexed[i].index, dvec_indexed[i].index, 0, height);
            V2.setMatrix(i, i, 0, height, tmp);
        }
        //TODO : Not sure why, but this has to be flipped - check this out maybe?
        Matrix V3 = new Matrix(V.getRowDimension(), V.getColumnDimension());
        for (int i = 0; i < V3.getRowDimension(); i++) {
            for (int j = 0; j < V3.getColumnDimension(); j++) {
                V3.set(i, j, V2.get(V3.getRowDimension() - i - 1, V3.getColumnDimension() - j - 1));
            }
        }

        return new Matrix[]{D2, V3};
    }

    public boolean isTrained() {
        return trained;
    }

    public int getNumEigenVecs() {
        return numEigenVecs;
    }

    void processTrainingSet(opencv_core.Mat cameraImage, String string) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

        class ProgressTracker {

        Thread thread;
        int task = 0;
        private ProgressMonitor progressMonitor;
        private Timer timer;
        private String sProgress;
        private boolean bFinished;

        public void advanceProgress(final String message) {
            task++;
            System.out.println(message);
            sProgress = "Task " + task + ": " + message;
        }

        class TimerListener implements ActionListener {

            public void actionPerformed(ActionEvent evt) {
                progressMonitor.setProgress(1);
                progressMonitor.setNote(sProgress);
                if (progressMonitor.isCanceled() || bFinished) {

                    timer.stop();
                }
            }
        }

        public void run(JComponent parent, final Runnable calc, String title) {
            bFinished = false;
            progressMonitor = new ProgressMonitor(parent,
                    title, "", 0, 100);
            progressMonitor.setProgress(0);
            progressMonitor.setMillisToDecideToPopup(0);

            timer = new Timer(100, new TimerListener());

            /*final SwingWorker worker = new SwingWorker() {
                public Object construct() {
                    thread = new Thread(calc);
                    thread.setPriority(Thread.MIN_PRIORITY);
                    thread.start();
                    return null;
                }
            };*/
            //worker.start();
            timer.start();

        }

        public void finished() {
            bFinished = true;
            progressMonitor.close();
            timer.stop();
        }
    }
}

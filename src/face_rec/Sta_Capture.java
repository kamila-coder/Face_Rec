/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package face_rec;

import java.sql.*;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.IntBuffer;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import org.bytedeco.javacpp.BytePointer;
import static org.bytedeco.javacpp.opencv_core.CV_32SC1;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.MatVector;
import org.bytedeco.javacpp.opencv_core.Rect;
import org.bytedeco.javacpp.opencv_core.RectVector;
import org.bytedeco.javacpp.opencv_core.Scalar;
import org.bytedeco.javacpp.opencv_core.Size;
import org.bytedeco.javacpp.opencv_face.FaceRecognizer;
import org.bytedeco.javacpp.opencv_face.LBPHFaceRecognizer;
import static org.bytedeco.javacpp.opencv_imgcodecs.imencode;
import static org.bytedeco.javacpp.opencv_imgcodecs.imread;
import static org.bytedeco.javacpp.opencv_imgcodecs.imwrite;
import org.bytedeco.javacpp.opencv_imgproc;
import static org.bytedeco.javacpp.opencv_imgproc.COLOR_BGRA2GRAY;
import static org.bytedeco.javacpp.opencv_imgproc.cvtColor;
import static org.bytedeco.javacpp.opencv_imgproc.rectangle;
import org.bytedeco.javacpp.opencv_objdetect.CascadeClassifier;
import org.bytedeco.javacpp.opencv_videoio.VideoCapture;


public class Sta_Capture extends javax.swing.JFrame {

    private Sta_Capture.DaemonThread myThread = null;

    VideoCapture webSource = null;
    Mat cameraImage = new Mat();
    CascadeClassifier cascade = new CascadeClassifier("C:\\Users\\HOME\\Desktop\\Kamila\\Staff_photos\\haarcascade_frontalface_alt.xml");
    //CascadeClassifier cascade = new CascadeClassifier("C:\\Users\\Lynx\\Desktop\\Kamila\\Staff_photos\\haarcascade_frontalface_alt.xml");
    BytePointer mem = new BytePointer();
    RectVector detectedFaces = new RectVector();

    String root, Staname, DOB, InTime, OutTime, MailID, image;
    
    int numSamples = 25, sample=1, Phnno,idPerson;
    double bsalary;
    DB db = new DB();
    String s;
    
    public Sta_Capture(int id, String Sname, String dob,double salary, String it, String ot, int phn , String MID, String imagee ) {
        
        initComponents();
        this.setAlwaysOnTop(true);
        idPerson=id; 
        Staname=Sname;
        DOB=dob;
        bsalary=salary;
        InTime=it;
        OutTime=ot;
        Phnno=phn;
        MailID=MID;
        image=imagee;
        
        startCamera();
       
    }

    private Sta_Capture() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        label_photo = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        counterLabel = new javax.swing.JLabel();
        saveButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Times New Roman", 3, 40)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 102, 102));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Capture 25 Shots");
        jLabel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, null, java.awt.Color.black, java.awt.Color.black));
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 440, -1));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(0, 0, 0), new java.awt.Color(0, 0, 0)));

        label_photo.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(label_photo, javax.swing.GroupLayout.DEFAULT_SIZE, 444, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(label_photo, javax.swing.GroupLayout.DEFAULT_SIZE, 433, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 470, 460));

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        counterLabel.setFont(new java.awt.Font("Times New Roman", 1, 48)); // NOI18N
        counterLabel.setText("00");
        counterLabel.setPreferredSize(new java.awt.Dimension(55, 65));

        saveButton.setFont(new java.awt.Font("Times New Roman", 3, 26)); // NOI18N
        saveButton.setText("Capture");
        saveButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        saveButton.setOpaque(false);
        saveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(130, 130, 130)
                .addComponent(counterLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(saveButton, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(saveButton, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2))
                    .addComponent(counterLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 550, 490, 90));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 490, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 640, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed

    }//GEN-LAST:event_saveButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Sta_Capture.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Sta_Capture.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Sta_Capture.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Sta_Capture.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Sta_Capture().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel counterLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel label_photo;
    private javax.swing.JButton saveButton;
    // End of variables declaration//GEN-END:variables


class DaemonThread implements Runnable {

        protected volatile boolean runnable = false;
      
        public void run() {
            synchronized (this) {
                while (runnable) {
                    try{
                        if (webSource.grab()){ 
                            
                            webSource.retrieve(cameraImage);
                            Graphics g = label_photo.getGraphics();
                            Mat imageColor = new Mat();
                            imageColor = cameraImage;

                            Mat imageGray = new Mat();
                            cvtColor(imageColor, imageGray,COLOR_BGRA2GRAY);

                            RectVector detectedFaces = new RectVector();
                            cascade.detectMultiScale(imageColor, detectedFaces, 1.1, 1, 0, new Size(150, 150), new Size(500, 500));
                        
                        for (int i = 0; i < detectedFaces.size(); i++) {
                            Rect dadosFace = detectedFaces.get(0);
                            rectangle(imageColor, dadosFace, new Scalar(255, 255, 255, 5));
                            Mat face;
                            face = new Mat(imageGray, dadosFace);
                            opencv_imgproc.resize(face, face, new Size(160, 160));
                            
                            if (saveButton.getModel().isPressed()) {
                                if (sample <= numSamples) {
                                    String cropped = "C:\\Users\\HOME\\Desktop\\Kamila\\Staff_photos\\person." + idPerson + "." + sample + ".jpg";
                                    //String cropped = "C:\\Users\\Lynx\\Desktop\\Kamila\\Staff_photos\\person." + idPerson + "." + sample + ".jpg";
                                    
                                    imwrite(cropped, face);
                                    System.out.println("Photo " + idPerson + " captured\n");
                                    counterLabel.setText(String.valueOf(sample));
                                    sample++;
                                }
                                if (sample > 25) {
                                    generate();
                                    insertDatabase();
                                    System.out.println("File Generated");
                                    stopCamera();
                                }
                            }
                        }
                        
                        imencode(".bmp", cameraImage, mem); 
                        Image im = ImageIO.read(new ByteArrayInputStream(mem.getStringBytes()));
                        BufferedImage buff = (BufferedImage) im;
                        
                        if (g.drawImage(buff, 0, 0, getWidth(), getHeight() - 90, 0, 0, buff.getWidth(), buff.getHeight(), null)) {
                            if (runnable == false) {
                                System.out.println("Save The Photo");
                                this.wait();
                            }
                        }
                    }
                }   catch (IOException ex) { 
                       ex.printStackTrace();
                       JOptionPane.showMessageDialog(null, "Error In Starting Camera (IOEx)\n" + ex);
                } catch (InterruptedException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Error In Starting Camera (Interrupted)\n" + ex);
                } 
           }
        }
      }
   }

public void generate(){
      
      File directory=new File("C:\\Users\\HOME\\Desktop\\Kamila\\Staff_photos");
      //File directory=new File("C:\\Users\\Lynx\\Desktop\\Kamila\\Staff_photos");
      FilenameFilter filter = new FilenameFilter() {
          @Override
          public boolean accept(File dir, String name) {
              return name.endsWith(".jpg")||name.endsWith(".png");
              
          }
      };
      
      File[] files = directory.listFiles(filter);
      MatVector photos = new MatVector(files.length);
      Mat labels = new Mat(files.length, 1, CV_32SC1);
      IntBuffer labelsBuffer = labels.createBuffer();
      
      int counter= 0;
      for(File image: files)
      {
          Mat photo = imread(image.getAbsolutePath(), COLOR_BGRA2GRAY);
          int idP = Integer.parseInt(image.getName().split("\\.")[1]);
          opencv_imgproc.resize(photo, photo, new Size(160, 160));

          photos.put(counter, photo);
          labelsBuffer.put(counter, idP);
          counter++;
          
      }
      
        FaceRecognizer lbph = LBPHFaceRecognizer.create();
        lbph.train(photos, labels);
        lbph.save("C:\\Users\\HOME\\Desktop\\Kamila\\Staff_photos\\classifierLBPH.yml");
       //lbph.save("C:\\Users\\Lynx\\Desktop\\Kamila\\Staff_photos\\classifierLBPH.yml");
  }

public void insertDatabase(){
      DB db=new DB();
     
        try
         {
            PreparedStatement pst= db.getpst("INSERT INTO `staff` (Name, DOB, BSalary, Intime, Off, phn, MID,images) VALUES (?,?,?,?,?,?,?,?)");
         
            pst.setString(1,Staname);
            pst.setString(2,DOB);
            pst.setString(3,Double.toString(bsalary));
            pst.setString(4,InTime);
            pst.setString(5,OutTime);
            pst.setString(6,Integer.toString(Phnno));
            pst.setString(7,MailID);
            pst.setString(8,image);
           
            pst.executeUpdate();
            JOptionPane.showMessageDialog(this, "Data of " +Staname+" Successfully Registered And 25 Photos Captured");
       
         }
        catch(Exception ex)
        {
            JOptionPane.showMessageDialog(null, ex);
        }
  }

public void stopCamera(){
      myThread.runnable = false;
      webSource.release();
      dispose();
  }
  
   public void startCamera(){
      webSource = new VideoCapture(0);
      myThread = new Sta_Capture.DaemonThread();
      Thread t = new Thread(myThread);
      t.setDaemon(true);
      myThread.runnable=true;
      t.start();
   
  }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package face_rec;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.IntBuffer;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacpp.DoublePointer;
import org.bytedeco.javacpp.IntPointer;
import org.bytedeco.javacpp.opencv_core;
import static org.bytedeco.javacpp.opencv_core.CV_32SC1;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.Rect;
import org.bytedeco.javacpp.opencv_core.RectVector;
import org.bytedeco.javacpp.opencv_core.Scalar;
import org.bytedeco.javacpp.opencv_core.Size;
import org.bytedeco.javacpp.opencv_face;
import static org.bytedeco.javacpp.opencv_imgcodecs.imencode;
import static org.bytedeco.javacpp.opencv_imgcodecs.imread;
import static org.bytedeco.javacpp.opencv_imgcodecs.imwrite;
import org.bytedeco.javacpp.opencv_imgproc;
import static org.bytedeco.javacpp.opencv_imgproc.COLOR_BGRA2GRAY;
import static org.bytedeco.javacpp.opencv_imgproc.cvtColor;
import static org.bytedeco.javacpp.opencv_imgproc.rectangle;
import org.bytedeco.javacpp.opencv_objdetect;
import org.bytedeco.javacpp.opencv_videoio;

public class StaffAtt extends javax.swing.JFrame {

     int hour, second, minute;
     private StaffAtt.DaemonThread myThread = null;

    opencv_videoio.VideoCapture webSource = null;
    opencv_core.Mat cameraImage = new opencv_core.Mat();
    opencv_objdetect.CascadeClassifier cascade = new opencv_objdetect.CascadeClassifier("C:\\Users\\Lynx\\Desktop\\Kamila\\Staff_photos\\haarcascade_frontalface_alt.xml");
    opencv_face.FaceRecognizer recognizer = opencv_face.LBPHFaceRecognizer.create();
    
    BytePointer mem = new BytePointer();
    opencv_core.RectVector detectedFaces = new opencv_core.RectVector();

    String root, Name, InTime,image;
    int idPerson;
    int numSamples = 3, sample=1;
    
    String s;
    
    DB db = new DB();
    
    
    public StaffAtt() {
        
        setTitle("Staff Make Your Attendance");
        setResizable(false);
        setLocationRelativeTo(null);
        initComponents();
        this.setAlwaysOnTop(true);
        recognizer.read("C:\\Users\\HOME\\Desktop\\Kamila\\Staff_photos\\classifierLBPH.yml");
        //recognizer.read("C:\\Users\\Lynx\\Desktop\\Kamila\\Staff_photos\\classifierLBPH.yml");
        recognizer.setThreshold(80);
        
        startCamera();
    }


    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        mainmenu = new javax.swing.JButton();
        Exit = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        label_photo = new javax.swing.JLabel();
        lblSid = new javax.swing.JLabel();
        lblSname = new javax.swing.JLabel();
        lblInT = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        lbldate = new javax.swing.JLabel();
        lbltime = new javax.swing.JLabel();
        CamStop = new javax.swing.JButton();
        CamStart = new javax.swing.JButton();
        refresh = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jPanel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.darkGray, java.awt.Color.darkGray, java.awt.Color.darkGray, java.awt.Color.darkGray));

        mainmenu.setFont(new java.awt.Font("Times New Roman", 3, 24)); // NOI18N
        mainmenu.setText("Back To...");
        mainmenu.setBorder(new javax.swing.border.MatteBorder(null));
        mainmenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mainmenuActionPerformed(evt);
            }
        });

        Exit.setFont(new java.awt.Font("Times New Roman", 3, 24)); // NOI18N
        Exit.setText("Exit");
        Exit.setBorder(new javax.swing.border.MatteBorder(null));
        Exit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ExitActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(mainmenu, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Exit, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(mainmenu, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Exit, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(0, 0, 0), new java.awt.Color(0, 0, 0)));
        jPanel2.setPreferredSize(new java.awt.Dimension(468, 458));
        jPanel2.setVerifyInputWhenFocusTarget(false);

        label_photo.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lblSid.setFont(new java.awt.Font("Times New Roman", 1, 25)); // NOI18N
        lblSid.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSid.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));

        lblSname.setFont(new java.awt.Font("Times New Roman", 1, 25)); // NOI18N
        lblSname.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSname.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));

        lblInT.setFont(new java.awt.Font("Times New Roman", 1, 25)); // NOI18N
        lblInT.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblInT.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(label_photo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 47, Short.MAX_VALUE)
                        .addComponent(lblSid, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lblSname, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(64, 64, 64))))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(105, 105, 105)
                .addComponent(lblInT, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(label_photo, javax.swing.GroupLayout.PREFERRED_SIZE, 347, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblSid, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblSname, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblInT, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 481, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 9, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(97, 97, 97)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 467, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel1.setFont(new java.awt.Font("Times New Roman", 3, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 102, 102));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Automatic Attendance Process");
        jLabel1.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.black, java.awt.Color.black));

        jPanel6.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.black, java.awt.Color.black));

        lbldate.setBackground(new java.awt.Color(204, 204, 255));
        lbldate.setFont(new java.awt.Font("Times New Roman", 1, 25)); // NOI18N
        lbldate.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbldate.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));

        lbltime.setBackground(new java.awt.Color(204, 204, 255));
        lbltime.setFont(new java.awt.Font("Times New Roman", 1, 25)); // NOI18N
        lbltime.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbltime.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));

        CamStop.setFont(new java.awt.Font("Times New Roman", 3, 24)); // NOI18N
        CamStop.setText("Stop Camera");
        CamStop.setBorder(new javax.swing.border.MatteBorder(null));
        CamStop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CamStopActionPerformed(evt);
            }
        });

        CamStart.setFont(new java.awt.Font("Times New Roman", 3, 24)); // NOI18N
        CamStart.setText("Start Camera");
        CamStart.setToolTipText("");
        CamStart.setBorder(new javax.swing.border.MatteBorder(null));
        CamStart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CamStartActionPerformed(evt);
            }
        });

        refresh.setFont(new java.awt.Font("Times New Roman", 3, 24)); // NOI18N
        refresh.setText("Refresh");
        refresh.setBorder(new javax.swing.border.MatteBorder(null));
        refresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbldate, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbltime, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 46, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(CamStop, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(CamStart, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(refresh, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(CamStart, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(CamStop, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(refresh, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(lbldate, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(7, 7, 7)
                        .addComponent(lbltime, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 470, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel1)
                .addGap(14, 14, 14)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void mainmenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mainmenuActionPerformed

        Attendance m = new Attendance();
        m.setVisible(true);
        this.dispose();

        stopCamera();
    }//GEN-LAST:event_mainmenuActionPerformed

    private void ExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ExitActionPerformed
        ab();
        notification();
        Welcome_page m = new Welcome_page();
        m.setVisible(true);
        this.dispose();
       
        stopCamera();
    }//GEN-LAST:event_ExitActionPerformed

    private void CamStopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CamStopActionPerformed

        webSource.release();
        clear();

    }//GEN-LAST:event_CamStopActionPerformed

    private void CamStartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CamStartActionPerformed
        startCamera();
    }//GEN-LAST:event_CamStartActionPerformed

    private void refreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshActionPerformed

        clear();
    }//GEN-LAST:event_refreshActionPerformed

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
            java.util.logging.Logger.getLogger(StaffAtt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(StaffAtt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(StaffAtt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(StaffAtt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new StaffAtt().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton CamStart;
    private javax.swing.JButton CamStop;
    private javax.swing.JButton Exit;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JLabel label_photo;
    private javax.swing.JLabel lblInT;
    private javax.swing.JLabel lblSid;
    private javax.swing.JLabel lblSname;
    private javax.swing.JLabel lbldate;
    private javax.swing.JLabel lbltime;
    private javax.swing.JButton mainmenu;
    private javax.swing.JButton refresh;
    // End of variables declaration//GEN-END:variables

    class DaemonThread implements Runnable {

        protected volatile boolean runnable = false;
        

        @Override
        public void run() {
           
            synchronized (this) {
                while (runnable) {
                    
                     Calendar cal = Calendar.getInstance();
                
                int day = cal.get(Calendar.DAY_OF_MONTH);
                int month = cal.get(Calendar.MONTH);
                int year = cal.get(Calendar.YEAR);
                lbldate.setText(year+":"+(month+1)+":"+day);
        
		hour=cal.get(Calendar.HOUR_OF_DAY);
		minute=cal.get(Calendar.MINUTE);
		second=cal.get(Calendar.SECOND);
			
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                java.util.Date dat = cal.getTime();
		String time=sdf.format(dat);
		
		lbltime.setText(time);
                    try {
                     opencv_objdetect.CascadeClassifier cascade = new opencv_objdetect.CascadeClassifier("C:\\Users\\HOME\\Desktop\\Kamila\\Staff_photos\\haarcascade_frontalface_alt.xml");
                        //opencv_objdetect.CascadeClassifier cascade = new opencv_objdetect.CascadeClassifier("C:\\Users\\Lynx\\Desktop\\Kamila\\Staff_photos\\haarcascade_frontalface_alt.xml");   
                        if (webSource.grab()) {
                            webSource.retrieve(cameraImage);
                            Graphics g = label_photo.getGraphics();

                            Mat imageGray = new Mat();
                            cvtColor(cameraImage, imageGray, COLOR_BGRA2GRAY);

                            RectVector detectedFace = new RectVector();
                            cascade.detectMultiScale(imageGray, detectedFace, 1.1, 2, 0, new Size(150, 150), new Size(500, 500));

                            for (int i = 0; i < detectedFace.size(); i++) {
                                Rect dadosFace = detectedFace.get(i);
                                rectangle(cameraImage, dadosFace, new Scalar(0, 255, 0, 0));
                                Mat faceCapturada = new Mat(imageGray, dadosFace);
                                opencv_imgproc.resize(faceCapturada, faceCapturada, new Size(160, 160));

                                IntPointer rotulo = new IntPointer(1);
                                DoublePointer confidence = new DoublePointer(1);
                                recognizer.predict(faceCapturada, rotulo, confidence);
                                
                                int prediction = rotulo.get(0);
                                String name = null;
                               if(!(prediction ==-1) ){
                                    
                                    System.out.println(confidence.get(0));
                                    idPerson = prediction;
                                    recogsta(); 
                                 
                                  
                                } else {
                                    lblSid.setText("0");
                                    lblSname.setText("Unknown Person");
                                    lblInT.setText("");
                                    idPerson = 0;
                                    cap();
                                    Undata();
                                    
                                } 
                            }
                            
                            imencode(".bmp", cameraImage, mem);
                            Image im = ImageIO.read(new ByteArrayInputStream(mem.getStringBytes()));
                            BufferedImage buff = (BufferedImage) im;

                            if (g.drawImage(buff, 0, 0, getWidth(), getHeight() - 100, 0, 0, buff.getWidth(), buff.getHeight(), null)) {
                                if (runnable == false) {
                                    System.out.println("Photo Saved");
                                    this.wait();
                                }
                            }
                        }
                         
                    } catch (IOException | InterruptedException ex) {
                    }  
                    
                }
               
            } 
            
        }
           
    }

private void recogsta(){
        
        SwingWorker worker = new SwingWorker() {

            @Override
            protected Object doInBackground() throws Exception {
               db.getConnection();
               try{
                   String SQL= "SELECT * FROM staff WHERE EID = " + String.valueOf(idPerson);
                   db.getResultSet(SQL);
                   
                   while(db.rs.next())
                   {
                       lblSid.setText(db.rs.getString("EID"));
                       lblSname.setText(db.rs.getString("Name"));
                       lblInT.setText(db.rs.getString("Intime"));
                       
                       System.out.println("Staff: " + db.rs.getString(2));
                       
                       Array Staff = db.rs.getArray("Name");
                       String[] staff = (String[]) Staff.getArray();
                       
                       for(int i=0; i < staff.length; i++){
                           System.out.println(staff[i]);
                       }
                    }
                   
               }catch(Exception ex)
               {
                   System.out.println(ex);
               }
               
               stadata();
                db.close();
                return null;
            }
           
        };
         
        worker.execute();
        
    }

    public void stopCamera(){
      myThread.runnable = false;
      webSource.release();
      dispose();
  }
  
   public void startCamera(){
      webSource = new opencv_videoio.VideoCapture(0);
      myThread = new StaffAtt.DaemonThread();
      Thread t = new Thread(myThread);
      t.setDaemon(true);
      myThread.runnable=true;
      t.start();
   
  }
   
    public void stadata()
 {
    try {
        
        String time = lbltime.getText();
        DateFormat timeform = new SimpleDateFormat("hh:mm:ss");
        Time opentime = new Time(timeform.parse(time).getTime()); 
        double a = (opentime.getTime())/(60*60);
        double b=a/1000;
        
        if((b<=2.5)||(b>=12.5))
        {
           
           webSource.release();
           clear();
           JOptionPane.showMessageDialog(this, "System is closing time now, please wait for the opening time");
            Welcome_page wp = new Welcome_page();
           wp.setVisible(true);
           this.dispose();
        }
        
        else{
        try { 
            ResultSet rs=db.getResultSet("select * from staattendance WHERE EID='"
                    +lblSid.getText()+"' AND Date='"+lbldate.getText()+"'");
            
            if(rs.next()==true)
                {
                    System.out.println("Already attendance marked");
                }
            
            else{
               
                PreparedStatement pst= db.getpst("INSERT INTO staattendance "
                        + "(Date, EID, InTime, Status) VALUES (?,?,?,?)");
                    
                pst.setString(1,lbldate.getText());
                pst.setString(2,lblSid.getText());
                pst.setString(3,lbltime.getText());
                
                String Yes="Present";
                String late="Half Day";
               
                String CT = lblInT.getText();
                String IT= lbltime.getText();//vantha
                
                DateFormat formatter = new SimpleDateFormat("hh:mm:ss");
                Time timeValue = new Time(formatter.parse(CT).getTime());  
                Time timeValue1 = new Time(formatter.parse(IT).getTime());
          
                double x = (timeValue1.getTime()- timeValue.getTime())/(1000*60*60);
           
                    if(x>=3)
                    {
                       pst.setString(4,late); 
                       System.out.println(x+CT+IT);
                    }
                    else{
                        pst.setString(4,Yes);
                        System.out.println(x+CT+IT);
                    }
                    
               pst.executeUpdate();  
            } 
            
            }catch (SQLException ex) {
                Logger.getLogger(Stu_Attendance.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex);
            }
            }}
        catch (ParseException ex) {
            Logger.getLogger(Stu_Attendance.class.getName()).log(Level.SEVERE, null, ex);
        }
 } 
   public void Undata()
 {
    try {
     
        ResultSet rs=db.getResultSet("select * from unauthorized WHERE Time='"+lbltime.getText()+"'");

        if(rs.next()==true)
            {
                System.out.println("Already person captured");
            }
        
        else{
               
                PreparedStatement pst= db.getpst("INSERT INTO unauthorized (Date, Time, Image) VALUES (?,?,?)");
                  
                pst.setString(1,lbldate.getText());
                pst.setString(2,lbltime.getText());
                //pst.setString(3, label_photo.getText());
                Graphics g = label_photo.getGraphics();
                  imencode(".bmp", cameraImage, mem); 
                  Image im = ImageIO.read(new ByteArrayInputStream(mem.getStringBytes()));
                  BufferedImage buff = (BufferedImage) im;
                  boolean img = g.drawImage(buff, 0, 0, getWidth(), getHeight() - 100, 0, 0, buff.getWidth(), buff.getHeight(), null);
                pst.setString(3, String.valueOf(img));
                pst.executeUpdate();
                    
            }
               
        }
        catch (SQLException ex) {
            Logger.getLogger(Stu_Attendance.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
             Logger.getLogger(StaffAtt.class.getName()).log(Level.SEVERE, null, ex);
         }
 }
   
    public void clear()
   {
        lblSid.setText(null);
        lblSname.setText(null);
        lblInT.setText(null);
        label_photo.setText(null);
   }
    
    public void cap()
  {
      
      opencv_objdetect.CascadeClassifier cascade1 = new opencv_objdetect.CascadeClassifier("C:\\Users\\HOME\\Desktop\\Kamila\\UnAuthorized\\haarcascade_frontalface_alt.xml");
      //opencv_objdetect.CascadeClassifier cascade1 = new opencv_objdetect.CascadeClassifier("C:\\Users\\Lynx\\Desktop\\Kamila\\UnAuthorized\\haarcascade_frontalface_alt.xml");
         try{
            if (webSource.grab()){ 
                webSource.retrieve(cameraImage);
                            Graphics g = label_photo.getGraphics();
                            Mat imageColor = new Mat();
                            imageColor = cameraImage;

                            Mat imageGray = new Mat();
                            cvtColor(imageColor, imageGray,COLOR_BGRA2GRAY);

                            RectVector detectedFaces = new RectVector();
                            cascade1.detectMultiScale(imageColor, detectedFaces, 1.1, 1, 0, new Size(150, 150), new Size(500, 500));
                        
                        for (int i = 0; i < detectedFaces.size(); i++) {
                            Rect dadosFace = detectedFaces.get(0);
                            rectangle(imageColor, dadosFace, new Scalar(255, 255, 255, 5));
                            Mat face;
                            face = new Mat(imageGray, dadosFace);
                            opencv_imgproc.resize(face, face, new Size(160, 160));
                            
                            
                                if (sample <= numSamples) {
                                    
                                    String cropped = "C:\\Users\\HOME\\Desktop\\Kamila\\UnAuthorized\\person." + sample + ".jpg";
                                    //String cropped = "C:\\Users\\Lynx\\Desktop\\Kamila\\UnAuthorized\\person." + sample + ".jpg";
                                    imwrite(cropped, face);
                                    System.out.println("Photo captured\n");
                                    sample++;
                                }
                                if (sample > 3) {
                                    generate();
                                    System.out.println("File Generated");
                                  
                                }
                            
                        }
                        
                        imencode(".bmp", cameraImage, mem); 
                        Image im = ImageIO.read(new ByteArrayInputStream(mem.getStringBytes()));
                        BufferedImage buff = (BufferedImage) im;
                        
                        if (g.drawImage(buff, 0, 0, getWidth(), getHeight() - 90, 0, 0, buff.getWidth(), buff.getHeight(), null)) {
                          
                        }
                    }
                }   catch (IOException ex) { 
                       ex.printStackTrace();
                       JOptionPane.showMessageDialog(null, "Error In Starting Camera (IOEx)\n" + ex);
                } 
      
  }
    
    public void generate(){
      
      File directory=new File("C:\\Users\\HOME\\Desktop\\Kamila\\UnAuthorized"); 
      //File directory=new File("C:\\Users\\Lynx\\Desktop\\Kamila\\UnAuthorized");
      FilenameFilter filter = new FilenameFilter() {
          @Override
          public boolean accept(File dir, String name) {
              return name.endsWith(".jpg")||name.endsWith(".png");
              
          }
      };
      
      File[] files = directory.listFiles(filter);
      opencv_core.MatVector photos = new opencv_core.MatVector(files.length);
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
      
        opencv_face.FaceRecognizer lbph = opencv_face.LBPHFaceRecognizer.create();
        lbph.train(photos, labels);
        lbph.save("C:\\Users\\HOME\\Desktop\\Kamila\\UnAuthorized\\classifierLBPH.yml");
       //lbph.save("C:\\Users\\Lynx\\Desktop\\Kamila\\UnAuthorized\\classifierLBPH.yml");
       
  }
    
    
    public void ab()
{
      
       try{
            String sqlABS= "select EID,MID from staff where EID not in (SELECT EID FROM `staattendance` where Date in ('" + lbldate.getText() +"'))";
                db.getResultSet(sqlABS);
                   
                while(db.rs.next())
                   {
                                     
                       String EmpID = db.rs.getString("EID");
                       System.out.println(EmpID);
                       
                       String mailID = db.rs.getString("emailid");
                       System.out.println(mailID);
                       
                       
                        PreparedStatement pst= db.getpst("INSERT INTO staattendance"
                        + "(Date, EID, InTime,OutTime,OT,Status,WH) VALUES (?,?,?,?,?,?,?)");
                    
                pst.setString(1,lbldate.getText());
                pst.setString(2,EmpID);
                pst.setString(3,lbltime.getText());
                pst.setString(4,lbltime.getText());
                pst.setString(5,"0");
                pst.setString(6,"Absent");
                pst.setString(7,"0");
                
                pst.executeUpdate();
                
               
                        String to=mailID;
                        String sub = "STAFF ABSENT NOTIFICATION";
			String msg = "Dear Staff, \r\n\r\nWe are pleased to inform you that today you not came to the university,\r\nSo we have considered and marked Absent today";
			Email e = new Email(to,sub,msg);
			e.setVisible(false);
                        e.dispose(); 
                
                    }
                
               }catch(Exception ex)
               {
                   System.out.println(ex);
               }
}

public void notification()
{
    
  try{
            String sqlABS= "SELECT `EID`, `MID` FROM `staff` WHERE EID = (SELECT `EID` FROM `staattendance` WHERE (STATUS='Half Day'||STATUS='Short Leave') AND Date in ('" + lbldate.getText() +"'))";
                db.getResultSet(sqlABS);
                   
                while(db.rs.next())
                   {
                      
                    String mailID = db.rs.getString("emailid");
                    System.out.println(mailID);
                   
                        String to=mailID;
                        String sub = "STAFF SHORT LEAVE/ HALF DAY NOTIFICATION";
			String msg = "Dear Staff, We are pleased to inform you that today your in time is kinda late, So we have considered and marked your attendance performance as Half Day/ Short Leave.";
			Email e = new Email(to,sub,msg);
			e.setVisible(false);
                        e.dispose(); 
                
                    }
                
               }catch(Exception ex)
               {
                   System.out.println(ex);
               }
}
}



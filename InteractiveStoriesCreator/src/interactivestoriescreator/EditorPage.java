/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interactivestoriescreator;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

/**
 *
 * @author alexa
 */
public class EditorPage extends javax.swing.JFrame {

    int currentIndex; ArrayList<StoryPage> pages;
    StartPage parentPage;
    
    ArrayList<Image> componentImages = new ArrayList<Image>();
    ArrayList<JLabel> componentLabels = new ArrayList<JLabel>();
    ArrayList<JButton> componentButtons = new ArrayList<JButton>();
    
    JLabel picture = null;
    
    JLabel currentComponentLabel = null;
    Image currentComponent = null;
    
    JButton correctButton; JButton wrongButton1; JButton wrongButton2;
    JLabel correctChoice = null; JLabel wrongChoice1 = null; JLabel wrongChoice2 = null;
    
    JFileChooser fc = new JFileChooser();
    
    JPanel optionPanel = null;
    
    MediaPlayer mediaPlayer = null;
    JFXPanel fxPanel = new JFXPanel();
    
    public EditorPage(ArrayList<StoryPage> storyPages, int i, StartPage parent) {
        parentPage = parent;
        
        initComponents();
        
        pages = storyPages;
        currentIndex = i;
        
        jPanel2.setLayout(new GridBagLayout());
        for(File file:parentPage.components){
            ImportImage(file);
        }
        
        jLayeredPane1.setLayout(null);
        jLayeredPane1.setBounds(10, 10, 450, 800);
        jLayeredPane1.setBorder(BorderFactory.createLineBorder(Color.black));
        jLayeredPane1.setBackground(Color.gray);
        
        goToPage(currentIndex);
        //drawBackground(currentIndex);
        //for(JLabel component:pages.get(currentIndex).componentLabels){
        //        jLayeredPane1.add(component);
        //}
        
        correctButton = new JButton();
        correctButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if(pages.get(currentIndex).correctPicture == null){
                    int returnVal = fc.showOpenDialog(jLayeredPane1); //open file picker
                    if(returnVal == JFileChooser.APPROVE_OPTION){ try {
                        //when a file is selected
                        pages.get(currentIndex).correctPicture = ImageIO.read(fc.getSelectedFile());
                        } catch (IOException ex) {
                            Logger.getLogger(StoryPage.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        drawChoiceButtons();
                    }
                }
                else{
                    pages.get(currentIndex).correctPicture = null;
                    drawChoiceButtons();
                }
            }
        });
        
        wrongButton1 = new JButton(); 
        wrongButton1.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if(pages.get(currentIndex).wrongPicture1 == null){
                    int returnVal = fc.showOpenDialog(jLayeredPane1); //open file picker
                    if(returnVal == JFileChooser.APPROVE_OPTION){ try {
                        //when a file is selected
                        pages.get(currentIndex).wrongPicture1 = ImageIO.read(fc.getSelectedFile());
                        } catch (IOException ex) {
                            Logger.getLogger(StoryPage.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        drawChoiceButtons();
                    }
                }
                else{
                    pages.get(currentIndex).wrongPicture1 = null;
                    drawChoiceButtons();
                }
            }
        });
        
        wrongButton2 = new JButton();
        wrongButton2.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if(pages.get(currentIndex).wrongPicture2 == null){
                    int returnVal = fc.showOpenDialog(jLayeredPane1); //open file picker
                    if(returnVal == JFileChooser.APPROVE_OPTION){ try {
                        //when a file is selected
                        pages.get(currentIndex).wrongPicture2 = ImageIO.read(fc.getSelectedFile());
                        } catch (IOException ex) {
                            Logger.getLogger(StoryPage.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        drawChoiceButtons();
                    }
                }
                else{
                    pages.get(currentIndex).wrongPicture2 = null;
                    drawChoiceButtons();
                }
            }
        });
        //correctButton.setText("Set correct answer"); wrongButton1.setText("Set 1st wrong answer"); wrongButton2.setText("Set 2nd wrong answer");
        //correctButton.setBounds(130,300,140,30); wrongButton1.setBounds(330,300,140,30); wrongButton2.setBounds(530,300,140,30);
    }
    
    private void drawBackground(int index){
        if(picture != null) jLayeredPane1.remove(picture);
        Image image = pages.get(index).background.getScaledInstance(800, 450, Image.SCALE_DEFAULT); //scale the image down to fit in the window
        ImageIcon icon = new ImageIcon(image); //make an image icon from the image
        
        picture = new JLabel(); //make a new label
        picture.setIcon(icon); //add the image icon to the lab    
        picture.setBounds(0,0,800,450);
        
        jLayeredPane1.add(picture,JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.revalidate();
        jLayeredPane1.repaint();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jCheckBox1 = new javax.swing.JCheckBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel2 = new javax.swing.JPanel();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jLayeredPane1 = new javax.swing.JLayeredPane();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jButton8 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButton1.setIcon(parentPage.nextIcon);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setIcon(parentPage.prvIcon);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setIcon(parentPage.outlineIcon);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jCheckBox1.setText("Choice page");
        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 307, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 513, Short.MAX_VALUE)
        );

        jScrollPane1.setViewportView(jPanel2);

        jButton4.setIcon(parentPage.saveIcon);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setIcon(parentPage.importIcon);
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jLayeredPane1.setPreferredSize(new java.awt.Dimension(800, 450));

        javax.swing.GroupLayout jLayeredPane1Layout = new javax.swing.GroupLayout(jLayeredPane1);
        jLayeredPane1.setLayout(jLayeredPane1Layout);
        jLayeredPane1Layout.setHorizontalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 800, Short.MAX_VALUE)
        );
        jLayeredPane1Layout.setVerticalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 450, Short.MAX_VALUE)
        );

        jButton6.setText("Import");
        jButton6.setIcon(parentPage.importIcon);
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton7.setText("Play");
        jButton7.setIcon(parentPage.playIcon);
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jLabel1.setText("Narration");

        jLabel2.setText("Characters");

        jLabel3.setText("Page");

        jButton8.setIcon(parentPage.deleteIcon);
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jLabel4.setText("Outline");

        jLabel5.setText("Page preview");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jButton2)
                            .addGap(202, 202, 202)
                            .addComponent(jLabel1)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jButton6)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jButton7)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton1))
                        .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jCheckBox1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3))
                    .addComponent(jScrollPane1)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton4)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3)
                    .addComponent(jCheckBox1)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton1)
                    .addComponent(jButton4)
                    .addComponent(jButton5)
                    .addComponent(jButton6)
                    .addComponent(jButton7)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jButton8))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if(currentIndex < pages.size()-1){
            goToPage(currentIndex+1);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        if(currentIndex > 0){
            goToPage(currentIndex-1);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    public final void goToPage(int index){
        if(index>=0 && index<pages.size()){
            if(picture != null) jLayeredPane1.remove(picture);
            
            for(JLabel component:pages.get(currentIndex).componentLabels){
                jLayeredPane1.remove(component);
                System.out.println("component removed! " + component);
            }
            
            if(correctChoice != null){jLayeredPane1.remove(correctChoice); correctChoice = null;}
            if(wrongChoice1 != null){jLayeredPane1.remove(wrongChoice1); wrongChoice1 = null;}
            if(wrongChoice2 != null){jLayeredPane1.remove(wrongChoice2); wrongChoice2 = null;}
                        
            currentIndex = index;
            drawBackground(currentIndex);
            
            for(JLabel component:pages.get(currentIndex).componentLabels){
                jLayeredPane1.add(component);
                System.out.println("component added! " + component);
            }
            
            drawChoiceButtons();

            if(pages.get(currentIndex).sound == null){jButton6.setText("Import"); jButton6.setIcon(parentPage.importIcon);}
            else {jButton6.setText("Delete"); jButton6.setIcon(parentPage.deleteIcon);}
            
            jLayeredPane1.revalidate();
            jLayeredPane1.repaint();
        }
    }
    
    public void drawChoiceButtons(){
        if(correctButton != null) jLayeredPane1.remove(correctButton);
        if(wrongButton1 != null) jLayeredPane1.remove(wrongButton1);
        if(wrongButton2 != null) jLayeredPane1.remove(wrongButton2);
        
        if(correctChoice != null) jLayeredPane1.remove(correctChoice);
        if(wrongChoice1 != null) jLayeredPane1.remove(wrongChoice1);
        if(wrongChoice2 != null) jLayeredPane1.remove(wrongChoice2);
        
        if(pages.get(currentIndex).choicePage){
                jCheckBox1.setSelected(true);
                if(pages.get(currentIndex).correctPicture != null){
                    ImageIcon icon = new ImageIcon(pages.get(currentIndex).correctPicture.getScaledInstance(140, 200, Image.SCALE_DEFAULT));
                    correctChoice = new JLabel();
                    correctChoice.setIcon(icon);
                    correctChoice.setBounds(530, 100, 140, 200);
                    jLayeredPane1.add(correctChoice, JLayeredPane.PALETTE_LAYER);
                    
                    correctButton.setText("Delete correct answer");
                    correctButton.setBounds(530,300,140,30);
                    jLayeredPane1.add(correctButton, JLayeredPane.POPUP_LAYER);
                }
                else{
                    correctButton.setText("Set correct answer");
                    correctButton.setBounds(530,200,140,30);
                    jLayeredPane1.add(correctButton, JLayeredPane.POPUP_LAYER);
                }
                
                if(pages.get(currentIndex).wrongPicture1 != null){
                    ImageIcon icon = new ImageIcon(pages.get(currentIndex).wrongPicture1.getScaledInstance(140, 200, Image.SCALE_DEFAULT));
                    wrongChoice1 = new JLabel();
                    wrongChoice1.setIcon(icon);
                    wrongChoice1.setBounds(130, 100, 140, 200);
                    jLayeredPane1.add(wrongChoice1, JLayeredPane.PALETTE_LAYER);
                    
                    wrongButton1.setText("Delete wrong answer");
                    wrongButton1.setBounds(130,300,140,30);
                    jLayeredPane1.add(wrongButton1, JLayeredPane.POPUP_LAYER);
                }
                else{
                    wrongButton1.setText("Set 1st wrong answer");
                    wrongButton1.setBounds(130,200,140,30);
                    jLayeredPane1.add(wrongButton1, JLayeredPane.POPUP_LAYER);
                }
                                
                if(pages.get(currentIndex).wrongPicture2 != null){
                    ImageIcon icon = new ImageIcon(pages.get(currentIndex).wrongPicture2.getScaledInstance(140, 200, Image.SCALE_DEFAULT));
                    wrongChoice2 = new JLabel();
                    wrongChoice2.setIcon(icon);
                    wrongChoice2.setBounds(330, 100, 140, 200);
                    jLayeredPane1.add(wrongChoice2, JLayeredPane.PALETTE_LAYER);
                    
                    wrongButton2.setText("Delete wrong answer");
                    wrongButton2.setBounds(330,300,140,30);
                    jLayeredPane1.add(wrongButton2, JLayeredPane.POPUP_LAYER);
                }
                else{
                    wrongButton2.setText("Set 2nd wrong answer answer");
                    wrongButton2.setBounds(330,200,140,30);
                    jLayeredPane1.add(wrongButton2, JLayeredPane.POPUP_LAYER);
                }
        }
            
        else{
            jCheckBox1.setSelected(false);
        }
        
        jLayeredPane1.repaint();
    }
    
    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        this.setVisible(false);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        int returnVal = fc.showOpenDialog(jLayeredPane1); //open file picker
        if(returnVal == JFileChooser.APPROVE_OPTION){ //when a file is selected
            File file = fc.getSelectedFile();
            parentPage.components.add(file); //add the file to roles
            ImportImage(file);
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        BufferedImage background = pages.get(currentIndex).background;
        //BufferedImage newImage = new BufferedImage(background.getWidth(), background.getHeight(), BufferedImage.TYPE_INT_ARGB);
        
        Graphics2D g2d = background.createGraphics();
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
       
        //g2d.drawImage(background, 0, 0, null);
        
        double scaleFactor = (double)background.getWidth()/picture.getWidth();
        System.out.println("scale: " + scaleFactor);
        for(int i = 0; i < pages.get(currentIndex).components.size(); i++){
            Image img = pages.get(currentIndex).components.get(i);
            int x = (int)Math.floor(pages.get(currentIndex).componentLabels.get(i).getX()*scaleFactor);
            int y = (int)Math.floor((pages.get(currentIndex).componentLabels.get(i).getY())*scaleFactor);
            int width = (int)Math.floor(pages.get(currentIndex).componentLabels.get(i).getWidth()*scaleFactor);
            int height = (int)Math.floor(pages.get(currentIndex).componentLabels.get(i).getHeight()*scaleFactor);
            System.out.println("x:" + x + " y:" + y + " width:" + width + " height:" + height);
            BufferedImage image = toBufferedImage(img.getScaledInstance(width, height, Image.SCALE_DEFAULT));
            
            g2d.drawImage(image, x, y, null);
            jLayeredPane1.remove(pages.get(currentIndex).componentLabels.get(i));
        }
        
        pages.get(currentIndex).componentLabels = new ArrayList<JLabel>();
        pages.get(currentIndex).components = new ArrayList<Image>();
        
        //pages.get(currentIndex).background = background;
        //ImageIO.write(newImage,"png",pages.get(currentIndex).file);

        drawBackground(currentIndex);
        
        Image image = background.getScaledInstance(300, 169, Image.SCALE_DEFAULT);
        ImageIcon icon = new ImageIcon(image);
        
        picture = new JLabel(); //make a new label
        picture.setIcon(icon); //add the image icon to the lab    
        picture.setBounds(15,15,300,169);
        
        pages.get(currentIndex).panel.remove(pages.get(currentIndex).picture);
        pages.get(currentIndex).panel.add(picture);
        pages.get(currentIndex).panel.repaint();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox1ActionPerformed
        if(!pages.get(currentIndex).choicePage){
            pages.get(currentIndex).makeChoice();
            drawChoiceButtons();
        }
        else{
            pages.get(currentIndex).makeNotChoice();
            drawChoiceButtons();
        }
    }//GEN-LAST:event_jCheckBox1ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
    if(pages.get(currentIndex).sound == null){
        int returnVal = fc.showOpenDialog(jLayeredPane1); //open file picker
        if(returnVal == JFileChooser.APPROVE_OPTION){ //when a file is selected
            pages.get(currentIndex).sound = fc.getSelectedFile();
            jButton6.setText("Delete");
            jButton6.setIcon(parentPage.deleteIcon);
        }
    }
    else{
        pages.get(currentIndex).sound = null;
        jButton6.setText("Import");
        jButton6.setIcon(parentPage.importIcon);
    }
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        if(pages.get(currentIndex).sound != null){
            if(mediaPlayer != null){
                        mediaPlayer.stop();
                        mediaPlayer = null;
            }
            else{
                if(pages.get(currentIndex).sound != null){
                    try {
                        Media hit = new Media(pages.get(currentIndex).sound.toURI().toURL().toString());
                        mediaPlayer = new MediaPlayer(hit);
                        mediaPlayer.setOnEndOfMedia(new Runnable() {
                            @Override
                            public void run() {
                                mediaPlayer = null;
                            }
                        });
                        mediaPlayer.play();
                    } catch (Exception ex) {
                        Logger.getLogger(StartPage.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        if(parentPage.components.size() > 0){ //if there are roles
            jPanel2.remove(componentLabels.get(parentPage.components.size()-1)); //undraw the label (image) of the role
            jPanel2.remove(componentButtons.get(parentPage.components.size()-1)); //undraw the button of the role

            componentImages.remove(parentPage.components.size()-1); //remove the role from the list of roles
            componentLabels.remove(parentPage.components.size()-1); //remove the corresponding label from the list of labels
            componentButtons.remove(parentPage.components.size()-1); //remove the corresponding button from the list of buttons
            parentPage.components.remove(parentPage.components.size()-1);

            jPanel2.revalidate(); //redraw the page
            jPanel2.repaint(); //redraw the background of the page
        }
    }//GEN-LAST:event_jButton8ActionPerformed

    
    public static BufferedImage toBufferedImage(Image img)
    {
        if (img instanceof BufferedImage)
        {
            return (BufferedImage) img;
        }

        // Create a buffered image with transparency
        BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

        // Draw the image on to the buffered image
        Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();

        // Return the buffered image
        return bimage;
    }
    
    private void ImportImage(File file){
        System.out.println("adding Image!");
        System.out.println(file);
        Image image = null;
        GridBagConstraints c = new GridBagConstraints(); //used to position image
        c.insets = new Insets(3,3,3,3); //specifies margins around image
        try {
            image = ImageIO.read(file); //convert file to image
        } catch (IOException ex) {
            Logger.getLogger(StartPage.class.getName()).log(Level.SEVERE, null, ex); //BAD THINGS (can happen if the file is not an image)
            return;
        }
        componentImages.add(image);
        Image image2 = image.getScaledInstance(200, 113, Image.SCALE_DEFAULT); //scale the image down to fit in the window
        ImageIcon icon = new ImageIcon(image2); //make an image icon from the image

        JLabel picLabel = new JLabel(); //make a new label
        picLabel.setIcon(icon); //add the image icon to the label
        //picLabel.setText(pages.get(pages.size()-1).getName()); //set the label text to the filename of the image

        JButton picButton = new JButton(); //make a new button (for picking an explanation)
        picButton.setText("add");

        picButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){ //if the button is clicked, handle in explanationButtonActionPerformed
                //System.out.println("add button clicked!");
                addButtonActionPerformed(e.getSource());
                //explanationButtonActionPerformed(e.getSource()); //pass the button to explanationButtonActionPerformed
            }
        });

        componentLabels.add(picLabel); //add the new label to the list of labels
        componentButtons.add(picButton); //add the new button to the list of buttons

        //set position of label in grid
        c.gridx = 0;
        c.gridy = (componentImages.size()-1)*2;
        jPanel2.add(picLabel,c); //draw image

        //set position of button in grid
        c.gridx = 1;
        jPanel2.add(picButton,c); //draw button

        jPanel2.revalidate(); //redraw and scale page
    }
    
    private void addButtonActionPerformed(Object e){
        int i = componentButtons.indexOf(e);
        System.out.println(i);
        Image image = componentImages.get(i);
        pages.get(currentIndex).components.add(image);
        pages.get(currentIndex).componentLabels.add(drawSizedImage(image,0.1));
        System.out.println(pages.get(currentIndex).componentLabels);
    }
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
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(EditorPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EditorPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EditorPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EditorPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        //java.awt.EventQueue.invokeLater(new Runnable() {
        //    public void run() {
        //        new EditorPage().setVisible(true);
        //    }
        //});
    }
    
    public void handleOptionClick(JLabel label){
        final JLabel p = label;
        label.addMouseListener(new MouseAdapter() {
            
            @Override
            public void mouseClicked(MouseEvent me){
                makeOptionPanel(label);
            }
        });
    }
    
    public void makeOptionPanel(JLabel label){
        currentComponentLabel = label;
        System.out.println("making option panel");
        if(optionPanel != null) jLayeredPane1.remove(optionPanel);
        optionPanel = new JPanel();
        optionPanel.setBounds(label.getX(),label.getY()-50,160,50);
        optionPanel.setBackground(Color.gray);
        optionPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        jLayeredPane1.add(optionPanel,JLayeredPane.POPUP_LAYER);

        optionPanel.setLayout(null);

        JButton deleteButton = new JButton();
        deleteButton.setText("del");
        deleteButton.setBounds(5, 5, 50, 40);
        optionPanel.add(deleteButton);
        deleteButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                int i = pages.get(currentIndex).componentLabels.indexOf(currentComponentLabel);
                pages.get(currentIndex).components.remove(i);
                jLayeredPane1.remove(currentComponentLabel);
                jLayeredPane1.remove(optionPanel); optionPanel = null;
                pages.get(currentIndex).componentLabels.remove(currentComponentLabel);
                jLayeredPane1.revalidate(); jLayeredPane1.repaint();
            }
        });

        JButton plusButton = new JButton();
        plusButton.setText(" + ");
        plusButton.setBounds(55,5,50,40);
        optionPanel.add(plusButton);
        plusButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                int i = pages.get(currentIndex).componentLabels.indexOf(currentComponentLabel);
                int x = currentComponentLabel.getX(); int y = currentComponentLabel.getY();
                Image image = pages.get(currentIndex).components.get(i);

                Double scale = (double) currentComponentLabel.getHeight()/image.getHeight(null);

                jLayeredPane1.remove(currentComponentLabel);
                jLayeredPane1.remove(optionPanel); optionPanel = null;
                pages.get(currentIndex).componentLabels.remove(currentComponentLabel);

                currentComponentLabel = drawSizedImage(image,scale+0.01,x,y);
                pages.get(currentIndex).componentLabels.add(currentComponentLabel);
                makeOptionPanel(currentComponentLabel);

                jLayeredPane1.revalidate(); jLayeredPane1.repaint();
            }
        });

        JButton minusButton = new JButton();
        minusButton.setText(" - ");
        minusButton.setBounds(105,5,50,40);
        optionPanel.add(minusButton);
        minusButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                int i = pages.get(currentIndex).componentLabels.indexOf(currentComponentLabel);
                int x = currentComponentLabel.getX(); int y = currentComponentLabel.getY();
                Image image = pages.get(currentIndex).components.get(i);

                Double scale = (double) currentComponentLabel.getHeight()/image.getHeight(null);

                jLayeredPane1.remove(currentComponentLabel);
                jLayeredPane1.remove(optionPanel); optionPanel = null;
                pages.get(currentIndex).componentLabels.remove(currentComponentLabel);

                currentComponentLabel = drawSizedImage(image,scale-0.01,x,y);
                pages.get(currentIndex).componentLabels.add(currentComponentLabel);
                makeOptionPanel(currentComponentLabel);

                jLayeredPane1.revalidate(); jLayeredPane1.repaint();
            }
        });
                
        jLayeredPane1.revalidate();
        jLayeredPane1.repaint();
    }
    
    public void handleDrag(JLabel label){
        final JLabel p = label;
        label.addMouseMotionListener(new MouseMotionAdapter() {

            @Override
            public void mouseDragged(MouseEvent me) {
                if(optionPanel != null) jLayeredPane1.remove(optionPanel);
                optionPanel = null;
                jLayeredPane1.repaint();
                me.translatePoint(me.getComponent().getLocation().x, me.getComponent().getLocation().y);
                p.setLocation(me.getX(), me.getY());
            }

        });
    }
    
    public JLabel drawSizedImage(Image image, double scale){
        Double widthScale = scale*image.getWidth(null);
        Double heightScale = scale*image.getHeight(null);
        Image scaledImage = image.getScaledInstance(widthScale.intValue(), heightScale.intValue(), Image.SCALE_DEFAULT);
        ImageIcon icon = new ImageIcon(scaledImage); //make an image icon from the image
        
        JLabel picLabel = new JLabel(); //make a new label
        picLabel.setIcon(icon); //add the image icon to the label
        
        System.out.println(image.getWidth(null)+","+image.getHeight(null));
        picLabel.setBounds(10, 10, scaledImage.getWidth(null), scaledImage.getHeight(null));
       
        jLayeredPane1.add(picLabel,JLayeredPane.PALETTE_LAYER);
        
        jLayeredPane1.revalidate();
        jLayeredPane1.repaint();
        
        handleDrag(picLabel);
        handleOptionClick(picLabel);
        return picLabel;
    }
    
    public JLabel drawSizedImage(Image image, double scale, int x, int y){
        Double widthScale = scale*image.getWidth(null);
        Double heightScale = scale*image.getHeight(null);
        Image scaledImage = image.getScaledInstance(widthScale.intValue(), heightScale.intValue(), Image.SCALE_DEFAULT);
        ImageIcon icon = new ImageIcon(scaledImage); //make an image icon from the image
        
        JLabel picLabel = new JLabel(); //make a new label
        picLabel.setIcon(icon); //add the image icon to the label
        
        System.out.println(image.getWidth(null)+","+image.getHeight(null));
        picLabel.setBounds(x, y, scaledImage.getWidth(null), scaledImage.getHeight(null));
       
        jLayeredPane1.add(picLabel,JLayeredPane.PALETTE_LAYER);
        
        jLayeredPane1.revalidate();
        jLayeredPane1.repaint();
        
        handleDrag(picLabel);
        handleOptionClick(picLabel);
        return picLabel;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}

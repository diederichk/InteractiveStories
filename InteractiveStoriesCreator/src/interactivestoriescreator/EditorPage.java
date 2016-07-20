/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interactivestoriescreator;

import java.awt.AlphaComposite;
import java.awt.Color;
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
 * @author Alex Popov
 */
public class EditorPage extends javax.swing.JFrame {
    
    int currentIndex; ArrayList<StoryPage> pages; //list of pages in outline and index of currently displayed page
    StartPage parentPage; //the startPage which created this editorPage
    
    ArrayList<Image> componentImages = new ArrayList<Image>(); //list of imported but not added components/characters
    ArrayList<JLabel> componentLabels = new ArrayList<JLabel>(); //list of imported but not added labels of components/characters
    ArrayList<JButton> componentButtons = new ArrayList<JButton>(); //list of "add" buttons
    
    JLabel picture = null; //currently displayed page background
    
    JLabel currentComponentLabel = null; //currently selected component/character label (used to draw option panel)
    
    JButton correctButton; JButton wrongButton1; JButton wrongButton2; //buttons for selecting/deleting choices on choice pages
    JLabel correctChoice = null; JLabel wrongChoice1 = null; JLabel wrongChoice2 = null; //labels for choices on choice pages
    
    JFileChooser fc = new JFileChooser(); //allows for choosing of files
    MediaPlayer mediaPlayer = null; //allows for playing of music
    JFXPanel fxPanel = new JFXPanel(); //allows for playing of music (required by MediaPlayer)
    
    JPanel optionPanel = null;
    
    int offsetX = 0; int offsetY = 0; //offset of mouse click (allows for better dragging of components/characters)
    
    public EditorPage(ArrayList<StoryPage> storyPages, int i, StartPage parent) {
        parentPage = parent;
        
        initComponents();
        
        pages = storyPages;
        currentIndex = i;
        
        jPanel2.setLayout(new GridBagLayout()); //set layout for import panel to grid
        for(File file:parentPage.components){ //loop through all components/characters on a given page
            importImage(file); //import the characters into the editor
        }
        
        jLayeredPane1.setLayout(null); //set layout for editor jPanel to absolute
        jLayeredPane1.setBounds(10, 10, 450, 800); //set the size of the editor jPanel
        jLayeredPane1.setBackground(Color.gray); //set the background color of the editor jPanel
        
        goToPage(currentIndex); //go to the specified page
        
        //setup of choice buttons for choice pages
        correctButton = new JButton();
        correctButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){ //listen for mouse click
                if(pages.get(currentIndex).correctPicture == null){ //if there isn't a correct answer selected
                    int returnVal = fc.showOpenDialog(jLayeredPane1); //open file picker
                    if(returnVal == JFileChooser.APPROVE_OPTION){ //when a file is selected
                        try { 
                            pages.get(currentIndex).correctPicture = ImageIO.read(fc.getSelectedFile()); //import the file as an image
                        } catch (IOException ex) {
                            Logger.getLogger(StoryPage.class.getName()).log(Level.SEVERE, null, ex); //BAD THINGS (can happen if the file can't be imported)
                        }
                        drawChoiceButtons(); //redraw the choice buttons with the new answer
                    }
                }
                else{ //if a correct answer is already selected
                    pages.get(currentIndex).correctPicture = null; //delete the correct answer
                    drawChoiceButtons(); //redraw the choice buttons without the deleted answer
                }
            }
        });
        
        wrongButton1 = new JButton(); 
        wrongButton1.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){ //listen for mouse click
                if(pages.get(currentIndex).wrongPicture1 == null){ //if there isn't a wrong answer selected
                    int returnVal = fc.showOpenDialog(jLayeredPane1); //open file picker
                    if(returnVal == JFileChooser.APPROVE_OPTION){ //when a file is selected
                        try {                 
                            pages.get(currentIndex).wrongPicture1 = ImageIO.read(fc.getSelectedFile()); //import the file as an image
                        } catch (IOException ex) {
                            Logger.getLogger(StoryPage.class.getName()).log(Level.SEVERE, null, ex); //BAD THINGS (can happen if the file can't be imported)
                        }
                        drawChoiceButtons(); //redraw the choice buttons with the new answer
                    }
                }
                else{ //if a wrong answer is already selected
                    pages.get(currentIndex).wrongPicture1 = null; //delete the wrong answer
                    drawChoiceButtons(); //redraw the choice buttons without the deleted answer
                }
            }
        });
        
        wrongButton2 = new JButton();
        wrongButton2.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){ //listen for mouse click
                if(pages.get(currentIndex).wrongPicture2 == null){ //if there isn't a wrong answer selected
                    int returnVal = fc.showOpenDialog(jLayeredPane1); //open file picker
                    if(returnVal == JFileChooser.APPROVE_OPTION){ //when a file is selected
                        try {
                            pages.get(currentIndex).wrongPicture2 = ImageIO.read(fc.getSelectedFile()); //import the file as an image
                        } catch (IOException ex) {
                            Logger.getLogger(StoryPage.class.getName()).log(Level.SEVERE, null, ex); //BAD THINGS (can happen if the file can't be imported)
                        }
                        drawChoiceButtons(); //redraw the choice buttons with the new answer
                    }
                }
                else{ //if a wrong answer is already selected
                    pages.get(currentIndex).wrongPicture2 = null; //delete the wrong answer
                    drawChoiceButtons(); //redraw the choice buttons without the deleted answer
                }
            }
        });
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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1)
                    .addComponent(jButton5)
                    .addComponent(jButton8)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton2)
                        .addComponent(jButton4)
                        .addComponent(jButton6)
                        .addComponent(jButton7)
                        .addComponent(jLabel1)
                        .addComponent(jLabel2)
                        .addComponent(jLabel3)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    //draws the background of a specified page in the editor
    private void drawBackground(int index){
        if(picture != null) jLayeredPane1.remove(picture); //if a background is already drawn, remove it
        Image image = pages.get(index).background.getScaledInstance(800, 450, Image.SCALE_DEFAULT); //scale the background image down to fit in the window
        ImageIcon icon = new ImageIcon(image); //make an image icon from the image
        
        picture = new JLabel(); //make a new label
        picture.setIcon(icon); //add the image icon to the label
        picture.setBounds(0,0,800,450); //set the size of the label
        
        jLayeredPane1.add(picture,JLayeredPane.DEFAULT_LAYER); //add the label to the editor
        jLayeredPane1.revalidate();
        jLayeredPane1.repaint(); //redraw and resize the editor
    }
    
    //handles clicks of the "next" button
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if(currentIndex < pages.size()-1){ //if not already on the last page
            goToPage(currentIndex+1); //go to the next page
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    //handles clicks of the "previous" button
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        if(currentIndex > 0){ //if not already on the first page
            goToPage(currentIndex-1); //go to the previous page
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    //handles clicks of the "outline" button
    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        for(int i = 0; i < parentPage.storyPages.size(); i++){ //loop through every page in the outline
            savePage(i); //save the page (merge components into the background)
        }
        this.setVisible(false); //hide the editor
    }//GEN-LAST:event_jButton3ActionPerformed

    //handles clicks of the "import component" button
    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        int returnVal = fc.showOpenDialog(jLayeredPane1); //open file picker
        if(returnVal == JFileChooser.APPROVE_OPTION){ //when a file is selected
            File file = fc.getSelectedFile();
            parentPage.components.add(file); //add the file to the list of components
            importImage(file); //add the component to the components panel
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    //handles clicks of the "save" button
    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        savePage(currentIndex); //save the currently open page

        goToPage(currentIndex); //go to the currently open page (redraws the new background and removes components)

        Image image = pages.get(currentIndex).background.getScaledInstance(300, 169, Image.SCALE_DEFAULT); //make a smaller version of the page background (for the outline)
        ImageIcon icon = new ImageIcon(image); //make an icon from the small background

        JLabel smallPicture = new JLabel(); //make a new label
        smallPicture.setIcon(icon); //add the image icon to the label
        smallPicture.setBounds(15,15,300,169); //set the size and location of the label

        pages.get(currentIndex).panel.remove(pages.get(currentIndex).picture); //remove the old outline background label
        pages.get(currentIndex).panel.add(smallPicture); //add the new background label to the outline
        pages.get(currentIndex).panel.repaint(); //repaint the outline
    }//GEN-LAST:event_jButton4ActionPerformed

    //handles click of "choice page" checkbox
    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox1ActionPerformed
        if(!pages.get(currentIndex).choicePage){ //if the current page is a normal page
            pages.get(currentIndex).makeChoice(); //make the current page a choice page
            drawChoiceButtons();
        }
        else{ //if the current page is a choice page
            pages.get(currentIndex).makeNotChoice(); //make the current page not a nomral page
            drawChoiceButtons();
        }
    }//GEN-LAST:event_jCheckBox1ActionPerformed

    //handles clicks of the "import/delete narration" button
    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        if(pages.get(currentIndex).sound == null){ //if there isn't narration on the current page
            int returnVal = fc.showOpenDialog(jLayeredPane1); //open file picker
            if(returnVal == JFileChooser.APPROVE_OPTION){ //when a file is selected
                pages.get(currentIndex).sound = fc.getSelectedFile(); //import the file as narration
                jButton6.setText("Delete"); //change the button text to "delete"
                jButton6.setIcon(parentPage.deleteIcon); //change the button icon to "delete"
            }
        }
        else{ //if there is narration on the current page
            pages.get(currentIndex).sound = null; //delete the narration
            jButton6.setText("Import"); //change the button text to "import"
            jButton6.setIcon(parentPage.importIcon); //change the button icon to "import"
        }
    }//GEN-LAST:event_jButton6ActionPerformed

    //handlles clicks of the "play narration" button
    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        if(pages.get(currentIndex).sound != null){ //if narration has been imported
            if(mediaPlayer != null){ //if sound is currently playing
                        mediaPlayer.stop(); //stop playing sound
                        mediaPlayer = null; //delete the mediaplayer
            }
            else{
                if(pages.get(currentIndex).sound != null){ //if sound is not currently playing
                    try {
                        Media narration = new Media(pages.get(currentIndex).sound.toURI().toURL().toString()); //convert the narration file to a media
                        mediaPlayer = new MediaPlayer(narration); //make a mediaPlayer from the media
                        mediaPlayer.setOnEndOfMedia(new Runnable() { //once the mediaPlayer is done playing the sound
                            @Override
                            public void run() {
                                mediaPlayer = null; //delete the mediaPlayer
                            }
                        });
                        mediaPlayer.play(); //play the sound
                    } catch (Exception ex) {
                        Logger.getLogger(StartPage.class.getName()).log(Level.SEVERE, null, ex); //BAD THINGS (can happen if the narration file isn't found
                    }
                }
            }
        }
    }//GEN-LAST:event_jButton7ActionPerformed

    //handles clicks of the "delete component" button
    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        if(parentPage.components.size() > 0){ //if there are components
            jPanel2.remove(componentLabels.get(parentPage.components.size()-1)); //undraw the label of the component
            jPanel2.remove(componentButtons.get(parentPage.components.size()-1)); //undraw the button of the component

            componentImages.remove(parentPage.components.size()-1); //component image from the list of component images
            componentLabels.remove(parentPage.components.size()-1); //remove the corresponding label from the list of labels
            componentButtons.remove(parentPage.components.size()-1); //remove the corresponding button from the list of buttons
            
            parentPage.components.remove(parentPage.components.size()-1); //remove the component from the list of components

            jPanel2.revalidate();
            jPanel2.repaint(); //redraw and scale import panel
        }
    }//GEN-LAST:event_jButton8ActionPerformed
    
    //opens a page specified by index
    public final void goToPage(int index){
        if(index>=0 && index<pages.size()){ //if the index is in the correct range (if a page with the specified index exists)
            if(picture != null) jLayeredPane1.remove(picture); //if a background is already drawn, delete it
            
            for(JLabel component:pages.get(currentIndex).componentLabels){ //for every component currently drawn in the editor
                jLayeredPane1.remove(component); //undraw the component
            }
            
            if(correctChoice != null){jLayeredPane1.remove(correctChoice); correctChoice = null;} //if a correct choice is currently drawn, delete it
            if(wrongChoice1 != null){jLayeredPane1.remove(wrongChoice1); wrongChoice1 = null;} //if a wrong choice is currently drawn, delete it
            if(wrongChoice2 != null){jLayeredPane1.remove(wrongChoice2); wrongChoice2 = null;} //if a wrong choice is currently drawn, delete it
                        
            currentIndex = index; //set the current index to the specified index
            drawBackground(currentIndex); //draw the background of the specified page
            
            for(JLabel component:pages.get(currentIndex).componentLabels){ //for every component on the specified page
                jLayeredPane1.add(component); //draw the component
            }
            
            drawChoiceButtons(); //draw choice buttons (if the page is a choice page)

            if(pages.get(currentIndex).sound == null){ //if the page doesn't have narration
                jButton6.setText("Import");  //set the import/delete narration button text to "import"
                jButton6.setIcon(parentPage.importIcon); //set the import/delete narration button icon to "import"
            } 
            else{ //if the page has narration
                jButton6.setText("Delete"); //set the import/delete narration button text to "delete"
                jButton6.setIcon(parentPage.deleteIcon); //set the import/delete narration button icon to "delete"
            }
            
            if(optionPanel != null){jLayeredPane1.remove(optionPanel); optionPanel = null;} //if an option panel is currently drawn, undraw and delete it
            
            jLayeredPane1.revalidate();
            jLayeredPane1.repaint(); //redraw and resize the editor
        }
    }
    
    //saves a page specified by index
    public void savePage(int index){
        BufferedImage background = pages.get(index).background; //get the full resolutoin backround of the page
        
        Graphics2D g2d = background.createGraphics(); //open the graphics of the background (allows for editing of image)
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1)); //set the alpha of the graphics to 1 (no transparency)
        
        double scaleFactor = (double)background.getWidth()/picture.getWidth(); //find the scale between the label (what is drawn on screen) and full resolution background
        for(int i = 0; i < pages.get(index).components.size(); i++){ //for every currently drawn component
            Image img = pages.get(index).components.get(i); //get the full resolution image of the component
            int x = (int)Math.floor(pages.get(index).componentLabels.get(i).getX()*scaleFactor); //find the x-location of the scaled component
            int y = (int)Math.floor((pages.get(index).componentLabels.get(i).getY())*scaleFactor); //find the y-location of the scaled component
            int width = (int)Math.floor(pages.get(index).componentLabels.get(i).getWidth()*scaleFactor); //find the width of the scaled component
            int height = (int)Math.floor(pages.get(index).componentLabels.get(i).getHeight()*scaleFactor); //find the height of the scaled component
            
            BufferedImage image = toBufferedImage(img.getScaledInstance(width, height, Image.SCALE_DEFAULT)); //scale the full resolution component to the correct size
            
            g2d.drawImage(image, x, y, null); //draw the scaled component onto the full resolution background
            jLayeredPane1.remove(pages.get(index).componentLabels.get(i)); //undraw the component label from the editor
        }
        
        pages.get(index).componentLabels = new ArrayList<JLabel>(); //delete the list of component labels
        pages.get(index).components = new ArrayList<Image>(); //delete the list of components
    }
    
    //draws or undraws choice buttons and choice images depending on if the current page is a choice page and which choices have been imported
    public void drawChoiceButtons(){
        if(correctButton != null) jLayeredPane1.remove(correctButton); //if a correct choice button is already drawn, undraw it
        if(wrongButton1 != null) jLayeredPane1.remove(wrongButton1); //if a wrong choice button is already drawn, undraw it
        if(wrongButton2 != null) jLayeredPane1.remove(wrongButton2); //if a wrong choice button is already drawn, undraw it
        
        if(correctChoice != null) jLayeredPane1.remove(correctChoice); //if a correct choice image is already drawn, undraw it
        if(wrongChoice1 != null) jLayeredPane1.remove(wrongChoice1); //if a wrong choice image is already drawn, undraw it
        if(wrongChoice2 != null) jLayeredPane1.remove(wrongChoice2); //if a wrong choice image is already drawn, undraw it
        
        if(pages.get(currentIndex).choicePage){ //if the current page is a choice page
                jCheckBox1.setSelected(true); //set the choice page checkbox to selected
                if(pages.get(currentIndex).correctPicture != null){ //if a correct choice has already been imported
                    ImageIcon icon = new ImageIcon(pages.get(currentIndex).correctPicture.getScaledInstance(140, 200, Image.SCALE_DEFAULT)); //scale the correct choice image and make an icon
                    correctChoice = new JLabel(); //make a new label
                    correctChoice.setIcon(icon); //add icon to label
                    correctChoice.setBounds(530, 100, 140, 200); //set the size and location of the label
                    jLayeredPane1.add(correctChoice, JLayeredPane.PALETTE_LAYER); //draw the label
                    
                    correctButton.setText("Delete correct answer"); //set the text of the import/delete correct choice button to "delete"
                    correctButton.setBounds(530,300,140,30); //set the size and location of the button
                    jLayeredPane1.add(correctButton, JLayeredPane.POPUP_LAYER); //draw the button
                }
                else{ //if a correct choice has not been imported
                    correctButton.setText("Set correct answer"); //set the text of the import/delete correct choice button to "import"
                    correctButton.setBounds(530,200,140,30); //set the size and location of the button
                    jLayeredPane1.add(correctButton, JLayeredPane.POPUP_LAYER); //draw the button
                }
                
                if(pages.get(currentIndex).wrongPicture1 != null){ //if a wrong choice has already been imported
                    ImageIcon icon = new ImageIcon(pages.get(currentIndex).wrongPicture1.getScaledInstance(140, 200, Image.SCALE_DEFAULT)); //scale the wrong choice image and make an icon
                    wrongChoice1 = new JLabel(); //make a new label
                    wrongChoice1.setIcon(icon); //add icon to label
                    wrongChoice1.setBounds(130, 100, 140, 200); //set the size and location of the label
                    jLayeredPane1.add(wrongChoice1, JLayeredPane.PALETTE_LAYER); //draw the label
                    
                    wrongButton1.setText("Delete wrong answer"); //set the text of the import/delete wrong choice button to "delete"
                    wrongButton1.setBounds(130,300,140,30); //set the size and location of the button
                    jLayeredPane1.add(wrongButton1, JLayeredPane.POPUP_LAYER); //draw the button
                }
                else{
                    wrongButton1.setText("Set 1st wrong answer"); //set the text of the import/delete correct choice button to "import"
                    wrongButton1.setBounds(130,200,140,30); //set the size and location of the button
                    jLayeredPane1.add(wrongButton1, JLayeredPane.POPUP_LAYER); //draw the button
                }
                                
                if(pages.get(currentIndex).wrongPicture2 != null){ //if a wrong choice has already been imported
                    ImageIcon icon = new ImageIcon(pages.get(currentIndex).wrongPicture2.getScaledInstance(140, 200, Image.SCALE_DEFAULT)); //scale the wrong choice image and make an icon
                    wrongChoice2 = new JLabel(); //make a new label
                    wrongChoice2.setIcon(icon); //add icon to label
                    wrongChoice2.setBounds(330, 100, 140, 200); //set the size and location of the label
                    jLayeredPane1.add(wrongChoice2, JLayeredPane.PALETTE_LAYER); //draw the label
                    
                    wrongButton2.setText("Delete wrong answer"); //set the text of the import/delete wrong choice button to "delete"
                    wrongButton2.setBounds(330,300,140,30); //set the size and location of the button
                    jLayeredPane1.add(wrongButton2, JLayeredPane.POPUP_LAYER); //draw the button
                }
                else{
                    wrongButton2.setText("Set 2nd wrong answer answer"); //set the text of the import/delete correct choice button to "import"
                    wrongButton2.setBounds(330,200,140,30); //set the size and location of the button
                    jLayeredPane1.add(wrongButton2, JLayeredPane.POPUP_LAYER); //draw the button
                }
        }
            
        else{ //if the current page is not a choice page (normal page)
            jCheckBox1.setSelected(false); //set the choice page checkbox to deselected
        }
        
        jLayeredPane1.repaint(); //redraw and resize the editor
    }
    
    //adds a new component from a specified file
    private void importImage(File file){
        Image image = null;
        GridBagConstraints c = new GridBagConstraints(); //used to position image
        c.insets = new Insets(3,3,3,3); //specifies margins around image
        try {
            image = ImageIO.read(file); //convert file to image
        } catch (IOException ex) {
            Logger.getLogger(StartPage.class.getName()).log(Level.SEVERE, null, ex); //BAD THINGS (can happen if the file is not an image)
            return;
        }
        componentImages.add(image); //add image to the list of components
        Image image2 = image.getScaledInstance(200, 113, Image.SCALE_DEFAULT); //scale the image down to fit in the window
        ImageIcon icon = new ImageIcon(image2); //make an image icon from the image

        JLabel picLabel = new JLabel(); //make a new label
        picLabel.setIcon(icon); //add the image icon to the label

        JButton addButton = new JButton(); //make a new button (for adding component to editor)
        addButton.setIcon(parentPage.addIcon); //change button icon to "add"

        addButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){ //if the button is clicked, handle in addButtonActionPerformed
                addButtonActionPerformed(e.getSource());
            }
        });

        componentLabels.add(picLabel); //add the new label to the list of labels
        componentButtons.add(addButton); //add the new button to the list of buttons

        //set position of label in grid
        c.gridx = 0;
        c.gridy = (componentImages.size()-1)*2;
        jPanel2.add(picLabel,c); //draw label

        //set position of button in grid
        c.gridx = 1;
        jPanel2.add(addButton,c); //draw button

        jPanel2.revalidate(); //redraw and scale import panel
    }
    
    //adds an imported component to the editor given its corresponding button
    private void addButtonActionPerformed(Object e){
        int i = componentButtons.indexOf(e); //find the index of the component's button (equal to the index of the component)
        Image image = componentImages.get(i); //get the component from the list of imported components
        pages.get(currentIndex).components.add(image); //add the component to the list of components in the editor
        pages.get(currentIndex).componentLabels.add(drawSizedImage(image,0.1)); //draw a scaled version of the component in the editor
    }
    
    //handles clicks of components
    public void handleOptionClick(JLabel label){
        final JLabel p = label; //get the component label
        label.addMouseListener(new MouseAdapter() { //listen for clicks on the component label
            @Override
            public void mouseClicked(MouseEvent me){ //if the component label is clicked
                makeOptionPanel(label); //draw the option panel for this label
            }
        });
    }
    
    //draws an option panel above a specified component label
    public void makeOptionPanel(JLabel label){
        currentComponentLabel = label;
        if(optionPanel != null){jLayeredPane1.remove(optionPanel); optionPanel = null;} //if an option panel is currently drawn, undraw and delete it
        optionPanel = new JPanel(); //make a new option anel
        optionPanel.setBounds(label.getX(),label.getY()-50,160,50); //set size and location of the option panel
        optionPanel.setBackground(Color.gray); //set the option panel background color
        optionPanel.setBorder(BorderFactory.createLineBorder(Color.black)); //set the option panel border color
        jLayeredPane1.add(optionPanel,JLayeredPane.POPUP_LAYER); //draw the option panel in the editor

        optionPanel.setLayout(null); //set the layout for the option panel to absolute

        //add delete button to option panel
        JButton deleteButton = new JButton();
        deleteButton.setIcon(parentPage.deleteIcon);
        deleteButton.setBounds(5, 5, 50, 40);
        optionPanel.add(deleteButton);
        deleteButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){ //listen for mouse click
                int i = pages.get(currentIndex).componentLabels.indexOf(currentComponentLabel); //get index of currently selected component
                pages.get(currentIndex).components.remove(i); //remove the component from the list of components
                jLayeredPane1.remove(currentComponentLabel); //undraw the component label from the editor
                jLayeredPane1.remove(optionPanel); optionPanel = null; //undraw and delete the option panel
                pages.get(currentIndex).componentLabels.remove(currentComponentLabel); //delete the component label from the list of component labels
                jLayeredPane1.revalidate(); jLayeredPane1.repaint(); //redraw and resize the editor
            }
        });

        //add increase size button to option panel
        JButton plusButton = new JButton();
        plusButton.setIcon(parentPage.enlargeIcon);
        plusButton.setBounds(55,5,50,40);
        optionPanel.add(plusButton);
        plusButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){ //listen for mouse click
                int i = pages.get(currentIndex).componentLabels.indexOf(currentComponentLabel); //get index of currently selected component
                int x = currentComponentLabel.getX(); int y = currentComponentLabel.getY(); //get location of component label
                Image image = pages.get(currentIndex).components.get(i); //get the component

                Double scale = (double) currentComponentLabel.getHeight()/image.getHeight(null); //get the scale of the label compared to the component (full resolution)

                jLayeredPane1.remove(currentComponentLabel); //undraw the component label
                jLayeredPane1.remove(optionPanel); optionPanel = null; //undraw the option panel
                pages.get(currentIndex).componentLabels.remove(currentComponentLabel); //delete the component label from the list of component labels

                currentComponentLabel = drawSizedImage(image,scale+0.02,x,y); //draw the component label at a larger size
                pages.get(currentIndex).componentLabels.add(i,currentComponentLabel); //add the component label to the list of component labels
                makeOptionPanel(currentComponentLabel); //draw the option panel above the new component label

                jLayeredPane1.revalidate(); jLayeredPane1.repaint(); //redraw and resize the editor
            }
        });

        //add decrease size button to option panel
        JButton minusButton = new JButton();
        minusButton.setIcon(parentPage.shrinkIcon);
        minusButton.setBounds(105,5,50,40);
        optionPanel.add(minusButton);
        minusButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){ //listen for mouse click
                int i = pages.get(currentIndex).componentLabels.indexOf(currentComponentLabel); //get index of currently selected component
                int x = currentComponentLabel.getX(); int y = currentComponentLabel.getY(); //get location of component label
                Image image = pages.get(currentIndex).components.get(i); //get the component

                Double scale = (double) currentComponentLabel.getHeight()/image.getHeight(null); //get the scale of the label compared to the component (full resolution)

                jLayeredPane1.remove(currentComponentLabel); //undraw the component label
                jLayeredPane1.remove(optionPanel); optionPanel = null; //undraw the option panel
                pages.get(currentIndex).componentLabels.remove(currentComponentLabel); //delete the component label from the list of component labels

                currentComponentLabel = drawSizedImage(image,scale-0.02,x,y); //draw the component label at a smaller size
                pages.get(currentIndex).componentLabels.add(i,currentComponentLabel); //add the component label to the list of component labels
                makeOptionPanel(currentComponentLabel); //draw the option panel above the new component label

                jLayeredPane1.revalidate(); jLayeredPane1.repaint(); //redraw and resize the editor
            }
        });
                
        jLayeredPane1.revalidate(); jLayeredPane1.repaint(); //redraw and resize the editor
    }
    
    //handles dragging of a component
    public void handleDrag(JLabel label){
        final JLabel p = label; //get tje component label
        label.addMouseListener(new MouseAdapter(){ //listen for clicks on the component label        
            @Override
            public void mousePressed(MouseEvent me){ //if the component label is clicked
                offsetX = me.getX(); //find the x-offset of the click (where on the component the click occured)
                offsetY = me.getY(); //find the y-offset of the click (where on the component the click occured)
            }
        });
        
        label.addMouseMotionListener(new MouseMotionAdapter() { //listen for drags on the component label
            @Override
            public void mouseDragged(MouseEvent me) { //if the component label is dragged
                if(optionPanel != null){jLayeredPane1.remove(optionPanel); optionPanel = null;} //if an option panel is currently drawn, undraw and delete it
                jLayeredPane1.repaint(); //redraw and resize the editor
                me.translatePoint(me.getComponent().getLocation().x, me.getComponent().getLocation().y); //move the location of the mousevent to the location of the mouse
                p.setLocation(me.getX()-offsetX, me.getY()-offsetY); //set the location of the label to the location of the mousevent, offset by the location of the original click
            }
        });
    }
    
    //draws a draggable scaled label in the top left corner given a component image and a scale
    public JLabel drawSizedImage(Image image, double scale){
        Double widthScale = scale*image.getWidth(null); //get the width of the new component label
        Double heightScale = scale*image.getHeight(null); //get the height of the new component label
        Image scaledImage = image.getScaledInstance(widthScale.intValue(), heightScale.intValue(), Image.SCALE_DEFAULT); //make an image of the correct height and width
        ImageIcon icon = new ImageIcon(scaledImage); //make icon from the image
        
        JLabel picLabel = new JLabel(); //make a new image label
        picLabel.setIcon(icon); //add the image icon to the image label
        
        picLabel.setBounds(10, 10, scaledImage.getWidth(null), scaledImage.getHeight(null)); //set the location and size of the component label
       
        jLayeredPane1.add(picLabel,JLayeredPane.PALETTE_LAYER); //draw the component label
        
        jLayeredPane1.revalidate(); jLayeredPane1.repaint(); //repaint and resize the editor
        
        handleDrag(picLabel); //set up dragging for the new component label
        handleOptionClick(picLabel); //set up clicking for the new component label
        return picLabel;
    }
    
    //draws a draggable scaled label in a specified location given a component image, scale and location
    public JLabel drawSizedImage(Image image, double scale, int x, int y){
        Double widthScale = scale*image.getWidth(null); //get the width of the new component label
        Double heightScale = scale*image.getHeight(null); //get the height of the new component label
        Image scaledImage = image.getScaledInstance(widthScale.intValue(), heightScale.intValue(), Image.SCALE_DEFAULT); //make an image of the correct height and width
        ImageIcon icon = new ImageIcon(scaledImage); //make an icon from the image
        
        JLabel picLabel = new JLabel(); //make a new image label
        picLabel.setIcon(icon); //add the image icon to the image label
        
        picLabel.setBounds(x, y, scaledImage.getWidth(null), scaledImage.getHeight(null)); //set the location and size of the component label
       
        jLayeredPane1.add(picLabel,JLayeredPane.PALETTE_LAYER); //draw the component label
        
        jLayeredPane1.revalidate(); jLayeredPane1.repaint(); //repaint and resize the editor
        
        handleDrag(picLabel); //set up dragging for the new component label
        handleOptionClick(picLabel); //set up clicking for the new component label
        return picLabel;
    }
    
    //converts an Image to a BufferedImage
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

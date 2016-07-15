/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interactivestoriescreator;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
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
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author alexa
 */
public class StoryPage {
    JPanel panel;
    JLabel picture;
    
    File correctPicture = null; File wrongPicture1 = null; File wrongPicture2 = null;
    ArrayList<Image> components = new ArrayList<Image>();
    ArrayList<JLabel> componentLabels = new ArrayList<JLabel>();
    
    File file = null;
    File sound = null;
    
    JPanel parentPanel;
    StartPage parentPage;
    
    boolean choicePage = false;
    
    JButton soundButton; JCheckBox choiceButton; JButton playButton;
    JButton correctButton; JButton wrongButton1; JButton wrongButton2;
    
    final JFileChooser fc = new JFileChooser();
    MediaPlayer mediaPlayer = null;
    JFXPanel fxPanel = new JFXPanel();
    
    public StoryPage(File pic, JPanel parent, StartPage page){
        file = pic;
        parentPanel = parent;
        parentPage = page;
        
        Image image = null;
        try {
            image = ImageIO.read(file);
        } catch (IOException ex) {
            Logger.getLogger(StartPage.class.getName()).log(Level.SEVERE, null, ex);
        }
        image = image.getScaledInstance(300, 169, Image.SCALE_DEFAULT); //scale the image down to fit in the window
        ImageIcon icon = new ImageIcon(image); //make an image icon from the image
        
        picture = new JLabel(); //make a new label
        picture.setIcon(icon); //add the image icon to the lab    
        picture.setBounds(15,15,300,169);
        
        panel = new JPanel();
        panel.setBounds(30,parentPage.storyPages.size()*230+100,600,200);
        panel.setLayout(null);
        panel.setBorder(BorderFactory.createLineBorder(Color.black));
        panel.setBackground(Color.gray);
        
        panel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                parentPage.snapPages();
            }
        });
        handleDrag(panel);
        
        JButton deleteButton = new JButton(); //make a new button (for picking an explanation)
        deleteButton.setText("Delete");
        deleteButton.setBounds(505,160,80,30);
        deleteButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){ //if the button is clicked, handle in explanationButtonActionPerformed
                deleteButtonActionPerformed(e.getSource()); //pass the button to explanationButtonActionPerformed
            }
        });
        
        correctButton = new JButton(); wrongButton1 = new JButton(); wrongButton2 = new JButton();
        correctButton.setText("Set correct answer"); wrongButton1.setText("Set 1st wrong answer"); wrongButton2.setText("Set 2nd wrong answer");
        correctButton.setBounds(445,20,140,30); wrongButton1.setBounds(445,50,140,30); wrongButton2.setBounds(445,80,140,30);
        
        correctButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if(correctPicture == null){
                    int returnVal = fc.showOpenDialog(parentPanel); //open file picker
                    if(returnVal == JFileChooser.APPROVE_OPTION){ //when a file is selected
                        correctPicture = fc.getSelectedFile();
                        correctButton.setText("Delete correct answer");
                    }
                }
                else{
                    correctPicture = null;
                    correctButton.setText("Set correct answer");
                }
            }
        });
        
        wrongButton1.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if(wrongPicture1 == null){
                    int returnVal = fc.showOpenDialog(parentPanel); //open file picker
                    if(returnVal == JFileChooser.APPROVE_OPTION){ //when a file is selected
                        wrongPicture1 = fc.getSelectedFile();
                        wrongButton1.setText("Delete wrong answer");
                    }
                }
                else{
                    wrongPicture1 = null;
                    wrongButton1.setText("Set 1st wrong answer");
                }
            }
        });
                
        wrongButton2.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if(wrongPicture2 == null){
                    int returnVal = fc.showOpenDialog(parentPanel); //open file picker
                    if(returnVal == JFileChooser.APPROVE_OPTION){ //when a file is selected
                        wrongPicture2 = fc.getSelectedFile();
                        wrongButton2.setText("Delete wrong answer");
                    }
                }
                else{
                    wrongPicture2 = null;
                    wrongButton2.setText("Set 2nd wrong answer");
                }
            }
        });
        
        choiceButton = new JCheckBox(); //make a new button (for picking an explanation)
        choiceButton.setText("Choice page");
        choiceButton.setBounds(320,20,120,30);
        choiceButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){ //if the button is clicked, handle in explanationButtonActionPerformed
                choiceButtonActionPerformed(e);
            }
        });
        
        playButton = new JButton();
        playButton.setText("Play");
        playButton.setBounds(320,130,120,30);
        playButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if(mediaPlayer != null){
                    mediaPlayer.stop();
                    mediaPlayer = null;
                }
                else{
                    if(sound != null){
                        try {
                            Media hit = new Media(sound.toURI().toURL().toString());
                            mediaPlayer = new MediaPlayer(hit);
                            mediaPlayer.play();
                        } catch (Exception ex) {
                            Logger.getLogger(StartPage.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
                    }
        });
        
        soundButton = new JButton(); //make a new button (for picking an explanation)
        soundButton.setText("Set narration");
        soundButton.setBounds(320,160,120,30);
        soundButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){ //if the button is clicked, handle in explanationButtonActionPerformed
                if(sound == null){
                    int returnVal = fc.showOpenDialog(parentPanel); //open file picker
                    if(returnVal == JFileChooser.APPROVE_OPTION){ //when a file is selected
                        sound = fc.getSelectedFile();
                        soundButton.setText("Delete narration");
                        panel.add(playButton);
                        panel.revalidate();
                        panel.repaint();
                    }
                }
                else{
                    sound = null;
                    soundButton.setText("Set narration");
                    panel.remove(playButton);
                    panel.repaint();
                }
            }
        });
        
        JButton editButton = new JButton(); //make a new button (for picking an explanation)
        editButton.setText("Edit");
        editButton.setBounds(200,160,120,30);
        editButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){ //if the button is clicked, handle in explanationButtonActionPerformed
                editButtonActionPerformed(e);
            }
        });
        //c.gridx = 0;
        //c.gridy = (story.size()-1)*2;
        panel.add(soundButton);
        panel.add(editButton);
        panel.add(choiceButton);
        panel.add(deleteButton);
        panel.add(picture);
        parentPanel.add(panel); //draw image
        
        parentPanel.revalidate(); //redraw and scale page
        parentPanel.repaint();
    }
    
    public void handleDrag(JPanel panel){
        final JPanel p = panel;
            panel.addMouseMotionListener(new MouseMotionAdapter() {

                @Override
                public void mouseDragged(MouseEvent me) {
                    me.translatePoint(me.getComponent().getLocation().x, me.getComponent().getLocation().y);
                    p.setLocation(me.getX(), me.getY());
                }

            });
    }
    
    public void choiceButtonActionPerformed(ActionEvent e){
        if(!choicePage){
            panel.setBackground(Color.cyan);
            panel.add(correctButton); panel.add(wrongButton1); panel.add(wrongButton2);
            choicePage = true;
        }
        else{
            panel.remove(correctButton); panel.remove(wrongButton1); panel.remove(wrongButton2);
            panel.setBackground(Color.gray);
            choicePage = false;
        }
    }
    
    public void deleteButtonActionPerformed(Object e){
        parentPanel.remove(panel);
        parentPage.storyPages.remove(this);

        parentPanel.revalidate();
        parentPanel.repaint();
        parentPage.snapPages();
    }
    
    public void editButtonActionPerformed(Object e){
        System.out.println(parentPage.storyPages.indexOf(this));
        parentPage.editor.setVisible(true);
        parentPage.editor.goToPage(parentPage.storyPages.indexOf(this));
    }
    
    public int getY(){
        return panel.getY();
    }
    
    public void setOrder(int i){
        panel.setLocation(30, i*230+100);
        parentPanel.revalidate();
    }
}
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
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
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
    
    BufferedImage correctPicture = null; BufferedImage wrongPicture1 = null; BufferedImage wrongPicture2 = null;
    ArrayList<Image> components = new ArrayList<Image>();
    ArrayList<JLabel> componentLabels = new ArrayList<JLabel>();
    
    //File file = null;
    BufferedImage background = null;
    File sound = null;
    
    JPanel parentPanel;
    StartPage parentPage;
    
    boolean choicePage = false;
    
    //JButton soundButton; JButton playButton;
    //JButton correctButton; JButton wrongButton1; JButton wrongButton2;
    
    final JFileChooser fc = new JFileChooser();
    
    public StoryPage(BufferedImage pic, JPanel parent, StartPage page){
        background = pic;
        parentPanel = parent;
        parentPage = page;
        
        //Image image = null;
        //try {
        //    image = ImageIO.read(file);
        //} catch (IOException ex) {
        //    Logger.getLogger(StartPage.class.getName()).log(Level.SEVERE, null, ex);
        //}
        Image image = background.getScaledInstance(300, 169, Image.SCALE_DEFAULT); //scale the image down to fit in the window
        ImageIcon icon = new ImageIcon(image); //make an image icon from the image
        
        picture = new JLabel(); //make a new label
        picture.setIcon(icon); //add the image icon to the lab    
        picture.setBounds(15,15,300,169);
        
        panel = new JPanel();
        panel.setBounds(30,parentPage.storyPages.size()*230+100,370,200);
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
        //deleteButton.setText("Delete");
        deleteButton.setIcon(parentPage.deleteIcon);
        deleteButton.setBounds(320,105,45,80);
        deleteButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){ //if the button is clicked, handle in explanationButtonActionPerformed
                deleteButtonActionPerformed(e.getSource()); //pass the button to explanationButtonActionPerformed
            }
        });
      
        JButton editButton = new JButton(); //make a new button (for picking an explanation)
        editButton.setIcon(parentPage.editIcon);
        //editButton.setText("Edit");
        editButton.setBounds(320,15,45,80);
        editButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){ //if the button is clicked, handle in explanationButtonActionPerformed
                editButtonActionPerformed(e);
            }
        });
        //c.gridx = 0;
        //c.gridy = (story.size()-1)*2;
        panel.add(editButton);
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
    
    public void makeChoice(){
        panel.setBackground(Color.cyan);
        choicePage = true;
    }
    public void makeNotChoice(){
            panel.setBackground(Color.gray);
            choicePage = false;
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
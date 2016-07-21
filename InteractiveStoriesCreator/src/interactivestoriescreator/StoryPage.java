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
 * @author Alex Popov
 */
public class StoryPage {
    JPanel panel; //background panel for the page
    JLabel picture; //icon for the page

    ArrayList<Image> components = new ArrayList<Image>(); //list of characters/components on the page
    ArrayList<JLabel> componentLabels = new ArrayList<JLabel>(); //list of character/componet icons on the page
    
    BufferedImage background = null; //background for the page
    File sound = null; //narration for the page
    
    JPanel parentPanel; //panel this page should be drawn in
    StartPage parentPage; //page that created this page
    
    boolean choicePage = false; //determines if the page is a choice page
    BufferedImage correctPicture = null; BufferedImage wrongPicture1 = null; BufferedImage wrongPicture2 = null; //correct and wrong choice images (only set if page is a choice page)
    
    final JFileChooser fc = new JFileChooser(); //allows for choosing of files
    
    public StoryPage(BufferedImage pic, JPanel parent, StartPage page){
        background = pic;
        parentPanel = parent;
        parentPage = page;
        
        Image image = background.getScaledInstance(300, 169, Image.SCALE_DEFAULT); //scale the image down to fit in the window
        ImageIcon icon = new ImageIcon(image); //make an image icon from the image
        
        picture = new JLabel(); //make a new label
        picture.setIcon(icon); //add the image icon to the label
        picture.setBounds(15,15,300,169); //set the location and size of the label
        
        panel = new JPanel(); //make a new panel
        panel.setBounds(30,parentPage.storyPages.size()*230+100,370,200); //set the size and location of the panel
        panel.setLayout(null); //set the panel to have an absolute layout (locations of components in the panel have to be explicitly defined)
        panel.setBorder(BorderFactory.createLineBorder(Color.black)); //add a black border to the panel
        panel.setBackground(Color.gray); //set the panel background to gray
        
        panel.addMouseListener(new java.awt.event.MouseAdapter() { //listen for the when the mouse is released
            public void mouseReleased(java.awt.event.MouseEvent evt) { //if the button is clicked, handle in snapPages
                parentPage.snapPages(); //rearange pages in the correct order
            }
        });
        handleDrag(panel); //setup drag and drop for the panel
        
        JButton deleteButton = new JButton(); //make a new button (for deleting a page)
        deleteButton.setIcon(parentPage.deleteIcon); //set the icon of the button to the delete icon
        deleteButton.setBounds(320,105,45,80); //set the location and size of the button
        deleteButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){ //if the button is clicked, handle in deleteButtonActionPerformed
                deleteButtonActionPerformed(e.getSource()); //pass the button to deleteButtonActionPerformed
            }
        });
      
        JButton editButton = new JButton(); //make a new button (for picking editing a page)
        editButton.setIcon(parentPage.editIcon); //set the icon for the button to the edit icon
        editButton.setBounds(320,15,45,80); //set the location and size of the button
        editButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){ //if the button is clicked, handle in editButtonActionPerformed
                editButtonActionPerformed(e); //pass the button to editButtonActionPerformed
            }
        });

        //draw buttons and icon on the panel
        panel.add(editButton);
        panel.add(deleteButton);
        panel.add(picture);
        
        //draw the panel on the outline
        parentPanel.add(panel);
        
        parentPanel.revalidate(); //redraw and scale page
        parentPanel.repaint();
    }
    
    //handles dragging of the page's panel
    public void handleDrag(JPanel panel){
        final JPanel p = panel;
            panel.addMouseMotionListener(new MouseMotionAdapter() { //listen for mouse drag on panel
                @Override
                public void mouseDragged(MouseEvent me) {
                    me.translatePoint(me.getComponent().getLocation().x, me.getComponent().getLocation().y); //get the location of the mouse pointer
                    p.setLocation(me.getX(), me.getY()); //set the location of the panel to the location of the mouse pointer
                }
            });
    }
    
    //makes the page a choice page
    public void makeChoice(){
        panel.setBackground(Color.cyan); //color the panel blue to visually indicate a choice page
        choicePage = true; //set the page to be a choice page
    }
    
    //makes the page a normal page
    public void makeNotChoice(){
            panel.setBackground(Color.gray);//color the panel grey to visually indicate a normal page
            choicePage = false; //set the page to be a normal page
        }
    
    //handles clicks of the "delete" button
    public void deleteButtonActionPerformed(Object e){
        parentPanel.remove(panel); //undraw the page's panel from the outline
        parentPage.storyPages.remove(this); //remove the page from the list of pages

        parentPanel.revalidate(); //redraw and scale the outline
        parentPanel.repaint();
        parentPage.snapPages(); //put the pages back in order
    }
    
    //handles clicks of the "edit" button
    public void editButtonActionPerformed(Object e){
        parentPage.editor.setVisible(true); //open the editor
        parentPage.editor.goToPage(parentPage.storyPages.indexOf(this)); //set the editor page to the page where the edit button was clicked
    }
    
    //gets the Y-location of the page's panel (for determining order)
    public int getY(){
        return panel.getY();
    }
    
    //sets the location of the panel based off of its order in the list of pages
    public void setOrder(int i){
        panel.setLocation(30, i*230+100);
        parentPanel.revalidate();
    }
}
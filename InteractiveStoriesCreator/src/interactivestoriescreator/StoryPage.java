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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author alexa
 */
public class StoryPage {
    JPanel panel;
    JLabel picture;
    File file;
    JPanel parentPanel;
    StartPage parentPage;
    
    boolean choicePage = false;
    
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
        //c.gridx = 0;
        //c.gridy = (story.size()-1)*2;
        panel.add(deleteButton);
        panel.add(picture);
        parentPanel.add(panel); //draw image
        //cm.registerComponent(picLabel);
        //cm.setSnapSize(new Dimension(40, 40));
        
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
    public void deleteButtonActionPerformed(Object e){
        JButton button = (JButton) e;
        
        JPanel panel = (JPanel) button.getParent();
        parentPanel.remove(panel);
        parentPage.storyPages.remove(this);
        
        //int i = parentPage.storyPanels.indexOf(panel);
        //parentPage.storyPanels.remove(i);
        //parentPage.story.remove(i);
        //parentPage.storyLabels.remove(i);
        
        parentPanel.revalidate();
        parentPanel.repaint();
        parentPage.snapPages();
    }
    
    public int getY(){
        return panel.getY();
    }
    
    public void setOrder(int i){
        //parentPage.story.remove(file);
        //parentPage.story.add(i,file);
            
        //parentPage.storyLabels.remove(picture);
        //parentPage.storyLabels.add(i,picture);
            
        //parentPage.storyPanels.remove(panel);
        //parentPage.storyPanels.add(i,panel);
            //highPanel.setBounds(100,i*200+100,300,169);
        panel.setLocation(30, i*230+100);
        parentPanel.revalidate();
    }
}

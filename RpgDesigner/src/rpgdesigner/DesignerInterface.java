package rpgdesigner;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.newdawn.slick.SlickException;
import playTest.GameInterface;
import rpgdesigner.iObjectList.ObjectType;

/**
 *
 * @author james
 * 
 * This class is the main interface class that puts all of the other interface
 * classes together in their own tabs.  
 */
public class DesignerInterface {
    
    
    iObjectList ActorList, MapList, EventList, ItemList;
    iSettings iSettings;
    Game game;
    JFrame frame;
    JOptionPane popup;
    private JPanel settingsTab;
    
    public DesignerInterface() throws IOException
    {
        try {
                //Lets change the gui to match the operating system for operating systems that don't do it manually
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(DesignerInterface.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InstantiationException ex) {
                Logger.getLogger(DesignerInterface.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(DesignerInterface.class.getName()).log(Level.SEVERE, null, ex);
            } catch (UnsupportedLookAndFeelException ex) {
                Logger.getLogger(DesignerInterface.class.getName()).log(Level.SEVERE, null, ex);
            }
        
            frame = new JFrame ("RPG Designer");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            BufferedImage logo = ImageIO.read(new File("Resources/logo.png"));
            frame.setIconImage(logo);
            game = new Game();
            
            //The Top Menu
            JMenuBar menu = new JMenuBar();
            JMenu fileMenu = new JMenu("File");
            JMenuItem saveMenu = new JMenuItem("Save");
            saveMenu.setEnabled(false);
            JMenuItem saveAsMenu = new JMenuItem("Save As");
            saveAsMenu.setActionCommand("save");
            saveAsMenu.addActionListener(new menuListener());
            JMenuItem exportMenu = new JMenuItem("Export");
            exportMenu.setEnabled(false);
            JMenuItem playTestMenu = new JMenuItem("Play Test");
            playTestMenu.setActionCommand("playtest");
            playTestMenu.addActionListener(new menuListener());
            JMenuItem exitMenu = new JMenuItem("Exit");
            exitMenu.setActionCommand("exit");
            exitMenu.addActionListener(new menuListener());
            fileMenu.add(saveMenu);
            fileMenu.add(saveAsMenu);
            fileMenu.add(exportMenu);
            fileMenu.add(playTestMenu);
            fileMenu.add(exitMenu);
            JMenu editMenu = new JMenu("Edit");
            JMenuItem copyMenu = new JMenuItem("Copy");
            copyMenu.setEnabled(false);
            JMenuItem cutMenu = new JMenuItem("Cut");
            cutMenu.setEnabled(false);
            JMenuItem pasteMenu = new JMenuItem("Paste");
            pasteMenu.setEnabled(false);
            editMenu.add(copyMenu);
            editMenu.add(cutMenu);
            editMenu.add(pasteMenu);
            JMenu helpMenu = new JMenu("Help");
            JMenuItem licenseMenu = new JMenuItem("License");
            licenseMenu.setEnabled(false);
            JMenuItem aboutMenu = new JMenuItem("About");
            aboutMenu.setActionCommand("about");
            aboutMenu.addActionListener(new menuListener());
            helpMenu.add(licenseMenu);
            helpMenu.add(aboutMenu);
            menu.add(fileMenu);
            menu.add(editMenu);
            menu.add(helpMenu);
            
            frame.setLayout(new BorderLayout());
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            game.actorList =  new ArrayList();
            game.eventList = new ArrayList();
            game.mapList = new ArrayList();
            game.itemList = new ArrayList();
           
            ActorList = new iObjectList(game.actorList, frame, ObjectType.ACTOR,  game);
            EventList = new iObjectList(game.eventList, frame, ObjectType.EVENT,   game);
            MapList = new iObjectList(game.mapList, frame, ObjectType.MAP,  game);
            ItemList = new iObjectList(game.itemList, frame, ObjectType.ITEM,  game);
            iSettings = new iSettings(game, frame);
            
            //Lets add our tabs
            JTabbedPane tabbedPane = new JTabbedPane();
            tabbedPane.addChangeListener(new TabChangeListener());
            tabbedPane.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
            JPanel actorTab = new JPanel();
            actorTab.add(ActorList);
            tabbedPane.addTab("Actors", actorTab);
            JPanel mapTab = new JPanel();
            mapTab.add(MapList);
            tabbedPane.addTab("Map Editor", mapTab);
            JPanel eventTab = new JPanel();
            eventTab.add(EventList);
            tabbedPane.addTab("Events", eventTab);
            JPanel itemTab = new JPanel();
            itemTab.add(ItemList);
            tabbedPane.addTab("Items", itemTab);
            settingsTab = new JPanel();
            settingsTab.add(iSettings);
            tabbedPane.addTab("Settings", settingsTab);

            
            frame.getContentPane().setLayout(new BorderLayout());
            JPanel content = new JPanel(new BorderLayout());
            content.add(tabbedPane, BorderLayout.NORTH);
            frame.getContentPane().add(menu, BorderLayout.NORTH);
            frame.getContentPane().add(content);
            frame.setPreferredSize(new Dimension(1146, 750));
            frame.pack();
            frame.setVisible(true);
    }
            
    public static void main(String[] args) { 
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    DesignerInterface di = new DesignerInterface();
                } catch (IOException ex) {
                    Logger.getLogger(DesignerInterface.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
    
    
    /*
     * This listener listens for changes in the menu
     */
    private class menuListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getActionCommand().equals("playtest")) {
                if(game.mapList.isEmpty())
                    JOptionPane.showMessageDialog(frame, "You must have at least 1 Map to playtest", 
                            "Cannot Continue", JOptionPane.ERROR_MESSAGE);
                else if (game.actorList.isEmpty())
                    JOptionPane.showMessageDialog(frame, "You must have at least 1 actor to playtest", 
                            "Cannot Continue", JOptionPane.ERROR_MESSAGE);    
                else {
                    try {
                        GameInterface playtest = new GameInterface(game);
                    } catch (SlickException ex) {
                        Logger.getLogger(DesignerInterface.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            } else if(e.getActionCommand().equals("exit")) {
                System.exit(0);
            } else if(e.getActionCommand().equals("license")) {
                
            } else if(e.getActionCommand().equals("about")) {
                Object toShow = "-RPG Designer-\n" + "Created by: Francine Wolfe and James Harris \n"
                        + "Version: Prototype";
                JOptionPane.showMessageDialog(frame, toShow, "About", JOptionPane.INFORMATION_MESSAGE);
            } else if(e.getActionCommand().equals("save")) {
                JFileChooser chooseDest = new JFileChooser();
                chooseDest.setDialogTitle("Select where to save the project folder");
                int returnVal = chooseDest.showDialog(frame, "Save Project To");
                if(returnVal == JFileChooser.APPROVE_OPTION) {
                    File dest = chooseDest.getCurrentDirectory();
                    try {
                        game.saveProject(dest);
                    } catch (IOException ex) {
                        Logger.getLogger(DesignerInterface.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            } else if(e.getActionCommand().equals("load")) {
                JFileChooser chooseDest = new JFileChooser();
                chooseDest.setDialogTitle("Select the projects folder");
                int returnVal = chooseDest.showDialog(frame, "Open Project");
                if(returnVal == JFileChooser.APPROVE_OPTION) {
                    File dest = chooseDest.getCurrentDirectory();
                    game.loadProject(dest);
                }
            } else
                System.out.println("Not Yet Implemented");
        }
    }
    
    /*
     * This listener updates the settings page with new data wehenever something changes
     * on a tab. (lets make sure we have an updated list of actors, maps, and events at all times)
     */
    private class TabChangeListener implements ChangeListener{

        @Override
        public void stateChanged(ChangeEvent e) {
            iSettings.updateMe();
        }   
}
}

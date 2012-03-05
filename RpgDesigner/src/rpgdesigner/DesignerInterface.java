package rpgdesigner;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import rpgdesigner.iObjectList.ObjectType;

/**
 *
 * @author james
 * 
 * This class is the main interface class that puts all of the other interface
 * classes together in their own tabs.  
 */
public class DesignerInterface {
    
    
    iObjectList ActorList, MapList, EventList;
    Game game;
    
    public DesignerInterface()
    {
        try {
                //Lets change the gui to match the operating system
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(RpgDesigner.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InstantiationException ex) {
                Logger.getLogger(RpgDesigner.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(RpgDesigner.class.getName()).log(Level.SEVERE, null, ex);
            } catch (UnsupportedLookAndFeelException ex) {
                Logger.getLogger(RpgDesigner.class.getName()).log(Level.SEVERE, null, ex);
            }
        
            JFrame frame = new JFrame ("RPG Designer");
            frame.setLayout(new BorderLayout());
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            //iActor fActor = new iActor(frame);
            iMap iMap = new iMap(frame, new Map());
            iSettings iSettings = new iSettings();
            
            //iActor ia = new iActor[0];
//            iMap[] im = new iMap[0];
//            iEvent[] ie = new iEvent[0];
            
            Actor a = new Actor();
            iActor ia = new iActor(frame, a);
            ArrayList<Object> alActor =  new ArrayList();
            ArrayList<Object> alEvent = new ArrayList();
            ArrayList<Object> alMap = new ArrayList();
           
            ActorList = new iObjectList((ArrayList<Object>)alActor, frame, ObjectType.ACTOR);
            //MapList = new iObjectList(im, frame, ObjectType.MAP);
            EventList = new iObjectList((ArrayList<Object>)alEvent, frame, ObjectType.EVENT);
            MapList = new iObjectList((ArrayList<Object>)alMap, frame, ObjectType.MAP);
            //EventList = new iObjectList(ie, frame, ObjectType.EVENT);
            //Lets add our tabs
            JTabbedPane tabbedPane = new JTabbedPane();
            tabbedPane.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
            JPanel actorTab = new JPanel();
            actorTab.add(ActorList);
            tabbedPane.addTab("Actors", actorTab);
            JPanel mapTab = new JPanel();
            mapTab.add(MapList);
            tabbedPane.addTab("Map Editor", mapTab);
//            JPanel settingsTab = new JPanel();
//            settingsTab.add(iSettings);
//            tabbedPane.addTab("Settings", settingsTab);
            JPanel eventsTab = new JPanel();
            eventsTab.add(EventList);
            tabbedPane.addTab("Events", eventsTab);

            
            frame.getContentPane().setLayout(new BorderLayout());
            frame.getContentPane().add(tabbedPane, BorderLayout.NORTH);
            
            frame.setPreferredSize(new Dimension(1146, 705));
            
            //frame.getContentPane().add(new JButton("Edit"), BorderLayout.SOUTH);
            frame.pack();
            //frame.setSize(500, 500);
            frame.setVisible(true);
            // TODO code application logic here
            
            
    
        
    }
            
    public static void main(String[] args) { 
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                DesignerInterface di = new DesignerInterface(); 
            }
        });
    }
}

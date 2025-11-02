package org.emp.gl.core.launcher;

import org.emp.gl.clients.CompteAReboursGUI;
import org.emp.gl.clients.HorlogeGUI;
import org.emp.gl.time.service.impl.DummyTimeServiceImpl;
import org.emp.gl.timer.service.TimerService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Interface graphique principale pour le gestionnaire de timer
 */
public class TimerGUI extends JFrame {
    
    private TimerService timerService;
    private List<HorlogeGUI> horloges;
    private List<CompteAReboursGUI> comptesARebours;
    
    private JPanel horlogesPanel;
    private JPanel comptesPanel;
    private JScrollPane horlogesScrollPane;
    private JScrollPane comptesScrollPane;
    
    private int horlogeCounter = 1;
    private int compteCounter = 1;
    
    public TimerGUI() {
        // Initialiser le service de timer
        timerService = new DummyTimeServiceImpl();
        horloges = new ArrayList<>();
        comptesARebours = new ArrayList<>();
        
        initGUI();
        
        // Créer quelques horloges et comptes par défaut
        ajouterHorloge();
        ajouterHorloge();
        ajouterCompteARebours(10);
    }
    
    private void initGUI() {
        setTitle("Gestionnaire de Timer - TP1");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        
        // Panneau principal
        JPanel mainPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Panneau gauche - Horloges
        JPanel leftPanel = new JPanel(new BorderLayout(5, 5));
        leftPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLoweredBevelBorder(), "Horloges"));
        
        horlogesPanel = new JPanel();
        horlogesPanel.setLayout(new BoxLayout(horlogesPanel, BoxLayout.Y_AXIS));
        horlogesPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        
        horlogesScrollPane = new JScrollPane(horlogesPanel);
        horlogesScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        horlogesScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        leftPanel.add(horlogesScrollPane, BorderLayout.CENTER);
        
        // Bouton pour ajouter une horloge
        JButton addHorlogeButton = new JButton("+ Ajouter une Horloge");
        addHorlogeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ajouterHorloge();
            }
        });
        leftPanel.add(addHorlogeButton, BorderLayout.SOUTH);
        
        // Panneau droit - Comptes à rebours
        JPanel rightPanel = new JPanel(new BorderLayout(5, 5));
        rightPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLoweredBevelBorder(), "Comptes à Rebours"));
        
        comptesPanel = new JPanel();
        comptesPanel.setLayout(new BoxLayout(comptesPanel, BoxLayout.Y_AXIS));
        comptesPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        
        comptesScrollPane = new JScrollPane(comptesPanel);
        comptesScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        comptesScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        rightPanel.add(comptesScrollPane, BorderLayout.CENTER);
        
        // Panneau pour ajouter un compte à rebours
        JPanel addComptePanel = new JPanel(new FlowLayout());
        JLabel valeurLabel = new JLabel("Valeur initiale:");
        JSpinner valeurSpinner = new JSpinner(new SpinnerNumberModel(10, 1, 999, 1));
        JButton addCompteButton = new JButton("+ Ajouter un Compte");
        addCompteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int valeur = (Integer) valeurSpinner.getValue();
                ajouterCompteARebours(valeur);
            }
        });
        addComptePanel.add(valeurLabel);
        addComptePanel.add(valeurSpinner);
        addComptePanel.add(addCompteButton);
        rightPanel.add(addComptePanel, BorderLayout.SOUTH);
        
        mainPanel.add(leftPanel);
        mainPanel.add(rightPanel);
        
        add(mainPanel, BorderLayout.CENTER);
        
        // Panneau de statut en bas
        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        statusPanel.setBorder(BorderFactory.createLoweredBevelBorder());
        JLabel statusLabel = new JLabel("Statut: Application en cours d'exécution");
        statusPanel.add(statusLabel);
        add(statusPanel, BorderLayout.SOUTH);
        
        // Configuration de la fenêtre
        setSize(900, 600);
        setLocationRelativeTo(null);
        setVisible(true);
        
        // Rafraîchir l'affichage périodiquement
        javax.swing.Timer refreshTimer = new javax.swing.Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                revalidate();
                repaint();
            }
        });
        refreshTimer.start();
    }
    
    private void ajouterHorloge() {
        HorlogeGUI horloge = new HorlogeGUI("Horloge " + horlogeCounter++, timerService);
        horloges.add(horloge);
        horlogesPanel.add(horloge);
        horlogesPanel.add(Box.createVerticalStrut(10));
        horlogesPanel.revalidate();
        horlogesPanel.repaint();
    }
    
    private void ajouterCompteARebours(int valeurInitiale) {
        CompteAReboursGUI compte = new CompteAReboursGUI(
            "Compte-" + compteCounter++, 
            timerService, 
            valeurInitiale
        );
        comptesARebours.add(compte);
        comptesPanel.add(compte);
        comptesPanel.add(Box.createVerticalStrut(10));
        comptesPanel.revalidate();
        comptesPanel.repaint();
    }
    
    public static void main(String[] args) {
        // Utiliser le look and feel système
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Créer l'interface graphique dans le thread EDT
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TimerGUI();
            }
        });
    }
}


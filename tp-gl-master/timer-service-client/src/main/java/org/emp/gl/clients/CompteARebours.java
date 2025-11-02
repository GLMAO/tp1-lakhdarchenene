package org.emp.gl.clients;

import org.emp.gl.timer.service.TimerChangeListener;
import org.emp.gl.timer.service.TimerService;

public class CompteARebours implements TimerChangeListener {

    private String name;
    private TimerService timerService;
    private int compte;
    private int initialValue;

    public CompteARebours(String name, TimerService timerService, int valeurInitiale) {
        this.name = name;
        this.timerService = timerService;
        this.compte = valeurInitiale;
        this.initialValue = valeurInitiale;
        
        // S'inscrire comme observer
        this.timerService.addTimeChangeListener(this);
        
        System.out.println("CompteARebours " + name + " initialisé avec " + compte);
    }

    @Override
    public void propertyChange(String propertyName, Object oldValue, Object newValue) {
        // Réagir seulement aux changements de secondes
        if (TimerChangeListener.SECONDE_PROP.equals(propertyName)) {
            decrementer();
        }
    }

    private synchronized void decrementer() {
        if (compte > 0) {
            compte--;
            System.out.println(name + " : " + compte);
            
            if (compte == 0) {
                System.out.println(name + " : Terminé!");
                // Se désinscrire automatiquement
                stop();
            }
        }
    }
    
    public void restart() {
        this.compte = initialValue;
        System.out.println(name + " redémarré avec " + compte);
    }
    
    // Méthode pour se désinscrire
    public synchronized void stop() {
        if (timerService != null) {
            timerService.removeTimeChangeListener(this);
            System.out.println(name + " désinscrit du timer service");
        }
    }
    
    public int getCompte() {
        return compte;
    }
}
package org.emp.gl.clients;

import org.emp.gl.timer.service.TimerChangeListener;
import org.emp.gl.timer.service.TimerService;

public class Horloge implements TimerChangeListener {

    private String name; 
    private TimerService timerService;

    public Horloge(String name, TimerService timerService) {
        this.name = name;
        this.timerService = timerService;
        
        // S'inscrire comme observer
        this.timerService.addTimeChangeListener(this);
        
        System.out.println("Horloge " + name + " initialized!");
    }

    @Override
    public void propertyChange(String propertyName, Object oldValue, Object newValue) {
        // Réagir seulement aux changements de secondes
        if (TimerChangeListener.SECONDE_PROP.equals(propertyName)) {
            afficherHeure();
        }
    }

    public void afficherHeure() {
        if (timerService != null) {
            System.out.println(name + " affiche " + 
                              timerService.getHeures() + ":" +
                              timerService.getMinutes() + ":" +
                              timerService.getSecondes());
        }
    }
    
    // Méthode pour se désinscrire
    public void stop() {
        if (timerService != null) {
            timerService.removeTimeChangeListener(this);
        }
    }
}
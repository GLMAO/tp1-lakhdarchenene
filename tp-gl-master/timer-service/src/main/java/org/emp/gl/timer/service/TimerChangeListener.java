package org.emp.gl.timer.service;

import java.beans.PropertyChangeListener;

public interface TimerChangeListener extends PropertyChangeListener {
    
    final static String DIXIEME_DE_SECONDE_PROP = "dixième";
    final static String SECONDE_PROP = "seconde";
    final static String MINUTE_PROP = "minute";
    final static String HEURE_PROP = "heure";
    
    // Cette méthode est appelée du TimeChangeProvider à chaque 
    // fois qu'il y a un changement sur l'une des propriétés de l'heure    
    void propertyChange(String prop, Object oldValue, Object newValue);
    
    // Implémentation par défaut pour PropertyChangeListener
    @Override
    default void propertyChange(java.beans.PropertyChangeEvent evt) {
        propertyChange(evt.getPropertyName(), evt.getOldValue(), evt.getNewValue());
    }
}
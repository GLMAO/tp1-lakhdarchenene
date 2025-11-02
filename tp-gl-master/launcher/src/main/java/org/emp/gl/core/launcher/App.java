package org.emp.gl.core.launcher;

import org.emp.gl.clients.Horloge;
import org.emp.gl.clients.CompteARebours;
import org.emp.gl.time.service.impl.DummyTimeServiceImpl;
import org.emp.gl.timer.service.TimerService;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class App {

    private static TimerService timerService;
    private static List<Horloge> horloges = new ArrayList<>();
    private static List<CompteARebours> comptesARebours = new ArrayList<>();

    public static void main(String[] args) {
        // Initialiser le service de timer
        timerService = new DummyTimeServiceImpl();
        
        testHorloges();
        
        // Attendre un peu avant de lancer les comptes à rebours
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        testComptesARebours();
    }

    private static void testHorloges() {
        System.out.println("=== TEST DES HORLOGES ===");
        
        // Créer plusieurs horloges
        horloges.add(new Horloge("Horloge 1", timerService));
        horloges.add(new Horloge("Horloge 2", timerService));
        horloges.add(new Horloge("Horloge 3", timerService));
        
        // Laisser tourner pendant 5 secondes
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        // Arrêter les horloges
        for (Horloge horloge : horloges) {
            horloge.stop();
        }
    }

    private static void testComptesARebours() {
        System.out.println("\n=== TEST DES COMPTES À REBOURS ===");
        
        // Test avec un compte à rebours simple
        CompteARebours compteSimple = new CompteARebours("Compte Simple", timerService, 5);
        
        // Attendre la fin du compte simple
        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        // Test avec plusieurs comptes à rebours
        System.out.println("\n=== TEST AVEC MULTIPLES COMPTES À REBOURS ===");
        Random random = new Random();
        
        for (int i = 0; i < 10; i++) {
            int valeurInitiale = 10 + random.nextInt(11); // Entre 10 et 20
            CompteARebours compte = new CompteARebours("Compte-" + (i+1), timerService, valeurInitiale);
            comptesARebours.add(compte);
        }
        
        // Laisser tourner assez longtemps pour que tous les comptes se terminent
        try {
            Thread.sleep(25000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.println("\n=== FIN DES TESTS ===");
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
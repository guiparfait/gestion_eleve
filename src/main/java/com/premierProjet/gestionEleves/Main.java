package com.premierProjet.gestionEleves;


import dao.EleveDAO;
import models.Eleve;

import java.util.List;
import java.util.Scanner;

public class Main { //test
    public static void main(String[] args) {
        EleveDAO eleveDAO = new EleveDAO();
        Scanner scanner = new Scanner(System.in);

        System.out.println("1. Ajouter un élève");
        System.out.println("2. Lister les élèves");
        int choix = scanner.nextInt();

        if (choix == 1) {
            System.out.print("Nom : ");
            String nom = scanner.next();
            System.out.print("Prénom : ");
            String prenom = scanner.next();
            System.out.print("Date de naissance (YYYY-MM-DD) : ");
            String dateNaissance = scanner.next();
            System.out.print("Classe : ");
            String classe = scanner.next();

            Eleve eleve = new Eleve(nom, prenom, dateNaissance, classe);
            eleveDAO.ajouterEleve(eleve);
        } else if (choix == 2) {
            List<Eleve> eleves = eleveDAO.listerEleves();
            for (Eleve eleve : eleves) {
                //affichage de la liste d'élèves
                System.out.println(eleve.getId() + ": " + eleve.getNom() + " " + eleve.getPrenom() + ", Classe: " + eleve.getClasse());
            }
        } else {
            System.out.println("Choix invalide.");
        }

        scanner.close();
    }
}

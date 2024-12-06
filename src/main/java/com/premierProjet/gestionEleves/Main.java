package com.premierProjet.gestionEleves;


import dao.EleveDAO;
import dao.NoteDAO;
import models.Eleve;
import models.Note;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        EleveDAO eleveDAO = new EleveDAO();
        NoteDAO noteDAO = new NoteDAO();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. Ajouter un élève");
            System.out.println("2. Lister les élèves");
            System.out.println("3. Ajouter une note");
            System.out.println("4. Lister les notes d'un élève");
            System.out.println("5. Calculer la moyenne d'un élève");
            System.out.println("6. Quitter");
            int choix = scanner.nextInt();

            switch (choix) {
                case 1:
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
                    break;

                case 2:
                    List<Eleve> eleves = eleveDAO.listerEleves();
                    for (Eleve e : eleves) {
                        System.out.println(e.getId() + ": " + e.getNom() + " " + e.getPrenom());
                    }
                    break;

                case 3:
                    System.out.print("ID de l'élève : ");
                    int eleveId = scanner.nextInt();
                    System.out.print("Matière : ");
                    String matiere = scanner.next();
                    System.out.print("Note(, pas . pour des notes avec virgule : ");
                    double note = scanner.nextDouble();
                    Note newNote = new Note(eleveId, matiere, note);
                    noteDAO.ajouterNote(newNote);
                    break;

                case 4:
                    int idEleve = demanderIdParNom(eleveDAO, scanner);
                    if (idEleve != -1) {
                        List<Note> notes = noteDAO.listerNotesParEleve(idEleve);
                        for (Note n : notes) {
                            System.out.println("Matiere : " + n.getMatiere() + ", Note : " + n.getNote());
                        }
                    }
                    break;


                case 5:
                    int id = demanderIdParNom(eleveDAO, scanner);
                    if (id != -1) {
                        noteDAO.afficherMoyenneAvecNom(id);
                    }
                    break;


                case 6:
                    System.out.println("Au revoir !");
                    scanner.close();
                    System.exit(0);

                default:
                    System.out.println("Choix invalide !");
            }
        }
    }

    public static int demanderIdParNom(EleveDAO eleveDAO, Scanner scanner) {
        System.out.print("Nom de l'élève : ");
        String nom = scanner.next();
        System.out.print("Prénom de l'élève : ");
        String prenom = scanner.next();

        List<Eleve> eleves = eleveDAO.rechercherParNomEtPrenom(nom, prenom);

        if (eleves.isEmpty()) {
            System.out.println("Aucun élève trouvé avec ce nom et prénom.");
            return -1; // Indique qu'aucun élève n'a été trouvé
        } else if (eleves.size() == 1) {
            Eleve eleve = eleves.get(0);
            System.out.println("Élève trouvé : ID = " + eleve.getId() + ", Nom = " + eleve.getNom() + ", Prénom = " + eleve.getPrenom());
            return eleve.getId();
        } else {
            System.out.println("Plusieurs élèves trouvés :");
            for (Eleve e : eleves) {
                System.out.println("ID = " + e.getId() + ", Nom = " + e.getNom() + ", Prénom = " + e.getPrenom() + ", Classe = " + e.getClasse());
            }
            System.out.print("Veuillez saisir l'ID de l'élève souhaité : ");
            return scanner.nextInt(); // L'utilisateur choisit l'ID
        }
    }

}

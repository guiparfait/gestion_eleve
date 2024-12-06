package dao;

import models.Note;
import utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NoteDAO {
    public void ajouterNote(Note note) {
    if (note.getNote() < 0 || note.getNote() > 10) {
        System.out.println("Erreur : La note doit être comprise entre 0 et 10. tchrooou (-_-)");
        return;

        }
        String sql = "INSERT INTO notes (eleve_id,matiere,note) values (?,?,?) ";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, note.getEleveID());
            stmt.setString(2, note.getMatiere());
            stmt.setDouble(3, note.getNote());
            stmt.executeUpdate();
            System.out.println("Note ajoutée avec succès !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout de la note : " + e.getMessage());
        }
    }

    public List<Note> listerNotesParEleve(int eleveId) {
        List<Note> notes = new ArrayList<>();
        String sql = "SELECT * FROM notes WHERE eleve_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, eleveId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Note note = new Note(
                        rs.getInt("eleve_id"),
                        rs.getString("matiere"),
                        rs.getDouble("note")
                );
                note.setId(rs.getInt("id"));
                notes.add(note);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des notes : " + e.getMessage());
        }
        return notes;
    }

    public double calculerMoyenne(int eleveId) {
        String sql = "SELECT AVG(note) AS moyenne FROM notes WHERE eleve_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, eleveId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble("moyenne");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors du calcul de la moyenne : " + e.getMessage());
        }
        return 0.0;
    }
    public void afficherMoyenneAvecNom(int eleveId) {
        String sql = "SELECT e.nom, e.prenom, AVG(n.note) AS moyenne " +
                "FROM eleves e " +
                "LEFT JOIN notes n ON e.id = n.eleve_id " +
                "WHERE e.id = ? " +
                "GROUP BY e.id, e.nom, e.prenom";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, eleveId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String nom = rs.getString("nom");
                String prenom = rs.getString("prenom");
                double moyenne = rs.getDouble("moyenne");

                // Si l'élève n'a pas de notes, la moyenne sera nulle
                if (rs.wasNull()) {
                    System.out.println("L'élève " + nom + " " + prenom + " n'a pas encore de notes.");
                } else {
                    System.out.println("Nom : " + nom + ", Prénom : " + prenom);
                    System.out.println("Moyenne(sur 10) : " + Math.round(moyenne * 100.0) / 100.0); // Arrondi à 2 décimales
                }
            } else {
                System.out.println("Élève introuvable.");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'affichage de la moyenne : " + e.getMessage());
        }
    }


}


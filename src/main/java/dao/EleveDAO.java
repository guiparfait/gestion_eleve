package dao;

import models.Eleve;
import utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
//test
public class EleveDAO {
    public void ajouterEleve(Eleve eleve) {
        String sql = "INSERT INTO eleves (nom, prenom, date_naissance, classe) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, eleve.getNom());
            stmt.setString(2, eleve.getPrenom());
            stmt.setString(3, eleve.getDateNaissance());
            stmt.setString(4, eleve.getClasse());
            stmt.executeUpdate();
            System.out.println("Élève ajouté avec succès !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout de l'élève : " + e.getMessage());
        }
    }

    public List<Eleve> listerEleves() {
        List<Eleve> eleves = new ArrayList<>();
        String sql = "SELECT * FROM eleves";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Eleve eleve = new Eleve(
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("date_naissance"),
                        rs.getString("classe")
                );
                eleve.setId(rs.getInt("id"));
                eleves.add(eleve);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des élèves : " + e.getMessage());
        }
        return eleves;
    }
    public List<Eleve> rechercherParNomEtPrenom(String nom, String prenom) {
        String sql = "SELECT * FROM eleves WHERE nom = ? AND prenom = ?";
        List<Eleve> eleves = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nom);
            stmt.setString(2, prenom);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                eleves.add(new Eleve(rs.getInt("id"), rs.getString("nom"),
                        rs.getString("prenom"), rs.getString("date_naissance"),
                        rs.getString("classe")));
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la recherche : " + e.getMessage());
        }
        return eleves;
    }


}


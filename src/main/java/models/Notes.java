package models;

public class Notes {
    private int id;
    private int eleve_ID;
    private String matiere;
    private double note;

    //constructeurs
    public Notes (int eleve_ID, String matiere, double note){
        this.eleve_ID = eleve_ID;
        this.matiere = matiere;
        this.note = note;
    }

}

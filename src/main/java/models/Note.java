package models;

public class Note {
    private int id;
    private int eleveID;
    private String matiere;
    private double note;
    //test
    //constructeurs
    public Note(int eleveID, String matiere, double note){
        this.eleveID = eleveID;
        this.matiere = matiere;
        this.note = note;
    }

    // Getters et Setters


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEleveID() {
        return eleveID;
    }

    public void setEleveID(int eleveID) {
        this.eleveID = eleveID;
    }

    public String getMatiere(){
        return matiere;
    }

    public void setMatiere (String matiere){
        this.matiere = matiere;
    }

    public double getNote() {
        return note;
    }

    public void setNote(double note) {
        this.note = note;
    }

}

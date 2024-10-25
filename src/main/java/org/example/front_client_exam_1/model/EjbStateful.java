package org.example.front_client_exam_1.model;

import jakarta.ejb.Remote;

import java.util.List;

@Remote
public interface EjbStateful {
    public void ajouterCD(String title, String artist);
    public void supprimerCD(String title, String artist);
    public void listerCD();
    public List<CD> getCDs();
}

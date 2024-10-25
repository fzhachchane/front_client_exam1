package org.example.front_client_exam_1.controller;

import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import org.example.front_client_exam_1.model.CD;

import org.example.hello_world_ejb_2.beans.EjbStateless;
import org.example.hello_world_ejb_2.beans.EjbStateful;
import org.example.hello_world_ejb_2.model.CD;

import java.io.Serializable;
import java.util.List;


@Named
@RequestScoped
public class CdController {

    @EJB(beanName = "EjbStatelessBean")
    private EjbStateless ejbStateless;

    @EJB(beanName = "EjbStatefulBean")
    private EjbStateful ejbStateful;

    private String title;
    private String artist;

    // Getters and Setters for title and artist
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    // Get the list of available CDs
    public List<CD> getAvailableCDs() {
        return ejbStateful.getCDs();
    }

    // Method to borrow a CD
    public String emprunterCD() {
        ejbStateless.pretCD(title, artist);
        return "cds?faces-redirect=true";
    }

    // Method to return a CD
    public String retournerCD() {
        ejbStateless.retourCD(title, artist);
        return "cds?faces-redirect=true";
    }
}

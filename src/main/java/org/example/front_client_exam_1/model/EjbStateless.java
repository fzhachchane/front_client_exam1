package org.example.front_client_exam_1.model;

import jakarta.ejb.Remote;

@Remote
public interface EjbStateless {
    public void pretCD(String title, String artist);
    public void retourCD(String title, String artist);
}

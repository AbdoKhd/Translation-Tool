package com.csis231.api.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Table(name = "saved")
@Entity
public class Saved {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long savedId;
    
    @ManyToOne
    @JoinColumn(name = "id")
    private User id;
    
    @NotEmpty
    private String savedTypeHere;
    
    @NotEmpty
    private String savedTranslation;
    
    public Saved() {

    }

    public Saved(long savedId, User id, String savedTypeHere, String savedTranslation) {
        super();
        this.savedId = savedId;
        this.id = id;
        this.savedTypeHere = savedTypeHere;
        this.savedTranslation = savedTranslation;
    }
    
    public long getSavedId() {
        return savedId;
    }
    public void setSavedId(long savedId) {
        this.savedId = savedId;
    }
    public User id() {
        return id;
    }
    public void setId(User id) {
        this.id = id;
    }
    public String getSavedTypeHere() {
        return savedTypeHere;
    }
    public void setSavedTypeHere(String savedTypeHere) {
        this.savedTypeHere = savedTypeHere;
    }
    public String getSavedTranslation() {
        return savedTranslation;
    }
    public void setSavedTranslation(String savedTranslation) {
        this.savedTranslation = savedTranslation;
    }
}

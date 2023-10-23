/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
/**
 *
 * @author abdelrahmankhodr
 */
public class Saved {
    
    private final SimpleIntegerProperty savedId = new SimpleIntegerProperty(0);
    private final SimpleIntegerProperty id = new SimpleIntegerProperty(0);
    private final SimpleStringProperty savedTypeHere = new SimpleStringProperty("");
    private final SimpleStringProperty savedTranslation = new SimpleStringProperty("");
    
    public Saved() {
        this(0, 0, "", "");
    }

    public Saved(int savedId, int id, String savedTypeHere, String savedTranslation) {
        setSavedId(savedId);
        setId(id);
        setSavedTypeHere(savedTypeHere);
        setSavedTranslation(savedTranslation);
    }
    
    public int getSavedId(){
        return savedId.get();
    }
    public void setSavedId(int History_id){
        savedId.set(History_id);
    }
    
    public int getId(){
        return id.get();
    }
    public void setId(int Id){
        id.set(Id);
    }
    
    public String getSavedTypeHere() {
        return savedTypeHere.get();
    }

    public void setSavedTypeHere(String History_typeHere) {
        savedTypeHere.set(History_typeHere);
    }

    public String getSavedTranslation() {
        return savedTranslation.get();
    }

    public void setSavedTranslation(String History_translation) {
        savedTranslation.set(History_translation);
    }

}


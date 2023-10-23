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
public class History {
    
    private final SimpleIntegerProperty history_id = new SimpleIntegerProperty(0);
    private final SimpleIntegerProperty id = new SimpleIntegerProperty(0);
    private final SimpleStringProperty history_typeHere = new SimpleStringProperty("");
    private final SimpleStringProperty history_translation = new SimpleStringProperty("");
    
    public History() {
        this(0, 0, "", "");
    }

    public History(int history_id, int id, String history_typeHere, String history_translation) {
        setHistoryId(history_id);
        setId(id);
        setHistoryTypeHere(history_typeHere);
        setHistoryTranslation(history_translation);
    }
    
    public int getHistoryId(){
        return history_id.get();
    }
    public void setHistoryId(int History_id){
        history_id.set(History_id);
    }
    
    public int getId(){
        return id.get();
    }
    public void setId(int Id){
        id.set(Id);
    }
    
    public String getHistoryTypeHere() {
        return history_typeHere.get();
    }

    public void setHistoryTypeHere(String History_typeHere) {
        history_typeHere.set(History_typeHere);
    }

    public String getHistoryTranslation() {
        return history_translation.get();
    }

    public void setHistoryTranslation(String History_translation) {
        history_translation.set(History_translation);
    }

}

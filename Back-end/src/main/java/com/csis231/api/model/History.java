
package com.csis231.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Table(name = "history")
@Entity
public class History {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long historyId;
    
    @ManyToOne
    @JoinColumn(name = "id")
    private User id;
    
    @NotEmpty
    private String historyTypeHere;
    
    @NotEmpty
    private String historyTranslation;
    
    public History() {

    }

    public History(long historyId, User id, String historyTypeHere, String historyTranslation) {
        super();
        this.historyId = historyId;
        this.id = id;
        this.historyTypeHere = historyTypeHere;
        this.historyTranslation = historyTranslation;
    }
    
    public long getHistoryId() {
        return historyId;
    }
    public void setHistoryId(long historyId) {
        this.historyId = historyId;
    }
    public User id() {
        return id;
    }
    public void setId(User id) {
        this.id = id;
    }
    public String getHistoryTypeHere() {
        return historyTypeHere;
    }
    public void setHistoryTypeHere(String historyTypeHere) {
        this.historyTypeHere = historyTypeHere;
    }
    public String getHistoryTranslation() {
        return historyTranslation;
    }
    public void setSavedTranslation(String historyTranslation) {
        this.historyTranslation = historyTranslation;
    }
}

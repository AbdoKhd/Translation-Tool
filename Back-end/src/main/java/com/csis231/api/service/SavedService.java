/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.csis231.api.service;

import com.csis231.api.model.History;
import com.csis231.api.model.Saved;
import com.csis231.api.model.User;
import com.csis231.api.repository.SavedRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author abdelrahmankhodr
 */
@Service
public class SavedService {
    @Autowired
    private SavedRepository savedRepository;
    
    public List<Saved> getSaved(int id) {
        
        User user = new User();
        user.setId(id);
        List<Saved> saved = savedRepository.findSavedByUser(user);
        return saved;
    }
    
    public Saved createSave(Saved saved){
        return savedRepository.save(saved);
    }
    
    
    public void deleteSave(Saved saved){
        System.out.println("This is the savedId " + saved.getSavedId());
        savedRepository.delete(saved);
    }
    
}

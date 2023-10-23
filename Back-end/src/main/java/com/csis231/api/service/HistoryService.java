/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.csis231.api.service;

import com.csis231.api.model.History;
import com.csis231.api.model.User;
import com.csis231.api.repository.HistoryRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author abdelrahmankhodr
 */
@Service
public class HistoryService {
    @Autowired
    private HistoryRepository historyRepository;
    
    public List<History> getHistory(int id) {
        //return historyRepository.findAll();
        User user = new User();
        user.setId(id);
        List<History> history = historyRepository.findHistoryByUser(user);
        return history;
    }
    
    public void createHistory(History history){
        historyRepository.save(history);
    }
    
    public void clearHistory(User user){
        //long userId = user.getId();
        List<History> history = historyRepository.findHistoryByUser(user);
        historyRepository.deleteAll(history);
    }
}


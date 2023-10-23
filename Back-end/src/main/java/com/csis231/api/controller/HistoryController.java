/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.csis231.api.controller;

import com.csis231.api.model.History;
import com.csis231.api.model.User;
import com.csis231.api.service.HistoryService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author abdelrahmankhodr
 */

@RestController
@RequestMapping("/api/")
public class HistoryController {
    @Autowired
    private HistoryService historyService;
    
    @GetMapping("getHistory")
    public ResponseEntity<List<History>> getHistory(int id) {
        List<History> history = historyService.getHistory(id);
//        return new ResponseEntity<>(history, HttpStatus.OK);
        return ResponseEntity.status(HttpStatus.OK).body(history);
    }
    
    @PostMapping("history")
    public ResponseEntity<String> createHistory(@RequestBody History history){
        System.out.println("id :" + history.id());
        System.out.println("typeHere :" + history.getHistoryTypeHere());
        System.out.println("translation :" + history.getHistoryTranslation());
        historyService.createHistory(history);
        return ResponseEntity.status(HttpStatus.OK).body("History entry created.");
    }
    
    @DeleteMapping("clearHistory")
    public ResponseEntity<String> clearHistory(@RequestBody User user){
        System.out.println("this is id cmon: " + user.getId());
        //System.out.println(user.getUsername());
        historyService.clearHistory(user);
        return ResponseEntity.status(HttpStatus.OK).body("history cleared.");
    }

}




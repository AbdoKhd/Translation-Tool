/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.csis231.api.controller;

import com.csis231.api.model.Saved;
import com.csis231.api.service.SavedService;
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
public class SavedController {
    @Autowired
    private SavedService savedService;
    
    @PostMapping("save")
    public ResponseEntity<Saved> createSave(@RequestBody Saved saved){
        System.out.println("id :" + saved.id());
        System.out.println("typeHere :" + saved.getSavedTypeHere());
        System.out.println("translation :" + saved.getSavedTranslation());
        Saved savedTrans = savedService.createSave(saved);
        return new ResponseEntity<Saved>(savedTrans, HttpStatus.CREATED);
    }
    
    @GetMapping("getSaved")
    public ResponseEntity<List<Saved>> getSaved(int id){
        List<Saved> saved = savedService.getSaved(id);
//        return new ResponseEntity<>(history, HttpStatus.OK);
        return ResponseEntity.status(HttpStatus.OK).body(saved);
    }
    
    @DeleteMapping("deleteSave")
    public ResponseEntity<String> deleteSave(@RequestBody Saved saved){
        savedService.deleteSave(saved);
        return ResponseEntity.status(HttpStatus.OK).body("saved entry deleted.");
    }
}

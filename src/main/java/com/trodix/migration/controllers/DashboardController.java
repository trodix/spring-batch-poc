package com.trodix.migration.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    /**
     * Permet d'afficher l'application Angular
     * 
     * @return Le fichier d'entrée de l'application Angular
     */
    @GetMapping("/")
    public String index() {
        return "index.html";
    }

}

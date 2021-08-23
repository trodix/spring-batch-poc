package com.trodix.migration.core.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

    /**
     * Match everything without a suffix (so not a static resource)
     */
    @RequestMapping("/**/{path:[^.]*}")       
    public String proxy() {
        return "forward:/";
    }

}

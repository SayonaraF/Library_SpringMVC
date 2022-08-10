package ru.broyaka.library.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/library")
public class MainController {

    @GetMapping()
    public String index() {
        return "startPage";
    }

}

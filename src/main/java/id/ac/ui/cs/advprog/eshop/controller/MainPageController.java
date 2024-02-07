package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
public class MainPageController {
    @Autowired
    private ProductService service;

    @GetMapping("")
    public String mainPage(Model model) {
        return "welcomePage";
    }
}

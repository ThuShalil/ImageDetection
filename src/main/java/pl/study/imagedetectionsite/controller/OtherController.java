package pl.study.imagedetectionsite.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class OtherController {

    @GetMapping("/help")
    public String helpSite(Model model)
    {
        return "help";
    }

    @GetMapping("/contact")
    public String contactSite(Model model)
    {
        return "contact";
    }
}

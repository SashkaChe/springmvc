package ru.neutrino.web;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.neutrino.domain.People;
import ru.neutrino.service.PeopleService;



@Controller
public class WebController {

    @Autowired
    PeopleService peopleservice;

    @GetMapping("/")
    public String hello(Model model) {
        model.addAttribute("people", peopleservice.findAll());
        return "index";
    }

    @GetMapping("/{id}")
    public String user(@PathVariable("id") long id, Model model) {

        People obj = peopleservice.findById(id);

        if (obj == null) {
            return "empty";
        }

        model.addAttribute("single", obj);
        return "users";
    }

    @GetMapping("/new")
    public String newuser(Model model) {
        model.addAttribute("newpeople", new People());
        return "adduser";
    }

    @GetMapping("/{id}/edit")
    public String edituser(@PathVariable("id") long id, Model model) {


        People obj = peopleservice.findById(id);

        if (obj == null) {
            return "empty";
        }

        model.addAttribute("editpeople", obj);
        return "edituser";
    }

    @PostMapping()
    public String save(@ModelAttribute("newpeople") @Valid People newpeople, BindingResult br) {

        if(br.hasErrors()) return "adduser";

      //  peopleservice.saveAndFlush(newpeople);
       return "redirect:/";


    }



    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") long id) {
        peopleservice.delete(peopleservice.findById(id));
        return "redirect:/";
    }

    @PostMapping("/{id}")
    public String edit(@ModelAttribute("editpeople") People people, @PathVariable("id") long id) {

        People obj = peopleservice.findById(id);

        obj.setName(people.getName());
        obj.setPhone(people.getPhone());

        peopleservice.saveAndFlush(obj);
        return "redirect:/";
    }


}




package ru.esplit.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import ru.esplit.dao.PersonDAO;
import ru.esplit.dao.PersonDAOImpl;
import ru.esplit.models.Person;

@Controller
@RequestMapping("/people")
public class PeopleController {
    private PersonDAO personDAO;

    @Autowired
    public PeopleController(PersonDAOImpl personDAO) {
        this.personDAO = personDAO;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("people", personDAO.getAll());
        return "people/getall";
    }

    @GetMapping("/{person_id}")
    public String show(@PathVariable("person_id") int person_id, Model model) {
        Optional<Person> personO = personDAO.show(person_id);
        if (personO.isPresent()) {
            model.addAttribute("person", personO.get());
            return "people/show";
        } else {
            return "redirect:/people";
        }
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Person person) {
        return "people/new";
    }

    @PatchMapping()
    public String create(@ModelAttribute("person") Person person, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "people/new";
        }
        personDAO.save(person);
        return "redirect:/people";
    }

    @GetMapping("/{person_id}/edit")
    public String edit(@PathVariable("person_id") int person_id, Model model) {
        Optional<Person> personO = personDAO.show(person_id);
        if (personO.isPresent()){
            model.addAttribute("person", personO.get());
            return "people/edit";
        } else {
            return "redirect:/people";
        }
        
    }

    @PatchMapping("/{person_id}")
    public String update(@ModelAttribute("person") Optional<Person> person, 
            BindingResult bindingResult, @PathVariable("person_id") int person_id) {
        if(bindingResult.hasErrors()) {
            return "people/edit";
        }
        personDAO.update(person_id, person);
        return "redirect:/people";
    }

    @DeleteMapping("/{person_id}")
    public String delete(@PathVariable("person_id") int person_id) {
        personDAO.delete(person_id);
        return "redirect:/people";
    }
}

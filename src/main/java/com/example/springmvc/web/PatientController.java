package com.example.springmvc.web;


import com.example.springmvc.entities.Patient;
import com.example.springmvc.repositories.PatientRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@AllArgsConstructor
public class PatientController {
    PatientRepository patientRepository;
    @GetMapping("/index")
    public String index(Model model, @RequestParam(name = "page", defaultValue = "0") int p,
                        @RequestParam(name = "size", defaultValue = "4") int s,
                        @RequestParam(name = "keyword", defaultValue = "") String kw){
        Page<Patient> pagePatient = patientRepository.findByNomContains(kw,PageRequest.of(p,s));
        model.addAttribute("listPatient", pagePatient.getContent());
        model.addAttribute("pages", new int[pagePatient.getTotalPages()]);
        model.addAttribute("currentPage", p);
        model.addAttribute("keyword", kw);
        return "patients";
    }

    @GetMapping("/delete")
    public String delete(Model model, Long id, String keyword, int page){
            patientRepository.deleteById(id);
        return "redirect:/index?page="+page+"&keyword="+ keyword;
    }

    @GetMapping("/")
    public String home(Model model){
        return "redirect:/index";
    }
}

package com.example.springbootwiththymleaf.controller;

import com.example.springbootwiththymleaf.service.PetClinicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller

public class PetClinicController {

    @Autowired
    private PetClinicService petClinicService;

    @RequestMapping("/owners")
    public ModelAndView getOwners(){
        ModelAndView mav=new ModelAndView();
        mav.addObject("owners",petClinicService.findOwners());
        mav.setViewName("owners");
        return mav;
    }

}

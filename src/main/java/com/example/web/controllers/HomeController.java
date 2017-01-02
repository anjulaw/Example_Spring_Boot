package com.example.web.controllers;

import com.example.domain.User;
import com.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by anjulaw on 11/21/2016.
 */
@Controller
public class HomeController {

    @Autowired
    Environment env;

    @Autowired
    UserRepository userRepository;

    @RequestMapping("/")
    public String index(Model model, HttpServletRequest request, HttpServletResponse response) {

        List<String>  userList = new ArrayList<>();
        userList.add("Anjula");
        userList.add("chaminda");


        model.addAttribute("userList",userList);

        model.addAttribute("Name","Anjula");

        return "index";
    }

    @RequestMapping("/getUser")
    public String test(Model model, HttpServletRequest request, HttpServletResponse response){

        return "users";
    }

    @RequestMapping("/addUser")
    public String addUser(@ModelAttribute("SpringWeb")User userObject, Model model, HttpServletRequest request, HttpServletResponse response){


        userRepository.save(userObject);

        return "users";
    }

}

package com.example.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    @RequestMapping("/")
    public String index(Model model, HttpServletRequest request, HttpServletResponse response) {

        List<String>  userList = new ArrayList<>();
        userList.add("Anjula");
        userList.add("chaminda");


        model.addAttribute("userList",userList);

        return "index";
    }

    @RequestMapping("/getUser")
    public String test(Model model, HttpServletRequest request, HttpServletResponse response){

        return "users";
    }
}

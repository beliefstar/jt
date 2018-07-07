package com.jtboot.manager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    @RequestMapping("/page/{module}")
    public String toItemAdd(@PathVariable String module) {
        return module;
    }

    @RequestMapping({"/index", "/"})
    public String index() {
        return "index";
    }
}

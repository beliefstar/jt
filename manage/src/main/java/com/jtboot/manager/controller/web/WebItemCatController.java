package com.jtboot.manager.controller.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jtboot.common.vo.ItemCatResult;
import com.jtboot.manager.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class WebItemCatController {

    @Autowired
    private ItemCatService itemCatService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @RequestMapping("/web/itemcat/all")
    @ResponseBody
    public void findItemCatAll(String callback, HttpServletResponse res) {
        ItemCatResult rel = itemCatService.findItemCatAll();
        String relStr = "";
        try {
            String str = objectMapper.writeValueAsString(rel);
            relStr = callback + "(" + str + ")";
            res.setContentType("application/json;charset=utf-8");
            res.getWriter().write(relStr);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

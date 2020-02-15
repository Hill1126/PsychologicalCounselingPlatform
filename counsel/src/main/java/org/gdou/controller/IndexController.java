package org.gdou.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author HILL
 * @version V1.0
 * @date 2020/2/6
 **/
@Controller
@Slf4j
public class IndexController {

    @RequestMapping("/index")
    @ResponseBody
    public String index(){

        return "helloWorld！！";
    }

}

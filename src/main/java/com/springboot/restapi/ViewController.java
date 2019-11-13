package com.springboot.restapi;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Description:
 * @Package: com.springboot.restapi
 * @Author: denglt
 * @Date: 2019-11-11 12:21
 * @Copyright: 版权归 HSYUNTAI 所有
 */

@Controller
public class ViewController {

    @RequestMapping("/jspwebsocket")
    ModelAndView jspWebsocket(){
        ModelAndView view = new ModelAndView();
        view.setViewName("jsp/websocket");
        return view;
    }

    @RequestMapping("/htmlwebsocket")
    ModelAndView htmlWebsocket(){
        ModelAndView view = new ModelAndView();
        view.setViewName("html/websocket");
        return view;
    }
}

package lxpsee.top.eshop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * The world always makes way for the dreamer
 * Created by 努力常态化 on 2018/12/23 21:18.
 */
@Controller
public class HomeController {

    @RequestMapping("/home")
    public String toHome() {
        return "index";
    }
}

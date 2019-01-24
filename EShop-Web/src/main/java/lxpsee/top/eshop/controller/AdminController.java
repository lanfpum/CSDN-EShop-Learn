package lxpsee.top.eshop.controller;

import lxpsee.top.eshop.domain.User;
import lxpsee.top.eshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * The world always makes way for the dreamer
 * Created by 努力常态化 on 2018/12/24 10:06.
 */
@Controller
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping("/admin/userlist")
    public String findAllUsers(Model model) {
        List<User> userList = userService.findAllEntities();
        model.addAttribute("allUsers", userList);
        return "userList";
    }

    /**
     * 注意点：重定向
     */
    @GetMapping("/admin/delUser")
    public String deleteUserById(@RequestParam("uid") Integer uid) {
        User user = new User();
        user.setId(uid);
        userService.deleteEntity(user);
        return "redirect:/admin/userlist";
    }

    @GetMapping("/admin/viewUser")
    public String showUserInfo(@RequestParam("uid") Integer uid, Model model) {
        User user = userService.getEntity(uid);
        model.addAttribute("user", user);
        return "viewUser";
    }

    @GetMapping("/admin/editUser")
    public String getEditUser(@RequestParam("uid") Integer uid, Model model) {
        User user = userService.getEntity(uid);
        model.addAttribute("user", user);
        return "editUser";
    }

    @PostMapping("/admin/updateUser")
    public String editUser(User user) {
        userService.updateEntity(user);
        return "redirect:/admin/userlist";
    }

}

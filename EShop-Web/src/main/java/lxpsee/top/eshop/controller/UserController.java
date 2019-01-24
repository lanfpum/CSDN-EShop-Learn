package lxpsee.top.eshop.controller;

import lxpsee.top.eshop.domain.User;
import lxpsee.top.eshop.service.UserService;
import lxpsee.top.eshop.utils.ValidateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * The world always makes way for the dreamer
 * Created by 努力常态化 on 2018/12/24 08:25.
 */
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/toRegPage")
    public String toRegPage() {
        return "userReg";
    }

    /**
     * 完成注册
     * 1.得到确认密码，判断密码是否一致
     * 2.判断email是否唯一
     * 3.保存用户
     */
    @PostMapping("/doReg")
    public String doReg(User user, HttpServletRequest request, Model model) {
        String confirmPass = request.getParameter("confirmPass");

        if (!confirmPass.equals(user.getPassword())) {
            model.addAttribute("error.password.nosame", "两次密码输入不一致，确认后请重新输入!!");
            return "userReg";
        }

        boolean b = userService.isEmailRegisted(user.getEmail());

        if (b) {
            model.addAttribute("error.email.registed", "邮箱已经注册!");
            return "userReg";
        }

        userService.saveEntity(user);
        return "login";
    }

    /**
     * user:封装的客户端提交的user信息
     * session : session对象，用来保存登录成功的用户名。
     * model : 登录失败，向客户端回传失败信息的载体。
     */
    @PostMapping("/doLogin")
    public String doLogin(User user, HttpSession session, Model model) {
        String hql = "from User u where u.name = ? and u.password = ?";
        List<User> users = userService.findByHQL(hql, user.getName(), user.getPassword());

        if (ValidateUtil.isValid(users)) {
            User findUser = users.get(0);
            session.setAttribute("name", findUser.getName());
        } else {
            model.addAttribute("error", "用户名/密码验证失败,请重试!!");
        }

        return "index";
    }


}

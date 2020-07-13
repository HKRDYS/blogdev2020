package com.example.demo.web;

import com.example.demo.dao.IUserMapper;
import com.example.demo.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * 用户相关控制器
 *
 */
@Controller
public class UserController {
    @Autowired
    private IUserMapper iUserMapper;

    @RequestMapping("/myLogin")
    public ModelAndView myLogin(HttpServletRequest request) {
        System.out.println("登陆成功");
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println(userDetails.getUsername());
        User user = iUserMapper.findByLoginName(userDetails.getUsername());
        request.getSession().setAttribute("user", user);
        Collection conllection = (Collection) userDetails.getAuthorities();
        Iterator<GrantedAuthority> iterator = conllection.iterator();
        List<String> list = new ArrayList<String>();
        while (iterator.hasNext()) {
            GrantedAuthority grantedAuthority = iterator.next();
            list.add(grantedAuthority.getAuthority());
            System.out.println(grantedAuthority.getAuthority());
        }
        if (list != null && list.contains("ROLE_ADMIN")) {
//            return "/admin/home";
            return new ModelAndView("forward:/user/home");//转身后面页面映射路径"/user/home"
        } else {
//            return "/guest/index";
            return new ModelAndView("forward:/");//转向首页映射的路径"/"
        }
    }
    //用户个人信息,用户和管理员使用一个后台，不分开做，在页面中做权限控制
    @RequestMapping("/user/home")
    public String backHome(HttpServletRequest request){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println(userDetails.getUsername());
        User user = iUserMapper.findByLoginName(userDetails.getUsername());
        request.getSession().setAttribute("user", user);
        return "/admin/home";
    }

}

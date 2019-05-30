package com.itheima.web;

import com.itheima.domian.system.Module;
import com.itheima.domian.system.User;
import com.itheima.service.system.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class LoginController extends BaseController{
    @Autowired
    private UserService userService;

	@RequestMapping("/login")
	public String login(String email,String password) {
	   /* User user=userService.findByEmail(email);
	    if(user==null||!user.getPassword().equals(password)){
	        request.setAttribute("error","用户名或者密码不能为空");
	        return "forward:login.jsp";
        }
        session.setAttribute("user",user);
	    List<Module> moduleList=userService.findModuleByUserId(user.getId());
	    session.setAttribute("modules",moduleList);*/
        if(StringUtils.isEmpty(email)||StringUtils.isEmpty(password)){
            request.setAttribute("error","用户名或者密码不能为空");
            return "forward:login.jsp";
        }
        try {
            Subject subject = SecurityUtils.getSubject();
            UsernamePasswordToken uptoken=new UsernamePasswordToken(email,password);
            subject.login(uptoken);
            User user =(User)subject.getPrincipal();
            session.setAttribute("user",user);
            List<Module> moduleList=userService.findModuleByUserId(user.getId());
            session.setAttribute("modules",moduleList);
            return "home/main";
        }catch (Exception e){
            request.setAttribute("error","用户名或者密码错误");
            return "forward:login.jsp";
            
        }

	}

    //退出
    @RequestMapping(value = "/logout",name="用户登出")
    public String logout(){
        //SecurityUtils.getSubject().logout();   //登出
        return "forward:login.jsp";
    }

    @RequestMapping("/home")
    public String home(){
	    return "home/home";
    }
}

package com.itheima.web.shiro;

import com.itheima.domian.system.Module;
import com.itheima.domian.system.User;
import com.itheima.service.system.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AuthRealm extends AuthorizingRealm {
    @Autowired
    private UserService userService;
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection parm) {
        //获取当前登陆的用户
        User user = (User) parm.fromRealm(this.getName()).iterator().next();
        //获取当前用户可以操作的所有模块
        List<Module> modules = userService.findModuleByUserId(user.getId());
        Set<String> set = new HashSet<>();
        for (Module module : modules) {
            set.add(module.getName());
        }
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setStringPermissions(set);
        return info;

    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken uptoken =(UsernamePasswordToken)authenticationToken;
        String username = uptoken.getUsername();
        String password = new String(uptoken.getPassword());
        User user = userService.findByEmail(username);
        if(user != null){
            SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user,
                    user.getPassword(), this.getName());
            return info;
        }

        return null;
    }
}

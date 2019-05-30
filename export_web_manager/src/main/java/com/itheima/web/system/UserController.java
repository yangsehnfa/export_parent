package com.itheima.web.system;

import com.github.pagehelper.PageInfo;
import com.itheima.common.utils.UtilFuns;
import com.itheima.domian.system.Role;
import com.itheima.domian.system.User;
import com.itheima.service.system.DeptService;
import com.itheima.service.system.RoleService;
import com.itheima.service.system.UserService;
import com.itheima.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/system/user")
public class UserController extends BaseController {
    @Autowired
    private UserService userService;
    @Autowired
    private DeptService deptService;
    @Autowired
    private RoleService roleService;
    @RequestMapping("/list")
    public String list(@RequestParam(name = "page",defaultValue = "1") int page,
                       @RequestParam(name = "size",defaultValue = "5") int size){
        PageInfo pageInfo = userService.findPage(super.companyId,page, size);
        request.setAttribute("page",pageInfo);
        return "system/user/user-list";
    }
    @RequestMapping("/toAdd")
    public String toAdd() {
        List list = deptService.findAll(super.companyId);
        request.setAttribute("deptList",list);
        return "system/user/user-add";
    }
    @RequestMapping("/toUpdate")
    public String toUpdate(String id) {
        //1.查询用户信息
        User user = userService.findById(id);
        request.setAttribute("user",user);
        //2.查询部门列表
        List list = deptService.findAll(super.companyId);
        request.setAttribute("deptList",list);
        return "system/user/user-update";
    }

    @RequestMapping("/edit")
    public String edit(User user){
        user.setCompanyId(companyId);
        user.setCompanyName(companyName);
        if(UtilFuns.isEmpty(user.getId())) {
            userService.save(user);
        }else{
            userService.update(user);
        }
        return "redirect:/system/user/list.do";
    }
    @RequestMapping("/delete")
    public String delete(String id){
        userService.delete(id);
        return "redirect:/system/user/list.do";
    }
    @RequestMapping("/roleList")
    public String roleList(String id){
        User user = userService.findById(id);
        request.setAttribute("user",user);
        List<String> roleIds=userService.findRoleByUserId(id);
        request.setAttribute("userRoleStr",roleIds.toString());
        List<Role> roleList=roleService.findAll(super.companyId);
        request.setAttribute("roleList",roleList);
        return "system/user/user-role";
    }
    @RequestMapping("/changeRole")
    public String changeRole(String userid,String[] roleIds){
        userService.changeRole(userid,roleIds);
        return "redirect:/system/user/list.do";
    }
}
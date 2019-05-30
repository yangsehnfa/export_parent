package com.itheima.web.system;

import com.github.pagehelper.PageInfo;
import com.itheima.domian.system.Module;
import com.itheima.domian.system.Role;
import com.itheima.service.system.ModuleService;
import com.itheima.service.system.RoleService;
import com.itheima.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/system/role")
public class RoleController extends BaseController {
    @Autowired
    private RoleService roleService;
    @Autowired
    private ModuleService moduleService;
    /**
     * 返回全部列表
     */
    @RequestMapping("/list")
    public ModelAndView list(@RequestParam(name = "page",defaultValue = "1") int page,
                             @RequestParam(name = "size",defaultValue = "5") int size){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/system/role/role-list");
        PageInfo pageInfo = roleService.findPage(companyId,page, size);
        mv.addObject("page",pageInfo);
        return mv;
    }
    @RequestMapping("/toAdd")
    public String toAdd() {
        return "/system/role/role-add";
    }
    @RequestMapping("/toUpdate")
    public ModelAndView toUpdate(String id) {
        Role role = roleService.findById(id);
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/system/role/role-update");
        mv.addObject("role",role);
        return mv;
    }
    /**
     * 增加
     */
    @RequestMapping("/edit")
    public String edit(Role role){
        role.setCompanyId(super.companyId);
        role.setCompanyName(super.companyName);
        if(StringUtils.isEmpty(role.getId())) {
            roleService.save(role);
        }else{
            roleService.update(role);
        }
        return "redirect:/system/role/list.do";
    }
    /**
     * 删除
     */
    @RequestMapping("/delete")
    public String delete(String id){
        roleService.delete(id);
        return "redirect:/system/role/list.do";
    }
    /**
     * 跳转到角色的菜单列表页
     */
    @RequestMapping("/roleModule")
    public String roleModule(String roleid){
        Role role = roleService.findById(roleid);
        request.setAttribute("role",role);
        return "system/role/role-module";
    }
    /**
     * 构造 ztree 树数据
     */
    @RequestMapping("initModuleData")
    public @ResponseBody List<Map<String, Object>> initModuleData(String id){
        //根据角色 id 查询对应的所有菜单对象
        List<Module> roleModules = moduleService.findModulesByRoleId(id);
        //查询所有菜单
        List<Module> modules = moduleService.findAll();
        List<Map<String, Object>> list = new ArrayList<>();
        for (Module module : modules) {
            Map map = new HashMap();
            map.put("id", module.getId());
            map.put("pId", module.getParentId());
            map.put("name", module.getName());
            //需要重写 module 对象中的 hashcode 和 equals 方法
            if(roleModules.contains(module)){
                map.put("checked", "true");
            }else{
                map.put("checked", "false");
            }
            list.add(map);
        }
        return list;
    }
    /**
     * 更新角色权限
     */
    @RequestMapping("/updateRoleModule")
    public String updateRoleModule(String roleid,String moduleIds){
        String[] moduleIds2 = moduleIds.split(",");
        roleService.changeRoleModule(roleid,moduleIds2);
        return "redirect:/system/role/list.do";
    }
}
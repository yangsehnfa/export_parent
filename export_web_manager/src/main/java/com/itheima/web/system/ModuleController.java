package com.itheima.web.system;

import com.github.pagehelper.PageInfo;
import com.itheima.domian.system.Module;
import com.itheima.service.system.ModuleService;
import com.itheima.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/system/module")
public class ModuleController extends BaseController {
    @Autowired
    private ModuleService moduleService;
    /**
     * 返回全部列表
     */
    @RequestMapping("/list")
    public ModelAndView list(@RequestParam(name = "page",defaultValue = "1") int page,
                             @RequestParam(name = "size",defaultValue = "5") int size){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/system/module/module-list");
        PageInfo pageInfo = moduleService.findPage(page, size);
        mv.addObject("page",pageInfo);
        return mv;
    }
    @RequestMapping("/toAdd")
    public String toAdd() {
        //1.查询所有的菜单列表
        List<Module> menus = moduleService.findAll();
        request.setAttribute("menus",menus);
        return "/system/module/module-add";
    }
    @RequestMapping("/toUpdate")
    public String toUpdate(String id) {
        //1.根据 id 查询部门
        Module module = moduleService.findById(id);
        request.setAttribute("module",module);
        //2.查询所有的菜单列表
        List<Module> menus = moduleService.findAll();
        request.setAttribute("menus",menus);
        return "/system/module/module-update";
    }
    /**
     * 增加
     */
    @RequestMapping("/edit")
    public String edit(Module module){
        if(StringUtils.isEmpty(module.getId())) {
            moduleService.save(module);
        }else{
            moduleService.update(module);
        }
        return "redirect:/system/module/list.do";
    }
    /**
     * 删除
     */
    @RequestMapping("/delete")
    public String delete(String id){
        moduleService.delete(id);
        return "redirect:/system/module/list.do";
    }
}

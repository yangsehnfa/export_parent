package com.itheima.web.system;

import com.github.pagehelper.PageInfo;
import com.itheima.domian.system.Dept;
import com.itheima.service.system.DeptService;
import com.itheima.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/system/dept")
public class DeptController extends BaseController {
    @Autowired
    private DeptService deptService;
    /**
     * 返回全部列表
     */
    @RequestMapping("/list")
    public String list(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int size){
        PageInfo pageInfo = deptService.findPage(super.companyId, page, size);
        request.setAttribute("page",pageInfo);
        return "system/dept/dept-list";
    }
    @RequestMapping("/toAdd")
    public String toAdd() {
        //查询部门全部列表
        List list = deptService.findAll(companyId);
        request.setAttribute("deptList",list);
        return "system/dept/dept-add";
    }
    @RequestMapping("/toUpdate")
    public String toUpdate(String id) {
        //1.根据 id 查询部门
        Dept dept = deptService.findById(id);
        request.setAttribute("dept",dept);
        //2.查询部门全部列表
        List list = deptService.findAll(companyId);
        request.setAttribute("deptList",list);
        return "system/dept/dept-update";
    }
    /**
     * 增加
     */
    @RequestMapping("/edit")
    public String edit(Dept dept){
        dept.setCompanyId(super.companyId);
        dept.setCompanyName(super.companyName);
        if(StringUtils.isEmpty(dept.getId())) {
            deptService.save(dept);
        }else{
            deptService.update(dept);
        }
        return "redirect:/system/dept/list.do";
    }
    /**
     * 删除
     */
    @RequestMapping("/delete")
    public String delete(String id){
        deptService.delete(id);
        return "redirect:/system/dept/list.do";
    }
}

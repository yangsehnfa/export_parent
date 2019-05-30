package com.itheima.web.system;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.common.entity.PageResult;
import com.itheima.service.company.CompanyService;
import com.itheima.web.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/company")
public class CompanyController extends BaseController {
    @Reference
    private CompanyService companyService;

    /**
     * 返回全部列表
     */
    @RequestMapping("/list")
    public String list(@RequestParam(defaultValue = "1")int page, @RequestParam(defaultValue = "5") int size) {
        PageResult pageResult = companyService.findPage(page, size);
        request.setAttribute("page",pageResult );
        return "company/company-list";
    }
}

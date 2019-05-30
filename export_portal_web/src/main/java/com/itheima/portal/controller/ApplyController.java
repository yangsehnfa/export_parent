package com.itheima.portal.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.domian.system.Company;
import com.itheima.service.company.CompanyService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ApplyController {
    @Reference
    private CompanyService companyService;

    @RequestMapping("/apply")
    public @ResponseBody
    String apply(Company company) {
        try {
            companyService.save(company);
            return "1";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "2";
    }
}

package com.itheima.service.company.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.common.entity.PageResult;
import com.itheima.dao.system.CompanyDao;
import com.itheima.domian.system.Company;
import com.itheima.service.company.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private CompanyDao companyDao;
    public PageResult findPage(int page, int size) {
        int count = companyDao.findCount();
        List<Company> list = companyDao.findPage((page - 1) * size, size);
        return new PageResult(count,list,page,size);
    }

    @Override
    public void save(Company company) {
        companyDao.save(company);
    }
}

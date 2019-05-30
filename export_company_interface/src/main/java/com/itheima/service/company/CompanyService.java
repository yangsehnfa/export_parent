package com.itheima.service.company;

import com.itheima.common.entity.PageResult;
import com.itheima.domian.system.Company;

public interface CompanyService {

    PageResult findPage(int page, int size);

    void save(Company company);
}

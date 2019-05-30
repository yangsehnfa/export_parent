package com.itheima;

import com.itheima.dao.system.CompanyDao;
import com.itheima.domian.system.Company;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:spring/applicationContext-*.xml")
public class test {
    @Autowired
    private CompanyDao companyDao;

    @Test
    public void test1(){
        List<Company> list = companyDao.findAll();
        System.out.println(list);


    }
}

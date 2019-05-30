package com.itheima.dao.system;

import com.itheima.domian.system.Company;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CompanyDao {
    //查询全部
    public List<Company> findAll();

    int findCount();

    List<Company> findPage(@Param("startIndex") int startIndex, @Param("size") int size);

    void save(Company company);
}

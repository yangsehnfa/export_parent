package com.itheima.service.cargo.service;

import com.github.pagehelper.PageInfo;
import com.itheima.domian.cargo.Contract;
import com.itheima.domian.cargo.ContractExample;
import com.itheima.domian.cargo.ContractProductVo;

import java.util.List;

public interface ContractService {

	//根据id查询
    Contract findById(String id);

    //保存
    void save(Contract contract);

    //更新
    void update(Contract contract);

    //删除
    void delete(String id);

    //分页查询
	public PageInfo findAll(ContractExample example, int page, int size);

    List<ContractProductVo> findContractProductVoByShipTime(String companyId, String inputDate);
}

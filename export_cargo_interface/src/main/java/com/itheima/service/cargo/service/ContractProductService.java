package com.itheima.service.cargo.service;

import com.github.pagehelper.PageInfo;
import com.itheima.domian.cargo.ContractProduct;
import com.itheima.domian.cargo.ContractProductExample;

import java.util.List;

/**
 * 业务层接口
 */
public interface ContractProductService {

	/**
	 * 保存
	 */
	void save(ContractProduct contractProduct);

	/**
	 * 更新
	 */
	void update(ContractProduct contractProduct);

	/**
	 * 删除
	 */
	void delete(String id);

	/**
	 * 根据id查询
	 */
	ContractProduct findById(String id);

	/**
	 * 分页查询
	 */
	PageInfo findAll(ContractProductExample example, int page, int size);

    void save(List<ContractProduct> cps);
}

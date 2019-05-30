package com.itheima.web.cargo;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.itheima.service.cargo.service.ContractProductService;
import com.itheima.service.cargo.service.FactoryService;
import com.itheima.domian.cargo.ContractProduct;
import com.itheima.domian.cargo.ContractProductExample;
import com.itheima.domian.cargo.Factory;
import com.itheima.domian.cargo.FactoryExample;
import com.itheima.web.BaseController;
import com.itheima.web.qiniuUtil.FileUploadUtil;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/cargo/contractProduct")
public class ContractProductController  extends BaseController{
    @Reference
    private ContractProductService contractProductService;
    @Reference
    private FactoryService factoryService;
    @RequestMapping("list")
    public String list(String contractId,@RequestParam(defaultValue = "1")int page ,@RequestParam(defaultValue = "5")int size){
        //根据合同id查询商品
        ContractProductExample example = new ContractProductExample();
        example.createCriteria().andContractIdEqualTo(contractId);
        PageInfo pageInfo = contractProductService.findAll(example, page, size);
        //查询货物生产货物的工厂
        FactoryExample factoryExample = new FactoryExample();
        factoryExample.createCriteria().andCtypeEqualTo("货物");

        List<Factory> factoryList = factoryService.findAll(factoryExample);
        request.setAttribute("page",pageInfo);
        request.setAttribute("factoryList",factoryList);
        request.setAttribute("contractId",contractId);
        return "cargo/product/product-list";
    }

    @RequestMapping("toUpdate")
    public String toUpdate(String id){
        ContractProduct contractProduct = contractProductService.findById(id);
        request.setAttribute("contractProduct",contractProduct);
        //查询货物生产货物的工厂
        FactoryExample factoryExample = new FactoryExample();
        factoryExample.createCriteria().andCtypeEqualTo("货物");
        List<Factory> factoryList = factoryService.findAll(factoryExample);
        request.setAttribute("factoryList",factoryList);
        return "cargo/product/product-update";
    }
    @Autowired
    private FileUploadUtil fileUploadUtil;
    @RequestMapping("/edit")
    public String edit( MultipartFile productPhoto,ContractProduct contractProduct) throws Exception {
        contractProduct.setCompanyId(super.companyId);
        contractProduct.setCompanyName(super.companyName);
        if (StringUtils.isEmpty(contractProduct.getId())){
                //图片上传
                String imgPath="";
                if(productPhoto!=null){

                    imgPath ="http://"+ fileUploadUtil.upload(productPhoto);
                }
                contractProduct.setProductImage(imgPath);
                contractProductService.save(contractProduct);

        }else{
            contractProductService.update(contractProduct);
        }
        return "redirect:/cargo/contractProduct/list.do?contractId="+contractProduct.getContractId();
    }
    @RequestMapping("delete")
    public String delete(String id){
        ContractProduct contractProduct = contractProductService.findById(id);
        contractProductService.delete(id);

        return "redirect:/cargo/contractProduct/list.do?contractId="+contractProduct.getContractId();
    }

    /**
     * 转发到货物上传页面
     * @param contractId
     * @return
     */
    @RequestMapping("toImport")
    public String toImport(String contractId){
        request.setAttribute("contractId",contractId);

        return "cargo/product/product-import" ;
    }
    @RequestMapping("import")
    public String importProduct(String contractId,@RequestParam(name = "file") MultipartFile attachment) throws IOException {
        //根据流获取xls对象
            Workbook workbook = WorkbookFactory.create(attachment.getInputStream());
            //获取sheet页
            Sheet sheet = workbook.getSheetAt(0);
            //创建集合用于存储货物
            List<ContractProduct> cps = new ArrayList<>();
            //遍历行,从1开始
            for (int rowNum = 1; rowNum <=sheet.getLastRowNum(); rowNum++) {
                Row row = sheet.getRow(rowNum);
                //创建封装列的集合
                Object objs[] = new Object[row.getLastCellNum()];
                for(int cellNum = 1; cellNum < row.getLastCellNum();cellNum++) {
                    //获取单元格
                    Cell cell = row.getCell(cellNum);
                    //获取单元格数据,根据单元格的位置封装到Object数组中
                    objs[cellNum] = getValue(cell);
                }
                ContractProduct cp = new ContractProduct(objs,companyId,companyName);
                cp.setContractId(contractId);
                cps.add(cp);
            }
            contractProductService.save(cps);
            return "redirect:/cargo/contractProduct/list.do?contractId="+contractId;
        }
        //获取数据
        private Object getValue(Cell cell) {
            Object value = null;
            switch (cell.getCellType()) {
                case STRING: //字符串类型
                    value = cell.getStringCellValue();
                    break;
                case BOOLEAN: //boolean 类型
                    value = cell.getBooleanCellValue();
                    break;
                case NUMERIC: //数字类型（包含日期和普通数字）
                    if(DateUtil.isCellDateFormatted(cell)) {
                        value = cell.getDateCellValue();
                    }else{
                        value = cell.getNumericCellValue();
                    }
                    break;
                case FORMULA: //公式类型
                    value = cell.getCellFormula();
                    break;
                default:
                    break;
            }
            return value;

    }
}

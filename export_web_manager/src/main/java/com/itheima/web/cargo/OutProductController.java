package com.itheima.web.cargo;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.service.cargo.service.ContractService;
import com.itheima.common.utils.DownloadUtil;
import com.itheima.common.utils.UtilFuns;
import com.itheima.domian.cargo.ContractProductVo;
import com.itheima.web.BaseController;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.ByteArrayOutputStream;
import java.util.List;

@Controller
@RequestMapping("/cargo/contract")
public class OutProductController extends BaseController {
    @Reference
    private ContractService ContractService;
    //进入页面
    @RequestMapping("/print")
    public String toedit(){
        return "cargo/print/contract-print";
    }
    //报表导出
    @RequestMapping("/printExcel")
    public void printExcel(String inputDate) throws Exception {
        //1.创建工作簿
        Workbook wb =new XSSFWorkbook();//操作 excel2003 版本
        //2.创建工作表
        Sheet sheet = wb.createSheet();
        //3.设置一些参数，设置一些公用变量, 列宽就是个 bug
        sheet.setColumnWidth(0, 2*256);
        sheet.setColumnWidth(1, 26*256);
        sheet.setColumnWidth(2, 11*256);
        sheet.setColumnWidth(3, 29*256);
        sheet.setColumnWidth(4, 12*256);
        sheet.setColumnWidth(5, 15*256);
        sheet.setColumnWidth(6, 10*256);
        sheet.setColumnWidth(7, 10*256);
        sheet.setColumnWidth(8, 8*256);
        //一些公用变量
        int rowNo=0;
        int cellNo=1;
        Row nRow =null;
        Cell nCell = null;
        //3.实现大标题的制作==========================================
        nRow = sheet.createRow(rowNo++);
        //设置行高
        nRow.setHeightInPoints(36);
        //创建单元格对象
        nCell = nRow.createCell(cellNo);
        //设置单元格的内容
        nCell.setCellValue(inputDate.replace("-0", "-").replace("-", "年")+"月份出货表 ");//2015-12 2015-07
        //设置样式
        nCell.setCellStyle(this.bigTitle(wb));
        sheet.addMergedRegion(new CellRangeAddress(0,0,1,8));//横向合并单元格
        //4.实现小标题的制作=======================================
        String titles[] ={"客户","订单号","货号","数量","工厂","工厂交期","船期","贸易条款"};
        //创建小标题的行对象
        nRow = sheet.createRow(rowNo++);
        nRow.setHeightInPoints(26.25f);//设置行高
        //创建多个单元格对象
        for (String title : titles) {
            nCell = nRow.createCell(cellNo++);//创建单元格对象
            nCell.setCellValue(title);//设置单元格内容
            nCell.setCellStyle(this.title(wb));
        }
        //5.实现数据行的制作
        //1.查询所有货物
        List<ContractProductVo> cpList =
                ContractService.findContractProductVoByShipTime(companyId,inputDate);
        for (ContractProductVo cp : cpList) {
            nRow = sheet.createRow(rowNo++);//行 cqj
            nRow.setHeightInPoints(24);//行高
            cellNo=1;
            nCell = nRow.createCell(cellNo++);
            nCell.setCellValue(cp.getCustomName());//客户
            nCell.setCellStyle(this.text(wb));
            nCell = nRow.createCell(cellNo++);
            nCell.setCellValue(cp.getContractNo());//合同号
            nCell.setCellStyle(this.text(wb));
            nCell = nRow.createCell(cellNo++);
            nCell.setCellValue(cp.getProductNo());//产品号
            nCell.setCellStyle(this.text(wb));
            nCell = nRow.createCell(cellNo++);
            nCell.setCellValue(cp.getCnumber());//数量
            nCell.setCellStyle(this.text(wb));
            nCell = nRow.createCell(cellNo++);
            nCell.setCellValue(cp.getFactoryName());//工厂
            nCell.setCellStyle(this.text(wb));
            nCell = nRow.createCell(cellNo++);

            nCell.setCellValue(UtilFuns.dateTimeFormat(cp.getDeliveryPeriod()));//交期
            nCell.setCellStyle(this.text(wb));
            nCell = nRow.createCell(cellNo++);
            nCell.setCellValue(UtilFuns.dateTimeFormat(cp.getShipTime()));//船期
            nCell.setCellStyle(this.text(wb));
            nCell = nRow.createCell(cellNo++);
            nCell.setCellValue(cp.getTradeTerms());//贸易条款
            nCell.setCellStyle(this.text(wb));
        }
        //6.实现文件下载
        DownloadUtil downloadUtil = new DownloadUtil();
        //产生一个字节输出流(缓存)
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        wb.write(baos);//将 excel 工作簿中的内容输出到一个缓存中
        downloadUtil.download(baos, response, "出货表.xls");
    }
    //大标题的样式
    public CellStyle bigTitle(Workbook wb){
        CellStyle style = wb.createCellStyle();
        Font font = wb.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short)16);
        font.setBold(true);//字体加粗
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER); //横向居中
        style.setVerticalAlignment(VerticalAlignment.CENTER); //纵向居中
        return style;
    }
    //小标题的样式
    public CellStyle title(Workbook wb){
        CellStyle style = wb.createCellStyle();
        Font font = wb.createFont();
        font.setFontName("黑体");
        font.setFontHeightInPoints((short)12);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER); //横向居中
        style.setVerticalAlignment(VerticalAlignment.CENTER); //纵向居中
        style.setBorderTop(BorderStyle.THIN); //上细线
        style.setBorderBottom(BorderStyle.THIN); //下细线
        style.setBorderLeft(BorderStyle.THIN); //左细线
        style.setBorderRight(BorderStyle.THIN); //右细线
        return style;
    }
    //文字样式
    public CellStyle text(Workbook wb){
        CellStyle style = wb.createCellStyle();
        Font font = wb.createFont();
        font.setFontName("Times New Roman");
        font.setFontHeightInPoints((short)10);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.LEFT); //横向居左
        style.setVerticalAlignment(VerticalAlignment.CENTER); //纵向居中
        style.setBorderTop(BorderStyle.THIN); //上细线
        style.setBorderBottom(BorderStyle.THIN); //下细线
        style.setBorderLeft(BorderStyle.THIN); //左细线
        style.setBorderRight(BorderStyle.THIN); //右细线
        return style;
    }
}
package com.wbw.excel;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;

import static javax.xml.bind.JAXBIntrospector.getValue;

@Component
public class ExcelTest {
    public static String PATH = "C:\\Users\\Administrator\\Desktop\\";

    public void exportExcel(HttpServletResponse response) throws Exception {
        //时间
        long begin = System.currentTimeMillis();

        //创建一个工作簿
        // office 2007专用格式，即.xlsx 最多65536行
        XSSFWorkbook workbook = new XSSFWorkbook();

        exportExcel2(workbook,0,"自研");
        exportExcel2(workbook,1,"非自研");

        // office 2003专用格式，即.xls
        // Workbook workbook = new HSSFWorkbook();

        // 解决当使用 XSSF 方式导出大数据量时，内存溢出的问题
        // Workbook workbook = new SXSSFWorkbook();
        //创建表
        Sheet sheet = workbook.createSheet();
        response.setContentType("application/octet-stream;charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + "test1.xlsx");
        ServletOutputStream servletOutputStream = response.getOutputStream();
        workbook.write(servletOutputStream);
        servletOutputStream.flush();
        servletOutputStream.close();


        // 采用文件流 直接写出文件到制定位置
//        FileOutputStream fileOutputStream = new FileOutputStream(PATH + "用户信息表-XLSX.xlsx");
//        workbook.write(fileOutputStream);
//        fileOutputStream.close();
        long end = System.currentTimeMillis();
        System.out.println((double) (end - begin) / 1000);//5.003s
    }


    public void exportExcel2(XSSFWorkbook workbook, int sheetNum, String sheetTitle) throws Exception {
        // 生成一个表格
        XSSFSheet sheet = workbook.createSheet();
        workbook.setSheetName(sheetNum, sheetTitle);
        //写入数据
        for (int rowNumber = 0; rowNumber < 10; rowNumber++) {
            Row row = sheet.createRow(rowNumber); // 创建第0行
            for (int cellNumber = 0; cellNumber < 10; cellNumber++) {
                Cell cell = row.createCell(cellNumber); // 创建第0个单元格
                cell.setCellValue(cellNumber);
            }
        }
    }

    /**
     * 水平居中、垂直居中
     * 字体：宋体
     * 字体大小：16号
     * 加粗
     */
    public static CellStyle getStyle(XSSFWorkbook workbook) {
        CellStyle cellstyle = workbook.createCellStyle();
        cellstyle.setAlignment(HorizontalAlignment.CENTER);//水平居中
        cellstyle.setVerticalAlignment(VerticalAlignment.CENTER);//垂直居中
        Font font = workbook.createFont();//字体
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 16);//字号
        font.setBold(true);//加粗
        cellstyle.setFont(font);
        setBorderStyle(cellstyle);
        return cellstyle;
    }

    // 边框样式
    public static void setBorderStyle(CellStyle style) {
        style.setBorderBottom(BorderStyle.THIN); //下边框
        style.setBorderLeft(BorderStyle.THIN);//左边框
        style.setBorderTop(BorderStyle.THIN);//上边框
        style.setBorderRight(BorderStyle.THIN);//右边框
    }

    // 奇数行 背景颜色为黄色
    public static void setCellStyleYellow(CellStyle style) {
        style.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
    }

    //偶数行 背景颜色为LIME
    public static void setCellStyleLime(CellStyle style) {
        style.setFillForegroundColor(IndexedColors.LIME.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
    }

    // 字体设置红色
    public static void setFontRedColor(XSSFWorkbook workbook, CellStyle style) {
        Font font = workbook.createFont();//字体
        font.setColor(IndexedColors.RED.getIndex());
        style.setFont(font);
    }

    public static void importExcel() throws Exception {
        //获取文件流
        FileInputStream inputStream = new FileInputStream(PATH + "用户信息表read.xlsx");

        //1.创建工作簿,使用excel能操作的这边都看看操作
        Workbook workbook = new XSSFWorkbook(inputStream); //2007版
        //Workbook workbook = new HSSFWorkbook(inputStream); //2003版
        //2.得到表
        Sheet sheet = workbook.getSheetAt(0);
        //3.得到行
        Row row = sheet.getRow(0);
        //4.得到单元格
        Cell cell = row.getCell(0);
        getValue(cell);
        inputStream.close();
    }

    public static void getValue(Cell cell) {
        //匹配类型数据
        if (cell != null) {
            CellType cellType = cell.getCellType();
            String cellValue = "";
            switch (cellType) {
                case STRING: //字符串
                    System.out.print("[String类型]");
                    cellValue = cell.getStringCellValue();
                    break;
                case BOOLEAN: //布尔类型
                    System.out.print("[boolean类型]");
                    cellValue = String.valueOf(cell.getBooleanCellValue());
                    break;
                case BLANK: //空
                    System.out.print("[BLANK类型]");
                    break;
                case NUMERIC: //数字（日期、普通数字）
                    System.out.print("[NUMERIC类型]");
                    if (DateUtil.isCellDateFormatted(cell)) { //日期
                        System.out.print("[日期]");
                        Date date = cell.getDateCellValue();
                        cellValue = new DateTime(date).toString("yyyy-MM-dd");
                    } else {
                        //不是日期格式，防止数字过长
                        System.out.print("[转换为字符串输出]");
                        DataFormatter formatter = new DataFormatter();
                        cellValue = formatter.formatCellValue(cell);
                    }
                    break;
                case ERROR:
                    System.out.print("[数据类型错误]");
                    break;
            }
            System.out.println(cellValue);
        }
    }




}

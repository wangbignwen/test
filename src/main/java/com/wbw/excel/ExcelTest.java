package com.wbw.excel;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.*;
import org.joda.time.DateTime;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTSheetProtection;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriUtils;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.Date;

@Component
public class ExcelTest {
    public static String PATH = "C:\\Users\\Administrator\\Desktop\\";

    public void exportExcel(HttpServletResponse response) throws Exception {

        this.test1(response);
//        //时间
//        long begin = System.currentTimeMillis();
//
//        //创建一个工作簿
//        // office 2007专用格式，即.xlsx 最多65536行
//        XSSFWorkbook workbook = new XSSFWorkbook();
//
//        exportExcel2(workbook, 0, "自研");
//        exportExcel2(workbook, 1, "非自研");

        // office 2003专用格式，即.xls
        // Workbook workbook = new HSSFWorkbook();

        // 解决当使用 XSSF 方式导出大数据量时，内存溢出的问题
        // Workbook workbook = new SXSSFWorkbook();

//        response.setContentType("application/octet-stream;charset=UTF-8");
//        response.setHeader("Content-Disposition", "attachment;filename=" + "test1.xlsx");
//        ServletOutputStream servletOutputStream = response.getOutputStream();
//        workbook.write(servletOutputStream);
//        servletOutputStream.flush();
//        servletOutputStream.close();
//
//
//        // 采用文件流 直接写出文件到制定位置
////        FileOutputStream fileOutputStream = new FileOutputStream(PATH + "用户信息表-XLSX.xlsx");
////        workbook.write(fileOutputStream);
////        fileOutputStream.close();
//        long end = System.currentTimeMillis();
//        System.out.println((double) (end - begin) / 1000);//5.003s
    }

    private void test1(HttpServletResponse response) throws Exception {
        XSSFWorkbook workbook = new XSSFWorkbook();

        XSSFSheet sheet = workbook.createSheet("自研漏洞");
        sheet.setColumnWidth(0, 3000); // 某一列宽
//        sheet.setDefaultColumnWidth(200); // 默认列宽
//        sheet.autoSizeColumn(2,true); // 自动调整列宽
        sheet.setColumnHidden(5, true); // 设置隐藏列

        // 列合并
        CellRangeAddress region1 = new CellRangeAddress(0, 0, 7, 8);
        sheet.addMergedRegion(region1);
        RegionUtil.setBorderRight(BorderStyle.THIN, region1, sheet); //删除后补上合并遗留空白边框

        Row row0 = sheet.createRow(0); // 创建第0行
        Cell cell = row0.createCell(0);
        Cell cell01 = row0.createCell(1);
        Cell cell02 = row0.createCell(2);
        Row row1 = sheet.createRow(1);
        Cell cell10 = row1.createCell(0);
        Cell cell11 = row1.createCell(1);
        Cell cell12 = row1.createCell(2);

        // 插入公式
        String strFormula ="IF(SUM(A2,B2)=0,\"\",SUM(A2,B2))";
        cell12.setCellFormula(strFormula); // 设置单元格公式

        //设置下拉框内容
        XSSFDataValidationHelper dvHelper = new XSSFDataValidationHelper(sheet);
        DataValidationConstraint dvConstraint1 = dvHelper.createExplicitListConstraint(new String[]{"0", "1", "2"});
        // 设置下拉框生效的单元格
        CellRangeAddressList addressList = new CellRangeAddressList(0, 0, 0, 1);
        DataValidation validation = dvHelper.createValidation(dvConstraint1, addressList);
        sheet.addValidationData(validation);

        //设置提示内容 得先创建出单元格
        XSSFDrawing draw = sheet.createDrawingPatriarch();
        // 提示的 长3-2，宽3-2
        XSSFComment comment = draw.createCellComment(new XSSFClientAnchor(0, 0, 0, 0, 2, 2, 3, 3));
        XSSFRichTextString rtf = new XSSFRichTextString("提示内容");
        XSSFFont commentFormatter = workbook.createFont();
        commentFormatter.setFontName("宋体");
        commentFormatter.setFontHeightInPoints((short) 9);
        rtf.applyFont(commentFormatter); // 设置字体
        comment.setString(rtf); // 把comment放入单元格
        sheet.getRow(1).getCell(0).setCellComment(comment); // 有提示的单元格位置

        Font font = workbook.createFont();//字体
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 16);//字号
        font.setBold(true);//加粗
        font.setItalic(true); // 设置斜体
        font.setColor(IndexedColors.RED.getIndex());

        CellStyle cellstyle = workbook.createCellStyle();
        cellstyle.setFont(font);
        cellstyle.setAlignment(HorizontalAlignment.CENTER);//水平居中
        cellstyle.setVerticalAlignment(VerticalAlignment.CENTER);//垂直居中
        cellstyle.setBorderBottom(BorderStyle.THIN); //下边框
        cellstyle.setBorderLeft(BorderStyle.THIN);//左边框
        cellstyle.setBorderTop(BorderStyle.THIN);//上边框
        cellstyle.setBorderRight(BorderStyle.THIN);//右边框
        cellstyle.setWrapText(true); // 自动换行
        cellstyle.setLocked(true); // 锁定单元格
        cellstyle.setFillForegroundColor(IndexedColors.LIME.getIndex());//灰色
        cellstyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        cell.setCellStyle(cellstyle);
        cell.setCellValue(0);

        //设置页保护，锁定单元格才能生效
        // sheet.protectSheet("mima"); // 锁表
        sheet.disableLocking();
        //sheet.enableLocking();
        CTSheetProtection sheetProtection = sheet.getCTWorksheet().getSheetProtection();
        sheetProtection.setSelectLockedCells(false);
        sheetProtection.setSelectUnlockedCells(true);
        sheetProtection.setFormatCells(true);
        sheetProtection.setFormatColumns(true);
        sheetProtection.setFormatRows(true);
        sheetProtection.setInsertColumns(true);
        sheetProtection.setInsertRows(false);
        sheetProtection.setInsertHyperlinks(true);
        sheetProtection.setDeleteColumns(true);
        sheetProtection.setDeleteRows(true);
        sheetProtection.setSort(false);
        sheetProtection.setAutoFilter(false);
        sheetProtection.setPivotTables(true);
        sheetProtection.setObjects(true);
        sheetProtection.setScenarios(true);

        ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
        BufferedImage bufferImg = ImageIO.read(new File("D:\\软件\\头像.jpg"));
        ImageIO.write(bufferImg, "jpg", byteArrayOut);
        bufferImg.flush();
        byteArrayOut.flush();
        // 插入图片至单元格
        byte[] bytes = byteArrayOut.toByteArray();
        int pictureIdx = workbook.addPicture(bytes, workbook.PICTURE_TYPE_PNG);
        CreationHelper helper = workbook.getCreationHelper();
        ClientAnchor anchor = helper.createClientAnchor();
        // 图片插入坐标
        anchor.setRow1(2);
        anchor.setCol1(2);

        // 画图的顶级管理器，一个sheet只能获取一个
        XSSFDrawing drawing = sheet.createDrawingPatriarch();
        Picture pict = drawing.createPicture(anchor, pictureIdx);  // 插入图片
        pict.resize(3, 3); // 图片占用单元格数

//        // 插入图片至坐标
//        XSSFDrawing drawing = sheet.createDrawingPatriarch();
//        // 前四个参数是偏移量，后四个是左上角单元格列，单元格行，右下角单元格列，单元格行
//        XSSFClientAnchor anchor = new XSSFClientAnchor(10000*10, 10000*10, 10000*10, 10000*10, 1, 1,  5, 8);
//        drawing.createPicture(anchor, workbook.addPicture(byteArrayOut.toByteArray(), workbook.PICTURE_TYPE_JPEG));

        String fileName = "test 1.1 的导出文件.xlsx";
        fileName = UriUtils.encode(fileName,"utf-8"); // 解决URLEncode空格转为+的问题
        response.setContentType("application/octet-stream;charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename*=utf-8'zh_cn'" + fileName);
        ServletOutputStream servletOutputStream = response.getOutputStream();
        workbook.write(servletOutputStream);
        servletOutputStream.flush();
        servletOutputStream.close();

    }

    public void exportExcel2(XSSFWorkbook workbook, int sheetNum, String sheetTitle) throws Exception {
        // 生成一个表格
        XSSFSheet sheet = workbook.createSheet();
        workbook.setSheetName(sheetNum, sheetTitle); // 创建两张表
        //写入数据
        for (int rowNumber = 0; rowNumber < 10; rowNumber++) {
            Row row = sheet.createRow(rowNumber); // 创建第0行
            for (int cellNumber = 0; cellNumber < 10; cellNumber++) {
                Cell cell = row.createCell(cellNumber); // 创建第0个单元格
                cell.setCellValue(cellNumber);
            }
        }
    }


    public static void importExcel() throws Exception {
        //获取文件流
        FileInputStream inputStream = new FileInputStream(PATH + "用户信息表read.xlsx");

        //1.创建工作簿
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

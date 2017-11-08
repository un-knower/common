package com.postss.common.util;

import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

/**
 * POI EXCEL工具类,暂未判断类型,全部String返回
 * @author jwSun
 * @date 2017年4月20日 下午4:57:40
 */
public class ExcelUtil {

    private static final String EMPTY = "";
    private static final int DEFAULT_BEGIN_ROW = -1;

    /**
      * 读取excel
      * @param fileStream excel文件流
      * @return
      * @throws Exception
      */

    public static List<Map<String, String>> readExcel(InputStream fileStream) throws Exception {
        return readExcel(fileStream, DEFAULT_BEGIN_ROW);
    }

    /**
      * 读取excel
      * @param fileStream excel文件流
      * @param beginRow 开始读取行数
      * @return
      * @throws Exception
      */
    public static List<Map<String, String>> readExcel(InputStream fileStream, Integer beginRow) throws Exception {
        List<Map<String, String>> list = new ArrayList<>();
        Workbook workbook = WorkbookFactory.create(fileStream);
        int sheetNum = workbook.getNumberOfSheets();
        for (int i = 0; i < sheetNum; i++) {
            Sheet sheet = workbook.getSheetAt(i);
            if (beginRow == DEFAULT_BEGIN_ROW)
                beginRow = sheet.getFirstRowNum();

            for (int j = beginRow; j <= sheet.getLastRowNum(); j++) {
                Map<String, String> map = new TreeMap<>();
                list.add(map);
                Row row = sheet.getRow(j);
                for (int k = row.getFirstCellNum(); k < row.getLastCellNum(); k++) {
                    Cell cell = row.getCell(k);
                    if (cell == null)
                        map.put(String.valueOf(k), EMPTY);
                    else {
                        map.put(String.valueOf(k), row.getCell(k).toString());
                    }
                }
            }
        }
        return list;
    }

    /**
     * 导出excel表格文件(需要jxl.jar包)
     * @param list : 数据
     * @param titles : excel第一行名称
     * @param titleCode : Map的key
     * @param filename : 文件名
     * @param response
     */
    public static void exportExcelToHtml(List<Map<String, Object>> list, String[] titles, String[] titleCode,
            String fileName, HttpServletResponse response) {
        WritableWorkbook workbook = null;
        OutputStream os = null;
        try {
            response.setContentType("application/msexcel;charset=utf-8");
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setHeader("Content-Disposition",
                    "attachment;filename=" + URLEncoder.encode(fileName, "utf-8") + ".xls");

            os = response.getOutputStream();
            workbook = jxl.Workbook.createWorkbook(os);
            // 创建新的一页
            WritableSheet sheet = workbook.createSheet("First Sheet", 0);//设置页的名字
            sheet.getSettings().setDefaultColumnWidth(20);//设置默认列宽
            for (int i = 0; i < titles.length; i++) {
                Label label = new Label(i, 0, titles[i]);
                sheet.addCell(label);
            }

            if (null == list || list.size() == 0) {
                workbook.write();
                return;
            }
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            // 表格添加数据
            for (int i = 0; i < list.size(); i++) { // 一行
                Map<String, Object> map = list.get(i);
                for (int j = 0; j < titles.length; j++) {
                    Label label = null;
                    Object param = map.get(titleCode[j]);
                    if (map.get(titleCode[j]) != null || titleCode[j] != null) {
                        jxl.write.Number number = null;
                        if (param instanceof Integer) {
                            int value = ((Integer) param).intValue();
                            number = new jxl.write.Number(j, i + 1, value);
                            sheet.addCell(number);
                        } else if (param instanceof String) {
                            String s = (String) param;
                            if ("price".equals(titleCode[j]) || "chrate".equals(titleCode[j])) {
                                int n = Integer.parseInt(s);
                                number = new jxl.write.Number(j, i + 1, n);
                                sheet.addCell(number);
                            } else {
                                label = new Label(j, i + 1, s);
                                sheet.addCell(label);
                            }
                        } else if (param instanceof Double) {
                            double d = ((Double) param).doubleValue();
                            number = new jxl.write.Number(j, i + 1, d);
                            sheet.addCell(number);
                        } else if (param instanceof Float) {
                            float f = ((Float) param).floatValue();
                            number = new jxl.write.Number(j, i + 1, f);
                            sheet.addCell(number);
                        } else if (param instanceof Long) {
                            long l = ((Long) param).longValue();
                            number = new jxl.write.Number(j, i + 1, l);
                            sheet.addCell(number);
                        } else if (param instanceof BigDecimal) {
                            long b = ((BigDecimal) param).longValue();
                            number = new jxl.write.Number(j, i + 1, b);
                            sheet.addCell(number);
                        } else if (param instanceof Date) {
                            Date date = (Date) param;
                            String newDate = dateFormat.format(date);
                            label = new Label(j, i + 1, newDate);
                            sheet.addCell(label);
                        }
                    } else {
                        label = new Label(j, i + 1, "");
                        sheet.addCell(label);
                    }
                }
            }
            workbook.write();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (workbook != null) {
                    workbook.close();
                }
                if (os != null) {
                    os.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}

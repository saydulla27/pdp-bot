package uz.pdp.pdpbot.service;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.aspectj.util.TypeSafeEnum;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.FileOutputStream;
import java.io.IOException;

public class Maincl {
    private static XSSFWorkbook workbook;
    private static  XSSFSheet sheet;



    public static void main(String[] args) throws IOException {

      export();

    }
    private static  void writeHeaderLine() {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Java Books");


        Row row = sheet.createRow(0);

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);

        row.createCell(0,1);
        createCell(row, 0, "User ID", style);
        createCell(row, 1, "phone", style);
        createCell(row, 2, "Full Name", style);
        createCell(row, 3, "Roles", style);
        createCell(row, 4, "Enabled", style);

    }
    private static void createCell(Row row, int columnCount, Object value, CellStyle style) {

        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        } else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }
    public static void export() throws IOException, IOException {
        writeHeaderLine();
//        writeDataLines();

        FileOutputStream outputStream = new FileOutputStream("ad.xlsx");
        workbook.write(outputStream);
        outputStream.close();


    }

}

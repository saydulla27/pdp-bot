package uz.pdp.pdpbot.service;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import uz.pdp.pdpbot.entity.UserResoult;

import java.util.List;

public class Excell {




    private XSSFWorkbook workbook = new XSSFWorkbook();
    private XSSFSheet sheet;
    private List<UserResoult> listUsers;

    public Excell(List<UserResoult> listUsers) {
        this.listUsers = listUsers;
    }


    private void writeHeaderLine() {
        sheet = workbook.createSheet("javoblar");

        Row row = sheet.createRow(0);

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);

        createCell(row, 0, "Savol", style);
        createCell(row, 1, "Ball", style);
        createCell(row, 2, "Javoblar", style);
        createCell(row, 3, "O`quvchi", style);
        createCell(row, 4, "Tel_raqami", style);

    }

    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
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

//    private void writeDataLines() {
//
//
//        int rowCount = 1;
//
//        CellStyle style = workbook.createCellStyle();
//        XSSFFont font = workbook.createFont();
//        font.setFontHeight(14);
//        style.setFont(font);
//
//        for (UserResoult user : listUsers) {
//            Row row = sheet.createRow(rowCount++);
//            int columnCount = 0;
//
//            createCell(row, columnCount++, user.getSavol().getName(), style);
//            createCell(row, columnCount++, user.getBall(), style);
//            createCell(row, columnCount++, user.getDescription(), style);
//            createCell(row, columnCount++, user.getUser().getFullName(), style);
//            createCell(row, columnCount++, user.getUser().getPhoneNumber(), style);
//
//        }
//    }

//    public void export(String a) throws IOException {
//        writeHeaderLine();
//        writeDataLines();
//
//        File compressFile = null;
//        File file = new File(a + ".xlsx");
//        OutputStream outputStream = new FileOutputStream(file);
//        workbook.write(outputStream);
//        outputStream.close();
//
//    }


}

//    Excell excell = new Excell(all);
//   excell.export(text);
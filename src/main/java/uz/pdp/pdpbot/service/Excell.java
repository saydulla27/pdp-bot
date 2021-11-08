package uz.pdp.pdpbot.service;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import uz.pdp.pdpbot.entity.User;
import uz.pdp.pdpbot.entity.UserResoult;
import uz.pdp.pdpbot.repository.UserRepository;
import uz.pdp.pdpbot.repository.UserResoultRepository;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Component
public class Excell {

    @Autowired
    UserResoultRepository userResoultRepository;

    @Autowired
    UserRepository userRepository;


    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<UserResoult> listUsers;


    public void UserExcelExporter(List<UserResoult> listUsers) {
        this.listUsers = listUsers;
        workbook = new XSSFWorkbook();
    }


    private void writeHeaderLine() {
        sheet = workbook.createSheet("Users");

        Row row = sheet.createRow(0);

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);

        createCell(row, 0, "User ID", style);
        createCell(row, 1, "phone", style);
        createCell(row, 2, "Full Name", style);
        createCell(row, 3, "Roles", style);
        createCell(row, 4, "Enabled", style);

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

    private void writeDataLines() {
        int rowCount = 1;

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);

        for (UserResoult user : listUsers) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;

            createCell(row, columnCount++, user.getId(), style);
            createCell(row, columnCount++, user.getUser(), style);
            createCell(row, columnCount++, user.getUser().getFullName(), style);
            createCell(row, columnCount++, user.getUser().getRole(), style);
            createCell(row, columnCount++, user.getUser().isActive(), style);

        }
    }

    public void export() throws IOException, IOException {
        writeHeaderLine();
//        writeDataLines();

        FileOutputStream outputStream = new FileOutputStream("ad.xlsx");
        workbook.write(outputStream);
        outputStream.close();


    }
}



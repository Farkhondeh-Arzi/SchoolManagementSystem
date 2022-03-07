package com.example.sm.excel;

import com.example.sm.model.User;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.swing.filechooser.FileSystemView;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ExcelExporter {
    private final XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private final List<User> listUsers;

    public ExcelExporter(List<User> listUsers) {
        this.listUsers = listUsers;
        workbook = new XSSFWorkbook();
    }

    private void writeHeaderLine() {
        sheet = workbook.createSheet("Users");

        Row row = sheet.createRow(0);

        createCell(row, 0, "User ID");
        createCell(row, 1, "First Name");
        createCell(row, 2, "Last Name");
        createCell(row, 3, "National Number");
        createCell(row, 4, "Role");

    }

    private void createCell(Row row, int columnCount, Object value) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        } else if (value instanceof Long) {
            cell.setCellValue((Long) value);
        } else {
            cell.setCellValue((String) value);
        }
    }

    private void writeDataLines() {
        int rowCount = 1;

        for (User user : listUsers) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;

            createCell(row, columnCount++, user.getId());
            createCell(row, columnCount++, user.getForename());
            createCell(row, columnCount++, user.getSurname());
            createCell(row, columnCount, user.getNationalNumber());
            createCell(row, columnCount, user.getRole());
        }
    }

    public void export() throws IOException {
        writeHeaderLine();
        writeDataLines();

        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        workbook.write(byteOut);

        File zipFile = new File("src/main/resources/export.zip");
        ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(zipFile));

        ZipEntry entry = new ZipEntry("file.xlsx");
        zipOut.putNextEntry(entry);

        byteOut.writeTo(zipOut);

        zipOut.closeEntry();
        zipOut.close();
        workbook.close();
        byteOut.close();
    }
}

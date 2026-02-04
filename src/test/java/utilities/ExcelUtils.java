package utilities;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelUtils {
    public static void writeCourseData(String[] names, String[] ratings, String[] hours) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Courses");

        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("Course Name");
        header.createCell(1).setCellValue("Rating");
        header.createCell(2).setCellValue("Hours");

        for (int i = 0; i < names.length; i++) {
            Row row = sheet.createRow(i + 1);
            row.createCell(0).setCellValue(names[i]);
            row.createCell(1).setCellValue(ratings[i]);
            row.createCell(2).setCellValue(hours[i]);
        }

        FileOutputStream fileOut = new FileOutputStream("CourseDetails.xlsx");
        workbook.write(fileOut);
        fileOut.close();
        workbook.close();
    }
}
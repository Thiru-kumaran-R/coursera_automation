package utilities;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class ExcelUtils {
    // Ab ye method Lists accept karega
    public static void writeCourseData(List<String> names, List<String> ratings, List<String> hours) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Courses");

        // Header Row
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("Course Name");
        header.createCell(1).setCellValue("Rating");
        header.createCell(2).setCellValue("Duration/Hours");

        // Data Rows
        for (int i = 0; i < names.size(); i++) {
            Row row = sheet.createRow(i + 1);
            row.createCell(0).setCellValue(names.get(i));
            row.createCell(1).setCellValue(ratings.get(i));
            row.createCell(2).setCellValue(hours.get(i));
        }

        // File auto-save logic
        try (FileOutputStream fileOut = new FileOutputStream("CourseDetails.xlsx")) {
            workbook.write(fileOut);
            System.out.println("Excel file created successfully: CourseDetails.xlsx");
        } catch (IOException e) {
            System.out.println("Error writing to Excel: " + e.getMessage());
        } finally {
            try {
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
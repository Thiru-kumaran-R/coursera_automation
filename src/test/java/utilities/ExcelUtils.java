package utilities;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

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

    public static void writeLanguageLearningSheet(List<String> languages, List<String> levels, String fileName) throws IOException {
        if (fileName == null || fileName.trim().isEmpty()) {
            fileName = "Filters.xlsx";
        }

        org.apache.poi.ss.usermodel.Workbook workbook = null;
        java.io.FileInputStream fis = null;

        java.io.File file = new java.io.File(fileName);
        try {
            if (file.exists()) {
                // Open existing workbook
                fis = new java.io.FileInputStream(file);
                workbook = new org.apache.poi.xssf.usermodel.XSSFWorkbook(fis);
            } else {
                // Create new workbook
                workbook = new org.apache.poi.xssf.usermodel.XSSFWorkbook();
            }
        } finally {
            if (fis != null) {
                fis.close();
            }
        }

        try {
            // If sheet exists, remove it to rewrite cleanly
            org.apache.poi.ss.usermodel.Sheet existing = workbook.getSheet("Language Learning");
            if (existing != null) {
                int idx = workbook.getSheetIndex(existing);
                workbook.removeSheetAt(idx);
            }

            org.apache.poi.ss.usermodel.Sheet sheet = workbook.createSheet("Language Learning");

            // Headers on the same row
            int headerRow = 0;
            org.apache.poi.ss.usermodel.Row header = sheet.createRow(headerRow);
            header.createCell(0).setCellValue("Language");
            header.createCell(1).setCellValue("Count");
            header.createCell(2).setCellValue("Level");
            header.createCell(3).setCellValue("Count");

            // Determine the max number of rows we need
            int langSize = (languages == null) ? 0 : languages.size();
            int levelSize = (levels == null) ? 0 : levels.size();
            int maxRows = Math.max(langSize, levelSize);

            // Write rows side-by-side
            for (int i = 0; i < maxRows; i++) {
                org.apache.poi.ss.usermodel.Row row = sheet.getRow(i + 1);
                if (row == null) row = sheet.createRow(i + 1);

                // Languages (parse "Name --> Count")
                if (i < langSize) {
                    String entry = languages.get(i);
                    if (entry != null && !entry.trim().isEmpty()) {
                        String name = entry;
                        String count = "";
                        String[] parts = entry.split("-->");
                        if (parts.length > 0) name = parts[0].trim().replaceAll("\\(.*\\)", "");;
                        if (parts.length > 1) count = parts[1].trim().replaceAll("[()]", "");;

                        row.createCell(0).setCellValue(name);
                        row.createCell(1).setCellValue(count);
                    }
                }

                // Levels in the next column
                if (i < levelSize) {
                    String lvl = languages.get(i);
                    if (lvl != null && !lvl.trim().isEmpty()) {
                        String name = lvl;
                        String count = "";
                        String[] parts = lvl.split("-->");
                        if (parts.length > 0) name = parts[0].trim().replaceAll("\\(.*\\)", "");;
                        if (parts.length > 1) count = parts[1].trim().replaceAll("[()]", "");;

                        row.createCell(2).setCellValue(name);
                        row.createCell(3).setCellValue(count);
                    }
                }
            }

            // Auto-size columns
            sheet.autoSizeColumn(0);
            sheet.autoSizeColumn(1);
            sheet.autoSizeColumn(2);
            sheet.autoSizeColumn(3);

            // Write back to file
            try (java.io.FileOutputStream fos = new java.io.FileOutputStream(fileName)) {
                workbook.write(fos);
            }
        } finally {
            if (workbook != null) {
                workbook.close();
            }
        }
    }
}
package com.example.MoneyManagement.Util;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;

@Slf4j
@UtilityClass
public class ExcelUtil {

    public static final String DEFAULT_SHEET_NAME = "Money Management Report";
    public static final String CURRENCY_FORMAT = "₹#,##0.00";
    public static final String DATE_FORMAT = "dd-MM-yyyy";

    public static Workbook createWorkbook() {
        log.info("Creating Excel Workbook...");
        return new XSSFWorkbook();
    }
    public static Sheet createSheet(Workbook workbook, String sheetName) {
        if (workbook == null) {
            throw new IllegalArgumentException("Workbook cannot be null.");
        }
        if (sheetName == null || sheetName.isBlank()) {
            sheetName = DEFAULT_SHEET_NAME;
        }
        return workbook.createSheet(sheetName);
    }
    public static Sheet createDefaultSheet(Workbook workbook) {
        return createSheet(workbook, DEFAULT_SHEET_NAME);
    }


    public static Font createHeaderFont(Workbook workbook) {
        Font font = workbook.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 12);
        font.setColor(IndexedColors.WHITE.getIndex());
        return font;
    }
    public static Font createDataFont(Workbook workbook) {
        Font font = workbook.createFont();
        font.setBold(false);
        font.setFontHeightInPoints((short) 11);
        return font;
    }
    public static CellStyle createHeaderCellStyle(Workbook workbook) {

        CellStyle style = workbook.createCellStyle();
        style.setFont(createHeaderFont(workbook));

        style.setFillForegroundColor(IndexedColors.DARK_BLUE.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);

        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);

        return style;
    }
    public static CellStyle createDataCellStyle(Workbook workbook) {

        CellStyle style = workbook.createCellStyle();
        style.setFont(createDataFont(workbook));

        style.setAlignment(HorizontalAlignment.LEFT);
        style.setVerticalAlignment(VerticalAlignment.CENTER);

        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);

        return style;
    }

    public static CellStyle createCurrencyCellStyle(Workbook workbook) {

        CellStyle style = createDataCellStyle(workbook);
        DataFormat format = workbook.createDataFormat();

        style.setDataFormat(format.getFormat(CURRENCY_FORMAT));
        style.setAlignment(HorizontalAlignment.RIGHT);

        return style;
    }

    public static CellStyle createDateCellStyle(Workbook workbook) {

        CellStyle style = createDataCellStyle(workbook);
        DataFormat format = workbook.createDataFormat();

        style.setDataFormat(format.getFormat(DATE_FORMAT));
        style.setAlignment(HorizontalAlignment.CENTER);

        return style;
    }

    public static CellStyle createTitleCellStyle(Workbook workbook) {

        Font font = workbook.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 14);

        CellStyle style = workbook.createCellStyle();
        style.setFont(font);

        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);

        return style;
    }
    public static Row createRow(Sheet sheet, int rowIndex) {
        if (sheet == null) {
            throw new IllegalArgumentException("Sheet cannot be null.");
        }
        return sheet.createRow(rowIndex);
    }
    public static Cell createStringCell(Row row, int column, String value, CellStyle style) {
        Cell cell = row.createCell(column);
        cell.setCellValue(value == null ? "" : value);

        if (style != null) {
            cell.setCellStyle(style);
        }
        return cell;
    }

    public static Cell createNumberCell(Row row, int column, Number value, CellStyle style) {
        Cell cell = row.createCell(column);
        if (value != null) {
            cell.setCellValue(value.doubleValue());
        }
        if (style != null) {
            cell.setCellStyle(style);
        }

        return cell;
    }
    public static Cell createBooleanCell(Row row, int column, Boolean value, CellStyle style) {
        Cell cell = row.createCell(column);
        cell.setCellValue(Boolean.TRUE.equals(value));
        if (style != null) {
            cell.setCellStyle(style);
        }
        return cell;
    }
    public static Cell createDateCell(Row row, int column, LocalDate date, CellStyle style) {
        Cell cell = row.createCell(column);
        if (date != null) {
            cell.setCellValue(java.sql.Date.valueOf(date));
        }
        if (style != null) {
            cell.setCellStyle(style);
        }
        return cell;
    }
    public static Row createHeaderRow(Sheet sheet, String[] headers, CellStyle style) {
        if (sheet == null) {
            throw new IllegalArgumentException("Sheet cannot be null.");
        }
        if (headers == null || headers.length == 0) {
            throw new IllegalArgumentException("Headers cannot be empty.");
        }
        Row row = sheet.createRow(0);
        for (int i = 0; i < headers.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(headers[i]);

            if (style != null) {
                cell.setCellStyle(style);
            }
        }
        return row;
    }

    public static void autoSizeColumns(Sheet sheet, int totalColumns) {

        for (int i = 0; i < totalColumns; i++) {
            sheet.autoSizeColumn(i);
        }
    }
    public static void freezeHeaderRow(Sheet sheet) {
        sheet.createFreezePane(0, 1);
    }
    public static void mergeCells(Sheet sheet,
                                  int firstRow,
                                  int lastRow,
                                  int firstCol,
                                  int lastCol) {

        sheet.addMergedRegion(
                new CellRangeAddress(firstRow, lastRow, firstCol, lastCol)
        );
    }
    public static void setDefaultColumnWidth(Sheet sheet, int width) {
        sheet.setDefaultColumnWidth(width);
    }
    public static void setRowHeight(Row row, float height) {
        row.setHeightInPoints(height);
    }
    public static byte[] writeWorkbook(Workbook workbook) throws IOException {

        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            workbook.write(out);
            return out.toByteArray();
        }
    }
    public static void closeWorkbook(Workbook workbook) {

        try {
            if (workbook != null) {
                workbook.close();
            }
        } catch (IOException e) {
            log.error("Error closing workbook", e);
        }
    }
}
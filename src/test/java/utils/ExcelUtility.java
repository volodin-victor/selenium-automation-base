package utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtility {

  private static Workbook book;
  private static Sheet sheet;

  /**
   * Method reads Excel file using filePath
   *
   * @param filePath
   */
  private static void openExcelFile(String filePath) {
    FileInputStream fileInputStream;

    try {
      fileInputStream = new FileInputStream(filePath);
      book = new XSSFWorkbook(fileInputStream);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static void loadSheet(String sheetName) {
    sheet = book.getSheet(sheetName);
  }

  private static int rowCount() {
    return sheet.getPhysicalNumberOfRows();
  }

  private static int columnsCount(int rowIndex) {
    return sheet.getRow(rowIndex).getLastCellNum();
  }

  private static String cellData(int rowIndex, int colIndex) {
    return sheet.getRow(rowIndex).getCell(colIndex).toString();
  }

  /**
   * Returns a 2D object array
   *
   * @param filePath
   * @param sheetName
   * @return Object[][]
   */
  public static Object[][] excelIntoArray(String filePath, String sheetName) {
    openExcelFile(filePath);
    loadSheet(sheetName);
    int rows = rowCount();
    int cols = columnsCount(0);
    Object[][] data = new Object[rows - 1][cols];
    // iterate over rows
    for (int row = 1; row < rows; row++) {
      // iterate over columns
      for (int col = 0; col < cols; col++) {
        data[row - 1][col] = cellData(row, col);
      }
    }
    return data;
  }

  /**
   * Returns Excel file as a List of Maps
   *
   * @param filePath
   * @param sheetName
   * @return List<Map < String, String>>
   */
  public static List<Map<String, String>> excelIntoListOfMaps(String filePath, String sheetName) {
    openExcelFile(filePath);
    loadSheet(sheetName);
    List<Map<String, String>> list = new ArrayList<>();
    Map<String, String> excelMap;
    for (int row = 1; row < rowCount(); row++) {
      excelMap = new LinkedHashMap<>();
      for (int c = 0; c < columnsCount(row); c++) {
        excelMap.put(cellData(0, c), cellData(row, c));
      }
      list.add(excelMap);
    }
    return list;
  }

}

package uk.axone.module1_project;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * This class is to handle multiple reading operations of content from Excel workbook.
 *
 * @author Karthik.Gandhinathan
 *a
 **/
public class ExcelReader {

    /** Path to the spreadsheet. */
    private String path;
    /** Stream to read the spreadsheet. */
    private FileInputStream fis = null;
    /** Access to the spreadsheet. */
    private XSSFWorkbook workbook = null;
    /** Work sheet in the current file. */
    private XSSFSheet sheet = null;
    /** Row within the current sheet. */
    private XSSFRow row = null;
    /** Cell within the current sheet. */
    private XSSFCell cell = null;
    /** Stream to write the spreadsheet. */
    private FileOutputStream fileOut = null;


    /**
     * ExcelReader(String path) constructor accepts only the path of the Excel
     * sheet.
     *
     * @author Karthik.Gandhinathan
     * @param path The name of and path to the spreadsheet
     *
     **/
    public ExcelReader(final String path) {
        if (path != null){
            this.setPath(path);
            try {
                setFis(new FileInputStream(path));
                setWorkbook(new XSSFWorkbook(getFis()));
                getFis().close();
            } catch (FileNotFoundException fe){
                System.out.println("Unable to open spreadsheet " + fe.getMessage());
            } catch (Exception e) {
                System.out.println("Problem creating stream");
            }
        } else {
            System.out.println("Unable to open spreadsheet as path is null");
        }
    }

    /**
     * ExcelReader(String path, String SheetName) constructor accepts the path
     * of the Excel sheet with its Name and the workSheetName.
     *
     * @author Karthik.Gandhinathan
     * @param path
     *            - name & path of the Excel spreadsheet
     * @param sheetName
     *            - name of the workSheet
     **/

    public ExcelReader(final String path, final String sheetName) {
        if (path != null){
            this.setPath(path);
            try {
                setFis(new FileInputStream(path));
                setWorkbook(new XSSFWorkbook(getFis()));
                this.setSheet(getWorkbook().getSheet(sheetName));
                if (this.getSheet() == null){
                    System.out.println("Unable to retrieve worksheet \"" + sheetName + "\" from workbook \"" + path + "\"");
                }
                getFis().close();
            } catch (FileNotFoundException fe){
                System.out.println("Unable to open spreadsheet " + fe.getMessage());
            } catch (Exception e) {
                System.out.println("Problem creating stream");
            }
        } else {
            System.out.println("Unable to open spreadsheet as path is null");
        }
    }

    /**
     * This method returns the String value of the cell content which matches
     * for the specified row number and column number.
     *
     * @author Karthik.Gandhinathan
     * @author srikanth.gone
     * @param row
     *            - Row Number
     * @param col
     *            - column Number
     * @return String Note: Row number and column numbers start from index 0.
     **/
    public String getCellValue(final int row, final int col) {
        Row rows = getSheet().getRow(row);
        Cell cell = rows.getCell(col);
        if (cell == null) {
            return "";
        } else if (cell.getCellType() == CellType.STRING) {
            return cell.getStringCellValue();
        } else if (cell.getCellType() == CellType.NUMERIC) {
            // return String.valueOf(cell.getNumericCellValue());
            return new java.text.DecimalFormat("0").format(cell
                    .getNumericCellValue());
        } else if (cell.getCellType() == CellType.BOOLEAN) {
            // return String.valueOf(cell.getNumericCellValue());
            return Boolean.toString(cell.getBooleanCellValue());
        }else {
            return cell.getStringCellValue();
        }

    }

    /**
     * This overloaded method returns the String value of the cell content which
     * matches for the specified SheetName, column number and row number.
     *
     * @author Karthik.Gandhinathan
     * @param sheetName
     *            - String value of the workSheetName
     * @param row
     *            - Row Number
     * @param col
     *            - column Number
     * @return String Note: column numbers start from index 0.
     **/
    public String getCellValue(final String sheetName, final int row,
                               final int col) {
        setSheet(getWorkbook().getSheet(sheetName));
        if (getSheet() != null){
            Row rows = getSheet().getRow(row - 1);
            Cell cell = rows.getCell(col);

            if (cell == null) {
                return "";
            } else if (cell.getCellType() == CellType.STRING) {
                return cell.getStringCellValue();
            } else if (cell.getCellType() == CellType.NUMERIC) {
                // return String.valueOf(cell.getNumericCellValue());
                return new java.text.DecimalFormat("0").format(cell
                        .getNumericCellValue());
            } else {
                return cell.getStringCellValue();
            }
        } else {
            System.out.println("Unable to retrieve sheet \"" + sheetName + "\" from workbook \"" + getPath() + "\"");
            return null;
        }
    }

    /**
     * This method returns the number of usedRows present in a worksheet.
     *
     * @author Karthik.Gandhinathan
     * @return integer
     **/

    public int getRowCount() {
        if (getSheet() != null){
            int rowCount = getSheet().getLastRowNum();
            return rowCount + 1;
        } else {
            System.out.println("Sheet not found");
            return 0;
        }
    }

    /**
     * This is a overloaded method which returns the number of usedRows present
     * in the specified workSheet.
     *
     * @author Karthik.Gandhinathan
     * @param sheetName
     *            String value of the workSheetName
     * @return integer
     *
     **/
    public int getRowCount(final String sheetName) {
        setSheet(getWorkbook().getSheet(sheetName));
        if (getSheet() != null){
            int rowCount = getSheet().getLastRowNum() + 1;
            return rowCount;
        } else {
            System.out.println("Sheet " + sheetName + " not found");
            return 0;
        }
    }

    /**
     * This method returns the number of columns present in a particular
     * worksheet for the specified rownumber.
     *
     * @author Karthik.Gandhinathan
     * @return integer
     *
     **/
    public int getColumnCount() {
        if (getSheet() != null){
            Row rowNum = getSheet().getRow(0);
            int columnCount = rowNum.getLastCellNum();
            return columnCount;
        } else {
            System.out.println("Sheet not found");
            return 0;
        }
    }

    /**
     * This overloaded method returns the number of columns present in a
     * particular worksheet for the specified worksheet.
     *
     * @author Karthik.Gandhinathan
     * @return integer
     * @param sheetName
     *            String value of the workSheetName
     *
     **/

    // returns number of columns in a sheet
    public int getColumnCount(final String sheetName) {
        setSheet(getWorkbook().getSheet(sheetName));
        if (getSheet() != null){
            Row rowNum = getSheet().getRow(0);
            int columnCount = rowNum.getLastCellNum();
            return columnCount;
        } else {
            System.out.println("Sheet " + sheetName + " not found");
            return 0;
        }
    }

    /**
     * This method is to set String value in Excel file.
     * @author Sumita.Rawat
     * @param sheetName
     *            is workSheet Name in which value need to be set
     * @param colName
     *            is to find particular column to set value
     * @param rowNum
     *            is to find particular cell to set value
     * @param data
     *            is the string to set
     * @return Boolean whether data added or not
     */
    public boolean setCellData(final String sheetName, final String colName, final int rowNum,
                               final String data) {
        try {
            setFis(new FileInputStream(getPath()));
            setWorkbook(new XSSFWorkbook(getFis()));

            if (rowNum <= 0) {
                return false;
            }

            int index = getWorkbook().getSheetIndex(sheetName);
            int colNum = -1;
            if (index == -1) {
                return false;
            }

            setSheet(getWorkbook().getSheetAt(index));

            setRow(getSheet().getRow(0));
            for (int i = 0; i < getRow().getLastCellNum(); i++) {
                // System.out.println(row.getCell(i).getStringCellValue().trim());
                if (getRow().getCell(i).getStringCellValue().trim().equals(colName)) {
                    colNum = i;
                }
            }
            if (colNum == -1) {
                return false;
            }

            getSheet().autoSizeColumn(colNum);
            setRow(getSheet().getRow(rowNum - 1));
            if (getRow() == null) {
                setRow(getSheet().createRow(rowNum - 1));
            }

            setCell(getRow().getCell(colNum));
            if (getCell() == null) {
                setCell(getRow().createCell(colNum));
            }

            getCell().setCellValue(data);

            setFileOut(new FileOutputStream(getPath()));

            getWorkbook().write(getFileOut());

            getFileOut().close();

        } catch (Exception e) {
            System.out.println("Failed to set cell value");
            return false;
        }
        return true;
    }

    /**
     * Getter.
     * @return the path
     */
    public String getPath() {
        return path;
    }

    /**
     * Setter.
     * @param path the path to set
     */
    public void setPath(final String path) {
        this.path = path;
    }

    /**
     * Getter.
     * @return the fis
     */
    public FileInputStream getFis() {
        return fis;
    }

    /**
     * Setter.
     * @param fis the fis to set
     */
    public void setFis(final FileInputStream fis) {
        this.fis = fis;
    }

    /**
     * Getter.
     * @return the workbook
     */
    public XSSFWorkbook getWorkbook() {
        return workbook;
    }

    /**
     * Setter.
     * @param workbook the workbook to set
     */
    public void setWorkbook(final XSSFWorkbook workbook) {
        this.workbook = workbook;
    }

    /**
     * Getter.
     * @return the sheet
     */
    public XSSFSheet getSheet() {
        return sheet;
    }

    /**
     * Setter.
     * @param sheet the sheet to set
     */
    public void setSheet(final XSSFSheet sheet) {
        this.sheet = sheet;
    }

    /**
     * Getter.
     * @return the row
     */
    public XSSFRow getRow() {
        return row;
    }

    /**
     * Setter.
     * @param row the row to set
     */
    public void setRow(final XSSFRow row) {
        this.row = row;
    }

    /**
     * Getter.
     * @return the cell
     */
    public XSSFCell getCell() {
        return cell;
    }

    /**
     * Setter.
     * @param cell the cell to set
     */
    public void setCell(final XSSFCell cell) {
        this.cell = cell;
    }

    /**
     * Getter.
     * @return the fileOut
     */
    public FileOutputStream getFileOut() {
        return fileOut;
    }

    /**
     * Setter.
     * @param fileOut the fileOut to set
     */
    public void setFileOut(final FileOutputStream fileOut) {
        this.fileOut = fileOut;
    }

}


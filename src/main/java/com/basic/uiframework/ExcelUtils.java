package com.basic.uiframework;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class ExcelUtils {
    
	public String[][] getExcelData(String excelFilePath, String sheetName) throws IOException{
		String[][] arrayExcelData = null;
		FileInputStream inputStram = new FileInputStream(new File(excelFilePath));
		XSSFWorkbook wb = new XSSFWorkbook(inputStram);
		XSSFSheet sheet = wb.getSheet(sheetName);
		
		XSSFRow row;
		//XSSFCell cell;
				
		int rowCount = sheet.getPhysicalNumberOfRows();
		row = sheet.getRow(0);
		int totalCols = row.getLastCellNum();
		
		arrayExcelData = new String[rowCount-1][totalCols];
		
		for (int i= 1 ; i < rowCount; i++) {

			for (int j=0; j < totalCols; j++) {
				arrayExcelData[i-1][j] = sheet.getRow(i).getCell(j).toString();
			}
		}
		wb.close();
		inputStram.close();
		return arrayExcelData;
   }
	
	//set value in the cell
	public void setCellValue(String excelPath, String sheetName, String testId, String status) {
		try {
			FileInputStream inputStream = new FileInputStream(new File(excelPath));
			Workbook workbook = new XSSFWorkbook(inputStream);
			Sheet testCaseSheet = workbook.getSheet(sheetName);
			
			Iterator<Row> iterator = testCaseSheet.iterator();
			
			while (iterator.hasNext()) {
				Row nextRow = iterator.next();
				Iterator<Cell> cellIterator = nextRow.iterator();
				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();
					if(cell.getCellTypeEnum()== CellType.STRING) {
						if(cell.getRichStringCellValue().getString().trim().equalsIgnoreCase(testId)) {
							cell.setCellValue(status);
						}
					}
				}
			}
			
			FileOutputStream outputStream = new FileOutputStream(excelPath);
			workbook.write(outputStream);
			workbook.close();
			outputStream.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
            e.printStackTrace();
        }
	}
	
}

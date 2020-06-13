package Setup;

import java.io.File;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExportToExcel {
	// **** Method to write Test Results into Excel ****
	public void write_Into_Excel(String fileName, String sheetName, String testCaseName, String testStatus)
			throws IOException {

		File filenew = new File(System.getProperty("user.dir") + "//" + fileName);
		InputStream file = new FileInputStream(filenew);
		XSSFWorkbook workbook = new XSSFWorkbook(file);
		XSSFSheet sheet = workbook.getSheet(sheetName);
		int totalRow = sheet.getLastRowNum();
		for (int i = 1; i <= totalRow; i++) {
			XSSFRow row = sheet.getRow(i);
			String ce = row.getCell(1).getStringCellValue();
			if (ce.contains(testCaseName)) {
				row.getCell(2).setCellValue(testStatus);
				file.close();
				FileOutputStream outFile = new FileOutputStream(filenew);
				workbook.write(outFile);
				outFile.close();
				break;
			}

		}
		workbook.close();

	}
}

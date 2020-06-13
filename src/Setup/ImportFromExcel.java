package Setup;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ImportFromExcel {

	// **** Method to get data from Excel ****
	public ArrayList<String> getdata(String SheetName, String testcaseName) throws IOException {
		ArrayList<String> arr = new ArrayList<String>();
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "//src//Test data//TEST_DATA.xlsx");
		XSSFWorkbook workbook = new XSSFWorkbook(fis);

		// To get the no of sheets in Excel
		int sheets = workbook.getNumberOfSheets();
		for (int i = 0; i < sheets; i++) {
			if (workbook.getSheetName(i).equalsIgnoreCase(SheetName)) {
				XSSFSheet sheet = workbook.getSheetAt(i);
				Iterator<Row> rows = sheet.iterator();// To iterate through the rows
				int column = 0;
				while (rows.hasNext()) {
					Row r = rows.next();
					// To check the row in which the given data is present
					if (r.getCell(column).getStringCellValue().equalsIgnoreCase(testcaseName)) {
						Iterator<Cell> cd = r.cellIterator();
						while (cd.hasNext()) {
							Cell c = cd.next();
							// To check the Cell Type
							if (c.getCellType() == CellType.STRING) {
								// To directly pass the String value if Cell Type is String
								arr.add(c.getStringCellValue());
							} else {
								// To Convert it into String and pass the value if Cell Type is Numeric
								arr.add(NumberToTextConverter.toText(c.getNumericCellValue()));
							}

						}
					}

				}

			}
		}
		workbook.close();
		return arr; // To return the ArrayList to TestData

	}

}

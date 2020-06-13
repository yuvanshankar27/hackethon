package Setup;

import java.io.IOException;
import java.util.ArrayList;

public class TestData {
	int dataNo = 1;

	// **** Method to specify the Data needed from Excel ****
	public String get_Data(String SheetName, String Value) throws IOException {
		ImportFromExcel readData = new ImportFromExcel();
		ArrayList<String> list = readData.getdata(SheetName, Value);
		String data = (String) list.get(dataNo);
		return data;
	}
}
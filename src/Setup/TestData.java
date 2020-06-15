package Setup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

public class TestData {
	public static Properties property;
	int dataNo;
	// ** Method to specify the Data needed from Excel **
	public String get_Data(String SheetName, String Value) throws IOException {
		property = BaseClass.invoke_Property_File();
		 dataNo = Integer.parseInt(property.getProperty("UserDataNo"));
		ImportFromExcel readData = new ImportFromExcel();
		ArrayList<String> list = readData.getdata(SheetName, Value);
		String data = (String) list.get(dataNo);
		return data;
	}
}
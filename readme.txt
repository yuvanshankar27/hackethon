                                                        MakeMyTrip - Hackethon Project


This project Test the functionality of:
1. Booking one way outstation cab.
2. Booking roundway trip Flight.
2. Find Group Gifting in Gift Cards, fill card details & give invalid email to check the error message .
3. On the Hotel booking page, extract all the available numbers for Adult persons.

Getting Started:
The below instructions will allow you to deploy the project on a live system for testing purposes.

Prerequisites:
Java    - jdk 14 - jre 1.8.
eclipse - Eclipse IDE for Java Developers - 2020-03
Maven   - apache-maven-3.6.3
Jenkins - 2.238 - Generic Java Package

Save your project in eclipse-workspace folder. Open your project in eclipse by File->Import->Existing Project. Save the pom.xml file for successful build.

Built With:
1. Maven   - Dependency Management.
2. Grid    - Multiple node Execution.
3. Jenkins - Build Management.

Script Details:
· Test Script Name : MakeMyTrip-Hackethon
· Naming Convention: CamelCase Notation.

· Packages Developed:
1. Pages      - Contains the different pages in the website
2. Setup      - Contains the utilities
3. TestCases  - Contains the testcases.

· Classes Developed:
1. ValidTestCases   - Contains all the valid test cases
2. InvalidTestCases - Contains all the valid test cases
3. ImportFromExcel  - Used to get data from Excel
4. ExportToExcel    - Used to write Test Results into Excel
5. BaseClass        - Contains the commonly used methods.
6. TestData         - Used to specify the data to be extracted from Excel.
7. CabSearch        - Contains all the functionalities of cab page
8. FlightSearch     - Contains all the functionalities of Flight page
9. GiftCards        - Contains all the functionalities of GiftCard page 
10. HomePage        - Contains all the functionalities of Home page
11. HotelBooking    - Contains all the functionalities of HotelBooking page 
12. LoginPage       - Contains all the functionalities of Login page


· Data Table excel details:
Excel file is located in the Test data Folder named TestData.

Running the test:
1. smoke.xml        - to perform smoke Testing.
2. Regression.xml   - to perform Regression Testing.
3. CabSearch.xml    - to test Cab functionalities
4. FlightSearch.xml - to test Flight functionalities
5. HotelBooking.xml - to test Hotel Booking functionalities
6. Login.xml        - to test Login functionalities
7. GiftCards.xml    - to test GiftCard functionalities
8. testng.xml       - to perform Complete Test.

Test Results:
(Refresh your workspace before Checking the test results)

1. Check the Console for the outputs of Print Statements in the code.
2. Open the Extent Report Generated from testoutput->ExtentReport.html.
3. You can also view the Screenshots of the failed testcases in ScreenShots folder.
4. Open the Test_Report.xlsx file to check the updated testcase-status and to update the defect log.

Build with Jenkins:
1. Open the cmd prompt and run 'java -jar jenkins.war -httpPort=8080' to invoke jenkins.
2. Create a new Job and add your project path.
3. Give your Java and Maven Environmental variables path in Configure menu.

To run as Grid:
1. Move the SeleniumGrid folder to the C drive.
2. Run the StartHub batch file.
3. Run the StartNode batch file and enter the ip address of your pc.
(Note: Donot close the command prompts while running the grid)
4. Go to http://localhost:4444/grid/console to verify that grid is initiated successfully.
5. Change the runtype to Grid in PropertyFile.properties and run the test.



SET dir=C:\SeleniumGrid
SET /p hubIP=Enter Selenium Hub IP Address:
ECHO %dir%
IF EXIST "%dir%\selenium-server-standalone.jar" (
    ECHO Selenium jar is exist at location : %dir%\selenium-server-standalone.jar
) ELSE (
    mkdir %dir%
	bitsadmin.exe /transfer "Download SeleniumGrid" https://selenium-release.storage.googleapis.com/3.141.59/selenium-server-standalone-3.141.59.jar %dir%\selenium-server-standalone.jar
)

ECHO %dir% -Directory is Created & All utilities are configured
start cmd.exe /k "cd /d %dir% && java -Dwebdriver.chrome.driver=^"%dir%\Webdrivers\chromedriver.exe^" -Dwebdriver.opera.driver=^"%dir%\Webdrivers\operadriver.exe^" -Dwebdriver.ie.driver=^"%dir%\Webdrivers\IEDriverServer.exe^" -Dwebdriver.edge.driver=^"%dir%\Webdrivers\msedgedriver.exe^" -Dwebdriver.gecko.driver=^"%dir%\Webdrivers\geckodriver.exe^" -jar selenium-server-standalone.jar -role webdriver -hub http://%hubIP%:4444/grid/register -browser browserName=MicrosoftEdge,maxInstances=10  -browser  browserName=iexplore,maxInstances=10 -browser  browserName=opera,maxInstances=10 -browser browserName=firefox,maxInstances=10 -browser browserName=chrome,maxInstances=10 "
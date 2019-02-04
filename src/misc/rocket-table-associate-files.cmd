@where javaw >%TEMP%\rocket-table_javaw.var
@set /p JAVAW=<%TEMP%\rocket-table_javaw.var
@del %TEMP%\rocket-table_javaw.var

@reg add HKCU\SOFTWARE\Classes\.csv /d "rocket-table" /f
@reg add HKCU\SOFTWARE\Classes\.sas7bdat /d "rocket-table" /f
@reg add HKCU\SOFTWARE\Classes\.sbdf /d "rocket-table" /f
@reg add HKCU\SOFTWARE\Classes\.stdf /d "rocket-table" /f

@reg add HKCU\SOFTWARE\Classes\rocket-table\shell\open\command /d "\"%JAVAW%\" -jar \"%~dp0rocket-table.jar\" \"%%1\"" /f

# Rocket table

## About
Rocket table is a lightweight table viewer for files in SAS7BDAT, SBDF, STDF and CSV formats.

![Application screenshot](https://xantorohara.github.io/rocket-table/screenshot.png)

## Download
[rocket-table-1.1.3-beta.zip](https://xantorohara.github.io/rocket-table/rocket-table-1.1.3-beta.zip)

Previous versions are available as well at the 
[project repository](https://github.com/xantorohara/rocket-table/tree/master/docs)

## Changelog
* 2021.01.10 v1.1.3 - Removed "Programs" support as it was confusing everyone. Added command line parameter to specify
  type of SAS date interpretation.
* 2019.03.08 v1.1.2 - Local and shared "Programs" configs
* 2019.03.07 v1.1.1 - Upgraded to Parso v2.0.11, added "Programs" support
* 2018.07.14 v1.0.12 - Published sources
* 2018.06.08 v1.0.11 - Changed license to Apache License v2.0
* 2018.06.05 v1.0.10 - Upgraded to Parso v2.0.9
* 2017.04.05 v1.0.9 - Added encoding command line parameter
* 2017.03.19 v1.0.7 - Added support for STDF (Spotfire Text Data File) format
* 2017.03.09 v1.0.6 - Added support for SBDF (Spotfire Binary Data File) format
* 2016.04.10 v1.0.5 - Upgraded to Parso v1.2.1
* ...
* 2015.01.10 v1.0.1 - Initial version

## Features
* Open files in SAS7BDAT, SBDF and CSV formats
* Real-time search over the data table
* Search using wildcard patterns
* Search by specific columns
* Show only specific set of columns
* Highlight matched rows
* Filter matched rows
* Sorting
* Truncate options
* Count total, matched, selected and unique rows
* Cross-platform (Windows, Linux, Mac OS X)
* Minimalistic flat design
* Small application size (around 100KB)

## Hotkeys

* **Alt+S** - focus to "Search" input
* **Alt+C** - focus to "Columns" input
* **Ctrl+Space** - autocomplete column name
* **Alt+F** - toggle "Filter" mode
* **Alt+U** - toggle "Unique" mode
* **Alt+O** - show "Open file" dialog
* **Alt+E** - show "Export table data" dialog
* **Alt+T** - focus to "Truncate" menu

## Search wildcards
* **?** -  match any single character
* \* -  match any string (including the empty string)

Wildcard characters can be anywhere in the search string, e.g:

* **word*** -  starts with "word"
* **\*word** -  ends with "word"
* **\*word*** -  contains "word"


## Search flags
* **~** - enable case-insensitive search
* **!** - inverse match

Flag characters should be at the leading positions in the string, e.g:

* **!word** - not equals to "word"
* **~word*** - starts with "Word" or "word"
* **~!\*word*** - not contains "Word" or "word", or "wOrD"


## Search examples
| Search string | Match result |
|---------------|--------------|
|\*berry | Match all rows with berries (e.g.: Blueberry, Strawberry, etc.)|
|CMSCAT=\*THERAPY | Match all medications with category name ending with "THERAPY" (e.g.: CHEMOTHERAPY, RADIOTHERAPY and HORMONAL THERAPY)|
|COLUMN1=VALUE1, COLUMN2=VALUE2, COLUMN3=VALUE3 | Match all rows where (COLUMN1=VALUE1 AND COLUMN2=VALUE2 AND COLUMN3=VALUE3)|
|SEX=Male, COUNTRY=USA, DEGREE=Academic | Match all men from the USA who have an Academic degree|
|AGE=6?, AGE=7? | Match all patients aged 60-70 years old (from 60 to 79)|
|COLUMN1=VALUE1, COLUMN1=VALUE2, COLUMN2=VALUE3 | Match all rows where ((COLUMN1=VALUE1 OR COLUMN1=VALUE2) AND COLUMN2=VALUE3)|
|SEX=Male, COUNTRY=USA, COUNTRY=Canada, DEGREE=Bachelor, DEGREE=Master | Match all men from the USA or Canada who have a Bachelor or Master degree|
|COUNTRY=!USA | Match all patients not from the USA|
|COUNTRY=!USA, GENDER=~MaLe | Match all men not from the USA|

## Associate table files with Rocket Table in Windows

Just execute this script from the directory where the rocket-table.jar is located:

```cmd
@where javaw >%TEMP%\rocket-table_javaw.var
@set /p JAVAW=<%TEMP%\rocket-table_javaw.var
@del %TEMP%\rocket-table_javaw.var

@reg add HKCU\SOFTWARE\Classes\.csv /d "rocket-table" /f
@reg add HKCU\SOFTWARE\Classes\.sas7bdat /d "rocket-table" /f
@reg add HKCU\SOFTWARE\Classes\.sbdf /d "rocket-table" /f
@reg add HKCU\SOFTWARE\Classes\.stdf /d "rocket-table" /f

@reg add HKCU\SOFTWARE\Classes\rocket-table\shell\open\command /d "\"%JAVAW%\" -jar \"%~dp0rocket-table.jar\" \"%%1\"" /f
```

## Command line parameters

* **--encoding=EncodingName** - specify encoding for input files. E.g.: --encoding=Cp1250
* **--sas-date-format-type=FORMAT_TYPE** - specify date format for input files.  
  E.g.:
  --sas-date-format-type=JAVA_DATE (default) | SAS_FORMAT_TRIM_EXPERIMENTAL | SAS_VALUE | EPOCH_SECONDS

## Libraries used

- Parso v2.0.14
- SBDF v6.5.0

## Contacts

For questions, feature requests or technical support related to this application you can contact
[xantorohara@gmail.com](mailto:xantorohara@gmail.com)

## Build from sources

1. Clone or download [Rocket Table repository](https://github.com/xantorohara/rocket-table)
2. Build it using maven:
```bash
mvn clean package
```

## License

Rocket Table by Xantorohara is licensed under Apache License v2.0.

&copy;Xantorohara, 2015-2021

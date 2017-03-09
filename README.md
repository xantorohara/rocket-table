## About
Rocket Table is a lightweight viewer for files in SAS7BDAT, SBDF and CSV formats.

![Rocket Table screenshot](screenshot.png)

Rocket Table is a successor of the [SAS Table Explorer](https://github.com/xantorohara/sas-table-explorer)

## Download
[rocket-table-1.0.7.zip](rocket-table-1.0.7.zip)

## Changelog
* 1.0.7 - Added support for SBDF (Spotfire Binary Data File) format

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
* Ultra small size (less than 100KB)

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
* ***** -  match any string (including the empty string)

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

## Contacts
For questions, feature requests or technical support related to this application you can contact
[xantorohara@gmail.com](mailto:xantorohara@gmail.com)

## License

Rocket Table binaries by Xantorohara are licensed under a
Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License.

Version 1.0.7, published 2015-01-10, updated 2017-03-10


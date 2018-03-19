::echo %webroot_filepath%
set static_file_path=%webroot_filepath%\main\webapp\statictarget\js
set ftl_filepath=%webroot_filepath%\main\webapp\WEB-INF\freemarker\modules
set config_filepath=%webroot_filepath%\main\webapp\WEB-INF\freemarker\modules\public\config_assign_static.ftl
::echo %static_file_path%
cd %static_file_path%
echo [#-- --]> %config_filepath%

@echo off

for /f "skip=1 delims=:" %%a in ('^(echo "%static_file_path%"^&echo.^)^|findstr /o ".*"') do set /a length=%%a-5


for /R %%s in (.,*) do ( 
 	set static_file=%%s
 	set static_file=!static_file:~%length%!
 	set static_file_tempname=!static_file:\=_!
 	set static_file_tempname=!static_file_tempname:.=_!
 	set static_file_tempname=!static_file_tempname:~0,-9!

 	set static_file=!static_file:\=^|!
 	set laststr=!static_file:~-2!
  	if  !laststr! == js (
  	  	echo =========!static_file_tempname!
	  	set str_line=
	  	set str_line=!str_line![#assign c_p_static__!static_file_tempname!="
	  	set str_line=!str_line!^|mall^|web^|pc^|js^|
	  	set str_line=!str_line!!static_file!
	    set str_line=!str_line!"/]
	    echo !str_line! >> %config_filepath%
	) 
) 





for /f "tokens=1 delims= " %%b in (C:\ftp.conf) do (
		set sslusername=%%b
)
for /f "tokens=2 delims= " %%b in (C:\ftp.conf) do (
		set sslpassword=%%b
)
for /f "tokens=3 delims= " %%b in (C:\ftp.conf) do (
		set sslhost=%%b
)
::echo %sslusername%
::echo %sslpassword%
::echo %webroot_filepath%\src\main\webapp\statictarget\js
::call C:\work\lftp-4.6.5.win64-openssl\bin\lftp.exe -u %sslusername%,%sslpassword% ftp://%sslhost%
call C:\work\lftp-4.6.5.win64-openssl\bin\lftp.exe -u %sslusername%,%sslpassword% ftp://%sslhost% -e "lcd %webroot_filepath%/;mirror -R --ignore-time --verbose main/webapp/statictarget/js \"/mall/web/pc/\";bye;"
@echo off
rem ---------------------------------------------------------------------------
rem please DO NOT rename this file (fisstatic.bat)
rem ---------------------------------------------------------------------------
setlocal enabledelayedexpansion

rem Suppress Terminate batch job on CTRL+C
if not ""%1"" == ""run"" goto mainEntry
if "%TEMP%" == "" goto mainEntry
if exist "%TEMP%\%~nx0.run" goto mainEntry
echo Y>"%TEMP%\%~nx0.run"
if not exist "%TEMP%\%~nx0.run" goto mainEntry
echo Y>"%TEMP%\%~nx0.Y"
call "%~f0" %* <"%TEMP%\%~nx0.Y"
rem Use provided errorlevel
set RETVAL=%ERRORLEVEL%
del /Q "%TEMP%\%~nx0.Y" >NUL 2>&1
exit /B %RETVAL%
:mainEntry
del /Q "%TEMP%\%~nx0.run" >NUL 2>&1

rem Guess SRC_HOME if not defined
set "CURRENT_DIR=%cd%"
if not "%SRC_HOME%" == "" goto gotHome
set "SRC_HOME=%CURRENT_DIR%"
if exist "%SRC_HOME%\shell\fisstatic.bat" goto okHome
cd ..
set "SRC_HOME=%cd%"
cd "%CURRENT_DIR%"
:gotHome

if exist "%SRC_HOME%\shell\fisstatic.bat" goto okHome
echo The SRC_HOME environment variable is not defined correctly
echo This environment variable is needed to run this program
goto end
:okHome
rem ####################################
rem #Section 1: Set Shell Const
rem ####################################
set WEB_ROOT=%SRC_HOME%\main\webapp
set JS_ROOT=%WEB_ROOT%\static
set JS_PUB_ROOT=%WEB_ROOT%\statictarget
set FTP_SYNC_ROOT=/mall/web/pc/js
set FTP_CONF_FILE=%USERPROFILE%\ftp.conf
set MODIFY_FTL_SCRIPT=%SRC_HOME%\shell\modifyftl.js
set JS_RES_FILE=%WEB_ROOT%\WEB-INF\freemarker\modules\public\config_assign_static.ftl

rem ####################################
rem #Section 2: Publish JS
rem ####################################
if "%JS_PUB_ROOT:~-1%" equ "\" (
  echo ERROR: the variable of %%JS_PUB_ROOT%% cannot end with '\'
  exit 21
)
echo js publish directory:%JS_PUB_ROOT%
if exist "%JS_PUB_ROOT%" (
  echo start to clean js publish directory ...
  rd /s /q "%JS_PUB_ROOT%"
  if %errorlevel% equ 0 (
    echo clean js publish directory ok
  ) else (
    echo fail to clean js publish directory
    exit 22
  )
)
echo start to publish js...
call fis release -opm -r "%JS_ROOT%" -d "%JS_PUB_ROOT%"
if %errorlevel% equ 0 (
  echo publish js ok
) else (
  echo fail to publish js
  exit 23
)
rem ###############################################
rem #Section 3: Sync JS Publishments To FTP Server
rem ###############################################
if "%FTP_SYNC_ROOT:~-1%" equ "/" (
  echo ERROR: the variable of %%FTP_SYNC_ROOT%% cannot end with '/'
  exit 31
)
if exist "%FTP_CONF_FILE%" (
  echo loading ftp config...
) else (
  echo ftp config file does not exist: %FTP_CONF_FILE%
  exit 32
)
set /p FTP_CONFIG=<"%FTP_CONF_FILE%"
for /f "tokens=1" %%b in ('echo %FTP_CONFIG%') do set FTP_USER=%%b
for /f "tokens=2" %%b in ('echo %FTP_CONFIG%') do set FTP_PASS=%%b
for /f "tokens=3" %%b in ('echo %FTP_CONFIG%') do set FTP_HOST=%%b
if "%FTP_HOST%" == "" (
  echo FTP Host Name can not be empty
  exit 33
)
if "%FTP_USER%" == "" (
  echo "FTP User Name can not be empty"
  exit 34
)
if "%FTP_PASS%" == "" (
  echo "FTP Password can not be empty"
  exit 35
)
echo start to sync ftp server:%FTP_HOST% ...
call lftp.exe -u %FTP_USER%,%FTP_PASS% ftp://%FTP_HOST% -e "lcd \"%JS_PUB_ROOT%\";mirror -R --ignore-time --verbose \"\" %FTP_SYNC_ROOT%;bye;"
if %errorlevel% equ 0 (
  echo sync ftp server complete
) else (
  echo "fail to sync ftp server"
  exit 36
)
rem ###############################################
rem #Section 4: Genergate JS Resource Conf Template
rem ###############################################
echo start to generate JS_RES_FILE
call node "%MODIFY_FTL_SCRIPT%" "%JS_PUB_ROOT%" "%FTP_SYNC_ROOT%" "%JS_RES_FILE%"
if %errorlevel% equ 0 (
  echo generate JS_RES_FILE ok
) else (
  echo fail to generate JS_RES_FILE
  exit 41
)
goto end
:end
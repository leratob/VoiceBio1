@echo off
echo Please read the following End User License Agreement (EULA) carefully and answer the prompt that follows it.
set /p acceptContinue=Press "enter" to continue:
type .\jtapi-sdk\LICENSE.txt | more

:getConfirmation
set /p acceptEULA=Do you agree to the terms of the EULA? [y/n]:
if /I "%acceptEULA%"=="n" goto cancelDeploy
if /I "%acceptEULA%"=="y" goto deployCode
goto getConfirmation

:deployCode
set acceptDir=C:\
set /p acceptDir=Enter the directory name to install the AE Services JTAPI SDK [C:\]: 
echo Copying files ...
xcopy /I/E/Q %TEMP%\WZSE0.TMP\jtapi-sdk %acceptDir%\jtapi-sdk
echo .
echo JTAPI SDK installed to %acceptDir%
goto end

:cancelDeploy
echo .
echo Aborting JTAPI SDK installation.
goto end

:end
echo .
set /p acceptEnd=Hit "ENTER" to complete the SDK installation:
rmdir /S/Q %TEMP%WZSE0.TMP

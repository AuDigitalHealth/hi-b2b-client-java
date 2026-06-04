@echo off
setlocal
set "P="
if /i "%~1"=="shaded" set "P=-Pfat-jar "
if /i "%~1"=="-shaded" set "P=-Pfat-jar "
if /i "%~1"=="--shaded" set "P=-Pfat-jar "
if not "%~1"=="" if not defined P (
    echo Unknown argument: %~1 >&2
    echo Optional: shaded --shaded -shaded >&2
    exit /b 2
)
pushd "%~dp0" || exit /b 1
set "SARGS="
if defined MVN_SETTINGS set "SARGS=-s %MVN_SETTINGS% "
if defined P (
    echo Building hi-b2b-client FAT/UBER JAR
) else (
    echo Building hi-b2b-client JAR
)
mvn -B %SARGS%%P%-Dgpg.skip=true clean verify
set "EXIT=%ERRORLEVEL%"
popd
exit /b %EXIT%

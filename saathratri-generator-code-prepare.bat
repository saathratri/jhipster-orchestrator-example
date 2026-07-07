@echo off
setlocal
REM Windows companion for saathratri-generator-code-prepare.sh: runs it under Git Bash, forwarding all
REM arguments. NOTE: a bare `bash` must NOT be used here -- on Windows it resolves
REM to the WSL relay in System32, which fails when no WSL distro is set up.
set "GITBASH="
for /f "delims=" %%i in ('where git 2^>nul') do if not defined GITBASH if exist "%%~dpi..\bin\bash.exe" set "GITBASH=%%~dpi..\bin\bash.exe"
if not defined GITBASH if exist "%ProgramFiles%\Git\bin\bash.exe" set "GITBASH=%ProgramFiles%\Git\bin\bash.exe"
if not defined GITBASH if exist "%LocalAppData%\Programs\Git\bin\bash.exe" set "GITBASH=%LocalAppData%\Programs\Git\bin\bash.exe"
if not defined GITBASH (
  echo Git Bash not found -- install Git for Windows.
  exit /b 1
)
"%GITBASH%" "%~dp0saathratri-generator-code-prepare.sh" %*
exit /b %ERRORLEVEL%

@echo off
setlocal
echo ===================================================
echo   Starting CodeClash LAN 1v1 Battle Server...
echo ===================================================
echo.

:: Add local portable Maven to PATH if present
if exist "%~dp0tools\maven\bin" (
    set "PATH=%~dp0tools\maven\bin;%PATH%"
)

:: Show local IP address for LAN peers
echo [LAN Host Information]
for /f "tokens=4" %%a in ('route print ^| findstr 0.0.0.0 ^| findstr /v "Persistent"') do (
    echo   Host Local IP Address: %%a
    echo   Opponents on Wi-Fi connect to: http://%%a:8080
    goto :ip_done
)
:ip_done

echo.
echo Starting Spring Boot Backend on http://localhost:8080 ...
echo (The first run will download project dependencies, please wait a moment)
echo.

call mvn spring-boot:run -Dspring-boot.run.profiles=local

pause

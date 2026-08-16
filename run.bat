@echo off
echo ===================================================
echo   Starting CodeClash LAN 1v1 Battle Server...
echo ===================================================
echo.

:: Show local IP address for LAN peers
echo [LAN Host Info]
for /f "tokens=4" %%a in ('route print ^| findstr 0.0.0.0 ^| findstr /v "Persistent"') do (
    echo Host Local IP Address: %%a
    echo Opponents on same Wi-Fi connect to: http://%%a:8080
    goto :ip_done
)
:ip_done

echo.
echo Starting Spring Boot Application on port 8080...
echo Open http://localhost:8080 in your browser.
echo.

:: Try mvnw first, then mvn, then direct java
if exist "mvnw.cmd" (
    call mvnw.cmd spring-boot:run
) else (
    call mvn spring-boot:run
)

pause

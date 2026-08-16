$dest = "C:\Users\Krish\.gemini\antigravity\scratch\CodeClash\tools"
$zip = "$dest\maven.zip"
$mavenHome = "$dest\maven"

if (-not (Test-Path "$mavenHome\bin\mvn.cmd")) {
    New-Item -ItemType Directory -Path $dest -Force | Out-Null
    Write-Host "Downloading Apache Maven 3.9.9..."
    Invoke-WebRequest -Uri "https://archive.apache.org/dist/maven/maven-3/3.9.9/binaries/apache-maven-3.9.9-bin.zip" -OutFile $zip
    Write-Host "Extracting..."
    Expand-Archive -Path $zip -DestinationPath $dest -Force
    if (Test-Path "$dest\apache-maven-3.9.9") {
        Rename-Item -Path "$dest\apache-maven-3.9.9" -NewName "maven" -Force
    }
    Remove-Item $zip -Force -ErrorAction SilentlyContinue
}

Write-Host "Testing Maven..."
& "$mavenHome\bin\mvn.cmd" -version

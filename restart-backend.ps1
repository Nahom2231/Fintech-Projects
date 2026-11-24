$ErrorActionPreference = "Stop"

Set-Location $PSScriptRoot

$port = [int]$env:SERVER_PORT
if ($port -eq 0) {
    $port = 8081
}

$listeners = Get-NetTCPConnection -LocalPort $port -State Listen -ErrorAction SilentlyContinue

foreach ($listener in $listeners) {
    $process = Get-CimInstance Win32_Process -Filter "ProcessId = $($listener.OwningProcess)" -ErrorAction SilentlyContinue

    if ($process -and
        ($process.CommandLine -match "onboarding" -or
         $process.ExecutablePath -match "java")) {
        Write-Host "Stopping previous onboarding process (PID $($listener.OwningProcess))..."
        Stop-Process -Id $listener.OwningProcess -Force
    } else {
        throw "Port $port is in use by an unrelated process (PID $($listener.OwningProcess))."
    }
}

for ($attempt = 0; $attempt -lt 20; $attempt++) {
    if (-not (Get-NetTCPConnection -LocalPort $port -State Listen -ErrorAction SilentlyContinue)) {
        break
    }
    Start-Sleep -Milliseconds 250
}

if (Get-NetTCPConnection -LocalPort $port -State Listen -ErrorAction SilentlyContinue) {
    throw "Port $port is still in use after stopping the previous process."
}

& ".\mvnw.cmd" spring-boot:run

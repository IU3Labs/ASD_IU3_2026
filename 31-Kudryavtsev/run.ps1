param(
    [switch]$Benchmarks,
    [string]$JavaHome = $env:JAVA_HOME,
    [string]$MavenPath
)

$ErrorActionPreference = 'Stop'
$originalJavaHome = $env:JAVA_HOME
$candidates = @($JavaHome)
$candidates += @(Get-ChildItem -Path "$env:USERPROFILE\.jdks" -Directory -ErrorAction SilentlyContinue |
    Select-Object -ExpandProperty FullName)
$selectedJava = $null
foreach ($candidate in $candidates) {
    if ($candidate -and (Test-Path -LiteralPath "$candidate\bin\javac.exe")) {
        $version = & "$candidate\bin\javac.exe" -version 2>&1
        if ("$version" -match '^javac 25\.') {
            $selectedJava = $candidate
            break
        }
    }
}
if (-not $selectedJava) {
    throw 'JDK 25 not found. Pass -JavaHome <path-to-jdk-25>.'
}

if (-not $MavenPath) {
    $mavenCommand = Get-Command mvn.cmd -ErrorAction SilentlyContinue
    if ($mavenCommand) {
        $MavenPath = $mavenCommand.Source
    } else {
        $MavenPath = Get-ChildItem -Path "$env:ProgramFiles\JetBrains\*\plugins\maven\lib\maven3\bin\mvn.cmd" `
            -File -ErrorAction SilentlyContinue | Select-Object -Last 1 -ExpandProperty FullName
    }
}
if (-not $MavenPath -or -not (Test-Path -LiteralPath $MavenPath)) {
    throw 'Maven not found. Pass -MavenPath <path-to-mvn.cmd>.'
}

Push-Location $PSScriptRoot
try {
    $env:JAVA_HOME = $selectedJava
    & $MavenPath -B -ntp clean test
    if ($LASTEXITCODE -ne 0) {
        throw 'Maven tests or coverage check failed.'
    }
    if ($Benchmarks) {
        & "$selectedJava\bin\java.exe" -Xms512m -Xmx512m `
            -cp 'target/test-classes;target/classes' ru.bmstu.lab2.bench.Benchmarks benchmarks.md
        if ($LASTEXITCODE -ne 0) {
            throw 'Benchmarks failed.'
        }
    }
} finally {
    $env:JAVA_HOME = $originalJavaHome
    Pop-Location
}

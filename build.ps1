#Requires -Version 5.1
# Build from source: mvn clean verify from repository root.
# WSDL tree override: HI_WSDL_TREE_ROOT or -Dhi.wsdl.tree.root=... (see CONTRIBUTING.md).
# Optional: $env:MVN_SETTINGS = path to Maven user settings file (-s).
param(
    [switch]$Shaded
)
Set-StrictMode -Version Latest
$ErrorActionPreference = 'Stop'
Set-Location -LiteralPath $PSScriptRoot
foreach ($a in $args) {
    if ($a -match '^(?i)(shaded|-shaded|--shaded)$') {
        $Shaded = $true
    }
    else {
        [Console]::Error.WriteLine("Unknown argument: $a")
        [Console]::Error.WriteLine("Optional: -Shaded | shaded | -shaded | --shaded")
        exit 2
    }
}
$mvnArgs = @('-B')
if ($null -ne $env:MVN_SETTINGS -and $env:MVN_SETTINGS.Trim().Length -gt 0) {
    $mvnArgs += @('-s', $env:MVN_SETTINGS.Trim())
}
if ($Shaded) {
    Write-Output 'Building hi-b2b-client FAT/UBER JAR'
    $mvnArgs += @('-Pfat-jar')
}
else {
    Write-Output 'Building hi-b2b-client JAR'
}
$mvnArgs += @('-Dgpg.skip=true', 'clean', 'verify')
& mvn @mvnArgs
exit $LASTEXITCODE

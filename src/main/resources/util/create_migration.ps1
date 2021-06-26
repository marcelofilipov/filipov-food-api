param($name)

$timestamp = (Get-Date).ToString("yyyyMMddHHmmssfff")

$arrayVersion=$project_version -split "\."

$migration="V" + $timestamp + "__" + $name + ".sql"

New-Item "./src/main/resources/db/migration/$migration"

Set-Content "./src/main/resources/db/migration/$migration" "-- Script ..."

echo "Migration $migration criada com sucesso em /src/main/resources/db/migration/"
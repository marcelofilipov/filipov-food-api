#!/bin/bash

timestamp=$(date +"%Y%m%d%H%M%S%3N")

migration="V"$timestamp"__"$1".sql"

echo "-- Script ... " > "./src/main/resources/db/migration/"$migration

echo "Migration "$migration" criada com sucesso em /src/main/resources/db/migration/"

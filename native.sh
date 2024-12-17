#!/usr/bin/env bash 
rm -rf target
./mvnw -DskipTests -Pnative spring-boot:build-image
#!/bin/bash +x

echo "==================== 发布 annotation START ===================="
./gradlew :annotation:clean :annotation:build :annotation:bintrayUpload -PpublishAar
echo "==================== 发布 annotation END ===================="
echo "==================== 发布 api START ===================="
./gradlew :api:clean :api:build :api:bintrayUpload -PpublishAar
echo "==================== 发布 api END ===================="
echo "==================== 发布 compiler START ===================="
./gradlew :compiler:clean :compiler:build :compiler:bintrayUpload -PpublishAar
echo "==================== 发布 compiler END ===================="

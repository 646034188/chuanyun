#!/bin/sh

mvn clean -U package -P $ENV  -Dmaven.test.skip=true
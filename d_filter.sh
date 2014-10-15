#! /bin/sh -
tomcatHome=/data/app/tomcat
rm -rf $tomcatHome/webapps/mcp/*
cp -r ./mcp-order-gateway/build/libs/. $tomcatHome/webapps/mcp/.

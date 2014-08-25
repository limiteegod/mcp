#! /bin/sh -
tomcatHome=../tomcat-filter
rm -rf $tomcatHome/webapps/mcp-filter/*
rm -rf $tomcatHome/webapps/mcp/*
cp -r ./mcp-order-filter/build/libs/. $tomcatHome/webapps/mcp-filter/.
cp -r ./mcp-order-gateway/build/libs/. $tomcatHome/webapps/mcp/.

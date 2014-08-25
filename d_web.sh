#! /bin/sh -
tomcatHome=../tomcat-web
rm -rf $tomcatHome/webapps/ht
cp -r ./mcp-admin/build/libs/. $tomcatHome/webapps/ht/.

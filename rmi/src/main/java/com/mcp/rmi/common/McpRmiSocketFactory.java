package com.mcp.rmi.common;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.server.RMISocketFactory;

public class McpRmiSocketFactory extends RMISocketFactory {

	@Override
	public Socket createSocket(String host, int port) throws IOException {
		return new Socket(host, port);
	}

	@Override
	public ServerSocket createServerSocket(int port) throws IOException {
		if(port == 0)
		{
			port = Integer.parseInt(RmiConstants.RMI_DATA_PORT);
		}
		return new ServerSocket(port);
	}
}

package com.demo;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import java.io.IOException;

import com.demo.service.UserServiceImpl;

public class GrpcServerApplication {

	public static void main(String[] args) throws IOException, InterruptedException {
		System.out.println("Starting server");
		Server server = ServerBuilder.forPort(9090)
						.addService(new UserServiceImpl())
						.build();

		server.start();
		System.out.println("Server Started at "+ server.getPort());
		server.awaitTermination();
	}

}

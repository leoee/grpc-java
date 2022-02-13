package com.demo.client.grpcclient;

import com.demo.proto.generated.user.CreateUserRequest;
import com.demo.proto.generated.user.CreateUserResponse;
import com.demo.proto.generated.user.User;
import com.demo.proto.generated.user.UserServiceGrpc;
import com.demo.proto.generated.user.UserServiceGrpc.UserServiceBlockingStub;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
@SpringBootApplication
public class GrpcClientApplication {
  public static void main(String[] args) {
    System.out.println("Starting client");
		ManagedChannel channel = ManagedChannelBuilder
                              .forAddress("localhost", 9090)
                              .usePlaintext()
                              .build();

    UserServiceBlockingStub userServiceBlockingStub = UserServiceGrpc.newBlockingStub(channel);
    User userRequest = User.newBuilder().setId("123").setEmail("test@mail.com").setName("Test").build();
    CreateUserRequest createUserRequest = CreateUserRequest.newBuilder().setUser(userRequest).build();
    CreateUserResponse createUserResponse = userServiceBlockingStub.createUser(createUserRequest);
    System.out.println(createUserResponse);
	}
}

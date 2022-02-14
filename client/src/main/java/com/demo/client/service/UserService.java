package com.demo.client.service;

import com.demo.client.proto.generated.user.CreateUserRequest;
import com.demo.client.proto.generated.user.CreateUserResponse;
import com.demo.client.proto.generated.user.User;
import com.demo.client.proto.generated.user.UserServiceGrpc;
import com.demo.client.proto.generated.user.UserServiceGrpc.UserServiceBlockingStub;

import org.springframework.stereotype.Service;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

@Service
public class UserService {
  ManagedChannel channel = ManagedChannelBuilder
                            .forAddress("localhost", 9090)
                            .usePlaintext()
                            .build();

  public com.demo.client.entity.User createUser(com.demo.client.entity.User requestUser) {

    UserServiceBlockingStub userServiceBlockingStub = UserServiceGrpc.newBlockingStub(channel);
    User userRequest = User.newBuilder()
                        .setEmail(requestUser.getEmail())
                        .setName(requestUser.getName())
                        .build();
    CreateUserRequest createUserRequest = CreateUserRequest.newBuilder().setUser(userRequest).build();
    CreateUserResponse createUserResponse = userServiceBlockingStub.createUser(createUserRequest);

    com.demo.client.entity.User entityUser = new com.demo.client.entity.User();
    entityUser.setId(createUserResponse.getUser().getId());
    entityUser.setEmail(createUserResponse.getUser().getEmail());
    entityUser.setName(createUserResponse.getUser().getName());

    return entityUser;
  }
}

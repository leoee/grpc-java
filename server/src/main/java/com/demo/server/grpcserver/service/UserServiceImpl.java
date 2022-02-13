package com.demo.server.grpcserver.service;

import com.demo.proto.generated.user.CreateUserRequest;
import com.demo.proto.generated.user.CreateUserResponse;
import com.demo.proto.generated.user.User;
import com.demo.proto.generated.user.UserServiceGrpc.UserServiceImplBase;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;

import io.grpc.stub.StreamObserver;

public class UserServiceImpl extends UserServiceImplBase {
  private MongoClient mongoClient = MongoClients.create("mongodb://demo:demo@localhost:27017/?authSource=admin&readPreference=primary&appname=MongoDB%20Compass&ssl=false");
  private MongoDatabase database = mongoClient.getDatabase("GRPC");
  private MongoCollection<Document> collection = database.getCollection("User");

  @Override
  public void createUser(CreateUserRequest request,
                          StreamObserver<CreateUserResponse> responseObserver) {
    System.out.println("Executing createUser");

    User user = request.getUser();

    Document doc = new Document("name", user.getName())
                .append("email", user.getEmail());

    collection.insertOne(doc);
    String userId = doc.getObjectId("_id").toString();

    User userResponse = User.newBuilder()
                          .setId(userId)
                          .setEmail(user.getEmail())
                          .setName(user.getName())
                          .build();

    CreateUserResponse createUserResponse = CreateUserResponse
                                              .newBuilder()
                                              .setUser(userResponse)
                                              .build();

    responseObserver.onNext(createUserResponse);
    responseObserver.onCompleted();
  }
  
}
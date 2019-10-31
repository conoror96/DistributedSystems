/** Adapted from https://github.com/john-french/distributed-systems-labs/blob/master/grpc-async-inventory/README.md */
package ie.gmit.ds;

import com.google.protobuf.ByteString;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class PasswordServiceClient {
    /** CLIENT INITIALISATION */
    private static final Logger logger = Logger.getLogger(PasswordServiceClient.class.getName());
    // Channel connects to the gRPC Server
    private final ManagedChannel channel;
    // Async stub
    private final PasswordServiceGrpc.PasswordServiceStub asyncPasswordService;
    // blocking (sync) stub
    private final PasswordServiceGrpc.PasswordServiceBlockingStub syncPasswordService;


    public PasswordServiceClient(String host, int port) {
        channel = ManagedChannelBuilder
                .forAddress(host, port)
                .usePlaintext()
                .build();
        syncPasswordService = PasswordServiceGrpc.newBlockingStub(channel);
        asyncPasswordService = PasswordServiceGrpc.newStub(channel);
    }

    /** SHUTDOWN METHOD */
    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    /** Scanner takes in user input */
    Scanner userInput = new Scanner(System.in);

    /** Variables to store user input */
    private String password;
    private ByteString hashedPassword;
    private ByteString salt;
    private int userId;

    /** Method to take in user ID and Password */
    public void getUserInput(){
        System.out.println("Enter User ID: ");
        userId = userInput.nextInt();
        System.out.println("Enter Password: ");
        password = userInput.next();
    }

}

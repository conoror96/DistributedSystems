/** Adapted from https://github.com/john-french/distributed-systems-labs/blob/master/grpc-async-inventory/README.md */
package ie.gmit.ds;

import com.google.protobuf.BoolValue;
import com.google.protobuf.ByteString;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserClient {
    /**
     * CLIENT INITIALISATION
     */
    private static final Logger logger = Logger.getLogger(UserClient.class.getName());
    // Channel connects to the gRPC Server
    private final ManagedChannel channel;
    // Async stub
    private final PasswordServiceGrpc.PasswordServiceStub asyncPasswordService;
    // blocking (sync) stub
    private final PasswordServiceGrpc.PasswordServiceBlockingStub syncPasswordService;


    public UserClient(String host, int port) {
        channel = ManagedChannelBuilder
                .forAddress(host, port)
                .usePlaintext()
                .build();
        syncPasswordService = PasswordServiceGrpc.newBlockingStub(channel);
        asyncPasswordService = PasswordServiceGrpc.newStub(channel);
    }

    /**
     * SHUTDOWN METHOD
     */
    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    /**
     * Scanner takes in user input
     */
    Scanner userInput = new Scanner(System.in);

    /**
     * Variables to store user input
     */
    private String password;
    private ByteString hashedPassword;
    private ByteString salt;
    private int userId;

    /**
     * Method to take in user ID and Password
     */
    public void getUserInput() {
        System.out.println("Enter User ID: ");
        userId = userInput.nextInt();
        System.out.println("Enter Password: ");
        password = userInput.next();
    }

    /**
     * METHOD FOR HASH REQUEST
     * Returns hash request synchronously by calling the blocking stub (syncPasswordService)
     */
    public void hashRequest() {
        getUserInput();

        HashRequest hashRequest = HashRequest.newBuilder().setUserId(userId).setPassword(password).build();

        HashResponse response;

        try {
            logger.info("Sending Hash Request ");
            response = syncPasswordService.hash(hashRequest);
            hashedPassword = response.getHashedPassword();
            salt = response.getSalt();
        }catch (StatusRuntimeException ex){
            logger.log(Level.WARNING, "RPC failed: {0}", ex.getStatus());
            return;
        }

    }
    /** METHOD FOR VALIDATION REQUEST
     * Asynchronous call. Stream observer is passed in handle response from the server */
    public void validationRequest(){
        StreamObserver<BoolValue> responseObserver = new StreamObserver<BoolValue>() {

            @Override
            public void onNext(BoolValue value) {
             if (value.getValue()){
                    System.out.println("Validation Successful! ");
                }
             else{
                 System.out.println("Problem with Validation");
                }
            }

            @Override
            public void onError(Throwable t) {
            System.out.println("An error has occurred!");
            }

            @Override
            public void onCompleted() {
            System.exit(0);
            }
        };

        try {
            logger.info("Validating Request ");
            asyncPasswordService.validate(ValidateRequest.newBuilder().build(), responseObserver);
           // logger.info("Returned from requesting all items ");
        } catch (
                StatusRuntimeException ex) {
            logger.log(Level.WARNING, "RPC failed: {0}", ex.getStatus());
            return;
        }

    }

    /** MAIN METHOD */
    public static void main(String[] args) throws Exception {
        UserClient client = new UserClient("localhost", 50551);
        try {
            client.hashRequest();
            client.validationRequest();

            System.out.println("User ID: " + client.userId);
            System.out.println("Password: " + client.password);
            System.out.println("Hashed Password: " + client.hashedPassword.toByteArray().toString());
            System.out.println("Salt: " + client.salt.toByteArray().toString());
        } finally {
            // Don't stop process, keep alive to receive async response
            Thread.currentThread().join();
        }
    }

}

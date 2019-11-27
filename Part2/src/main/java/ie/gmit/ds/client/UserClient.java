/** Adapted from https://github.com/john-french/distributed-systems-labs/blob/master/grpc-async-inventory/README.md */
// https://howtodoinjava.com/dropwizard/tutorial-and-hello-world-example/

package ie.gmit.ds.client;

import com.google.protobuf.ByteString;
import ie.gmit.ds.*;
import ie.gmit.ds.api.User;
import ie.gmit.ds.database.UserDatabase;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;


import java.util.concurrent.TimeUnit;
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


    public void Hash(User user){
        StreamObserver<Password.HashResponse> responseStreamObserver = new StreamObserver<Password.HashResponse>() {
            @Override
            public void onNext(Password.HashResponse hashResponse) {
                User u = new User(user.getUserID(),
                        user.getUserName(),
                        user.getEmail(),
                        hashResponse.getHashedPassword(),
                        hashResponse.getSalt());

                UserDatabase.createUser(u.getUserID(), u);
            }

            @Override
            public void onError(Throwable throwable) {
                Status status = Status.fromThrowable(throwable);
            }

            @Override
            public void onCompleted() {

            }
        };
        try {
            asyncPasswordService.hash(Password.Credentials.newBuilder()
                    .setId(user.getUserID())
                    .setPassword(user.getPassword())
                    .build(), responseStreamObserver);
        } catch (StatusRuntimeException ex) {
            logger.warning("Exception: " + ex);
        }


    }

    public boolean Validate(String password, ByteString hashedPassword, ByteString salt) {

        boolean isTrue;

        Password.Compare validateRequest = Password.Compare.newBuilder()
                .setPassword(password)
                .setHashedPassword(hashedPassword)
                .setSalt(salt)
                .build();

        isTrue = syncPasswordService.validate(validateRequest).getValue();

        return isTrue;
    }




}

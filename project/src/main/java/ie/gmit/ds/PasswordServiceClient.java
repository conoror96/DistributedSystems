/** Adapted from https://github.com/john-french/distributed-systems-labs/blob/master/grpc-async-inventory/README.md */
package ie.gmit.ds;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class PasswordServiceClient {
    /** CLIENT INITIALISATION */
    private static final Logger logger = Logger.getLogger(PasswordServiceClient.class.getName());
    private final ManagedChannel channel;
    private final PasswordServiceGrpc.PasswordServiceStub asyncInventoryService;
    private final PasswordServiceGrpc.PasswordServiceBlockingStub syncInventoryService;

    public PasswordServiceClient(String host, int port) {
        channel = ManagedChannelBuilder
                .forAddress(host, port)
                .usePlaintext()
                .build();
        syncInventoryService = PasswordServiceGrpc.newBlockingStub(channel);
        asyncInventoryService = PasswordServiceGrpc.newStub(channel);
    }

    /** SHUTDOWN METHOD */
    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }
}

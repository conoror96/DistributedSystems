/** Adapted from https://github.com/john-french/distributed-systems-labs/blob/master/grpc-async-inventory/README.md */
package ie.gmit.ds;

import com.google.protobuf.BoolValue;
import com.google.protobuf.ByteString;
import io.grpc.stub.StreamObserver;

import java.util.logging.Logger;

public class PasswordServiceImpl extends PasswordServiceGrpc.PasswordServiceImplBase{
    /** */
    private static final Logger logger = Logger.getLogger(PasswordServiceImpl.class.getName());

    /** HASH METHOD */
    @Override
    public void hash(HashRequest request, StreamObserver<HashResponse> responseObserver) {
        try{
            /** Request password from user */
            String userPassword = request.getPassword();
            /** Send userPassword to CharArray */
            char[] hashString = userPassword.toCharArray();

            /** Passwords class is generating salt */
            byte[] addSalt = Passwords.getNextSalt();
            /** Passwords class hashes user password */
            byte[] hashedPassword = Passwords.hash(hashString, addSalt);


            responseObserver.onNext(HashResponse.newBuilder().setUserId(request.getUserId())
                    .setHashedPassword(ByteString.copyFrom(hashedPassword))
                    .setSalt(ByteString.copyFrom(addSalt))
                    .build());
        }
        catch(RuntimeException ex){
            responseObserver.onNext(HashResponse.newBuilder().getDefaultInstanceForType());
        }
        responseObserver.onCompleted();
    }

    /**  VALIDATE METHOD */
    @Override
    public void validate(ValidateRequest request, StreamObserver<BoolValue> responseObserver) {
        try{
            /**  */
            char[] userPassword = request.getPassword().toCharArray();
            byte[] hashedPassword = request.getHashedPassword().toByteArray();
            byte[] salt = request.getSalt().toByteArray();


            if(Passwords.isExpectedPassword(userPassword, salt, hashedPassword)) {
                responseObserver.onNext(BoolValue.newBuilder().setValue(true).build());
            }
            else{
                responseObserver.onNext(BoolValue.newBuilder().setValue(false).build());
            }
        }
        catch(RuntimeException ex){
            responseObserver.onNext(BoolValue.newBuilder().setValue(false).build());
        }
    }



}

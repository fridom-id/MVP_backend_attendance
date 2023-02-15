package id.fridom.cloud.attendance.exceptions;

import id.fridom.cloud.common.exceptions.FridomException;

public class DynamoDBException extends FridomException {
    public DynamoDBException(String message) {
        super(message);
    }
}

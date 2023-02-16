package id.fridom.cloud.attendance.exceptions;

import id.fridom.cloud.common.exceptions.FridomException;

public class AttendanceAlreadyExistsException extends FridomException {
    public AttendanceAlreadyExistsException(String message) {
        super(message);
    }
}

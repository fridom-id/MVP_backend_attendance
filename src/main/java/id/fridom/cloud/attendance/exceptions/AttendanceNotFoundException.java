package id.fridom.cloud.attendance.exceptions;

import id.fridom.cloud.common.exceptions.FridomException;

public class AttendanceNotFoundException extends FridomException {
    public AttendanceNotFoundException(String message) {
        super(message);
    }
}

package id.fridom.cloud.attendance.exceptions;

import id.fridom.cloud.attendance.services.AttendanceService;
import id.fridom.cloud.attendance.services.impl.AttendanceServiceBase;
import id.fridom.cloud.common.exceptions.FridomException;

/**
 * Handles any problem occurring in the attendance processes.
 * @see AttendanceService
 * @see AttendanceServiceBase
 */
public class AttendanceException extends FridomException {
    public AttendanceException(String message) {
        super(message);
    }
}

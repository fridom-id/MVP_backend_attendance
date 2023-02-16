package id.fridom.cloud.attendance.services;

import id.fridom.cloud.attendance.exceptions.AttendanceAlreadyExistsException;
import id.fridom.cloud.attendance.exceptions.AttendanceException;
import id.fridom.cloud.attendance.exceptions.AttendanceNotFoundException;
import id.fridom.cloud.common.model.dto.CheckIn;
import id.fridom.cloud.common.model.dto.CheckOut;

/**
 * Contains all methods related to attendance operations.
 */
public interface AttendanceService {

    /**
     * Create a new attendance with the information of check in.
     * @param checkIn The object with the information of check in.
     * @throws AttendanceAlreadyExistsException Exception thrown when the attendance already exists.
     * @throws AttendanceException Any problem related to check in operations.
     */
    void checkIn(CheckIn checkIn) throws AttendanceAlreadyExistsException, AttendanceException;

    /**
     * Update the attendance with the information of check out.
     * @param checkOut The object with the information of check out.
     * @throws AttendanceNotFoundException Exception when the check in attendance has not been found.
     * @throws AttendanceException Any problem related to check out operations.
     */
    void checkOut(CheckOut checkOut) throws AttendanceNotFoundException, AttendanceException;
}

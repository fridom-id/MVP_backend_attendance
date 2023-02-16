package id.fridom.cloud.attendance.services.impl;

import id.fridom.cloud.attendance.exceptions.AttendanceAlreadyExistsException;
import id.fridom.cloud.attendance.exceptions.AttendanceException;
import id.fridom.cloud.attendance.exceptions.AttendanceNotFoundException;
import id.fridom.cloud.attendance.exceptions.DynamoDBException;
import id.fridom.cloud.attendance.services.AttendanceService;
import id.fridom.cloud.attendance.services.dynamodb.DynamoDBAttendanceService;
import id.fridom.cloud.common.model.dto.CheckIn;
import id.fridom.cloud.common.model.dto.CheckOut;
import id.fridom.cloud.common.model.entities.Attendance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Implements the methods of attendance process.
 *
 * @author jhmarquez
 * @see AttendanceService
 * @since 1.0
 */
@Service
public class AttendanceServiceBase implements AttendanceService {

    @Autowired
    DynamoDBAttendanceService dynamoDBAttendanceService;


    /**
     * {@inheritdoc}
     */
    @Override
    public void checkIn(CheckIn checkIn) throws AttendanceAlreadyExistsException, AttendanceException {

        if (checkIn == null) {
            throw new AttendanceException("The CheckIn object cannot be empty.");
        }

        Optional<Attendance> attendanceOpt = dynamoDBAttendanceService.getAttendanceByUsernameAndEventId(
                checkIn.getUsername(), checkIn.getEventId());

        if (attendanceOpt.isPresent()) {
            throw new AttendanceAlreadyExistsException("The check in attendance already exists.");
        }

        Attendance attendance = new Attendance();

        attendance.setUsername(checkIn.getUsername());
        attendance.setCustomerId(checkIn.getCustomerId());
        attendance.setEventId(checkIn.getEventId());
        attendance.setEventName(checkIn.getEventName());
        attendance.setEventGroupId(checkIn.getEventGroupId());
        attendance.setEventGroupName(checkIn.getEventGroupName());
        attendance.setStart(System.currentTimeMillis());
        attendance.setOutOfRange(attendance.getStart() < checkIn.getEventStart());

        try {
            dynamoDBAttendanceService.createAttendance(attendance);
        } catch (DynamoDBException e) {
            AttendanceException ae = new AttendanceException("Problems in check in: %s".formatted(e.getMessage()));
            ae.setStackTrace(e.getStackTrace());
            throw ae;
        }

    }

    /**
     * {@inheritdoc}
     */
    @Override
    public void checkOut(CheckOut checkOut) throws AttendanceNotFoundException, AttendanceException {

        if (checkOut == null) {
            throw new AttendanceException("The CheckOut object cannot be empty.");
        }

        Optional<Attendance> attendanceOpt = dynamoDBAttendanceService.getAttendanceByUsernameAndEventId(
                checkOut.getUsername(), checkOut.getEventId());

        if (attendanceOpt.isEmpty()) {
            throw new AttendanceNotFoundException("The attendance related to the username and eventId not found.");
        }

        Attendance attendance = attendanceOpt.get();

        attendance.setEnd(System.currentTimeMillis());
        if (!attendance.getOutOfRange()) {
            attendance.setOutOfRange(checkOut.getEventEnd() > attendance.getEnd());
        }

        try {
            dynamoDBAttendanceService.updateAttendance(attendance);
        } catch (DynamoDBException e) {
            AttendanceException ae = new AttendanceException("Problems in check out: %s".formatted(e.getMessage()));
            ae.setStackTrace(e.getStackTrace());
            throw ae;
        }

    }
}

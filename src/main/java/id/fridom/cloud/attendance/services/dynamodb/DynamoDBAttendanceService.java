package id.fridom.cloud.attendance.services.dynamodb;

import id.fridom.cloud.attendance.exceptions.DynamoDBException;
import id.fridom.cloud.common.model.entities.Attendance;

import java.util.Optional;

/**
 *
 */
public interface DynamoDBAttendanceService {

    /**
     * Creates a new Attendance object in the DynamoDB table and return it with the identifier.
     * @param attendance The object to be created.
     * @return The attendance object created
     */
    Attendance createAttendance(Attendance attendance) throws DynamoDBException;

    /**
     * Updates an Attendance object in the DynamoDB table and return it.
     * @param attendance The object to be updated.
     * @return The attendance object updated
     */
    Attendance updateAttendance(Attendance attendance) throws DynamoDBException;

    /**
     * Finds the attendance related to the username and eventId.
     * @param username The username who attend the event.
     * @param eventId The identifier of specific event.
     * @return The attendance found or the optional empty.
     */
    Optional<Attendance> getAttendanceByUsernameAndEventId(String username, String eventId);
}

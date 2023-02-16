package id.fridom.cloud.attendance.services.dynamodb.impl;

import id.fridom.cloud.attendance.exceptions.DynamoDBException;
import id.fridom.cloud.attendance.repositories.AttendanceRepository;
import id.fridom.cloud.attendance.services.dynamodb.DynamoDBAttendanceService;
import id.fridom.cloud.common.model.entities.Attendance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Handles the communication about the CRUD operation with DynamoDB.
 * @see DynamoDBAttendanceService
 * @see Attendance
 * @author jhmarquez
 * @since 1.0
 */
@Service
public class DynamoDBAttendanceServiceBase implements DynamoDBAttendanceService {

    @Autowired
    AttendanceRepository repository;

    /**
     * {@inheritdoc}
     */
    @Override
    public Attendance createAttendance(Attendance attendance) throws DynamoDBException {

        if (attendance == null) {
            throw new DynamoDBException("The attendance cannot be null");
        }

        attendance.setCreationTimestamp(System.currentTimeMillis());
        return repository.save(attendance);
    }

    /**
     * {@inheritdoc}
     */
    @Override
    public Attendance updateAttendance(Attendance attendance) throws DynamoDBException {

        if (attendance == null) {
            throw new DynamoDBException("The attendance cannot be null.");
        }

        Optional<Attendance> attendanceOpt = repository.findById(attendance.getId());
        if (attendanceOpt.isEmpty()) {
            throw new DynamoDBException("The attendance not found in table, cannot be update.");
        }

        Attendance attendanceSaved = attendanceOpt.get();

        attendanceSaved.setEventGroupId(attendance.getEventGroupId());
        attendanceSaved.setEventGroupName(attendance.getEventGroupName());
        attendanceSaved.setEventName(attendance.getEventName());
        attendanceSaved.setEventId(attendance.getEventId());
        attendanceSaved.setCustomerId(attendance.getCustomerId());
        attendanceSaved.setStart(attendance.getStart());
        attendanceSaved.setEnd(attendance.getEnd());
        attendanceSaved.setOutOfRange(attendance.getOutOfRange());
        attendanceSaved.setLastModificationTimestamp(System.currentTimeMillis());

        return repository.save(attendanceSaved);
    }

    /**
     * {@inheritdoc}
     */
    @Override
    public Optional<Attendance> getAttendanceByUsernameAndEventId(String username, String eventId) {

        List<Attendance> attendances = repository.findByUsernameAndEventId(username, eventId);

        if (!attendances.isEmpty()) {
            return Optional.of(attendances.get(0));
        }

        return Optional.empty();
    }
}

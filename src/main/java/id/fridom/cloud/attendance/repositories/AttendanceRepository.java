package id.fridom.cloud.attendance.repositories;

import id.fridom.cloud.common.model.entities.Attendance;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableScan
public interface AttendanceRepository extends CrudRepository<Attendance, String> {

    List<Attendance> findByUsernameAndEventId(String username, String eventId);

}

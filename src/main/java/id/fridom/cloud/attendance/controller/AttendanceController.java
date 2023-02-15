package id.fridom.cloud.attendance.controller;

import id.fridom.cloud.attendance.services.impl.AttendanceServiceBase;
import id.fridom.cloud.common.apiresponse.AttendanceResponseCodes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for exposing the endpoints to process the attendance requests.
 *
 * @see AttendanceServiceBase
 * @author jhmarquez
 * @since 1.0
 */
@RestController
@Slf4j
public class AttendanceController {

    @Autowired
    private AttendanceServiceBase profileService;

    /**
     * @return Information about the status and parameters of microservice attendance.
     */
    @GetMapping("/info")
    public ResponseEntity<?> getInfo() {
        return ResponseEntity.ok(AttendanceResponseCodes.buildApiResponse(AttendanceResponseCodes.SAT_001));
    }

    /**
     * @return HTTP 200 for verifying availability.
     */
    @GetMapping("/health")
    public ResponseEntity<?> healthcheck() {
        return ResponseEntity.ok("");
    }

}

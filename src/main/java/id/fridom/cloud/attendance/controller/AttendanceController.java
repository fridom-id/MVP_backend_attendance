package id.fridom.cloud.attendance.controller;

import id.fridom.cloud.attendance.exceptions.AttendanceAlreadyExistsException;
import id.fridom.cloud.attendance.exceptions.AttendanceException;
import id.fridom.cloud.attendance.exceptions.AttendanceNotFoundException;
import id.fridom.cloud.attendance.services.AttendanceService;
import id.fridom.cloud.common.apiresponse.AttendanceResponseCodes;
import id.fridom.cloud.common.model.dto.CheckIn;
import id.fridom.cloud.common.model.dto.CheckOut;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Controller for exposing the endpoints to process the attendance requests.
 *
 * @see AttendanceService
 * @author jhmarquez
 * @since 1.0
 */
@RestController
@Slf4j
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

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

    @PostMapping("/check-in")
    public ResponseEntity<?> checkIn(@Valid @RequestBody CheckIn checkIn) {

        try {
            attendanceService.checkIn(checkIn);

            return ResponseEntity.ok(AttendanceResponseCodes.buildApiResponse(AttendanceResponseCodes.SAT_002));
        } catch (AttendanceAlreadyExistsException e) {
            log.error("Error in check in process: ", e);
            return ResponseEntity.internalServerError().body(AttendanceResponseCodes.buildApiResponse(
                    AttendanceResponseCodes.EAT_001));
        } catch (AttendanceException e) {
            log.error("Problems in the check in process: ", e);
            return ResponseEntity.internalServerError().body(AttendanceResponseCodes.buildApiResponse(
                    AttendanceResponseCodes.EAT_002, e.getMessage()));
        }
    }

    @PostMapping("/check-out")
    public ResponseEntity<?> checkOut(@Valid @RequestBody CheckOut checkOut) {

        try {
            attendanceService.checkOut(checkOut);

            return ResponseEntity.ok(AttendanceResponseCodes.buildApiResponse(AttendanceResponseCodes.SAT_003));
        } catch (AttendanceNotFoundException e) {
            log.error("Error in check out process: ", e);
            return ResponseEntity.internalServerError().body(AttendanceResponseCodes.buildApiResponse(
                    AttendanceResponseCodes.EAT_003));
        } catch (AttendanceException e) {
            log.error("Problems in the check in process: ", e);
            return ResponseEntity.internalServerError().body(AttendanceResponseCodes.buildApiResponse(
                    AttendanceResponseCodes.EAT_004, e.getMessage()));
        }
    }

}

package cinema.com.codersergg.controller;

import cinema.com.codersergg.request.UUIDTokenRequest;
import cinema.com.codersergg.dto.TheaterDTO;
import cinema.com.codersergg.dto.TicketDTO;
import cinema.com.codersergg.model.Theater;
import cinema.com.codersergg.service.TheaterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@Slf4j
public class TheaterController {

    private final TheaterService theaterService;

    public TheaterController(TheaterService theaterService) {
        this.theaterService = theaterService;
    }

    @GetMapping(value = "/seats")
    public TheaterDTO getTheater() {
        log.info("getTheater()");

        List<TicketDTO> listSeatDTO = theaterService.getTheater().getAvailableSeats().stream()
                .map(TicketDTO::convertToDTO)
                .collect(Collectors.toList());

        return new TheaterDTO(
                Theater.getTheater().getTotalRows(),
                Theater.getTheater().getTotalColumns(),
                listSeatDTO);

    }

    @PostMapping("/purchase")
    public ResponseEntity<Object> bookingTicket(@RequestBody TicketDTO ticketDTO) {
        log.info("bookingTicket() with TicketDTO: " + ticketDTO);

        if (ticketDTO.getRow() < 1 || ticketDTO.getColumn() < 1 ||
                ticketDTO.getRow() > Theater.getTheater().getTotalRows() ||
                ticketDTO.getColumn() > Theater.getTheater().getTotalColumns()) {

            return new ResponseEntity<>(
                    Map.of("error", "The number of a row or a column is out of bounds!"),
                    HttpStatus.BAD_REQUEST);
        }

        return theaterService.bookingTicket(ticketDTO);
    }

    @PostMapping("/return")
    public ResponseEntity<Object> returnTicket(@RequestBody UUIDTokenRequest request) {
        log.info("returnTicket() with UUID token: " + request.getToken());

        if (!TheaterService.mapToken.containsKey(request.getToken()) || TheaterService.mapToken.get(request.getToken()).isExpired()) {
            return new ResponseEntity<>(
                    Map.of("error", "Wrong token!"),
                    HttpStatus.BAD_REQUEST);
        }

        return theaterService.returnTicket(request.getToken());
    }

    @PostMapping("/stats")
    public ResponseEntity<Object> getStatistics(@RequestParam(required = false) String password) {
        log.info("getStatistics() with URL parameters: " + password);

        if (!"super_secret".equals(password)) {
            return new ResponseEntity<>(Map.of("error", "The password is wrong!"), HttpStatus.UNAUTHORIZED);
        }

        return theaterService.getStatistics(password);
    }
}

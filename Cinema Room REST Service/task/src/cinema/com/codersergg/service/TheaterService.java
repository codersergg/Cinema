package cinema.com.codersergg.service;

import cinema.com.codersergg.dto.TicketDTO;
import cinema.com.codersergg.dto.TokenDTO;
import cinema.com.codersergg.model.Theater;
import cinema.com.codersergg.model.Ticket;
import cinema.com.codersergg.model.Token;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
@Slf4j
public class TheaterService {

    static List<Theater> listTheaters = new ArrayList<>();
    public static Map<UUID, Token> mapToken = new HashMap<>();

    public Theater getTheater() {
        if (listTheaters.isEmpty()) {
            listTheaters.add(Theater.getTheater());
        }
        return listTheaters.get(0);
    }

    public ResponseEntity<Object> bookingTicket(TicketDTO ticketDTO) {

        if (listTheaters.isEmpty()) {
            getTheater();
        }

        Ticket ticket = listTheaters.get(0).getAvailableSeats().stream()
                .filter(s -> s.getRow() == ticketDTO.getRow() && s.getColumn() == ticketDTO.getColumn())
                .findAny().get();

        if (!ticket.isAvailable()) {
            return new ResponseEntity<>(
                    Map.of("error", "The ticket has been already purchased!"),
                    HttpStatus.BAD_REQUEST);
        }

        ticket.setAvailable(false);
        Token token = Token.generateToken(ticket);
        mapToken.put(token.getToken(), token);
        TokenDTO created = new TokenDTO(token.getToken(), TicketDTO.convertToDTO(ticket));

        return new ResponseEntity<>(created, HttpStatus.OK);
    }

    public ResponseEntity<Object> returnTicket(UUID token) {
        mapToken.get(token).setExpired(true);
        mapToken.get(token).getTicket().setAvailable(true);

        TokenDTO tokenDTO = TokenDTO.convertToDTO(mapToken.get(token));


        return new ResponseEntity<>(Map.of("returned_ticket", tokenDTO.getTicket()), HttpStatus.OK);
    }

    public ResponseEntity<Object> getStatistics(String password) {
        log.info("getStatistics() with password: " + password);

        int currentIncome = mapToken.values().stream()
                .filter(token -> !token.isExpired())
                .mapToInt(value -> value.getTicket().getPrice())
                .reduce(0, Integer::sum);

        long numberOfAvailableSeats = 81 - mapToken.values().stream()
                .filter(token -> !token.getTicket().isAvailable())
                .count();

        long numberOfPurchasedTickets = mapToken.values().stream()
                .filter(token -> !token.getTicket().isAvailable())
                .count();

        return new ResponseEntity<>(Map.of(
                "current_income", currentIncome,
                "number_of_available_seats", numberOfAvailableSeats,
                "number_of_purchased_tickets", numberOfPurchasedTickets), HttpStatus.OK);
    }
}

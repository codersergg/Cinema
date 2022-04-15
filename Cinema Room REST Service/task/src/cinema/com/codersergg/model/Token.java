package cinema.com.codersergg.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Data
@Getter
@Setter
public class Token {

    private UUID token;

    private Ticket ticket;

    private boolean isExpired;

    public static Token generateToken(Ticket ticket) {
        Token token = new Token();
        token.setToken(UUID.randomUUID());

        Ticket ticketWithPrice = Theater.getTheater().getAvailableSeats().stream()
                .filter(tick -> tick.getColumn() == ticket.getColumn() && tick.getRow() == ticket.getRow())
                .findAny()
                .get();

        token.setTicket(ticketWithPrice);
        token.setExpired(false);

        return token;
    }

}

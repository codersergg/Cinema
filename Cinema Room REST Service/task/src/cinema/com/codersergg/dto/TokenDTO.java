package cinema.com.codersergg.dto;

import cinema.com.codersergg.model.Ticket;
import cinema.com.codersergg.model.Token;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Data
@Getter
@Setter
@AllArgsConstructor
public class TokenDTO {

    private UUID token;

    private TicketDTO ticket;

    public static TokenDTO convertToDTO(Token token) {
        return new TokenDTO(token.getToken(), TicketDTO.convertToDTO(token.getTicket()));
    }

}

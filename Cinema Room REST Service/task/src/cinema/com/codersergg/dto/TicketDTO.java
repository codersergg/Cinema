package cinema.com.codersergg.dto;

import cinema.com.codersergg.model.Ticket;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TicketDTO {

    private final int row;

    private final int column;

    private int price;

    public static TicketDTO convertToDTO(Ticket ticket) {
        return new TicketDTO(ticket.getRow(), ticket.getColumn(), ticket.getPrice());
    }

}
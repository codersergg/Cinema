package cinema.com.codersergg.dto;

import lombok.*;

import java.util.List;


@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TheaterDTO {

    private int total_rows;

    private int total_columns;

    private List<TicketDTO> available_seats;

}

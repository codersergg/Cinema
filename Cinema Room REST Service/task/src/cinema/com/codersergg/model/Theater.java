package cinema.com.codersergg.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

//@Entity
@Data
public class Theater {

    /*@Id
    @Column(name = "theater_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;*/

    private final static int total_rows;

    private final static int total_columns;

    private static List<Ticket> available_seats;

    static {
        total_rows = 9;
        total_columns = 9;
        available_seats = new ArrayList<>();
        for (int i = 1; i <= total_rows; i++) {
            for (int j = 1; j <= total_columns; j++) {
                available_seats.add(new Ticket(i, j));
            }
        }
    }

    public int getTotalRows() {
        return total_rows;
    }

    public int getTotalColumns() {
        return total_columns;
    }

    public List<Ticket> getAvailableSeats() {
        return available_seats;
    }

    public static Theater getTheater() {
        return new Theater();
    }
}

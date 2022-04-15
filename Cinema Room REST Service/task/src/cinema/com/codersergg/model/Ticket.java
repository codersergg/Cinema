package cinema.com.codersergg.model;

import lombok.Data;

@Data
public class Ticket {

    private final int row;

    private final int column;

    private int price;

    private boolean isAvailable;

    public Ticket(int row, int column) {
        this.row = row;
        this.column = column;
        this.price = getPriceOfSeat(row);
        this.isAvailable = true;
    }

    private int getPriceOfSeat(int row) {
        if (row <= 4) {
            return 10;
        } else {
            return 8;
        }
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }
}

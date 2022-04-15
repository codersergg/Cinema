package cinema.com.codersergg.request;

import lombok.Data;
import lombok.Getter;

import java.util.UUID;

@Data
@Getter
public class UUIDTokenRequest {

    private UUID token;

}

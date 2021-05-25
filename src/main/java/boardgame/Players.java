package boardgame;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Players {

    private Long id;
    private String playerone;
    private String playertwo;

    public Players(String playerone, String playertwo)
    {
        this(null,playerone,playertwo);
    }
}

package tgg.securityblog.dto.message;

import lombok.Getter;
import lombok.Setter;

@Getter
public class AlertMessageDTO {
    public AlertMessageDTO(String message, String redirectUrl){
        this.message = message;
        this.redirectUrl = redirectUrl;
    }

    private String message;
    private String redirectUrl;
}

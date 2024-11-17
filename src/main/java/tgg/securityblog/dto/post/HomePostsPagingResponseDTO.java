package tgg.securityblog.dto.post;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class HomePostsPagingResponseDTO {
    public HomePostsPagingResponseDTO(int startPage, int endPage){
        this.startPage = startPage;
        this.endPage = endPage;
    }
    private int startPage;
    private int endPage;
}

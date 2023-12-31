package africa.semicolon.gemstube.dtos.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ApiResponse <T>{
    private T data;
    private String message;
}

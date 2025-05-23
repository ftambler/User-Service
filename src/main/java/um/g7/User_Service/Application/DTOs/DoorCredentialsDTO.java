package um.g7.User_Service.Application.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoorCredentialsDTO {
    private String doorName;
    private String passcode;
}

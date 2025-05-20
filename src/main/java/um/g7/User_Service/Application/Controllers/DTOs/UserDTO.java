package um.g7.User_Service.Application.Controllers.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDTO {
    private String fullName;
    private String cid;
    private int accessLevel;

}

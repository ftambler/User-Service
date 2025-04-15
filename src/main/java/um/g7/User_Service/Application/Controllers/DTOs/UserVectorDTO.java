package um.g7.User_Service.Application.Controllers.DTOs;

import java.util.UUID;

import lombok.Data;

@Data
public class UserVectorDTO {
    private UUID userId;
    private float[] vector;
}

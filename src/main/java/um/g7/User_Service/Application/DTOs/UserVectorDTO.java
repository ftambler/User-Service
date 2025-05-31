package um.g7.User_Service.Application.DTOs;

import lombok.Data;

import java.util.UUID;

@Data
public class UserVectorDTO {
    private UUID userId;
    private float[] vector;
}

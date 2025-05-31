package um.g7.User_Service.Application.Listeners;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import um.g7.User_Service.Application.DTOs.DeletionRequest;
import um.g7.User_Service.Domain.Entities.Door;
import um.g7.User_Service.Domain.Entities.UserEntity;
import um.g7.User_Service.Domain.Entities.UserRFID;
import um.g7.User_Service.Domain.Entities.UserVector;
import um.g7.User_Service.Domain.Exceptions.DoorNotFoundException;
import um.g7.User_Service.Domain.Exceptions.UserNotFoundException;
import um.g7.User_Service.Domain.Services.DoorService;
import um.g7.User_Service.Domain.Services.UserAccessService;
import um.g7.User_Service.Domain.Services.UserService;

import java.util.UUID;

@Component
public class KafkaListeners {

    private final String USER_TOPIC = "users";
    private final String USER_VECTOR_TOPIC = "userVector";
    private final String USER_RFID_TOPIC = "userRFID";
    private final String DOOR_TOPIC = "door";
    private final String DELETION_TOPIC = "deletion";

    private final UserService userService;
    private final UserAccessService userAccessService;
    private final DoorService doorService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public KafkaListeners(UserService userService, UserAccessService userAccessService, DoorService doorService) {
        this.userService = userService;
        this.userAccessService = userAccessService;
        this.doorService = doorService;
    }

    @KafkaListener(topics = USER_TOPIC, groupId = "gid_1")
    public void userTopicListener(String userJson) throws JsonProcessingException {
        userService.addUser(objectMapper.readValue(userJson, UserEntity.class));
    }

    @KafkaListener(topics = USER_VECTOR_TOPIC, groupId = "gid_1")
    public void userVectorTopicListener(String userVectorJson) throws JsonProcessingException {
        userAccessService.addUserVector(objectMapper.readValue(userVectorJson, UserVector.class));
    }
    
    @KafkaListener(topics = USER_RFID_TOPIC, groupId = "gid_1")
    public void userRFIDTopicListener(String userRFIDJson) throws JsonProcessingException {
        userAccessService.addUserRFID(objectMapper.readValue(userRFIDJson, UserRFID.class));
    }  

    @KafkaListener(topics = DOOR_TOPIC, groupId = "gid_1")
    public void doorTopicListener(String doorJson) throws JsonProcessingException {
        doorService.addDoor(objectMapper.readValue(doorJson, Door.class));
    }

    @KafkaListener(topics = DELETION_TOPIC, groupId = "gid_1")
    public void deletionTopicListener(String deletionRequestJson) throws JsonProcessingException, UserNotFoundException, DoorNotFoundException {
        DeletionRequest deletionRequest = objectMapper.readValue(deletionRequestJson, DeletionRequest.class);

        switch (deletionRequest.getType()) {
            case USER:
                userService.deleteUser(UUID.fromString(deletionRequest.getId()));
                break;
            case DOOR:
                doorService.deleteDoor(UUID.fromString(deletionRequest.getId()));
                break;
        }
    }
}

package um.g7.User_Service.Application.Listeners;


import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import um.g7.User_Service.Domain.Entities.UserEntity;
import um.g7.User_Service.Domain.Entities.UserRFID;
import um.g7.User_Service.Domain.Entities.UserVector;
import um.g7.User_Service.Domain.Services.UserRFIDService;
import um.g7.User_Service.Domain.Services.UserService;
import um.g7.User_Service.Domain.Services.UserVectorService;

@Component
public class UserListener {

    private final String USER_TOPIC = "users";
    private final String USER_VECTOR_TOPIC = "userVector";
    private final String USER_RFID_TOPIC = "userRFID";

    private UserService userService;
    private UserVectorService userVectorService;
    private UserRFIDService userRFIDService;    
    private final ObjectMapper objectMapper = new ObjectMapper();

    public UserListener(UserService userService, UserVectorService userVectorService, UserRFIDService userRFIDService) {
        this.userService = userService;
        this.userVectorService = userVectorService;
        this.userRFIDService = userRFIDService;
    }

    @KafkaListener(topics = USER_TOPIC, groupId = "gid_1")
    public void userTopicListener(String userJson) throws JsonMappingException, JsonProcessingException {
        userService.addUser(objectMapper.readValue(userJson, UserEntity.class));
    }

    @KafkaListener(topics = USER_VECTOR_TOPIC, groupId = "gid_1")
    public void userVectorTopicListener(String userVectorJson) throws JsonMappingException, JsonProcessingException {
        userVectorService.addUserVector(objectMapper.readValue(userVectorJson, UserVector.class));
    }
    
    @KafkaListener(topics = USER_RFID_TOPIC, groupId = "gid_1")
    public void userRFIDTopicListener(String userRFIDJson) throws JsonMappingException, JsonProcessingException {
        userRFIDService.addUserRFID(objectMapper.readValue(userRFIDJson, UserRFID.class));
    }  

}

package um.g7.User_Service.Application.Listeners;


import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import um.g7.User_Service.Domain.Entities.UserEntity;
import um.g7.User_Service.Domain.Services.UserService;

@Component
public class UserListener {

    private final String USER_TOPIC = "users";

    private UserService userService;    
    private final ObjectMapper objectMapper = new ObjectMapper();

    public UserListener(UserService userService) {
        this.userService = userService;
    }

    @KafkaListener(topics = USER_TOPIC, groupId = "gid_1")
    public void userTopicListener(String userJson) throws JsonMappingException, JsonProcessingException {
        userService.addUser(objectMapper.readValue(userJson, UserEntity.class));
    }

    

}

package ivan.projectManager.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import ivan.projectManager.application.config.ApplicationConfig;
import ivan.projectManager.application.controller.UserController;
import ivan.projectManager.application.dto.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Application {
    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) throws JsonProcessingException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        UserController userController = context.getBean(UserController.class);
        UserDTO userDTO = new UserDTO();
        UserDTO userDTO1=new UserDTO();
        UserDTO userDTO2 = new UserDTO();
        userDTO2.setUsername("updatedUsername1").setPassword("updatedPassword1").setEmail("UpdatedEmail1");
        userDTO.setId(1).setUsername("username1").setPassword("password1").setEmail("email1");
        userDTO.setId(2).setUsername("username2").setPassword("password2").setEmail("email2");
        Runnable saveUser = () -> {

            try {
                log.info("saveUser: {}",userController.save(userDTO));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        };
        Runnable saveUser2 = () -> {

            try {
                log.info("saveUser: {}",userController.save(userDTO1));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        };

        Runnable updateUser = () -> {
            try {
                log.info("UpdateUser: {}",userController.update(1, userDTO2));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        };

        Runnable deleteUser = () -> {
            userController.delete(2);
        };
        Runnable getAllUsers=()->{
            try {
                log.info("Users: {}",userController.getAll());
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        };
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        executorService.submit(saveUser);
         executorService.submit(saveUser2);
        executorService.submit(updateUser);
        executorService.submit(deleteUser);
        executorService.submit(getAllUsers);

        executorService.shutdown();
        while (!executorService.isTerminated()) {
        }
        context.close();

    }
}
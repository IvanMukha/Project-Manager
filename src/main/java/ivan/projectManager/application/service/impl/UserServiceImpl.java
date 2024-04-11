package ivan.projectManager.application.service.impl;

import ivan.projectManager.application.dto.UserDTO;
import ivan.projectManager.application.model.User;
import ivan.projectManager.application.repository.UserRepository;
import ivan.projectManager.application.service.UserService;
import ivan.projectManager.application.service.annotations.Transaction;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final ModelMapper modelMapper;

    private final UserRepository jdbcRepositoryImpl;

    @Autowired
    public UserServiceImpl(ModelMapper modelMapper, @Qualifier("JDBCUserRepository") UserRepository jdbcRepositoryImpl) {
        this.modelMapper = modelMapper;
        this.jdbcRepositoryImpl = jdbcRepositoryImpl;
    }


    public List<UserDTO> getAll() {
        return jdbcRepositoryImpl.getAll().stream().map(this::mapUserToDTO).collect(Collectors.toList());
    }

    @Transaction
    public UserDTO save(UserDTO userDTO) {
        return mapUserToDTO(jdbcRepositoryImpl.save(mapDTOToUser(userDTO)));
    }

    public Optional<UserDTO> getById(int id) {
        Optional<User> userOptional = jdbcRepositoryImpl.getById(id);
        return userOptional.map(this::mapUserToDTO);
    }

    @Transaction
    public Optional<UserDTO> update(int id, UserDTO updatedUserDTO) {
        Optional<User> userOptional = jdbcRepositoryImpl.update(id, mapDTOToUser(updatedUserDTO));
        return userOptional.map(this::mapUserToDTO);
    }

    @Transaction
    public void delete(int id) {
        jdbcRepositoryImpl.delete(id);
    }

    private User mapDTOToUser(UserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }

    private UserDTO mapUserToDTO(User user) {
        return modelMapper.map(user, UserDTO.class);
    }
}

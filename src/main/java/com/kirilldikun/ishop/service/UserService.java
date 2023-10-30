package com.kirilldikun.ishop.service;

import com.kirilldikun.ishop.dto.PersonDTO;
import com.kirilldikun.ishop.dto.UserDTO;
import com.kirilldikun.ishop.entity.Person;
import com.kirilldikun.ishop.entity.User;
import com.kirilldikun.ishop.exception.RoleNotFoundException;
import com.kirilldikun.ishop.exception.UserAlreadyExistsException;
import com.kirilldikun.ishop.exception.UserNotFoundException;
import com.kirilldikun.ishop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final PersonService personService;

    private final RoleService roleService;

    public List<UserDTO> findAll() {
        return userRepository.findAll().stream().map(this::mapToUserDTO).toList();
    }

    public void save(UserDTO userDTO) throws RoleNotFoundException, UserAlreadyExistsException {
        if (!roleService.isValidRole(userDTO.getRole())) {
            throw new RoleNotFoundException();
        }
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new UserAlreadyExistsException();
        }
        Person person = personService.save(userDTO.getPerson());
        User user = mapToUser(userDTO);
        user.setPerson(person);
        userRepository.save(user);
    }

    public void update(Long id, UserDTO userDTO)
            throws UserNotFoundException, RoleNotFoundException, UserAlreadyExistsException {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException();
        }
        if (!roleService.isValidRole(userDTO.getRole())) {
            throw new RoleNotFoundException();
        }
        if (userRepository.existsByEmail(userDTO.getEmail()) &&
                !userRepository.findByEmail(userDTO.getEmail()).orElseThrow(UserNotFoundException::new)
                        .getId().equals(id)) {
            throw new UserAlreadyExistsException();
        }
        PersonDTO personDTO = userDTO.getPerson();
        personDTO.setId(userRepository.findById(id).orElseThrow(UserNotFoundException::new).getPerson().getId());
        Person person = personService.save(personDTO);
        User user = mapToUser(userDTO);
        user.setPerson(person);
        user.setId(id);
        userRepository.save(user);
    }

    public void delete(Long id) throws UserNotFoundException {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException();
        }
        personService.delete(userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new).getPerson());
    }

    public User findById(Long id) throws UserNotFoundException {
        return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }

    public User findByEmail(String email){
        return userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
    }

    public UserDTO mapToUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setEmail(user.getEmail());
        userDTO.setPassword(user.getPassword());
        userDTO.setRole(user.getRole().name());
        userDTO.setPerson(personService.mapToPersonDTO(user.getPerson()));
        return userDTO;
    }

    public User mapToUser(UserDTO userDTO) throws RoleNotFoundException {
        User user = new User();
        user.setId(userDTO.getId());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setRole(roleService.mapToRole(userDTO.getRole()));
        user.setPerson(personService.mapToPerson(userDTO.getPerson()));
        return user;
    }
}

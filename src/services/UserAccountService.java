package src.services;

import src.exceptions.InvalidInputException;
import src.models.UserAccount;
import src.repositories.UserAccountRepository;
import src.utils.Validator;

public class UserAccountService {

    private final UserAccountRepository userRepository;

    public UserAccountService() {
        userRepository = new UserAccountRepository();
    }

    public void register(UserAccount user) throws InvalidInputException {
        if (Validator.isNullOrEmpty(user.getUsername())) {
            throw new InvalidInputException("Username is required");
        }
        if (Validator.isNullOrEmpty(user.getPassword())) {
            throw new InvalidInputException("Password is required");
        }
        if (Validator.isNullOrEmpty(user.getRole())) {
            throw new InvalidInputException("Role is required");
        }

        userRepository.addUser(user);
    }

    public UserAccount login(String username, String password) throws InvalidInputException {
        if (Validator.isNullOrEmpty(username) || Validator.isNullOrEmpty(password)) {
            throw new InvalidInputException("Username and password required");
        }

        UserAccount user = userRepository.login(username, password);

        if (user == null) {
            throw new InvalidInputException("Invalid credentials");
        }

        return user;
    }
}

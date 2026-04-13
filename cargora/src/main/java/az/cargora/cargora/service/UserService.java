package az.cargora.cargora.service;

import org.springframework.stereotype.Service;

import az.cargora.cargora.entity.PickUpPoint;
import az.cargora.cargora.entity.User;
import az.cargora.cargora.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User getUserByPin(String pin) {
        return userRepository.findByPIN(pin)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public void updatePickupPoint(Long userId, PickUpPoint point) {

        User user = getUserById(userId);

        user.setPickUpAdress(point);

        userRepository.save(user);
    }

    public void updateHomeAddress(Long userId, String address) {

        User user = getUserById(userId);

        user.setHomeAddress(address);

        userRepository.save(user);
    }
}

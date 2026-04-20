package az.cargora.cargora.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import az.cargora.cargora.dto.request.TopUpRequest;
import az.cargora.cargora.entity.PickUpPoint;
import az.cargora.cargora.entity.User;
import az.cargora.cargora.payment.FakeStripeService;
import az.cargora.cargora.payment.StripeChargeRequest;
import az.cargora.cargora.payment.StripeChargeResponse;
import az.cargora.cargora.repository.PickUpPointRepository;
import az.cargora.cargora.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PickUpPointRepository pickUpPointRepository;

    private final FakeStripeService fakeStripeService;

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

    @Transactional
    public void updatePickupPoint(Long userId, Long pickupPointId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        PickUpPoint point = pickUpPointRepository.findById(pickupPointId)
                .orElseThrow(() -> new RuntimeException("Pickup point not found"));

        user.setPickUpAdress(point);
    }

    public void updateHomeAddress(Long userId, String address) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setHomeAddress(address);

        userRepository.save(user);
    }

    @Transactional
    public void topUpBalance(TopUpRequest request) {

        // 1. Stripe request
        StripeChargeRequest stripeRequest = new StripeChargeRequest(request.getCardNumber(),
                request.getCvv(),
                request.getAmount(),
                "AZN");

        // 2. Payment
        StripeChargeResponse stripeResponse = fakeStripeService.charge(stripeRequest);

        // 3. if successed
        if (stripeResponse.isSuccess()) {

            User user = userRepository
                    .findById(request.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            user.setBalance(
                    user.getBalance().add(request.getAmount()));

        } else {
            throw new RuntimeException(stripeResponse.getMessage());
        }
    }

    // --------------------------------------------------------------

    @Transactional
    public void updateBonus(long userId, BigDecimal amount) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setBonus(user.getBonus().add(amount));
    }

}

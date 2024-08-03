package com.example.oauth2.service;

import com.example.oauth2.model.User;
import com.example.oauth2.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SocialAppService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(SocialAppService.class);

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        Map<String, Object> attributes = oAuth2User.getAttributes();
        String email = (String) attributes.get("email");
        email = email == null ? "not_present" : email;

        logger.info("Loading user attributes:");
        attributes.forEach((key, value) -> logger.info(key + ": " + value));

        // Получаем ID пользователя и конвертируем его в строку
        Object idObject = attributes.get("id");
        String providerId = idObject != null ? idObject.toString() : null;
        String role = (String) attributes.get("type");
        Optional<User> optionalUser = userRepository.findByProviderId(providerId);
        User user;
        if (optionalUser.isPresent()) {
            user = optionalUser.get();
            user.setName((String) attributes.get("name"));
            logger.info("User present: " + user.getName());
        } else {
            user = new User();
            user.setEmail(email);
            user.setName((String) attributes.get("name"));
            user.setRole(role);
            user.setProvider(userRequest.getClientRegistration().getRegistrationId());
            user.setProviderId(providerId);
            logger.info("Creating new user: " + user.getName());
        }
        return userRepository.save(user);

//        System.out.println("user saved successfully");
//        return new DefaultOAuth2User(
//                Collections.singletonList(new SimpleGrantedAuthority(user.getRole())),
//                attributes,
//                "name"
//        );
    }

}

//package com.example.oauth2.service;
//
//import com.example.oauth2.model.User;
//import com.example.oauth2.repository.UserRepository;
//import jakarta.persistence.Table;
//import lombok.AllArgsConstructor;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
//import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
//import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
//import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
//import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
//import org.springframework.security.oauth2.core.user.OAuth2User;
//import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.HashSet;
//import java.util.Map;
//import java.util.Optional;
//import java.util.Set;
//
//@AllArgsConstructor
//@Service
//public class SocialAppService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
//
//    private final UserRepository userRepository;
//
//
//    @Transactional
//    public User saveOrUpdateUser(OAuth2User oAuth2User, String provider) {
//        String email = oAuth2User.getAttribute("name");
//        Optional<User> existingUser = userRepository.findByName(email);
//
//        User user;
//        if (existingUser.isPresent()) {
//            user = existingUser.get();
//        } else {
//            user = new User();
//            user.setEmail(email);
//        }
//
//        user.setName(oAuth2User.getAttribute("name"));
//        user.setProvider(provider);
//        user.setProviderId(oAuth2User.getName());
//
//        return userRepository.save(user);
//    }
//
//    @Override
//    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
//        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
//        OAuth2User oAuth2User = delegate.loadUser(userRequest);
//
//        Map<String, Object> attributes = oAuth2User.getAttributes();
//        String email = (String) attributes.get("email");
//        String name = (String) attributes.get("name");
//
//        String provider = userRequest.getClientRegistration().getRegistrationId();
//
//        User user = saveOrUpdateUser(oAuth2User, provider);
//
//        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
//        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
//        return new DefaultOAuth2User(
//                authorities,
//                attributes,
//                "email" // Здесь укажите атрибут, который используется в качестве имени пользователя
//        );
//    }
//}

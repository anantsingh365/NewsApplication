//package com.anant.newApp.Service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Service
//public class AuthenticationProviderService implements AuthenticationProvider {
//
//        static class tempUser{
//            public String UserName = "gaurav";
//            public String UserPassword = "1234";
////            public List<>
//        }
//
////    @Autowired
////    private BCryptPasswordEncoder bCryptPasswordEncoder;
////
////    @Autowired
////    private SCryptPasswordEncoder sCryptPasswordEncoder;
//
//    @Override
//    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//        String username = authentication.getName();
//        String password = authentication.getCredentials().toString();
//        System.out.println("Inside the Authenticate Method in CustomAuthenticationProvider");
//       // return null;
//        tempUser user = new tempUser();
//
////        switch (user.getUser().getAlgorithm()) {
////            case BCRYPT:
////                return checkPassword(user, password, bCryptPasswordEncoder);
////            case SCRYPT:
////                return checkPassword(user, password, sCryptPasswordEncoder);
////        }
//        if(user.UserName.equals(username) && user.UserPassword.equals(password)){
//            return checkPassword(user, password,new BCryptPasswordEncoder());
//        }
//
//        throw new BadCredentialsException("Bad credentials");
//    }
//
//    @Override
//    public boolean supports(Class<?> aClass) {
//        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(aClass);
//    }
//
//    private Authentication checkPassword(tempUser user, String rawPassword, PasswordEncoder encoder) {
//        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
//        for(int i =0; i < 10; i++){
//            authorities.add(new SimpleGrantedAuthority("READ"));
//        }
//        if (rawPassword.equals(user.UserPassword)) {
//            return new UsernamePasswordAuthenticationToken(user.UserName, user.UserPassword, authorities);
//        } else {
//            throw new BadCredentialsException("Bad credentials");
//        }
//    }
//}
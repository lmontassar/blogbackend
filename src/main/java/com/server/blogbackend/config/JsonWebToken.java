package com.server.blogbackend.config;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import org.springframework.stereotype.Component;

import com.server.blogbackend.Entity.User;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JsonWebToken {
    private static final long EXPIRATION_TIME = 864_000_000; //10 days
    private static final String secretKey = "ezbiderel3ankaboutezbiderel3ankabouttiriritiriritiri";
  
   
    public JsonWebToken(){
        try{
            KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
            SecretKey k = keyGen.generateKey();
            Base64.getEncoder().encodeToString(k.getEncoded()) ;
        }catch(Exception e){
            throw new RuntimeException();
        }
    }
    
    private SecretKey getKey(){
        byte[] keyBytes = Decoders.BASE64.decode(secretKey) ;
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(User u) {
        Map<String, Object> claims = new HashMap<String,Object>();
        String r = Jwts.builder()
                    .claims()
                    .add( claims )
                    .subject(String.valueOf(u.getId()))
                    .issuedAt(new Date(System.currentTimeMillis()))
                    .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                    .and()
                    .signWith(this.getKey())
                    .compact();
        return r;
    }
}
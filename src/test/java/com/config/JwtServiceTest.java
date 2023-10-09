package com.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.junit.Test;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class JwtServiceTest {

    @Test
    public void testExtractUsername_withToken_returnsExtractedClaims() {
        String mockToken = "mockToken";
        JwtService jwtService = new JwtService();

        Claims claims = mock(Claims.class);
        when(jwtService.extractClaim(mockToken, Claims::getSubject)).thenReturn(String.valueOf(claims));

        String expectedUsername = "testUser";
        when(claims.getSubject()).thenReturn(expectedUsername);

        String extractedUsername = jwtService.extractUsername(mockToken);

        assertEquals(expectedUsername, extractedUsername);
    }

}

package org.example.algosolve.user.token;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.authentication.BadCredentialsException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.example.algosolve.user.token.TokenHeaderExtractor.extract;

public class TokenHeaderExtractorTest {

    @Test
    void 토큰을_추출한다(){
        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
        String token="token";
        mockHttpServletRequest.addHeader(HttpHeaders.AUTHORIZATION,"Bearer "+token);

        String result= extract(mockHttpServletRequest);

        assertThat(result).isEqualTo(token);
    }

    @Test
    void 헤더가_존재하지_않으면_예외(){
        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();

        assertThatThrownBy(()->extract(mockHttpServletRequest)).isInstanceOf(BadCredentialsException.class);
    }

    @Test
    void prefix가_않으면_예외(){
        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
        String token="token";
        mockHttpServletRequest.addHeader(HttpHeaders.AUTHORIZATION,token);

        assertThatThrownBy(()->extract(mockHttpServletRequest)).isInstanceOf(BadCredentialsException.class);
    }
}

package org.example.algosolve.user.token;

public class TestTokenEncoderFactory {
    private static final String key="testtesttesttesttesttesttesttesttesttesttest";
    private static final int accessTokenExpireTime = 30;

    public static TokenEncoder tokenEncoder(){
        return new TokenEncoder(new HS256JwtBuilderProvider(key),accessTokenExpireTime,100);
    }
}

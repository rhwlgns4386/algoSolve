package org.example.algosolve.user.token;

import java.time.LocalDateTime;
import java.util.Date;
import org.example.algosolve.user.util.DateUtil;

public class TokenExpireMinute {
    private final int tokenExpireMinute;

    TokenExpireMinute(int tokenExpireMinute) {
        this.tokenExpireMinute = tokenExpireMinute;
    }

    Date calculateExpirationDate(LocalDateTime now){
        return DateUtil.toDate(now.plusMinutes(tokenExpireMinute));
    }

    public int toInt() {
        return tokenExpireMinute;
    }
}

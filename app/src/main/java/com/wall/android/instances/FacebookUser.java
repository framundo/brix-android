package com.wall.android.instances;

import java.util.Date;

public class FacebookUser {
    final String token;
    final Date expire_date;

    public FacebookUser(String token, Date expire_date) {
        this.token = token;
        this.expire_date = expire_date;
    }

}

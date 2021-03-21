package com.ciprianmosincat.tests.resourceaccess.user;

import com.ciprianmosincat.tests.domain.User;
import org.springframework.stereotype.Component;

@Component
public class UserResourceAccessImpl implements UserInternalResourceAccess {

    @Override
    public User getCurrentLoggedInUser() {
        return null;
    }

}

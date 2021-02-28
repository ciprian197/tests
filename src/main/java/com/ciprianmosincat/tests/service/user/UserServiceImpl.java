package com.ciprianmosincat.tests.service.user;

import com.ciprianmosincat.tests.domain.User;
import org.springframework.stereotype.Service;

@Service
class UserServiceImpl implements UserInternalService {

    @Override
    public User getCurrentLoggedInUser() {
        return null;
    }

}

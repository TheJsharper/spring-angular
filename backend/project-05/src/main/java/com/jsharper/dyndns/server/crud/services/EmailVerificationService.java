package com.jsharper.dyndns.server.crud.services;

import com.jsharper.dyndns.server.crud.models.User;

public interface EmailVerificationService {
    void scheduleEmailConfirmation(User user);
}

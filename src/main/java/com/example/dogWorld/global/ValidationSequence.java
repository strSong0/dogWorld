package com.example.dogWorld.global;

import jakarta.validation.GroupSequence;

@GroupSequence(
        {ValidationGroups.UsernameValidationGroup.class,
        ValidationGroups.PasswordValidationGroup.class,
        ValidationGroups.PasswordCheckValidationGroup.class,
        ValidationGroups.NameValidationGroup.class,
        ValidationGroups.EmailValidationGroup.class,
        ValidationGroups.ImageValidationGroup.class}
)
public interface ValidationSequence {
}

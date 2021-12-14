package com.example.tvtracker;

import org.junit.Test;
import static org.junit.Assert.*;

    public class SignUpTest {

        @Test
        public void emailAddressFormatIsValid() {
            boolean emailIsValid = SignUp.emailIsValid("emma@mail.com");
            assertEquals(true, emailIsValid);
        }

        @Test
        public void emailAddressFormatIsNotValid() {
            boolean emailIsValid = SignUp.emailIsValid("emma_mail.com");
            assertEquals(false, emailIsValid);
        }

    }

package com.example.tvtracker;

import org.junit.Test;
import static org.junit.Assert.*;

    public class SignUpTest {


        // Email format

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


        // String is empty?

        @Test
        public void stringIsNotEmpty() {
            boolean isEmpty = SignUp.isEmpty("Emma", "567", "emma@gmail.com");
            assertEquals(false, isEmpty);
        }

        @Test
        public void usernameIsEmpty() {
            boolean isEmpty = SignUp.isEmpty("", "567", "emma@gmail.com");
            assertEquals(true, isEmpty);
        }

        @Test
        public void passwordIsEmpty() {
            boolean isEmpty = SignUp.isEmpty("Emma", "", "emma@gmail.com");
            assertEquals(true, isEmpty);
        }

        @Test
        public void emailIsEmpty() {
            boolean isEmpty = SignUp.isEmpty("Emma", "567", "");
            assertEquals(true, isEmpty);
        }

        @Test
        public void allIsEmpty() {
            boolean isEmpty = SignUp.isEmpty("", "", "");
            assertEquals(true, isEmpty);
        }



    }

package com.igoryasko.justmusic.validator;

import lombok.NonNull;

import java.util.regex.Pattern;

public class Validator2 {
        private static final String CAR_REGEX_PATTERN =
                "^(CAR),([\\d]+),([\\d.]+),(DISEL|[AI/92,5]+),([4|6|8]+)$";

        private static final String TRUCK_REGEX_PATTERN =
                "^(TRUCK),([\\d]+),([\\d.]+),(DISEL|[AI/92,5]+),([\\d.]+),(TRUE|FALSE)$";

        private static Validator2 instance;

        private Validator2() {
        }

        public static Validator2 getInstance() {
            if (instance == null) {
                return new Validator2();
            }
            return instance;
        }

        public boolean validateLineCar(@NonNull String line) {
            return Pattern.matches(CAR_REGEX_PATTERN, line);
        }

        public boolean validateLineTruck(@NonNull String line) {
            return Pattern.matches(TRUCK_REGEX_PATTERN, line);
        }

}

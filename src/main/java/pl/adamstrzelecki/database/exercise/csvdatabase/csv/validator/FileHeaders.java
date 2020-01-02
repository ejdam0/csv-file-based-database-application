package pl.adamstrzelecki.database.exercise.csvdatabase.csv.validator;

public enum FileHeaders {
    USER_FNAME {
        public String toString() {
            return "first_name";
        }
    },
    USER_LNAME {
        public String toString() {
            return "last_name";
        }
    },
    USER_BDATE {
        public String toString() {
            return "birth_date";
        }
    },
    USER_PHONENO {
        public String toString() {
            return "phone_no";
        }
    }
}

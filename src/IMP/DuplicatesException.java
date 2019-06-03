package IMP;

class DuplicatesException extends Exception {

    DuplicatesException(String msg) {
        super(msg);
    }

    DuplicatesException() {
        super("Cannot enter duplicate values");
    }
}

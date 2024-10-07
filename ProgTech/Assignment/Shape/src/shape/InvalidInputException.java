package shape;

class InvalidInputException extends Exception {

    public InvalidInputException(String msg) {
        super(msg);
    }
    
    public InvalidInputException(){
        super();
    }
}
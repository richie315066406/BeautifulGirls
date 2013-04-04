package org.lansir.beautifulgirls.exception;

public class AkException extends Exception {
    private static final long serialVersionUID =  -2431196726844826744L;
    
    protected AkException(){
        super();
    }

    protected AkException(Throwable t){
        super(t);
    }
    
    public AkException(String msg){
        super(msg);
    }
    
    public AkException(String msg, Throwable t){
        super(msg,t);
}
}

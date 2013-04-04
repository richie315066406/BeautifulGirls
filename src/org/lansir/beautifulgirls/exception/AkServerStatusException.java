package org.lansir.beautifulgirls.exception;


public class AkServerStatusException extends Exception{
    private static final long serialVersionUID = 8831634121316777078L;

    /**
     * exception code
     */
    public int code;
    
    @SuppressWarnings("unused")
    private AkServerStatusException(){
        super();
    }
    @SuppressWarnings("unused")
    private AkServerStatusException(Throwable t){
        super(t);
    }
    
    public AkServerStatusException(int code, String msg){
        super(msg);
        
        this.code = code;
    }
    
    public AkServerStatusException(int code, String msg, Throwable t){
        super(msg,t);
        
        this.code = code;
    }
    


}

package org.lansir.beautifulgirls.exception;

public class AkInvokeException extends AkException {
    private static final long serialVersionUID = -2431196726844826744L;
   
    public static final int CODE_CONNECTION_ERROR = 1000;
    public static final int CODE_HTTP_PROTOCOL_ERROR = 1001;
    public static final int CODE_UNSUPPORT_ENCODING = 1002;
    public static final int CODE_PARSE_EXCEPTION = 1003;
    public static final int CODE_JSONPROCESS_EXCEPTION = 1004;
    public static final int CODE_IO_EXCEPTION = 1005;
    public static final int CODE_FULFILL_INVOKE_EXCEPTION = 1006;
    public static final int CODE_PARAM_IN_URL_NOT_FOUND = 1007;
    public static final int CODE_FILE_NOT_FOUND = 1008;
    public static final int CODE_TARGET_HOST_OR_URL_ERROR = 1009;

    public static final int CODE_UNKOWN_ERROR = 1099;

    /**
     * exception code
     */
    public int code;

    @SuppressWarnings("unused")
    private AkInvokeException(){
        super();
    }
    @SuppressWarnings("unused")
    private AkInvokeException(Throwable t){
        super(t);
    }
    
    public AkInvokeException(int code, String msg){
        super(msg);
        
        this.code = code;
    }
    
    public AkInvokeException(int code, String msg, Throwable t){
        super(msg,t);
        
        this.code = code;
    }

    @Override
    public String toString() {
        return "["+code+"] "+super.toString();    //defaults
    }
}

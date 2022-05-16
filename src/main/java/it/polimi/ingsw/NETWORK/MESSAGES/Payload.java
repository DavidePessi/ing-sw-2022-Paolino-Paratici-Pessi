package it.polimi.ingsw.NETWORK.MESSAGES;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Payload  implements Serializable{
    private static final long serialVersionUID = 1L;
    private Map<String,Object> parameters;

    public Payload(String key1, String nickname){
        parameters = new HashMap<>();
        parameters.put(key1, nickname);
    }

    public Payload(String key1, Integer value){
        parameters = new HashMap<>();
        parameters.put(key1, value);
    }

    public Object getParameter(String type){
        return parameters.get(type);

    }

    public boolean containsParameter(String type){
        return parameters.containsKey(type);
    }



    public String toString() {
        return new StringBuffer(parameters.toString()).toString();
    }
}

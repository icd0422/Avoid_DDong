package org.jun.avoidddongo;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by icd04 on 2017-12-15.
 */

public class InsertRequest extends StringRequest {
    final static private String URL = "http://icd0422.cafe24.com/insert.php" ;
    private Map<String, String> parameters ;

    public InsertRequest(String rank, String rankString, Response.Listener<String> listener)
    {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<String, String>();
        parameters.put("rank", rank);
        parameters.put("rankString", rankString);
    }

    public Map<String, String> getParams()
    {
        return parameters ;
    }
}

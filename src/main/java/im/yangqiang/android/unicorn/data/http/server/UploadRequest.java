package im.yangqiang.android.unicorn.data.http.server;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * 文件上传
 * Created by Carlton on 15/3/1.
 */
public class UploadRequest extends Request<String>
{
    /**
     * 正确数据的时候回掉用
     */
    private Response.Listener<String> mResponseListener;
    private String BOUNDARY            = "--------------520-13-14"; //数据分隔线
    private String MULTIPART_FORM_DATA = "multipart/form-data";

    public UploadRequest(String url, Response.Listener<String> listener, Response.ErrorListener errorListener)
    {
        super(Method.POST, url, errorListener);
        mResponseListener = listener;
        setShouldCache(false);
        setRetryPolicy(new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    /**
     * 这里开始解析数据
     *
     * @param response Response from the network
     * @return
     */
    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response)
    {
        try
        {
            String string = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            return Response.success(string, HttpHeaderParser.parseCacheHeaders(response));
        }
        catch (UnsupportedEncodingException e)
        {
            return Response.error(new ParseError(e));
        }
    }

    /**
     * 回调正确的数据
     *
     * @param response The parsed response returned by
     */
    @Override
    protected void deliverResponse(String response)
    {
        if (mResponseListener == null)
        {
            return;
        }
        mResponseListener.onResponse(response);
    }

    protected String getMime()
    {
        return "application/octet-stream";
    }

    @Override
    public byte[] getBody() throws AuthFailureError
    {
        Map<String, String> params = getParams();
        if (params == null || params.size() == 0)
        {
            return super.getBody();
        }
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        int size = params.size();
        for (String key : params.keySet())
        {
            StringBuffer sb = new StringBuffer();
            /*第一行*/
            //`"--" + BOUNDARY + "\r\n"`
            sb.append("--").append(BOUNDARY);
            sb.append("\r\n");
            /*第二行*/
            //Content-Disposition: form-data; name="参数的名称"; filename="上传的文件名" + "\r\n"
            sb.append("Content-Disposition: form-data;");
            sb.append(" name=\"");
            sb.append(key);
            sb.append("\"");
            sb.append("; filename=\"");
            String fileName = params.get(key);
            sb.append(fileName);
            sb.append("\"");
            sb.append("\r\n");
            /*第三行*/
            //Content-Type: 文件的 mime 类型 + "\r\n"
            sb.append("Content-Type: ");
            sb.append(getMime());
            sb.append("\r\n");
            /*第四行*/
            //"\r\n"
            sb.append("\r\n");
            try
            {
                bos.write(sb.toString().getBytes("utf-8"));
                /*第五行*/
                //文件的二进制数据 + "\r\n"
                bos.write(getByteValue(fileName));
                bos.write("\r\n".getBytes("utf-8"));
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        /*结尾行*/
        //`"--" + BOUNDARY + "--" + "\r\n"`
        String endLine = "--" + BOUNDARY + "--" + "\r\n";
        try
        {
            bos.write(endLine.getBytes("utf-8"));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                bos.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        return bos.toByteArray();
    }

    //Content-Type: multipart/form-data; boundary=----------8888888888888
    @Override
    public String getBodyContentType()
    {
        return MULTIPART_FORM_DATA + "; boundary=" + BOUNDARY;
    }

    private byte[] getByteValue(String fileName)
    {
        FileInputStream fos = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try
        {
            fos = new FileInputStream(new File(fileName));
            int data;
            while ((data = fos.read()) != -1)
            {
                bos.write(data);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (fos != null)
            {
                try
                {
                    fos.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            try
            {
                bos.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        return bos.toByteArray();
    }
}
package im.yangqiang.android.unicorn.data.http;

import android.os.AsyncTask;
import android.text.TextUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import im.yangqiang.android.unicorn.core.log.ULog;
import im.yangqiang.android.unicorn.core.toolbox.FileUtils;

/**
 * 文件下载
 */
public class HttpDownloader
{
    private static final String TAG = "HttpDownloader";

    private class DownloadAsyncTask extends AsyncTask<String, Long, Map<String, Object>>
    {
        private IProgressListener mProgressListener;
        private long              mTotalSize;

        public DownloadAsyncTask(IProgressListener listener)
        {
            mProgressListener = listener;
        }

        @Override
        protected void onProgressUpdate(Long... values)
        {
            super.onProgressUpdate(values);
            mProgressListener.onProgress(mTotalSize, values[0]);
        }

        @Override
        protected void onPostExecute(Map<String, Object> result)
        {
            super.onPostExecute(result);
            boolean isSuccess = (boolean) result.get("state");
            String message = (String) result.get("message");
            if (isSuccess)
            {
                mProgressListener.onFinish(message);
            }
            else
            {
                mProgressListener.onError(message);
            }
        }

        @Override
        protected Map<String, Object> doInBackground(String[] params)
        {
            String httpUrl = params[0];
            String path = params[1];
            ULog.d(TAG, "下载地址:" + httpUrl);
            ULog.d(TAG, "保存路径:" + path);
            Map<String, Object> resultMap = new HashMap<>();
            boolean isSuccess = false;
            if (TextUtils.isEmpty(path))
            {
                resultMap.put("state", false);
                resultMap.put("message", "文件路径是：" + path);
                return resultMap;
            }
            if (TextUtils.isEmpty(httpUrl))
            {
                resultMap.put("state", false);
                resultMap.put("message", "下载地址是：" + httpUrl);
                return resultMap;
            }
            File file = FileUtils.createFile(path);
            URL url;
            HttpURLConnection connection = null;
            InputStream inputStream = null;
            FileOutputStream outputStream = null;
            try
            {
                url = new URL(httpUrl);
                connection = (HttpURLConnection) url.openConnection();
                mTotalSize = connection.getContentLength(); // 文件总大小
                long nowSize = 0; // 当前下载的大小
                inputStream = connection.getInputStream();
                outputStream = new FileOutputStream(file);
                byte[] buf = new byte[256];
                connection.connect();
                if (connection.getResponseCode() >= 400)
                {
                    ULog.d(TAG, "下载文件连接超时");
                    resultMap.put("message", "下载文件超时");
                }
                else
                {
                    while (inputStream != null)
                    {
                        int numRead = inputStream.read(buf);
                        if (numRead < 0)
                        {
                            isSuccess = true;
                            resultMap.put("message", "下载成功");
                            break;
                        }
                        else
                        {
                            outputStream.write(buf, 0, numRead);
                            nowSize += numRead;
                            publishProgress(nowSize);
                        }
                    }

                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
                resultMap.put("message", ULog.getPrintException(e));
            }
            finally
            {
                try
                {
                    if (outputStream != null)
                    {
                        outputStream.close();
                    }
                    if (inputStream != null)
                    {
                        inputStream.close();
                    }
                    if (connection != null)
                    {
                        connection.disconnect();
                    }
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            resultMap.put("state", isSuccess);
            return resultMap;
        }
    }

    /**
     * 后台线程下载文件
     *
     * @param httpUrl
     * @param path
     * @param loadListener
     */
    public void downLoadFileInBack(final String httpUrl, final String path, final IProgressListener loadListener)
    {
        new DownloadAsyncTask(loadListener).execute(httpUrl, path);
    }
}
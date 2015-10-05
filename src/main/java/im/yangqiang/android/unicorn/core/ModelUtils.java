package im.yangqiang.android.unicorn.core;

import java.util.HashMap;
import java.util.Map;

/**
 * 模型实例化工具
 * Created by Carlton on 15/10/4.
 */
public class ModelUtils
{
    private static Map<String, UinModel> mModels = new HashMap<>();

    public static <T extends UinModel> T instance(Class<T> cls)
    {
        String key = cls.getName();
        if (!mModels.keySet().contains(key))
        {
            synchronized (ModelUtils.class)
            {
                if (!mModels.keySet().contains(key))
                {
                    UinModel value = null;
                    try
                    {
                        value = cls.newInstance();
                    }
                    catch (InstantiationException e)
                    {
                        e.printStackTrace();
                    }
                    catch (IllegalAccessException e)
                    {
                        e.printStackTrace();
                    }
                    if (value != null)
                    {
                        mModels.put(key, value);
                    }
                    else
                    {
                        throw new RuntimeException("不能实例化:" + key);
                    }
                }
            }
        }
        return (T) mModels.get(key);
    }
}

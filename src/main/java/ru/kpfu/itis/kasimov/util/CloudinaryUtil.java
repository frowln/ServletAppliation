package ru.kpfu.itis.kasimov.util;

import com.cloudinary.Cloudinary;

import java.util.HashMap;
import java.util.Map;

public class CloudinaryUtil {

    private static Cloudinary cloudinary;

    public static Cloudinary getInstance() {
        if (cloudinary == null) {
            Map<String, String> configMap = new HashMap<>();
            configMap.put("cloud_name", "dgtm90ts8");
            configMap.put("api_key", "118997312764755");
            configMap.put("api_secret", "cwBU1E6svsFDToYTDvmcAQr99kA");
            cloudinary = new Cloudinary(configMap);
        }
        return cloudinary;
    }
}

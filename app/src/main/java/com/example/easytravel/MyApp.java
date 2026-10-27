package com.example.easytravel;

import android.app.Application;
import com.cloudinary.android.MediaManager;
import java.util.HashMap;
import java.util.Map;

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // 初始化 Cloudinary，只需要你的 Cloud Name
        Map<String, String> config = new HashMap<>();
        config.put("cloud_name", "e32aflao"); // 你的 Cloud Name
        MediaManager.init(this, config);
    }
}
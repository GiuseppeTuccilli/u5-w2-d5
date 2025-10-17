package giuseppetuccilli.u5_w2_d5.confic;

import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CloudinaryConfig {
    @Bean
    public Cloudinary getImgUploader(
            @Value("${cloudinary.name}") String name,
            @Value("${cloudinary.key}") String key,
            @Value("${cloudinary.secret}") String secert
    ) {
        Map<String, String> conf = new HashMap<>();
        conf.put("cloud_name", name);
        conf.put("api_key", key);
        conf.put("api_secret", secert);
        return new Cloudinary(conf);
    }
}

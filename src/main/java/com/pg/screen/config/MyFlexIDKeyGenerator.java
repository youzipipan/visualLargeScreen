package com.pg.screen.config;

import com.mybatisflex.core.keygen.impl.FlexIDKeyGenerator;
import org.springframework.context.annotation.Configuration;

/**
 * @author c.chuang
 */
@Configuration
public class MyFlexIDKeyGenerator extends FlexIDKeyGenerator {

    @Override
    public Object generate(Object entity, String keyColumn) {
        return super.generate(entity, keyColumn);
    }
}

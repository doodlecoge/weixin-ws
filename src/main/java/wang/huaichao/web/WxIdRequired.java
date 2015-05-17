package wang.huaichao.web;


import java.lang.annotation.*;

/**
 * Created by Administrator on 2015/5/17.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface WxIdRequired {
    boolean value() default true;
}

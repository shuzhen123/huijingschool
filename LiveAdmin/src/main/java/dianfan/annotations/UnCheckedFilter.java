package dianfan.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * @ClassName UnChecked
 * @Description 此注解的作用是禁止方法拦截（禁止session登录验证）
 * @author cjy
 * @date 2017年12月20日 下午5:12:00
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface UnCheckedFilter {

}

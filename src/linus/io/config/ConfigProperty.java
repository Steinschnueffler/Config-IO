package linus.io.config;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Documented
@Retention(RUNTIME)
@Target(TYPE)
public @interface ConfigProperty {

	public abstract boolean primitive() default false;
	
	public abstract String primitiveValue() default "";
	
	public abstract double sinceVersion() default 1.0;
	
	public abstract double version() default 1.0;
	
	public abstract ConfigType defaultConfigType() default ConfigType.Custom;
	
	public abstract ConfigStat defaultConfigStat() default ConfigStat.Empty;	
}

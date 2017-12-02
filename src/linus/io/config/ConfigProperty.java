package linus.io.config;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 
 * This Annotation saves the default values of a {@link Config} class, such like it is primitive
 * or what version it is.
 * 
 * @author Linus
 * @since 1.0
 * @version 1.0
 * @see Config
 *
 */
@Documented
@Retention(RUNTIME)
@Target(TYPE)
public @interface ConfigProperty {

	/**
	 * @return
	 */
	public abstract boolean primitive() default false;
	
	/**
	 * 
	 * @return
	 */
	public abstract String primitiveValue() default "";
	
	/**
	 * 
	 * @return
	 */
	public abstract double sinceVersion() default 1.0;
	
	/**
	 * 
	 * @return
	 */
	public abstract double version() default 1.0;
	
	/**
	 * 
	 * @return
	 */
	public abstract ConfigType defaultConfigType() default ConfigType.Custom;
	
	/**
	 * 
	 * @return
	 */
	public abstract ConfigStat defaultConfigStat() default ConfigStat.Empty;	
}

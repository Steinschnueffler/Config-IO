package linus.io.config;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Documented
@Retention(RUNTIME)
@Target(TYPE)
/**
 * This Annotation marks that a Config holds a primitive Value. It is used by
 * Config.
 * 
 * @author Linus
 *
 */
public @interface PrimitiveConfig {
	String value();
}

package org.codex.index;

import org.codex.apix.EnableApix;
import org.codex.core.EnableCodex;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(value = java.lang.annotation.RetentionPolicy.RUNTIME)
@Target(value = { java.lang.annotation.ElementType.TYPE })
@EnableApix
@EnableCodex
public @interface EnableCodexLeague {
}

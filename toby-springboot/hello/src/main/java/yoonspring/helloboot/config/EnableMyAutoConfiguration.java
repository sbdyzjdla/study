package yoonspring.helloboot.config;

import org.springframework.context.annotation.Import;

@Import(MyAutoConfigImportSelector.class)
public @interface EnableMyAutoConfiguration {
}

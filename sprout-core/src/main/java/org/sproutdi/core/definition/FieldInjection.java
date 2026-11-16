package org.sproutdi.core.definition;

import java.lang.reflect.Field;

public record FieldInjection(Field field, InjectionPoint point) {
}

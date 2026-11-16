package org.sproutdi.core.definition;

import java.lang.reflect.Method;
import java.util.List;

public record SetterInjection(Method method, List<InjectionPoint> args) {

}

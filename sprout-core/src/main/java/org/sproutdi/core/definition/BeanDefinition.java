package org.sproutdi.core.definition;

import org.sproutdi.core.annotation.ScopeType;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.List;

public class BeanDefinition {
    private final String name;
    private final Class<?> beanClass;
    private final ScopeType scope;
    private final boolean primary;
    private final BeanSource source;
    private final boolean hasCachedMethods;

    // ... fields for COMPONENT_CLASS ...
    private final Constructor<?> targetConstructor;
    private final List<InjectionPoint> constructorArgs;
    private final List<FieldInjection> autowiredFields;
    private final List<SetterInjection> autowiredSetters;

    // ... fields for CONFIG_BEAN_METHOD ...
    private final Method factoryMethod;
    private final String configBeanName;
    private final List<InjectionPoint> factoryMethodArgs;

    private BeanDefinition(
            // ... Common fields ...
            String name, Class<?> beanClass,
            ScopeType scope, boolean primary,
            BeanSource source, boolean hasCachedMethods,
            // ... COMPONENT_CLASS ...
            Constructor<?> targetConstructor,
            List<InjectionPoint> constructorArgs,
            List<FieldInjection> autowiredFields,
            List<SetterInjection> autowiredSetters,
            // ... CONFIG_BEAN_METHOD ...
            Method factoryMethod,
            String configBeanName,
            List<InjectionPoint> factoryMethodArgs
    ) {
        // ... Common fields ...
        this.name = name;
        this.beanClass = beanClass;
        this.scope = scope;
        this.primary = primary;
        this.source = source;
        this.hasCachedMethods = hasCachedMethods;

        // ... COMPONENT_CLASS ...
        this.targetConstructor = targetConstructor;
        this.constructorArgs = constructorArgs != null ? List.copyOf(constructorArgs) : List.of();
        this.autowiredFields = autowiredFields != null ? List.copyOf(autowiredFields) : List.of();
        this.autowiredSetters = autowiredSetters != null ? List.copyOf(autowiredSetters) : List.of();

        // ... COMPONENT_CLASS ...
        this.factoryMethod = factoryMethod;
        this.configBeanName = configBeanName;
        this.factoryMethodArgs = factoryMethodArgs != null ? List.copyOf(factoryMethodArgs) : List.of();
    }

    public static BeanDefinition forComponent(
            // ... Common fields ...
            String name, Class<?> beanClass,
            ScopeType scope, boolean primary,
            boolean hasCachedMethods,
            // ... COMPONENT_CLASS ...
            Constructor<?> targetConstructor,
            List<InjectionPoint> constructorArgs,
            List<FieldInjection> autowiredFields,
            List<SetterInjection> autowiredSetters
    ) {
        return new BeanDefinition(
                name, beanClass,
                scope, primary,
                BeanSource.COMPONENT_CLASS, hasCachedMethods,
                targetConstructor,
                constructorArgs,
                autowiredFields,
                autowiredSetters,
                null,
                null,
                null
        );
    }

    public static BeanDefinition forBeanMethod(
            // ... Common fields ...
            String name, Class<?> beanClass,
            ScopeType scope, boolean primary,
            boolean hasCachedMethods,
            // ... CONFIG_BEAN_METHOD ...
            Method factoryMethod,
            String configBeanName,
            List<InjectionPoint> factoryMethodArgs
    ) {
        return new BeanDefinition(
                name, beanClass,
                scope, primary,
                BeanSource.CONFIG_BEAN_METHOD, hasCachedMethods,
                null,
                null,
                null,
                null,
                factoryMethod,
                configBeanName,
                factoryMethodArgs
        );
    }

    public String getName() {
        return name;
    }

    public Class<?> getBeanClass() {
        return beanClass;
    }

    public ScopeType getScope() {
        return scope;
    }

    public boolean isPrimary() {
        return primary;
    }

    public BeanSource getSource() {
        return source;
    }

    public boolean isHasCachedMethods() {
        return hasCachedMethods;
    }

    public Constructor<?> getTargetConstructor() {
        return targetConstructor;
    }

    public List<InjectionPoint> getConstructorArgs() {
        return constructorArgs;
    }

    public List<FieldInjection> getAutowiredFields() {
        return autowiredFields;
    }

    public List<SetterInjection> getAutowiredSetters() {
        return autowiredSetters;
    }

    public Method getFactoryMethod() {
        return factoryMethod;
    }

    public String getConfigBeanName() {
        return configBeanName;
    }

    public List<InjectionPoint> getFactoryMethodArgs() {
        return factoryMethodArgs;
    }
}

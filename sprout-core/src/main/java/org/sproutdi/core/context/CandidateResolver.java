package org.sproutdi.core.context;

import org.sproutdi.core.definition.BeanDefinition;
import org.sproutdi.core.exception.NoSuchBeanException;
import org.sproutdi.core.exception.NoUniqueBeanException;

import java.util.*;

class CandidateResolver {
    BeanDefinition resolve(Class<?> requiredType, String qualifier, Collection<BeanDefinition> allDefinitions) {
        List<BeanDefinition> candidates = findAllByType(requiredType, allDefinitions);
        if (candidates.isEmpty()) {
            throw new NoSuchBeanException("No bean found of type " + requiredType.getName());
        }

        if (qualifier != null && !qualifier.isBlank()) {
            BeanDefinition candidate = findByQualifier(qualifier, candidates);
            if (candidate == null) {
                throw new NoSuchBeanException("No bean found with qualifier " + qualifier);
            }
            return candidate;
        }

        // only 1 candidate left
        if (candidates.size() == 1) {
            return candidates.getFirst();
        }
        // only 1 candidate is primary
        List<BeanDefinition> uniqueCandidates = new ArrayList<>();
        for (BeanDefinition candidate : candidates) {
            if (candidate.isPrimary()) {
                uniqueCandidates.add(candidate);
            }
        }
        if (uniqueCandidates.size() == 1) {
            return uniqueCandidates.getFirst();
        }
        throw new NoUniqueBeanException(
                "Expected single primary bean for type " + requiredType.getName() +
                ", found " + candidates.size() + " candidates"
        );
    }

    List<BeanDefinition> findAllByType(Class<?> requiredType, Collection<BeanDefinition> allDefinitions) {
        List<BeanDefinition> beans = new ArrayList<>();

        for (BeanDefinition def : allDefinitions) {
            if (requiredType.isAssignableFrom(def.getBeanClass())) {
                beans.add(def);
            }
        }

        return beans;
    }

    private BeanDefinition findByQualifier(String qualifier, Collection<BeanDefinition> allDefinitions) {
        for (BeanDefinition def : allDefinitions) {
            if (Objects.equals(def.getName(), qualifier)) {
                return def;
            }
        }

        return null;
    }
}

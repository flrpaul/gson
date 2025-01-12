package com.google.gson.reflect;

import java.lang.reflect.*;

public class TypeValidator {

    /**
     * Επαληθεύει ότι ο τύπος δεν περιέχει Type Variables.
     */
    public static void verifyNoTypeVariable(Type type) {
        if (type instanceof TypeVariable) {
            TypeVariable<?> typeVariable = (TypeVariable<?>) type;
            throw new IllegalArgumentException(
                "Type argument must not contain a type variable: " + typeVariable.getName());
        } else if (type instanceof GenericArrayType) {
            verifyNoTypeVariable(((GenericArrayType) type).getGenericComponentType());
        } else if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            for (Type typeArgument : parameterizedType.getActualTypeArguments()) {
                verifyNoTypeVariable(typeArgument);
            }
        } else if (type instanceof WildcardType) {
            WildcardType wildcardType = (WildcardType) type;
            for (Type bound : wildcardType.getUpperBounds()) {
                verifyNoTypeVariable(bound);
            }
            for (Type bound : wildcardType.getLowerBounds()) {
                verifyNoTypeVariable(bound);
            }
        }
    }

    /**
     * Επαληθεύει ότι ο τύπος είναι parameterized και έχει τα σωστά arguments.
     */
    public static void validateParameterized(Type rawType, Type... typeArguments) {
        if (!(rawType instanceof Class)) {
            throw new IllegalArgumentException("Raw type must be a class type.");
        }
        
        Class<?> rawClass = (Class<?>) rawType;
        TypeVariable<?>[] typeVariables = rawClass.getTypeParameters();
        
        if (typeArguments.length != typeVariables.length) {
            throw new IllegalArgumentException(
                "Raw type requires " + typeVariables.length + " type arguments, but got " + typeArguments.length);
        }
        
        // Validate type arguments
        for (int i = 0; i < typeArguments.length; i++) {
            Type typeArgument = typeArguments[i];
            TypeVariable<?> typeVariable = typeVariables[i];
            
            for (Type bound : typeVariable.getBounds()) {
                Class<?> boundClass = getRawType(bound);
                Class<?> typeArgumentClass = getRawType(typeArgument);
                
                if (!boundClass.isAssignableFrom(typeArgumentClass)) {
                    throw new IllegalArgumentException(
                        "Type argument " + typeArgument + " does not satisfy bounds for type variable " 
                        + typeVariable.getName() + " declared by " + rawClass.getName());
                }
            }
        }
    }

    /**
     * Λαμβάνει τον raw type από τον Type (αντί για την χρήση του $Gson$Types).
     */
    static Class<?> getRawType(Type type) {
        if (type instanceof Class<?>) {
            return (Class<?>) type;
        } else if (type instanceof ParameterizedType) {
            return (Class<?>) ((ParameterizedType) type).getRawType();
        } else {
            throw new IllegalArgumentException("Unsupported type: " + type);
        }
    }
}
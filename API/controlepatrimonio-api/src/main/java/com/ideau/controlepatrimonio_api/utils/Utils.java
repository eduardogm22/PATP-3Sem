package com.ideau.controlepatrimonio_api.utils;

import java.lang.reflect.Array;
import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;


public class Utils {
    private Utils() {
        throw new UnsupportedOperationException("Classe utilitária - não pode ser instanciada.");
    }

    public static boolean isNullOrEmpty(Object obj) {
        if (obj == null) return true;
        if (obj instanceof String) return ((String) obj).trim().isEmpty();
        if (obj instanceof Number) return ((Number) obj).intValue() == 0;
        if (obj instanceof Collection<?>) {
            return ((Collection<?>) obj).isEmpty();
        }
        if (obj instanceof Map<?, ?>) {
            return ((Map<?, ?>) obj).isEmpty();
        }
        if (obj instanceof List) return ((List<?>)obj).isEmpty();
        if (obj instanceof ArrayList) return ((ArrayList<?>)obj).isEmpty();
        if (obj.getClass().isArray()) {
            return Array.getLength(obj) == 0;
        }
        return false;
    }

    public static String formataAtivo(int intAtivo) {
        if (intAtivo == 1)
            return "Ativo"; 
        else
            return "Inativo";
    }
}

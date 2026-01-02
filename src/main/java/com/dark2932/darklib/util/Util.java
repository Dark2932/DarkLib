package com.dark2932.darklib.util;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Dark2932
 */
public class Util {

    @SafeVarargs
    public static <E> int size(Collection<E>... collections) {
        int size = 0;
        for (Collection<E> collection : collections) {
            size += collection.size();
        }
        return size;
    }

    @SafeVarargs
    public static <E> Collection<E> merge(Collection<E>... collections) {
        if (collections.length == 0) return new ArrayList<>();
        if (collections.length == 1) return collections[0];

        Collection<E> result = new ArrayList<>(size(collections));
        for (Collection<E> collection : collections) {
            result.addAll(collection);
        }
        return result;
    }

}

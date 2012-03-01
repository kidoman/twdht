package com.thoughtworks.dht.contenthashing;

import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;

/* Understands the hashing of a key */
public class HashingStrategy<TKey> {
    public double index(TKey key) {
        HashCode hc = Hashing.sha512()
                .newHasher()
                .putString(key.toString())
                .putLong(key.hashCode())
                .hash();

        return (hc.asLong() & 0xffff) / (double) 0xffff;
    }
}

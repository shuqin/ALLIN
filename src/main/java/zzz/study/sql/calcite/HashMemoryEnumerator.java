package zzz.study.sql.calcite;

import org.apache.calcite.linq4j.Enumerator;

import java.util.Set;

public class HashMemoryEnumerator<E>  implements Enumerator<E> {
    private Set<String> hashes;

    public HashMemoryEnumerator(Set<String> hashes) {
        this.hashes = hashes;
    }

	@Override
    public void close() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
    public E current() {
        return null;
	}

	@Override
    public boolean moveNext() {
        return false;
	}

	@Override
    public void reset() {
		// TODO Auto-generated method stub
		
	}

}

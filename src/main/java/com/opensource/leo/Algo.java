package com.opensource.leo;

public interface Algo<INIT, OUT, INCREMENT> {

    OUT init(INIT init);

    OUT iterate(OUT old, INCREMENT increment);
}

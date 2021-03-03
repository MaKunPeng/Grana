package com.dust.concurrent;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

public class SimpleLock {
    private final Sync sync;
    
    public SimpleLock() {
        sync = new Sync();
    }
    
    public void lock() {
        sync.acquire(1);
    }
    
    public void unlock() {
        sync.release(1);
    }
    
    private static class Sync extends AbstractQueuedSynchronizer {
        private static final long serialVersionUID = -7589744504623156612L;

        @Override
        protected boolean tryAcquire(int arg) {
            return compareAndSetState(0, 1);
        }
        
        @Override
        protected boolean tryRelease(int arg) {
            setState(0);
            return true;
        }
        
        @Override
        protected boolean isHeldExclusively() {
            return getState() == 1;
        }
    }
}

package com.app.models;

import com.app.exceptions.InternalServiceException;

import java.util.concurrent.Semaphore;

public class RequestCounter {

    private int counter;
    private Semaphore semaphore;

    public RequestCounter(int counter, Semaphore semaphore) {
        this.counter = counter;
        this.semaphore = semaphore;
    }

    public int getCounter() {
        return counter;
    }

    public void increaseNumberOfRequests() throws InternalServiceException {
        try {
            this.semaphore.acquire();
            this.counter++;
            Thread.sleep(50);

        } catch (InterruptedException e) {
            //System.out.println(e.getMessage());
            throw new InternalServiceException(500, e.getMessage());
        } finally {
            this.semaphore.release();
        }
    }
}

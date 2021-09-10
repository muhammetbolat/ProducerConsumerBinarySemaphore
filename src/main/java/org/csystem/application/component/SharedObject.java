package org.csystem.application.component;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.concurrent.Semaphore;

@Component
public class SharedObject {
    private final Semaphore m_producerSemaphore;
    private final Semaphore m_consumerSemaphore;
    private int m_val;

    public SharedObject(@Qualifier("producerSemaphore") Semaphore m_producerSemaphore,
                        @Qualifier("consumerSemaphore") Semaphore m_consumerSemaphore)
    {
        this.m_producerSemaphore = m_producerSemaphore;
        this.m_consumerSemaphore = m_consumerSemaphore;
    }

    public void setVal(int val)
    {
        try {
            m_producerSemaphore.acquire();
        } catch (InterruptedException ignore) {
        }

        m_val = val;
        m_consumerSemaphore.release();
    }

    public int getVal()
    {
        try {
            m_consumerSemaphore.acquire();
        } catch (InterruptedException ignore) {
        }

        int val = m_val;
        m_producerSemaphore.release();
        return val;
    }


}


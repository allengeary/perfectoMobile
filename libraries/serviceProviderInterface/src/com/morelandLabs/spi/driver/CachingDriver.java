package com.morelandLabs.spi.driver;

public interface CachingDriver
{
    public boolean isCachingEnabled();
    public void setCachingEnabled( boolean cachingEnabled );
}

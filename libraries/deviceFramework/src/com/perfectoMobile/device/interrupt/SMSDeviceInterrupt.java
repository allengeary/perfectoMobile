package com.perfectoMobile.device.interrupt;

import com.morelandLabs.integrations.perfectoMobile.rest.PerfectoMobile;
import com.perfectoMobile.device.factory.DeviceWebDriver;

public class SMSDeviceInterrupt extends AbstractDeviceInterrupt
{

    @Override
    public void interruptDevice( DeviceWebDriver webDriver )
    {
        PerfectoMobile.instance().device().sendText( executionId, "Test from SMSDeviceInterrupt", deviceId );
    }

}

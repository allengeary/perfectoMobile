import java.util.Iterator;

import com.morelandLabs.integrations.perfectoMobile.rest.PerfectoMobile;
import com.morelandLabs.integrations.perfectoMobile.rest.services.Devices;
import com.morelandLabs.integrations.perfectoMobile.rest.bean.Handset;
import com.morelandLabs.integrations.perfectoMobile.rest.bean.HandsetCollection;

// TODO: Auto-generated Javadoc
/**
 * The Class DeviceListTest.
 */
public class DeviceListTest
{
    
    /**
     * The main method.
     *
     * @param args the arguments
     */
    public static void main( String[] args )
    {
        PerfectoMobile.instance().setBaseUrl( "https://partners.perfectomobile.com" );
        PerfectoMobile.instance().setUserName( "jharrington@morelandlabs.com" );
        PerfectoMobile.instance().setPassword( "Spike123!" );

        Devices deviceHolder = PerfectoMobile.instance().devices();
        HandsetCollection deviceList = deviceHolder.getDevices();
        Iterator devices = deviceList.getHandsetList().iterator();

        while( devices.hasNext() )
        {
            Handset device = (Handset) devices.next();

            String id = device.getDeviceId();
            String desc = device.getDescription();

            System.out.println( id + " (" + desc + ")" );
        }
    }

}
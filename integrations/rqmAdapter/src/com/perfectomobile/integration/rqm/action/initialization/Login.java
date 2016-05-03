package com.perfectomobile.integration.rqm.action.initialization;

import java.net.HttpCookie;
import java.util.Map;
import com.perfectomobile.integration.action.AbstractAdapterAction;
import com.perfectomobile.integration.net.http.Header;
import com.perfectomobile.integration.net.http.HttpRequest;
import com.perfectomobile.integration.net.http.HttpResponse;

public class Login extends AbstractAdapterAction
{
    private static final String AUTH_MESSAGE = "X-com-ibm-team-repository-web-auth-msg";
    private static final String AUTH_FAILED = "authfailed";
    public static final String NAME = "Login";

    //
    // Static Headers
    //
    private static final Header ACCEPT_HEADER = new Header( "Accept-Encoding", "gzip,deflate" );
    private static final Header REQUEST_HEADER = new Header( "X-Requested-With", "XMLHttpRequest" );
    private static final Header OSLC_HEADER = new Header( "OSLC-Core-Version", "2.0" );
    private static final Header CONNECTION_HEADER = new Header( "Connection", "Keep-Alive" );
    private static final Header CONTENT_TYPE_HEADER = new Header( "Content-Type", "application/x-www-form-urlencoded" );

    //
    // Static Parameter Names
    //
    private static final String USER_NAME = "j_username";
    private static final String PASSWORD = "j_password";

    //
    // Static Request Names
    //
    private static final String AUTH_ID = "authenticated/identity";
    private static final String SECURITY_CHECK = "j_security_check";

    private String baseUrl;
    private String userName;
    private String password;

    public Login( String baseUrl, String userName, String password )
    {
        this.baseUrl = baseUrl;
        this.userName = userName;
        this.password = password;
    }

    @Override
    protected Object _performAction( Map<String, Object> actionParameters ) throws Exception
    {
        //
        // Perform the initial GET request to get a Session ID
        //
        HttpResponse httpResponse = doGet( baseUrl + AUTH_ID );

        HttpCookie sessionId = null;

        try
        {
            sessionId = httpResponse.getresponseHeaders().getCookie( JSESSIONID );
        }
        catch ( Exception e )
        {
            log.error( "JSESSION ID was NOT AVAILABLE" );
        }

        //
        // Build the Request Headers
        //
        HttpRequest securityCheck = new HttpRequest();
        securityCheck.getRequestHeaders().addHeader( ACCEPT_HEADER );
        securityCheck.getRequestHeaders().addHeader( REQUEST_HEADER );
        securityCheck.getRequestHeaders().addHeader( OSLC_HEADER );
        securityCheck.getRequestHeaders().addHeader( CONNECTION_HEADER );
        securityCheck.getRequestHeaders().addHeader( CONTENT_TYPE_HEADER );
        if ( sessionId != null )
            securityCheck.getRequestHeaders().addHeader( Header.COOKIE, sessionId.toString() );

        //
        // Build the LOGIN POST data
        //
        StringBuilder loginBuilder = new StringBuilder();
        loginBuilder.append( USER_NAME ).append( "=" ).append( userName );
        loginBuilder.append( "&" ).append( PASSWORD ).append( "=" ).append( password );
        securityCheck.setRequestBody( loginBuilder.toString().getBytes() );

        //
        // Login
        //
        httpResponse = doPost( baseUrl + SECURITY_CHECK, securityCheck );

        //
        // Check for the JAZZ failure
        //
        Header authMessage = httpResponse.getresponseHeaders().getHeader( AUTH_MESSAGE );
        if ( authMessage != null && AUTH_FAILED.equals( authMessage.getValues().get( 0 ) ) )
            throw new SecurityException( "Could not authenticate user [" + userName + "]" );

        //
        // Check for any other HTTP Failure
        //
        if ( httpResponse.getresponseDefinition().getCode() != 302 )
            throw new IllegalStateException( "The login request could not be properly served" );

        //
        // Now get the authenticated session id
        //
        HttpRequest authIdentity = new HttpRequest();
        if ( sessionId != null )
            authIdentity.getRequestHeaders().addHeader( Header.COOKIE, sessionId.toString() );
        
        httpResponse = doGet( baseUrl + AUTH_ID, authIdentity );
        sessionId = httpResponse.getresponseHeaders().getCookie( JSESSIONID );
        if ( sessionId == null )
        {
            try
            {
                sessionId = httpResponse.getresponseHeaders().getCookie( JSESSIONID );
            }
            catch ( Exception e )
            {
                log.error( "Session ID was not returned the second time" );
            }
        }

        return sessionId;
    }

    public String getName()
    {
        return NAME;
    }

}

/*
 * Copyright 2009 Martin Grotzke
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an &quot;AS IS&quot; BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package de.javakaffee.misc.wicket.ccs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.wicket.Request;
import org.apache.wicket.Response;
import org.apache.wicket.Session;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.protocol.http.WebSession;
import org.apache.wicket.request.IRequestCycleProcessor;
import org.apache.wicket.request.target.coding.HybridUrlCodingStrategy;
import org.apache.wicket.settings.IExceptionSettings;

/**
 * Application object for your web application. If you want to run this
 * application without deploying, run the Start class.
 * 
 * @see de.javakaffee.misc.wicket.dropdowns.Start#main(String[])
 */
public class WicketApplication extends WebApplication {

    public static final List<String> MAKES_DB = Arrays.asList( "Audi", "Cadillac", "Ford" );
    
    /**
     * Constructor
     */
    public WicketApplication() {
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected void init() {
        super.init();
        
        getDebugSettings().setDevelopmentUtilitiesEnabled( true );
        getDebugSettings().setAjaxDebugModeEnabled( true );
        getDebugSettings().setOutputComponentPath( true );
        getDebugSettings().setLinePreciseReportingOnAddComponentEnabled( true );
        getDebugSettings().setLinePreciseReportingOnNewComponentEnabled( true );

        getRequestLoggerSettings().setRequestLoggerEnabled( true );
        getRequestLoggerSettings().setRecordSessionSize( true );
        
        getExceptionSettings().setUnexpectedExceptionDisplay( IExceptionSettings.SHOW_EXCEPTION_PAGE );
        
        mount(new HybridUrlCodingStrategy("select", ChoicePage.class));
    }

    /**
     * @see org.apache.wicket.Application#getHomePage()
     */
    public Class<HomePage> getHomePage() {
        return HomePage.class;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected IRequestCycleProcessor newRequestCycleProcessor() {
        return new CustomRequestCycleProcessor();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Session newSession( Request request, Response response ) {
        return new MySession( request );
    }
    
    static class MySession extends WebSession {

        private static final long serialVersionUID = 1L;
        
        private List<String> _items; 
        
        public MySession( Request request ) {
            super( request );
        }

        public List<String> getItems() {
            return _items != null ? Collections.unmodifiableList( _items ) : Collections.<String> emptyList();
        }
        
        public void addItem( String item ) {
            if ( _items == null ) {
                _items = new ArrayList<String>();
            }
            _items.add( item );
        }
        
        public static MySession get() {
            return (MySession) Session.get();
        }
        
    }

}

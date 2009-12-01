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
package de.javakaffee.misc.wicket.dropdowns;

import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.target.coding.HybridUrlCodingStrategy;
import org.apache.wicket.settings.IExceptionSettings;
import org.apache.wicket.settings.IExceptionSettings.UnexpectedExceptionDisplay;

/**
 * Application object for your web application. If you want to run this
 * application without deploying, run the Start class.
 * 
 * @see de.javakaffee.misc.wicket.dropdowns.Start#main(String[])
 */
public class WicketApplication extends WebApplication {
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
        
        mount(new HybridUrlCodingStrategy("choice", ChoicePage.class));
    }

    /**
     * @see org.apache.wicket.Application#getHomePage()
     */
    public Class<HomePage> getHomePage() {
        return HomePage.class;
    }

}

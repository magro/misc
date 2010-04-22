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

import static de.javakaffee.misc.wicket.ccs.WicketApplication.MAKES_DB;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.StatelessForm;
import org.apache.wicket.model.Model;

import de.javakaffee.misc.wicket.ccs.WicketApplication.MySession;

/**
 * Submit a chosen value via a stateless form and display this value.
 */
public class DetailsPage extends BasePage {

    private static final String PARAM_KEY = SingleParameterNoPathCodingStrategy.FIRST_PARAMETER_KEY;
    private static final String PARAM_VALUE_SUFFIX = ".html";
    private static final String SERVLET_PATH_PATTERN_STRING = "/?[^/=?]*i(\\d)+(:?\\.html)?(/.*)?";
    private static final Pattern SERVLET_PATH_PATTERN = Pattern.compile( SERVLET_PATH_PATTERN_STRING );

    public static final SingleParameterNoPathCodingStrategy URL_CODING_STRATEGY =
            new SingleParameterNoPathCodingStrategy( "", DetailsPage.class );

    public static boolean isRequestedBy( final String servletPath ) {
        return SERVLET_PATH_PATTERN.matcher( servletPath ).matches();
    }

    /**
     * Constructor.
     */
    public DetailsPage( PageParameters params ) {
        super( params );

        final Integer submittedMakeId = getMakeIdFrom( params );
        if ( submittedMakeId == null ) {
            throw new NullPointerException( "No valid make provided! (params: " + params + ")" );
        }
        final String submittedMake = MAKES_DB.get( submittedMakeId );

        setStatelessHint( true );
        
        final Label subline = new Label( "subline", "Buy make '" + submittedMake + "' now!" );
        add( subline );

        final Form<?> form = new StatelessForm<Object>( "form" );
        add( form );
        
        form.add( new Button( "submit", new Model<String>( "Buy now!" ) ) {
            private static final long serialVersionUID = 1L;

            public void onSubmit() {
                final MySession session = MySession.get();
                session.addItem( submittedMake );
                if ( session.isTemporary() ) {
                    session.bind();
                }
                final PageParameters params = pageParametersFor( submittedMake );
                setResponsePage( getPage().getClass(), params );
            };
        } );


    }

    public static PageParameters pageParametersFor( final String make ) {
        // very simple by-name lookup in our "db"
        int id = MAKES_DB.indexOf( make );
        if ( id == -1 ) {
            throw new IllegalArgumentException( "No make " + make + " found." );
        }
        final PageParameters params = new PageParameters();
        // see also SERVLET_PATH_PATTERN_STRING
        params.put( PARAM_KEY, make + "-i" + id + PARAM_VALUE_SUFFIX );
        return params;
    }

    public static Integer getMakeIdFrom( final PageParameters params ) {
        final String value = params.getString( PARAM_KEY );
        if ( value != null ) {
            Matcher matcher = SERVLET_PATH_PATTERN.matcher( value );
            if ( matcher.matches() ) {
                return Integer.parseInt( matcher.group( 1 ) );
            }
        }
        return null;
    }

}

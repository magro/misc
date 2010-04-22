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

import org.apache.wicket.IRequestTarget;
import org.apache.wicket.protocol.http.WebRequestCycleProcessor;
import org.apache.wicket.protocol.http.request.WebRequestCodingStrategy;
import org.apache.wicket.request.IRequestCodingStrategy;
import org.apache.wicket.request.target.coding.IRequestTargetUrlCodingStrategy;
import org.apache.wicket.request.target.component.BookmarkablePageRequestTarget;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 */
public class CustomRequestCycleProcessor extends WebRequestCycleProcessor {
    
    @SuppressWarnings( "unused" )
    private static final Logger LOG = LoggerFactory.getLogger( CustomRequestCycleProcessor.class );

    static final class WebshopRequestCodingStrategy extends WebRequestCodingStrategy {
        
        @Override
        public IRequestTargetUrlCodingStrategy urlCodingStrategyForPath( final String path ) {
            final IRequestTargetUrlCodingStrategy strategy = super.urlCodingStrategyForPath( path );
            if ( strategy == null ) {
                if ( DetailsPage.isRequestedBy( path ) ) {
                    return DetailsPage.URL_CODING_STRATEGY;
                }
            }
            return strategy;
        }
        
        @Override
        protected IRequestTargetUrlCodingStrategy getMountEncoder( final IRequestTarget requestTarget ) {
            if ( requestTarget instanceof BookmarkablePageRequestTarget ) {
                final BookmarkablePageRequestTarget bookmarkableTarget = (BookmarkablePageRequestTarget) requestTarget;
                if ( bookmarkableTarget.getPageClass() == DetailsPage.class ) {
                    return DetailsPage.URL_CODING_STRATEGY;
                }
            }
            return super.getMountEncoder( requestTarget );
        }
    }
    
    @Override
    protected IRequestCodingStrategy newRequestCodingStrategy() {
        return new WebshopRequestCodingStrategy();
    }

}

package org.apache.maven.scm.plugin;

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.scm.ScmException;
import org.apache.maven.scm.ScmTagParameters;
import org.apache.maven.scm.command.tag.TagScmResult;
import org.apache.maven.scm.provider.ScmProvider;
import org.apache.maven.scm.repository.ScmRepository;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Tag the project.
 *
 * @author <a href="evenisse@apache.org">Emmanuel Venisse</a>
 * @author <a href="saden1@gmil.com">Sharmarke Aden</a>
 *
 * @goal tag
 * @aggregator
 */
public class TagMojo
    extends AbstractScmMojo
{
    /**
     * The tag name.
     *
     * @parameter expression="${tag}"
     * @required
     */
    private String tag;

    /**
     * The message applied to the tag creation.
     *
     * @parameter expression="${message}"
     */
    private String message;

    /**
     * Set the timestamp format.
     *
     * @parameter expression="${timestampFormat}" default-value="yyyyMMddHHmmss"
     */
    private String timestampFormat;

    /**
     * Use timestamp tagging.
     *
     * @parameter expression="${addTimestamp}" default-value="false"
     */
    private boolean addTimestamp;

    /**
     * Define the timestamp position (end or begin).
     *
     * @parameter expression="${timestampPosition}" default-value="end"
     */
    private String timestampPosition;

    /**
     * Timestamp tag prefix.
     *
     * @parameter expression="${timestampPrefix}" default-value="-"
     */
    private String timestampPrefix;
    
    /**
     * currently only implemented with svn scm. Enable a workaround to prevent issue 
     * due to svn client > 1.5.0 (http://jira.codehaus.org/browse/SCM-406)
     *      
     * 
     * @parameter expression="${remoteTagging}" default-value="true"
     * @since 1.2
     */    
    private boolean remoteTagging;    

    /** {@inheritDoc} */
    public void execute()
        throws MojoExecutionException
    {
        super.execute();

        try
        {
            SimpleDateFormat dateFormat = null;
            String tagTimestamp = "";
            String finalTag = tag;

            if ( addTimestamp )
            {
                try
                {
                    getLog().info( "Using timestamp pattern '" + timestampFormat + "'" );
                    dateFormat = new SimpleDateFormat( timestampFormat );
                    tagTimestamp = dateFormat.format( new Date() );
                    getLog().info( "Using timestamp '" + tagTimestamp + "'" );
                }
                catch ( IllegalArgumentException e )
                {
                    String msg = "The timestamp format '" + timestampFormat + "' is invalid.";
                    getLog().error( msg, e );
                    throw new MojoExecutionException( msg, e );
                }

                if ( "end".equals( timestampPosition ) )
                {
                    finalTag += timestampPrefix + tagTimestamp;
                }
                else
                {
                    finalTag = tagTimestamp + timestampPrefix + finalTag;
                }
            }

            ScmRepository repository = getScmRepository();
            ScmProvider provider = getScmManager().getProviderByRepository( repository );

            finalTag = provider.sanitizeTagName( finalTag );
            getLog().info( "Final Tag Name: '" + finalTag + "'" );

            ScmTagParameters scmTagParameters = new ScmTagParameters( message);
            scmTagParameters.setRemoteTagging( remoteTagging );
            
            TagScmResult result = provider.tag( repository, getFileSet(), finalTag, scmTagParameters);

            checkResult( result );
        }
        catch ( IOException e )
        {
            throw new MojoExecutionException( "Cannot run tag command : ", e );
        }
        catch ( ScmException e )
        {
            throw new MojoExecutionException( "Cannot run tag command : ", e );
        }
    }
}

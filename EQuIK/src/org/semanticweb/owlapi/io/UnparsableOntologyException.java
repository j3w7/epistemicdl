package org.semanticweb.owlapi.io;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;

import java.net.URI;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
/*
 * Copyright (C) 2007, University of Manchester
 *
 * Modifications to the initial code base are copyright of their
 * respective authors, or their employers as appropriate.  Authorship
 * of the modifications may be determined from the ChangeLog placed at
 * the end of this file.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.

 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.

 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 11-Apr-2008<br><br>
 * </p>
 * A class that describes how ontology parsing failed.  This class collects parse errors and the parsers that
 * generated the errors.
 */
public class UnparsableOntologyException extends OWLOntologyCreationException {

    private static boolean includeStackTraceInMessage = false;

    private IRI documentIRI;

    private Map<OWLParser, OWLParserException> exceptions;

    public UnparsableOntologyException(IRI documentIRI, Map<OWLParser, OWLParserException> exceptions) {
        super("Could not parse ontology from document IRI: " + documentIRI.toQuotedString());
        this.documentIRI = documentIRI;
        this.exceptions = new LinkedHashMap<OWLParser, OWLParserException>(exceptions);
    }


    public String getMessage() {
        StringBuilder msg = new StringBuilder();
        msg.append("Problem parsing ");
        msg.append(documentIRI);
        msg.append("\n");
        msg.append("Could not parse ontology.  Either a suitable equik.parser could not be found, or " + "parsing failed.  See equik.parser logs below for explanation.\n");
        msg.append("The following parsers were tried:\n");
        int counter = 1;
        for (OWLParser parser : exceptions.keySet()) {
            msg.append(counter);
            msg.append(") ");
            msg.append(parser.getClass().getSimpleName());
            msg.append("\n");
            counter++;
        }
        msg.append("\n\nDetailed logs:\n");
        for (OWLParser parser : exceptions.keySet()) {
            Throwable exception = exceptions.get(parser);
            msg.append("--------------------------------------------------------------------------------\n");
            msg.append("Parser: ");
            msg.append(parser.getClass().getSimpleName());
            msg.append("\n");
            msg.append(exception.getMessage());
            msg.append("\n\n");
            if (includeStackTraceInMessage) {
                msg.append("    Stack trace:\n");
                for (StackTraceElement element : exception.getStackTrace()) {
                    msg.append("        ");
                    msg.append(element.toString());
                    msg.append("\n");
                }
                msg.append("\n\n");
            }
        }
        return msg.toString();
    }


    /**
     * Gets the ontology document IRI from which there was an attempt to parse an ontology
     * @return The ontology document IRI
     */
    public IRI getDocumentIRI() {
        return documentIRI;
    }


    /**
     * Determines if the stack trace for each parse exception is
     * included in the getMessage() method.
     * @return <code>true</code> if the stack trace is included in the
     * message for this exception, other wise <code>false</code>.
     */
    public static boolean isIncludeStackTraceInMessage() {
        return includeStackTraceInMessage;
    }


    /**
     * Specifies whether the stack trace for each equik.parser exception should be included in the message
     * generated by this exception - this can be useful for debugging purposes, but can bloat the
     * message for end user usage.
     * @param includeStackTraceInMessage Set to <code>true</code> to indicate that the stack
     * trace for each equik.parser exception should be included in the message for this exception,
     * otherwise set to <code>false</code>.
     */
    public static void setIncludeStackTraceInMessage(boolean includeStackTraceInMessage) {
        UnparsableOntologyException.includeStackTraceInMessage = includeStackTraceInMessage;
    }


    /**
     * Gets a map that lists the parsers (that were used to parse an
     * ontology) and the errors that they generated.
     * @return The map of parsers and their errors.
     */
    public Map<OWLParser, OWLParserException> getExceptions() {
        return Collections.unmodifiableMap(exceptions);
    }
}